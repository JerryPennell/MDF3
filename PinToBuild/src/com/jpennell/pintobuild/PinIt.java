/*
 * project		PinToBuild
 * 
 * package		com.jpennell.pintobuild
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 24, 2013
 */

package com.jpennell.pintobuild;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

// TODO: Auto-generated Javadoc
/**
 * The Class PinUser.
 */
public class PinIt extends Activity {
	
	/** The web view. */
	WebView webView;
	
	final String DEFAULT_URL = "file:///android_asset/pinner.html";
	
	//updating

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_submit);

		//FORCE PORTRAIT ORIENTATION
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//CREATE AND GET REFERENCE TO WEBVIEW AND ADD JAVASCRIPT COMPATIBILITY
		
		WebView webView = (WebView) findViewById(R.id.webView1);
		WebSettings theWebSettings = webView.getSettings();
		
		webView.getSettings().setUserAgentString("Android");
		theWebSettings.setAllowFileAccess(true);
		theWebSettings.setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JSInterface(this), "Android");
		webView.loadUrl(DEFAULT_URL);


	}

	//CREATE WEB INTERFACE TO HANDLE JAVASCRIPT FUNCTIONALITY
	/**
	 * The Class JSInterface.
	 */
	public class JSInterface{
		
		/** The _context. */
		Context _context;

		/**
		 * Instantiates a new jS interface.
		 *
		 * @param c the c
		 */
		JSInterface(Context c){
			_context = c;
		}

		/**
		 * Send username over.
		 *
		 * @param user the user
		 * @return true, if successful
		 */
		@JavascriptInterface
		public boolean sendUsernameOver(String user){
			Log.i("user", user);
			Intent intent = new Intent(PinIt.this, MainActivity.class);
			intent.putExtra("USER",  user); 
	        startActivity(intent);

			return true;
		}
		
		/**
		 * Send username over.
		 *
		 * @param user the user
		 * @return true, if successful
		 */
		@JavascriptInterface
		public boolean sendUsernameOverToList(String user){
			Log.i("user", user);
			Intent intent = new Intent(PinIt.this, ListViewActivity.class);
			intent.putExtra("USER",  user); 
	        startActivity(intent);

			return true;
		}

	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}