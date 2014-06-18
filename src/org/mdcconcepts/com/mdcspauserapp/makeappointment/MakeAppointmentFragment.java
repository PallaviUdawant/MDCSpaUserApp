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
import org.mdcconcepts.com.mdcspauserapp.findspa.FindSpaFragment;
import org.mdcconcepts.com.mdcspauserapp.findspa.Spa_Data;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import com.todddavies.components.progressbar.ProgressWheel;

import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class MakeAppointmentFragment extends Fragment {

	ListView listview_spa;
	SpaListAdapter adapter;
	Typeface font;
	private View rootView;
	JSONParser jsonParser = new JSONParser();
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	private ArrayList<String> ArrayList_Filter = new ArrayList<String>();

	Spinner filter;
	// private List<String> ArrayList_AllSpaList = new ArrayList<String>();
	// private ArrayList<String> ArrayList_AllSpaIdList = new
	// ArrayList<String>();
	//
	ArrayList<HashMap<String, String>> SpaDetails = new ArrayList<HashMap<String, String>>();
	private ArrayList<Spa_Data> NearestLocations = new ArrayList<Spa_Data>();

	static final String SPA_ID = "spa_id";
	static final String SPA_NAME = "spa_name";
	static final String SPA_Address = "spa_addr";

	private double mLastLatitude;
	private double mLastLongitude;

	GPSTracker gps;

	public MakeAppointmentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_makeappointment_spa_list,
				container, false);

		listview_spa = (ListView) rootView.findViewById(R.id.listview_spa);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");

		

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

					new GetAllSpa().execute();

					break;

				case 1:
					
					gps = new GPSTracker(getActivity());
					if (gps.canGetLocation()) {
						listview_spa.setAdapter(null);
						new FetchNearestSpa().execute();
					} else {
						// can't get location
						// GPS or Network is not enabled
						// Ask user to enable GPS/network in settings
						gps.showSettingsAlert();
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

	public class FetchNearestSpa extends AsyncTask<String, String, String> {

		// private ProgressDialog pDialog;
		private Dialog dialog;
		int success;
		JSONParser jsonParser = new JSONParser();
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//
			dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Fetching data....");
			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// Building Parameters
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				// check if GPS enabled
				if (gps.canGetLocation()) {

					mLastLatitude = gps.getLatitude();
					mLastLongitude = gps.getLongitude();

					// \n is for new line
					// Toast.makeText(
					// getActivity(),
					// "Your Location is - \nLat: " + mLastLatitude
					// + "\nLong: " + mLastLongitude, Toast.LENGTH_LONG)
					// .show();
					// Log.d("latitude", "" + mLastLatitude);
					// Log.d("longitude", "" + mLastLongitude);

				}
				Log.d("Sending Lat", String.valueOf(mLastLatitude));
				Log.d("Sending Long", String.valueOf(mLastLongitude));
				params1.add(new BasicNameValuePair("CurrentLat", String
						.valueOf(mLastLatitude)));
				params1.add(new BasicNameValuePair("CurrentLong", String
						.valueOf(mLastLongitude)));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.NearestSpa_URL, "POST", params1);

				// full json response
				Log.d("Nearest Spa", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);
						// NearestLocations= new ArrayList<Spa_Data> ();

						String addr = Temp.getString("Addresss");
						Log.d("data", addr);
						//
						// NearestLocations.add(new Spa_Data(Temp
						// .getString("Spa_Name"), Temp
						// .getString("Spa_Id"),
						// Temp.getString("Spa_Lat"), Temp
						// .getString("Spa_long"), addr));
						// Log.d("address",
						// NearestLocations.get(i).Spa_Address);

						HashMap<String, String> spaDetails = new HashMap<String, String>();
						spaDetails.put(SPA_ID, Temp.getString("Spa_Id"));
						spaDetails.put(SPA_NAME, Temp.getString("Spa_Name"));
						spaDetails.put(SPA_Address, Temp.getString("Addresss"));
						// ArrayList_AllSpaList.add(Temp.getString("Spa_Name"));
						// ArrayList_AllSpaIdList.add(Temp.getString("Spa_Id"));

						SpaDetails.add(spaDetails);
						

						/*
						 * Log.d("data", Temp.getString("Spa_Name"));
						 * Log.d("data", Temp.getString("Spa_Id"));
						 * Log.d("data", Temp.getString("Spa_Lat"));
						 * Log.d("data", Temp.getString("Spa_long"));
						 * Log.d("data", Temp.getString("Addresss"));
						 */
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
			dialog.cancel();

			if (file_url != null) 
			{
				
				adapter = new SpaListAdapter(getActivity(), SpaDetails);
				adapter.notifyDataSetChanged();
				listview_spa.setAdapter(adapter);
				
			}

	}
	}

	class GetAllSpa extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(rootView.getContext());
			pDialog.setMessage("Getting Your Data ... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.GetAllSpa,
						"POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < 10; i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						HashMap<String, String> spaDetails = new HashMap<String, String>();
						spaDetails.put(SPA_ID, Temp.getString("Spa_Id"));
						spaDetails.put(SPA_NAME, Temp.getString("Spa_Name"));
						spaDetails.put(SPA_Address, Temp.getString("Addresss"));
						// ArrayList_AllSpaList.add(Temp.getString("Spa_Name"));
						// ArrayList_AllSpaIdList.add(Temp.getString("Spa_Id"));

						SpaDetails.add(spaDetails);
					}
					return json.getString(TAG_MESSAGE) + " , Please Login !";
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
			pDialog.dismiss();
			if (file_url != null) {
				adapter = new SpaListAdapter(getActivity(), SpaDetails);
				listview_spa.setAdapter(adapter);

			}
		}
	}
}
