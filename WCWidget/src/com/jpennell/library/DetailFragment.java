package com.jpennell.library;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jpennell.wcwidget.R;


public class DetailFragment extends Fragment {
    // Global variables
    TextView dateView;
    TextView descView;
    TextView lowView;
    TextView hiView;
    TextView windView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Allows items to work in ActionBar
        setHasOptionsMenu(false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.details, container, false);

        // Get views by id
        if (view != null) {
            dateView = (TextView) view.findViewById(R.id.detailDate);
            descView = (TextView) view.findViewById(R.id.detailDesc);
            lowView = (TextView) view.findViewById(R.id.low_description);
            hiView = (TextView) view.findViewById(R.id.hi_description);
            windView = (TextView) view.findViewById(R.id.wind_description);
        }

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }


    public void displayDetails(String date, String desc, String low, String hi, String wind) {
        // Set layout elements
        dateView.setText(date);
        descView.setText(desc);
        lowView.setText(low);
        hiView.setText(hi);
        windView.setText(wind);
    }
}