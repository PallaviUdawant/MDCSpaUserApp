package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MakeAppointmentFragment extends Fragment 
{

	ListView listview_spa;
	SpaListAdapter adapter;
	
	private View rootView;
	
	public MakeAppointmentFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_makeappointment_spa_list,
				container, false);

		listview_spa=(ListView)rootView.findViewById(R.id.listview_spa);
		
		adapter=new SpaListAdapter(getActivity());
		listview_spa.setAdapter(adapter);
		
		return rootView;
		
	}
}
