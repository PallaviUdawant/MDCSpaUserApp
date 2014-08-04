package org.mdcconcepts.com.mdcspauserapp.contactSync;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;

import com.todddavies.components.progressbar.ProgressWheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneContactSyncActivity extends Activity {

	ListView ListView_Controller_Contact_List;
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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.phonebook_contact_sync);
		ListView_Controller_Contact_List = (ListView) 
				findViewById(R.id.ListView_Controller_Contact_List);

		font = Typeface.createFromAsset(getAssets(),
				"Raleway-Light.otf");

		if (!AppSharedPreferences.getDatabaseCreatedStatus(this)) {
			Log.d("DB Create", "no");
			new SyncContactSTask().execute();
		} else {
			db = openOrCreateDatabase("GlobalContactList",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);

			String getContacts = "select * from PhoneContactList";
			Cursor Cur = db.rawQuery(getContacts, null);
			if (Cur.getCount() > 0) {
				Cur.moveToFirst();
				while (Cur.moveToNext()) {
					HashMap<String, String> personalData = new HashMap<String, String>();
					personalData.put(USER_ID, Cur.getString(0));
					personalData.put(USER_NAME, Cur.getString(1));
					personalData.put(USER_EMAIL, Cur.getString(2));
					personalData.put(USER_PHONE, Cur.getString(3));

					ContactsData.add(personalData);
				}
				adapter = new ContactAdapter(this, ContactsData);
				ListView_Controller_Contact_List.setAdapter(adapter);

			} else {
				new SyncContactSTask().execute();

			}
			Cur.close();
			db.close();

		}
	}
	class SyncContactSTask extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			// super.onPreExecute();
			pdialog = new Dialog(PhoneContactSyncActivity.this,
					R.style.ThemeWithCorners);
			pdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			pdialog.setContentView(R.layout.custom_progress_dialog);
			pdialog.setCancelable(false);
			pdialog.show();

			Txt_Title = (TextView) pdialog.findViewById(R.id.txt_alert_text);
			Txt_Title.setTypeface(font);

			Txt_Title.setText("Synchronising Contacts...");
			/**
			 * custom circular progress bar
			 */
			ProgressWheel pw_four = (ProgressWheel) pdialog
					.findViewById(R.id.progressBarFour);
			pw_four.spin();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (CreateContactsDatabase())
				return "1";
			else
				return "0";
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			// super.onPostExecute(result);
			if (result.equals("1")) {
				pdialog.dismiss();
				AppSharedPreferences.setDatabaseCreatedStatus(PhoneContactSyncActivity.this,
						true);
				Toast.makeText(PhoneContactSyncActivity.this, "Contact Synced",
						Toast.LENGTH_LONG).show();
				adapter = new ContactAdapter(PhoneContactSyncActivity.this, ContactsData);
				ListView_Controller_Contact_List.setAdapter(adapter);
			} else {
				Toast.makeText(PhoneContactSyncActivity.this,
						"Oops Some error occurred.. Please try later ",
						Toast.LENGTH_LONG).show();
			}

		}

	}

	private boolean CreateContactsDatabase() {
		// TODO Auto-generated method stub
		// pdialog.show();

		try {
			db = openOrCreateDatabase("GlobalContactList",
					SQLiteDatabase.CREATE_IF_NECESSARY, null);

			String s2 = "Drop table if exists PhoneContactList ";
			db.execSQL(s2);

			String Query = "Create table PhoneContactList(User_Id INTEGER PRIMARY KEY AUTOINCREMENT,Name text,Email text,PhoneNumber text)";
			db.execSQL(Query);

			ContentResolver cr = getContentResolver();
			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
					null, null, null);
			
			

			if (cur.getCount() > 0) {
				int i = 0;
				while (cur.moveToNext()) {
					String User_Name = "";
					String Phone_Number = "";
					String Email_Id = "";
					String id = cur.getString(cur
							.getColumnIndex(ContactsContract.Contacts._ID));
					String name = cur
							.getString(cur
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					if (name != null) {
						User_Name = name.toString(); // Name
						Log.d("Name", User_Name);
					}
					else
					{
						User_Name="";
					}

					
					// get the phone number
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					if (pCur.getCount() > 0) {
						pCur.moveToFirst();
						String phn = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						if (phn != null) {
							Phone_Number = phn.toString();
							Log.d("Phone_Number", Phone_Number);
						}
						else
						{
							Phone_Number="";
						}
						//
					}
					pCur.close();

					// get email and type
					Cursor emailCur = cr.query(
							ContactsContract.CommonDataKinds.Email.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Email.CONTACT_ID
									+ " = ?", new String[] { id }, null);

					if (emailCur.getCount() > 0) {

						emailCur.moveToFirst();
						String email = emailCur
								.getString(emailCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

						if (email != null) {
							Email_Id = email.toString();
							Log.d("Email_Id", Email_Id);
						}
						else
						{
							Email_Id="";
						}
					}
					emailCur.close();

					String insertDataQuery = "Insert into PhoneContactList (Name,Email,PhoneNumber)values('"
							+ User_Name.replace("'", "")
							+ "','"
							+ Email_Id.replace("'", "")
							+ "','"
							+ Phone_Number.replace("'", "") + "')";
					db.execSQL(insertDataQuery);
					HashMap<String, String> personalData = new HashMap<String, String>();
					personalData.put(USER_ID, "" + i);
					personalData.put(USER_NAME, User_Name.replace("'", ""));
					personalData.put(USER_EMAIL, Email_Id.replace("'", ""));
					personalData.put(USER_PHONE, Phone_Number.replace("'", ""));

					ContactsData.add(personalData);

					// Toast.makeText(getActivity(), "DB Created",
					// Toast.LENGTH_LONG)
					// .show();
					i++;
				}

			}
			// pdialog.dismiss();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
