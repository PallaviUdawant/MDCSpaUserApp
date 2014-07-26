package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.ArrayList;
import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TimelineDynamicActivity extends Activity {

	ArrayList<HashMap<String, String>> TherapyTimelineDetails = new ArrayList<HashMap<String, String>>();
	View verticalLine;
	View Circle;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline_dynamic);
		
		LinearLayout dynamic_timeline_layout=(LinearLayout)findViewById(R.id.dynamic_timeline_layout);
		
		
		TextView TextView_Controller_Therapist_Name_Timeline=(TextView)findViewById(R.id.TextView_Controller_Therapist_Name_Timeline);
		TextView TextView_Controller_Spa_Name_Timeline=(TextView)findViewById(R.id.TextView_Controller_Spa_Name_Timeline);
		
		Typeface font=Typeface.createFromAsset(getAssets(), Util.fontPath);
		TextView_Controller_Therapist_Name_Timeline.setTypeface(font,Typeface.BOLD);
		TextView_Controller_Spa_Name_Timeline.setTypeface(font);
		
		 Intent i = getIntent();
		 TherapyTimelineDetails=(ArrayList<HashMap<String, String>>)
		 i.getSerializableExtra("TimelineData");
		 
		 TextView_Controller_Therapist_Name_Timeline.setText(i.getStringExtra("TherapistName"));
		 TextView_Controller_Spa_Name_Timeline.setText(i.getStringExtra("SpaName"));
		 
		 Toast.makeText(this, i.getStringExtra("Profile_url"), Toast.LENGTH_LONG).show();
		 
		 getActionBar().setDisplayHomeAsUpEnabled(true);
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_LARGE) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
			View v = Timeline_Large_Screen();
			dynamic_timeline_layout.addView(v);
//			this.setContentView(v);
		} else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_NORMAL) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
			View v = Timeline_Small_Screen();
			dynamic_timeline_layout.addView(v);
//			this.setContentView(v);
		} else {
			View v = Timeline_Large_Screen();
//			this.setContentView(v);
		}


	}

	@SuppressWarnings("deprecation")
	private View Timeline_Small_Screen() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub

		/*** Main Layout ***/
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.gravity = Gravity.CENTER_VERTICAL;
		layoutParams.topMargin = 100;
		mainLayout.setLayoutParams(layoutParams);
		mainLayout.setGravity(Gravity.CENTER_VERTICAL);

		/*** ScrollView ***/
		ScrollView sv = new ScrollView(this);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		sv.setLayoutParams(layoutParams);

		/**
		 * ScrollView LinearLayout
		 */
		LinearLayout ScrollViewLinear = new LinearLayout(this);
		ScrollViewLinear.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		ScrollViewLinear.setPadding(5, 5, 5, 5);
		ScrollViewLinear.setBackgroundColor(0x50cccccc);
		ScrollViewLinear.setLayoutParams(layoutParams);
		sv.addView(ScrollViewLinear);

		/*** Linear Layout with horizontal orientation ***/

		LinearLayout LinearHorizontal = new LinearLayout(this);
		LinearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		LinearHorizontal.setGravity(Gravity.CENTER);
		layoutParams.topMargin = 100;
		LinearHorizontal.setLayoutParams(layoutParams);
		ScrollViewLinear.addView(LinearHorizontal);

		/**
		 * Child 1
		 */
		LinearLayout Linear_Child1 = new LinearLayout(this);
		Linear_Child1.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		// layoutParams.leftMargin=5;
		// Linear_Child1.setBackgroundColor(Color.RED);
		Linear_Child1.setLayoutParams(layoutParams);
		LinearHorizontal.addView(Linear_Child1);

		/**
		 * Child 2
		 */
		LinearLayout Linear_Child2 = new LinearLayout(this);
		Linear_Child2.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		Linear_Child2.setLayoutParams(layoutParams);
		// Linear_Child2.setBackgroundColor(Color.DKGRAY);
		Linear_Child2.setGravity(Gravity.CENTER);
		LinearHorizontal.addView(Linear_Child2);

		/**
		 * Child 3
		 */
		LinearLayout Linear_Child3 = new LinearLayout(this);
		Linear_Child3.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		Linear_Child3.setLayoutParams(layoutParams);
		// Linear_Child3.setBackgroundColor(Color.GRAY);
		LinearHorizontal.addView(Linear_Child3);
		int k = 0;
		for (int j = 0; j < TherapyTimelineDetails.size(); j++) {

			if (k == 6)
				k = 1;

			verticalLine = new View(this);
			verticalLine.setLayoutParams(new ViewGroup.LayoutParams(4, 100));
			verticalLine.setBackgroundColor(0xff4e3115);
			Linear_Child2.addView(verticalLine);

			Circle = new View(this);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(15,
					15);
			Circle.setBackground(getResources().getDrawable(R.drawable.circle));
			param.setMargins(0, 2, 0, 2);
			Circle.setLayoutParams(param);
			Linear_Child2.addView(Circle);

			verticalLine = new View(this);
			verticalLine.setLayoutParams(new ViewGroup.LayoutParams(4, 100));
			verticalLine.setBackgroundColor(0xff4e3115);
			Linear_Child2.addView(verticalLine);

			LinearLayout Linear_Child1_Parent = new LinearLayout(this);
			Linear_Child1_Parent.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams layoutParamsParent = new LinearLayout.LayoutParams(
					200, LayoutParams.WRAP_CONTENT);
			layoutParamsParent.gravity = Gravity.CENTER;

			LinearLayout Linear_Child1_Child = new LinearLayout(this);
			Linear_Child1_Child.setOrientation(LinearLayout.HORIZONTAL);
			layoutParams = new LinearLayout.LayoutParams(170,
					LayoutParams.WRAP_CONTENT);
			Linear_Child1_Child.setBackground(getResources().getDrawable(
					R.drawable.drop_sawdow_bk));
			Linear_Child1_Child.setLayoutParams(layoutParams);

			TextView tv = new TextView(this);
			Typeface font=Typeface.createFromAsset(getAssets(), Util.fontPath);
			tv.setTypeface(font);
			String Appointment_Time = TherapyTimelineDetails.get(j).get(
					TherapistSchedule.Appointment_Start_Time);
			String Appointment_End_Time = TherapyTimelineDetails.get(j).get(
					TherapistSchedule.Appointment_End_Time);
			String Timeline_Data = "Therapist Busy \n From " + Appointment_Time
					+ " to " + Appointment_End_Time;
			tv.setText(Timeline_Data);

			// tv.setText("Therapist Busy \n From 12/12/2013 12:00 to 12/12/2013 1:00");
			tv.setPadding(10, 10, 10, 10);
			tv.setTextColor(0xff4e3115);

			View viewHorizontal = new View(this);
			LinearLayout.LayoutParams ViewHorizontalParam = new LinearLayout.LayoutParams(
					50, 3);
			ViewHorizontalParam.gravity = Gravity.CENTER_VERTICAL;
			viewHorizontal.setLayoutParams(ViewHorizontalParam);
			viewHorizontal.setBackgroundColor(0xff4e3115);

			if (j % 2 == 0) {
				/** Even Child1 ***/
				if (j == 0)
					/** First Child **/
					layoutParamsParent.topMargin = 225;
				else

					layoutParamsParent.topMargin = 235 + k;

				Linear_Child1_Parent.setLayoutParams(layoutParamsParent);
				Linear_Child1_Child.addView(tv);
				Linear_Child1_Parent.addView(Linear_Child1_Child);

				Linear_Child1_Parent.addView(viewHorizontal);
				Linear_Child1.addView(Linear_Child1_Parent);

			} else {
				// Linear_Child1_Child.addView(viewHorizontal);

				// LinearLayout.LayoutParams ViewHorizontalParam = new
				// LinearLayout.LayoutParams(50, 3);
				// View viewHorizontal = new View(this);
				// ViewHorizontalParam.gravity=Gravity.CENTER_VERTICAL;
				// viewHorizontal.setLayoutParams(ViewHorizontalParam);
				// viewHorizontal.setBackgroundColor(0xff4e3115);

				/** Odd Child ***/
				if (j == 1)
					/** First Child **/
					layoutParamsParent.topMargin = 7;
				else
					layoutParamsParent.topMargin = 235 + k;

				Linear_Child1_Parent.setLayoutParams(layoutParamsParent);
				Linear_Child1_Parent.addView(viewHorizontal);
				Linear_Child1_Child.addView(tv);
				Linear_Child1_Parent.addView(Linear_Child1_Child);
				Linear_Child3.addView(Linear_Child1_Parent);

			}
			/*
			 * LinearLayout Linear_Child1_Parent = new LinearLayout(this);
			 * Linear_Child1_Parent.setOrientation(LinearLayout.HORIZONTAL);
			 * 
			 * LinearLayout Linear_Child1_Child = new LinearLayout(this);
			 * Linear_Child1_Child.setOrientation(LinearLayout.HORIZONTAL);
			 * 
			 * layoutParams = new LinearLayout.LayoutParams(250,
			 * LayoutParams.WRAP_CONTENT);
			 * Linear_Child1_Child.setBackground(getResources().getDrawable(
			 * R.drawable.drop_sawdow_bk));
			 * 
			 * TextView tv = new TextView(this);
			 */

			// Log.v("Appointment Time",
			// TherapyTimelineDetails.get(j).get(TherapistSchedule.Appointment_Start_Time).toString()+"  j: "+j);

			// String
			// Appointment_Time=TherapyTimelineDetails.get(j).get(TherapistSchedule.Appointment_Start_Time);
			// String
			// Appointment_End_Time=TherapyTimelineDetails.get(j).get(TherapistSchedule.Appointment_End_Time);
			// String Timeline_Data="Therapist Busy \n From "+ Appointment_Time
			// + " to "+Appointment_End_Time;
			// tv.setText(Timeline_Data);
			/*
			 * tv.setText(
			 * "Therapist Busy \n From 12/12/2013 12:00 to 12/12/2013 1:00");
			 * tv.setPadding(10, 10, 10, 10); tv.setTextColor(0xff4e3115);
			 * 
			 * 
			 * View viewHorizontal = new View(this); LinearLayout.LayoutParams
			 * ViewHorizontalParam = new LinearLayout.LayoutParams(50, 4);
			 * viewHorizontal.setBackgroundColor(0xff4e3115);
			 * 
			 * if (j % 2 != 0) { /*if (j == 1) { layoutParams.setMargins(0, 170,
			 * 0, 0); ViewHorizontalParam.setMargins(0, 230, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam);
			 * 
			 * } else { layoutParams.setMargins(0, 100, 0, 0);
			 * ViewHorizontalParam.setMargins(0, 170, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam); }
			 * 
			 * Linear_Child1_Child.setLayoutParams(layoutParams);
			 * 
			 * Linear_Child1_Child.addView(tv); // Add Text View to Linear //
			 * Layout
			 * 
			 * Linear_Child1_Parent.addView(Linear_Child1_Child);// Add Inner //
			 * layout to // parent
			 * 
			 * Linear_Child1_Parent.addView(viewHorizontal);// Add horizontal //
			 * line to // parent
			 * 
			 * Linear_Child1.addView(Linear_Child1_Parent); // Add parent to //
			 * grandparent
			 * 
			 * } else { if (j == 0) { layoutParams.setMargins(0, 50, 0, 0);
			 * ViewHorizontalParam.setMargins(0, 110, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam); } else {
			 * layoutParams.setMargins(0, 100, 0, 0);
			 * ViewHorizontalParam.setMargins(0, 170, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam); }
			 * 
			 * Linear_Child1_Child.setLayoutParams(layoutParams);
			 * 
			 * Linear_Child1_Child.addView(tv); // Add Text View to Linear //
			 * Layout
			 * 
			 * Linear_Child1_Parent.addView(viewHorizontal); // Add horizontal
			 * // line to // parent
			 * Linear_Child1_Parent.addView(Linear_Child1_Child); // Add Inner
			 * // layout to // parent
			 * 
			 * Linear_Child3.addView(Linear_Child1_Parent); // Add parent to //
			 * grandparent
			 * 
			 * }
			 */

		}

		// View view1 = new View(this);
		// view1.setLayoutParams(new ViewGroup.LayoutParams(4, 100));
		// view1.setBackgroundColor(0xff4e3115);
		// Linear_Child2.addView(view1);
		mainLayout.addView(sv);

		
		return mainLayout;

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// NavUtils.navigateUpFromSameTask(this);

			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("deprecation")
	private View Timeline_Large_Screen() {
		// TODO Auto-generated method stub

		/*** Main Layout ***/
		LinearLayout mainLayout = new LinearLayout(this);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		layoutParams.gravity = Gravity.CENTER_VERTICAL;
		mainLayout.setLayoutParams(layoutParams);
		mainLayout.setGravity(Gravity.CENTER_VERTICAL);

//		LinearLayout headerLayout = new LinearLayout(this);
//		mainLayout.setOrientation(LinearLayout.HORIZONTAL);
//		layoutParams = new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.FILL_PARENT,
//				LinearLayout.LayoutParams.MATCH_PARENT);
//		layoutParams.gravity = Gravity.CENTER_VERTICAL;
//		mainLayout.setLayoutParams(layoutParams);
//		mainLayout.setGravity(Gravity.CENTER_VERTICAL);
//		
//		ImageView profile_pic=new ImageView(this);
		
		
		/*** ScrollView ***/
		ScrollView sv = new ScrollView(this);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		sv.setLayoutParams(layoutParams);

		/**
		 * ScrollView LinearLayout
		 */
		LinearLayout ScrollViewLinear = new LinearLayout(this);
		ScrollViewLinear.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		ScrollViewLinear.setPadding(5, 5, 5, 5);
		ScrollViewLinear.setLayoutParams(layoutParams);
		sv.addView(ScrollViewLinear);

		/*** Linear Layout with horizontal orientation ***/

		LinearLayout LinearHorizontal = new LinearLayout(this);
		LinearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		LinearHorizontal.setGravity(Gravity.CENTER);
		LinearHorizontal.setLayoutParams(layoutParams);
		ScrollViewLinear.addView(LinearHorizontal);

		/**
		 * ScrollView LinearLayout Child
		 * 
		 * 
		 * LinearLayout ScrollViewLinear_Child1 = new LinearLayout(this);
		 * ScrollViewLinear_Child1.setOrientation(LinearLayout.HORIZONTAL);
		 * layoutParams = new LinearLayout.LayoutParams(
		 * LinearLayout.LayoutParams.WRAP_CONTENT,
		 * LinearLayout.LayoutParams.WRAP_CONTENT);
		 * ScrollViewLinear_Child1.setLayoutParams(layoutParams);
		 * LinearHorizontal.addView(ScrollViewLinear_Child1);
		 */

		/**
		 * Child 1
		 * 
		 * LinearLayout BlankLinear_Child1 = new LinearLayout(this);
		 * BlankLinear_Child1.setOrientation(LinearLayout.VERTICAL);
		 * layoutParams = new LinearLayout.LayoutParams(
		 * LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT,1);
		 * BlankLinear_Child1.setLayoutParams(layoutParams);
		 * ScrollViewLinear_Child1.addView(BlankLinear_Child1);
		 */
		/**
		 * Child 1
		 */
		LinearLayout Linear_Child1 = new LinearLayout(this);
		Linear_Child1.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		// layoutParams.leftMargin=5;
		// Linear_Child1.setBackgroundColor(Color.RED);
		Linear_Child1.setLayoutParams(layoutParams);
		LinearHorizontal.addView(Linear_Child1);

		/**
		 * Child 2
		 */
		LinearLayout Linear_Child2 = new LinearLayout(this);
		Linear_Child2.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		Linear_Child2.setLayoutParams(layoutParams);
		// Linear_Child2.setBackgroundColor(Color.DKGRAY);
		Linear_Child2.setGravity(Gravity.CENTER);
		LinearHorizontal.addView(Linear_Child2);

		/**
		 * Child 3
		 */
		LinearLayout Linear_Child3 = new LinearLayout(this);
		Linear_Child3.setOrientation(LinearLayout.VERTICAL);
		layoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		Linear_Child3.setLayoutParams(layoutParams);
		// Linear_Child3.setBackgroundColor(Color.GRAY);
		LinearHorizontal.addView(Linear_Child3);

		int k = 0;
		for (int j = 0; j < TherapyTimelineDetails.size(); j++) {
//		for (int j = 0; j <4; j++) {
			if (k == 6)
				k = 1;

			verticalLine = new View(this);
			verticalLine.setLayoutParams(new ViewGroup.LayoutParams(4, 100));
			verticalLine.setBackgroundColor(0xff4e3115);
			Linear_Child2.addView(verticalLine);

			Circle = new View(this);
			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(15,
					15);
			Circle.setBackground(getResources().getDrawable(R.drawable.circle));
			param.setMargins(0, 2, 0, 2);
			Circle.setLayoutParams(param);
			Linear_Child2.addView(Circle);

			verticalLine = new View(this);
			verticalLine.setLayoutParams(new ViewGroup.LayoutParams(4, 100));
			verticalLine.setBackgroundColor(0xff4e3115);
			Linear_Child2.addView(verticalLine);

			LinearLayout Linear_Child1_Parent = new LinearLayout(this);
			Linear_Child1_Parent.setOrientation(LinearLayout.HORIZONTAL);
			LinearLayout.LayoutParams layoutParamsParent = new LinearLayout.LayoutParams(
					250, LayoutParams.WRAP_CONTENT);
			layoutParamsParent.gravity = Gravity.CENTER;
//			Linear_Child1_Parent.setBackgroundColor(Color.LTGRAY);

			LinearLayout Linear_Child1_Child = new LinearLayout(this);
			Linear_Child1_Child.setOrientation(LinearLayout.HORIZONTAL);
			layoutParams = new LinearLayout.LayoutParams(170,
					LayoutParams.WRAP_CONTENT);
			Linear_Child1_Child.setBackground(getResources().getDrawable(
					R.drawable.drop_sawdow_bk));
			Linear_Child1_Child.setLayoutParams(layoutParams);

			TextView tv = new TextView(this);
			String Appointment_Time = TherapyTimelineDetails.get(j).get(
					TherapistSchedule.Appointment_Start_Time);
			String Appointment_End_Time = TherapyTimelineDetails.get(j).get(
					TherapistSchedule.Appointment_End_Time);
			String Timeline_Data = "Therapist Busy \n From " + Appointment_Time
					+ " to " + Appointment_End_Time;
//			String Timeline_Data = "Therapist Busy \n From 22-06-2014 to 22-06-2014";
			tv.setText(Timeline_Data);
			Typeface font=Typeface.createFromAsset(getAssets(), Util.fontPath);
			tv.setTypeface(font);
//			tv.setText("Therapist Busy \n From 12/12/2013 12:00 to 12/12/2013 1:00");
			
			tv.setPadding(10, 10, 10, 10);
			tv.setTextColor(0xff4e3115);

			View viewHorizontal = new View(this);

			if (j % 2 != 0) {
				LinearLayout.LayoutParams ViewHorizontalParam = new LinearLayout.LayoutParams(
						LayoutParams.FILL_PARENT, 3);
				ViewHorizontalParam.gravity = Gravity.CENTER_VERTICAL;
				viewHorizontal.setLayoutParams(ViewHorizontalParam);
				viewHorizontal.setBackgroundColor(0xff4e3115);
				/** Even Child1 ***/
				if (j == 1)
					/** First Child **/
					layoutParamsParent.topMargin = 240;
				else

					layoutParamsParent.topMargin = 265 + k;

				Linear_Child1_Parent.setLayoutParams(layoutParamsParent);
				Linear_Child1_Child.addView(tv);
				Linear_Child1_Parent.addView(Linear_Child1_Child);

				Linear_Child1_Parent.addView(viewHorizontal);
				Linear_Child1.addView(Linear_Child1_Parent);

			} else {
				LinearLayout.LayoutParams ViewHorizontalParam = new LinearLayout.LayoutParams(
						60, 3);
				ViewHorizontalParam.gravity = Gravity.CENTER_VERTICAL;
				viewHorizontal.setLayoutParams(ViewHorizontalParam);
				viewHorizontal.setBackgroundColor(0xff4e3115);
				// Linear_Child1_Child.addView(viewHorizontal);

				// LinearLayout.LayoutParams ViewHorizontalParam = new
				// LinearLayout.LayoutParams(50, 3);
				// View viewHorizontal = new View(this);
				// ViewHorizontalParam.gravity=Gravity.CENTER_VERTICAL;
				// viewHorizontal.setLayoutParams(ViewHorizontalParam);
				// viewHorizontal.setBackgroundColor(0xff4e3115);

				/** Odd Child ***/
				if (j == 0)
					/** First Child **/
					layoutParamsParent.topMargin = 23;
				else
					layoutParamsParent.topMargin = 265 + k;

				Linear_Child1_Parent.setLayoutParams(layoutParamsParent);
				Linear_Child1_Parent.addView(viewHorizontal);
				Linear_Child1_Child.addView(tv);
				Linear_Child1_Parent.addView(Linear_Child1_Child);
				Linear_Child3.addView(Linear_Child1_Parent);
			}
			/*
			 * LinearLayout Linear_Child1_Parent = new LinearLayout(this);
			 * Linear_Child1_Parent.setOrientation(LinearLayout.HORIZONTAL);
			 * 
			 * LinearLayout Linear_Child1_Child = new LinearLayout(this);
			 * Linear_Child1_Child.setOrientation(LinearLayout.HORIZONTAL);
			 * 
			 * layoutParams = new LinearLayout.LayoutParams(250,
			 * LayoutParams.WRAP_CONTENT);
			 * Linear_Child1_Child.setBackground(getResources().getDrawable(
			 * R.drawable.drop_sawdow_bk));
			 * 
			 * TextView tv = new TextView(this);
			 */

			// Log.v("Appointment Time",
			// TherapyTimelineDetails.get(j).get(TherapistSchedule.Appointment_Start_Time).toString()+"  j: "+j);

			// String
			// Appointment_Time=TherapyTimelineDetails.get(j).get(TherapistSchedule.Appointment_Start_Time);
			// String
			// Appointment_End_Time=TherapyTimelineDetails.get(j).get(TherapistSchedule.Appointment_End_Time);
			// String Timeline_Data="Therapist Busy \n From "+ Appointment_Time
			// + " to "+Appointment_End_Time;
			// tv.setText(Timeline_Data);
			/*
			 * tv.setText(
			 * "Therapist Busy \n From 12/12/2013 12:00 to 12/12/2013 1:00");
			 * tv.setPadding(10, 10, 10, 10); tv.setTextColor(0xff4e3115);
			 * 
			 * 
			 * View viewHorizontal = new View(this); LinearLayout.LayoutParams
			 * ViewHorizontalParam = new LinearLayout.LayoutParams(50, 4);
			 * viewHorizontal.setBackgroundColor(0xff4e3115);
			 * 
			 * if (j % 2 != 0) { /*if (j == 1) { layoutParams.setMargins(0, 170,
			 * 0, 0); ViewHorizontalParam.setMargins(0, 230, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam);
			 * 
			 * } else { layoutParams.setMargins(0, 100, 0, 0);
			 * ViewHorizontalParam.setMargins(0, 170, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam); }
			 * 
			 * Linear_Child1_Child.setLayoutParams(layoutParams);
			 * 
			 * Linear_Child1_Child.addView(tv); // Add Text View to Linear //
			 * Layout
			 * 
			 * Linear_Child1_Parent.addView(Linear_Child1_Child);// Add Inner //
			 * layout to // parent
			 * 
			 * Linear_Child1_Parent.addView(viewHorizontal);// Add horizontal //
			 * line to // parent
			 * 
			 * Linear_Child1.addView(Linear_Child1_Parent); // Add parent to //
			 * grandparent
			 * 
			 * } else { if (j == 0) { layoutParams.setMargins(0, 50, 0, 0);
			 * ViewHorizontalParam.setMargins(0, 110, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam); } else {
			 * layoutParams.setMargins(0, 100, 0, 0);
			 * ViewHorizontalParam.setMargins(0, 170, 0, 0);
			 * viewHorizontal.setLayoutParams(ViewHorizontalParam); }
			 * 
			 * Linear_Child1_Child.setLayoutParams(layoutParams);
			 * 
			 * Linear_Child1_Child.addView(tv); // Add Text View to Linear //
			 * Layout
			 * 
			 * Linear_Child1_Parent.addView(viewHorizontal); // Add horizontal
			 * // line to // parent
			 * Linear_Child1_Parent.addView(Linear_Child1_Child); // Add Inner
			 * // layout to // parent
			 * 
			 * Linear_Child3.addView(Linear_Child1_Parent); // Add parent to //
			 * grandparent
			 * 
			 * }
			 */

		}

		// View view1 = new View(this);
		// view1.setLayoutParams(new ViewGroup.LayoutParams(4, 100));
		// view1.setBackgroundColor(0xff4e3115);
		// Linear_Child2.addView(view1);
		mainLayout.addView(sv);

		return mainLayout;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		onDestroy();
	}

}
