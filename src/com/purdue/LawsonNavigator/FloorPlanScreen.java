/*
 * @author Jeremy Jenkins
 * The FloorPlanScreen will ask the user to choose which floor plan they would like to view,
 *   and will give a small thumbnail preview.
 * 
 * After clicking go, it will give a much larger preview of the floor plan.
 * 
 */

 package com.purdue.LawsonNavigator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class FloorPlanScreen extends Activity {
	private Spinner spinner;
	private Activity parent;
	private ImageView changeImage;
	
	public void onCreate(Bundle savedInstanceState) {
		//starts screen and builds everything
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floorplans);
		parent = (Activity) this.getParent();
		changeImage = (ImageView)findViewById(R.id.floorPlanImage);
		setUpChoices();
		//setUpButtons();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.exit, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
            	System.exit(0);
                break;
        }
        return true;
    }
	
	public void setUpChoices()
	{
		//spinner has the list of options
		spinner = (Spinner)this.findViewById(R.id.floorPlanChoice);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, 
				new String[] { "Basement", "Floor One", "Floor Two", "Floor Three" });
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		
		spinner.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
			{
				Intent i = new Intent();
				
				try {
					String choice = spinner.getSelectedItem().toString();
					System.out.println(choice);
					
					if (choice == "Basement")
					{
						changeImage.setImageResource(R.drawable.floorb);
						ImageZoomActivity.image = 0;
						Toast.makeText(getApplicationContext(), "Press the soft 'Back' key to go back", Toast.LENGTH_SHORT).show();
						i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ImageZoomActivity");
						startActivity(i);
					}
					else if (choice == "Floor One")
					{
						changeImage.setImageResource(R.drawable.floor1);
						ImageZoomActivity.image = 1;
						Toast.makeText(getApplicationContext(), "Press the soft 'Back' key to go back", Toast.LENGTH_SHORT).show();
						i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ImageZoomActivity");
						startActivity(i);
					}
					else if (choice == "Floor Two")
					{
						changeImage.setImageResource(R.drawable.floor2);
						ImageZoomActivity.image = 2;
						Toast.makeText(getApplicationContext(), "Press the soft 'Back' key to go back", Toast.LENGTH_SHORT).show();
						i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ImageZoomActivity");
						startActivity(i);
					}
					else if (choice == "Floor Three")
					{
						changeImage.setImageResource(R.drawable.floor3);
						ImageZoomActivity.image = 3;
						Toast.makeText(getApplicationContext(), "Press the soft 'Back' key to go back", Toast.LENGTH_SHORT).show();
						i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ImageZoomActivity");
						startActivity(i);
					}
				} catch (Exception e) { finish(); }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	protected void onStop()
	{
		super.onStop();
		
		try {
			parent.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
			parent.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
		} catch (Exception e) { System.out.println("Well, I shouldn't do this, but it works!"); }
		
		
		/*Intent i = new Intent();
		i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.LawsonNavigatorActivity");
		startActivity(i);*/
	}
}
