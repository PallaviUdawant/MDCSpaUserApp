package org.mdcconcepts.com.mdcspauserapp.findspa;

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
import android.widget.ImageView;
import android.widget.TextView;

public class TFSListviewAdapter extends BaseAdapter {

	private static LayoutInflater inflater = null;
	Activity activity;
	ArrayList<HashMap<String, String>> data;
	TextView Txt_Price, Txt_Time;
	String Spa_Id;
	ImageView imageView_spa_logo;
	int count;
	// ImageLoader imgLoader;
	Context context;

	public TFSListviewAdapter(Activity a,
			ArrayList<HashMap<String, String>> pricingDetails) {
		// TODO Auto-generated constructor stub
		this.activity = a;
		this.data = pricingDetails;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final View rootView = inflater.inflate(R.layout.tfs_listview_adapter,
				null, true);
		context = rootView.getContext();

		Txt_Price = (TextView) rootView.findViewById(R.id.textView_Price);

		Txt_Time = (TextView) rootView.findViewById(R.id.textView_Time);
		Typeface font = Typeface.createFromAsset(activity.getAssets(),
				"Raleway-Light.otf");
		Txt_Price.setTypeface(font);
		Txt_Time.setTypeface(font, Typeface.BOLD);
		//
		HashMap<String, String> therapiesDetails = new HashMap<String, String>();

		therapiesDetails = data.get(position);

		rootView.setTag(therapiesDetails);

		Txt_Price.setText("Rs."+therapiesDetails.get("Price"));
		Txt_Time.setText(therapiesDetails.get("Time")+" hr/hrs");

		return rootView;
	}

}
