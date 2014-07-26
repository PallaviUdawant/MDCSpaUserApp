package org.mdcconcepts.com.mdcspauserapp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.customitems.ConnectionDetector;
import org.mdcconcepts.com.mdcspauserapp.customitems.GPSTracker;
import org.mdcconcepts.com.mdcspauserapp.favourites.FavouritesFragment;
import org.mdcconcepts.com.mdcspauserapp.findspa.FindSpaMapFragment;
import org.mdcconcepts.com.mdcspauserapp.giftcard.GiftCardFragment;
import org.mdcconcepts.com.mdcspauserapp.navigation.NavDrawerItem;
import org.mdcconcepts.com.mdcspauserapp.navigation.NavDrawerListAdapter;
import org.mdcconcepts.com.mdcspauserapp.profile.MyProfileFragment;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.setting.SettingActivity;
import org.mdcconcepts.com.mdcspauserapp.signup.SignUpActivity;
import org.mdcconcepts.com.mdcspauserapp.util.Util;
import org.mdcconcepts.com.mdcspauserapp.viewappointments.ViewAppointmentFragment;
import org.mdcconcepts.com.mdcspauserapp.wishlist.WishList_Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.todddavies.components.progressbar.ProgressWheel;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	JSONParser jsonParser = new JSONParser();
	Typeface font;
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	SharedPreferences sharedPreferences;
	Dialog loginDialog;
	EditText EditText_Controller_Username_Login;
	EditText EditText_Controller_Password_Login;
	TextView textView_controller_incorrect_credentials;
	ProgressWheel progressBar_Controller_Login;
	Editor editor;
	public Menu APP_MENU;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		font = Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");
		cd = new ConnectionDetector(getApplicationContext());

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// File Spa
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));

		// View an appointment
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1), true, "22"));

		// Notifications
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));

		// Send Gift Card
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1), true, "50+"));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons
				.getResourceId(6, -1)));

		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer_new, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				getActionBar().show();
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
//				getActionBar().hide();
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		ImageView view = (ImageView) findViewById(android.R.id.home);
		view.setPadding(10, 0, 0, 10);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

		sharedPreferences = getSharedPreferences(Util.APP_PREFERENCES,
				Context.MODE_PRIVATE);
		// boolean isFirstRun = sharedPreferences.getBoolean("FIRSTRUN", true);
		try {
			Util.Uid = Integer.parseInt(sharedPreferences.getString("Uid", ""));
			Log.d("User Uid Value", "" + Util.Uid);
		} catch (Exception e) {
			// TODO: handle exception
			Log.d("User Uid Value", "it is not set now");
		}

		// if (isFirstRun) {
		// // Code to run once
		// SharedPreferences.Editor editor = sharedPreferences.edit();
		// editor.putBoolean("FIRSTRUN", false);
		// editor.commit();
		//
		// Intent i = new Intent(MainActivity.this, InjuriesActivityMain.class);
		// startActivity(i);
		//
		// }

		// new GetUserData().execute();
		
		ImageView view1 = (ImageView) findViewById(android.R.id.home);
		view1.setPadding(10, 10, 10, 10);
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if (getLayoutInflater().getFactory() == null)
			setMenuBackground();
		SharedPreferences sharedPreferences = getSharedPreferences(
				Util.APP_PREFERENCES, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);
		APP_MENU = menu;

		if (!isLogin) {
			APP_MENU.findItem(R.id.action_profile).setVisible(false);
			APP_MENU.findItem(R.id.action_logout).setVisible(false);
			APP_MENU.findItem(R.id.action_Login).setVisible(true);

		} else {
			APP_MENU.findItem(R.id.action_profile).setVisible(true);
			APP_MENU.findItem(R.id.action_logout).setVisible(true);
			APP_MENU.findItem(R.id.action_Login).setVisible(false);
		}

		return true;
	}

	@SuppressLint("CommitPrefEdits")
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		Log.d("Iteam ID", String.valueOf(item.getTitle()));

		/**
		 * Condition For Login Menu Iteam Click
		 */
		if (item.getTitle().equals("Login")) {
			SharedPreferences sharedPreferences = getSharedPreferences(
					Util.APP_PREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);

			if (!isLogin) {
				open_LoginPopUp(1);
			} else {
				Intent intent = new Intent(this, FavouritesFragment.class);
				startActivity(intent);
			}
		}

		/**
		 * Condition For Favorite SPA Menu Item Click
		 */
		if (item.getTitle().equals("Favourite SPA")) {
			SharedPreferences sharedPreferences = getSharedPreferences(
					Util.APP_PREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);

			if (!isLogin) {
				open_LoginPopUp(2);
			} else {
				Intent intent = new Intent(this, FavouritesFragment.class);
				startActivity(intent);
			}
		}

		/**
		 * Condition For Help Menu Item Click
		 */
		if (item.getTitle().equals("Help")) {
		}
		/**
		 * Condition For User Profile Menu Item Click
		 */
		if (item.getTitle().equals("Profile")) {
			SharedPreferences sharedPreferences = getSharedPreferences(
					Util.APP_PREFERENCES, Context.MODE_PRIVATE);
			editor = sharedPreferences.edit();
			boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);

			if (!isLogin) {
				open_LoginPopUp(2);
			} else {
				Intent intent = new Intent(this, MyProfileFragment.class);
				startActivity(intent);
			}
		}
		/**
		 * Condition For Settings Menu Item Click
		 */
		if (item.getTitle().equals("Settings")) {
//			SharedPreferences sharedPreferences = getSharedPreferences(
//					Util.APP_PREFERENCES, Context.MODE_PRIVATE);
//			editor = sharedPreferences.edit();
//			boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);
//
//			if (!isLogin) {
//				open_LoginPopUp(3);
//			} else {
				Intent intent = new Intent(this, SettingActivity.class);
				startActivity(intent);
//			}
		}

		/**
		 * Condition For Logout Menu Item Click
		 */
		if (item.getTitle().equals("Logout")) {
			editor.putBoolean("IsLogin", false);
			editor.commit();

			APP_MENU.findItem(R.id.action_profile).setVisible(false);
			APP_MENU.findItem(R.id.action_logout).setVisible(false);
			APP_MENU.findItem(R.id.action_Login).setVisible(true);

		}
		// // Handle action bar actions click
		// switch (item.getItemId()) {
		// case R.id.action_settings:
		// return true;
		// case R.id.action_fav:
		// SharedPreferences sharedPreferences = getSharedPreferences(
		// Util.APP_PREFERENCES, Context.MODE_PRIVATE);
		// editor = sharedPreferences.edit();
		// boolean isLogin = sharedPreferences.getBoolean("IsLogin", false);
		//
		// if (!isLogin) {
		// open_LoginPopUp(1);
		// } else {
		// Intent intent = new Intent(this, FavouritesFragment.class);
		// startActivity(intent);
		// }
		//
		// return true;
		// default:
		// return super.onOptionsItemSelected(item);
		// }
		return true;
	}

	protected void setMenuBackground() {

		// Log.d("in", "Enterting setMenuBackGround");

		getLayoutInflater().setFactory(new Factory() {

			@Override
			public View onCreateView(String name, Context context,
					AttributeSet attrs) {
				// Log.d("Enterting onCreateView", name);
				LayoutInflater f = getLayoutInflater();
				if (name.equalsIgnoreCase("TextView")) {

					try { // Ask our inflater to create the view

						final View view = f.createView(name, null, attrs);
						/*
						 * The background gets refreshed each time a new item is
						 * added the options menu. So each time Android applies
						 * the default background we need to set our own
						 * background. This is done using a thread giving the
						 * background change as runnable object
						 */
						new Handler().post(new Runnable() {
							public void run() {
								// ((TextView)
								// view).setTextColor(Color.parseColor("#4e3115"));
								((TextView) view).setTypeface(font);
							}
						});
						return view;
					} catch (InflateException e) {
					} catch (ClassNotFoundException e) {
					}
				}
				return null;
			}
		});
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		isInternetPresent = cd.isConnectingToInternet();
		switch (position) {
		case 0:
			// Home Fragment
			fragment = new HomeFragment();
			break;

		// case 1:
		// //Profile Fragment
		// fragment = new MyProfileFragment();
		// break;

		case 1:

			// Find Spa Fragment
			/**
			 * Check if Gps is On
			 */

			if (isInternetPresent) {
				GPSTracker gps = new GPSTracker(this);
				getActionBar().hide();
				if (gps.canGetLocation()) {

					fragment = new FindSpaMapFragment();

				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}
			} else {
				showAlertDialog(MainActivity.this, "No Internet Connection",
						"You don't have internet connection.", false);
			}
			break;

		case 2:
			// View Appointment
			fragment = new ViewAppointmentFragment();
			// fragment = new MakeAppointmentFragment();
			break;

		case 3:
			// Notifications
			fragment = new HomeFragment();
			break;

		case 4:
			// Send Gift card
			fragment = new GiftCardFragment();
			// fragment = new WhatsHotFragment();
			break;

		// case 6:
		// //Favourites
		// fragment = new FavouritesFragment();
		//
		// break;

		case 5:
			// Offers
			fragment = new HomeFragment();
			break;
		 case 6:
		 //Settings
		 fragment = new WishList_Fragment();
		 break;
		// case 9:
		// //Logout
		// Intent i = new Intent(MainActivity.this, LoginActivity.class);
		// startActivity(i);
		// finish();
		// break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

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

	

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		/**
		 * Ask before leaving app
		 */
		final Dialog dialog = new Dialog(MainActivity.this,
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
				MainActivity.this.finish();

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

	class LoginUser extends AsyncTask<String, String, String> {

		int whichMenuIteam;

		// private Dialog dialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		public LoginUser(int whichMenuIteam) {
			this.whichMenuIteam = whichMenuIteam;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			// dialog = new Dialog(FinalMakeAppointmentActivity.this,
			// R.style.ThemeWithCorners);
			// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			// dialog.setContentView(R.layout.custom_progress_dialog);
			// dialog.setCancelable(false);
			// // dialog.show();
			//
			// TextView Txt_Title = (TextView) dialog
			// .findViewById(R.id.txt_alert_text);
			// Txt_Title.setTypeface(font);
			// /**
			// * custom circular progress bar
			// */
			// ProgressWheel pw_four = (ProgressWheel) dialog
			// .findViewById(R.id.progressBarFour);
			// pw_four.spin();
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
			
			progressBar_Controller_Login.setVisibility(View.GONE);
			progressBar_Controller_Login.stopSpinning();
			if (file_url != null) {
				// Toast.makeText(LoginActivity.this, file_url,
				// Toast.LENGTH_LONG)
				// .show();
				if (success == 1) {
					loginDialog.cancel();
					editor.putBoolean("IsLogin", true);
					editor.putString("UserName",
							EditText_Controller_Username_Login.getText()
									.toString());
					editor.putString("Password",
							EditText_Controller_Password_Login.getText()
									.toString());
					editor.putString("Uid", "" + Util.Uid);
					editor.commit();
					

					APP_MENU.findItem(R.id.action_profile).setVisible(true);
					APP_MENU.findItem(R.id.action_logout).setVisible(true);
					APP_MENU.findItem(R.id.action_Login).setVisible(false);
					Intent intent;
					switch (whichMenuIteam) {
					
					case 2:
						 intent = new Intent(MainActivity.this,
								FavouritesFragment.class);
						startActivity(intent);
						break;
					case 3:
						 intent = new Intent(MainActivity.this,
								SettingActivity.class);
						startActivity(intent);
						break;
					default:
						break;
					}
					// Intent intent = new Intent(MainActivity.this,
					// FavouritesFragment.class);
					// startActivity(intent);

					// Intent myIntent = new Intent(
					// FinalMakeAppointmentActivity.this,
					// MainActivity.class);
					// finish();
					// startActivity(myIntent);
				} else if (file_url.equalsIgnoreCase("timeout")) {
					Toast.makeText(
							MainActivity.this,
							"Connection TimeOut..!!! Please try again later..!!!",
							Toast.LENGTH_LONG).show();
					loginDialog.cancel();
				} else {

					textView_controller_incorrect_credentials
							.setVisibility(View.VISIBLE);
				
					// textView_controller_incorrect_credentials.setTypeface(font);
					// Toast.makeText(FinalMakeAppointmentActivity.this,
					// "Wrong Username or Password.. Please try again..!!!",
					// Toast.LENGTH_LONG).show();
				}
			}

		}

	}

	public void open_LoginPopUp(final int whichMenuIteam) {
		loginDialog = new Dialog(this, R.style.ThemeWithCorners);
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
		textView_controller_incorrect_credentials.setTypeface(font);

		EditText_Controller_Password_Login.setTypeface(font);
		EditText_Controller_Username_Login.setTypeface(font);

		/**
		 * Shared preferences to autofill username and password
		 */
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				Util.APP_PREFERENCES, 0);
		editor = pref.edit();

		EditText_Controller_Username_Login.setText(pref.getString("UserName",
				""));
		EditText_Controller_Password_Login.setText(pref.getString("Password",
				""));

		Button_Controller_Login.setTypeface(font);
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
					new LoginUser(whichMenuIteam).execute();
				}

			}

		});
		
		TextView_Controller_Create_account.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent i=new Intent(MainActivity.this,SignUpActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//				loginDialog.dismiss();
			}
		});
		loginDialog.show();
	}

}
