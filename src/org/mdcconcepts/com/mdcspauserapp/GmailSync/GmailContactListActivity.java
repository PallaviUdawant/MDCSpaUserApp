package org.mdcconcepts.com.mdcspauserapp.GmailSync;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.contactSync.ContactAdapter;
import org.mdcconcepts.com.mdcspauserapp.contactSync.ContactSyncFragment;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class GmailContactListActivity extends Activity {
	
	ListView listView_Controller_Gmail_Contacts;
	ContactAdapter adapter;
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String, String>> ContactsData = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gmail_contact_list);
		
		listView_Controller_Gmail_Contacts = (ListView) findViewById(R.id.listView_Controller_Gmail_Contacts);
		
		Log.d("GmailContact", AppSharedPreferences.getGmailContactList(this));
		ContactsData=ParseContactsData(AppSharedPreferences.getGmailContactList(this));
		
		adapter = new ContactAdapter(
				GmailContactListActivity.this, ContactsData);
		listView_Controller_Gmail_Contacts.setAdapter(adapter);
	}
	public ArrayList<HashMap<String, String>> ParseContactsData(String html) {
		// TODO Auto-generated method stub
		String Name="No Name";
		try {
			JSONObject jsnobject = new JSONObject(html.trim());
			JSONArray jsonArray = jsnobject.getJSONArray("post");
			for (int i = 0; i < jsonArray.length(); i++) {

				JSONObject Temp = jsonArray.getJSONObject(i);
				HashMap<String, String> contactDetails = new HashMap<String, String>();
				contactDetails.put(ContactSyncFragment.USER_EMAIL,
						Temp.getString("email_id").trim());
				if(!Temp.getString("name").trim().isEmpty())
					 Name=Temp.getString("name").trim();
				else
					 Name="No Name";
					
				contactDetails.put(ContactSyncFragment.USER_NAME,Name
						);
				contactDetails.put(ContactSyncFragment.USER_PHONE,
						"");

				ContactsData.add(contactDetails);
				// Log.d("Temp", Temp.getString("email_id"));
				AppSharedPreferences.setGmailContactList(this,	html.trim());
				AppSharedPreferences.setGmailDataStatus(this, true);
			}

			return ContactsData;
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ContactsData;
	
}

}
