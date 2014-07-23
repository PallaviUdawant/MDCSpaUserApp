package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectTherapyAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	Activity activity;
	ArrayList<HashMap<String, String>> data;
	TextView Txt_Time_For_Service;
	TextView Txt_Therapy_Time;
	TextView Txt_Therapy_Price;
	TextView Txt_Spa_Title;
	TextView Txt_Spa_Descp;

	ImageView imageView_controller_wishlist;
	final int imageViewState[];
	Button Btn_Select_Therapist;

	public SelectTherapyAdapter(Activity a,
			ArrayList<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub

		this.activity = a;
		this.data = data;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageViewState = new int[this.data.size()];
		for (int i = 0; i < data.size(); i++)
			imageViewState[i] = 0;
		Log.v("data", data.toString());
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
		final View rootView = inflater.inflate(R.layout.therapy_list_item,
				null, true);

		Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"Raleway-Light.otf");
		Txt_Spa_Title = (TextView) rootView.findViewById(R.id.Txt_Spa_Title);
		Txt_Spa_Descp = (TextView) rootView.findViewById(R.id.Txt_Spa_Descp);

		imageView_controller_wishlist = (ImageView) rootView
				.findViewById(R.id.imageView_controller_wishlist);

		Txt_Spa_Descp.setTypeface(font);
		Txt_Spa_Title.setTypeface(font);

		HashMap<String, String> therapyDetails = new HashMap<String, String>();
		therapyDetails = data.get(position);

		imageView_controller_wishlist.setTag(position);

		Txt_Spa_Title.setText(therapyDetails.get("therapy_name"));
		Txt_Spa_Descp.setText(therapyDetails.get("therapy_details"));
		rootView.setTag(therapyDetails);
//
//		imageView_controller_wishlist
//				.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						int pos = (Integer) v.getTag();
//						imageView_controller_wishlist = (ImageView) v
//								.findViewById(R.id.imageView_controller_wishlist);
//						Log.d("Position", "" + pos);
//						if (imageViewState[pos] == 1) {
//							imageView_controller_wishlist
//									.setImageDrawable(rootView
//											.getResources()
//											.getDrawable(
//													R.drawable.ic_wishlist_diasbled));
//							imageViewState[pos] = 0;
//						} else {
//							imageView_controller_wishlist
//									.setImageDrawable(rootView
//											.getResources()
//											.getDrawable(
//													R.drawable.ic_wishlist_enabled));
//							imageViewState[pos] = 1;
//						}
//
//					}
//				});

		// if (imageViewState[position] == 0) {
		// imageView_controller_wishlist.setImageDrawable(rootView
		// .getResources()
		// .getDrawable(R.drawable.ic_wishlist_diasbled));
		// } else {
		// imageView_controller_wishlist
		// .setImageDrawable(rootView.getResources().getDrawable(
		// R.drawable.ic_wishlist_enabled));
		// }

		return rootView;
	}

}
