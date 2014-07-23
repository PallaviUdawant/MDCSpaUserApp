package org.mdcconcepts.com.mdcspauserapp.viewappointments;

import java.util.ArrayList;

import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntryAdapter extends ArrayAdapter<Item> {
	 
	 private ArrayList<Item> items;
	 private LayoutInflater vi;
	 
	 public EntryAdapter(Context context,ArrayList<Item> items) {
	  super(context,0, items);
	  this.items = items;
	  vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 }
	 
	 
	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
	  View v = convertView;
	 
	  final Item i = items.get(position);
	  if (i != null) {
	   if(i.isSection()){
	    SectionItem si = (SectionItem)i;
	    v = vi.inflate(R.layout.history_header, null);
	 
	    v.setOnClickListener(null);
	    v.setOnLongClickListener(null);
	    v.setLongClickable(false);
	    TextView history_header=(TextView)v
				.findViewById(R.id.history_header);
	 Typeface font = Typeface.createFromAsset(v.getContext()                                                                                                                                                                          
				.getAssets(), Util.fontPath);
	 history_header.setTypeface(font, Typeface.BOLD);
	 history_header.setText(si.getTitle());
//	    final TextView sectionView = (TextView) v.findViewById(R.id.list_item_section_text);
//	    sectionView.setText(si.getTitle());
	     
	   }else{
	    EntryItem ei = (EntryItem)i;
	    v = vi.inflate(R.layout.view_appointment__single_list, null);
	    
	    TextView txt_therapyname = (TextView) v
				.findViewById(R.id.TextviewController_Services);

		TextView TextView_TimeDate = (TextView) v
				.findViewById(R.id.TextviewController_TIme);
		
		TextView textView_Therapist = (TextView) v
				.findViewById(R.id.textView_Therapist);
		
		TextView textView_TimeForService = (TextView) v
				.findViewById(R.id.textView_TimeForService);
		
		TextView textView_Price = (TextView) v
				.findViewById(R.id.textView_Price);
		TextView  textView_Spa= (TextView) v
				.findViewById(R.id.textView_Spa);
		
		txt_therapyname.setText(ei.TherapyName);
		TextView_TimeDate.setText(ei.Appointment_Time);
		textView_Therapist.setText("Therapist: "+ei.Therapist_Name);
		textView_TimeForService.setText("Service Time : "+ei.Time_For_Service);
		textView_Price.setText("Price : Rs. "+ei.Pricing);
		textView_Spa.setText(ei.Spa_Name);
		
		Typeface font = Typeface.createFromAsset(v.getContext()                                                                                                                                                                          
				.getAssets(), Util.fontPath);
		
		
		txt_therapyname.setTypeface(font,Typeface.BOLD);
		TextView_TimeDate.setTypeface(font,Typeface.BOLD);
		textView_Therapist.setTypeface(font);
		textView_TimeForService.setTypeface(font);
		textView_Price.setTypeface(font);
		textView_Spa.setTypeface(font,Typeface.BOLD);
		v.setTag(ei);
		
//	    final TextView title = (TextView)v.findViewById(R.id.list_item_entry_title);
//	    final TextView subtitle = (TextView)v.findViewById(R.id.list_item_entry_summary);
	     
//	     
//	    if (title != null) 
//	     title.setText(ei.title);
//	    if(subtitle != null)
//	     subtitle.setText(ei.subtitle);
	   }
	  }
	  return v;
	 }
	 
	}
