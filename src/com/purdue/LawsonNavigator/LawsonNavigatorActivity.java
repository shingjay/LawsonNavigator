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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.*;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class LawsonNavigatorActivity extends Activity
{
    private Button roomButton, profButton, nonAcaButton, floorButton, optionButton;
    public UserInput saved = new UserInput();
    private LocationManager locMan = null;
	private LocationListener locListen = null;
	private getLocation location = null;
	private Context context;
	private String provider, myText1 = null, roomNumber = null, nonAcademicRoom = null, professorName = null;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private static int TTS_DATA_CHECK = 1;
	private static String[] roomNumbers = { "B105", "B107", "B116 (TA)", "B129", "B131", "B132", "B134", "B146", "B148", "B151",
											"B155", "B158", "B160", "1106", "1142", "2121", "2149", "2161", "3133", "3151", "3155"};
	private static String[] professorNames = { "Aditya Mathur", "Ahmed Elmagarmid", "Ahmed Sameh", "Alex Pothen", "Ananth Grama",
												"Assefaw Gebremedhin", "Bharat Bhargava", "Buster Dunsmore", "Charles Killian", "Chris Clifton",
											    "Chris Hoffman", "Cristina Nita-Rotaru", "Daisuke Kihara", "Daniel Aliaga", "David Gleich", "David Yau",
											    "Dongyan Xu", "Douglas Comer", "Elisa Bertino", "Elisha Sacks", "Eugene Spafford", "Faisal Saied",
											    "Greg Frederickson", "Gustavo Rodriguez Rivera", "Jan Vitek", "Jennifer Neville", "Kihong Park",
											    "Luo Si", "Mikhail Atallah", "Ninghui Li", "Patrik Eugster", "Ramana Kompella", "Robert Skeel",
											    "Samuel Wagstaff", "Sonia Fahmy", "Sunil Prabhakar", "Suresh Jagannathan", "Susanne Hambrusch",
											    "Tony Hosking", "Vernon Rego", "Voicu Popescu", "Walif Aref", "Wojciech Szpankowski", "Xavier Tricoche",
											    "Xiangyu Zhang", "Yuan Qi", "Zhiyuan Li"};
	private static String[] nonAcademicRooms = { "ACM Office", "CSWN Offic", "GSB Office", "USB Office", "Graduate Office", "Mail/Copy Room",
	  											"Port", "Port Office", "Undergraduate Office", "Tolpolka Terrace", "Undergraduate Office", "Bathroom" };
	private int prompt = 0;
	private boolean ttsIsInit = false;
	private TextToSpeech myTextToSpeech = null;
	Floor floor = null;
	Transport transport = null;
	Display displayOption = null;
	private boolean check1=false, check2=false, check3=false, voiceOnOff;
	private ListView wordsList;
	public SharedPreferences settings;
    
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
		settings = getSharedPreferences("Options", 0);
		voiceOnOff = settings.getBoolean("voiceOn", true);
		context = this.getApplicationContext();
		location = new getLocation(context, saved);
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        initTextToSpeech();
        setUpButton();
        getLocation();
        System.out.println("Location changed : Lat: " + saved.getLatitude()
				+ " Lng: " + saved.getLongitude());
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
    
    public void setUpButton()
    {
    	roomButton = (Button)findViewById(R.id.roomNum);
    	profButton = (Button)findViewById(R.id.profName);
    	nonAcaButton = (Button)findViewById(R.id.nonAcaRoom);
    	floorButton = (Button)findViewById(R.id.floorPlan);
    	optionButton = (Button)findViewById(R.id.options);
    	
    	//Toast.makeText(getApplicationContext(), saved.getRoomNumber() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    	
    	roomButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    			//finish();	
    			Intent i = new Intent();
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.RoomNumber");
    			startActivity(i);
    		}
    	});
    	
    	profButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    			//finish();	
    			Intent i = new Intent();
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.ProfScreen");
    			startActivity(i);
    		}
    	});
    	
    	nonAcaButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    			//finish();	
    			Intent i = new Intent();
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.NonAcademicRooms");
    			startActivity(i);
    		}
    	});
    	
    	floorButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    			//finish();	
    			Intent i = new Intent();
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.FloorPlanScreen");
    			startActivity(i);
    			}
    		});
    	
    	optionButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			//Toast.makeText(getApplicationContext(), saved.getRoom() + ":" + saved.getTransport() + ":" + saved.getFloor() + ":" + saved.getLatitude() + ":" + saved.getLongitude(), Toast.LENGTH_SHORT).show();
    			//finish();	
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
    		saved.setRoomNumber(null);
    		saved.setNonAcademicRoom(null);
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
    
	public void setDisplay(Display option)
	{
		saved.setDisplayOption(option);
	}
	
	public void setLong(double longitude)
	{
		saved.setLongitude(longitude);
	}
	
	public void setLat(double latitude)
	{
		saved.setLatitude(latitude);
	}
    
	public void setPref(boolean voiceOn)
	{
		//get preferences to write changes
		settings = getSharedPreferences("Options", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("voiceOn", voiceOn);
		
		//commit
		editor.commit();
		String justChecking = new Boolean(settings.getBoolean("voiceOn", true)).toString();
		System.out.println(justChecking + " Leaving Options");
	}
	
	public void getLocation()
	{
		// TODO Auto-generated constructor stub\
		locMan = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Criteria locCriteria = new Criteria();
		provider = locMan.getBestProvider(locCriteria, true);
		
		locListen = new MyLocationListener();
		
		locMan.requestLocationUpdates(provider, 0, 0, locListen);
		Toast.makeText(
				context,
				"Location changed : Lat: " + saved.getLatitude()
						+ " Lng: " + saved.getLongitude(),
				Toast.LENGTH_SHORT).show();
		System.out.println("Location changed : Lat: " + saved.getLatitude()
						+ " Lng: " + saved.getLongitude());
		//updateLocation(location);
	}

	private class MyLocationListener implements LocationListener {
		public void onLocationChanged(Location loc) {
			if (loc != null) {
				saved.setLatitude(loc.getLatitude());
				saved.setLongitude(loc.getLongitude());
				Toast.makeText(
						context,
						"Location changed : Lat: " + saved.getLatitude()
								+ " Lng: " + saved.getLongitude(),
						Toast.LENGTH_SHORT).show();
				System.out.println("Location changed : Lat: " + saved.getLatitude()
						+ " Lng: " + saved.getLongitude());
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "GPS status off", Toast.LENGTH_SHORT);
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "GPS status on", Toast.LENGTH_SHORT);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}
	
	private void initTextToSpeech(){
		if (voiceOnOff)
		{
		  Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
		  startActivityForResult(intent, TTS_DATA_CHECK);
		}
	  }
	  
	  private void startVoiceRecognitionActivity() {
		  	
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			
			// Specify the calling package to identify your application
			intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
			
			// Display an hint to the user about what he should say.
			if (prompt == 0){
				try {
					myText1 = "Are you looking for a room number, " +
							  "a professor, or a non-academic room?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(5000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Are you looking for a room number, " +
							  "a professor, or a non-academic room?");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (prompt == 1){
				try {
					myText1 = "What is the room number you are looking for?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(3000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What is the room number you are looking for?");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (prompt == 2){
				try {
					myText1 = "Who is the professor you are looking for?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(3000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Who is the professor you are looking for?");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (prompt == 3){
				try {
					myText1 = "Which non-academic room are you looking for?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(3000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Which non-academic room are you looking for?");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (prompt == 4){
				try {
					myText1 = "What floor are you currently on?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(3000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What floor are you currently on?");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (prompt == 5){
				try {
					myText1 = "Would you like to use the stairs or elevator?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(3000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Would you like to use the stairs or elevator?");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			else if (prompt == 6){
				try {
					myText1 = "Would you like to hear directions or see a map?";
					myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
					Thread.currentThread();
					Thread.sleep(3000);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Would you like to hear directions or see a map?");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
					
			// Given an hint to the recognizer about what the user is going to say
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
			        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			
			// Specify how many results you want to receive. The results will be sorted
			// where the first result is the one with higher confidence.
			intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
			
			// Specify the recognition language. This parameter has to be specified only if the
			// recognition has to be done in a specific language and not the default one (i.e., the
			// system locale). Most of the applications do not have to set this parameter.
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
			
		    startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	    }
	    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    	if(requestCode == TTS_DATA_CHECK){
	    		if (resultCode == Engine.CHECK_VOICE_DATA_PASS){
		    		myTextToSpeech = new TextToSpeech(this, new OnInitListener() {
				  		public void onInit(int status){
				  			if (status == TextToSpeech.SUCCESS){
				  				ttsIsInit = true;
				  				if(myTextToSpeech.isLanguageAvailable(Locale.US) >= 0)
				  					myTextToSpeech.setLanguage(Locale.US);
				  				myTextToSpeech.setPitch(0.8f);
				  				myTextToSpeech.setSpeechRate(1.1f);
				  				startVoiceRecognitionActivity();
				  			}
				  		}
		    		});
	    		}
	    		else {
	    			Intent installTTS = new Intent(Engine.ACTION_INSTALL_TTS_DATA);
	    			startActivity(installTTS);
	    		}
	    	}
	    	
	    	else if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
	    		ArrayList<String> results;
	    		results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	    		
	    		
	    		// TODO Do something with the recognized voice strings
	    		if (prompt == 0){
		    		if (results.get(0).equals("a room number")){
		    			prompt = 1;
		    			startVoiceRecognitionActivity();
		    		}
		    		else if (results.get(0).equals("a professor")){
		    			prompt = 2;
		    			startVoiceRecognitionActivity();
		    		}
		    		else if (results.get(0).equals("a non academic room")){
		    			prompt = 3;
		    			startVoiceRecognitionActivity();
		    		}
		    		else{
		    			prompt = 0;
		    			startVoiceRecognitionActivity();
		    		}
	    		}
	    		else if (prompt == 1){
	    			for (int i=0; i<roomNumbers.length; i++){
	    				if (results.get(0).equals(roomNumbers[i].toLowerCase())){
	    					roomNumber = results.get(0);
	    	    			prompt = 4;
	    	    			check1 = true;
	    	    			startVoiceRecognitionActivity();
	    				}
	    			}
	    			if (check1 == false){
		    			prompt = 1;
		    			startVoiceRecognitionActivity();	
	    			}
	    			
	    		}
	    		else if (prompt == 2){
	    			for (int i=0; i<professorNames.length; i++){
	    				if (results.get(0).equals(professorNames[i].toLowerCase())){
	    					professorName = results.get(0);
	    	    			prompt = 4;
	    	    			check2 = true;
	    	    			startVoiceRecognitionActivity();
	    				}
	    			}
	    			if (check2 == false){
		    			prompt = 2;
		    			startVoiceRecognitionActivity();	
	    			}
	    		}
	    		else if (prompt == 3){
	    			for (int i=0; i<nonAcademicRooms.length; i++){
	    				if (results.get(0).equals(nonAcademicRooms[i].toLowerCase())){
	    					nonAcademicRoom = results.get(0);
	    	    			prompt = 4;
	    	    			check3 = true;
	    	    			startVoiceRecognitionActivity();
	    				}
	    			}
	    			if (check3 == false){
		    			prompt = 3;
		    			startVoiceRecognitionActivity();	
	    			}
	    		}
	    		else if (prompt == 4){
	    			if (results.get(0).equals("basement")){
	    				floor = Floor.BASEMENT;
	    				prompt = 5;
		    			startVoiceRecognitionActivity();
	    			}
	    			else if (results.get(0).equals("first")){
	    				floor = Floor.FIRST;
	    				prompt = 5;
		    			startVoiceRecognitionActivity();
	    			}
	    			else if (results.get(0).equals("second")){
	    				floor = Floor.SECOND;
	    				prompt = 5;
		    			startVoiceRecognitionActivity();
	    			}
	    			else if (results.get(0).equals("third")){
	    				floor = Floor.THIRD;
	    				prompt = 5;
		    			startVoiceRecognitionActivity();
	    			}
	    			else{
	    				prompt = 4;
	    				startVoiceRecognitionActivity();
	    			}
	    		}
	    		else if (prompt == 5){
	    			if (results.get(0).equals("stairs")){
	    				transport = Transport.STAIRS;
	    				prompt = 6;
		    			startVoiceRecognitionActivity();
	    			}
	    			else if (results.get(0).equals("elevator")){
	    				transport = Transport.ELEVATOR;
	    				prompt = 6;
		    			startVoiceRecognitionActivity();
	    			}
	    			else{
	    				prompt = 5;
	    				startVoiceRecognitionActivity();
	    			}
	    		}
	    		else if (prompt == 6){
	    			if (results.get(0).equals("hear directions")){
	    				displayOption = Display.TEXTSPEECH;
	    				prompt = 0;
	    			}
	    			else if (results.get(0).equals("map")){
	    				displayOption = Display.MAP;
	    				prompt = 0;
	    			}
	    			else{
	    				prompt = 6;
	    				startVoiceRecognitionActivity();
	    			}
	    		}

	    		saved.setDisplayOption(displayOption);
	    		saved.setFloor(floor);
	    		saved.setNonAcademicRoom(nonAcademicRoom);
	    		saved.setProfessorName(professorName);
	    		saved.setRoomNumber(roomNumber);
	    		saved.setTransport(transport);
	    		
	    		//Sending stuff to server
    			/*Socket kkSocket = null;
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
				oos.writeObject(saved);
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
    			
    			
    			//Toast.makeText(getApplicationContext(), getProfMap.getRoomNumber() + ":" + getProfMap.getFloor() + ":" + getProfMap.getTransport(), Toast.LENGTH_SHORT).show();
    			/*Intent i = new Intent();
				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.LawsonNavigatorActivity");
				startActivity(i);
    		}
    	});*/
	    		
	    		check1 = false;
	    		check2 = false;
	    		check3 = false;
	    	}
	    	super.onActivityResult(requestCode, resultCode, data);
	    }
	
	/*
	public void updateLocation(Location loc)
	{
		if (loc != null)
		{
			saved.setLatitude(loc.getLatitude());
			saved.setLongitude(loc.getLongitude());
		}
		else
		{
			Toast.makeText(context, "No Location Found", Toast.LENGTH_SHORT);
		}
	}
*/

	public LocationListener getLocListen() {
		return locListen;
	}

	public void setLocListen(LocationListener locListen) {
		this.locListen = locListen;
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
