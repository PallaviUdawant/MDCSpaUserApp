package org.mdcconcepts.com.mdcspauserapp.wishlist;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WishListTherapyAdapter extends BaseAdapter {
	private LayoutInflater inflater = null;
	Activity activity;
	ArrayList<HashMap<String, String>> data;
	TextView Txt_THERAPY_Title;
	TextView Txt_THERAPY_Descp;
	ImageButton Imageview_Controller_Delete_Wishlist;

	Button Btn_Select_Therapist;

	public WishListTherapyAdapter(Activity a,
			ArrayList<HashMap<String, String>> data) {
		// TODO Auto-generated constructor stub
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
				R.layout.wishlist_therapy_list_item, null, true);

		final Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"Raleway-Light.otf");

		Txt_THERAPY_Title = (TextView) rootView
				.findViewById(R.id.Txt_THERAPY_Title);
		Imageview_Controller_Delete_Wishlist = (ImageButton) rootView
				.findViewById(R.id.Imageview_Controller_Delete_Wishlist);

		Txt_THERAPY_Descp = (TextView) rootView
				.findViewById(R.id.Txt_THERAPY_Descp);

		Txt_THERAPY_Descp.setTypeface(font);

		Txt_THERAPY_Title.setTypeface(font);

		Imageview_Controller_Delete_Wishlist
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						/**
						 * Ask before leaving app
						 */
						// Typeface font = Typeface.createFromAsset(
						// activity.getAssets(), "Raleway-Light.otf");

						final Dialog dialog = new Dialog(rootView.getContext(),
								R.style.ThemeWithCorners);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.custom_alert_box);
						dialog.setCancelable(false);
						dialog.show();

						TextView title;
						Button btn_yes;
						Button btn_no;

						btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
						btn_no = (Button) dialog.findViewById(R.id.btn_no);
						title = (TextView) dialog.findViewById(R.id.txt_title);

						title.setTypeface(font);
						btn_yes.setTypeface(font);
						btn_no.setTypeface(font);

						btn_yes.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

							}
						});
						btn_no.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub

								dialog.cancel();
							}
						});
					}
				});
		// new HashMap<String, String>();
		// data.get(position);
		Log.d("In Wishlist Adapter", data.get(position).get("therapy_name"));
		
		Txt_THERAPY_Title.setText(data.get(position).get("therapy_name"));
		Txt_THERAPY_Descp.setText(data.get(position).get("therapy_details"));
		return rootView;
	}

}
