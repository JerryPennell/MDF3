/*
 * project		WCWidget
 * 
 * package		com.jpennell.library
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 17, 2013
 */
package com.jpennell.library;

import android.util.Log;
import com.jpennell.wcwidget.R;


// TODO: Auto-generated Javadoc
/**
 * The Class StorageParser.
 */
public class StorageParser {

    /**
     * Gets the desc image.
     *
     * @param descText the desc text
     * @return the desc image
     */
    public static Integer getDescImage (String descText) {
        Log.i("DESCRIPTION", descText);

        Integer _image;

        // Set description image
        if (descText.equals("Sunny")) {
            _image = R.drawable.sunny;
        } else if (descText.equals("Partly Cloudy")) {
            _image = R.drawable.partly_cloudy;
        } else if (descText.equals("Overcast") || descText.equals("Cloudy")) {
            _image = R.drawable.overcast;
        } else {
            _image = R.drawable.warning;
        }

        return _image;
    }
}