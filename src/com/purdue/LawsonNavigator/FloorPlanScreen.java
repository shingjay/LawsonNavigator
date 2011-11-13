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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.*;
import java.net.*;

public class FloorPlanScreen extends Activity {
	private Button goButton, backButton;
	private Spinner spinner;
	private String finalFloor;
	private static UserInput getRoomMap = new UserInput();
	private LawsonNavigatorActivity saved = new LawsonNavigatorActivity();
	private Activity parent;
	
	public void onCreate(Bundle savedInstanceState) {
		//starts screen and builds everything
		super.onCreate(savedInstanceState);
		setContentView(R.layout.floorplans);
		parent = (Activity) this.getParent();
		setUpChoices();
		setUpButtons();
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
    			
    			//Sending stuff to server
    			Socket kkSocket = null;
			PrintWriter out = null;
			BufferedReader in = null;
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
	
			//The IP address of moore01	
			byte[] IP = new byte[4];
			IP[0] = (byte) 128;
			IP[1] = (byte) 10;
			IP[2] = (byte) 12;
			IP[3] = (byte) 131;

			try {
			    kkSocket = new Socket(InetAddress.getByAddress(IP), 4444);
			    out = new PrintWriter(kkSocket.getOutputStream(), true);
			    in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
				ois = new ObjectInputStream(kkSocket.getInputStream());
				oos = new ObjectOutputStream(kkSocket.getOutputStream());
			} catch (UnknownHostException e) {
			    System.err.println("Cannot find the host.");
			    System.exit(1);
			} catch (IOException e) {
			    System.err.println("Couldn't get I/O for the connection to the host.");
			    System.exit(1);
			}
			
			try{
				oos.writeObject(getRoomMap);
			}catch(Exception e){
				System.out.println("oos error");
				e.printStackTrace();
				System.exit(1);
			}

    			//Toast.makeText(getApplicationContext(), in.readLine(), Toast.LENGTH_SHORT).show();
    			
    			try {
				out.close();
				in.close();
				oos.close();
				ois.close();
				kkSocket.close();
			}catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
    			
    			
    			Toast.makeText(getApplicationContext(), finalFloor + " MAP!", Toast.LENGTH_SHORT).show();
    			/*Intent i = new Intent();
				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.LawsonNavigatorActivity");
				startActivity(i);*/
    		}
    	});
		
		backButton.setOnClickListener(new Button.OnClickListener() { 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), "back!", Toast.LENGTH_SHORT).show();
    			finish();
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
