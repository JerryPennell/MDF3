/*
 * project		CaptureCam
 * 
 * package		com.jpennell.capturecam
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 11, 2013
 */
package com.jpennell.capturecam;

import library.CaptureCameraStorage;
import library.Network;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class MainActivity.
 */
public class MainActivity extends Activity {
    // Global Variables
    /** The _context. */
    Context _context = this;
    
    /** The folder text. */
    EditText folderText;
    
    /** The folder name. */
    String folderName = "";
    
    /** The Constant CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE. */
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    
    /** The _file uri. */
    private static Uri _fileUri;

    /** The all notification id. */
    private final int ALL_NOTIFICATION_ID = 1;
    
    /** The small notification id. */
    private final int SMALL_NOTIFICATION_ID = 2;



    /**
     * Check camera hardware.
     *
     * @param context the context
     */
    private void checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // Device has a camera
            Log.i("CAMERA_HARDWARE", "Camera found");

            // Launch camera
            takePicture();
        } else {
            Log.i("CAMERA_HARDWARE", "No camera found");

            // No camera on device
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("No Camera");
            alert.setMessage("Sorry, but this device does not support a camera.");
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


    /**
     * Take picture.
     */
    private void takePicture() {
        // Create camera intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        _fileUri = CaptureCameraStorage.getOutputMediaFileUri(CaptureCameraStorage.MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _fileUri);

        // Start intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        Log.i("TAKE_PICTURE", "Launching camera");
    }

    



    /**
     * Notification.
     */
    public void notification() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();

        notification.defaults =Notification.DEFAULT_ALL;
        nm.notify(ALL_NOTIFICATION_ID, notification);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(_context)
                .setSmallIcon(R.drawable.notify_icon)
                .setContentTitle("Captured camera photo saved")
                .setContentText("Check out the awesome picture you took!");

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        PendingIntent pendingIntent = PendingIntent.getActivity(_context, 0, galleryIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        galleryIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyBuilder.setContentIntent(pendingIntent);

        // Because the ID remains unchanged, the existing notification is
        // updated.
        nm.notify(SMALL_NOTIFICATION_ID, notifyBuilder.build());
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Call Network
        Network.checkNetwork(_context);

        // Camera button
        Button cameraButton = (Button) findViewById(R.id.cameraButton);

        // OnClickListener
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Edit text
                folderText = (EditText) findViewById(R.id.editText);
                folderName = folderText.getText().toString();

                if (folderName.length() > 0) {
                    // Launch camera
                	CaptureCameraStorage.FOLDER_NAME = folderName;
                    checkCameraHardware(_context);
                } else {
                    // Alert if folder name is black
                    AlertDialog.Builder alert = new AlertDialog.Builder(_context);
                    alert.setTitle("No Folder Name");
                    alert.setMessage("Please create a folder name before taking a photo.");
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
        });
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


    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("RESULT_CODE", String.valueOf(resultCode));
        Log.i("DATA", String.valueOf(data));

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Save image
                CaptureCameraStorage.galleryAddPic(_context,_fileUri);

                // Call Notification
                notification();

                // Toast
                Toast.makeText(_context, "Image saved to gallery", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
                Toast.makeText(_context, "Camera cancelled by user", Toast.LENGTH_LONG).show();
            } else {
                // Image capture failed, advise user
                Toast.makeText(_context, "Camera app has failed", Toast.LENGTH_LONG).show();
            }
        }
    }  
}