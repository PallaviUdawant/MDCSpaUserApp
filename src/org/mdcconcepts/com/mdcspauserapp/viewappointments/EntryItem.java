package org.mdcconcepts.com.mdcspauserapp.viewappointments;

public class EntryItem implements Item{
	 
	 public final String TherapyName;
	 public final String Therapist_Name;
	 public final String Appointment_Time;
	 public final String Time_For_Service;
	 public final String Pricing;
	 public final String status;
	 public final String Spa_Id;
	 public final String Spa_Name;
	 public final String Therapist_Id;
	 public final String Appointment_Id;
	 
	 public EntryItem(String TherapyName, String Therapist_Name,String Appointment_Time,
			 String Time_For_Service,String Pricing,String status,String Spa_Id,String Spa_Name,
			 String Therapist_Id,String Appointment_Id) {
	  this.TherapyName = TherapyName;
	  this.Therapist_Name = Therapist_Name;
	  this.Appointment_Time = Appointment_Time;
	  this.Time_For_Service = Time_For_Service;
	  this.Pricing = Pricing;
	  this.status = status;
	  this.Spa_Id=Spa_Id;
	  this.Spa_Name=Spa_Name;
	  this.Therapist_Id=Therapist_Id;
	  this.Appointment_Id=Appointment_Id;
	 }
	  
	 @Override
	 public boolean isSection() {
	  return false;
	 }
	 
	}