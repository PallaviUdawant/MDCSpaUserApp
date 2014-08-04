package org.mdcconcepts.com.mdcspauserapp.GmailSync;

import org.mdcconcepts.com.mdcspauserapp.AppSharedPreferences;
import org.mdcconcepts.com.mdcspauserapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GmailContactSyncFragment extends Activity {

	// private SharedPreferences prefs;

	// ArrayList<String> ContactsArrayList = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_gmail_fragment);

		final WebView browser = (WebView) findViewById(R.id.webView);

		final Intent i = new Intent(GmailContactSyncFragment.this,
				GmailContactListActivity.class);

		if (AppSharedPreferences.getGmailDataStatus(this)) {
			browser.setVisibility(View.GONE);
			startActivity(i);
			finish();

		} else {

			final Context myApp = this;

			class MyJavaScriptInterface {
				@JavascriptInterface
				public void showHTML(String html) {

					// new AlertDialog.Builder(myApp).setTitle("HTML")
					// .setMessage(html.trim())
					// .setPositiveButton(android.R.string.ok, null)
					// .setCancelable(false).create().show();

					if (!AppSharedPreferences.getGmailDataStatus(myApp)
							&& html.trim() != null) {
						AppSharedPreferences.setGmailContactList(myApp,
								html.trim());
						AppSharedPreferences.setGmailDataStatus(myApp, true);
						startActivity(i);
						finish();
					}
					// Log.d("html Text", html);

					// JSONArray PostJson = json.getJSONArray("posts");
				}

			}

			/* JavaScript must be enabled if you want it to work, obviously */
			browser.getSettings().setJavaScriptEnabled(true);

			/* Register a new JavaScript interface called HTMLOUT */
			browser.addJavascriptInterface(new MyJavaScriptInterface(),
					"HTMLOUT");

			/* WebViewClient must be set BEFORE calling loadUrl! */
			browser.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
					/*
					 * This call inject JavaScript into the page which just
					 * finished loading.
					 */
					// browser.lo
					// adUrl("javascript:window.HTMLOUT.showHTML(document.body.getElementsByTagName('div')[0].innerHTML);");
					browser.loadUrl("javascript:window.HTMLOUT.showHTML(document.getElementById('MDCSPA_Email_Ids').innerHTML);");
				}
			});

			/* load a web page */
			browser.loadUrl("https://accounts.google.com/o/oauth2/auth?client_id=1078376164827-ejfad8gtg9bvgqpktnuuu8c2vb6gehkv.apps.googleusercontent.com&redirect_uri=http://mdcspa.mdcconcepts.com/OAuth_Php/oauth.php&scope=https://www.google.com/m8/feeds/&response_type=code");

		}
	}

	/*
	 * setContentView(R.layout.fragment_gmail_sync); this.prefs =
	 * PreferenceManager.getDefaultSharedPreferences(this);
	 * ListViewController_Gmail_Sync
	 * =(ListView)findViewById(R.id.ListViewController_Gmail_Sync); // new
	 * GetContactsAsyncTask().execute();
	 * 
	 * 
	 * 
	 * console = (TextView) findViewById(R.id.text_console); // Button
	 * launchOauth = (Button) findViewById(R.id.button_start_oauth); Button
	 * clearCredentials = (Button) findViewById(R.id.button_delete_tokens);
	 * Button getContacts = (Button) findViewById(R.id.button_get_contacts);
	 * 
	 * launchOauth.setOnClickListener(new View.OnClickListener() { public void
	 * onClick(View v) { startActivity(new Intent().setClass(v.getContext(),
	 * RequestTokenActivity.class));
	 * 
	 * } });
	 * 
	 * clearCredentials.setOnClickListener(new View.OnClickListener() { public
	 * void onClick(View v) { clearCredentials();
	 * console.setText("Tokens deleted, getContacts call should fail now."); }
	 * });
	 * 
	 * getContacts.setOnClickListener(new View.OnClickListener() { public void
	 * onClick(View v) { getContacts(); } });
	 */

	// }

	// public class GetContactsAsyncTask extends AsyncTask<String, String,
	// String>
	// {
	//
	// @Override
	// protected String doInBackground(String... params) {
	// // TODO Auto-generated method stub
	// startActivity(new Intent().setClass(GmailContactSyncFragment.this,
	// RequestTokenActivity.class));
	// getContacts();
	// return null;
	// }
	//
	// }
	//
	// private void getContacts() {
	//
	//
	// String jsonOutput = "";
	// try {
	// jsonOutput =
	// makeSecuredReq(Util.GET_CONTACTS_FROM_GOOGLE_REQUEST,getConsumer(this.prefs));
	// JSONObject jsonResponse = new JSONObject(jsonOutput);
	// // Log.d("ContactList", jsonResponse.toString());
	// JSONObject m = (JSONObject)jsonResponse.get("feed");
	// JSONArray entries =(JSONArray)m.getJSONArray("entry");
	// String contacts = "";
	// Log.d("entries.length()", String.valueOf(entries.length()));
	// for (int i=0 ; i<entries.length() ; i++) {
	// JSONObject entry = entries.getJSONObject(i);
	// JSONArray title = entry.getJSONArray("gd$email");
	// Log.d("title.length()",
	// String.valueOf(title.length())+" "+title.toString());
	// for(int j=0;j<title.length();j++)
	// {
	// JSONObject entry1 = title.getJSONObject(j);
	// ContactsArrayList.add(entry1.getString("address"));
	//
	// contacts += entry1.getString("address") + "\n";
	// Log.d("ContactList", entry1.getString("address"));
	//
	// }
	// // if (title.getString("$t")!=null && title.getString("$t").length()>0) {
	// // contacts += title.getString("$t") + "\n";
	// // Log.d("ContactList", title.getString("$t"));
	// // }
	// }
	// // Array[] a=new
	// String[] namesArr = ContactsArrayList.toArray(new
	// String[ContactsArrayList.size()]);
	//
	// ArrayAdapter<String> adapter = new ArrayAdapter<String>(this ,
	// android.R.layout.simple_list_item_1, android.R.id.text1, namesArr);
	// ListViewController_Gmail_Sync.setAdapter(adapter);
	//
	// console.setText(contacts);
	// } catch (Exception e) {
	// Log.e(Util.TAG, "Error executing request",e);
	// console.setText("Error retrieving contacts : " + jsonOutput);
	// }
	// }
	//
	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// if (isOAuthSuccessful()) {
	// // OAuth successful, try getting the contacts
	// console.setText("OAuth successful, try getting the contacts");
	// }
	// else {
	// console.setText("OAuth failed, no tokens, Click on the Do OAuth Button.");
	// }
	// }
	//
	// private void clearCredentials() {
	// SharedPreferences prefs =
	// PreferenceManager.getDefaultSharedPreferences(this);
	// final Editor edit = prefs.edit();
	// edit.remove(OAuth.OAUTH_TOKEN);
	// edit.remove(OAuth.OAUTH_TOKEN_SECRET);
	// edit.commit();
	// }
	//
	// private boolean isOAuthSuccessful() {
	// String token = prefs.getString(OAuth.OAUTH_TOKEN, null);
	// String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, null);
	// if (token != null && secret != null)
	// return true;
	// else
	// return false;
	// }
	//
	//
	// private OAuthConsumer getConsumer(SharedPreferences prefs) {
	// String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
	// String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
	// OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Util.CONSUMER_KEY,
	// Util.CONSUMER_SECRET);
	// consumer.setTokenWithSecret(token, secret);
	//
	// return consumer;
	// }
	//
	// private String makeSecuredReq(String url,OAuthConsumer consumer) throws
	// Exception {
	// DefaultHttpClient httpclient = new DefaultHttpClient();
	// HttpGet request = new HttpGet(url);
	// Log.i(Util.TAG,"Requesting URL : " + url);
	// consumer.sign(request);
	// HttpResponse response = httpclient.execute(request);
	// Log.i(Util.TAG,"Statusline : " + response.getStatusLine());
	// InputStream data = response.getEntity().getContent();
	// BufferedReader bufferedReader = new BufferedReader(new
	// InputStreamReader(data));
	// String responeLine;
	// StringBuilder responseBuilder = new StringBuilder();
	// while ((responeLine = bufferedReader.readLine()) != null) {
	// responseBuilder.append(responeLine);
	// }
	// Log.i(Util.TAG,"Response : " + responseBuilder.toString());
	// return responseBuilder.toString();
	// }
}