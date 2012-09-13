package logic;

import java.util.Date;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

public class LocationManager implements android.location.LocationListener{

	private boolean updated = false;
	private static Date previous = null;
	//private Activity caller;
	

	public static boolean isAllowed(android.location.Location location, commons.Coordinate coordinate)
	{
		boolean ret = false;
		if(previous==null)
		{
			previous = new Date();
			ret = true;
		}
		else
		{
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(previous.getTime()+configuration.Config.getSensorRefreshWindow());
			if(currentTime.compareTo(windowTime)>0)
			{
				ret = true;
			}
			else
			{
				ret = false;
			}
		}
		
		return ret;
	}
	
	public boolean isUpdated()
	{
		return updated;
	}
	
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	

}
