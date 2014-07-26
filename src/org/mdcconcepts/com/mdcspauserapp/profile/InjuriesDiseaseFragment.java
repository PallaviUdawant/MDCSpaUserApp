package org.mdcconcepts.com.mdcspauserapp.profile;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.todddavies.components.progressbar.ProgressWheel;

public class InjuriesDiseaseFragment extends FragmentActivity {

	private ListView listview_injuries;
	private ListView listview_disease;

	private TextView txt_injuries;
	private TextView txt_disease;
	JSONParser jsonParser = new JSONParser();

	private ArrayList<String> Injuries = new ArrayList<String>();
	private ArrayList<String> Disease = new ArrayList<String>();

	private String[] InjuriesArray;
	private String[] DiseaseArray;

	SharedPreferences pref;
	
	Typeface font;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_injuries_diseases);

		listview_injuries = (ListView) findViewById(R.id.listView_injuries);
		listview_disease = (ListView)
				findViewById(R.id.listView_disease);

		txt_injuries = (TextView) findViewById(R.id.TextView_Injuries);
		txt_disease = (TextView) findViewById(R.id.TextView_Disease);

		txt_injuries.setTextColor(Color.parseColor("#ffffff"));
		txt_disease.setTextColor(Color.parseColor("#ffffff"));

		font = Typeface.createFromAsset(getAssets(), Util.fontPath);

		pref = getApplicationContext()
				.getSharedPreferences(Util.APP_PREFERENCES, 0);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		new GetPainingAreas().execute();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// NavUtils.navigateUpFromSameTask(this);

			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class GetPainingAreas extends AsyncTask<String, String, String> {

		Dialog pdialog;
		int success;
		TextView Txt_Title;

		private static final String TAG_SUCCESS = "success";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new Dialog(InjuriesDiseaseFragment.this,
					R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			pdialog.show();

			Txt_Title = (TextView) pdialog.findViewById(R.id.txt_alert_text);

			Txt_Title.setText("Fetching Data...");
			Txt_Title.setTypeface(font);
			/**
			 * custom circular progress bar
			 */
			ProgressWheel pw_four = (ProgressWheel) pdialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				params1.add(new BasicNameValuePair("uid", String.valueOf(Util.Uid)));

				Log.d("request Injuries fragment!", "starting" + String.valueOf(Util.Uid));

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.getPainData,
						"POST", params1);

				// full json response
				Log.d("Pain Ids", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);
						int Pain_Id = Integer.parseInt(Temp
								.getString("Pain_Id"));
						switch (Pain_Id) {
						case 1:
							// Head
							Injuries.add("Head");
							break;
						case 2:
							// Neck
							Injuries.add("Neck");
							break;
						case 3:
							// Shoulder
							Injuries.add("Shoulder");
							break;
						case 4:
							// Arm
							Injuries.add("Arm");
							break;
						case 5:
							// Waist
							Injuries.add("Waist");
							break;
						case 6:
							// Back
							Injuries.add("Back");
							break;
						case 7:
							// Thigh
							Injuries.add("Thigh");
							break;
						case 8:
							// Calf
							Injuries.add("Calf");
							break;
						case 9:
							// Sole
							Injuries.add("Sole");
							break;

						}
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pdialog.dismiss();

			new GetDisease().execute();
		}
	}

	class GetDisease extends AsyncTask<String, String, String> {

		Dialog pdialog;
		int success;
		TextView Txt_Title;

		private static final String TAG_SUCCESS = "success";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new Dialog(InjuriesDiseaseFragment.this,
					R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			pdialog.show();

			Txt_Title = (TextView) pdialog.findViewById(R.id.txt_alert_text);

			Txt_Title.setText("Fetching Data...");
			Txt_Title.setTypeface(font);
			/**
			 * custom circular progress bar
			 */
			ProgressWheel pw_four = (ProgressWheel) pdialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {

				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				params1.add(new BasicNameValuePair("uid",  String.valueOf(Util.Uid)));

				Log.d("request disease!", "starting" +String.valueOf(Util.Uid));

				if (!Util.getDiseaseData.isEmpty()) {// Posting user data to
														// script
					JSONObject json = jsonParser.makeHttpRequest(
							Util.getDiseaseData, "POST", params1);

					// full json response
					Log.d("Pain Ids", json.toString());

					// json success element
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						JSONArray PostJson = json.getJSONArray("posts");
						Log.d("Post Date ", PostJson.toString());
						for (int i = 0; i < PostJson.length(); i++) {

							JSONObject Temp = PostJson.getJSONObject(i);
							int Pain_Id = Integer.parseInt(Temp
									.getString("Disease_Id"));
							switch (Pain_Id) {
							case 1:
								Disease.add("Heart Disease");
								// Heart
								break;
							case 2:
								// Hepatitis
								Disease.add("Hepatitis");
								break;
							case 3:
								// Diabeties
								Disease.add("Diabeties");
								break;
							case 4:
								// Lung Disease
								Disease.add("Lung Disease");
								break;
							case 5:
								// Hypotension
								Disease.add("Hypotension");
								break;
							case 6:
								// Skin Disease
								Disease.add("Skin Disease");
								break;
							case 7:
								// Thigh
								Disease.add("High Blood Pressure");
								break;
							case 8:
								// Calf
								Disease.add("Arthrities");
								break;
							case 9:
								// Sole
								Disease.add("Pregnant");
								break;
							case 10:
								// Sole
								Disease.add("Other");
								break;

							}
						}
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pdialog.dismiss();

			if (Injuries.isEmpty()) {
				txt_injuries.setText("No Injuries Added");
				listview_injuries.setVisibility(View.GONE);
			} else {
				InjuriesArray = new String[Injuries.size()];
				InjuriesArray = Injuries.toArray(InjuriesArray);

				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						InjuriesDiseaseFragment.this, R.layout.listview_text,
						InjuriesArray);
				listview_injuries.setAdapter(adapter);
			}
			if (Disease.isEmpty()) {
				txt_injuries.setText("No Disease Added");
				listview_disease.setVisibility(View.GONE);

			} else {
				DiseaseArray = new String[Disease.size()];
				DiseaseArray = Disease.toArray(DiseaseArray);

				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						InjuriesDiseaseFragment.this, R.layout.listview_text,
						DiseaseArray);
				listview_disease.setAdapter(adapter);
			}

		}

	}

}
