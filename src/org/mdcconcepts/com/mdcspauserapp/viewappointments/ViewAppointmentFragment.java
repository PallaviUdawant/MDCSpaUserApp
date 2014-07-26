package org.mdcconcepts.com.mdcspauserapp.viewappointments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.ConnectionDetector;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

public class ViewAppointmentFragment extends Fragment {

	public static final String TherapyName = "TherapyName";
	public static final String Therapist_Name = "Therapist_Name";
	public static final String Appointment_Time = "Appointment_Time";
	public static final String Time_For_Service = "Time_For_Service";
	public static final String Pricing = "Pricing";
	public static final String status = "status";
	public static final String Spa_Id = "Spa_Id";
	public static final String Spa_Name = "Spa_Name";
	public static final String Therapist_Id = "Therapist_Id";
	public static final String Appointment_Id = "Appointment_Id";

	ProgressWheel progressBar_Controller_Login;

	EditText EditText_Controller_Username_Login;
	EditText EditText_Controller_Password_Login;
	Dialog loginDialog;
	Editor editor;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	TextView textView_controller_incorrect_credentials;
	public ViewAppointmentFragment() {
	}

	ListView listViewController_Appointmentlist;

	TextView textView_Current_Appointments;
	TextView textView_History;
	JSONParser jsonParser = new JSONParser();
	ArrayList<String> ArrayList_AppointMent_Service = new ArrayList<String>();
	ArrayList<String> ArrayList_AppointMent_Time = new ArrayList<String>();
	ArrayList<String> ArrayList_AppointMentIDList = new ArrayList<String>();
	ArrayList<HashMap<String, String>> ArrayList_AppointMent = new ArrayList<HashMap<String, String>>();
	HashMap<String, String> MissingArray = new HashMap<String, String>();

	ArrayList<Item> items = new ArrayList<Item>();
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	Typeface font;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		cd = new ConnectionDetector(getActivity());
		isInternetPresent = cd.isConnectingToInternet();
		if (!isInternetPresent) {
			View rootView = inflater.inflate(R.layout.fragment_no_internet_connection,
					container, false);
			return rootView;
		}
		
		View rootView = inflater.inflate(R.layout.fragment_viewappointment,
				container, false);
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(Util.APP_PREFERENCES,
						Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);
		font = Typeface.createFromAsset(rootView.getContext().getAssets(),
				Util.fontPath);

		

	
		if (!isLogin) {
			loginDialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
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

			progressBar_Controller_Login = (ProgressWheel) loginDialog
					.findViewById(R.id.progressBar_Controller_Login);
			
			TextView_Controller_Login_Title.setTypeface(font);
			TextView_Controller_Create_account.setTypeface(font);
			Button_Controller_Login.setTypeface(font);

			textView_controller_incorrect_credentials.setTypeface(font);
			EditText_Controller_Password_Login.setTypeface(font);
			EditText_Controller_Username_Login.setTypeface(font);
			
			loginDialog.show();
			
			
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
		else
		{
			new GetAppointments().execute();
		}
		
		listViewController_Appointmentlist = (ListView) rootView
				.findViewById(R.id.listViewController_Appointmentlist);

		textView_Current_Appointments = (TextView) rootView
				.findViewById(R.id.textView_Current_Appointments);
		font = Typeface.createFromAsset(rootView.getContext().getAssets(),
				Util.fontPath);
		textView_Current_Appointments.setTypeface(font, Typeface.BOLD);

		Util.isHeader = false;
		Util.isUpcoming = false;
		
	
		return rootView;

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
					editor.putBoolean("IsLogin", true);
					editor.putString("UserName",
							EditText_Controller_Username_Login.getText()
									.toString());
					editor.putString("Password",
							EditText_Controller_Password_Login.getText()
									.toString());
					editor.commit();
					loginDialog.dismiss();
					
					new GetAppointments().execute();
//					Intent myIntent = new Intent(
//							FinalMakeAppointmentActivity.this,
//							MainActivity.class);
//					finish();
//					startActivity(myIntent);
				} else if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(
							getActivity(),
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
	class GetAppointments extends AsyncTask<String, String, String> {

		// Progress Dialog
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		Dialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Getting Your Appointments ... ");
			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			String Uid = "" + Util.Uid;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", Uid));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.Get_Appointments_URL, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						// HashMap<String, String> Appointment_Details = new
						// HashMap<String, String>();
						JSONObject Temp = PostJson.getJSONObject(i);
						if (Temp.getString("Appointment_Status")
								.equalsIgnoreCase("Attended")
								&& Util.isHeader == false) {
							items.add(new SectionItem("History"));
							Util.isHeader = true;
						}
						if (Temp.getString("Appointment_Status")
								.equalsIgnoreCase("Upcoming")
								&& Util.isUpcoming == false) {
							items.add(new SectionItem("Upcoming Appointments"));
							Util.isUpcoming = true;
						}
						items.add(new EntryItem(Temp.getString("Name"), Temp
								.getString("Therapist_Name"), Temp
								.getString("Appointment_DateTime"), Temp
								.getString("Time"), Temp.getString("Pricing"),
								Temp.getString("Appointment_Status"), Temp
										.getString("Spa_Id"), Temp
										.getString("Spa_Name"), Temp
										.getString("Therapist_Id"), Temp
										.getString("Appointment_Id")));
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
			dialog.dismiss();
			if (file_url != null) {
				if (success == 1) {
					EntryAdapter adapter = new EntryAdapter(getActivity(),
							items);
					listViewController_Appointmentlist.setAdapter(adapter);
					listViewController_Appointmentlist
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									//

									HashMap<String, String> MyAppointmentDetails = new HashMap<String, String>();

									EntryItem data = (EntryItem) view.getTag();

									MyAppointmentDetails.put(Spa_Name,
											data.Spa_Name);
									MyAppointmentDetails.put(Spa_Id,
											data.Spa_Id);
									MyAppointmentDetails.put(TherapyName,
											data.TherapyName);
									MyAppointmentDetails.put(Therapist_Id,
											data.Therapist_Id);
									MyAppointmentDetails.put(Therapist_Name,
											data.Therapist_Name);
									MyAppointmentDetails.put(Time_For_Service,
											data.Time_For_Service);
									MyAppointmentDetails.put(Pricing,
											data.Pricing);
									MyAppointmentDetails.put(Appointment_Id,
											data.Appointment_Id);
									MyAppointmentDetails.put(Appointment_Time,
											data.Appointment_Time);

									// MyAppointement.add(data.Spa_Name);
									// MyAppointement.add(data.TherapyName);
									// MyAppointement.add(data.Therapist_Name);
									// MyAppointement.add(data.Time_For_Service);
									// MyAppointement.add(data.Pricing);
									// MyAppointement.add(data.Appointment_Time);
									// MyAppointement.add(data.Therapist_Id);

									// MyAppointement.add(data.)
									// Toast.makeText(getActivity(),
									// data.status.toString(),
									// Toast.LENGTH_LONG).show();
									if (data.status.toString()
											.equalsIgnoreCase("Upcoming")) {
										Intent i = new Intent(
												getActivity(),
												RescheduleAppointmentActivity.class);
										i.putExtra("AppointmentDetails",
												MyAppointmentDetails);
										startActivityForResult(i, 2);
										getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

									} else if (data.status.toString()
											.equalsIgnoreCase("Attended"))
										;

									// Intent intentGetMessage = new Intent(
									// getActivity(),
									// SeeApointmentActivity.class);
									//
									// intentGetMessage.putExtra("Appointment_Id",
									// ArrayList_AppointMentIDList
									// .get(position));
									//
									// startActivity(intentGetMessage);

									// // Toast.makeText(
									// // ManageAppointmentActivity.this,
									// // "You Clicked at "
									// // + ArrayList_AppointMent_Header
									// // .get(+position),
									// // Toast.LENGTH_SHORT).show();
									// Intent myIntent = new Intent(
									// ManageAppointmentActivity.this,
									// UpdateAppointmentActivity.class);
									//
									// int Header = Integer
									// .parseInt(ArrayList_AppointMent_Header
									// .get(+position));
									//
									// Log.d("request!", "" + Header);
									//
									// Util.Appointment_Id = Header;
									// startActivity(myIntent);
								}
							});
				}
			}

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed here it is 2
		if (requestCode == 2) {
			Util.isHeader = false;
			Util.isUpcoming = false;
			items.clear();
			listViewController_Appointmentlist.setAdapter(null);
			new GetAppointments().execute();

		}

	}
}
