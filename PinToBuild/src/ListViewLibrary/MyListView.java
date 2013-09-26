/*
 * project		PinToBuild
 * 
 * package		listViewLibrary
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 25, 2013
 */

package listViewLibrary;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

import com.jpennell.pintobuild.MainActivity;
import com.jpennell.pintobuild.R;
import com.jpennell.pintobuild.R.id;
import com.jpennell.pintobuild.R.layout;

// TODO: Auto-generated Javadoc
/**
 * The Class MyListView.
 */
public class MyListView extends Activity {

    /** The Constant TAG. */
    private static final String TAG = MainActivity.TAG;
    
    //Test image as a kitten
    /** The Constant IMAGE_SOURCE_BASE. */
    private static final String IMAGE_SOURCE_BASE = "http://placekitten.com/";

    // Total number in list view.
    /** The Constant N. */
    private static final int N = 20;
    
    /** The m source. */
    private ArrayList<Point> mSource = new ArrayList<Point>();
    
    /** The m random. */
    private Random mRandom = new Random();

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        initSource();
        ListView list = (ListView) findViewById(R.id.list);
        MyListAdapter adapter = new MyListAdapter(this, mSource);
        list.setAdapter(adapter);
    }

    /**
     * Inits the source.
     */
    private void initSource() {
        int w, h;
        for (int i = 0; i < N; i++) {
            // Generate w, h range from [1,5] and [1,4] respectively.
            w = mRandom.nextInt(5) + 1;
            h = mRandom.nextInt(4) + 1;
            w *= 100;
            h *= 100;
            mSource.add(new Point(w, h));
        }
    }
}
	