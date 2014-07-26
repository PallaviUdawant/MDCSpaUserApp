package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

public class SelectTherapistActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private List<String> ArrayList_AllTherapistList = new ArrayList<String>();
	private ArrayList<String> ArrayList_AllTherapistIdList = new ArrayList<String>();
	JSONParser jsonParser = new JSONParser();

	private String Therapies_Id;
	Typeface font;
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	HashMap<String, String> AllDetails = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_therapist);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(10, 10, 10, 10);
		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");

		Intent i = getIntent();
		AllDetails = (HashMap<String, String>) i
				.getSerializableExtra("AllDetails");
		Therapies_Id = AllDetails.get("Therapy_Id");

		new GetTherapies().execute();

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
			Intent data = new Intent();
			data.putExtra("Therapist_Id", 0);
			data.putExtra("Therapist_Name", "No Therapist Selected");
			setResult(1, data);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			TherapistSchedule fragment = new TherapistSchedule(
					ArrayList_AllTherapistIdList.get(position),
					ArrayList_AllTherapistList.get(position), AllDetails);
			// Bundle data = new Bundle();
			// data.putInt("current_page", position + 1);
			// fragment.setArguments(data);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return ArrayList_AllTherapistIdList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {

			return ArrayList_AllTherapistList.get(position);
			// Locale l = Locale.getDefault();
			// switch (position) {
			// case 0:
			// return getString(R.string.title_section1).toUpperCase(l);
			// case 1:
			// return getString(R.string.title_section2).toUpperCase(l);
			// case 2:
			// return getString(R.string.title_section3).toUpperCase(l);
			// }
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent data = new Intent();
		data.putExtra("Therapist_Id", 0);
		data.putExtra("Therapist_Name", "No Therapist Selected");
		setResult(1, data);
		finish();
		// super.onBackPressed();
	}

	class GetTherapies extends AsyncTask<String, String, String> {

		// Progress Dialog
		Dialog dialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new Dialog(SelectTherapistActivity.this,
					R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);
			dialog.show();

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Getting Therapist Details....");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("Therapies_Id", ""
						+ Therapies_Id));
				Log.d("Get Therapist!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.GetTherapist,
						"POST", params);

				if (json != null) {
					// json success element
					// full json response
					Log.d("Login attempt", json.toString());

					success = json.getInt(TAG_SUCCESS);
					if (success == 1) {

						JSONArray PostJson = json.getJSONArray("posts");
						Log.d("Post Date ", PostJson.toString());
						for (int i = 0; i < PostJson.length(); i++) {

							JSONObject Temp = PostJson.getJSONObject(i);

							ArrayList_AllTherapistList.add(Temp
									.getString("Name"));
							ArrayList_AllTherapistIdList.add(Temp
									.getString("Therapist_Id"));

						}
						return json.getString(TAG_MESSAGE)
								+ " , Please Login !";
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
			dialog.dismiss();
			if (file_url != null) {
				if (file_url.equalsIgnoreCase("timeout"))
					Toast.makeText(SelectTherapistActivity.this,
							"Connection Timeout..!!!", Toast.LENGTH_LONG)
							.show();
				// Create the adapter that will return a fragment for each of
				// the three
				// primary sections of the app.
				mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());
				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);
				mViewPager.setPageMargin(2);
				mViewPager.setPageMarginDrawable(R.color.pager_margin);
			}
		}

	}

}
