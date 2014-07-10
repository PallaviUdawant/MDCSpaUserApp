package org.mdcconcepts.com.mdcspauserapp.findspa;

import java.util.HashMap;
import java.util.Locale;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class SpaProfileActivity extends Activity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	String Spa_Name = "";
	String Spa_Desc = "";
	String Spa_Addr = "";
	String Spa_Rating = "";
	Typeface font;

	HashMap<String, String> selectedSpaDetails = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spa_profile);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		Intent i = getIntent();

		selectedSpaDetails = (HashMap<String, String>) i
				.getSerializableExtra("SelectedSpDetails");

		// Toast.makeText(getApplicationContext(),
		// selectedSpaDetails.get("spa_rating"), Toast.LENGTH_LONG).show();
		// Spa_Name=selectedSpaDetails.get("spa_name");
		// Spa_Addr=selectedSpaDetails.get("spa_addr");
		// Spa_Rating=selectedSpaDetails.get("spa_rating");

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		font = Typeface.createFromAsset(getAssets(), Util.fontPath);
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

		SpannableString s = new SpannableString("Spa Profile");
		// s.setSpan(new TypefaceSpan(SpaProfileActivity.this, Util.fontPath),
		// 0, s.length(),
		// Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		int titleId = getResources().getIdentifier("action_bar_title", "id",
				"android");
		TextView yourTextView = (TextView) findViewById(titleId);
		yourTextView.setTypeface(font);

		TextView t1 = new TextView(this);
		t1.setTypeface(font);
		t1.setTextColor(Color.parseColor("#4e3115"));
		t1.setText("Spa Profile");
		t1.setTextSize(14);
		t1.setGravity(Gravity.CENTER_HORIZONTAL);
		t1.setPadding(10, 20, 10, 10);
		actionBar.addTab(actionBar.newTab().setTabListener(this)
				.setCustomView(t1));

		TextView t2 = new TextView(this);
		t2.setTypeface(font);
		t2.setTextColor(Color.parseColor("#4e3115"));
		t2.setTextSize(14);
		t2.setText("Threapies Details");
		t2.setGravity(Gravity.CENTER_HORIZONTAL);
		t2.setPadding(0, 20, 0, 0);
		actionBar.addTab(actionBar.newTab().setTabListener(this)
				.setCustomView(t2));

		TextView t3 = new TextView(this);
		t3.setTypeface(font);
		t3.setTextColor(Color.parseColor("#4e3115"));
		t3.setTextSize(14);
		t3.setText("Offers");
		t3.setGravity(Gravity.CENTER_HORIZONTAL);
		t3.setPadding(0, 20, 0, 0);

		actionBar.addTab(actionBar.newTab().setTabListener(this)
				.setCustomView(t3));

		// For each of the sections in the app, add a tab to the action bar.
		/*
		 * for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) { //
		 * Create a tab with text corresponding to the page title defined by //
		 * the adapter. Also specify this Activity object, which implements //
		 * the TabListener interface, as the callback (listener) for when //
		 * this tab is selected. /*actionBar.addTab(actionBar.newTab()
		 * .setText(mSectionsPagerAdapter.getPageTitle(i))
		 * .setTabListener(this)); SpannableString s = new SpannableString(
		 * mSectionsPagerAdapter.getPageTitle(i)); s.setSpan(new
		 * TypefaceSpan(Util.fontPath), 0, s.length(),
		 * Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		 * actionBar.addTab(actionBar.newTab().setText(s)
		 * .setTabListener(this)); }
		 */

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spa_profile, menu);
		return true;
	}

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
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			// return PlaceholderFragment.newInstance(position + 1);

			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new SpaInfoFragment(selectedSpaDetails);
				return fragment;
			case 1:
				fragment = new SpaTherapiesFragment();
				return fragment;
			case 2:
				fragment = new SpaOffersFragment();
				return fragment;
			}
			return fragment;
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
				return "Spa Profile".toUpperCase(l);
			case 1:
				return "Thearapies Details".toUpperCase(l);
			case 2:
				return "Offers".toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 * 
	 * public static class PlaceholderFragment extends Fragment { /** The
	 * fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 * 
	 * public static PlaceholderFragment newInstance(int sectionNumber) {
	 * PlaceholderFragment fragment = new PlaceholderFragment(); Bundle args =
	 * new Bundle(); args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	 * fragment.setArguments(args); return fragment; }
	 * 
	 * public PlaceholderFragment() { }
	 * 
	 * @Override public View onCreateView(LayoutInflater inflater, ViewGroup
	 *           container, Bundle savedInstanceState) { View rootView =
	 *           inflater.inflate(R.layout.fragment_spa_profile, container,
	 *           false); TextView textView = (TextView) rootView
	 *           .findViewById(R.id.section_label);
	 *           textView.setText(Integer.toString(getArguments().getInt(
	 *           ARG_SECTION_NUMBER))); return rootView; } }
	 */

}
