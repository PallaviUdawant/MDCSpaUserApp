package org.mdcconcepts.com.mdcspauserapp.socialauth;

import java.util.List;

import org.brickred.socialauth.android.SocialAuthAdapter;
import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Profile;
import android.widget.ListView;
import android.widget.TextView;

public class SocialAuthActivity extends Activity {

	private static SocialAuthAdapter adapter;
	Profile profileMap;
	List<Photo> photosList;

	// Android Components
	ListView listview;
	AlertDialog dialog;
	TextView title;
	ProgressDialog mDialog;

	// Variables
	boolean status;
	String providerName;
	public static int pos;
	private static final int SELECT_PHOTO = 100;
	public static Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.social_auth);
//		adapter = new SocialAuthAdapter(new ResponseListener());
//
//		// Set title
//		title = (TextView) findViewById(R.id.textview);
//		title.setText(R.string.app_name);
//
//		listview = (ListView) findViewById(R.id.listview);
//		listview.setAdapter(new CustomAdapter(this, adapter));
	}
}
