package com.purdue.LawsonNavigator;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class getLocation
{
	private LocationManager locMan = null;
	private LocationListener locListen = null;
	private LawsonNavigatorActivity saved;
	private SharedPreferences settings;
	private Location location = null;
	private Context context;
	private String provider;
	private UserInput userLatLong;
	public static double lat = 0.0, longi = 0.0;
	
	public getLocation(Context con, UserInput user)
	{
		// TODO Auto-generated constructor stub\
		this.context = con;
		locMan = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Criteria locCriteria = new Criteria();
		provider = locMan.getBestProvider(locCriteria, true);
		locListen = new MyLocationListener();
		
		locMan.requestLocationUpdates(provider, 0, 0, locListen);
		
		updateLocation(location);
	}

	private class MyLocationListener implements LocationListener 
	{
		public void onLocationChanged(Location loc) {
			if (loc != null) {
				lat = loc.getLatitude();
				longi = loc.getLongitude();
				Toast.makeText(context, "Location changed : Lat: " + lat + " Lng: " + longi, Toast.LENGTH_SHORT).show();
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
	
	public void updateLocation(Location loc)
	{
		if (loc != null)
		{
			userLatLong.setLatitude(loc.getLatitude());
			userLatLong.setLongitude(loc.getLongitude());
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
