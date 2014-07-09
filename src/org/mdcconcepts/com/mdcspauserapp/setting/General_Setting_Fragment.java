package org.mdcconcepts.com.mdcspauserapp.setting;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class General_Setting_Fragment extends Fragment {

	public General_Setting_Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_general_setting,
				container, false);
		return rootView;
	}
}
