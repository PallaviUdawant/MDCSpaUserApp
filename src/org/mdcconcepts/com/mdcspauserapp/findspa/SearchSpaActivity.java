package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.GPSTracker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class SearchSpaActivity extends FragmentActivity implements
		android.location.LocationListener, OnInfoWindowClickListener {

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
	HashMap<String, String> spaDetails;
	private double mLastLatitude;
	private double mLastLongitude;

	double myCurrentLocationLat = 0.0;
	double myCurrentLocationlong = 0.0;
	private ArrayList<Spa_Data> NearestLocations = new ArrayList<Spa_Data>();

	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_find_spa);

		Intent i = getIntent();
		spaDetails = (HashMap<String, String>) i
				.getSerializableExtra("spaDetails");

		gps = new GPSTracker(SearchSpaActivity.this);

		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");
		
		initilizeMap();
	}

	/**
	 * Initialize Google Map
	 */
	@SuppressLint("NewApi")
	private void initilizeMap() {
		if (google_map == null) {

			try {
				google_map = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// check if map is created successfully or not
			if (google_map == null) {
				Toast.makeText(SearchSpaActivity.this,
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			} else {

				// mLastLatitude=spaDetails.get("spa_lat");

				// // check if GPS enabled
				// if (gps.canGetLocation()) {
				//
				// // mLastLatitude = gps.getLatitude();
				// // mLastLongitude = gps.getLongitude();
				//
				// mLastLatitude
				// // \n is for new line
				// // Toast.makeText(
				// // getActivity(),
				// // "Your Location is - \nLat: " + mLastLatitude
				// // + "\nLong: " + mLastLongitude, Toast.LENGTH_LONG)
				// // .show();
				// Log.d("latitude", "" + mLastLatitude);
				// Log.d("longitude", "" + mLastLongitude);
				//
				// } else {
				// // can't get location
				// // GPS or Network is not enabled
				// // Ask user to enable GPS/network in settings
				// gps.showSettingsAlert();
				// }
				mLastLatitude = Double.parseDouble(spaDetails.get("spa_lat"));
				mLastLongitude = Double.parseDouble(spaDetails.get("spa_long"));
				google_map.setMyLocationEnabled(true);
				// getLocation();
				Toast.makeText(getApplicationContext(),String.valueOf(mLastLatitude), Toast.LENGTH_LONG).show();
				marker = new MarkerOptions().position(new LatLng(Double
						.parseDouble(spaDetails.get("spa_lat")), Double
						.parseDouble(spaDetails.get("spa_long"))));

				MarkerOptions options = new MarkerOptions();
				options.position(new LatLng(mLastLatitude, mLastLongitude));

				/*
				 * for (int i = 0; i < NearestLocations.size(); i++) { newLat =
				 * Double .parseDouble(NearestLocations.get(i).Spa_Lat); newLong
				 * = Double .parseDouble(NearestLocations.get(i).Spa_Long);
				 * newLatLong = new LatLng(newLat, newLong);
				 * 
				 * // options.position(newLatLong);
				 * 
				 * String url = getMapsApiDirectionsUrl(new LatLng(
				 * mLastLatitude, mLastLongitude), newLatLong); ReadTask
				 * downloadTask = new ReadTask(); downloadTask.execute(url);
				 * 
				 * }
				 */

				google_map.setOnInfoWindowClickListener(this);

				/*** ZoomIn ****/

				google_map.animateCamera(CameraUpdateFactory.newLatLngZoom(
						new LatLng(mLastLatitude, mLastLongitude), 15));

				/**
				 * Custom InfoWindow Adapter
				 */
				google_map.setInfoWindowAdapter(new InfoWindowAdapter() {

					@Override
					public View getInfoWindow(Marker arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public View getInfoContents(Marker marker) {
						// TODO Auto-generated method stub
						View v = rootActivity.getLayoutInflater().inflate(
								R.layout.custom_info_window, null);

						txt_spa_name = (TextView) v
								.findViewById(R.id.txt_spa_name);

						txt_addr = (TextView) v.findViewById(R.id.txt_address);

						txt_spa_name.setTypeface(font);
						txt_addr.setTypeface(font);

						double search_lat, search_long;
						search_lat = marker.getPosition().latitude;
						search_long = marker.getPosition().longitude;

						// search_lat=Math.round(search_lat,6);
						// search_long=Math.round(search_long ,6);
						DecimalFormat df = new DecimalFormat("#.000000");
						search_lat = Double.parseDouble(df.format(search_lat));
						search_long = Double.parseDouble(df.format(search_long));

						spa_data = searchDetails(search_lat, search_long);

						txt_spa_name.setText(spa_data.Spa_Name);
						txt_addr.setText(spa_data.Spa_Address);

						Log.d("Spa Address", spa_data.Spa_Address);
						LatLng newLatLong;
						double newLat, newLong;
						newLat = Double.parseDouble(spa_data.Spa_Lat);
						newLong = Double.parseDouble(spa_data.Spa_Long);
						newLatLong = new LatLng(newLat, newLong);

						// options.position(newLatLong);

						String url = getMapsApiDirectionsUrl(new LatLng(
								mLastLatitude, mLastLongitude), newLatLong);
						ReadTask downloadTask = new ReadTask();
						downloadTask.execute(url);

						return v;
					}
				});

				// google_map.setOnMapClickListener(new OnMapClickListener() {
				//
				// public void onMapClick(LatLng arg0) {
				// // TODO Auto-generated method stub
				// google_map.clear();
				// MarkerOptions markerOptions = new MarkerOptions();
				// markerOptions.position(arg0);
				// google_map.animateCamera(CameraUpdateFactory.newLatLng(arg0));
				// Marker marker = google_map.addMarker(markerOptions);
				// marker.showInfoWindow();
				// }
				// });

				addMarkers(new LatLng(Double
						.parseDouble(spaDetails.get("spa_lat")), Double
						.parseDouble(spaDetails.get("spa_long"))));

				// Fragment fragment = (getFragmentManager()
				// .findFragmentById(R.id.map));
				// if (fragment != null) {
				//
				// // FragmentTransaction ft =
				// getActivity().getFragmentManager()
				// // .beginTransaction();
				// // // fragment= new FindSpaFragment();
				// // // ft.add(R.id.map, fragment);
				// // // ft.replace(R.id.map, sp);
				// // // ft.commit();
				// // Toast.makeText(rootActivity, "fragment not null",
				// // Toast.LENGTH_LONG).show();
				// // ft.remove(fragment);
				// // ft.commit();}
				// } else {
				// Toast.makeText(rootActivity, "fragment is null",
				// Toast.LENGTH_LONG).show();
				// }
			}

		}
	}

	/**
	 * Add Nearest 10 Markers on the map
	 */
	private void addMarkers(LatLng latLong) {
		if (google_map != null) {
			// google_map.addMarker(new MarkerOptions().position(
			// new LatLng(mLastLatitude, mLastLongitude)).title(
			// "Current Location"));
			LatLng newLatLong;
			double newLat, newLong;
			for (int i = 0; i < NearestLocations.size(); i++) {
				newLat = Double.parseDouble(NearestLocations.get(i).Spa_Lat);
				newLong = Double.parseDouble(NearestLocations.get(i).Spa_Long);
				newLatLong = new LatLng(newLat, newLong);
				google_map.addMarker(new MarkerOptions().position(newLatLong)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_marker)));

			}

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

	/**
	 * 
	 * @param source
	 *            :- Start Point(LatLong)
	 * @param destination
	 *            :- Destination(LatLong)
	 * @return String:- Url from where points to draw route will be drawn
	 */

	private String getMapsApiDirectionsUrl(LatLng source, LatLng destination) {

		String waypoints = "waypoints=optimize:true|" + +source.latitude + ","
				+ source.longitude + "|" + destination.latitude + ","
				+ destination.longitude;
		// + "|"+STREET1.latitude + ","+ STREET1.longitude;
		String sensor = "sensor=false";
		String params = waypoints + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		return url;
	}

	/**
	 * Download points to draw route
	 * 
	 * @author Pallavi
	 * 
	 */
	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
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

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				// polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

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
		Intent i = new Intent(SearchSpaActivity.this,
				Spa_Details_Activity.class);
		i.putExtra("Spa_Name", spa_data.Spa_Name);
		i.putExtra("Spa_Id", spa_data.Spa_Id);
		startActivity(i);

	}

	/**
	 * F Clear Map
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		google_map.clear();
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

}
