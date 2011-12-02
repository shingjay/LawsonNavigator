package com.purdue.LawsonNavigator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.purdue.LawsonNavigator.imagezoom.ImageViewTouch;

public class ImageZoomActivity extends Activity {
	static int image;
	static byte[] mapArray1, mapArray2;
	static Bitmap bitmap1, bitmap2;
	private ImageViewTouch	mImageView;
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		
		requestWindowFeature( Window.FEATURE_NO_TITLE );
		setContentView( R.layout.previewscreen );
		getWindow().addFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN );
		selectRandomImage();
	}
	
	@Override
	public void onContentChanged()
	{
		super.onContentChanged();
		mImageView = (ImageViewTouch)findViewById( R.id.imageView1 );
	}
	
	/**
	 * pick a random image from your library
	 * and display it
	 */
	public void selectRandomImage()
	{
		if (image == 0)
		{
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.floorb);
			mImageView.setImageBitmapReset( bitmap, 0, true );
			
		}
		else if (image == 1)
		{
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.floor1);
			mImageView.setImageBitmapReset( bitmap, 0, true );
		}
		else if (image == 2)
		{
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.floor2);
			mImageView.setImageBitmapReset( bitmap, 0, true );
		}
		else if (image == 3)
		{
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.floor3);
			mImageView.setImageBitmapReset( bitmap, 0, true );
		}
		else if (image == 4)
		{
			Bitmap bitmap = BitmapFactory.decodeByteArray(mapArray1, 0, mapArray1.length);
			mImageView.setImageBitmapReset( bitmap, 0, true );
		}
		else if (image == 5)
		{
			Bitmap bitmap = BitmapFactory.decodeByteArray(mapArray2, 0, mapArray2.length);
			mImageView.setImageBitmapReset( bitmap, 0, true );
		}
	}
	
}
