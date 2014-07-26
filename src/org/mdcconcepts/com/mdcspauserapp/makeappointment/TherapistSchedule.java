package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.ImageLoader;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

@SuppressLint("ValidFragment")
public class TherapistSchedule extends Fragment {

	int mCurrentPage;

	JSONParser jsonParser = new JSONParser();

	private String Therapist_Id;
	private String Therapist_name;
	public String TherapistProfileUrl="";
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	ListView listViewController_Schedule;

	Button Btn_Select_Therapist;
	TextView textViewController_SpaName;
	TextView textViewController_TherapistName;
	ImageView ImageviewController_ProfileImage;
	RatingBar ratingBarController_Therapist;
	TextView textViewController_No_Of_Rating;
	TextView txt_service, txt_service_time, txt_date, txt_time;
	TextView txt_feedback_therapist, txt_feedback_therapist_name,
			txt_feedback_message, txt_feedkback_title;

	EditText edt_feedback_message;
	EditText edt_service;
	EditText edt_service_time;
	EditText edt_date;
	EditText edt_time;

	private Calendar cal;
	private int day;
	private int month;
	private int year;

	static String Schedule_Id = "Schedule_Id";
	static String Timeline_Therapist_Id = "Therapist_Id";
	static String Appointment_Start_Time = "Start_Time";
	static String Appointment_End_Time = "End_time";

	Button btn_show_timeline, btn_feedback, btn_send_feedback;
	Spinner Date_Spinner;
	HashMap<String, String> AllDetails = new HashMap<String, String>();
	Dialog feedback_dialog;
	String feedback_message;
	static final int TIME_DIALOG_ID = 999;
	Typeface font;
	ProgressWheel progressBar_therapist;
	ProgressWheel progressBar_timeline;
	ArrayList<HashMap<String, String>> TherapyTimelineDetails = new ArrayList<HashMap<String, String>>();

	public TherapistSchedule() {
		// TODO Auto-generated constructor stub

	}

	public TherapistSchedule(String Therapist_Id, String Therapist_Name,
			HashMap<String, String> AllDetails) {
		// TODO Auto-generated constructor stub
		this.Therapist_Id = Therapist_Id;
		this.Therapist_name = Therapist_Name;
		this.AllDetails = AllDetails;
		Log.d("Therapist_Id", Therapist_Id);

	}

	public TherapistSchedule(String string, String string2) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater
				.inflate(R.layout.therapist_schedule, container, false);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");

		textViewController_SpaName = (TextView) v
				.findViewById(R.id.textViewController_SpaName);

		textViewController_TherapistName = (TextView) v
				.findViewById(R.id.textViewController_TherapistName);

		ImageviewController_ProfileImage = (ImageView) v
				.findViewById(R.id.Imageview_ProfileImage);

		ratingBarController_Therapist = (RatingBar) v
				.findViewById(R.id.ratingBarController_Therapist);
		ratingBarController_Therapist.setEnabled(false);

		textViewController_No_Of_Rating = (TextView) v
				.findViewById(R.id.textViewController_No_Of_Rating);

		txt_service = (TextView) v.findViewById(R.id.txt_service);

		txt_service_time = (TextView) v.findViewById(R.id.txt_service_time);
		txt_date = (TextView) v.findViewById(R.id.txt_date);
		txt_time = (TextView) v.findViewById(R.id.txt_time);

		edt_service = (EditText) v.findViewById(R.id.edt_service);
		edt_service_time = (EditText) v.findViewById(R.id.edt_service_time);
		edt_date = (EditText) v.findViewById(R.id.edt_date);
		edt_time = (EditText) v.findViewById(R.id.edt_time);

		progressBar_therapist = (ProgressWheel) v
				.findViewById(R.id.progressBar_therapist);
		progressBar_timeline = (ProgressWheel) v
				.findViewById(R.id.progressBar_timeline);
		progressBar_therapist.setVisibility(View.GONE);
		Button btn_show_timeline = (Button) v
				.findViewById(R.id.btn_show_timeline);

		Btn_Select_Therapist = (Button) v
				.findViewById(R.id.Btn_Select_Therapist_final);

		btn_feedback = (Button) v.findViewById(R.id.btn_feedback);

		/**
		 * Feedback Dialog
		 */
		feedback_dialog = new Dialog(getActivity());
		feedback_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		feedback_dialog.setContentView(R.layout.feedback_dialog);

		txt_feedback_message = (TextView) feedback_dialog
				.findViewById(R.id.txt_feedback_message);
		txt_feedback_therapist = (TextView) feedback_dialog
				.findViewById(R.id.txt_feedback_therapist);
		txt_feedback_therapist_name = (TextView) feedback_dialog
				.findViewById(R.id.txt_feedback_therapist_name);
		txt_feedkback_title = (TextView) feedback_dialog
				.findViewById(R.id.txt_feedkback_title);

		edt_feedback_message = (EditText) feedback_dialog
				.findViewById(R.id.edt_feedback_message);

		btn_send_feedback = (Button) feedback_dialog
				.findViewById(R.id.btn_send_feedback);

		txt_feedback_therapist_name.setText(Therapist_name);
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
		edt_service.setTypeface(font);
		edt_service_time.setTypeface(font);
		edt_time.setTypeface(font);

		Btn_Select_Therapist.setTypeface(font);
		btn_show_timeline.setTypeface(font);
		btn_feedback.setTypeface(font);
		btn_send_feedback.setTypeface(font);
		edt_date.setTypeface(font);

		/**
		 * SetText
		 */
		edt_service.setText(AllDetails.get("Therapy_Name"));
		edt_service_time.setText(AllDetails.get("Therapy_Time"));
		textViewController_TherapistName.setText(Therapist_name);
		textViewController_SpaName.setText(AllDetails.get("Spa_Name"));

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		edt_date.setInputType(InputType.TYPE_NULL);

		edt_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDatepickerDialog();
			}
		});

		edt_time.setInputType(InputType.TYPE_NULL);

		edt_time.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Calendar c = Calendar.getInstance();
				final int mHour = c.get(Calendar.HOUR_OF_DAY);
				final int mMinute = c.get(Calendar.MINUTE);

				// c.set(Calendar.AM_PM, AM_PM);
				// Launch Time Picker Dialog
				TimePickerDialog tpd = new TimePickerDialog(getActivity(),
						new TimePickerDialog.OnTimeSetListener() {

							@Override
							public void onTimeSet(TimePicker view,
									int hourOfDay, int minute) {
								// Display Selected time in textbox
								// edt_time.setText(hourOfDay + ":" + minute);
								Util.Appointment_Time = hourOfDay + ":"
										+ minute;
								String am_pm = null;
								Calendar datetime = Calendar.getInstance();
								datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
								datetime.set(Calendar.MINUTE, minute);

								if (datetime.get(Calendar.AM_PM) == Calendar.AM)
									am_pm = "AM";
								else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
									am_pm = "PM";

								String strHrsToShow = (datetime
										.get(Calendar.HOUR) == 0) ? "12"
										: datetime.get(Calendar.HOUR) + "";
								edt_time.setText(strHrsToShow + ":"
										+ datetime.get(Calendar.MINUTE) + " "
										+ am_pm);

							}

						}, mHour, mMinute, false);

				tpd.show();
			}

		});
		edt_date.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}

		});
		Btn_Select_Therapist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (!edt_date.getText().toString().isEmpty()) {

					edt_date.setError(null);
					if (edt_time.getText().toString().isEmpty())
						edt_time.setError("Please enter time..!!!");
					else if (edt_time.getText().toString() != null) {
						edt_time.setError(null);
						new IsTherapistAvailable().execute();
						progressBar_therapist.setVisibility(View.VISIBLE);
						progressBar_therapist.spin();

					}
				} else
					edt_date.setError("Please enter date..!!!");

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
				progressBar_timeline.setVisibility(View.VISIBLE);
				progressBar_timeline.spin();
				new GetTherapistTimeline().execute();
				// timeline_dialog.show();
			}
		});
		btn_send_feedback.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (edt_feedback_message.getText().toString().isEmpty())
					edt_feedback_message.setError("Please Enter Feedback !");
				else {
					feedback_message = edt_feedback_message.getText()
							.toString().trim();
					new SendFeedback().execute();
				}

			}
		});

		new GetTherapistSchedule().execute();

		return v;
	}

	/**
	 * Date Picker
	 */
	public void showDatepickerDialog() {
		DatePickerDialog mDatePicker = new DatePickerDialog(getActivity(),
				new OnDateSetListener() {
					public void onDateSet(DatePicker datepicker,
							int selectedyear, int selectedmonth, int selectedday) {
						// TODO Auto-generated method stub
						edt_date.setText(selectedyear + "-"
								+ (selectedmonth + 1) + "-" + selectedday);

					}
				}, year, month, day);
		mDatePicker.setTitle("Select date");
		mDatePicker.show();
	}

	class SendFeedback extends AsyncTask<String, String, String> {

		Dialog pdialog;
		int success;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			pdialog.show();

			TextView Txt_Title = (TextView) pdialog
					.findViewById(R.id.txt_alert_text);

			Txt_Title.setText("Sending Feedback...");
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

				params1.add(new BasicNameValuePair("Therapist_Id", Therapist_Id));
				params1.add(new BasicNameValuePair("Feedback", feedback_message));
				params1.add(new BasicNameValuePair("Uid", String
						.valueOf(Util.Uid)));

				Log.d("request!", "starting");
				JSONObject json = jsonParser.makeHttpRequest(
						Util.send_feedback, "POST", params1);
				// Posting user data to script
				if (json != null) {

					// full json response
					Log.d("Login attempt", json.toString());

					// json success element
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						return json.getString(TAG_MESSAGE);
					} else {
						Log.d("Login Failure!", json.getString(TAG_MESSAGE));
						return json.getString(TAG_MESSAGE);

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String file_url) {
			// TODO Auto-generated method stub
			super.onPostExecute(file_url);
			pdialog.dismiss();
			if (file_url != null) {
				if (success == 1) {
					feedback_dialog.dismiss();
					Toast.makeText(getActivity(),
							"Thank you for your feedback...!!!",
							Toast.LENGTH_LONG).show();
				}
			}
		}
	}

	class GetTherapistSchedule extends AsyncTask<String, String, String> {

		// Progress Dialog
		ProgressDialog pDialog;
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
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Getting Therapies Data ... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
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
				Log.d("GetTherapistSchedule!", "starting" + Therapist_Id);

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetTherapistSchedule, "POST", params);

				// json success element
				if (json != null) {
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						Profile_url = json.getString("Profile_Image_Url");
						rating = Float.parseFloat(json.getString("Rate"));
						TherapistProfileUrl=Profile_url;
						Log.d("therapist details ", Profile_url);

						return json.getString(TAG_MESSAGE);
					} else {
						Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
			if (file_url != null) {

				ratingBarController_Therapist.setRating(rating);

				if (!Profile_url.equalsIgnoreCase("null")) {

					ImageLoader imgLoader = new ImageLoader(getActivity());

					// whenever you want to load an image from url
					// call DisplayImage function
					// url - image url to load
					// loader - loader image, will be displayed before
					// getting
					// image
					// image - ImageView
					imgLoader.DisplayImage(Profile_url.replace("\\", ""),
							R.id.Imageview_ProfileImage,
							ImageviewController_ProfileImage);
				}

			}
		}
	}

	class GetTherapistTimeline extends AsyncTask<String, String, String> {

		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (TherapyTimelineDetails.size() > 0)
				TherapyTimelineDetails.clear();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// Building Parameters
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				params1.add(new BasicNameValuePair("Therapist_Id", ""
						+ Therapist_Id));

				Log.d("GetTherapistTimeline!", Therapist_Id);

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetTherapistTimeline, "POST", params1);

				if (json != null) {
					// full json response
					Log.d("Login attempt", json.toString());

					// json success element
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {
						JSONArray PostJson = json.getJSONArray("posts");
						Log.d("GetTherapistTimeline ", PostJson.toString());
						for (int i = 0; i < PostJson.length(); i++) {

							JSONObject Temp = PostJson.getJSONObject(i);
							HashMap<String, String> TherapistScheduleDetails = new HashMap<String, String>();

							TherapistScheduleDetails.put(Schedule_Id,
									Temp.getString("Schedule_Id"));
							TherapistScheduleDetails.put(Timeline_Therapist_Id,
									Temp.getString("Therapist_Id"));
							TherapistScheduleDetails.put("Start_Time",
									Temp.getString(Appointment_Start_Time));
							TherapistScheduleDetails.put(Appointment_End_Time,
									Temp.getString("End_Time"));

							TherapyTimelineDetails
									.add(TherapistScheduleDetails);

						}

						return json.getString(TAG_MESSAGE);

					} else {
						Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				if (success == 1) {
					progressBar_timeline.setVisibility(View.INVISIBLE);
					progressBar_timeline.stopSpinning();
					Intent i = new Intent(getActivity(),
							TimelineDynamicActivity.class);
					i.putExtra("TimelineData", TherapyTimelineDetails);
					i.putExtra("TherapistName", Therapist_name);
					i.putExtra("SpaName", AllDetails.get("Spa_Name"));
					i.putExtra("ProfileUrl", TherapistProfileUrl);
					startActivity(i);
					 getActivity().overridePendingTransition(R.anim.fade_in
		        				, R.anim.fade_out);
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
						+ edt_date.getText().toString().trim() + " "
						+ Util.Appointment_Time));

				Log.d("request!", Therapist_Id);
				Log.d("Appointment time!", edt_date.getText().toString().trim()
						+ " " + Util.Appointment_Time);

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
			progressBar_therapist.setVisibility(View.GONE);
			progressBar_therapist.stopSpinning();
			if (file_url != null) {
				if (success == 1) {

					try {
						Btn_Select_Therapist.setText("Select Therapist");

						Btn_Select_Therapist.setEnabled(true);
						Intent i = new Intent(getActivity(),
								FinalMakeAppointmentActivity.class);
						if (edt_date.getText().toString().isEmpty())
							edt_date.setError("Please select appointment date");
						else if (edt_time.getText().toString().isEmpty())
							edt_time.setError("Please select appointment date");
						else {
							AllDetails.put("Selected_Time", edt_time.getText()
									.toString().trim());
							AllDetails.put("Selected_Date", edt_date.getText()
									.toString().trim());
							AllDetails
									.put("Selected_Therapist", Therapist_name);
							AllDetails.put("Therapist_Id", Therapist_Id);
							i.putExtra("AllDetails", AllDetails);
							startActivity(i);
							getActivity(). overridePendingTransition(R.anim.fade_in
				        				, R.anim.fade_out);
						}

					} catch (Exception e) {
						// Toast.makeText(getActivity(), e.toString(),
						// Toast.LENGTH_LONG).show();
					}

				} else {
					Btn_Select_Therapist.setText("Therapist unavailable");
				}

			}
		}

	}
}