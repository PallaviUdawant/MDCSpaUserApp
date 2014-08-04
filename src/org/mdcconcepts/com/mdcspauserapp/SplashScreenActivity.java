package org.mdcconcepts.com.mdcspauserapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.customitems.ConnectionDetector;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;
import org.mdcconcepts.com.mdcspauserapp.wishlist.WishList_Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class SplashScreenActivity extends Activity {

	private final int SPLASH_TIME_OUT = 3000;
	ConnectionDetector cd;
	JSONParser jsonParser = new JSONParser();
//	SharedPreferences pref;
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		final ImageView imageView_Logo=(ImageView)findViewById(R.id.imageView_Logo);
		
		cd = new ConnectionDetector(getApplicationContext());
		
//		final Animation animFade = AnimationUtils.loadAnimation(
//				SplashScreenActivity.this, R.drawable.fade);

		Animation animation = AnimationUtils.loadAnimation(this,
				R.drawable.translate);
		imageView_Logo.startAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				imageView_Logo.clearAnimation();

			}
		});
		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity

				/**
				 * Shared preferences to Store Wishlist
				 */
//				pref = getApplicationContext()
//						.getSharedPreferences(Util.APP_PREFERENCES, 0);
//				String value = pref.getString(Util.WishList, null);
				String value = AppSharedPreferences.getUserWishList(SplashScreenActivity.this);
				// Log.d("Wish List Json ", value);
				if (value != null) {

					try {
						JSONArray WishList_JsonArray = new JSONArray(value);

						for (int i = 0; i < WishList_JsonArray.length(); i++) {
							JSONObject Wish_JsonObject = WishList_JsonArray
									.getJSONObject(i);
							WishList_Fragment.WishList.put(Integer
									.parseInt(Wish_JsonObject
											.getString("therapy_id")),
									Wish_JsonObject.getString("therapy_name"));

						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
					}

				}
				boolean isInternetPresent = cd.isConnectingToInternet();
				
				boolean isLogin =AppSharedPreferences.getLoginStatus(SplashScreenActivity.this);
				
				Log.d("IsLogin", String.valueOf(isLogin));
				
				Log.d("isInternetPresent", String.valueOf(isInternetPresent));
				
			
				if(isInternetPresent && isLogin)
				{
					Log.d("get user data", "getting data");
					Log.d("UserId", String.valueOf(Util.Uid));
					new GetUserData().execute();
				}

				Intent i = new Intent(SplashScreenActivity.this,
						MainActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				// close this activity
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
	class GetUserData extends AsyncTask<String, String, String> {

		// Progress Dialog
		// private ProgressDialog pDialog;
//		private Dialog dialog;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		String Name1;
		String Mobile1;
		String Email1;
		String Address1;
		String DOB1;
		String Anniversary1;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

//			dialog = new Dialog(SplashScreenActivity.this,
//					R.style.ThemeWithCorners);
//			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dialog.setContentView(R.layout.custom_progress_dialog);
//			dialog.setCancelable(false);
//			dialog.show();
//
//			TextView Txt_Title = (TextView) dialog
//					.findViewById(R.id.txt_alert_text);
//			Txt_Title.setTypeface(font);
//			Txt_Title.setText("Getting User Details....");
//
//			ProgressWheel pw_four = (ProgressWheel) dialog
//					.findViewById(R.id.progressBarFour);
//			pw_four.spin();

		}

		@SuppressLint("SimpleDateFormat")
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", "" + AppSharedPreferences.getUID(getApplicationContext())));
//				Log.d("Uid", "" + Util.Uid);
				Log.d("requesting from Splash screen GetUserData task!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetUserDetails, "POST", params);
				if (json != null) {
					// full json response
					Log.d("Login attempt", json.toString());

					// json success element
					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						Name1 = json.getString("Name");
						Mobile1 = json.getString("Mobile");
						Email1 = json.getString("Email");
						Address1 = json.getString("Address");
						DOB1 = json.getString("DOB");
						Anniversary1 = json.getString("Anniversary");

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
			// dismiss the dialog once product deleted
//			dialog.cancel();
			if (file_url != null) {

				if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(
							SplashScreenActivity.this,
							"Connection TimeOut..!!! Please try again later..!!!",
							Toast.LENGTH_LONG).show();
				} else {
					Util.User_Name = Name1;
					Util.User_Contact_Number = Mobile1;
					Util.User_EmailId = Email1;
					Util.User_Address = Address1;
					Util.User_DOB = DOB1;
					Util.User_Anniversary = Anniversary1;
				}
			}

		}
	}
}
