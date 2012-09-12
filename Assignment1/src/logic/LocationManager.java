package logic;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

public class LocationManager implements android.location.LocationListener{

	private boolean updated = false;
	//private Activity caller;
	

	
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
