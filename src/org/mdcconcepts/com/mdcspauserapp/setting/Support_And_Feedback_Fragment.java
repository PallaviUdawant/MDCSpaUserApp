package org.mdcconcepts.com.mdcspauserapp.setting;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Support_And_Feedback_Fragment extends Fragment {

	public Support_And_Feedback_Fragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_support_and_feedback, container, false);

		TextView Textview_Controller_Send_Feedback = (TextView) rootView
				.findViewById(R.id.Textview_Controller_Send_Feedback);
		ImageView Image_Controller_Send_Feedback = (ImageView) rootView
				.findViewById(R.id.Image_Controller_Send_Feedback);

		Textview_Controller_Send_Feedback
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								Send_FeedBack.class);
						// EditText editText = (EditText)
						// findViewById(R.id.edit_message);
						// String message = editText.getText().toString();
						// intent.putExtra(EXTRA_MESSAGE, message);
						startActivity(intent);
					}
				});
		Image_Controller_Send_Feedback
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(getActivity(),
								Send_FeedBack.class);
						// EditText editText = (EditText)
						// findViewById(R.id.edit_message);
						// String message = editText.getText().toString();
						// intent.putExtra(EXTRA_MESSAGE, message);
						startActivity(intent);
					}
				});

		TextView Text_View_Share_App = (TextView) rootView
				.findViewById(R.id.Text_View_Share_App);
		ImageView Image_View_Share_App = (ImageView) rootView
				.findViewById(R.id.Image_View_Share_App);

		Text_View_Share_App.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});
		Image_View_Share_App.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				String shareBody = "Here is the share content body";
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"Subject Here");
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareBody);
				startActivity(Intent.createChooser(sharingIntent, "Share via"));
			}
		});

		TextView Text_View_Rate_App = (TextView) rootView
				.findViewById(R.id.Text_View_Rate_App);
		ImageView Image_View_Rate_App = (ImageView) rootView
				.findViewById(R.id.Image_View_Rate_App);

		Text_View_Rate_App.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("market://details?id="
						+ getActivity().getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ getActivity().getPackageName())));
				}
			}
		});
		Image_View_Rate_App.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("market://details?id="
						+ getActivity().getPackageName());
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				try {
					startActivity(goToMarket);
				} catch (ActivityNotFoundException e) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ getActivity().getPackageName())));
				}
			}
		});
		return rootView;
	}
}
