package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.signup.SignUpActivity;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

public class FinalMakeAppointmentActivity extends Activity {

	TextView final_spa_name, final_spa_therapy, final_time_for_service,
			final_price, final_date, final_time, final_therapist;
	TextView txt_final_spa_name, txt_final_spa_therapy,
			txt_final_time_for_service, txt_final_price, txt_final_date,
			txt_final_time, txt_final_therapist;
	TextView textView_controller_incorrect_credentials;
	HashMap<String, String> AllDetails = new HashMap<String, String>();
	Typeface font;
	Button Button_Controller_make_an_appointment;
//	SharedPreferences sharedPreferences;

	EditText EditText_Controller_Username_Login;
	EditText EditText_Controller_Password_Login;
	JSONParser jsonParser = new JSONParser();

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	Dialog loginDialog;
	
//	Editor editor;

	ProgressWheel progressBar_Controller_Login;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_make_appointment);

		Intent i = getIntent();
		AllDetails = (HashMap<String, String>) i
				.getSerializableExtra("AllDetails");

		txt_final_spa_name = (TextView) findViewById(R.id.txt_final_spa_name);
		txt_final_spa_therapy = (TextView) findViewById(R.id.txt_final_spa_therapy);
		txt_final_time_for_service = (TextView) findViewById(R.id.txt_final_time_for_service);
		txt_final_price = (TextView) findViewById(R.id.txt_final_price);
		txt_final_date = (TextView) findViewById(R.id.txt_final_date);
		txt_final_time = (TextView) findViewById(R.id.txt_final_time);
		txt_final_therapist = (TextView) findViewById(R.id.txt_final_therapist);

		final_spa_name = (TextView) findViewById(R.id.final_spa_name);
		final_spa_therapy = (TextView) findViewById(R.id.final_spa_therapy);
		final_time_for_service = (TextView) findViewById(R.id.final_time_for_service);
		final_price = (TextView) findViewById(R.id.final_price);
		final_date = (TextView) findViewById(R.id.final_date);
		final_time = (TextView) findViewById(R.id.final_time);
		final_therapist = (TextView) findViewById(R.id.final_therapist);

		Button_Controller_make_an_appointment = (Button) findViewById(R.id.Button_Controller_make_an_appointment);

		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");

		txt_final_spa_name.setTypeface(font);
		txt_final_spa_therapy.setTypeface(font);
		txt_final_time_for_service.setTypeface(font);
		txt_final_price.setTypeface(font);
		txt_final_date.setTypeface(font);
		txt_final_time.setTypeface(font);
		txt_final_therapist.setTypeface(font);

		final_spa_name.setTypeface(font);
		final_spa_therapy.setTypeface(font);
		final_time_for_service.setTypeface(font);
		final_price.setTypeface(font);
		final_date.setTypeface(font);
		final_time.setTypeface(font);
		final_therapist.setTypeface(font);

		final_spa_name.setText(AllDetails.get("Spa_Name"));
		final_spa_therapy.setText(AllDetails.get("Therapy_Name"));
		final_time_for_service.setText(AllDetails.get("Therapy_Time"));
		final_price.setText(AllDetails.get("Therapy_Price"));
		final_date.setText(AllDetails.get("Selected_Date"));
		final_time.setText(AllDetails.get("Selected_Time"));
		final_therapist.setText(AllDetails.get("Selected_Therapist"));

		Button_Controller_make_an_appointment.setTypeface(font);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(10, 10, 10, 10);

//		sharedPreferences = getSharedPreferences(Util.APP_PREFERENCES,
//				Context.MODE_PRIVATE);
//		editor = sharedPreferences.edit();

		loginDialog = new Dialog(this, R.style.ThemeWithCorners);
		loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		loginDialog.setContentView(R.layout.activity_login);
		loginDialog.setCancelable(true);

		TextView TextView_Controller_Login_Title = (TextView) loginDialog
				.findViewById(R.id.textView_Controller_Login_Title);
		TextView TextView_Controller_Create_account = (TextView) loginDialog
				.findViewById(R.id.TextView_Controller_Create_account);

		EditText_Controller_Username_Login = (EditText) loginDialog
				.findViewById(R.id.EditText_Controller_Username_Login);
		EditText_Controller_Password_Login = (EditText) loginDialog
				.findViewById(R.id.EditText_Controller_Password_Login);

		textView_controller_incorrect_credentials = (TextView) loginDialog
				.findViewById(R.id.textView_controller_incorrect_credentials);

		Button Button_Controller_Login = (Button) loginDialog
				.findViewById(R.id.Button_Controller_Login);

		
		progressBar_Controller_Login=(ProgressWheel)loginDialog.findViewById(R.id.progressBar_Controller_Login);
		
		TextView_Controller_Login_Title.setTypeface(font);
		TextView_Controller_Create_account.setTypeface(font);
		textView_controller_incorrect_credentials.setTypeface(font);
		
		EditText_Controller_Password_Login.setTypeface(font);
		EditText_Controller_Username_Login.setTypeface(font);

		Button_Controller_Login.setTypeface(font);
TextView_Controller_Create_account.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(FinalMakeAppointmentActivity.this,SignUpActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//				loginDialog.dismiss();
			}
		});
		Button_Controller_make_an_appointment
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

//						boolean isLogin = sharedPreferences.getBoolean(
//								"IsLogin", false);
						if (!AppSharedPreferences.getLoginStatus(FinalMakeAppointmentActivity.this))
							loginDialog.show();
						else {
							//forward to payment gateway
						}

					}
				});
		Button_Controller_Login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (EditText_Controller_Username_Login.getText().toString()
						.trim().isEmpty()) {
					EditText_Controller_Username_Login
							.setError("Please Enter Username !");
				} else if (EditText_Controller_Password_Login.getText()
						.toString().trim().isEmpty()) {
					EditText_Controller_Password_Login
							.setError("Please Enter Password !");
				} else {
					new LoginUser().execute();
				}

			}

		});
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

	class LoginUser extends AsyncTask<String, String, String> {

//		private Dialog dialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

//			dialog = new Dialog(FinalMakeAppointmentActivity.this,
//					R.style.ThemeWithCorners);
//			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setContentView(R.layout.custom_progress_dialog);
//			dialog.setCancelable(false);
////			dialog.show();
//
//			TextView Txt_Title = (TextView) dialog
//					.findViewById(R.id.txt_alert_text);
//			Txt_Title.setTypeface(font);
//			/**
//			 * custom circular progress bar
//			 */
//			ProgressWheel pw_four = (ProgressWheel) dialog
//					.findViewById(R.id.progressBarFour);
//			pw_four.spin();
			progressBar_Controller_Login.setVisibility(View.VISIBLE);
			progressBar_Controller_Login.spin();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			String Username_Container = EditText_Controller_Username_Login
					.getText().toString().trim();
			String Password_Container = EditText_Controller_Password_Login
					.getText().toString().trim();

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Username",
						Username_Container));
				params.add(new BasicNameValuePair("Password",
						Password_Container));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.Login_URL,
						"POST", params);

				if (json != null) {// full json response
					Log.d("Login attempt", json.toString());

					// json success element
					success = json.getInt(TAG_SUCCESS);

					if (success == 1) {
						Util.Uid = json.getInt("Uid");
						
						AppSharedPreferences.setUID(getApplicationContext(), String.valueOf(json.getInt("Uid")));
						
						Util.User_Name = json.getString("Name");
						Util.User_Contact_Number = json.getString("Mobile");
						Util.User_EmailId = json.getString("Email");
						Util.User_Address = json.getString("Address");
						Util.User_DOB = json.getString("DOB");
						Util.User_Anniversary = json.getString("Anniversary");
						return json.getString(TAG_MESSAGE);
					} else

					{
						// Log.d("Login Failure!", json.getString(TAG_MESSAGE));
						// Toast.makeText(LoginActivity.this,
						// "Wrong Username or Password", Toast.LENGTH_LONG)
						// .show();
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
		 **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
//			dialog.cancel();
			progressBar_Controller_Login.setVisibility(View.GONE);
			progressBar_Controller_Login.stopSpinning();
			if (file_url != null) {
				// Toast.makeText(LoginActivity.this, file_url,
				// Toast.LENGTH_LONG)
				// .show();
				if (success == 1) {
					
					AppSharedPreferences.setLoginStatus(getApplicationContext(), true);
					AppSharedPreferences.setUserName(getApplicationContext(), EditText_Controller_Username_Login.getText()
									.toString());
					AppSharedPreferences.setPassword(getApplicationContext(), EditText_Controller_Password_Login.getText()
									.toString());
//					editor.putBoolean("IsLogin", true);
//					editor.putString("UserName",
//							EditText_Controller_Username_Login.getText()
//									.toString());
//					editor.putString("Password",
//							EditText_Controller_Password_Login.getText()
//									.toString());
//					editor.commit();
					loginDialog.dismiss();
					
					//forward to payment gateway
					
//					Intent myIntent = new Intent(
//							FinalMakeAppointmentActivity.this,
//							MainActivity.class);
//					finish();
//					startActivity(myIntent);
				} else if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(
							FinalMakeAppointmentActivity.this,
							"Connection TimeOut..!!! Please try again later..!!!",
							Toast.LENGTH_LONG).show();
				} else {
					
					textView_controller_incorrect_credentials
							.setVisibility(View.VISIBLE);
//					textView_controller_incorrect_credentials.setTypeface(font);
//					Toast.makeText(FinalMakeAppointmentActivity.this,
//							"Wrong Username or Password.. Please try again..!!!",
//							Toast.LENGTH_LONG).show();
				}
			}

		}

	}
}
