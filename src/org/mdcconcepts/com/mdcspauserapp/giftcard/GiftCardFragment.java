package org.mdcconcepts.com.mdcspauserapp.giftcard;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.CustomValidator;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GiftCardFragment extends Fragment {

	public GiftCardFragment() {
	}

	EditText Name;
	EditText Mobile;
	EditText Email;
	
	TextView Txt_friends_name;
	TextView Txt_friends_mob;
	TextView Txt_friends_email;
	Button ButtonController_SendGiftCard;
	Button SendGiftCard_MakeAppointment;
	// JSON parser class
		JSONParser jsonParser = new JSONParser();

		// ids
		private static final String TAG_SUCCESS = "success";
		private static final String TAG_MESSAGE = "message";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_gift_card,
				container, false);

		Name = (EditText) rootView.findViewById(R.id.Friend_Name);

		Mobile = (EditText) rootView.findViewById(R.id.Friend_Mobile);

		Email = (EditText) rootView.findViewById(R.id.Friend_Email);

		Txt_friends_name=(TextView)rootView.findViewById(R.id.txt_friends_name);
		
		Txt_friends_mob=(TextView)rootView.findViewById(R.id.txt_friends_contact_number);
		
		Txt_friends_email=(TextView)rootView.findViewById(R.id.txt_friend_email);
		
		Typeface font=Typeface.createFromAsset(getActivity().getAssets(), "Raleway-Light.otf");
		
		
		
		
		ButtonController_SendGiftCard = (Button) rootView
				.findViewById(R.id.ButtonController_SendGiftCard);
		SendGiftCard_MakeAppointment=(Button) rootView
				.findViewById(R.id.SendGiftCard_MakeAppointment);
		
		Name.setTypeface(font);
		Mobile.setTypeface(font);
		Email.setTypeface(font);
		Txt_friends_email.setTypeface(font);
		Txt_friends_mob.setTypeface(font);
		Txt_friends_name.setTypeface(font);
		ButtonController_SendGiftCard.setTypeface(font);
		SendGiftCard_MakeAppointment.setTypeface(font);
		
		ButtonController_SendGiftCard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (Name.getText().toString().trim().isEmpty()) {
					Name.setError("Please Enter Name !");
				} else if (Mobile.getText().toString().trim().isEmpty()) {
					Mobile.setError("Please Enter Mobile !");
				} else if (Email.getText().toString().trim().isEmpty()) {
					Email.setError("Please Enter Email !");
				} else if (!CustomValidator.isValidEmail(Email.getText()
						.toString().trim())) {
					Email.setError("Please Enter Correct Email Address !");
				} else if (!CustomValidator.isValidPhoneNumber(Mobile.getText()
						.toString().trim())) {
					Mobile.setError("Please Enter Correct Mobile !");
				} else {
					 new SendGift().execute();

				}

			}
		});

		return rootView;
	}
	
	class SendGift extends AsyncTask<String, String, String> {

		// Progress Dialog
		private ProgressDialog pDialog;
		int success;
		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean failure = false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Sending Gift ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid",
						""+Util.Uid));
				params.add(new BasicNameValuePair("Mail_To",
						Email.getText().toString().trim()));
				params.add(new BasicNameValuePair("receiver_number", Mobile.getText().toString().trim()));
				params.add(new BasicNameValuePair("Gift_To", Name.getText().toString().trim()));
				params.add(new BasicNameValuePair("Gift_Package","Some Package"));
				params.add(new BasicNameValuePair("Therapie_Gifted", "Some Therapie"));
				params.add(new BasicNameValuePair("Spa_name", "MDC Spa"));
				params.add(new BasicNameValuePair("Gift_Card_Details", "None"));
				params.add(new BasicNameValuePair("Other_Data", "None"));
				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(Util.sendgift,
						"POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					return json.getString(TAG_MESSAGE) ;
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(getActivity(), file_url, Toast.LENGTH_LONG)
						.show();
				if (success == 1) {
					
				}
			}

		}

	}
}
