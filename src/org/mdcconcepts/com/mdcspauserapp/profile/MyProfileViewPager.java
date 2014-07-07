package org.mdcconcepts.com.mdcspauserapp.profile;

import org.mdcconcepts.com.mdcspauserapp.DiseaseFragment;
import org.mdcconcepts.com.mdcspauserapp.HumanBodyFragment;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyProfileViewPager extends FragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;
	public static ViewPager mViewPager;
	JSONParser jsonParser = new JSONParser();
	private Typeface font;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_profile_viewpager);

		FragmentManager fm = getSupportFragmentManager();
		mSectionsPagerAdapter = new SectionsPagerAdapter(fm);
		// // Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.myprofile_pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		font = Typeface.createFromAsset(getAssets(), Util.fontPath);

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
//				fragment = new ProfileFragment();
				break;

			case 1:
				fragment = new DiseaseFragment();
			default:
				break;
			}

			// Bundle data = new Bundle();
			// data.putInt("current_page", position + 1);
			// fragment.setArguments(data);
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
				title = "My Profile";
				break;
			case 1:
				title = "Injuries/Paining Areas";
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
			View rootView = inflater.inflate(R.layout.myprofile_section_label,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label_tabs);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

	
}
