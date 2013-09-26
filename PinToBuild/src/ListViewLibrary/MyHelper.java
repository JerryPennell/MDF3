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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.net.URL;

import com.jpennell.pintobuild.MainActivity;

// TODO: Auto-generated Javadoc
/**
 * The Class MyHelper.
 */
public class MyHelper {

    /** The Constant TAG. */
    private static final String TAG = MainActivity.TAG;

    /**
     * The Class RemoteImageTask.
     */
    public static class RemoteImageTask extends AsyncTask<Void, Void, Bitmap> {
        
        /** The _image. */
        ImageView _image;
        
        /** The _image source. */
        String _imageSource;
        
        /** The _callback. */
        TaskCallback _callback;

        /**
         * Instantiates a new remote image task.
         *
         * @param image the image
         * @param imageSource the image source
         */
        public RemoteImageTask(ImageView image, String imageSource) {
            this(image, imageSource, null);
        }

        /**
         * Instantiates a new remote image task.
         *
         * @param image the image
         * @param imageSource the image source
         * @param callback the callback
         */
        public RemoteImageTask(ImageView image, String imageSource, TaskCallback callback) {
            _image = image;
            _imageSource = imageSource;
            _callback = callback;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#doInBackground(Params[])
         */
        protected Bitmap doInBackground(Void... params) {
            URL url;
            Bitmap bmp = null;
            try {
                url = new URL(_imageSource);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception ignored) {
                Log.e(TAG, "Exception", ignored);
            }

            return bmp;
        }

        /* (non-Javadoc)
         * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
         */
        protected void onPostExecute(Bitmap bmp) {
            _image.setImageBitmap(bmp);
            if (_callback != null)
                _callback.onTaskFinished(bmp);
        }
    }

    /**
     * The Interface TaskCallback.
     */
    public interface TaskCallback {
        
        /**
         * On task finished.
         *
         * @param bmp the bmp
         */
        public void onTaskFinished(final Bitmap bmp);
    }
}
