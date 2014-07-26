package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;
import org.mdcconcepts.com.mdcspauserapp.wishlist.WishList_Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ViewHolder")
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

		imageView_controller_wishlist
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						int pos = (Integer) v.getTag();
						imageView_controller_wishlist = (ImageView) v
								.findViewById(R.id.imageView_controller_wishlist);
						String value = WishList_Fragment.WishList.get(Integer
								.parseInt(data.get(pos).get("therapy_id")));
						if (value != null) {

							imageView_controller_wishlist
									.setImageDrawable(rootView
											.getResources()
											.getDrawable(
													R.drawable.ic_wishlist_diasbled));

							WishList_Fragment.WishList.remove(Integer
									.parseInt(data.get(pos).get("therapy_id")));
//							WishList_Fragment.WishListDesc.remove(Integer
//									.parseInt(data.get(pos).get("therapy_id")));

							JSONArray WishList_JsonArray = HashMapToJsonArray(
									WishList_Fragment.WishList);
//							JSONArray WishListDesc_JsonArray = HashMapToJsonArray(
//									WishList_Fragment.WishList, "therapy_id",
//									"therapy_desc");
							/**
							 * Shared preferences to Store Wishlist
							 */
							SharedPreferences pref = rootView.getContext()
									.getSharedPreferences(Util.APP_PREFERENCES,
											0);

							Editor editor = pref.edit();

							editor.putString(Util.WishList,
									WishList_JsonArray.toString());
//							editor.putString(Util.WishListDesc,
//									WishListDesc_JsonArray.toString());

							editor.commit();

						} else {
							imageView_controller_wishlist
									.setImageDrawable(rootView
											.getResources()
											.getDrawable(
													R.drawable.ic_wishlist_enabled));
							JSONObject Therapy_Data = new JSONObject();

							try {
								Therapy_Data.put("therapy_desc", data.get(pos)
										.get("therapy_details"));
								Therapy_Data.put("therapy_name", data.get(pos)
										.get("therapy_name"));
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							WishList_Fragment.WishList.put(
									Integer.parseInt(data.get(pos).get(
											"therapy_id")),
											Therapy_Data.toString());
							
							// WishList_Fragment.WishListDesc.put(
							// Integer.parseInt(data.get(pos).get(
							// "therapy_id")),
							// data.get(pos).get("therapy_desc"));

							JSONArray WishList_JsonArray = HashMapToJsonArray(
									WishList_Fragment.WishList);
//							JSONArray WishListDesc_JsonArray = HashMapToJsonArray(
//									WishList_Fragment.WishList, "therapy_id",
//									"therapy_desc");
							/**
							 * Shared preferences to Store Wishlist
							 */
							SharedPreferences pref = rootView.getContext()
									.getSharedPreferences(Util.APP_PREFERENCES,
											0);

							Editor editor = pref.edit();

							editor.putString(Util.WishList,
									WishList_JsonArray.toString());
//							editor.putString(Util.WishListDesc,
//									WishListDesc_JsonArray.toString());
							editor.commit();

						}
					}
				});

		/**
		 * Shared preferences to Store Wishlist
		 */
		String value = WishList_Fragment.WishList.get(Integer.parseInt(data
				.get(position).get("therapy_id")));
		if (value == null) {
			imageView_controller_wishlist.setImageDrawable(rootView
					.getResources()
					.getDrawable(R.drawable.ic_wishlist_diasbled));
		} else {
			imageView_controller_wishlist
					.setImageDrawable(rootView.getResources().getDrawable(
							R.drawable.ic_wishlist_enabled));
		}

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
