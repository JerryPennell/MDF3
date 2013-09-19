/*
 * project		WCWidget
 * 
 * package		com.jpennell.widget
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 17, 2013
 */
package com.jpennell.widget;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.jpennell.library.Web;


// TODO: Auto-generated Javadoc
/**
 * The Class WeatherWidgetRefresh.
 */
public class WeatherWidgetRefresh{
    // Global Variables
    /** The desc. */
    public static String desc;
    
    /** The temp. */
    public static String temp;


    /**
     * Refresh.
     *
     * @param zip the zip
     */
    public static void refresh(String zip) {
        String baseUrl = "http://api.worldweatheronline.com/free/v1/weather.ashx";
        String apiKey = "qsxcvw8kpztq9hpwjsm3yaa6";
        String qs = "";
        try {
            qs = URLEncoder.encode(zip, "UTF-8");
        } catch (Exception e) {
            Log.e("BAD URL", "ENCODING PROBLEM");
        }

        URL finalURL;
        try {
            finalURL = new URL(baseUrl + "?q=" + qs + "&format=json&key=" + apiKey);
            // Call weatherRequest method
            weatherRequest wr = new weatherRequest();
            wr.execute(finalURL);
        } catch (MalformedURLException e) {
            Log.e("BAD URL", "MalformedURLException");
            finalURL = null;
        }
    }

    /**
     * The Class weatherRequest.
     */
    private static class weatherRequest extends AsyncTask<URL, Void, String> {

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        @Override
        protected String doInBackground(URL...urls) {
            String response = "";
            for (URL url:urls) {
                response = Web.getURLStringResponse(url);
            }
            return response;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        @Override
        protected void onPostExecute(String result) {
            Log.i("URL RESPONSE", result);

            try {
                // Pull JSON data from API
                JSONObject json = new JSONObject(result);
                JSONObject data = json.getJSONObject("data");
                Boolean error = data.has("error");
                if (!error) {
                    // Get JSON data
                    JSONArray results = json.getJSONObject("data").getJSONArray("current_condition");


                    // Get values from JSON to display
                    desc = results.getJSONObject(0).getJSONArray("weatherDesc").getJSONObject(0).getString("value");
                    temp = results.getJSONObject(0).getString("temp_F")+"F¡";
                }
            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        }
    }
}