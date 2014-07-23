package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import java.util.HashMap;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalMakeAppointmentActivity extends Activity {

	TextView final_spa_name,final_spa_therapy,final_time_for_service,final_price,final_date,final_time,final_therapist;
	TextView txt_final_spa_name,txt_final_spa_therapy,txt_final_time_for_service,txt_final_price,txt_final_date,txt_final_time,txt_final_therapist;
	HashMap<String, String> AllDetails = new HashMap<String, String>();
	Typeface font;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_make_appointment);
		
		Intent i=getIntent();
		AllDetails=(HashMap<String, String>) i.getSerializableExtra("AllDetails");
		
		txt_final_spa_name=(TextView)findViewById(R.id.txt_final_spa_name);
		txt_final_spa_therapy=(TextView)findViewById(R.id.txt_final_spa_therapy);
		txt_final_time_for_service=(TextView)findViewById(R.id.txt_final_time_for_service);
		txt_final_price=(TextView)findViewById(R.id.txt_final_price);
		txt_final_date=(TextView)findViewById(R.id.txt_final_date);
		txt_final_time=(TextView)findViewById(R.id.txt_final_time);
		txt_final_therapist=(TextView)findViewById(R.id.txt_final_therapist);
		
		final_spa_name=(TextView)findViewById(R.id.final_spa_name);
		final_spa_therapy=(TextView)findViewById(R.id.final_spa_therapy);
		final_time_for_service=(TextView)findViewById(R.id.final_time_for_service);
		final_price=(TextView)findViewById(R.id.final_price);
		final_date=(TextView)findViewById(R.id.final_date);
		final_time=(TextView)findViewById(R.id.final_time);
		final_therapist=(TextView)findViewById(R.id.final_therapist);
		
		font=Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");
		
		txt_final_spa_name.setTypeface(font);
		txt_final_spa_therapy.setTypeface(font);
		txt_final_time_for_service.setTypeface(font);
		txt_final_price.setTypeface(font);
		txt_final_date.setTypeface(font);
		txt_final_time.setTypeface(font);
		txt_final_therapist.setTypeface(font);
		
		final_spa_name.setTypeface(font);
		final_spa_therapy.setTypeface(font);
		final_time_for_service.setTypeface(font);
		final_price.setTypeface(font);
		final_date.setTypeface(font);
		final_time.setTypeface(font);
		final_therapist.setTypeface(font);
		
		final_spa_name.setText(AllDetails.get("Spa_Name"));
		final_spa_therapy.setText(AllDetails.get("Therapy_Name"));
		final_time_for_service.setText(AllDetails.get("Therapy_Time"));
		final_price.setText(AllDetails.get("Therapy_Price"));
		final_date.setText(AllDetails.get("Selected_Date"));
		final_time.setText(AllDetails.get("Selected_Time"));
		final_therapist.setText(AllDetails.get("Selected_Therapist"));
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		ImageView view = (ImageView)findViewById(android.R.id.home);
		view.setPadding(10, 10, 10, 10);
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
}
