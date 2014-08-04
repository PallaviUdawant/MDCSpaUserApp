package org.mdcconcepts.com.mdcspauserapp;

import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSharedPreferences {

	/**
	 * Get Preferance object
	 */
	private static SharedPreferences getMyPreferences(Context context) {
		return context.getSharedPreferences(Util.APP_PREFERENCES,
				Context.MODE_PRIVATE);
	}
	
	/**
	 * Set Login Status
	 * @param context
	 * @param value
	 */
	public static void setLoginStatus(Context context, boolean value)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("IsLogin", value);
		editor.commit();
	}
	
	/**
	 * Get Login Status
	 * @param context
	 * @return
	 */
	public static boolean getLoginStatus(Context context)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("IsLogin", false);
		return value;
	}
	
	/**
	 * Set UID
	 * @param context
	 * @param value
	 */
	public static void setUID(Context context, String value)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("Uid", value);
		editor.commit();
	}
	
	/**
	 * Get UID
	 * @param context
	 * @return
	 */
	public static String getUID(Context context)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		String value = prefs.getString("Uid", "0");
		return value;
	}
	
	/**
	 * Set UserName
	 * @param context
	 * @param value
	 */
	public static void setUserName(Context context, String value)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("UserName", value);
		editor.commit();
	}
	
	
	
	/**
	 * Get UserName
	 * @param context
	 * @return
	 */
	public static String getUserName(Context context)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		String value = prefs.getString("UserName", "");
		return value;
	}
	
	/**
	 * Set UserName
	 * @param context
	 * @param value
	 */
	public static void setPassword(Context context, String value)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("Password", value);
		editor.commit();
	}
	
	
	
	/**
	 * Get UserName
	 * @param context
	 * @return
	 */
	public static String getPassword(Context context)
	{
		final SharedPreferences prefs = getMyPreferences(context);
		String value = prefs.getString("Password", "");
		return value;
	}

	/**
	 * Set if Contacts Database Created
	 * 
	 * @param context
	 * @param value
	 */
	public static void setDatabaseCreatedStatus(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Is_First_Run", value);
		editor.commit();

	}

	/**
	 * Get if Contacts Database Created
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getDatabaseCreatedStatus(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Is_First_Run", false);
		return value;
	}
	
	/**
	 * Set Contacts Synced Status
	 * 
	 * @param context
	 * @param value
	 */
	public static void setContactSyncStatus(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Is_Contact_Sync", value);
		editor.commit();

	}
	
	/**
	 * Get if Contacts Synced Status
	 * 
	 * @param context
	 * @return
	 */
	public static boolean getContactSyncStatus(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Is_Contact_Sync", false);
		return value;
	}

	/**
	 * Set Push Notification-Notification Status
	 * @param context
	 * @param value
	 */
	public static void setPush_Noti_Notify_Status(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Switch_Controller_Push_Noti_Notify", value);
		editor.commit();

	}
	/**
	 * Get Push Notification-Notification Status
	 * @param context
	 * @return
	 */
	public static boolean getPush_Noti_Notify_Status(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Switch_Controller_Push_Noti_Notify", true);
		return value;
	}
	
	
	/**
	 * Set Push Notification-Vibrate Status
	 * @param context
	 * @param value
	 */
	public static void setPush_Noti_Vibrate_Status(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Switch_Controller_Push_Noti_Vibrate", value);
		editor.commit();

	}
	/**
	 * Get Push Notification-Vibrate Status
	 * @param context
	 * @return
	 */
	public static boolean getPush_Noti_Vibrate_Status(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Switch_Controller_Push_Noti_Vibrate", true);
		return value;
	}
	
	/**
	 * Set Push Notification-Ringtone Status
	 * @param context
	 * @param value
	 */
	public static void setPush_Noti_Ringtone_Status(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Switch_Controller_Push_Noti_Rigtone", value);
		editor.commit();

	}
	/**
	 * Get Push Notification-Ringtone Status
	 * @param context
	 * @return
	 */
	public static boolean getPush_Noti_Ringtone_Status(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Switch_Controller_Push_Noti_Rigtone", true);
		return value;
	}
	
	/**
	 * Set Push Notification-Light Status
	 * @param context
	 * @param value
	 */
	public static void setPush_Noti_Light_Status(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Switch_Controller_Push_Noti_Light", value);
		editor.commit();

	}
	/**
	 * Get Push Notification-Light Status
	 * @param context
	 * @return
	 */
	public static boolean getPush_Noti_Light_Status(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Switch_Controller_Push_Noti_Light", true);
		return value;
	}
	
	
	/**
	 * Set Email Notification-Email  Status
	 * @param context
	 * @param value
	 */
	public static void setEmail_Noti_Email_Status(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Switch_Controller_Email_Noti_Notify", value);
		editor.commit();

	}
	/**
	 * Get Push Notification-Light Status
	 * @param context
	 * @return
	 */
	public static boolean getEmail_Noti_Email_Status(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Switch_Controller_Email_Noti_Notify", true);
		return value;
	}

	
	/**
	 * Set User whishlist
	 * @param context
	 * @param value
	 */
	public static void setUserWishList(Context context, String value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("User_WishList", value);
		editor.commit();

	}
	/**
	 * Get User whishlist
	 * @param context
	 * @return
	 */
	public static String getUserWishList(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		String value = prefs.getString("User_WishList", null);
		return value;
	}
	
	/**
	 * Set User whishlist
	 * @param context
	 * @param value
	 */
	public static void setGmailDataStatus(Context context, boolean value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean("Gmail_Data_Status", value);
		editor.commit();

	}
	/**
	 * Get User whishlist
	 * @param context
	 * @return
	 */
	public static boolean getGmailDataStatus(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		boolean value = prefs.getBoolean("Gmail_Data_Status", false);
		return value;
	}
	
	
	
	/**
	 * Set GmailContactlist
	 * @param context
	 * @param value
	 */
	public static void setGmailContactList(Context context, String value) {
		final SharedPreferences prefs = getMyPreferences(context);
		Editor editor = prefs.edit();
		editor.putString("GmailContactList", value);
		editor.commit();

	}
	/**
	 * Get User GmailContactlist
	 * @param context
	 * @return
	 */
	public static String getGmailContactList(Context context) {
		final SharedPreferences prefs = getMyPreferences(context);
		String value = prefs.getString("GmailContactList", null);
		return value;
	}
	
}
