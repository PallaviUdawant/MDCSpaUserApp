package org.mdcconcepts.com.mdcspauserapp.notification;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NotificationFragment extends Fragment 
{
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		rootView=inflater.inflate(R.layout.fragment_notification, null);
		return rootView;
	}

}
