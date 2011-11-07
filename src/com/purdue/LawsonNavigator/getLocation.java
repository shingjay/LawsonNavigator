package com.purdue.LawsonNavigator;

import android.content.Context;
import android.location.*;
import android.os.SystemClock;
import android.widget.Toast;

public class getLocation
{
	private LocationManager locMan;
	private LocationListener locListen;
	private Location location;
	private Context context;
	private String provider;
	private UserInput userLatLong;
	
	public getLocation(Context context, UserInput user)
	{
		// TODO Auto-generated constructor stub\
		this.context = context;
		userLatLong = user;
		locMan = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Criteria locCriteria = new Criteria();
		provider = locMan.getBestProvider(locCriteria, true);
		
		if (provider != null)
		{
			location = locMan.getLastKnownLocation(provider);
		}
		
		updateLocation(location);
	}

	public void updateLocation(Location location)
	{
		if (location != null)
		{
			userLatLong.setLatitude(location.getLatitude());
			userLatLong.setLongitude(location.getLongitude());
		}
		else
		{
			Toast.makeText(context, "No Location Found", Toast.LENGTH_SHORT);
		}
	}

	public LocationListener getLocListen() {
		return locListen;
	}

	public void setLocListen(LocationListener locListen) {
		this.locListen = locListen;
	}
}
