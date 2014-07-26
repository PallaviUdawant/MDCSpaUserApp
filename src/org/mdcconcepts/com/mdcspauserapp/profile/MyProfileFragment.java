package org.mdcconcepts.com.mdcspauserapp.profile;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.InjuriesActivityMain;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

public class MyProfileFragment extends Activity {

	ProfileListViewAdapter customAdapter;
	JSONParser jsonParser = new JSONParser();
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */

	static Typeface font;
	// public static View rootView;
	TextView User_Name;
	private ListView listView_tabs;
	private Intent i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile_fragment);
		
//		if(isUserDetailsFetched)
	
		// rootView = inflater.inflate(R.layout.my_profile_fragment, container,
		// false);

		User_Name = (TextView) findViewById(R.id.User_Name);
		User_Name.setText(Util.User_Name.toString());

		Typeface font = Typeface.createFromAsset(getAssets(), Util.fontPath);
		User_Name.setTypeface(font, Typeface.BOLD);
		listView_tabs = (ListView) findViewById(R.id.listView_tabs);

		customAdapter = new ProfileListViewAdapter(this);
		listView_tabs.setAdapter(customAdapter);

		listView_tabs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					i = new Intent(MyProfileFragment.this,
							ProfileFragment.class);
					startActivity(i);
					break;
				case 1:
					i = new Intent(MyProfileFragment.this,
							InjuriesActivityMain.class);
					startActivity(i);
					break;
				}
			}
		});
		getActionBar().setDisplayHomeAsUpEnabled(true);

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

	/**
	 * Fetch User Details and save it in util class details shown in Profile
	 * class
	 * 
	 * @author Pallavi
	 * 
	 */
	class GetUserData extends AsyncTask<String, String, String> {

		// Progress Dialog
		// private ProgressDialog pDialog;
		private Dialog dialog;
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

			dialog = new Dialog(MyProfileFragment.this,
					R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Getting User Details....");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();

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
				params.add(new BasicNameValuePair("Uid", "" + Util.Uid));
				Log.d("Uid", "" + Util.Uid);
				Log.d("request!", "starting");

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
			dialog.cancel();
			if (file_url != null) {

				if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(
							MyProfileFragment.this,
							"Connection TimeOut..!!! Please try again later..!!!",
							Toast.LENGTH_LONG).show();
				} else {
					Util.User_Name = Name1;
					Util.User_Contact_Number = Mobile1;
					Util.User_EmailId = Email1;
					Util.User_Address = Address1;
					Util.User_DOB = DOB1;
					Util.User_Anniversary = Anniversary1;
					User_Name.setText(Name1);
				}
			}

		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActionBar();
		actionBar.removeAllTabs();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onDestroy();

	}

	
}
