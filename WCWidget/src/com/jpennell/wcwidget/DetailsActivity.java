/*
 * project		WCWidget
 * 
 * package		com.jpennell.wcwidget
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 17, 2013
 */
package com.jpennell.wcwidget;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.jpennell.library.DetailFragment;


// TODO: Auto-generated Javadoc
/**
 * The Class DetailsActivity.
 */
public class DetailsActivity extends Activity {

    /** The date. */
    String date;

    /**
     * Gets the data.
     *
     * @return the data
     */
    public void getData() {
        Log.i("DETAIL ACTIVITY", "Running!");
        Bundle data = getIntent().getExtras();

        if (data != null) {
            date = data.getString("detailDate");
            String desc = data.getString("detailDesc");
            String hi = data.getString("detailHi");
            String low = data.getString("detailLow");
            String wind = data.getString("detailWind");

            Log.i("DETAIL STRINGS", date + ", " + desc + ", " + hi + ", " + low + ", " + wind);


            DetailFragment fragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.detail_frag);
            fragment.displayDetails(date, desc, low, hi, wind);
        }
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content
        setContentView(R.layout.detail_frag);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        // Call getData method
        getData();

    }


    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.i("item ID : ", "onOptionsItemSelected Item ID" + id);

        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("ACTION BAR", "Up navigation pressed");

                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /* (non-Javadoc)
     * @see android.app.Activity#finish()
     */
    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("date", date);

        setResult(RESULT_OK, data);
        super.finish();
    }
}