/*
 * @author Jeremy Jenkins
 * The ProfScreen is where a majority of my testing took place, just because it was convenient.
 *   This will populate the list of Professors, ask what way the person would like to travel, 
 *   and then what floor they are on.
 * 
 * After clicking go, the information is taken and put together in a single object and sent to the server.
 * 
 */

package com.purdue.LawsonNavigator;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.ContextWrapper;
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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Spinner;

public class ProfScreen extends Activity {
	
	private Button goButton, backButton;
	private RadioButton stairs, elevator, base, first, second, third, textSpeech, map;
	private Spinner spinner;
	private getLocation location;
	private UserInput getProfMap = new UserInput();
	private LawsonNavigatorActivity saved = new LawsonNavigatorActivity();
	private String finalName;
	private Floor floor = Floor.BASEMENT;
	private Transport transport = Transport.ELEVATOR;
	private Display displayOption = Display.TEXTSPEECH;
	private Activity parent;
	private SharedPreferences settings;
	private String directions1, directions2;
	private Point point1, point2;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.professorname);
		parent = (Activity) this.getParent();
		settings = getSharedPreferences("Options", 0);
		setUpChoices();
		setUpRadio();
		setUpButtons();
        //location = new getLocation(this.getApplicationContext(), getProfMap);
        //Toast.makeText(getApplicationContext(), getProfMap.getRoomNumber() + ":" + getProfMap.getTransport() + ":" + getProfMap.getFloor() + ":" + getProfMap.getLatitude() + ":" + getProfMap.getLongitude(), Toast.LENGTH_SHORT).show();
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
		spinner = (Spinner)this.findViewById(R.id.profChoice);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, 
				new String[] { "Aditya Mathur", "Ahmed Elmagarmid", "Ahmed Sameh", "Alex Pothen", "Ananth Grama",
				"Assefaw Gebremedhin", "Bharat Bhargava", "Buster Dunsmore", "Charles Killian", "Chris Clifton",
				"Chris Hoffman", "Cristina Nita-Rotaru", "Daisuke Kihara", "Daniel Aliaga", "David Gleich", "David Yau",
				"Dongyan Xu", "Douglas Comer", "Elisa Bertino",	"Elisha Sacks", "Eugene Spafford", "Faisal Saied",
				"Greg Frederickson", "Gustavo Rodriguez Rivera", "Jan Vitek", "Jennifer Neville", "Kihong Park",
				"Luo Si", "Mikhail Atallah", "Ninghui Li", "Patrik Eugster", "Ramana Kompella", "Robert Skeel",
				"Samuel Wagstaff", "Sonia Fahmy", "Sunil Prabhakar", "Suresh Jagannathan", "Susanne  Hambrusch",
				"Tony Hosking", "Vernon Rego", "Voicu Popescu", "Walif Aref", "Wojciech Szpankowski", "Xavier Tricoche",
				"Xiangyu Zhang", "Yuan Qi", "Zhiyuan Li"});
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
		goButton = (Button)findViewById(R.id.goProf);
		backButton = (Button)findViewById(R.id.back);
		
		goButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		@SuppressWarnings("unchecked")
			public void onClick(View v) 
    		{
    			finalName = spinner.getSelectedItem().toString();
    			
    			getProfMap.setTransport(transport);
    			saved.setUsage(transport);
    			getProfMap.setFloor(floor);
    			saved.setFloor(floor);
    			getProfMap.setProfessorName(finalName);
    			saved.setName("prof", finalName);
    			getProfMap.setDisplayOption(displayOption);
    			saved.setDisplay(displayOption);
    			getProfMap.setLatitude(getLocation.lat);
    			getProfMap.setLongitude(getLocation.longi);
    	        
    			//Sending stuff to server
    			Socket kkSocket = null;
				PrintWriter out = null;
				BufferedReader in = null;
				ObjectInputStream ois = null;
				ObjectOutputStream oos = null;
				InputStream is = null;
				FileOutputStream fos = null;
		
				//The IP address of moore01	
				byte[] IP = new byte[4];
				IP[0] = (byte) 128;
				IP[1] = (byte) 10;
				IP[2] = (byte) 12;
				IP[3] = (byte) 131;
	
				try {
				    kkSocket = new Socket(InetAddress.getByAddress(IP), 9999);
				    out = new PrintWriter(kkSocket.getOutputStream(), true);
				    in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
					ois = new ObjectInputStream(kkSocket.getInputStream());
					oos = new ObjectOutputStream(kkSocket.getOutputStream());
					is = kkSocket.getInputStream();
				} catch (UnknownHostException e) {
				    System.err.println("Cannot find the host.");
				    System.exit(1);
				} catch (IOException e) {
				    System.err.println("Couldn't get I/O for the connection to the host.");
				    System.exit(1);
				}
				
				try{
					oos.writeObject(getProfMap);
				}catch(Exception e){
					System.out.println("oos error");
					e.printStackTrace();
					System.exit(1);
				}
	
				//get input from server
				ArrayList<Byte> images1 = new ArrayList<Byte>();
				ArrayList<String> textDirections1 = new ArrayList<String>();
				ArrayList<Point> points1 = new ArrayList<Point>();
				ArrayList<Byte> images2 = new ArrayList<Byte>();
				ArrayList<String> textDirections2 = new ArrayList<String>();
				ArrayList<Point> points2 = new ArrayList<Point>();
				
    			ContextWrapper cw = new ContextWrapper(getApplicationContext());

				try
				{
					//read in first image, if there
					System.out.println("about to read images1");
					images1 = (ArrayList<Byte>)ois.readObject();
					ImageZoomActivity.mapArray1 = new byte[images1.size()];
					
					for (int i = 0; i < images1.size(); i++)
					{
						ImageZoomActivity.mapArray1[i] = images1.get(i).byteValue();
					}
					
					//read in first directions, if there
					textDirections1 = (ArrayList<String>)ois.readObject();
					directions1 = textDirections1.toString();
					//System.out.println(directions1);
					
					//read in first point, if there
					points1 = (ArrayList<Point>)ois.readObject();
					
					//read in second image, if there
					images2 = (ArrayList<Byte>)ois.readObject();
					ImageZoomActivity.mapArray2 = new byte[images2.size()];
					
					for (int i = 0; i < images2.size(); i++)
					{
						ImageZoomActivity.mapArray2[i] = images2.get(i).byteValue();
					}
					
					//read in second directions, if there
					textDirections2 = (ArrayList<String>)ois.readObject();
					directions2 = textDirections2.toString();
					
					//read in second points, if there
					points2 = (ArrayList<Point>)ois.readObject();
					
					
				}catch(Exception e){
					System.err.println("Error in recieving data from the server");
					e.printStackTrace();
					System.exit(1);
				}
	    		
    			finish();
    			
    			Intent i = new Intent();
    			if (displayOption == Display.MAP)
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.MapScreen");
    			else if (displayOption == Display.TEXTSPEECH)
    			{
    				TextView.directions += directions1;
    				TextView.directions2 += directions2;
    				i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.TextView");
    			}
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
	
	public UserInput returnInfo()
	{
		return getProfMap;
	}
}
