/*
 * project		PinToBuild
 * 
 * package		com.jpennell.pintobuild
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 25, 2013
 */

package com.jpennell.pintobuild;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pinterest.pinit.PinItButton;

import java.util.ArrayList;
import java.util.Arrays;

import listViewLibrary.MyListView;

// TODO: Auto-generated Javadoc
/**
 * The listview of pinit.
 * Show a list of pinit .
 *
 */
public class ListViewActivity extends Activity {

    /** The Constant TAG. */
    public static final String TAG = "Demo Activity";
    /**
     * Please generate your Client ID at http://developers.pinterest.com/manage/ , and put it here.
     */
    public static final String CLIENT_ID = "1433332";
    
    /** The m list. */
    private ListView mList;
    
    /** The m adapter. */
    private ArrayAdapter<String> mAdapter;

    // Create and populate a List of planet names.
    /** The Constant LISTCALL. */
    private static final String[] LISTCALL = new String[] {
        "1. PinIt in ListView"
    };

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mList = (ListView) findViewById(R.id.list);
        ArrayList<String> examples = new ArrayList<String>(Arrays.asList(LISTCALL));

        // Create ArrayAdapter using the list.
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, examples);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mListener);

        PinItButton.setDebugMode(true);
        PinItButton.setPartnerId(CLIENT_ID);
    }

    /** The m listener. */
    private OnItemClickListener mListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            Intent intent = null;
            final Activity activity = ListViewActivity.this;
            switch (arg2) {
                case 0:
                    intent = new Intent(activity, MyListView.class);
                    break;
                default:
                    return;
            }
            activity.startActivity(intent);
        }
    };
}
