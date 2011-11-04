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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class FloorPlanScreen extends Activity {
	private Button goButton, backButton;
	private Spinner spinner;
	private String finalFloor;
	private static UserInput getRoomMap = new UserInput();
	private LawsonNavigatorvActivity saved = new LawsonNavigatorvActivity();
	
	public void onCreate(Bundle savedInstanceState) {
		//starts screen and builds everything
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floorplans);
		setUpChoices();
		setUpButtons();
	}
	
	public void setUpChoices()
	{
		//spinner has the list of options
		spinner = (Spinner)this.findViewById(R.id.floorPlanChoice);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, 
				new String[] { "Basement", "Floor One", "Floor Two", "Floor Three" });
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	public void setUpButtons()
	{
		//buttons set to actually listen for a press
		goButton = (Button)findViewById(R.id.goFloorPlan);
		backButton = (Button)findViewById(R.id.back);
		
		goButton.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick(View v) 
    		{ 
    			finalFloor = spinner.getSelectedItem().toString();
    			
    			getRoomMap.setRoomNumber(finalFloor);
    			saved.setName("Floor", finalFloor);
    			saved.setFloor(Floor.BASEMENT);
    			saved.setUsage(Transport.ELEVATOR);
    			
    			Toast.makeText(getApplicationContext(), finalFloor + " MAP!", Toast.LENGTH_SHORT).show();
    			/*Intent i = new Intent();
				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.LawsonNavigatorActivity");
				startActivity(i);*/
    		}
    	});
		
		backButton.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), "back!", Toast.LENGTH_SHORT).show();
    			Intent i = new Intent();
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.LawsonNavigatorvActivity");
    			startActivity(i);
    		}
    	});
	}
}
