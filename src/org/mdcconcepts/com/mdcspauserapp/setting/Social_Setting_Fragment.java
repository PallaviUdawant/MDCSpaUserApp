package org.mdcconcepts.com.mdcspauserapp.setting;

import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Social_Setting_Fragment extends Fragment {

	Switch Switch_Controller_ContactSync;
	public Social_Setting_Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_social_setting,
				container, false);
		
		Switch_Controller_ContactSync=(Switch)rootView.findViewById(R.id.Switch_Controller_ContactSync);
		
		Switch_Controller_ContactSync.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				
				if(isChecked)
				{
					if(!AppSharedPreferences.getContactSyncStatus(getActivity()))
					{
						/**
						 * Contacts Not Synced
						 */
						new SyncContactsWithDBTask().execute();
					}
					else
					{
						/**
						 * Contacts Synced.. Do nothing
						 */
						
					}
				}
				else
				{
					
				}
			}
		
		});
		
		return rootView;
	}
	
	public class SyncContactsWithDBTask extends AsyncTask<String, String, String>
	{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
	}
}
