/*
Voice_Recognition.java
Written by Ryan Thompson

Demonstrates text-to-speech and voice recognition.
Unfinished and not linked with main program
*/

package com.purdue.LawsonNavigator;

import java.util.List;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Voice_Recognition extends Activity{
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
	  String myText1 = null;
	  Floor floor = null;
	  Transport transport = null;
	  String roomNumber = null;
	  String nonAcademicRoom = null;
	  String professorName = null;
	  private boolean check1=false, check2=false, check3=false;
	  private ListView wordsList;
	  
	  /** Called when the activity is first created. */
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.voice_recog2);
	       //startVoiceRecognitionActivity();
	 
	        Button speakButton = (Button) findViewById(R.id.speakButton);
	 
	        wordsList = (ListView) findViewById(R.id.list);
	 
	        // Disable button if no recognition service is present
	        PackageManager pm = getPackageManager();
	        List<ResolveInfo> activities = pm.queryIntentActivities(
	                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
	        if (activities.size() == 0)
	        {
	            speakButton.setEnabled(false);
	            speakButton.setText("Recognizer not present");
	        }
	    }
	  
	  public void speakButtonClicked(View v){
	      initTextToSpeech();  
		  //startVoiceRecognitionActivity();
	  }
	  
	  private void initTextToSpeech(){
		  Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
		  startActivityForResult(intent, TTS_DATA_CHECK);
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
				} catch (InterruptedException e) {
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
					
			// Given an hint to the recognizer about what the user is going to say
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
			        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			
			// Specify how many results you want to receive. The results will be sorted
			// where the first result is the one with higher confidence.
			intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
			
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
	    				prompt = 0;
	    			}
	    			else if (results.get(0).equals("elevator")){
	    				transport = Transport.ELEVATOR;
	    				prompt = 0;
	    			}
	    			else{
	    				prompt = 5;
	    				startVoiceRecognitionActivity();
	    			}
	    		}
	    		
	    		UserInput ui = new UserInput(transport, floor, 0, 0, roomNumber, nonAcademicRoom, professorName);
	    		check1 = false;
	    		check2 = false;
	    		check3 = false;
	    	}
	    	super.onActivityResult(requestCode, resultCode, data);
	    }
    
}
