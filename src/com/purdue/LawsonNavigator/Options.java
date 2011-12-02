/*
 * @author Jeremy Jenkins
 * The Options Menu will allow the user to change certain aspects of the application. Granted, the Options
 *  only has a disable voice option, but the framework is there for more to be easily implemented.
 * 
 * After clicking save, the choices made will be saved to the phone so that they will still be in effect
 *   after teh user has closed the application.
 * 
 */

package com.purdue.LawsonNavigator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class Options extends Activity {
	private Button goButton, backButton;
	private CheckBox voiceChoice;
	private SharedPreferences settings;
	private boolean voice;
	private Activity parent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		settings = getSharedPreferences("Options", 0);
		voice = settings.getBoolean("voiceOn", true);
		parent = (Activity) this.getParent();
		setUpCheckbox();
		setUpButtons();
	}
	
	public void setUpCheckbox()
	{
		voiceChoice = (CheckBox)findViewById(R.id.voiceBox);
		voiceChoice.setChecked(voice);
		
		if (!voice)
		{
			voiceChoice.setChecked(false);
		}
		
		voiceChoice.setOnCheckedChangeListener(new OnCheckedChangeListener()  {
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (!voiceChoice.isChecked())
				{
					voiceChoice.setText("Voice Recognition Off");
					voice = false;
					//turn off speech
				}
				else
				{
					voiceChoice.setText("Voice Recognition On");
					voice = true;
					//turn on speech
				}
			}
			
		});
	}
	
	public void setUpButtons()
	{
		goButton = (Button)findViewById(R.id.saveOpt);
		backButton = (Button)findViewById(R.id.back);
		
		goButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			Toast.makeText(getApplicationContext(), "SAVE! ", Toast.LENGTH_SHORT).show();
    		}
    	});
		
		backButton.setOnClickListener(new Button.OnClickListener() { 
    		//@Override 
    		public void onClick(View v) { 
    			finish();
    		}
    	});
	}
	
	protected void onStop()
	{
		super.onStop();
		
		//get preferences to write changes
		settings = getSharedPreferences("Options", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("voiceOn", voice);
		
		//commit
		editor.commit();
		
		if (!voice)
		{
			try {
				parent.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
				parent.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK));
			} catch (Exception e) { System.out.println("Well, I shouldn't do this, but it works!"); }
		}
		else
		{
			Intent i = new Intent();
			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.LawsonNavigatorActivity");
			startActivity(i);
		}
	}
}
