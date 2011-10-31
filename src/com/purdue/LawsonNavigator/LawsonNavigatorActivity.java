package com.LawsonNavigator.org;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.content.*;

public class LawsonNavigatorvActivity extends Activity
{
    private Button roomButton, profButton, nonAcaButton, floorButton, optionButton;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        setUpButton();
    }
    
    public void setUpButton()
    {
        roomButton = (Button)findViewById(R.id.roomNum);
    	profButton = (Button)findViewById(R.id.profName);
    	nonAcaButton = (Button)findViewById(R.id.nonAcaRoom);
    	floorButton = (Button)findViewById(R.id.floorPlan);
    	optionButton = (Button)findViewById(R.id.options);
    	
    	roomButton.setOnClickListener(new Button.OnClickListener() { 
    		@Override 
    		public void onClick(View v) { 
    				//Toast.makeText(getApplicationContext(), "room!", Toast.LENGTH_LONG).show();
    				Intent i = new Intent();
    				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.RoomNumber");
    				startActivity(i);
    			}
    		});
    	
    	profButton.setOnClickListener(new Button.OnClickListener() { 
    		@Override 
    		public void onClick(View v) { 
    				//Toast.makeText(getApplicationContext(), "professor!", Toast.LENGTH_LONG).show();
    				Intent i = new Intent();
    				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.ProfScreen");
    				startActivity(i);
    			}
    		});
    	
    	nonAcaButton.setOnClickListener(new Button.OnClickListener() { 
    		@Override 
    		public void onClick(View v) { 
    				//Toast.makeText(getApplicationContext(), "non-academic!", Toast.LENGTH_LONG).show();
    				Intent i = new Intent();
    				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.NonAcademicRooms");
    				startActivity(i);
    			}
    		});
    	
    	floorButton.setOnClickListener(new Button.OnClickListener() { 
    		@Override 
    		public void onClick(View v) { 
    				//Toast.makeText(getApplicationContext(), "floor!", Toast.LENGTH_LONG).show();
    				Intent i = new Intent();
    				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.FloorPlanScreen");
    				startActivity(i);
    			}
    		});
    	
    	optionButton.setOnClickListener(new Button.OnClickListener() { 
    		@Override 
    		public void onClick(View v) { 
    				//Toast.makeText(getApplicationContext(), "options!", Toast.LENGTH_LONG).show();
    				Intent i = new Intent();
    				i.setClassName("com.LawsonNavigator.org", "com.LawsonNavigator.org.Options");
    				startActivity(i);
    			}
    		});
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