package com.jpennell.library;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Network extends BroadcastReceiver {
    // Global Variables
    static Boolean wifiConnected;
    static Boolean mobileConnected;

    public static void checkNetwork(Context context) {
        
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }
}