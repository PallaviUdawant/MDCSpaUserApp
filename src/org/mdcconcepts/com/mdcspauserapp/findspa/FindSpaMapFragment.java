package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.GPSTracker;
import org.mdcconcepts.com.mdcspauserapp.customitems.ImageLoader;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.todddavies.components.progressbar.ProgressWheel;

public class FindSpaMapFragment extends Fragment implements
		android.location.LocationListener, OnInfoWindowClickListener,
		OnScrollListener {

	private static View rootView;
	Typeface font;
	public GoogleMap google_map;
	LocationManager location_manager;
	Location location;
	double latitude, longitude;
	MarkerOptions marker;
	int count = 0;
	Context context;

	Activity rootActivity;
	GPSTracker gps;
	TextView txt_spa_name;
	TextView txt_addr;
	Spa_Data spa_data;
	TextView txt_distance_time;
	int Selected_Filter;
	private double mLastLatitude;
	private double mLastLongitude;

	private FetchNearestSpa getTenNearestSpaTask;

	// private FetchHighestRatedSpa getHighestRatedSpaTask;
	double myCurrentLocationLat = 0.0;
	double myCurrentLocationlong = 0.0;

	private boolean isloadingNearestSpa = false;
	// private boolean isloadingHighestRatedSpa = false;
	Spinner filter;

	private boolean isNearestDataAvailable = true;
	// private boolean isHighestRatedDataAvailable = true;

	ProgressWheel progressbar_footer;
	ProgressWheel progressbar_header;
	int hit_counter = 0;
	ArrayList<HashMap<String, String>> SpaDetails = new ArrayList<HashMap<String, String>>();
	private ArrayList<Spa_Data> NearestLocations = new ArrayList<Spa_Data>();
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	Dialog dialog;
	private View footer;
	private View header;
	private View infoWindowView;
	int VisiblePosition, distFromTop;
	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	static final String SPA_ID = "spa_id";
	static final String SPA_NAME = "spa_name";
	static final String SPA_Address = "spa_addr";
	static final String SPA_LAT = "spa_lat";
	static final String SPA_LONG = "spa_long";
	static final String SPA_RATING = "spa_rating";
	static final String SPA_LOGO = "spa_logo";
	static final String SPA_COVER_PHOTO = "spa_cover_photo";
	private NearestSpaListviewAdapter adapter;
	ImageLoader imgLoader;

	// private ArrayList<String> ArrayList_Filter = new ArrayList<String>();

	public FindSpaMapFragment() {
		// TODO Auto-generated constructor stub

		// getActivity().getActionBar().hide();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		gps = new GPSTracker(getActivity());
		try {
			rootActivity = getActivity();
			rootView = inflater.inflate(R.layout.fragment_find_spa, container,
					false);
			if (rootView != null) {
				context = rootView.getContext();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		imgLoader = new ImageLoader(rootActivity);
		font = Typeface.createFromAsset(getActivity().getAssets(),
				"Raleway-Light.otf");
		mDrawerLayout = (DrawerLayout) rootView
				.findViewById(R.id.drawer_layout1);
		mDrawerList = (ListView) rootView.findViewById(R.id.list_slidermenu1);

		mDrawerList.setOnScrollListener(this);

		/**
		 * Dialog properties
		 */

		dialog = new Dialog(getActivity(), R.style.ThemeWithCorners);
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

		footer = inflater.inflate(R.layout.footer, null);
		progressbar_footer = (ProgressWheel) footer
				.findViewById(R.id.progressbar_footer);
		progressbar_footer.spin();

		if (header == null) {
			header = inflater.inflate(R.layout.header, null);
			mDrawerList.addHeaderView(header, null, false);
		}

		mDrawerList.addFooterView(footer);

		adapter = new NearestSpaListviewAdapter(getActivity(), SpaDetails);
		mDrawerList.setAdapter(adapter);

		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
				R.drawable.ic_drawer_new, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActivity().getActionBar().setTitle(mTitle);
				getActivity().getActionBar().show();
				// calling onPrepareOptionsMenu() to show action bar icons
				getActivity().invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActivity().getActionBar().setTitle(mDrawerTitle);
				if (getActivity().getActionBar().isShowing())
//					getActivity().getActionBar().hide();
				// calling onPrepareOptionsMenu() to hide action bar icons
				getActivity().invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.openDrawer(Gravity.RIGHT);
		/**
		 * Fetch 10 Nearest Spa's from server
		 */

		getTenNearestSpaTask = new FetchNearestSpa();
		getTenNearestSpaTask.execute();

		mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				@SuppressWarnings("unchecked")
				HashMap<String, String> spaDetails = (HashMap<String, String>) view
						.getTag();
				LatLng locateOnMap = new LatLng(Double.parseDouble(spaDetails
						.get("spa_lat")), Double.parseDouble(spaDetails
						.get("spa_long")));
				String url = getMapsApiDirectionsUrl(new LatLng(mLastLatitude,
						mLastLongitude), locateOnMap);
				DownloadTask downloadTask = new DownloadTask();
				downloadTask.execute(url);
				Util.selectedSpaDetails = spaDetails;
				addMarkers(locateOnMap);
				mDrawerLayout.closeDrawers();
			}
		});
		return rootView;
	}

	public void setTitle(CharSequence title) {
		mTitle = title;
		getActivity().getActionBar().setTitle(mTitle);
	}

	/**
	 * Fetch 10 Nearest Spa
	 * 
	 * @author Pallavi
	 * 
	 */

	public class FetchNearestSpa extends AsyncTask<String, String, String> {

		// private ProgressDialog pDialog;

		int success;
		JSONParser jsonParser = new JSONParser();
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// dialog.show();

		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			SystemClock.sleep(1000);
			isloadingNearestSpa = true;
			try {
				// Building Parameters
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();

				// check if GPS enabled
				if (gps.canGetLocation()) {

					mLastLatitude = gps.getLatitude();
					mLastLongitude = gps.getLongitude();
					do {
						if (mLastLatitude == 0 && mLastLongitude == 0) {
							mLastLatitude = gps.getLatitude();
							mLastLongitude = gps.getLongitude();
						}
						else
						{
							break;
						}
					} while (true);

				}
				Log.d("Sending Lat", String.valueOf(mLastLatitude));
				Log.d("Sending Long", String.valueOf(mLastLongitude));

				params1.add(new BasicNameValuePair("CurrentLat", String
						.valueOf(mLastLatitude)));
				params1.add(new BasicNameValuePair("CurrentLong", String
						.valueOf(mLastLongitude)));
				params1.add(new BasicNameValuePair("hit_counter", String
						.valueOf(hit_counter)));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.get_nearest_ten_spa, "POST", params1);

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

							NearestLocations
									.add(new Spa_Data(Temp
											.getString("Spa_Name"), Temp
											.getString("Spa_Id"), Temp
											.getString("Spa_Lat"), Temp
											.getString("Spa_long"), Temp
											.getString("Addresss"), logo_url,
											cover_url));

							Log.v("NearestLocations",
									NearestLocations.toString());
							SpaDetails.add(spaDetails);

							isNearestDataAvailable = true;
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
			isloadingNearestSpa = false;

			if (file_url != null) {
				if (file_url.contains("Spa Found !")) {
					mDrawerList.setAdapter(adapter);
					mDrawerList.setSelectionFromTop(VisiblePosition,
							distFromTop);
					hit_counter++;

				} else if (file_url.equalsIgnoreCase("false")) {
					/**
					 * Stop loading items if data finished
					 */
					mDrawerList.setOnScrollListener(null);
					mDrawerList.removeFooterView(footer);
					progressbar_footer.stopSpinning();
					isNearestDataAvailable = false;
				} else if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(getActivity(), "Connection TimeOut..!!!",
							Toast.LENGTH_LONG).show();
				}
			}
			initilizeMap();

		}
	}

	/*
	 * public class FetchHighestRatedSpa extends AsyncTask<String, String,
	 * String> {
	 * 
	 * // private ProgressDialog pDialog;
	 * 
	 * int success; JSONParser jsonParser = new JSONParser(); private static
	 * final String TAG_SUCCESS = "success"; private static final String
	 * TAG_MESSAGE = "message";
	 * 
	 * @Override protected void onPreExecute() { // TODO Auto-generated method
	 * stub super.onPreExecute(); // dialog.show();
	 * 
	 * }
	 * 
	 * @Override protected String doInBackground(String... params) { // TODO
	 * Auto-generated method stub SystemClock.sleep(1000);
	 * isloadingHighestRatedSpa = true; try { // Building Parameters
	 * List<NameValuePair> params1 = new ArrayList<NameValuePair>();
	 * 
	 * // check if GPS enabled
	 * 
	 * params1.add(new BasicNameValuePair("Hit_Counter", String
	 * .valueOf(hit_counter)));
	 * 
	 * Log.d("request!", "starting highest rated spa");
	 * 
	 * // Posting user data to script JSONObject json =
	 * jsonParser.makeHttpRequest( Util.getHighestRatedSpa, "POST", params1);
	 * 
	 * Log.d("Hit count", String.valueOf(hit_counter));
	 * 
	 * if (json != null) { // json success element success =
	 * json.getInt(TAG_SUCCESS);
	 * 
	 * if (success == 1) {
	 * 
	 * JSONArray PostJson = json.getJSONArray("posts");
	 * Log.d("Executing Highest Rated ", PostJson.toString()); for (int i = 0; i
	 * < PostJson.length(); i++) {
	 * 
	 * JSONObject Temp = PostJson.getJSONObject(i);
	 * 
	 * HashMap<String, String> spaDetails = new HashMap<String, String>();
	 * spaDetails.put(SPA_ID, Temp.getString("Spa_Id")); spaDetails
	 * .put(SPA_NAME, Temp.getString("Spa_Name")); spaDetails.put(SPA_Address,
	 * Temp.getString("Addresss")); spaDetails.put(SPA_LAT,
	 * Temp.getString("Spa_Lat")); spaDetails .put(SPA_LONG,
	 * Temp.getString("Spa_long")); spaDetails.put(SPA_RATING,
	 * Temp.getString("Total_Rating")); String logo_url =
	 * Temp.getString("Spa_Logo_Url"); if (logo_url.isEmpty()) logo_url =
	 * "http://mdcspa.mdcconcepts.com/spa_logos/spa_logo.jpg"; else logo_url =
	 * logo_url.replace("\\", ""); spaDetails.put(SPA_LOGO, logo_url);
	 * 
	 * String cover_url = Temp .getString("Spa_Cover_Photo_Url"); if
	 * (cover_url.isEmpty()) cover_url =
	 * "http://mdcspa.mdcconcepts.com/spa_cover_photos/sp_profile_cover.png";
	 * else cover_url = cover_url.replace("\\", "");
	 * spaDetails.put(SPA_COVER_PHOTO, cover_url);
	 * 
	 * NearestLocations .add(new Spa_Data(Temp .getString("Spa_Name"), Temp
	 * .getString("Spa_Id"), Temp .getString("Spa_Lat"), Temp
	 * .getString("Spa_long"), Temp .getString("Addresss"), logo_url,
	 * cover_url)); // NearestLocations.add(spa_data);
	 * SpaDetails.add(spaDetails);
	 * 
	 * isHighestRatedDataAvailable = true; }
	 * 
	 * return json.getString(TAG_MESSAGE); } else { Log.d("Login Failure!",
	 * json.getString(TAG_MESSAGE)); return json.getString(TAG_MESSAGE);
	 * 
	 * } } else { return "timeout"; }
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } return null;
	 * 
	 * }
	 * 
	 * /** After completing background task Dismiss the progress dialog
	 * 
	 * protected void onPostExecute(String file_url) { // dismiss the dialog
	 * once product deleted // pDialog.dismiss(); // if (dialog.isShowing()) //
	 * dialog.dismiss(); isloadingHighestRatedSpa = false;
	 * 
	 * if (file_url!=null) { if (file_url.contains("Spa Found !")) {
	 * mDrawerList.setAdapter(adapter);
	 * mDrawerList.setSelectionFromTop(VisiblePosition, distFromTop);
	 * hit_counter++;
	 * 
	 * } else if (file_url.equalsIgnoreCase("false")) { /** Stop loading items
	 * if data finished
	 * 
	 * mDrawerList.setOnScrollListener(null);
	 * mDrawerList.removeFooterView(footer); progressbar_footer.stopSpinning();
	 * isHighestRatedDataAvailable = false; } else if
	 * (file_url.equalsIgnoreCase("timeout")) { Toast.makeText(getActivity(),
	 * "Connection TimeOut..!!!", Toast.LENGTH_LONG).show(); } } //
	 * initilizeMap();
	 * 
	 * } }
	 */

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		int loadedItems = (firstVisibleItem) + visibleItemCount;

		// switch (Selected_Filter) {
		//
		// case 0:
		if (isNearestDataAvailable) {
			if ((loadedItems == totalItemCount) && !isloadingNearestSpa) {

				if (getTenNearestSpaTask != null
						&& (getTenNearestSpaTask.getStatus() == AsyncTask.Status.FINISHED)) {
					getTenNearestSpaTask = new FetchNearestSpa();
					getTenNearestSpaTask.execute();

				}
			}
		} else {
			mDrawerList.setOnScrollListener(null);
			mDrawerList.removeFooterView(footer);
			progressbar_footer.stopSpinning();
			// adapter.notifyDataSetChanged();

		}
		// break;
		// case 1:
		// /**
		// * Select Nearest Spa's On Scroll event- Load 10 items on scroll end
		// */
		//
		// if (isHighestRatedDataAvailable) {
		// if ((loadedItems == totalItemCount)
		// && !isloadingHighestRatedSpa) {
		//
		// if (getHighestRatedSpaTask != null
		// && (getHighestRatedSpaTask.getStatus() == AsyncTask.Status.FINISHED))
		// {
		//
		// getHighestRatedSpaTask = new FetchHighestRatedSpa();
		// getHighestRatedSpaTask.execute();
		//
		// }
		// }
		// } else {
		// mDrawerList.setOnScrollListener(null);
		// mDrawerList.removeFooterView(footer);
		// progressbar_footer.stopSpinning();
		// // adapter.notifyDataSetChanged();
		//
		// }
		//
		// break;
		// }
		/**
		 * When you scroll down and start getting new data store Last Visible
		 * Position in some variables using this method
		 */
		VisiblePosition = mDrawerList.getFirstVisiblePosition();
		View v = mDrawerList.getChildAt(0);
		distFromTop = (v == null) ? 0 : v.getTop();

	}

	/**
	 * Initialize Google Map
	 */
	@SuppressLint("NewApi")
	private void initilizeMap() {
		if (google_map == null) {

			try {
				google_map = ((MapFragment) this.getActivity()
						.getFragmentManager().findFragmentById(R.id.map))
						.getMap();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// check if map is created successfully or not
			if (google_map == null) {
//				Toast.makeText(this.getActivity(),
//						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
//						.show();
			} else {

				// check if GPS enabled
				if (gps.canGetLocation()) {

					mLastLatitude = gps.getLatitude();
					mLastLongitude = gps.getLongitude();
					Log.d("latitude", "" + mLastLatitude);
					Log.d("longitude", "" + mLastLongitude);

				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}

				google_map.setMyLocationEnabled(true);
				// getLocation();

				marker = new MarkerOptions().position(
						new LatLng(mLastLatitude, mLastLongitude)).title(
						"Current Location");

				MarkerOptions options = new MarkerOptions();
				options.position(new LatLng(mLastLatitude, mLastLongitude));

				google_map.setOnInfoWindowClickListener(this);

				/*** ZoomIn ****/

				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(new LatLng(mLastLatitude, mLastLongitude))
						.zoom(16) // Sets the zoom
						.bearing(0) // Sets the orientation of the camera to
									// east
						.tilt(90) // Sets the tilt of the camera to 30 degrees
						.build();
				google_map.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));

				/**
				 * Custom InfoWindow Adapter
				 */
				google_map.setInfoWindowAdapter(new InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker marker) {
						// TODO Auto-generated method stub

						if (marker != null && marker.isInfoWindowShown()) {
							marker.hideInfoWindow();
							marker.showInfoWindow();
						}
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						// TODO Auto-generated method stub
						infoWindowView = rootActivity.getLayoutInflater()
								.inflate(R.layout.custom_info_window, null);

						double search_lat, search_long;
						search_lat = marker.getPosition().latitude;
						search_long = marker.getPosition().longitude;

						// search_lat=Math.round(search_lat,6);
						// search_long=Math.round(search_long ,6);
						DecimalFormat df = new DecimalFormat("#.000000");
						search_lat = Double.parseDouble(df.format(search_lat));
						search_long = Double.parseDouble(df.format(search_long));

						spa_data = searchDetails(search_lat, search_long);

						// Log.d("Spa Address", spa_data.Spa_Address);

						ImageView spa_logo_info_window = (ImageView) infoWindowView
								.findViewById(R.id.spa_logo_info_window1);

						imgLoader.DisplayImage(spa_data.Spa_Logo_Url,
								R.id.spa_logo_info_window1,
								spa_logo_info_window);

						// SystemClock.sleep(5000);
						// options.position(newLatLong);

						font = Typeface.createFromAsset(getActivity()
								.getAssets(), "Raleway-Light.otf");
						txt_spa_name = (TextView) infoWindowView
								.findViewById(R.id.txt_spa_name1);
						txt_distance_time = (TextView) infoWindowView
								.findViewById(R.id.txt_distance_time);
						txt_addr = (TextView) infoWindowView
								.findViewById(R.id.txt_address);

						//

						txt_spa_name.setTypeface(font);
						txt_addr.setTypeface(font);
						txt_distance_time.setTypeface(font);
						txt_spa_name.setText(spa_data.Spa_Name);
						txt_addr.setText(spa_data.Spa_Address);

						Log.v("InfoWindow Url", spa_data.Spa_Logo_Url);
						// Toast.makeText(getActivity(), spa_data.Spa_Logo_Url,
						// Toast.LENGTH_LONG).show();
						// ImageLoader imgLoader = new
						// ImageLoader(getActivity());
						// imgLoader.DisplayImage(spa_data.Spa_Logo_Url,
						// R.id.spa_logo_info_window, spa_logo_info_window);

						/*
						 * String url = getMapsApiDirectionsUrl(new LatLng(
						 * mLastLatitude, mLastLongitude), newLatLong); ReadTask
						 * downloadTask = new ReadTask();
						 * downloadTask.execute(url);
						 */

						// txt_distance_time.setText(Util.DISTANCE);
						return infoWindowView;
					}
				});

				// addMarkers();

			}

		}
	}

	/**
	 * Add Nearest 10 Markers on the map
	 */
	private void addMarkers(LatLng newLatLong) {
		if (google_map != null) {
			google_map.addMarker(
					new MarkerOptions().position(newLatLong).icon(
							BitmapDescriptorFactory
									.fromResource(R.drawable.ic_marker)))
					.showInfoWindow();
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(newLatLong).zoom(17) // Sets the zoom
					.bearing(0) // Sets the orientation of the camera to east
					.tilt(90) // Sets the tilt of the camera to 30 degrees
					.build();
			google_map.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			// google_map.addMarker(new MarkerOptions().position(
			// new LatLng(mLastLatitude, mLastLongitude)).title(
			// "Current Location"));
			// LatLng newLatLong;
			// double newLat, newLong;
			// for (int i = 0; i < NearestLocations.size(); i++) {
			// newLat = Double.parseDouble(NearestLocations.get(i).Spa_Lat);
			// newLong = Double.parseDouble(NearestLocations.get(i).Spa_Long);
			// newLatLong = new LatLng(newLat, newLong);
			// google_map.addMarker(new MarkerOptions().position(newLatLong)
			// .icon(BitmapDescriptorFactory
			// .fromResource(R.drawable.ic_marker)));
			//
			// }

		}
	}

	/**
	 * Find the details of the Spa on Marker Click event.
	 * 
	 * @param searchLat
	 * @param searchLong
	 * @return Spa_Data object
	 */
	public Spa_Data searchDetails(double searchLat, double searchLong) {
		double l1, l2;
		for (int i = 0; i < NearestLocations.size(); i++) {
			l1 = Double.parseDouble(NearestLocations.get(i).Spa_Lat);
			l2 = Double.parseDouble(NearestLocations.get(i).Spa_Long);

			if (l1 == searchLat && l2 == searchLong) {

				Log.d("l1",
						NearestLocations.get(i).Spa_Lat + " "
								+ String.valueOf(searchLat));
				Log.d("l2",
						NearestLocations.get(i).Spa_Long + " "
								+ String.valueOf(searchLong));

				Log.d("l2", NearestLocations.get(i).Spa_Name + " "
						+ NearestLocations.get(i).Spa_Address);

				return NearestLocations.get(i);
			}

		}
		return null;

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param source
	 *            :- Start Point(LatLong)
	 * @param destination
	 *            :- Destination(LatLong)
	 * @return String:- Url from where points to draw route will be drawn
	 */

	private String getMapsApiDirectionsUrl(LatLng source, LatLng destination) {

		// Origin of route
		String str_origin = "origin=" + source.latitude + ","
				+ source.longitude;

		// Destination of route
		String str_dest = "destination=" + destination.latitude + ","
				+ destination.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
				// Log.v("downloaded data", data);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);
		}
	}

	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}
		return data;
	}

	/**
	 * Download points to draw route
	 * 
	 * @author Pallavi
	 * 
	 */

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {
			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = new PolylineOptions();
			String duration = "";
			String distance = "";
			LatLng position = null;
			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				// polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);
				Log.d("HaspMap", path.toString());
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					if (j == 0) { // Get distance from the list
						distance = (String) point.get("distance");
						continue;
					} else if (j == 1) { // Get duration from the list
						duration = (String) point.get("duration");
						continue;
					}

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					position = new LatLng(lat, lng);
					// LatLng position = new LatLng(lat, lng);
					// Get duration from the list

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(5);
				polyLineOptions.color(Color.BLUE);
				switch (count) {
				case 0:
					polyLineOptions.color(Color.BLUE);
					count++;
					break;
				case 1:
					polyLineOptions.color(Color.RED);
					count++;
					break;
				case 2:
					polyLineOptions.color(Color.GREEN);
					count++;
					break;
				case 3:
					polyLineOptions.color(Color.BLACK);
					count = 0;
					break;

				}

			}
			google_map.addPolyline(polyLineOptions);
			txt_addr = (TextView) infoWindowView.findViewById(R.id.txt_address);

			Util.DISTANCE = "Distance " + distance + " "
					+ "\nEstimated time taken to reach " + duration;
			txt_distance_time.setText("Distance " + distance + " "
					+ "Estimated time taken to reach" + duration);
//			Toast.makeText(getActivity(),
//					"Duration" + duration + " " + "Distance" + distance,
//					Toast.LENGTH_LONG).show();
			// google_map.clear();
			// google_map
			// .addMarker(new MarkerOptions().position(position).icon(
			// BitmapDescriptorFactory
			// .fromResource(R.drawable.ic_marker))).showInfoWindow();
		}
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		// Toast.makeText(getActivity(), "Onclick", Toast.LENGTH_LONG).show();

		Intent i = new Intent(getActivity(), SpaProfileActivity.class);

		if (Util.selectedSpaDetails.get("Spa_Id") == spa_data.Spa_Id)
			;
		i.putExtra("SelectedSpDetails", Util.selectedSpaDetails);
		// i.putExtra("Spa_Data", )
		// i.putExtra("Spa_Name", spa_data.Spa_Name);
		// i.putExtra("Spa_Id", spa_data.Spa_Id);
		// i.putExtra("Spa_Addr", spa_data.Spa_Address);
		startActivity(i);
		getActivity(). overridePendingTransition(R.anim.fade_in
				, R.anim.fade_out);

	}

	/**
	 * Clear Map
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (google_map != null)
			google_map.clear();
		if (mDrawerList.getHeaderViewsCount() > 0)
			mDrawerList.removeHeaderView(header);
		if (mDrawerList.getFooterViewsCount() > 0)
			mDrawerList.removeFooterView(footer);
		mDrawerList.setAdapter(null);
	}
}
