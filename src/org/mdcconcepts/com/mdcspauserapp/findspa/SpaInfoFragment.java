package org.mdcconcepts.com.mdcspauserapp.findspa;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("ValidFragment") public class SpaInfoFragment extends Fragment {

	TextView txt_spa_name_profile;
	TextView txt_spa_desc_profile;
	TextView txt_spa_addr_profile;
	Button Btn_add_to_fav;
	Typeface font;

	String Spa_Name = "";
	String Spa_Desc = "";
	String Spa_Addr = "";


	public SpaInfoFragment(String spa_Name2, String spa_Addr2) {
		// TODO Auto-generated constructor stub
		Spa_Name = spa_Name2;
		Spa_Addr = spa_Addr2;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootview = inflater.inflate(R.layout.activity_spa_details, null);

		txt_spa_addr_profile = (TextView) rootview
				.findViewById(R.id.txt_spa_addr_profile);
		txt_spa_desc_profile = (TextView) rootview
				.findViewById(R.id.txt_spa_dec_profile);
		txt_spa_name_profile = (TextView) rootview
				.findViewById(R.id.txt_spa_name_profile);

		Btn_add_to_fav = (Button) rootview.findViewById(R.id.Btn_Add_to_fav);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				Util.fontPath);

		txt_spa_addr_profile.setTypeface(font);
		txt_spa_desc_profile.setTypeface(font);
		txt_spa_name_profile.setTypeface(font);

		txt_spa_name_profile.setText(Spa_Name);
		txt_spa_addr_profile.setText(Spa_Addr);
		
		Btn_add_to_fav.setTypeface(font);

		return rootview;
	}
}
