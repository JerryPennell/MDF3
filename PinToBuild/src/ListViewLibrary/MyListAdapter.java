/*
 * project		PinToBuild
 * 
 * package		ListViewLibrary
 * 
 * author		Jerry Pennell
 * 
 * date			Sep 25, 2013
 */

package ListViewLibrary;

import java.util.ArrayList;
import java.util.HashMap;

import ListViewLibrary.MyHelper.RemoteImageTask;
import ListViewLibrary.MyHelper.TaskCallback;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jpennell.pintobuild.MainActivity;
import com.jpennell.pintobuild.R;
import com.jpennell.pintobuild.R.id;
import com.jpennell.pintobuild.R.layout;
import com.jpennell.pintobuild.R.string;
import com.pinterest.pinit.PinItButton;

// TODO: Auto-generated Javadoc
/**
 * The Class MyListAdapter.
 */
public class MyListAdapter extends BaseAdapter {

    /** The Constant TAG. */
    private static final String TAG = MainActivity.TAG;
    
    /** The Constant WEB_URL. */
    
    //Using a kitten as a test image
    private static final String WEB_URL = "http://placekitten.com";
    
    /** The Constant IMAGE_SOURCE_BASE. */
    private static final String IMAGE_SOURCE_BASE = "http://placekitten.com/";

    /** The m source. */
    private ArrayList<Point> mSource = new ArrayList<Point>();
    
    /** The m activity. */
    private Activity mActivity;
    
    /** The m inflater. */
    private LayoutInflater mInflater;

    /** The m description. */
    final String mDescription;
    
    /** The m cache. */
    private HashMap<String, Bitmap> mCache = new HashMap<String, Bitmap>(20);

    /**
     * Instantiates a new my list adapter.
     *
     * @param activity the activity
     * @param source the source
     */
    public MyListAdapter(Activity activity, ArrayList<Point> source) {
        mSource = source;
        mActivity = activity;
        mInflater = activity.getLayoutInflater();

        Resources res = activity.getResources();
        mDescription = res.getString(R.string.pin_desc_table);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
        return mSource.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
        return mSource.get(position);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.mImage = (ImageView) convertView.findViewById(R.id.source_iv);
            holder.mText = (TextView) convertView.findViewById(R.id.desc_tv);
            holder.mPinIt = (PinItButton) convertView.findViewById(R.id.pin_bt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Point size = (Point) getItem(position);

        String url = IMAGE_SOURCE_BASE + size.x + "/" + size.y;
        setDisplayImage(holder.mImage, url);

        String desc = mDescription + " with size " + size.x + " X " + size.y;
        holder.mText.setText(desc);

        holder.mPinIt.setImageUrl(url);
        holder.mPinIt.setDescription(desc);
        holder.mPinIt.setUrl(WEB_URL);

        return convertView;
    }

    /**
     * Sets the display image.
     *
     * @param iv the iv
     * @param url the url
     */
    private void setDisplayImage(final ImageView iv, final String url) {
        // Show the remote image in ImageView.
        if (getFromCache(url) != null) {
            iv.setImageBitmap(getFromCache(url));
        } else {
            new MyHelper.RemoteImageTask(iv, url, new TaskCallback() {

                @Override
                public void onTaskFinished(Bitmap bmp) {
                    putToCache(url, bmp);
                }
            }).execute();
        }
    }

    /**
     * The Class ViewHolder.
     */
    private static class ViewHolder {
        
        /** The m image. */
        ImageView mImage;
        
        /** The m text. */
        TextView mText;
        
        /** The m pin it. */
        PinItButton mPinIt;
    }

    // Get and put from the memory cache.
    /**
     * Gets the from cache.
     *
     * @param key the key
     * @return the from cache
     */
    private Bitmap getFromCache(String key) {
        return mCache.get(key);
    }

    /**
     * Put to cache.
     *
     * @param key the key
     * @param bmp the bmp
     */
    private void putToCache(String key, Bitmap bmp) {
        if (getFromCache(key) == null) {
            mCache.put(key, bmp);
        }
    }
}
