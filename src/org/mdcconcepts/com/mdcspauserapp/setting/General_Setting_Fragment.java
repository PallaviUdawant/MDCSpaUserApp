package org.mdcconcepts.com.mdcspauserapp.setting;

import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.customitems.CustomValidator;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class General_Setting_Fragment extends Fragment {
//	Editor Setting_Editor;

	public General_Setting_Fragment() {
	}

	private Switch Switch_Controller_Push_Noti_Notify;
	private Switch Switch_Controller_Push_Noti_Vibrate;
	private Switch Switch_Controller_Push_Noti_Rigtone;
	private Switch Switch_Controller_Push_Noti_Light;
	private Switch Switch_Controller_Email_Noti_Notify;

	private TextView Textview_Controller_Email_Noti_Current_Email;
	TextView TextView_Controller_PushNotification;
	TextView TextView_Controller_Notification;
	TextView TextView_Controller_vibrate;
	TextView TextView_Controller_Ringtone;
	TextView TextView_Controller_Light;

	LinearLayout LinearLayout_Email_Notification;
	LinearLayout LinearLayout_AlternateEmail;
	
	ImageView ImageView_Controller_Email_Noti_Add_Another_Email;

	Typeface font;

	@SuppressLint("CommitPrefEdits")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_general_setting,
				container, false);

		TextView_Controller_PushNotification = (TextView) rootView
				.findViewById(R.id.TextView_Controller_PushNotification);

		ImageView_Controller_Email_Noti_Add_Another_Email = (ImageView) rootView
				.findViewById(R.id.ImageView_Controller_Email_Noti_Add_Another_Email);

		LinearLayout_Email_Notification = (LinearLayout) rootView
				.findViewById(R.id.LinearLayout_Email_Notification);
		
		LinearLayout_AlternateEmail = (LinearLayout) rootView
				.findViewById(R.id.LinearLayout_AlternateEmail);

		font = Typeface.createFromAsset(getActivity().getAssets(),
				Util.fontPath);


		if(!AppSharedPreferences.getLoginStatus(getActivity()))
			LinearLayout_Email_Notification.setVisibility(View.GONE);
		
			
		@SuppressWarnings("deprecation")
		final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		params.setMargins(70, 10, 10, 10);

		if (Util.AlternateEmailId.size() > 0) {
			for (int i = 0; i < Util.AlternateEmailId.size(); i++) {

				TextView alternate_email = new TextView(getActivity());
				alternate_email.setText(Util.AlternateEmailId.get(i));
				alternate_email.setTextSize(18);
				alternate_email.setLayoutParams(params);
				alternate_email.setTextColor(Color.parseColor("#4e3115"));
				alternate_email.setTypeface(font);
				LinearLayout_AlternateEmail.addView(alternate_email);
			}
		}

		ImageView_Controller_Email_Noti_Add_Another_Email
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final Dialog dialog = new Dialog(getActivity(),
								R.style.ThemeWithCorners);
						dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
						dialog.setContentView(R.layout.custom_alternate_email_dialog);
						// dialog.setCancelable(false);
						dialog.show();

						TextView textViewController_Alternate_Email = (TextView) dialog
								.findViewById(R.id.textViewController_Alternate_Email);
						textViewController_Alternate_Email.setTypeface(font);

						final EditText EditTextController_Alternate_Email = (EditText) dialog
								.findViewById(R.id.EditTextController_Alternate_Email);
						EditTextController_Alternate_Email.setTypeface(font);

						Button ButtonController_Add_Alternate_Email = (Button) dialog
								.findViewById(R.id.ButtonController_Add_Alternate_Email);
						ButtonController_Add_Alternate_Email.setTypeface(font);

						ButtonController_Add_Alternate_Email
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										if (EditTextController_Alternate_Email
												.getText().toString().isEmpty())
											EditTextController_Alternate_Email
													.setError("Please enter alternate email address");
										else {
											EditTextController_Alternate_Email
													.setError(null);

											if (CustomValidator
													.isValidEmail(EditTextController_Alternate_Email
															.getText()
															.toString().trim())) {

												Util.AlternateEmailId
														.add(EditTextController_Alternate_Email
																.getText()
																.toString());
												TextView alternate_email = new TextView(
														getActivity());
												alternate_email
														.setText(EditTextController_Alternate_Email
																.getText()
																.toString());
												alternate_email
														.setLayoutParams(params);
												alternate_email.setTextColor(Color
														.parseColor("#4e3115"));
												alternate_email
														.setTypeface(font);
												alternate_email.setTextSize(18);
												LinearLayout_AlternateEmail
														.addView(alternate_email);
												dialog.dismiss();
											} else {
												EditTextController_Alternate_Email
														.setError("Invalid Email Address");
											}
										}

									}
								});

					}
				});
		
		/**
		 * Push Notification - Setting For Email Notification
		 */
		Switch_Controller_Push_Noti_Notify = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Notify);
		
		Switch_Controller_Push_Noti_Notify.setChecked(true);
		
		Switch_Controller_Push_Noti_Notify
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							AppSharedPreferences.setPush_Noti_Notify_Status(getActivity(), true);
						} else {
							AppSharedPreferences.setPush_Noti_Notify_Status(getActivity(), false);
						}

					}

				});

		if (AppSharedPreferences.getPush_Noti_Notify_Status(getActivity())) {
			Switch_Controller_Push_Noti_Notify.setChecked(true);

		}
		else
		{
			Switch_Controller_Push_Noti_Notify.setChecked(false);
		}

		/**
		 * Push Notification - Setting For Vibrate Control
		 */
		Switch_Controller_Push_Noti_Vibrate = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Vibrate);
		Switch_Controller_Push_Noti_Vibrate.setChecked(true);
		Switch_Controller_Push_Noti_Vibrate
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							
							AppSharedPreferences.setPush_Noti_Vibrate_Status(getActivity(), true);
						} else {
							AppSharedPreferences.setPush_Noti_Vibrate_Status(getActivity(), false);
						}

					}

				});

		if (AppSharedPreferences.getPush_Noti_Vibrate_Status(getActivity())) {
			Switch_Controller_Push_Noti_Vibrate.setChecked(true);

		}
		else
		{
			Switch_Controller_Push_Noti_Vibrate.setChecked(false);
		}

		/**
		 * Push Notification - Setting For ringtone Control
		 */
		Switch_Controller_Push_Noti_Rigtone = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Rigtone);
		
		Switch_Controller_Push_Noti_Rigtone.setChecked(true);
	
		Switch_Controller_Push_Noti_Rigtone
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							AppSharedPreferences.setPush_Noti_Ringtone_Status(getActivity(), true);
						} else {
							AppSharedPreferences.setPush_Noti_Ringtone_Status(getActivity(), false);
						}

					}

				});

		if (AppSharedPreferences.getPush_Noti_Ringtone_Status(getActivity())) {
			Switch_Controller_Push_Noti_Rigtone.setChecked(true);

		}
		else
		{
			Switch_Controller_Push_Noti_Rigtone.setChecked(false);
		}
		

		/**
		 * Push Notification - Setting For Light Control
		 */
		Switch_Controller_Push_Noti_Light = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Light);
		Switch_Controller_Push_Noti_Light.setChecked(true);
		Switch_Controller_Push_Noti_Light
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							
							AppSharedPreferences.setPush_Noti_Light_Status(getActivity(), true);
						} else {
							AppSharedPreferences.setPush_Noti_Light_Status(getActivity(), false);
						}

					}

				});

		if (AppSharedPreferences.getPush_Noti_Light_Status(getActivity())) {
			Switch_Controller_Push_Noti_Light.setChecked(true);

		}
		else
		{
			Switch_Controller_Push_Noti_Light.setChecked(false);
		}

		/**
		 * Push Notification - Setting For Light Control
		 */
		Switch_Controller_Email_Noti_Notify = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Email_Noti_Notify);
		Switch_Controller_Email_Noti_Notify.setChecked(true);
		Switch_Controller_Email_Noti_Notify
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							AppSharedPreferences.setEmail_Noti_Email_Status(getActivity(), true);
//							Setting_Editor.commit();
						} else {
							AppSharedPreferences.setEmail_Noti_Email_Status(getActivity(), false);
						}

					}

				});

		if (AppSharedPreferences.getEmail_Noti_Email_Status(getActivity())) {
			Switch_Controller_Email_Noti_Notify.setChecked(true);

		}
		else
		{
			Switch_Controller_Email_Noti_Notify.setChecked(false);
		}

		/**
		 * Current Email
		 */
		Textview_Controller_Email_Noti_Current_Email = (TextView) rootView
				.findViewById(R.id.Textview_Controller_Email_Noti_Current_Email);

		Textview_Controller_Email_Noti_Current_Email.setText(Util.User_EmailId);

		return rootView;
	}
}
