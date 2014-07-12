package org.mdcconcepts.com.mdcspauserapp.viewappointments;

import java.util.ArrayList;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ViewAppointmentCustomList extends ArrayAdapter<String> {

	private final Activity context;
	private final ArrayList<String> ArrayList_AppointMent_Service;
	private final ArrayList<String> ArrayList_AppointMent_Time;

	public ViewAppointmentCustomList(Activity context,
			ArrayList<String> ArrayList_AppointMent_Service,
			ArrayList<String> ArrayList_AppointMent_Time) {
		super(context, R.layout.view_appointment__single_list,
				ArrayList_AppointMent_Service);
		this.context = context;
		this.ArrayList_AppointMent_Service = ArrayList_AppointMent_Service;
		this.ArrayList_AppointMent_Time = ArrayList_AppointMent_Time;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rootView = inflater.inflate(
				R.layout.view_appointment__single_list, null, true);

		TextView txtTitle = (TextView) rootView
				.findViewById(R.id.TextviewController_Services);

		TextView TextView_TimeDate = (TextView) rootView
				.findViewById(R.id.TextviewController_TIme);
		TextView textView_appointment_desc = (TextView) rootView
				.findViewById(R.id.textView_appointment_desc);

		txtTitle.setText(ArrayList_AppointMent_Service.get(position));
		TextView_TimeDate.setText(ArrayList_AppointMent_Time.get(position));

		Typeface font = Typeface.createFromAsset(rootView.getContext()                                                                                                                                                                          
				.getAssets(), Util.fontPath);

		txtTitle.setTypeface(font,Typeface.BOLD);
		TextView_TimeDate.setTypeface(font,Typeface.BOLD);
		textView_appointment_desc.setTypeface(font);
		
		
		return rootView;
	}
}