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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


// TODO: Auto-generated Javadoc
/**
 * The Class CaptureCameraStorage.
 */
@SuppressLint("SimpleDateFormat")
public class CaptureCameraStorage {
    // Global Variables
    /** The Constant MEDIA_TYPE_IMAGE. */
    public static final int MEDIA_TYPE_IMAGE = 1;
    
    /** The folder name. */
    public static String FOLDER_NAME = null;

    // Create a file Uri for saving an image
    /**
     * Gets the output media file uri.
     *
     * @param type the type
     * @return the output media file uri
     */
    public static Uri getOutputMediaFileUri(int type){
        Log.i("MEDIA_FILE_URI", "URI created");
        return Uri.fromFile(getOutputMediaFile(type));
    }


    // Create a file for saving an image
    /**
     * Gets the output media file.
     *
     * @param type the type
     * @return the output media file
     */
    public static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), FOLDER_NAME);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("CAPTURE_CAM", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    // Save image to gallery
    /**
     * Gallery add pic.
     *
     * @param context the context
     * @param fileUri the file uri
     */
    public static void galleryAddPic(Context context, Uri fileUri) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
        File f = new File(String.valueOf(fileUri));
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }
}