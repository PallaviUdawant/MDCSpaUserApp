package org.mdcconcepts.com.mdcspauserapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

public class InjuriesActivityMain extends FragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static ViewPager mViewPager;
	JSONParser jsonParser = new JSONParser();
	private Typeface font;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.injuries_viewpager);

		new GetPainingAreas().execute();
		
		font = Typeface.createFromAsset(getAssets(), Util.fontPath);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(10, 10, 10, 10);
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

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new HumanBodyFragment();
				break;

			case 1:
				fragment = new DiseaseFragment();
			default:
				break;
			}

			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {

			CharSequence title = null;
			switch (position) {
			case 0:
				title = "Injuries/Paining Areas";
				break;
			case 1:
				title = "Disease";
				break;
			}
			return title;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_injuries,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_labelNew);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
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
			pdialog = new Dialog(InjuriesActivityMain.this,
					R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			pdialog.show();

			Txt_Title = (TextView) pdialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);

			Txt_Title.setText("Fetching Data...");
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

				Log.d("request Injuries Activity!", "starting"+ String.valueOf(Util.Uid));

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.getPainData,
						"POST", params1);

				if (json != null) {
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
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Head = true;
								Util.PainAreas.add("1");
								Log.d("Head",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Head));
								break;
							case 2:
								// Neck
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck = true;
								Util.PainAreas.add("2");
								Log.d("Neck",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck));
								break;
							case 3:
								// Shoulder
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Shoulder = true;
								Util.PainAreas.add("3");
								Log.d("Shoulder",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Shoulder));
								break;
							case 4:
								// Arm
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Arm = true;
								Util.PainAreas.add("4");
								Log.d("Arm",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Arm));
								break;
							case 5:
								// Waist
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Waist = true;
								Util.PainAreas.add("5");
								Log.d("Waist",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Waist));
								break;
							case 6:
								// Back
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Back = true;
								Util.PainAreas.add("6");
								Log.d("Back",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Back));
								break;
							case 7:
								// Thigh
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Thigh = true;
								Util.PainAreas.add("7");
								Log.d("Thigh",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Thigh));
								break;
							case 8:
								// Calf
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Calf = true;
								Util.PainAreas.add("8");
								Log.d("Calf",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Calf));
								break;
							case 9:
								// Sole
								Util.PainAreas.add("9");
								Util.isSelected_ImageView_Controller_Activity_BodyPart_Sole = true;
								Log.d("Sole",
										String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Sole));
								break;

							}
						}
					}
					return "true";

				} 
//				else if(success==-1)
//				{
//					return "No injuries data";
//				}
				else{
					return "timeout";
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pdialog.dismiss();

			if (file_url != null) {
				if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(InjuriesActivityMain.this,
							"Connection Timeout...!!!", Toast.LENGTH_LONG)
							.show();
					finish();
				}

				else if (file_url.equalsIgnoreCase("true"))
					new GetDisease().execute();
//				else if (file_url.equalsIgnoreCase("No injuries data")) {
//					Toast.makeText(InjuriesActivityMain.this,
//							"Connection Timeout...!!!", Toast.LENGTH_LONG)
//							.show();
//				}
			}
			
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
			pdialog = new Dialog(InjuriesActivityMain.this,
					R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			pdialog.show();

			Txt_Title = (TextView) pdialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);

			Txt_Title.setText("Fetching Data...");
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

				Log.d("request Injuries Activity disease!", "starting"+ String.valueOf(Util.Uid));

				if (!Util.getDiseaseData.isEmpty()) {// Posting user data to
														// script
					JSONObject json = jsonParser.makeHttpRequest(
							Util.getDiseaseData, "POST", params1);

					// full json response

					if (json != null) {
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
									// Heart
									Util.isChecked_Heart = true;
									// Log.d("Head",
									// String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Head));
									break;
								case 2:
									// Hepatitis
									Util.isChecked_Hepatitis = true;
									// Log.d("Neck",
									// String.valueOf(Util.isSelected_ImageView_Controller_Activity_BodyPart_Neck));
									break;
								case 3:
									// Shoulder
									Util.isChecked_Diabeties = true;
									break;
								case 4:
									// Arm
									Util.isChecked_Lung = true;
									break;
								case 5:
									// Waist
									Util.isChecked_Hypotension = true;
									break;
								case 6:
									// Back
									Util.isChecked_Skin = true;
									break;
								case 7:
									// Thigh
									Util.isChecked_BP = true;
									break;
								case 8:
									// Calf
									Util.isChecked_Arthritis = true;
									break;
								case 9:
									// Sole
									Util.isChecked_Pregnant = true;
									break;
								case 10:
									// Sole
									Util.isChecked_Other = true;
									break;

								}
							}
							return "true";
						}

					} else {
						return "timeout";
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
			FragmentManager fm = getSupportFragmentManager();
			mSectionsPagerAdapter = new SectionsPagerAdapter(fm);
			// // Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.injuries_pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			if (file_url != null) {
				if (file_url.equals("timeout"))
					Toast.makeText(InjuriesActivityMain.this,
							"Connection Timeout...!!!!", Toast.LENGTH_LONG)
							.show();
			}
			
		}

	}
}
