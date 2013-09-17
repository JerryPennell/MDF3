package com.jpennell.library;

import android.util.Log;
import com.jpennell.wcwidget.R;


public class StorageParser {

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