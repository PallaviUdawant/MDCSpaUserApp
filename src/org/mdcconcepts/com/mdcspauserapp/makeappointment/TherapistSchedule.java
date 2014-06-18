package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.AppointmentScheduleCustomList;
import org.mdcconcepts.com.mdcspauserapp.customitems.ImageLoader;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class TherapistSchedule extends Fragment {

	int mCurrentPage;

	JSONParser jsonParser = new JSONParser();

	private String Therapist_Id;
	private String Therapist_name;
	AppointmentScheduleCustomList adapter;

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	private ArrayList<String> ArrayList_AllServiceList = new ArrayList<String>();
	private ArrayList<String> ArrayList_Dates = new ArrayList<String>();
	private ArrayList<String> ArrayList_AllTimeList = new ArrayList<String>();
	ListView listViewController_Schedule;
	Button buttonController_ChooseTherapist;
	Button buttonController_SendFeedback;
	Button Btn_Select_Therapist;
	TextView textViewController_SpaName;
	TextView textViewController_TherapistName;
	ImageView ImageviewController_ProfileImage;
	RatingBar ratingBarController_Therapist;
	TextView textViewController_No_Of_Rating;
	TextView txt_service, txt_service_time, txt_date, txt_time;
	TextView txt_feedback_therapist,txt_feedback_therapist_name,txt_feedback_message,txt_feedkback_title;
	EditText edt_feedback_message;
	Button btn_show_timeline,btn_feedback,btn_send_feedback;
	Spinner Date_Spinner;

	Dialog feedback_dialog;
	public TherapistSchedule() {
		// TODO Auto-generated constructor stub

	}

	public TherapistSchedule(String Therapist_Id, String Therapist_Name) {
		// TODO Auto-generated constructor stub
		this.Therapist_Id = Therapist_Id;
		this.Therapist_name = Therapist_Name;
		Log.d("Therapist_Id", Therapist_Id);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// /** Getting the arguments to the Bundle object */
		// Bundle data = getArguments();
		//
		// /** Getting integer data of the key current_page from the bundle */
		// mCurrentPage = data.getInt("Therapist_Id", 0);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater
				.inflate(R.layout.therapist_schedule, container, false);

		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");

		// listViewController_Schedule = (ListView) v
		// .findViewById(R.id.listViewController_Schedule);
		//
		// buttonController_ChooseTherapist = (Button) v
		// .findViewById(R.id.buttonController_ChooseTherapist);
		// buttonController_SendFeedback = (Button) v
		// .findViewById(R.id.buttonController_SendFeedback);
		textViewController_SpaName = (TextView) v
				.findViewById(R.id.textViewController_SpaName);

		textViewController_TherapistName = (TextView) v
				.findViewById(R.id.textViewController_TherapistName);

		ImageviewController_ProfileImage = (ImageView) v
				.findViewById(R.id.ImageviewController_ProfileImage);

		ratingBarController_Therapist = (RatingBar) v
				.findViewById(R.id.ratingBarController_Therapist);
		ratingBarController_Therapist.setEnabled(false);

		textViewController_No_Of_Rating = (TextView) v
				.findViewById(R.id.textViewController_No_Of_Rating);

		txt_service = (TextView) v.findViewById(R.id.txt_service);
		txt_service_time = (TextView) v.findViewById(R.id.txt_service_time);
		txt_date = (TextView) v.findViewById(R.id.txt_date);
		txt_time = (TextView) v.findViewById(R.id.txt_time);

		textViewController_TherapistName.setText(Therapist_name);

		textViewController_SpaName.setText(Util.Spa_Name);

		Button btn_show_timeline = (Button) v
				.findViewById(R.id.btn_show_timeline);

		Btn_Select_Therapist = (Button) v
				.findViewById(R.id.Btn_Select_Therapist_final);

		btn_feedback=(Button)v.findViewById(R.id.btn_feedback);
		
		/**
		 * Feedback Dialog
		 */
		feedback_dialog= new Dialog(getActivity());
		feedback_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		feedback_dialog.setContentView(R.layout.feedback_dialog);
		
		txt_feedback_message=(TextView)feedback_dialog.findViewById(R.id.txt_feedback_message);
		txt_feedback_therapist=(TextView)feedback_dialog.findViewById(R.id.txt_feedback_therapist);
		txt_feedback_therapist_name=(TextView)feedback_dialog.findViewById(R.id.txt_feedback_therapist_name);
		txt_feedkback_title=(TextView)feedback_dialog.findViewById(R.id.txt_feedkback_title);
		
		edt_feedback_message=(EditText)feedback_dialog.findViewById(R.id.edt_feedback_message);
		
		btn_send_feedback=(Button)feedback_dialog.findViewById(R.id.btn_send_feedback);
		
		/**
		 * Set Fonts
		 */
		textViewController_SpaName.setTypeface(font);
		textViewController_TherapistName.setTypeface(font);
		textViewController_No_Of_Rating.setTypeface(font);
		txt_service.setTypeface(font);
		txt_service_time.setTypeface(font);
		txt_date.setTypeface(font);
		txt_time.setTypeface(font);
		txt_feedback_message.setTypeface(font);
		txt_feedback_therapist.setTypeface(font);
		txt_feedback_therapist_name.setTypeface(font);
		txt_feedkback_title.setTypeface(font);
		
		edt_feedback_message.setTypeface(font);

		Btn_Select_Therapist.setTypeface(font);
		btn_show_timeline.setTypeface(font);
		btn_feedback.setTypeface(font);
		btn_send_feedback.setTypeface(font);
		
		/**
		 * TimeLine Dialog
		 */

		final Dialog timeline_dialog = new Dialog(getActivity());
		timeline_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		timeline_dialog.setContentView(R.layout.timeline);

		Date_Spinner = (Spinner) timeline_dialog.findViewById(R.id.date_spinner);

		ArrayList_Dates.add("Today: 17/06/2014");
		ArrayList_Dates.add("Tommorrow:18/06/2014");
		ArrayList_Dates.add("19/06/2014");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), R.layout.spinner_item, ArrayList_Dates);

		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Date_Spinner.setAdapter(dataAdapter);
		
		
		Btn_Select_Therapist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent i=new Intent(getActivity(),FinalMakeAppointmentActivity.class);
				startActivity(i);
			}
		});
		
		btn_feedback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				feedback_dialog.show();
			}
		});

		

		btn_show_timeline.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				timeline_dialog.show();
			}
		});
		
		
		// buttonController_ChooseTherapist.setEnabled(false);
		//
		// buttonController_ChooseTherapist
		// .setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent data = new Intent();
		// data.putExtra("Therapist_Id", Therapist_Id);
		// data.putExtra("Therapist_Name", Therapist_name);
		// getActivity().setResult(2, data);
		// getActivity().finish();
		// }
		// });
		// buttonController_SendFeedback.setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intentGetMessage = new Intent(getActivity(),
		// SendFeedbackActivity.class);
		// intentGetMessage.putExtra("Therapist_Id", Therapist_Id);
		// intentGetMessage.putExtra("Therapist_name", Therapist_name);
		// startActivity(intentGetMessage);
		// }
		// });
		// Clear collection..
		ArrayList_AllServiceList.clear();

		ArrayList_AllTimeList.clear();

		adapter = new AppointmentScheduleCustomList(getActivity(),
				ArrayList_AllServiceList, ArrayList_AllTimeList);

		adapter.clear();

		// new GetTherapistSchedule().execute();
		//
		// new IsTherapistAvailable().execute();

		return v;
	}

	class GetTherapistSchedule extends AsyncTask<String, String, String> {

		// Progress Dialog
		int success;
		String Profile_url = "";
		float rating = 0;
		String No_Of_Rated = "";
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(getActivity());
			// pDialog.setMessage("Getting Therapies Data ... ");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("Therapist_Id", ""
						+ Therapist_Id));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetTherapistSchedule, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					Profile_url = json.getString("Profile_Image_Url");
					No_Of_Rated = json.getString("No_Of_Rated");
					rating = Float.parseFloat(json.getString("Rate"));
					JSONArray PostJson = json.getJSONArray("posts");

					Log.d("Post Date ", PostJson.toString());

					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);

						ArrayList_AllServiceList.add(Temp.getString("Name"));
						ArrayList_AllTimeList.add(Temp.getString("End_Time"));

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
			// pDialog.dismiss();
			if (file_url != null) {

				try {
					adapter = new AppointmentScheduleCustomList(getActivity(),
							ArrayList_AllServiceList, ArrayList_AllTimeList);
					listViewController_Schedule.setAdapter(adapter);

					ratingBarController_Therapist.setRating(rating);
					textViewController_No_Of_Rating.setText(No_Of_Rated);
					if (!Profile_url.equalsIgnoreCase("null")) {
						// Image url

						// ImageLoader class instance
						ImageLoader imgLoader = new ImageLoader(getActivity());

						// whenever you want to load an image from url
						// call DisplayImage function
						// url - image url to load
						// loader - loader image, will be displayed before
						// getting
						// image
						// image - ImageView
						imgLoader.DisplayImage(Profile_url.replace("\\", ""),
								R.id.ImageviewController_ProfileImage,
								ImageviewController_ProfileImage);
					}

				} catch (Exception e) {
					Toast.makeText(getActivity(), e.toString(),
							Toast.LENGTH_LONG).show();
				}

			}
		}
	}

	class IsTherapistAvailable extends AsyncTask<String, String, String> {

		// Progress Dialog
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// pDialog = new ProgressDialog(getActivity());
			// pDialog.setMessage("Getting Therapies Data ... ");
			// pDialog.setIndeterminate(false);
			// pDialog.setCancelable(false);
			// pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("Therapist_Id", ""
						+ Therapist_Id));
				params.add(new BasicNameValuePair("Appointment_Time", ""
						+ Util.Appointment_Time));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.IsTherapistAvailable, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

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
			// pDialog.dismiss();
			if (file_url != null) {
				if (success == 1) {

					try {
						buttonController_ChooseTherapist.setEnabled(true);
					} catch (Exception e) {
						Toast.makeText(getActivity(), e.toString(),
								Toast.LENGTH_LONG).show();
					}

				}

			}
		}

	}

	// @Override
	// private void onBackPressed() {
	// // TODO Auto-generated method stub
	// Intent data = new Intent();
	// getActivity().setResult(0, data);
	// getActivity().finish();
	// }

}