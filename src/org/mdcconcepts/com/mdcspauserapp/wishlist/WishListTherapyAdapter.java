package org.mdcconcepts.com.mdcspauserapp.wishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.app.Dialog;
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
//	private LayoutInflater inflater = null;
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
		
		Imageview_Controller_Delete_Wishlist.setTag(position);
		
		Imageview_Controller_Delete_Wishlist
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						/**
						 * Ask before leaving app
						 */

						final Dialog dialog = new Dialog(rootView.getContext(),
								R.style.ThemeWithCorners);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.custom_alert_box);
						dialog.setCancelable(true);
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
						final int pos = (Integer) v.getTag();
						title.setText("Are you sure you want to remove it from wishlist?");
						
						btn_yes.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								
								WishList_Fragment.WishList.remove(Integer
										.parseInt(data.get(pos).get("therapy_id")));
								
								JSONArray WishList_JsonArray = HashMapToJsonArray(
										WishList_Fragment.WishList);

								AppSharedPreferences.setUserWishList(activity, WishList_JsonArray.toString());
								data.remove(pos);
								notifyDataSetChanged();
								dialog.cancel();
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
		Log.d("In Wishlist Adapter", data.get(position).get("therapy_name"));
		
		Txt_THERAPY_Title.setText(data.get(position).get("therapy_name"));
		Txt_THERAPY_Descp.setText(data.get(position).get("therapy_details"));
		return rootView;
	}

	public JSONArray HashMapToJsonArray(HashMap<Integer, String> WishList
			) {
			JSONArray Wishlist_JsonArray = new JSONArray();

			for (Entry<Integer, String> entry : WishList.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
				JSONObject Theropy = new JSONObject();
				try {
					Theropy.put("therpay_id", "" + entry.getKey());
					Theropy.put("therapy_name", entry.getValue());
					Wishlist_JsonArray.put(Theropy);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			return Wishlist_JsonArray;

		}
}
