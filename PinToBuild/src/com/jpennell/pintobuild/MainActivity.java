/*
 * project		PinToBuild
 * 
 * package		com.jpennell.pintobuild
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 23, 2013
 */

package com.jpennell.pintobuild;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {
	
	 public static final String TAG = "MainActivityCall";
	 
	 public static final String CLIENT_ID = "1433332";
	 private ListView mList;
	 private ArrayAdapter<String> mAdapter;

	/** The spinner. */
	Spinner spinner;
	
	/** The array boards. */
	ArrayList<String> arrayBoards = new ArrayList<String>();
	
	/** The my handler. */
	final Handler myHandler = new Handler();
	
	/** The spin item. */
	String spinItem;


	/**
	 * Called when the activity is first created.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new MyAsyncTask().execute();

	}
	
	

	/**
 * The Class MyTask.
 */
private class MyAsyncTask extends AsyncTask<Void, Void, ArrayList<String>> {

       //Set the pintrest URL
		/** The Constant PINTREST_URL. */
       protected static final String PINTREST_URL = "http://m.pinterest.com/";


		/* (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected ArrayList<String> doInBackground(Void... params) {

			Document doc;
			String linkText = ""; 

			try {
				doc = Jsoup.connect(PINTREST_URL + getIntent().getExtras().getString("USER")).get();
				Elements links = doc.select("div.user_boards ul li a span");
				arrayBoards.add("Select");
				for (Element el : links) { 
					linkText = el.text();

					arrayBoards.add(linkText); 
					Log.i("LINK: ",linkText);
				}
			} catch (IOException e) {
				Log.e(PINTREST_URL, e.getMessage());
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return arrayBoards;     


		} 


		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(ArrayList<String> result) {        
			
			// Create an ArrayAdapter using the string array and a default spinner layout
			Spinner spinner = (Spinner) findViewById(R.id.spinner); 
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayBoards);
			
			// Specify the layout to use when the list of choices appears
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
			
			// Apply the adapter to my spinner
			spinner.setAdapter(adapter);
			Log.i("Spinner", "spinner populated");

			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onNothingSelected(AdapterView<?> parent) {
				}

				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
					
					if(pos > 0){
						spinItem = parent.getItemAtPosition(pos).toString();
						if (spinItem.equals("Select") || spinItem == "Select"){
						} else {
							String myParent = parent.getItemAtPosition(pos).toString().toLowerCase();	
							myParent = myParent.replace(' ', '-');
							String theURL = (PINTREST_URL +  getIntent().getExtras().getString("USER") +"/" + myParent);

							WebView boardWebView = (WebView) findViewById(R.id.webview);
							boardWebView.loadUrl(theURL);

							Log.i("name", parent.getItemAtPosition(pos).toString());
						}
					}

				}
			});
		}
	}
}