/*
 * project		CaptureCam
 * 
 * package		library
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 10, 2013
 */
package library;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;


// TODO: Auto-generated Javadoc
/**
 * The Class Network.
 */
public class Network extends BroadcastReceiver {
    // Global Variables
    /** The wifi is connected. */
    static Boolean wifiIsConnected;
    
    /** The mobile is connected. */
    static Boolean mobileIsConnected;

    /**
     * Check network.
     *
     * @param context the context
     */
    public static void checkNetwork(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

        if (activeInfo != null && activeInfo.isConnected()) {
        	wifiIsConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
        	mobileIsConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;

            // Connected toast
            Toast.makeText(context, "Network is detected", Toast.LENGTH_SHORT).show();
        } else {
        	wifiIsConnected = false;
        	mobileIsConnected = false;

            // Alert if not connected
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("No Network Connection");
            alert.setMessage("Please check your network connections and try again.");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alert.show();
        }
    }

    /* (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

        Log.i("ONRECEIVE", String.valueOf(netInfo));

        if (netInfo != null) {
            Toast.makeText(context, "Network Detected", Toast.LENGTH_SHORT).show();

        } else {
            // Alert if not connected
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("No Network Connection");
            alert.setMessage("Oops....you are no longer connected to a network. Please check your network connections and try again.");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            alert.show();
        }
    }
}