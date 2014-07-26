package org.mdcconcepts.com.mdcspauserapp.setting;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

public class General_Setting_Fragment extends Fragment {
	SharedPreferences Setting_Preferences;
	Editor Setting_Editor;

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

	Typeface font;
	@SuppressLint("CommitPrefEdits")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_general_setting,
				container, false);
		
		
		
		TextView_Controller_PushNotification=(TextView)rootView.findViewById(R.id.TextView_Controller_PushNotification);
		
		font=Typeface.createFromAsset(getActivity().getAssets(), Util.fontPath);
		
//		Textview_Controller_Email_Noti_Current_Email.setTypeface(font);
//		TextView_Controller_PushNotification.setTypeface(font);
		/**
		 * Shared Preferences For Settings
		 */
		Setting_Preferences = getActivity().getSharedPreferences(
				Util.APP_PREFERENCES, 0); // 0 -
		Setting_Editor = Setting_Preferences.edit();

		/**
		 * Push Notification - Setting For Email Notification
		 */
		Switch_Controller_Push_Noti_Notify = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Notify);
		Switch_Controller_Push_Noti_Notify.setChecked(false);
		Switch_Controller_Push_Noti_Notify
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Setting_Editor.putBoolean(
									"Switch_Controller_Push_Noti_Notify", true);
							Setting_Editor.commit();
						} else {
							Setting_Editor
									.putBoolean(
											"Switch_Controller_Push_Noti_Notify",
											false);
							Setting_Editor.commit();
						}

					}

				});

		if (Setting_Preferences.getBoolean(
				"Switch_Controller_Push_Noti_Notify", false)) {
			Switch_Controller_Push_Noti_Notify.setChecked(true);

		}

		/**
		 * Push Notification - Setting For Vibrate Control
		 */
		Switch_Controller_Push_Noti_Vibrate = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Vibrate);
		Switch_Controller_Push_Noti_Vibrate.setChecked(false);
		Switch_Controller_Push_Noti_Vibrate
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Setting_Editor
									.putBoolean(
											"Switch_Controller_Push_Noti_Vibrate",
											true);
							Setting_Editor.commit();
						} else {
							Setting_Editor.putBoolean(
									"Switch_Controller_Push_Noti_Vibrate",
									false);
							Setting_Editor.commit();
						}

					}

				});

		if (Setting_Preferences.getBoolean(
				"Switch_Controller_Push_Noti_Vibrate", false)) {
			Switch_Controller_Push_Noti_Vibrate.setChecked(true);

		}

		/**
		 * Push Notification - Setting For ringtone Control
		 */
		Switch_Controller_Push_Noti_Rigtone = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Rigtone);
		Switch_Controller_Push_Noti_Rigtone.setChecked(false);
		Switch_Controller_Push_Noti_Rigtone
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Setting_Editor
									.putBoolean(
											"Switch_Controller_Push_Noti_Rigtone",
											true);
							Setting_Editor.commit();
						} else {
							Setting_Editor.putBoolean(
									"Switch_Controller_Push_Noti_Rigtone",
									false);
							Setting_Editor.commit();
						}

					}

				});

		if (Setting_Preferences.getBoolean(
				"Switch_Controller_Push_Noti_Rigtone", false)) {
			Switch_Controller_Push_Noti_Rigtone.setChecked(true);

		}

		// /**
		// * Push Notification - Setting For ringtone Control
		// */
		// Switch_Controller_Push_Noti_Rigtone = (Switch) rootView
		// .findViewById(R.id.Switch_Controller_Push_Noti_Rigtone);
		// Switch_Controller_Push_Noti_Rigtone.setChecked(false);
		// Switch_Controller_Push_Noti_Rigtone
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		//
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if (isChecked) {
		// Setting_Editor
		// .putBoolean(
		// "Switch_Controller_Push_Noti_Rigtone",
		// true);
		// Setting_Editor.commit();
		// } else {
		// Setting_Editor.putBoolean(
		// "Switch_Controller_Push_Noti_Rigtone",
		// false);
		// Setting_Editor.commit();
		// }
		//
		// }
		//
		// });
		//
		// if (Setting_Preferences.getBoolean(
		// "Switch_Controller_Push_Noti_Rigtone", false)) {
		// Switch_Controller_Push_Noti_Rigtone.setChecked(true);
		//
		// }

		/**
		 * Push Notification - Setting For Light Control
		 */
		Switch_Controller_Push_Noti_Light = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Push_Noti_Light);
		Switch_Controller_Push_Noti_Light.setChecked(false);
		Switch_Controller_Push_Noti_Light
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Setting_Editor.putBoolean(
									"Switch_Controller_Push_Noti_Light", true);
							Setting_Editor.commit();
						} else {
							Setting_Editor.putBoolean(
									"Switch_Controller_Push_Noti_Light", false);
							Setting_Editor.commit();
						}

					}

				});

		if (Setting_Preferences.getBoolean("Switch_Controller_Push_Noti_Light",
				false)) {
			Switch_Controller_Push_Noti_Light.setChecked(true);

		}

		/**
		 * Push Notification - Setting For Light Control
		 */
		Switch_Controller_Email_Noti_Notify = (Switch) rootView
				.findViewById(R.id.Switch_Controller_Email_Noti_Notify);
		Switch_Controller_Email_Noti_Notify.setChecked(false);
		Switch_Controller_Email_Noti_Notify
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							Setting_Editor
									.putBoolean(
											"Switch_Controller_Email_Noti_Notify",
											true);
							Setting_Editor.commit();
						} else {
							Setting_Editor.putBoolean(
									"Switch_Controller_Email_Noti_Notify",
									false);
							Setting_Editor.commit();
						}

					}

				});

		if (Setting_Preferences.getBoolean(
				"Switch_Controller_Email_Noti_Notify", false)) {
			Switch_Controller_Email_Noti_Notify.setChecked(true);

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
