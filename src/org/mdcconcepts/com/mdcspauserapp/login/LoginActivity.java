package org.mdcconcepts.com.mdcspauserapp.login;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.MainActivity;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.ConnectionDetector;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.signup.SignUpActivity;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	EditText Username;
	EditText Password;
	
	Typeface font;
	
	TextView txt_Create_account;
	TextView txt_demo;
	Editor editor ;
	Button Login_ButtonController;
	Button GoToCreateAccount_ButtonController;

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	SharedPreferences pref;

	// Connection detector class
	ConnectionDetector cd;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_login);

		
		
//		RelativeLayout Login_relative_layout=(RelativeLayout)findViewById(R.id.Login_relative_layout);
		final LinearLayout login_panel_layout=(LinearLayout)findViewById(R.id.login_panel_layout);
		final LinearLayout title_layout=(LinearLayout)findViewById(R.id.title_layout);
		
//		txt_demo=(TextView)findViewById(R.id.txt_demo);
		
		login_panel_layout.setVisibility(View.GONE);
		 final Animation animFade  = AnimationUtils.loadAnimation( LoginActivity.this, R.drawable.fade);
		
		 Animation animation = AnimationUtils.loadAnimation(this, R.drawable.translate);
		 title_layout.startAnimation(animation);
		    //use this to make it longer:  animation.setDuration(1000);
		    animation.setAnimationListener(new AnimationListener() {
		        @Override
		        public void onAnimationStart(Animation animation) {
//		        	Toast.makeText(getApplicationContext(), "Animation Started", Toast.LENGTH_LONG).show();
		        }

		        @Override
		        public void onAnimationRepeat(Animation animation) {
//		        	Toast.makeText(getApplicationContext(), "Animation Repeat", Toast.LENGTH_LONG).show();
		        }

		        @Override
		        public void onAnimationEnd(Animation animation) {
//		        	Toast.makeText(getApplicationContext(), "Animation End", Toast.LENGTH_LONG).show();
		        	title_layout.clearAnimation();
		        	login_panel_layout.setVisibility(View.VISIBLE);
		        	
		        	 login_panel_layout.startAnimation(animFade);
		        	
//		        	txt_demo.setVisibility(View.VISIBLE);
//		        	 Animation animFade  = AnimationUtils.loadAnimation( LoginActivity.this, R.anim.fade);
//		        	 txt_demo.startAnimation(animFade);
//		        	 login_panel_layout.startAnimation(animFade);
//		             view.setVisibility(View.GONE);
		        }
		    });

		   
		    
		    
		font = Typeface.createFromAsset(getAssets(),"Raleway-Light.otf");	
		pref=getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		editor = pref.edit();
		
		
		
		
		
		// creating connection detector class instance
		cd = new ConnectionDetector(getApplicationContext());

		Username = (EditText) findViewById(R.id.Username_Login);
		Password = (EditText) findViewById(R.id.Password_Login);
		Login_ButtonController = (Button) findViewById(R.id.Login);
		
		txt_Create_account=(TextView)findViewById(R.id.txt_Create_account);
		
		Username.setTypeface(font);
		Password.setTypeface(font);
		Login_ButtonController.setTypeface(font);
		txt_Create_account.setTypeface(font);

		
		if(pref.getBoolean("Login_Status", false))
		{
//			Intent i= new Intent(LoginActivity.this,MainActivity.class);
//			startActivity(i);
//			finish();
		
			Username.setText(pref.getString("UserName", ""));
			Password.setText(pref.getString("Password", ""));
			
		}
		
		Login_ButtonController.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (Username.getText().toString().trim().isEmpty()) {
					Username.setError("Please Enter Username !");
				} else if (Password.getText().toString().trim().isEmpty()) {
					Password.setError("Please Enter Password !");
				} else {

					isInternetPresent = cd.isConnectingToInternet();

					// check for Internet status
					if (isInternetPresent) {
						// Internet Connection is Present
						// make HTTP requests

						new LoginUser().execute();
						// showAlertDialog(
						// AndroidDetectInternetConnectionActivity.this,
						// "Internet Connection",
						// "You have internet connection", true);
					} else {
						// Internet connection is not present
						// Ask user to connect to Internet
						showAlertDialog(LoginActivity.this,
								"No Internet Connection",
								"You don't have internet connection.", false);
					}

				}

			}
		});
		txt_Create_account.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(LoginActivity.this,
					SignUpActivity.class);
			finish();
			startActivity(myIntent);
			} 
		});
//
//		GoToCreateAccount_ButtonController = (Button) findViewById(R.id.GoToRegister);
//
//		GoToCreateAccount_ButtonController
//				.setOnClickListener(new OnClickListener() {
//					@Override
//					public void onClick(View v) {
////						Intent myIntent = new Intent(LoginActivity.this,
////								SignUpActivity.class);
////						finish();
////						startActivity(myIntent);
//
//					}
//				});
	}

	class LoginUser extends AsyncTask<String, String, String> {

		// Progress Dialog
//		private ProgressDialog pDialog;
		private Dialog dialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			dialog= new Dialog(LoginActivity.this,R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();
			
			TextView Txt_Title=(TextView)dialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			
//			pDialog = new ProgressDialog(LoginActivity.this);
//			pDialog.setContentView(R.layout.custom_progress_dialog);
//			pDialog.setMessage("Attempting Login ... ");
//			pDialog.setIndeterminate(false);
//			pDialog.setCancelable(false); pDialog.show();
			
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			
			String Username_Container = Username.getText().toString();
			String Password_Container = Password.getText().toString();

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

			
					
				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				Util.Uid = json.getInt("Uid");
				if (success == 1) {
					
					
					return json.getString(TAG_MESSAGE);
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
		 **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
		dialog.cancel();
			if (file_url != null) {
				Toast.makeText(LoginActivity.this, file_url, Toast.LENGTH_LONG)
						.show();
				if (success == 1)
				{
					editor.putBoolean("Login_Status", true);
					editor.putString("UserName", Username.getText().toString());
					editor.putString("Password", Password.getText().toString());
					editor.commit();
					
					Intent myIntent = new Intent(LoginActivity.this,
							MainActivity.class);
					finish();
					startActivity(myIntent);
				}
			}

		}

	}

	/**
	 * 
	 * Function to display simple Alert Dialog
	 * 
	 * @param context
	 *            - application context
	 * @param title
	 *            - alert dialog title
	 * @param message
	 *            - alert message
	 * @param status
	 *            - success/failure (used to set icon)
	 */
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		alertDialog.setIcon((status) ? R.drawable.ic_launcher
				: R.drawable.ic_launcher);

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}

}
