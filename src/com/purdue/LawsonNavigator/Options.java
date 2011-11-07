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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Options extends Activity {
	private Button goButton, backButton;
	private CheckBox voiceChoice;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);
		setUpCheckbox();
		setUpButtons();
	}
	
	public void setUpCheckbox()
	{
		voiceChoice = (CheckBox)findViewById(R.id.voiceBox);
		
		voiceChoice.setOnCheckedChangeListener(new OnCheckedChangeListener()  {
			//@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (!arg1)
				{
					voiceChoice.setText("Voice Recognition Off");
				}
				else
				{
					voiceChoice.setText("Voice Recognition On");
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
    			Toast.makeText(getApplicationContext(), "SAVE!", Toast.LENGTH_SHORT).show();
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
    			i.setClassName("com.purdue.LawsonNavigator", "com.purdue.LawsonNavigator.LawsonNavigatorActivity");
    			startActivity(i);
    		}
    	});
	}
}
