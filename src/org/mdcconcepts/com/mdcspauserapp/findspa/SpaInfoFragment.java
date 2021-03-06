package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.ImageLoader;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.todddavies.components.progressbar.ProgressWheel;

@SuppressLint("ValidFragment")
public class SpaInfoFragment extends Fragment {

	TextView txt_spa_name_profile;
	TextView txt_spa_desc_profile;
	TextView txt_spa_addr_profile;
	TextView txt_distance;

	Button Btn_add_to_fav;
	Typeface font;

	ImageView imageView_Spa_Profile_Logo;
	ImageView imageView_Spa_cover_photo;
	RatingBar spa_profile_ratings;

	String Spa_Name = "";
	String Spa_Desc = "";
	String Spa_Addr = "";

	// JSON parser class
	JSONParser jsonParser = new JSONParser();

	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	HashMap<String, String> selectedSpaDetails = new HashMap<String, String>();

	public SpaInfoFragment(HashMap<String, String> spaDetails) {
		// TODO Auto-generated constructor stub
		// Spa_Name = spa_Name2;
		// Spa_Addr = spa_Addr2;
		this.selectedSpaDetails = spaDetails;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootview = inflater.inflate(R.layout.activity_spa_details, null);

		txt_spa_addr_profile = (TextView) rootview
				.findViewById(R.id.txt_spa_addr_profile);
		txt_spa_desc_profile = (TextView) rootview
				.findViewById(R.id.txt_spa_dec_profile);
		txt_spa_name_profile = (TextView) rootview
				.findViewById(R.id.txt_spa_name_profile);
		txt_distance = (TextView) rootview.findViewById(R.id.txt_distance);
		Btn_add_to_fav = (Button) rootview.findViewById(R.id.Btn_Add_to_fav);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				Util.fontPath);

		txt_spa_addr_profile.setTypeface(font);
		txt_spa_desc_profile.setTypeface(font);
		txt_spa_name_profile.setTypeface(font);
		txt_distance.setTypeface(font);

		txt_spa_name_profile.setText(selectedSpaDetails.get("spa_name"));
		txt_spa_addr_profile.setText(selectedSpaDetails.get("spa_addr"));
		txt_distance.setText(Util.DISTANCE);
		Btn_add_to_fav.setTypeface(font);

		imageView_Spa_Profile_Logo = (ImageView) rootview
				.findViewById(R.id.imageView_Spa_Profile_Logo);

		imageView_Spa_cover_photo = (ImageView) rootview
				.findViewById(R.id.imageView_Spa_cover_photo);

		ImageLoader imgLoader = new ImageLoader(getActivity());
		imgLoader.DisplayImage(selectedSpaDetails.get("spa_logo"),
				R.id.imageView_Spa_Profile_Logo, imageView_Spa_Profile_Logo);

		imgLoader.DisplayImage(selectedSpaDetails.get("spa_cover_photo"),
				R.id.imageView_Spa_cover_photo, imageView_Spa_cover_photo);
//
//		SharedPreferences pref = getActivity()
//				.getSharedPreferences(Util.APP_PREFERENCES, 0);
		if(!AppSharedPreferences.getLoginStatus(getActivity()))
			Btn_add_to_fav.setVisibility(View.GONE);
		
		Log.d("iSFav", selectedSpaDetails.get("IsFav"));
		if (selectedSpaDetails.get("IsFav").equals("1")) {
			Btn_add_to_fav.setText("Remove from Favourite list");
			Btn_add_to_fav.setTag(1);
		} else {
			Btn_add_to_fav.setText("Add to Favourite");
			Btn_add_to_fav.setTag(0);
		}

		Btn_add_to_fav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int tag = (Integer) v.getTag();
				if (tag == 0) {
					new AddToFavTask().execute();
					
				} else {
					if (tag == 1) {
						final Dialog dialog = new Dialog(getActivity(),
								R.style.ThemeWithCorners);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.custom_alert_box);
						// dialog.setCancelable(false);
						dialog.show();

						TextView title;
						Button btn_yes;
						Button btn_no;

						btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
						btn_no = (Button) dialog.findViewById(R.id.btn_no);
						title = (TextView) dialog.findViewById(R.id.txt_title);
						
						title.setText("Are you sure you want to remove from favourites list");
						
						title.setTypeface(font);
						btn_yes.setTypeface(font);
						btn_no.setTypeface(font);

						btn_yes.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								// Intent i = new Intent(MainActivity.this,
								// LoginActivity.class);
								// startActivity(i);
								// MainActivity.this.finish();
								new RemoveFavTask().execute();
								dialog.cancel();
							}
						});
						btn_no.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								dialog.cancel();
							}
						});
					}

				}
			}
		});

		return rootview;
	}

	class AddToFavTask extends AsyncTask<String, String, String> {

		// Progress Dialog
		Dialog dialog;
		int success;

		TextView Txt_Title;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);

			Txt_Title = (TextView) dialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Adding to favourites....");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
			dialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", String
						.valueOf(Util.Uid)));
				params.add(new BasicNameValuePair("Spa_Id", selectedSpaDetails
						.get("spa_id")));
				Log.d("request!", "starting" + String.valueOf(Util.Uid) + " "
						+ selectedSpaDetails.get("spa_id"));

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.Add_To_Fav,
						"POST", params);

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

			// Txt_Title.setText("Added to favourites....");
			// SystemClock.sleep(2000);
			dialog.dismiss();
			if (file_url != null) {
				if (success == 1) {
					// Intent myIntent = new Intent(SignUpActivity.this,
					// LoginActivity.class);
					// finish();
					// startActivity(myIntent);
					Btn_add_to_fav.setText("Remove from Favourite list");
					Btn_add_to_fav.setTag(1);
				}
			}

		}

	}

	/**
	 * Remove Frm Favourites
	 */
	class RemoveFavTask extends AsyncTask<String, String, String> {

		// Progress Dialog
		Dialog dialog;
		int success;

		TextView Txt_Title;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);

			Txt_Title = (TextView) dialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Removing...");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
			dialog.show();

		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", String
						.valueOf(Util.Uid)));
				params.add(new BasicNameValuePair("Spa_Id", selectedSpaDetails
						.get("spa_id")));
				Log.d("request!", "starting" + String.valueOf(Util.Uid) + " "
						+ selectedSpaDetails.get("spa_id"));

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.RemoveFavouriteSpa, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Remove Fav Failure!", json.getString(TAG_MESSAGE));
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

			// Txt_Title.setText("Added to favourites....");
			// SystemClock.sleep(2000);
			dialog.dismiss();
			if (file_url != null) {
				if (success == 1) {
					// Intent myIntent = new Intent(SignUpActivity.this,
					// LoginActivity.class);
					// finish();
					// startActivity(myIntent);
					Btn_add_to_fav.setText("Add to favourites");
					Btn_add_to_fav.setTag(0);
				}
			}

		}

	}
}
