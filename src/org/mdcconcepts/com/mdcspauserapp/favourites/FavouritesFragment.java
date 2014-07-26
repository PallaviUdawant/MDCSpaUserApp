package org.mdcconcepts.com.mdcspauserapp.favourites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.findspa.NearestSpaListviewAdapter;
import org.mdcconcepts.com.mdcspauserapp.findspa.SpaProfileActivity;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

@SuppressLint("InflateParams")
public class FavouritesFragment extends Activity implements OnScrollListener {

	// View rootView;
	ListView listView_controller_favourite;
	private boolean isloading = false;
	static final String SPA_ID = "spa_id";
	static final String SPA_NAME = "spa_name";
	static final String SPA_Address = "spa_addr";
	static final String SPA_LAT = "spa_lat";
	static final String SPA_LONG = "spa_long";
	static final String SPA_RATING = "spa_rating";
	static final String SPA_LOGO = "spa_logo";
	static final String SPA_COVER_PHOTO = "spa_cover_photo";
	private View footer;
	private GetFavouriteSpa getFavouriteSpaTask;
	Typeface font;
	private boolean isDataAvailable = true;
	ProgressWheel progressbar_footer;
	int hit_counter = 0;
	int VisiblePosition, distFromTop;
	private NearestSpaListviewAdapter adapter;
	ArrayList<HashMap<String, String>> SpaDetails = new ArrayList<HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_favourites);
		// TODO Auto-generated method stub

		// rootView = inflater.inflate(R.layout.fragment_favourites, null);
		listView_controller_favourite = (ListView) findViewById(R.id.listView_controller_favourite);

		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");

		listView_controller_favourite.setOnScrollListener(this);

		footer = ((LayoutInflater) FavouritesFragment.this
				.getSystemService(FavouritesFragment.LAYOUT_INFLATER_SERVICE))
				.inflate(R.layout.footer, null);

		TextView txt_footer_text = (TextView) footer
				.findViewById(R.id.txt_footer_text);
		txt_footer_text.setTypeface(font);
		progressbar_footer = (ProgressWheel) footer
				.findViewById(R.id.progressbar_footer);
		progressbar_footer.spin();

		listView_controller_favourite.addFooterView(footer);
		adapter = new NearestSpaListviewAdapter(this, SpaDetails);
		listView_controller_favourite.setAdapter(adapter);
		getFavouriteSpaTask = new GetFavouriteSpa();
		getFavouriteSpaTask.execute();

		listView_controller_favourite
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub

						@SuppressWarnings("unchecked")
						HashMap<String, String> spaDetails = (HashMap<String, String>) view
								.getTag();
						Intent i = new Intent(FavouritesFragment.this,
								SpaProfileActivity.class);
						i.putExtra("SelectedSpDetails", spaDetails);
						startActivity(i);
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
	class GetFavouriteSpa extends AsyncTask<String, String, String> {

		int success;
		JSONParser jsonParser = new JSONParser();
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";
		Dialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// dialog.show();
			dialog = new Dialog(FavouritesFragment.this,
					R.style.ThemeWithCorners);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.custom_progress_dialog);
			dialog.setCancelable(false);

			TextView Txt_Title = (TextView) dialog
					.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);
			Txt_Title.setText("Fetching data....");

			ProgressWheel pw_four = (ProgressWheel) dialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			SystemClock.sleep(1000);
			isloading = true;
			try {
				// Building Parameters
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				params1.add(new BasicNameValuePair("Uid", String
						.valueOf(Util.Uid)));
				params1.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.GetFavouriteSpa, "POST", params1);

				Log.d("Hit count", String.valueOf(hit_counter));

				if (json != null) {
					// json success element
					success = json.getInt(TAG_SUCCESS);

					if (success == 1) {

						JSONArray PostJson = json.getJSONArray("posts");
						Log.d("Executing Nearest 10 ", PostJson.toString());
						for (int i = 0; i < PostJson.length(); i++) {

							JSONObject Temp = PostJson.getJSONObject(i);

							HashMap<String, String> spaDetails = new HashMap<String, String>();
							spaDetails.put(SPA_ID, Temp.getString("Spa_Id"));
							spaDetails
									.put(SPA_NAME, Temp.getString("Spa_Name"));
							spaDetails.put(SPA_Address,
									Temp.getString("Addresss"));
							spaDetails.put(SPA_LAT, Temp.getString("Spa_Lat"));
							spaDetails
									.put(SPA_LONG, Temp.getString("Spa_long"));
							spaDetails.put(SPA_RATING,
									Temp.getString("Total_Rating"));

							String logo_url = Temp.getString("Spa_Logo_Url");
							if (logo_url.isEmpty())
								logo_url = "http://mdcspa.mdcconcepts.com/spa_logos/spa_logo.jpg";
							else
								logo_url = logo_url.replace("\\", "");
							spaDetails.put(SPA_LOGO, logo_url);

							String cover_url = Temp
									.getString("Spa_Cover_Photo_Url");
							if (cover_url.isEmpty())
								cover_url = "http://mdcspa.mdcconcepts.com/spa_cover_photos/sp_profile_cover.png";
							else
								cover_url = cover_url.replace("\\", "");
							spaDetails.put(SPA_COVER_PHOTO, cover_url);
							SpaDetails.add(spaDetails);
							isDataAvailable = true;
						}

						return json.getString(TAG_MESSAGE);
					} else {
						Log.d("Login Failure!", json.getString(TAG_MESSAGE));
						return json.getString(TAG_MESSAGE);
					}
				} else {
					return "timeout";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			// pDialog.dismiss();
			if (dialog.isShowing())
				dialog.dismiss();
			isloading = false;

			if (file_url != null) {
				if (file_url.contains("Spa Found !")) {
					listView_controller_favourite.setAdapter(adapter);
					listView_controller_favourite.setSelectionFromTop(
							VisiblePosition, distFromTop);
					hit_counter++;

				} else if (file_url.equalsIgnoreCase("false")) {
					/**
					 * Stop loading items if data finished
					 */
					listView_controller_favourite.setOnScrollListener(null);
					listView_controller_favourite.removeFooterView(footer);
					progressbar_footer.stopSpinning();
					isDataAvailable = false;
				} else if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(FavouritesFragment.this,
							"Connection TimeOut..!!!", Toast.LENGTH_LONG)
							.show();
				}
			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int loadedItems = (firstVisibleItem) + visibleItemCount;
		if (isDataAvailable) {
			if ((loadedItems == totalItemCount) && !isloading) {

				if (getFavouriteSpaTask != null
						&& (getFavouriteSpaTask.getStatus() == AsyncTask.Status.FINISHED)) {
					getFavouriteSpaTask = new GetFavouriteSpa();
					getFavouriteSpaTask.execute();

				}
			}
		} else {
			listView_controller_favourite.setOnScrollListener(null);
			listView_controller_favourite.removeFooterView(footer);
			progressbar_footer.stopSpinning();
			// adapter.notifyDataSetChanged();

		}
		VisiblePosition = listView_controller_favourite
				.getFirstVisiblePosition();
		View v = listView_controller_favourite.getChildAt(0);
		distFromTop = (v == null) ? 0 : v.getTop();
	}
}
