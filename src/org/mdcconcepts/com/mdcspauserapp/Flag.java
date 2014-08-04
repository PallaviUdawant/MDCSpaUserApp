package org.mdcconcepts.com.mdcspauserapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Flag 
{
	
	public static void setFlag(Context context, boolean flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean("SET_MY_FLAG", flag);
        editor.commit();
     
    }
	
	public static boolean getFlag(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        boolean isRegistered = prefs.getBoolean("SET_MY_FLAG", false);
        return isRegistered;
    }
	public static void setisPostedFlag(Context context, boolean flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean("SET_IMG_FLAG", flag);
        editor.commit();
     
    }
	
	public static boolean getisPostedFlag(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        boolean isRegistered = prefs.getBoolean("SET_POST_FLAG", false);
        return isRegistered;
    }
	public static boolean getimgFlag(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        boolean isRegistered = prefs.getBoolean("SET_IMG_FLAG", false);
        return isRegistered;
    }
	public static void setimgFlag(Context context, boolean flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean("SET_POST_FLAG", flag);
        editor.commit();
     
    }
	public static boolean getShowcaseFlag(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        boolean isRegistered = prefs.getBoolean("SET_SHOWCASE_FLAG", false);
        return isRegistered;
    }
	public static void setShowcaseFlag(Context context, boolean flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean("SET_SHOWCASE_FLAG", flag);
        editor.commit();
     
    }
	public static boolean getGroupFlag(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        boolean isRegistered = prefs.getBoolean("SET_GROUP_FLAG", false);
        return isRegistered;
    }
	public static void setGroupFlag(Context context, boolean flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean("SET_GROUP_FLAG", flag);
        editor.commit();
     
    }
	public static String getPath(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        String isRegistered = prefs.getString("File", "");
        return isRegistered;
    }
	public static void setPath(Context context, String flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("File", flag);
        editor.commit();
     
    }
	public static String getGroupName(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        String isRegistered = prefs.getString("GROUP_NAME", "5 Star Foodies");
        return isRegistered;
    }
	public static void setGroupName(Context context, String flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("GROUP_NAME", flag);
        editor.commit();
     
    }

	public static Boolean getLinkedGroupName(Context context)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Boolean isRegistered = prefs.getBoolean("LINKED_GROUP_NAME", false);
        return isRegistered;
    }
	public static void setLinkedGroupName(Context context, Boolean flag)
	{
        final SharedPreferences prefs = getMyPreferences(context);
        Editor editor = prefs.edit();
        editor.putBoolean("LINKED_GROUP_NAME", flag);
        editor.commit();
     
    }
	 private static SharedPreferences getMyPreferences(Context context) {
	        return context.getSharedPreferences("MyPrefrences", Context.MODE_PRIVATE);
	    }



}
