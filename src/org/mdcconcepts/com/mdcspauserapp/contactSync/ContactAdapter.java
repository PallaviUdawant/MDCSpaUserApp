package org.mdcconcepts.com.mdcspauserapp.contactSync;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	
	Activity activity;
	ArrayList<HashMap<String, String>> data;
	
	TextView TextViewController_UserName;
	TextView TextViewController_Email_Id;
	TextView TextViewController_PhoneNumber;
	
	public ContactAdapter(Activity a,
			ArrayList<HashMap<String, String>> data)
	{
		this.activity = a;
		this.data = data;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 LayoutInflater inflater = activity.getLayoutInflater();
			final View rootView = inflater.inflate(
					R.layout.contact_item, null, true);

			final Typeface font = Typeface.createFromAsset(activity.getAssets(),
					"Raleway-Light.otf");
			
			TextViewController_UserName = (TextView) rootView
					.findViewById(R.id.TextViewController_UserName);
			TextViewController_Email_Id = (TextView) rootView
					.findViewById(R.id.TextViewController_Email_Id);
			TextViewController_PhoneNumber = (TextView) rootView
					.findViewById(R.id.TextViewController_PhoneNumber);
			
			TextViewController_UserName.setTypeface(font,Typeface.BOLD);
			TextViewController_Email_Id.setTypeface(font);
			TextViewController_PhoneNumber.setTypeface(font);
			
			String Email=data.get(position).get(ContactSyncFragment.USER_EMAIL);
			String PhoneNumber=data.get(position).get(ContactSyncFragment.USER_PHONE);
			
			
				
			TextViewController_UserName.setText(data.get(position).get(ContactSyncFragment.USER_NAME));
			TextViewController_Email_Id.setText("Email: "+Email);
			TextViewController_PhoneNumber.setText("Contact Number: "+PhoneNumber);
			
			if(Email.isEmpty())
				TextViewController_Email_Id.setVisibility(View.GONE);
			
			if(PhoneNumber.isEmpty())
				TextViewController_PhoneNumber.setVisibility(View.GONE);
			
			
		return rootView;
	}

}
