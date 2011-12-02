package com.purdue.LawsonNavigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class MapScreen extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapview);
		
		Gallery gallery = (Gallery) findViewById(R.id.gallery);
	    gallery.setAdapter(new ImageAdapter(this));
	    
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	    	@Override
	        public void onItemClick(AdapterView parent, View v, int position, long id)
	    	{
		        Intent i = new Intent();
	    		
	    		switch (position)
	    		{
	    		case 0:
			        Toast.makeText(MapScreen.this, "" + position, Toast.LENGTH_SHORT).show();
			        ImageZoomActivity.image = 4;
					Toast.makeText(getApplicationContext(), "Press the soft 'Back' key to go back", Toast.LENGTH_SHORT).show();
					i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ImageZoomActivity");
					startActivity(i);
			        
			        break;
	    		case 1:
	    			Toast.makeText(MapScreen.this, "" + position, Toast.LENGTH_SHORT).show();
			        ImageZoomActivity.image = 5;
					Toast.makeText(getApplicationContext(), "Press the soft 'Back' key to go back", Toast.LENGTH_SHORT).show();
					i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ImageZoomActivity");
					startActivity(i);
			        
			        break;
	    		}
	        }
	    });
	}
	
	public class ImageAdapter extends BaseAdapter {
	    int mGalleryItemBackground;
	    private Context mContext;

	    private Integer[] mImageIds = {
	            R.drawable.floorb,
	            R.drawable.floor1
	    };

	    public ImageAdapter(Context c) {
	    	mContext = c;
            TypedArray a = obtainStyledAttributes(R.styleable.gallery);
            mGalleryItemBackground = a.getResourceId(
                    R.styleable.gallery_android_galleryItemBackground, 0);
            a.recycle();
	    }

	    public int getCount() {
	        return mImageIds.length;
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ImageView imageView = new ImageView(mContext);

	        imageView.setImageResource(mImageIds[position]);
	        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
	        imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));
	        imageView.setBackgroundResource(mGalleryItemBackground);

	        return imageView;
	    }
	}

}
