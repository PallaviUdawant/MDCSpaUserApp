package org.mdcconcepts.com.mdcspauserapp.viewappointments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mdcconcepts.com.mdcspauserapp.R;
import org.mdcconcepts.com.mdcspauserapp.serverhandler.JSONParser;
import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAppointmentFragment extends Fragment {

	public ViewAppointmentFragment() {
	}

	ListView listViewController_Appointmentlist;
	
	TextView textView_Current_Appointments;
	TextView textView_History;
	JSONParser jsonParser = new JSONParser();
	ArrayList<String> ArrayList_AppointMent_Service = new ArrayList<String>();
	ArrayList<String> ArrayList_AppointMent_Time = new ArrayList<String>();
	ArrayList<String> ArrayList_AppointMentIDList = new ArrayList<String>();
	// ids
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_viewappointment,
				container, false);
		listViewController_Appointmentlist = (ListView) rootView
				.findViewById(R.id.listViewController_Appointmentlist);

		textView_Current_Appointments=(TextView) rootView
				.findViewById(R.id.textView_Current_Appointments);
		textView_History=(TextView) rootView
		.findViewById(R.id.textView_History);
		
		Typeface font = Typeface.createFromAsset(rootView.getContext().getAssets(), Util.fontPath);
		textView_Current_Appointments.setTypeface(font,Typeface.BOLD);
		textView_History.setTypeface(font,Typeface.BOLD);
		
		ArrayList_AppointMent_Service.add("Lorem Ipsum 1");
		ArrayList_AppointMent_Service.add("Lorem Ipsum 2");

		ArrayList_AppointMent_Time.add("13/07/2014 12.00 pm");
		ArrayList_AppointMent_Time.add("15/07/2014 2.00 pm");

		ArrayList_AppointMentIDList.add("1");
		ArrayList_AppointMentIDList.add("2");
		
		ViewAppointmentCustomList adapter = new ViewAppointmentCustomList(
				getActivity(), ArrayList_AppointMent_Service,
				ArrayList_AppointMent_Time);
		listViewController_Appointmentlist.setAdapter(adapter);
		
		ListView listViewController_History=(ListView)rootView.findViewById(R.id.listViewController_History);

		ViewAppointmentCustomList adapter1 = new ViewAppointmentCustomList(
				getActivity(), ArrayList_AppointMent_Service,
				ArrayList_AppointMent_Time);
		ArrayList_AppointMent_Time.clear();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
		ArrayList_AppointMent_Time.add("03/06/2014 12.00 pm");
		ArrayList_AppointMent_Time.add("05/06/2014 2.00 pm");
		
		listViewController_History.setAdapter(adapter1);
		
//		new GetAppointments().execute();
		return rootView;
	}

	class GetAppointments extends AsyncTask<String, String, String> {

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
			pDialog.setMessage("Getting Your Appointments ... ");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag

			String Uid = "" + Util.Uid;

			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("Uid", Uid));

				Log.d("request!", "starting");

				// Posting user data to script
				JSONObject json = jsonParser.makeHttpRequest(
						Util.Get_Appointments_URL, "POST", params);

				// full json response
				Log.d("Login attempt", json.toString());

				// json success element
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {

					JSONArray PostJson = json.getJSONArray("posts");
					Log.d("Post Date ", PostJson.toString());
					for (int i = 0; i < PostJson.length(); i++) {

						JSONObject Temp = PostJson.getJSONObject(i);
						ArrayList_AppointMent_Service.add(Temp
								.getString("name"));
						ArrayList_AppointMent_Time.add(Temp
								.getString("Appointment_DateTime"));
						ArrayList_AppointMentIDList.add(Temp
								.getString("Appointment_Id"));

					}
					return json.getString(TAG_MESSAGE) + " , Please Login !";
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
				if (success == 1) {

					ViewAppointmentCustomList adapter = new ViewAppointmentCustomList(
							getActivity(), ArrayList_AppointMent_Service,
							ArrayList_AppointMent_Time);
					listViewController_Appointmentlist.setAdapter(adapter);
					listViewController_Appointmentlist
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {

									Intent intentGetMessage = new Intent(
											getActivity(),
											SeeApointmentActivity.class);

									intentGetMessage.putExtra("Appointment_Id",
											ArrayList_AppointMentIDList
													.get(position));

									startActivity(intentGetMessage);

									// // Toast.makeText(
									// // ManageAppointmentActivity.this,
									// // "You Clicked at "
									// // + ArrayList_AppointMent_Header
									// // .get(+position),
									// // Toast.LENGTH_SHORT).show();
									// Intent myIntent = new Intent(
									// ManageAppointmentActivity.this,
									// UpdateAppointmentActivity.class);
									//
									// int Header = Integer
									// .parseInt(ArrayList_AppointMent_Header
									// .get(+position));
									//
									// Log.d("request!", "" + Header);
									//
									// Util.Appointment_Id = Header;
									// startActivity(myIntent);
								}
							});
				}
			}

		}
	}
}
