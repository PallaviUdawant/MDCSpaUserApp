package org.mdcconcepts.com.mdcspauserapp.GmailSync;

import java.net.URLEncoder;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.mdcconcepts.com.mdcspauserapp.util.Util;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;


public class RequestTokenActivity extends Activity {

	
	
    private OAuthConsumer consumer; 
    private OAuthProvider provider;
    private SharedPreferences prefs;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	try {
    		consumer = new CommonsHttpOAuthConsumer(Util.CONSUMER_KEY, Util.CONSUMER_SECRET);
    		provider = new CommonsHttpOAuthProvider(
    				Util.REQUEST_URL  + "?scope=" + URLEncoder.encode(Util.SCOPE, Util.ENCODING) + "&xoauth_displayname=" + Util.APP_NAME,
    				Util.ACCESS_URL,
    				Util.AUTHORIZE_URL);
    	} catch (Exception e) {
    		Log.e(Util.TAG, "Error creating consumer / provider",e);
    	}

    	getRequestToken();
    }

	
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent); 
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		final Uri uri = intent.getData();
		if (uri != null && uri.getScheme().equals(Util.OAUTH_CALLBACK_SCHEME)) {
			Log.i(Util.TAG, "Callback received : " + uri);
			Log.i(Util.TAG, "Retrieving Access Token");
			getAccessToken(uri);
		}
	}
	
	private void getRequestToken() {
		try {
			Log.d(Util.TAG, "getRequestToken() called");
			String url = provider.retrieveRequestToken(consumer, Util.OAUTH_CALLBACK_URL);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			this.startActivity(intent);
			
		} catch (Exception e) {
			Log.e(Util.TAG, "Error retrieving request token", e);
		}
	}
	
	private void getAccessToken(Uri uri) {
		final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);
		try {
			provider.retrieveAccessToken(consumer, oauth_verifier);

			final Editor edit = prefs.edit();
			edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
			edit.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
			edit.commit();
			
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			
			consumer.setTokenWithSecret(token, secret);
			this.startActivity(new Intent(this ,GmailContactSyncFragment.class));

			Log.i(Util.TAG, "Access Token Retrieved");
			
		} catch (Exception e) {
			Log.e(Util.TAG, "Access Token Retrieval Error", e);
		}
	}
	
}
