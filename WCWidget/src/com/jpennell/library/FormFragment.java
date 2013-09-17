package com.jpennell.library;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jpennell.wcwidget.R;


public class FormFragment extends Fragment {
    private formListener listener;

    public interface formListener {
        public void onWeatherSearch();
        public void onSetWidget();
        public void onSingleDaySelected(Integer i);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (formListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Allows items to work in ActionBar
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Create view
        LinearLayout view = (LinearLayout)inflater.inflate(R.layout.form, container, false);

        // Add "Get Weather" button from MainActivity here
        Button searchButton = (Button) view.findViewById(com.jpennell.wcwidget.R.id.searchButton);

        // OnClickListener
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onWeatherSearch();
            }
        });

        // Add "Set Widget" button from MainActivity here
        Button setButton = (Button) view.findViewById(R.id.set_button);

        // OnClickListener
        setButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.onSetWidget();
            }
        });

//      // Attach list adapter
        ListView listView = (ListView) view.findViewById(R.id.list);


        // OnItemClickListener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onSingleDaySelected(i);
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }
}