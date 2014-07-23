package org.mdcconcepts.com.mdcspauserapp.viewappointments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import com.todddavies.components.progressbar.ProgressWheel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

public class RescheduleAppointmentActivity extends Activity {

	TextView reschedule_spa_name, reschedule_spa_therapy,
			reschedule_time_for_service, reschedule_price, reschedule_date,
			reschedule_time, reschedule_therapist;
	TextView txt_reschedule_spa_name, txt_reschedule_spa_therapy,
			txt_reschedule_time_for_service, txt_reschedule_price,
			txt_reschedule_date, txt_reschedule_time, txt_reschedule_therapist;
	EditText edt_reschedule_therapist, edt_reschedule_date,
			edt_reschedule_appointment_time;
	Button Btn_Reschedule_Appointment;
	String Appointment_Id;
	Typeface font;
	ProgressBar progressBar_therapist;
	JSONParser jsonParser = new JSONParser();
	HashMap<String, String> AppointmentSendDeatils;

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reschedule_appointment);

		Intent i = getIntent();
		@SuppressWarnings("unchecked")
		final HashMap<String, String> AppointmentDeatils = (HashMap<String, String>) i
				.getSerializableExtra("AppointmentDetails");
		AppointmentSendDeatils = AppointmentDeatils;
		txt_reschedule_spa_name = (TextView) findViewById(R.id.txt_reschedule_spa_name);
		txt_reschedule_spa_therapy = (TextView) findViewById(R.id.txt_reschedule_spa_therapy);
		txt_reschedule_time_for_service = (TextView) findViewById(R.id.txt_reschedule_time_for_service);
		txt_reschedule_price = (TextView) findViewById(R.id.txt_reschedule_price);
		txt_reschedule_date = (TextView) findViewById(R.id.txt_reschedule_date);
		txt_reschedule_time = (TextView) findViewById(R.id.txt_reschedule_time);
		txt_reschedule_therapist = (TextView) findViewById(R.id.txt_reschedule_therapist);

		reschedule_spa_name = (TextView) findViewById(R.id.reschedule_spa_name);
		reschedule_spa_therapy = (TextView) findViewById(R.id.reschedule_spa_therapy);
		reschedule_time_for_service = (TextView) findViewById(R.id.reschedule_service_time);
		reschedule_price = (TextView) findViewById(R.id.reschedule_price);

		edt_reschedule_therapist = (EditText) findViewById(R.id.edt_reschedule_therapist);
		edt_reschedule_date = (EditText) findViewById(R.id.edt_reschedule_date);
		edt_reschedule_appointment_time = (EditText) findViewById(R.id.edt_reschedule_appointment_time);

		Btn_Reschedule_Appointment = (Button) findViewById(R.id.Btn_Reschedule_Appointment);

		progressBar_therapist = (ProgressBar) findViewById(R.id.progressBar_spin);

		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");

		txt_reschedule_spa_name.setTypeface(font);
		txt_reschedule_spa_therapy.setTypeface(font);
		txt_reschedule_time_for_service.setTypeface(font);
		txt_reschedule_price.setTypeface(font);
		txt_reschedule_date.setTypeface(font);
		txt_reschedule_time.setTypeface(font);
		txt_reschedule_therapist.setTypeface(font);

		reschedule_spa_name.setTypeface(font);
		reschedule_spa_therapy.setTypeface(font);
		reschedule_time_for_service.setTypeface(font);
		reschedule_price.setTypeface(font);

		edt_reschedule_therapist.setTypeface(font);
		edt_reschedule_date.setTypeface(font);
		edt_reschedule_appointment_time.setTypeface(font);

		Btn_Reschedule_Appointment.setTypeface(font);

		reschedule_spa_name.setText(AppointmentDeatils
				.get(ViewAppointmentFragment.Spa_Name));// Spa name
		reschedule_spa_therapy.setText(AppointmentDeatils
				.get(ViewAppointmentFragment.TherapyName));// Spa Therapy
		edt_reschedule_therapist.setText(AppointmentDeatils
				.get(ViewAppointmentFragment.Therapist_Name));// Therapist
		reschedule_time_for_service.setText(AppointmentDeatils
				.get(ViewAppointmentFragment.Time_For_Service));// Time
		// for
		// service
		reschedule_price.setText(AppointmentDeatils
				.get(ViewAppointmentFragment.Pricing));// Price

		String date = AppointmentDeatils
				.get(ViewAppointmentFragment.Appointment_Time);
		final String[] parts = date.split(" ");

		edt_reschedule_date.setText(parts[0]);
		edt_reschedule_appointment_time.setText(parts[1]);

		edt_reschedule_appointment_time.setInputType(InputType.TYPE_NULL);
		edt_reschedule_date.setInputType(InputType.TYPE_NULL);

		getActionBar().setDisplayHomeAsUpEnabled(true);

		edt_reschedule_date.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] t = edt_reschedule_date.getText().toString()
						.split("-");
				final Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(t[2]));
				c.set(Calendar.MONTH, Integer.parseInt(t[1]));
				c.set(Calendar.DATE, Integer.parseInt(t[0]));
				showDatepickerDialog();
			}
		});
		edt_reschedule_appointment_time
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						String[] t = edt_reschedule_appointment_time.getText()
								.toString().split(":");
						final Calendar c = Calendar.getInstance();
						c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t[0]));
						c.set(Calendar.MINUTE, Integer.parseInt(t[1]));

						final int mHour = c.get(Calendar.HOUR_OF_DAY);
						final int mMinute = c.get(Calendar.MINUTE);

						// c.set(Calendar.AM_PM, AM_PM);
						// Launch Time Picker Dialog
						TimePickerDialog tpd = new TimePickerDialog(
								RescheduleAppointmentActivity.this,
								new TimePickerDialog.OnTimeSetListener() {

									@Override
									public void onTimeSet(TimePicker view,
											int hourOfDay, int minute) {
										// Display Selected time in textbox
										// edt_time.setText(hourOfDay + ":" +
										// minute);
										Util.Appointment_Time = hourOfDay + ":"
												+ minute;
//										String am_pm = null;
										Calendar datetime = Calendar
												.getInstance();
										datetime.set(Calendar.HOUR_OF_DAY,
												hourOfDay);
										datetime.set(Calendar.MINUTE, minute);

//										if (datetime.get(Calendar.AM_PM) == Calendar.AM)
//											am_pm = "AM";
//										else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
//											am_pm = "PM";

										String strHrsToShow = (datetime
												.get(Calendar.HOUR) == 0) ? "12"
												: datetime.get(Calendar.HOUR)
														+ "";
										edt_reschedule_appointment_time.setText(strHrsToShow
												+ ":"
												+ datetime.get(Calendar.MINUTE));//+am_pm

									}

								}, mHour, mMinute, false);

						tpd.show();
					}
				});

		Btn_Reschedule_Appointment
				.setOnClickListener(new View.OnClickListener() {

					@SuppressLint("SimpleDateFormat")
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Calendar cal = Calendar.getInstance();
						int day = cal.get(Calendar.DAY_OF_MONTH);
						int month = cal.get(Calendar.MONTH);
						int year = cal.get(Calendar.YEAR);
						final int mHour = cal.get(Calendar.HOUR_OF_DAY);
						final int mMinute = cal.get(Calendar.MINUTE);
//						final int mSec = cal.get(Calendar.SECOND);

						String startTime = year + "-" + (month + 1) + "-" + day
								+ " " + mHour + ":" + mMinute;

						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");

						String app_time = edt_reschedule_date.getText()
								.toString()
								+ " "
								+ edt_reschedule_appointment_time.getText()
										.toString();
						// Toast.makeText(RescheduleAppointmentActivity.this,
						// "App: "+app_time, Toast.LENGTH_LONG).show();
						try {

							Date today = dateFormat.parse(startTime);
							Date appointment = dateFormat.parse(app_time);
							if (appointment.before(today)) {
								edt_reschedule_date.setError("Incorrect Date");
								// Toast.makeText(
								// RescheduleAppointmentActivity.this,
								// "wrong appointment date",
								// Toast.LENGTH_LONG).show();
							}
							//
							else if (appointment.compareTo(today) == 0
									|| appointment.compareTo(today) > 0) {
								edt_reschedule_date.setError(null);
								progressBar_therapist
										.setVisibility(View.VISIBLE);
								new IsTherapistAvailable().execute(AppointmentDeatils
										.get(ViewAppointmentFragment.Therapist_Id));
							}
							// Toast.makeText(RescheduleAppointmentActivity.this,
							// "correct appointment date",
							// Toast.LENGTH_LONG).show();

						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

		/** Appointment Id ***/
		// Appointment_Id=AppointmentDeatils.get(7);
	}

	/**
	 * Date Picker
	 */
	public void showDatepickerDialog() {
		// InputMethodManager imm = (InputMethodManager)getSystemService(
		// Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(edt_date.getWindowToken(), 0);

		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		DatePickerDialog mDatePicker = new DatePickerDialog(
				RescheduleAppointmentActivity.this, new OnDateSetListener() {
					public void onDateSet(DatePicker datepicker,
							int selectedyear, int selectedmonth, int selectedday) {
						// TODO Auto-generated method stub
						/* Your code to get date and time */
						edt_reschedule_date.setText(selectedyear + "-"
								+ (selectedmonth + 1) + "-" + selectedday);

					}
				}, year, month, day);
		mDatePicker.setTitle("Select date");
		mDatePicker.show();
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

				params.add(new BasicNameValuePair("Therapist_Id", "" + args[0]));
				params.add(new BasicNameValuePair("Appointment_Time", ""
						+ edt_reschedule_date.getText().toString().trim()
						+ " "
						+ edt_reschedule_appointment_time.getText().toString()
								.trim()));

				Log.d("request!", args[0]);
				Log.d("Appointment time!", edt_reschedule_date.getText()
						.toString().trim()
						+ " "
						+ edt_reschedule_appointment_time.getText().toString()
								.trim());

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
			progressBar_therapist.setVisibility(View.GONE);
			if (file_url != null) {
				if (success == 1) {

					try {
						Btn_Reschedule_Appointment.setEnabled(true);
						new RescheduleAppointmentTask().execute();
						// Btn_Reschedule_Appointment.setText("Select Therapist");

					} catch (Exception e) {

					}

				} else {
					Btn_Reschedule_Appointment.setText("Therapist unavailable");
					Btn_Reschedule_Appointment.setEnabled(false);
				}

			}
		}

	}

	class RescheduleAppointmentTask extends AsyncTask<String, String, String> {

		String Appointment_Id = "";
		String Uid = "";
		String Therapist_Id = "";
		String Time = "";
		String Date = "";
		ProgressDialog pDialog;
		Dialog dialog;
		int success;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// Toast.makeText(getApplicationContext(),
			// AppointmentSendDeatils.get(ViewAppointmentFragment.TherapyName),
			// Toast.LENGTH_LONG).show();

			dialog = new Dialog(RescheduleAppointmentActivity.this,
					R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Rescheduling Appointment... ");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				// Building Parameters
				List<NameValuePair> param1 = new ArrayList<NameValuePair>();
				param1.add(new BasicNameValuePair("Appointment_Id",
						AppointmentSendDeatils
								.get(ViewAppointmentFragment.Appointment_Id)));
				param1.add(new BasicNameValuePair("Therapist_Id",
						AppointmentSendDeatils
								.get(ViewAppointmentFragment.Therapist_Id)));
				param1.add(new BasicNameValuePair("Appointment_Start_Time",
						edt_reschedule_date.getText().toString() + " "
								+ edt_reschedule_appointment_time.getText()));

				JSONObject json = jsonParser.makeHttpRequest(
						Util.Reschedule_Appointments_URL, "POST", param1);

				// full json response
				Log.d("Reschedule appointment", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
			Intent intent = new Intent();
			setResult(2, intent);
			finish();

		}

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
			Intent intent = new Intent();
			setResult(2, intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent();
		setResult(2, intent);
	}
}
