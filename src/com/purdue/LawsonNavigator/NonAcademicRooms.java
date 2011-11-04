/*
 * @author Jeremy Jenkins
 * The NonRoomNumber, aka NonAcademic Rooms, screen will populate the list of Rooms, ask what way the person would like to travel, 
 *   and then what floor they are on.
 * 
 * After clicking go, the information is taken and put together in a single object and sent to the server.
 * 
 */

package com.purdue.LawsonNavigator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.*;
import android.content.*;

public class NonAcademicRooms extends Activity {
	private Button goButton, backButton;
	private RadioButton stairs, elevator, base, first, second, third;
	private Spinner spinner;
	private static UserInput getNonRoomMap = new UserInput();
	private LawsonNavigatorActivity saved = new LawsonNavigatorActivity();
	private String finalNonRoom;
	private Floor floor = Floor.BASEMENT;
	private Transport transport = Transport.ELEVATOR;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nonacademic);
		setUpChoices();
		setUpRadio();
		setUpButtons();
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
    			
    			
    			//Toast.makeText(getApplicationContext(), getNonRoomMap.getRoom() + ":" + getNonRoomMap.getFloor() + ":" + getNonRoomMap.getTransport(), Toast.LENGTH_SHORT).show();
    			/*Intent i = new Intent();
				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.LawsonNavigatorActivity");
				startActivity(i);*/
    		}
    	});
		
		backButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), "back!", Toast.LENGTH_SHORT).show();
    			Intent i = new Intent();
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.LawsonNavigatorvActivity");
    			startActivity(i);
    		}
    	});
	}
	
	public static UserInput returnInfo()
	{
		return getNonRoomMap;
	}
}
