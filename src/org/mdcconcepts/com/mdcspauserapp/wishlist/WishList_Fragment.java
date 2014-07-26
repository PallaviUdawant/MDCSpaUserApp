package org.mdcconcepts.com.mdcspauserapp.wishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class WishList_Fragment extends Fragment implements OnScrollListener {
	ListView Wishlist_Therapy_List;
	WishListTherapyAdapter adapter;
	ArrayList<HashMap<String, String>> TherapyDetails = new ArrayList<HashMap<String, String>>();
	static final String THERAPY_ID = "therapy_id";
	static final String THERAPY = "therapy_name";
	static final String THERAPY_DETAILS = "therapy_details";
	/**
	 * This is wish list HashMap Data Structure. Contains Theropy Id as Key and
	 * Value as Theropy Name.
	 */
	public static HashMap<Integer, String> WishList = new HashMap<Integer, String>();
	public static HashMap<Integer, String> WishListDesc = new HashMap<Integer, String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wishlist, container,
				false);

		Wishlist_Therapy_List = (ListView) rootView
				.findViewById(R.id.listview_Controller_Wishlist);
		Wishlist_Therapy_List.setOnScrollListener(this);

	
		for (Entry<Integer, String> entry : WishList.entrySet()) {
			
			HashMap<String, String> spaDetails = new HashMap<String, String>();
			
			System.out.println(entry.getKey() + " : " + entry.getValue());
			spaDetails.put(THERAPY_ID, "" + entry.getKey());
			try {
				spaDetails.put(THERAPY,new JSONObject(entry.getValue()).getString("therapy_name") );
				spaDetails.put(THERAPY_DETAILS, new JSONObject(entry.getValue()).getString("therapy_desc") );
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			TherapyDetails.add(spaDetails);

		}
		adapter = new WishListTherapyAdapter(getActivity(), TherapyDetails);
		Wishlist_Therapy_List.setAdapter(adapter);
		Log.d("TherapyDetails Details ", TherapyDetails.toString());

		// for (int i = 0; i < 10; i++) {
		// spaDetails.put(THERAPY_ID, "" + 1);
		// spaDetails.put(THERAPY, "Some Name");
		// spaDetails.put(THERAPY_DETAILS, "Some Description");
		//
		// TherapyDetails.add(spaDetails);
		//
		// }
		return rootView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}
}
