package com.jpennell.capturecam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class BlackAndWhiteImageActivity extends Activity {
	
	Context _context;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blackwhite);

        ImageView orginalImageView = (ImageView) findViewById(R.id.image);
        ImageView blackImageView = (ImageView) findViewById(R.id.blackimage);
        
        //Sample image to test
        Bitmap sample = BitmapFactory.decodeResource(getResources(),
                R.drawable.sample);

        orginalImageView.setBackground(new BitmapDrawable(_context.getResources(), sample));
        
        blackImageView.setBackground(new BitmapDrawable(_context.getResources(), 
        		convertColorIntoBlackAndWhiteImage(sample)));

    }
    
    

    private Bitmap convertColorIntoBlackAndWhiteImage(Bitmap orginalBitmap) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0);

        ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                colorMatrix);

        Bitmap blackAndWhiteBitmap = orginalBitmap.copy(
                Bitmap.Config.ARGB_8888, true);

        Paint paint = new Paint();
        paint.setColorFilter(colorMatrixFilter);

        Canvas canvas = new Canvas(blackAndWhiteBitmap);
        canvas.drawBitmap(blackAndWhiteBitmap, 0, 0, paint);

        return blackAndWhiteBitmap;
    }
}
