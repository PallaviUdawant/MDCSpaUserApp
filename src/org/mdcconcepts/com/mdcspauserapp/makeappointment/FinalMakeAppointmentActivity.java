package org.mdcconcepts.com.mdcspauserapp.makeappointment;

import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class FinalMakeAppointmentActivity extends Activity {

	TextView final_spa_name,final_spa_therapy,final_time_for_service,final_price,final_date,final_time;
	TextView txt_final_spa_name,txt_final_spa_therapy,txt_final_time_for_service,txt_final_price,txt_final_date,txt_final_time;
	Typeface font;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_make_appointment);
		
		txt_final_spa_name=(TextView)findViewById(R.id.txt_final_spa_name);
		txt_final_spa_therapy=(TextView)findViewById(R.id.txt_final_spa_therapy);
		txt_final_time_for_service=(TextView)findViewById(R.id.txt_final_time_for_service);
		txt_final_price=(TextView)findViewById(R.id.txt_final_price);
		txt_final_date=(TextView)findViewById(R.id.txt_final_date);
		txt_final_time=(TextView)findViewById(R.id.txt_final_time);
		
		final_spa_name=(TextView)findViewById(R.id.final_spa_name);
		final_spa_therapy=(TextView)findViewById(R.id.final_spa_therapy);
		final_time_for_service=(TextView)findViewById(R.id.final_time_for_service);
		final_price=(TextView)findViewById(R.id.final_price);
		final_date=(TextView)findViewById(R.id.final_date);
		final_time=(TextView)findViewById(R.id.final_time);
		
		font=Typeface.createFromAsset(getAssets(), "Raleway-Light.otf");
		
		txt_final_spa_name.setTypeface(font);
		txt_final_spa_therapy.setTypeface(font);
		txt_final_time_for_service.setTypeface(font);
		txt_final_price.setTypeface(font);
		txt_final_date.setTypeface(font);
		txt_final_time.setTypeface(font);
		final_spa_name.setTypeface(font);
		final_spa_therapy.setTypeface(font);
		final_time_for_service.setTypeface(font);
		final_price.setTypeface(font);
		final_date.setTypeface(font);
		final_time.setTypeface(font);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
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
