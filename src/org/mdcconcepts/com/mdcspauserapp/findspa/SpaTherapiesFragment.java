package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.makeappointment.SelectTherapistActivity;
import org.mdcconcepts.com.mdcspauserapp.makeappointment.SelectTherapyAdapter;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

@SuppressLint("ValidFragment") public class SpaTherapiesFragment extends Fragment implements OnScrollListener {

	ListView Therapy_List;
	SelectTherapyAdapter adapter;
	Dialog dialog, getDataDialog;
	Button Btn_Service_Ok;
	Button Btn_Service_Cancel;
	String Spa_Id;
	String Spa_Name;
	TextView Txt_Title;
	ProgressWheel pw_four;

	int hit_counter = 0;
	int VisiblePosition, distFromTop;

	GetTenTherapies getTenTherapies;

	private boolean isloading = false;
	private boolean isDataAvailable = true;

	ArrayList<HashMap<String, String>> TherapyDetails = new ArrayList<HashMap<String, String>>();

	JSONParser jsonParser = new JSONParser();

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	static final String THERAPY_ID = "therapy_id";
	static final String THERAPY = "therapy_name";
	static final String THERAPY_DETAILS = "therapy_details";

	ArrayList<String> Therapies_Pricing = new ArrayList<String>();
	ArrayList<String> Therapies_Timing = new ArrayList<String>();
	// ArrayList<String> AllDetails=new ArrayList<String>();
	HashMap<String, String> AllDetails = new HashMap<String, String>();
	TextView txt_dialog_title, txt_dialog_time, txt_dialog_price;
	Typeface font;
	ViewGroup footer;
	SeekBar seekBar_time_for_service;
	ProgressWheel progressbar_footer;
	int stepSize = 2;
	ListView listView_TFS;

	ArrayList<HashMap<String, String>> PricingDetails = new ArrayList<HashMap<String, String>>();

	public SpaTherapiesFragment(HashMap<String, String> spaDetails) {
		// TODO Auto-generated constructor stub
		this.Spa_Id = spaDetails.get("spa_id");
		this.Spa_Name=spaDetails.get("spa_name");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.therapies_list, null);

		AllDetails.put("Spa_Id", Spa_Id);
		AllDetails.put("Spa_Name", Spa_Name);
		Toast.makeText(getActivity(), "Therapies fragment"+ Spa_Id, Toast.LENGTH_SHORT).show();
		
		Therapy_List = (ListView) rootView
				.findViewById(R.id.listview_therapies);
		Therapy_List.setOnScrollListener(this);
		adapter = new SelectTherapyAdapter(getActivity(), TherapyDetails);
		Therapy_List.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");

		footer = (ViewGroup) inflater.inflate(R.layout.footer, Therapy_List,
				false);

		progressbar_footer = (ProgressWheel) footer
				.findViewById(R.id.progressbar_footer);
		progressbar_footer.spin();

		getTenTherapies = new GetTenTherapies();
		getTenTherapies.execute();

		getDataDialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
		getDataDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getDataDialog.setContentView(R.layout.custom_progress_dialog);
		getDataDialog.setCancelable(false);

		Txt_Title = (TextView) getDataDialog.findViewById(R.id.txt_alert_text);
		pw_four = (ProgressWheel) getDataDialog
				.findViewById(R.id.progressBarFour);

		Txt_Title.setTypeface(font);

		/**
		 * Dialog box for selecting time for service
		 */
		dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_time_for_service);
		dialog.setTitle("Select Time for service");
		// dialog.setCancelable(false);
		txt_dialog_title = (TextView) dialog
				.findViewById(R.id.txt_dialog_title_TFS);
		txt_dialog_title.setTypeface(font);

		listView_TFS = (ListView) dialog.findViewById(R.id.listView_TFS);

		// TODO Auto-generated method stub
		/*
		 * Btn_Service_Ok = (Button) dialog
		 * .findViewById(R.id.btn_time_for_service_ok);
		 * 
		 * Btn_Service_Cancel = (Button) dialog
		 * .findViewById(R.id.btn_time_for_service_cancel);
		 * 
		 * txt_dialog_title = (TextView) dialog
		 * .findViewById(R.id.txt_dialog_title); txt_dialog_time = (TextView)
		 * dialog.findViewById(R.id.txt_dialog_time); txt_dialog_price =
		 * (TextView) dialog .findViewById(R.id.txt_dialog_price);
		 * 
		 * seekBar_time_for_service = (SeekBar) dialog
		 * .findViewById(R.id.seekBar_time_for_service);
		 * 
		 * Btn_Service_Ok.setTypeface(font);
		 * Btn_Service_Cancel.setTypeface(font);
		 * txt_dialog_title.setTypeface(font);
		 * txt_dialog_time.setTypeface(font);
		 * txt_dialog_price.setTypeface(font);
		 */

		Therapy_List.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				@SuppressWarnings("unchecked")
				HashMap<String, String> therapyDetails = (HashMap<String, String>) view
						.getTag();
				Toast.makeText(getActivity(), therapyDetails.get("therapy_id"),
						Toast.LENGTH_SHORT).show();
				AllDetails.put("Therapy_Id", therapyDetails.get("therapy_id"));
				AllDetails.put("Therapy_Name", therapyDetails.get("therapy_name"));

				if (!PricingDetails.isEmpty()) {
					PricingDetails.clear();
					listView_TFS.setAdapter(null);
				}
				new GetTimeForServiceTask().execute(therapyDetails
						.get("therapy_id"));

			}
		});
		listView_TFS.setOnItemClickListener(new OnItemClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, String> therapyDetails = (HashMap<String, String>) view
						.getTag();
				AllDetails.put("Therapy_Time", therapyDetails.get("Time"));
				AllDetails.put("Therapy_Price", therapyDetails.get("Price"));

				Intent i = new Intent(getActivity(),
						SelectTherapistActivity.class);
				i.putExtra("AllDetails", AllDetails);
				startActivity(i);
				dialog.dismiss();
			}
		});

		/*
		 * seekBar_time_for_service .setOnSeekBarChangeListener(new
		 * OnSeekBarChangeListener() { public void onProgressChanged(SeekBar
		 * seekBar, int progress, boolean fromUser) { // TODO Auto-generated
		 * method stub // txt_dialog_time.setText("SeekBar value is " + //
		 * progress);
		 * 
		 * int size=Therapies_Timing.size();
		 * txt_dialog_time.setText(Therapies_Timing.get(0)+" "+size); /*progress
		 * = ((int) Math.round(progress / stepSize)) stepSize;
		 * seekBar_time_for_service.setProgress(progress);
		 * txt_dialog_time.setText(progress + ""); }
		 * 
		 * public void onStartTrackingTouch(SeekBar seekBar) { // TODO
		 * Auto-generated method stub }
		 * 
		 * public void onStopTrackingTouch(SeekBar seekBar) { // TODO
		 * Auto-generated method stub } });
		 * 
		 * /*Btn_Service_Ok.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Intent intentGetMessage = new Intent(getActivity(),
		 * SelectTherapistActivity.class); startActivity(intentGetMessage);
		 * 
		 * dialog.dismiss(); } });
		 * 
		 * Btn_Service_Cancel.setOnClickListener(new View.OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub dialog.dismiss(); } });
		 */
		Therapy_List.addFooterView(footer);
		return rootView;
	}

	/**
	 * Get Time slots and corresponding price details
	 * 
	 * @author Pallavi Udawant
	 * 
	 */
	class GetTimeForServiceTask extends AsyncTask<String, String, String> {

		int success;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String Therapy_id = params[0];

			try {
				List<NameValuePair> Newparams = new ArrayList<NameValuePair>();

				Newparams.add(new BasicNameValuePair("Therapy_Id", Therapy_id));
				Log.d("requesting therapies pricing details!", "starting "
						+ Therapy_id);

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetTherapiesPricingDetails, "POST", Newparams);
				if (json != null) {
					// json success element

					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						JSONArray PostJson = json.getJSONArray("posts");
						Log.d(" Therapies details ", PostJson.toString());
						for (int i = 0; i < PostJson.length(); i++) {
							HashMap<String, String> therapyDetails = new HashMap<String, String>();

							JSONObject Temp = PostJson.getJSONObject(i);
							therapyDetails.put("Price",
									Temp.getString("Pricing"));
							therapyDetails.put("Time", Temp.getString("Time"));
							// PricingDetails.put("Price",
							// Temp.getString("Pricing"));
							// PricingDetails.put("Time",Temp.getString("Time"));
							PricingDetails.add(therapyDetails);
						}

					}
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			TFSListviewAdapter adapter = new TFSListviewAdapter(getActivity(),
					PricingDetails);
			listView_TFS.setAdapter(adapter);

			dialog.show();
		}
	}

	class GetTenTherapies extends AsyncTask<String, String, String> {

		int success;
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected String doInBackground(String... params) {

			// TODO Auto-generated method stub
			SystemClock.sleep(1000);
			isloading = true;
			try {
				// Building Parameters
				List<NameValuePair> Newparams = new ArrayList<NameValuePair>();

				Newparams.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));
				Newparams.add(new BasicNameValuePair("Spa_Id", Spa_Id));
				Log.d("requesting 10 therapies!", "starting " + hit_counter);

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.get_ten_therapies, "POST", Newparams);

				// full json response
				// Log.d("Login attempt", json.toString());

				// json success element
				if (json != null) {

					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						JSONArray PostJson = json.getJSONArray("posts");
						Log.d("Ten Therapies ", PostJson.toString());
						for (int i = 0; i < PostJson.length(); i++) {

							JSONObject Temp = PostJson.getJSONObject(i);

							HashMap<String, String> spaDetails = new HashMap<String, String>();

							spaDetails.put(THERAPY_ID,
									Temp.getString("Therapies_Id"));
							spaDetails.put(THERAPY,
									Temp.getString("Therapy_Name"));
							spaDetails.put(THERAPY_DETAILS,
									Temp.getString("Therapy_Description"));

							TherapyDetails.add(spaDetails);

							isDataAvailable = true;
						}

						return json.getString(TAG_MESSAGE);
					} else if (success == 0) {
						Log.d("Login Failure!", json.getString(TAG_MESSAGE));
						isDataAvailable = false;
						Therapy_List.setOnScrollListener(null);
						return json.getString(TAG_MESSAGE);

					}
				} else {
					return "timeout";
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
			// if(getDataDialog.isShowing())
			// getDataDialog.dismiss();
			isloading = false;

			if (file_url != null) {
				if (file_url.contains("Spa Found !")) {
					Log.v("onPostLog If", file_url);

					Therapy_List.setAdapter(adapter);
					Therapy_List.setSelectionFromTop(VisiblePosition,
							distFromTop);
					hit_counter++;

				} else if (file_url.equalsIgnoreCase("false")) {
					Log.v("onPostLog Else", file_url);

					if (Therapy_List.getFooterViewsCount() > 0) {
						// Toast.makeText(Select_Therapy_Activity.this,
						// String.valueOf(Therapy_List.getFooterViewsCount()),
						// Toast.LENGTH_SHORT).show();
						Therapy_List.removeFooterView(footer);
						Therapy_List.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}

					Therapy_List.setOnScrollListener(null);

					// progressbar_footer.stopSpinning();
					isDataAvailable = false;

				}
			} else {
				Toast.makeText(getActivity(),
						"Connection TimeOut..!!! Please try again.. ",
						Toast.LENGTH_LONG).show();
			}

		}
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

		if (isDataAvailable) {
			if ((loadedItems == totalItemCount) && !isloading) {

				if (getTenTherapies != null
						&& (getTenTherapies.getStatus() == AsyncTask.Status.FINISHED)) {

					getTenTherapies = new GetTenTherapies();
					getTenTherapies.execute();

				}
			}

		} else {
			Therapy_List.setOnScrollListener(null);
			Therapy_List.removeFooterView(footer);
			progressbar_footer.stopSpinning();
			Therapy_List.setAdapter(adapter);
			adapter.notifyDataSetChanged();

		}
	}
}
