package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.GPSTracker;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.todddavies.components.progressbar.ProgressWheel;

public class MakeAppointmentFragment extends Fragment implements
		OnScrollListener {

	ListView listview_spa;
	SpaListAdapter adapter;
	Typeface font;
	private View rootView;
	JSONParser jsonParser = new JSONParser();
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	
	
	private GetTenSpa getTenSpaTask;
	private FetchNearestSpa getTenNearestSpaTask;
	
	int hit_counter = 0;
	
	private ArrayList<String> ArrayList_Filter = new ArrayList<String>();
	private boolean isloading = false;
	private boolean isloadingNearestSpa = false;
	Spinner filter;
	private boolean isDataAvailable = true;
	private boolean isNearestDataAvailable = true;
	ProgressWheel progressbar_footer;
	ProgressWheel progressbar_header;

	ArrayList<HashMap<String, String>> SpaDetails = new ArrayList<HashMap<String, String>>();
	
//	private ArrayList<Spa_Data> NearestLocations = new ArrayList<Spa_Data>();
//	private Dialog NearestSpaDialog;
	
	static final String SPA_ID = "spa_id";
	static final String SPA_NAME = "spa_name";
	static final String SPA_Address = "spa_addr";

	int Selected_Filter;
	private double mLastLatitude;
	private double mLastLongitude;
	int VisiblePosition,distFromTop;
	TextView Txt_Title;
	ProgressWheel pw_four;
	Handler handler = new Handler();
	Runnable refresh;
	Dialog dialog;
	GPSTracker gps;
	private View footer;

	public MakeAppointmentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_makeappointment_spa_list,
				container, false);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");

		footer = inflater.inflate(R.layout.footer, null);

		progressbar_footer = (ProgressWheel) footer
				.findViewById(R.id.progressbar_footer);
		progressbar_footer.spin();

		listview_spa = (ListView) rootView.findViewById(R.id.listview_spa);

		adapter = new SpaListAdapter(getActivity(), SpaDetails);
		listview_spa.setAdapter(adapter);

		listview_spa.setOnScrollListener(this);

		/**
		 * Add footer to listview
		 */
		listview_spa.addFooterView(footer);
		
		/**
		 * Dialog Box
		 */

		dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_progress_dialog);
		dialog.setCancelable(false);

		Txt_Title = (TextView) dialog.findViewById(R.id.txt_alert_text);
		pw_four = (ProgressWheel) dialog.findViewById(R.id.progressBarFour);

		Txt_Title.setTypeface(font);

		/**
		 * Filter drop down
		 */
		filter = (Spinner) rootView.findViewById(R.id.spinner_sort_by);

		ArrayList_Filter.add("Show All");
		ArrayList_Filter.add("Nearest Spa's");
		ArrayList_Filter.add("Popularity");
		ArrayList_Filter.add("Price- High to Low");
		ArrayList_Filter.add("Price- Low to High");
		ArrayList_Filter.add("Newest");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_item, ArrayList_Filter);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		filter.setAdapter(dataAdapter);

		filter.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				switch (position) {
				case 0:
					hit_counter = 0;

//					Txt_Title.setText("Fetching data....");
//					pw_four.spin();
//					dialog.show();

					Selected_Filter = 0;
					listview_spa.setAdapter(null);
					if (!SpaDetails.isEmpty())
						SpaDetails.clear();
					getTenSpaTask = new GetTenSpa();
					getTenSpaTask.execute();
					 
					
					break;

				case 1:
					hit_counter = 0;
					Selected_Filter = 1;
					gps = new GPSTracker(getActivity());
					if (gps.canGetLocation()) {

						listview_spa.setAdapter(null);
						if (!SpaDetails.isEmpty())
							SpaDetails.clear();

//						Txt_Title.setText("Fetching Nearest Spa....");
//						pw_four.spin();
//						dialog.show();
						getTenNearestSpaTask = new FetchNearestSpa();
						getTenNearestSpaTask.execute();
						
//						 scrollMyListViewToBottom();
					} else {
						// can't get location
						// GPS or Network is not enabled
						// Ask user to enable GPS/network in settings

						gps.showSettingsAlert();
						refresh = new Runnable() {
							public void run() {
								// Do something
								handler.postDelayed(refresh, 5);
								// dialog.show();
							}
						};
						handler.post(refresh);
						listview_spa.setAdapter(null);
						if (!SpaDetails.isEmpty())
							SpaDetails.clear();
								listview_spa.setOnScrollListener(null);
								listview_spa.removeFooterView(footer);
								progressbar_footer.stopSpinning();
								// adapter.notifyDataSetChanged();

							
					}

					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		
	

		return rootView;

	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int loadedItems = (firstVisibleItem) + visibleItemCount;
		
		switch (Selected_Filter) {

		case 0:
			/**
			 * Select All Spa's On Scroll event- Load 10 items on scroll end
			 */

			if (isDataAvailable) 
			{
				if ((loadedItems == totalItemCount) && !isloading) {

					if (getTenSpaTask != null	&& (getTenSpaTask.getStatus() == AsyncTask.Status.FINISHED)) {
						getTenSpaTask = new GetTenSpa();
						getTenSpaTask.execute();
						
					}
				}
			} else {
				listview_spa.setOnScrollListener(null);
				listview_spa.removeFooterView(footer);
				progressbar_footer.stopSpinning();
				// adapter.notifyDataSetChanged();

			}
			break;
		case 1:
			/**
			 * Select Nearest Spa's On Scroll event- Load 10 items on scroll end
			 */
			
			if (isNearestDataAvailable) {
					if ((loadedItems == totalItemCount) && !isloadingNearestSpa) {

						if (getTenNearestSpaTask != null
								&& (getTenNearestSpaTask.getStatus() == AsyncTask.Status.FINISHED)) {
							getTenNearestSpaTask = new FetchNearestSpa();
							getTenNearestSpaTask.execute();
							
						}
					}
				} else {
					listview_spa.setOnScrollListener(null);
					listview_spa.removeFooterView(footer);
					progressbar_footer.stopSpinning();
					// adapter.notifyDataSetChanged();

				}
			 
			break;

		}
		/**
		 * When you scroll down and start getting new data store  Last Visible Position in some variables using this method 
		 */
		VisiblePosition= listview_spa.getFirstVisiblePosition();
		View v = listview_spa.getChildAt(0);
		distFromTop= (v == null) ? 0 : v.getTop();

	}

	/**
	 * Fetch 10 Nearest Spa's
	 * 
	 * @author Pallavi Udawant
	 * 
	 */
	public class FetchNearestSpa extends AsyncTask<String, String, String> {

		// private ProgressDialog pDialog;

		int success;
		JSONParser jsonParser = new JSONParser();
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();


		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			SystemClock.sleep(1000);
			isloadingNearestSpa = true;
			try {
				// Building Parameters
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				// check if GPS enabled
				if (gps.canGetLocation()) {

					mLastLatitude = gps.getLatitude();
					mLastLongitude = gps.getLongitude();

				}
				Log.d("Sending Lat", String.valueOf(mLastLatitude));
				Log.d("Sending Long", String.valueOf(mLastLongitude));
				params1.add(new BasicNameValuePair("CurrentLat", String
						.valueOf(mLastLatitude)));
				params1.add(new BasicNameValuePair("CurrentLong", String
						.valueOf(mLastLongitude)));
				params1.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.get_nearest_ten_spa, "POST", params1);

				Log.d("Hit count", String.valueOf(hit_counter));

				// json success element
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Executing Nearest 10 ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						HashMap<String, String> spaDetails = new HashMap<String, String>();
						spaDetails.put(SPA_ID, Temp.getString("Spa_Id"));
						spaDetails.put(SPA_NAME, Temp.getString("Spa_Name"));
						spaDetails.put(SPA_Address, Temp.getString("Addresss"));

						SpaDetails.add(spaDetails);

						isNearestDataAvailable = true;
					}

					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			// pDialog.dismiss();
			if (dialog.isShowing())
				dialog.dismiss();
			isloadingNearestSpa = false;

			if (file_url.contains("Spa Found !")) {
				listview_spa.setAdapter(adapter);
				listview_spa.setSelectionFromTop(VisiblePosition, distFromTop);
				hit_counter++;

			} else if (file_url.equalsIgnoreCase("false")) {
				/**
				 * Stop loading items if data finished
				 */
				listview_spa.setOnScrollListener(null);
				listview_spa.removeFooterView(footer);
				progressbar_footer.stopSpinning();
				isNearestDataAvailable = false;
			}

		}
	}

	/**
	 * Get All Spa's-10 at a time
	 * 
	 * @author Pallavi Udawant
	 * 
	 */
	class GetTenSpa extends AsyncTask<String, String, String> {

		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {

			// TODO Auto-generated method stub
			SystemClock.sleep(3000);
			isloading = true;
			try {
				// Building Parameters
				List<NameValuePair> Newparams = new ArrayList<NameValuePair>();

				Newparams.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.get_ten_spa,
						"POST", Newparams);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Ten spa ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						HashMap<String, String> spaDetails = new HashMap<String, String>();

						spaDetails.put(SPA_ID, Temp.getString("Spa_Id"));
						spaDetails.put(SPA_NAME, Temp.getString("Spa_Name"));
						spaDetails.put(SPA_Address, Temp.getString("Addresss"));

						SpaDetails.add(spaDetails);

						isDataAvailable = true;

					}

					return json.getString(TAG_MESSAGE) + " , Please Login !";
				} else if (success == 0) {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					isDataAvailable = false;
					listview_spa.setOnScrollListener(null);
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			if (dialog.isShowing())
				dialog.dismiss();
			isloading = false;

			if (file_url.contains("Spa Found !")) {
				Log.v("onPostLog If", file_url);

				listview_spa.setAdapter(adapter);
				listview_spa.setSelectionFromTop(VisiblePosition, distFromTop);
				hit_counter++;

			} else if (file_url.equalsIgnoreCase("false")) {
				Log.v("onPostLog Else", file_url);
				listview_spa.setOnScrollListener(null);
				listview_spa.removeFooterView(footer);
				
				progressbar_footer.stopSpinning();
				listview_spa.setAdapter(adapter);
				isDataAvailable = false;

			}
			

		}
	}

}
