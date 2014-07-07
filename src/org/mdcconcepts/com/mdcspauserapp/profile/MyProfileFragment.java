package org.mdcconcepts.com.mdcspauserapp.profile;

import org.mdcconcepts.com.mdcspauserapp.InjuriesActivityMain;
import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyProfileFragment extends Fragment {

	

	ProfileListViewAdapter customAdapter;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */

	static Typeface font;
	public static View rootView;

	private ListView listView_tabs;
	private Intent i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.my_profile_fragment, container,
				false);

		listView_tabs = (ListView) rootView.findViewById(R.id.listView_tabs);


		customAdapter = new ProfileListViewAdapter(getActivity());
		listView_tabs.setAdapter(customAdapter);

		listView_tabs.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
						i= new Intent(getActivity(),ProfileFragment.class);
						startActivity(i);
					break;
				case 1:i= new Intent(getActivity(),InjuriesActivityMain.class);
				startActivity(i);
			break;
				}
			}
		});
		
		return rootView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		final ActionBar actionBar = getActivity().getActionBar();
		actionBar.removeAllTabs();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		super.onDestroy();
		// final ActionBar actionBar =getActivity().getActionBar();
		// actionBar.removeAllTabs();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
