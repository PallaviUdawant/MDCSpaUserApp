package org.mdcconcepts.com.mdcspauserapp.wishlist;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.os.Bundle;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_wishlist, container,
				false);

		Wishlist_Therapy_List = (ListView) rootView
				.findViewById(R.id.listview_Controller_Wishlist);
		Wishlist_Therapy_List.setOnScrollListener(this);
		
		adapter = new WishListTherapyAdapter(getActivity(), TherapyDetails);
		Wishlist_Therapy_List.setAdapter(adapter);
		HashMap<String, String> spaDetails = new HashMap<String, String>();

		for (int i = 0; i < 10; i++) {
			spaDetails.put(THERAPY_ID, "" + 1);
			spaDetails.put(THERAPY, "Some Name");
			spaDetails.put(THERAPY_DETAILS, "Some Description");

			TherapyDetails.add(spaDetails);

		}
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
