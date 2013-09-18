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

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.jpennell.wcwidget.MainActivity;
import com.jpennell.wcwidget.R;


// TODO: Auto-generated Javadoc
/**
 * The Class WeatherWidgetProvider.
 */
public class WeatherWidgetProvider extends AppWidgetProvider {
    
    /* (non-Javadoc)
     * @see android.appwidget.AppWidgetProvider#onUpdate(android.content.Context, android.appwidget.AppWidgetManager, int[])
     */
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        String temp = WeatherWidgetRefresh.temp;
        String desc = WeatherWidgetRefresh.desc;
        String zip = MainActivity.zip;

        //Run refresh
        WeatherWidgetRefresh.refresh(zip);

        Log.i("WIDGET", "Widget was refreshed");

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent buttonIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, buttonIntent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

            remoteViews.setTextViewText(R.id.now_temp, temp);
            remoteViews.setTextViewText(R.id.now_desc, desc);

            remoteViews.setOnClickPendingIntent(R.id.go_button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }


    /* (non-Javadoc)
     * @see android.appwidget.AppWidgetProvider#onDeleted(android.content.Context, int[])
     */
    public void onDeleted (Context context, int[] appWidgetIds) {

    }
}