package org.mdcconcepts.com.mdcspauserapp.setting;

import java.util.Locale;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * This class is used for showing setting for user which contains Tab view and
 * all settings needed.
 * 
 * @author Codelord
 * 
 */
public class SettingActivity extends Activity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	public SectionsPagerAdapter1 mSectionsPagerAdapter;
	Typeface font;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
//	public View rootView;
	ActionBar actionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

//		rootView = inflater
//				.inflate(R.layout.activity_setting, container, false);

		font = Typeface.createFromAsset(getAssets(),
				Util.fontPath);

		// Set up the action bar.
		actionBar =getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the
		// three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter1(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		TextView t1 = new TextView(this);
		t1.setTypeface(font);
		t1.setTextColor(Color.parseColor("#4e3115"));
		t1.setText("General Setting");
		t1.setTextSize(14);
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		t1.setPadding(10, 20, 10, 10);
		actionBar.addTab(actionBar.newTab().setTabListener(this)
				.setCustomView(t1));

		TextView t2 = new TextView(this);
		t2.setTypeface(font);
		t2.setTextColor(Color.parseColor("#4e3115"));
		t2.setTextSize(14);
		t2.setText("Social Setting");
		t2.setGravity(Gravity.CENTER_HORIZONTAL);
		t2.setPadding(0, 20, 0, 0);
		actionBar.addTab(actionBar.newTab().setTabListener(this)
				.setCustomView(t2));

		TextView t3 = new TextView(this);
		t3.setTypeface(font);
		t3.setTextColor(Color.parseColor("#4e3115"));
		t3.setTextSize(14);
		t3.setText("Support & Feedback");
		t3.setGravity(Gravity.CENTER_HORIZONTAL);
		t3.setPadding(0, 20, 0, 0);

		actionBar.addTab(actionBar.newTab().setTabListener(this)
				.setCustomView(t3));
		if (getLayoutInflater().getFactory() == null)
			setMenuBackground();
		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

//		return rootView;
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
								 ((TextView)
								 view).setTextColor(Color.parseColor("#4e3115"));
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

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// // Handle action bar item clicks here. The action bar will
	// // automatically handle clicks on the Home/Up button, so long
	// // as you specify a parent activity in AndroidManifest.xml.
	// int id = item.getItemId();
	// if (id == R.id.action_settings) {
	// return true;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onDestroy() {
//		actionBar.removeAllTabs();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onDestroy();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

		public SectionsPagerAdapter1(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new General_Setting_Fragment();
				return fragment;

			case 1:

				fragment = new Social_Setting_Fragment();
				return fragment;

			case 2:
				fragment = new Support_And_Feedback_Fragment();
				return fragment;

			default:
				break;
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.General_Setting).toUpperCase(l);
			case 1:
				return getString(R.string.Social_Setting).toUpperCase(l);
			case 2:
				return getString(R.string.Sopport_Feedback).toUpperCase(l);
			}
			return null;
		}
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
}
