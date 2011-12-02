/*
 * @author Jeremy Jenkins
 * The NonRoomNumber, aka NonAcademic Rooms, screen will populate the list of Rooms, ask what way the person would like to travel, 
 *   and then what floor they are on.
 * 
 * After clicking go, the information is taken and put together in a single object and sent to the server.
 * 
 */

package com.purdue.LawsonNavigator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Spinner;

public class NonAcademicRooms extends Activity {
	private Button goButton, backButton;
	private RadioButton stairs, elevator, base, first, second, third, textSpeech, map;
	private getLocation location;
	private Spinner spinner;
	private static UserInput getNonRoomMap = new UserInput();
	private LawsonNavigatorActivity saved = new LawsonNavigatorActivity();
	private String finalNonRoom;
	private Floor floor = Floor.BASEMENT;
	private Transport transport = Transport.ELEVATOR;
	private Display displayOption = Display.TEXTSPEECH;
	private Activity parent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nonacademic);
		parent = (Activity) this.getParent();
		setUpChoices();
		setUpRadio();
		setUpButtons();
        //location = new getLocation(this.getApplicationContext(), getNonRoomMap);
        //Toast.makeText(getApplicationContext(), getNonRoomMap.getRoomNumber() + ":" + getNonRoomMap.getTransport() + ":" + getNonRoomMap.getFloor() + ":" + getNonRoomMap.getLatitude() + ":" + getNonRoomMap.getLongitude(), Toast.LENGTH_SHORT).show();
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
		spinner = (Spinner)this.findViewById(R.id.nonRoomNumChoice);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, 
				new String[] {"ACM Office", "CSWN Offic", "GSB Office", "USB Office", "Graduate Office", "Mail/Copy Room",
				"Port", "Port Office", "Undergraduate Office", "Tolpolka Terrace", "Undergraduate Office", "Bathroom" });
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}
	
	public void setUpRadio()
	{
		stairs = (RadioButton)findViewById(R.id.stairs);
		stairs.setChecked(true);
		transport = Transport.ELEVATOR;
		elevator = (RadioButton)findViewById(R.id.elevator);
		base = (RadioButton)findViewById(R.id.basement);
		base.setChecked(true);
		floor = Floor.BASEMENT;
		first = (RadioButton)findViewById(R.id.firstFloor);
		second = (RadioButton)findViewById(R.id.secondFloor);
		third = (RadioButton)findViewById(R.id.thirdFloor);
		textSpeech = (RadioButton)findViewById(R.id.useTextSpeech);
		textSpeech.setChecked(true);
		map = (RadioButton)findViewById(R.id.useMap);
		
		stairs.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					elevator.setChecked(false);
					transport = Transport.STAIRS;
				}
			}
		});
		
		elevator.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					stairs.setChecked(false);
					transport = Transport.ELEVATOR;
				}
			}
		});
		
		base.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					first.setChecked(false);
					second.setChecked(false);
					third.setChecked(false);
					floor = Floor.BASEMENT;
				}
			}
		});
		
		first.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					base.setChecked(false);
					second.setChecked(false);
					third.setChecked(false);
					floor = Floor.FIRST;
				}
			}
		});
		
		second.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					base.setChecked(false);
					first.setChecked(false);
					third.setChecked(false);
					floor = Floor.SECOND;
				}
			}
		});
		
		third.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					base.setChecked(false);
					first.setChecked(false);
					second.setChecked(false);
					floor = Floor.THIRD;
				}
			}
		});
		
		textSpeech.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					map.setChecked(false);
					displayOption = Display.TEXTSPEECH;
				}
			}
		});
		
		map.setOnCheckedChangeListener(new OnCheckedChangeListener() 
		{
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) 
			{
				if (arg1)
				{
					textSpeech.setChecked(false);
					displayOption = Display.MAP;
				}
			}
		});
	}
	
	public void setUpButtons()
	{
		goButton = (Button)findViewById(R.id.goNonAca);
		backButton = (Button)findViewById(R.id.back);
		
		goButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), "GO! Woo!", Toast.LENGTH_LONG).show();
    			
    			finalNonRoom = spinner.getSelectedItem().toString();
    			
    			getNonRoomMap.setTransport(transport);
    			saved.setUsage(transport);
    			getNonRoomMap.setFloor(floor);
    			saved.setFloor(floor);
    			getNonRoomMap.setNonAcademicRoom(finalNonRoom);
    			saved.setName("NonRoom", finalNonRoom);
    			getNonRoomMap.setDisplayOption(displayOption);
    			saved.setDisplay(displayOption);
    			
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
					oos.writeObject(getNonRoomMap);
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
    			
    			finish();
    			Intent i = new Intent();
				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.MapScreen");
				startActivity(i);
    		}
    	});
		
		backButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
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
	
	public static UserInput returnInfo()
	{
		return getNonRoomMap;
	}
}
