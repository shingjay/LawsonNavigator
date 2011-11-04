package com.purdue.LawsonNavigator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.speech.RecognizerIntent;
import android.view.*;
import android.content.*;
import android.content.pm.PackageManager;

public class LawsonNavigatorActivity extends Activity
{
    private Button roomButton, profButton, nonAcaButton, floorButton, optionButton;
    //private static String finalName, finalFloor, finalUsage;
    private getLocation location;
    private Voice_Recognition vr;
    public UserInput saved = new UserInput();
    
    public LawsonNavigatorActivity()
    {
    	//used to access and set the saved information
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		PackageManager pm = getPackageManager();
        //vr = new Voice_Recognition(pm);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //saved = vr.start(intent);
        //getMapInfo();
        location = new getLocation(getApplicationContext(), saved);
        setUpButton();
    }
    
    public void setUpButton()
    {
    	roomButton = (Button)findViewById(R.id.roomNum);
    	profButton = (Button)findViewById(R.id.profName);
    	nonAcaButton = (Button)findViewById(R.id.nonAcaRoom);
    	floorButton = (Button)findViewById(R.id.floorPlan);
    	optionButton = (Button)findViewById(R.id.options);
    	
    	Toast.makeText(getApplicationContext(), saved.getRoomNumber() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    	
    	roomButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    				Intent i = new Intent();
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.RoomNumber");
    				startActivity(i);
    			}
    		});
    	
    	profButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    				Intent i = new Intent();
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ProfScreen");
    				startActivity(i);
    			}
    		});
    	
    	nonAcaButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    				Intent i = new Intent();
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.NonAcademicRooms");
    				startActivity(i);
    			}
    		});
    	
    	floorButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    				Intent i = new Intent();
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.FloorPlanScreen");
    				startActivity(i);
    			}
    		});
    	
    	optionButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    				Intent i = new Intent();
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.Options");
    				startActivity(i);
    			}
    		});
    }
    
    public void setName(String type, String choice)
	{
    	if (type == "Prof")
    	{
    		saved.setProfessorName(choice);
    	}
    	else if (type == "Room")
    	{
    		saved.setRoomNumber(choice);
    	}
    	else if (type == "NonRoom")
    	{
    		saved.setNonAcademicRoom(choice);
    	}
    	else if (type == "Floor")
    	{
    		//nothing
    	}
    	else
    	{
    		System.out.println("error");
    	}
	}
	
	public void setFloor(Floor floor)
	{
		switch (floor) {
		case BASEMENT:
			//finalFloor = "Basement";
			saved.setFloor(floor);
			break;
		case FIRST:
			//finalFloor = "First Floor";
			saved.setFloor(floor);
			break;
		case SECOND:
			//finalFloor = "Second Floor";
			saved.setFloor(floor);
			break;
		case THIRD:
			//finalFloor = "Third Floor";
			saved.setFloor(floor);
			break;
			
		}
	}
	
	public void setUsage(Transport transport)
	{
		switch (transport) {
		case ELEVATOR:
			//finalUsage = "Elevator";
			saved.setTransport(transport);
			break;
		case STAIRS:
			//finalUsage = "Stairs";
			saved.setTransport(transport);
			break;
		}
	}
    
	public void setLong(double longitude)
	{
		saved.setLongitude(longitude);
	}
	
	public void setLat(double latitude)
	{
		saved.setLatitude(latitude);
	}
    /*
    public void toast(String buttonPressed)
    {
    	Context context = getApplicationContext();
    	CharSequence text = buttonPressed;
    	int duration = Toast.LENGTH_LONG;
    	
    	Toast toast = Toast.makeText(context, text, duration);
    	toast.show();
    }
*/
}
