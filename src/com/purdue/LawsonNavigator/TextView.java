package com.purdue.LawsonNavigator;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.Engine;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.widget.EditText;

public class TextView extends Activity {
	static String directions = "", directions2 = "";
	private String[] directionsArray;
	private LawsonNavigatorActivity saved;
	private SharedPreferences settings;
	private boolean voiceOnOff = false;
	private int prompt = 0;
	private EditText setText;
	private boolean ttsIsInit = false, read = false;
	private TextToSpeech myTextToSpeech = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textview);
		
		settings = getSharedPreferences("Options", 0);
		voiceOnOff = settings.getBoolean("voiceOn", true);
		setText = (EditText)findViewById(R.id.textDirections);
		saved = new LawsonNavigatorActivity();
		directionsArray = new String[directions.length() + directions2.length()];
		directionsArray[0] = directions.substring(1, directions.indexOf(","));
		//System.out.println(directions);
		directions = directions.substring(directions.indexOf(",") + 2);
		int temp = 1;
		
		while (directions.charAt(0) != ']')
		{
			try {
				directionsArray[temp] = directions.substring(0, directions.indexOf(","));
				directions = directions.substring(directions.indexOf(",") + 2);
			} catch(Exception e) {
				directionsArray[temp] = directions.substring(0, directions.indexOf("]"));
				directions = directions.substring(directions.indexOf("]"));
			}
			//System.out.println(directionsArray[temp]);
			temp++;
		}
		
		if (!directions2.matches(""))
		{
			while (directions.charAt(0) != ']')
			{
				try {
					directionsArray[temp] = directions2.substring(0, directions2.indexOf(","));
					directions2 = directions2.substring(directions2.indexOf(",") + 2);
				} catch(Exception e) {
					directionsArray[temp] = directions2.substring(0, directions2.indexOf("]"));
					directions2 = directions2.substring(directions2.indexOf("]"));
				}
				//System.out.println(directionsArray[temp]);
				temp++;
			}
		}
		
		for (int i = 0; i < temp; i++)
		{
			if (temp != i)
			{
				setText.append(directionsArray[i] + "\n");
			} else {
				setText.append(directionsArray[i]);
			}
			
		}
		
		//readDirections();
	}
	
	/*public void readDirections()
	{
		private void initTextToSpeech()
		{
			if (voiceOnOff)
			{
			  Intent intent = new Intent(Engine.ACTION_CHECK_TTS_DATA);
			  startActivityForResult(intent, TTS_DATA_CHECK);
			}
		}
		  
	    private void startVoiceRecognitionActivity() 
	    {
		  	if (voiceOnOff)
		  	{
				// Display an hint to the user about what he should say.
				while (0)
				{
					try
					{
						if (!read)
						{
							myText1 = directionsArray[prompt];
							myTextToSpeech.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);
							Thread.currentThread();
							Thread.sleep(5000);
							read = true;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		  	}
	  }
	}*/
}