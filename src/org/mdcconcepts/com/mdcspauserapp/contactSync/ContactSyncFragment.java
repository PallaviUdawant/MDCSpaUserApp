package org.mdcconcepts.com.mdcspauserapp.contactSync;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.GmailSync.GmailContactSyncFragment;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactSyncFragment extends Fragment {
	View rootView;
	ListView ListViewController_All_Sync;
	SQLiteDatabase db;
	Dialog pdialog;
	TextView Txt_Title;
	Typeface font;
	ContactAdapter adapter;
	ArrayList<HashMap<String, String>> ContactsData = new ArrayList<HashMap<String, String>>();

	public static String USER_ID = "User_Id";
	public static String USER_NAME = "Name";
	public static String USER_EMAIL = "Email";
	public static String USER_PHONE = "PhoneNumber";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		rootView = inflater.inflate(R.layout.fragment_contact_sync, container,
				false);

		ListViewController_All_Sync = (ListView) rootView
				.findViewById(R.id.ListViewController_All_Sync);
		   String[] syncType = new String[] { "Phonebook Contacts", 
                   "Gmail COntacts",
                  };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity() ,
	              android.R.layout.simple_list_item_1, android.R.id.text1, syncType);
		ListViewController_All_Sync.setAdapter(adapter);
		ListViewController_All_Sync.setOnItemClickListener(new OnItemClickListener() {

		
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					Intent i=new Intent(getActivity(),PhoneContactSyncActivity.class);
					getActivity().startActivity(i);
						
					break;
				case 1:
					Intent i1=new Intent(getActivity(),GmailContactSyncFragment.class);
					getActivity().startActivity(i1);
						
					break;

				default:
					break;
				}
			}
		});
		
		
		return rootView;
	}

	

}
