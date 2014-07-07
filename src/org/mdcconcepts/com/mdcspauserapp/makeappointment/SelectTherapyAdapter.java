package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class SelectTherapyAdapter extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Activity activity;
    ArrayList<HashMap<String, String>> data;
    TextView Txt_Time_For_Service;
    TextView Txt_Therapy_Time;
    TextView Txt_Therapy_Price;
    TextView Txt_Spa_Title;
    TextView Txt_Spa_Descp;

	Button Btn_Select_Therapist;
    public SelectTherapyAdapter(Activity a, ArrayList<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
		this.activity = a;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 notifyDataSetChanged();
	}

    @Override
	public int getCount() {
		// TODO Auto-generated method stub
    	return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// TODO Auto-generated method stub
		LayoutInflater inflater = activity.getLayoutInflater();
		final View rootView = inflater.inflate(R.layout.therapy_list_item,
				null, true);
		
		
		Typeface font=Typeface.createFromAsset(activity.getAssets(), "Raleway-Light.otf");
		
		
		Txt_Time_For_Service=(TextView)rootView.findViewById(R.id.txt_time_for_service);
		
		Txt_Therapy_Time=(TextView)rootView.findViewById(R.id.txt_therapy_time);
		
		Txt_Therapy_Price=(TextView)rootView.findViewById(R.id.txt_therapy_price);
		
		Txt_Spa_Title=(TextView)rootView.findViewById(R.id.Txt_Spa_Title);
		
		Txt_Spa_Descp=(TextView)rootView.findViewById(R.id.Txt_Spa_Descp);
		
		Btn_Select_Therapist=(Button)rootView.findViewById(R.id.btn_select_therapist);
		
		Txt_Therapy_Time.setTypeface(font);
		Txt_Therapy_Price.setTypeface(font);
		Txt_Time_For_Service.setTypeface(font);
		Btn_Select_Therapist.setTypeface(font);
		Txt_Spa_Descp.setTypeface(font);
		Txt_Spa_Title.setTypeface(font);
			
		HashMap<String, String> therapyDetails = new HashMap<String, String>();
		therapyDetails=data.get(position);
		
		Txt_Spa_Title.setText(therapyDetails.get(Select_Therapy_Activity.THERAPY));
		Txt_Spa_Descp.setText(therapyDetails.get(Select_Therapy_Activity.THERAPY_DETAILS));
			return rootView;
	}

}
