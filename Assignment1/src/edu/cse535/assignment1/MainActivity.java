package edu.cse535.assignment1;

//import ui.Dummy;
//import logic.GetDeviceIPTask;
import commons.Location;

import logic.IPServiceProviderUtil;
import logic.LocationManager;
//import logic.Protocol.GetDeviceIPTask;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
//import android.content.Context;
//import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	logic.Protocol protocol = null;
	commons.Coordinate coordinate = null;
	String ip = null;
	android.location.LocationManager locationManager = null;
	LocationTask locationTask = null;
	boolean networkLocationEnabled = false;
	boolean gpsLocationEnabled= false;
	commons.Location location=null;
	
	String locationTag="location";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Load in the layout from UI
        setContentView(ui.Dummy.getContentView());
        
        //Create the Protocol object we need
       // protocol = new logic.Protocol();
        
        //Set Location and Joke to processing state
        ui.Dummy.setWaitingJoke();
    	ui.Dummy.setWaitingLocation();
    	
    	//Gather Device IP
    	Log.d(locationTag,"Starting application");
    	getDeviceIP();
        
    	//Is gps enabled
    	/*determineLocationEnabled();
    	
    	//If location is enabled launch the location listener
    	if(gpsLocationEnabled|networkLocationEnabled)
    	{
    		Log.d(locationTag,"Location service available");
    		getDeviceLocation();
    	}
    	else
    	{
    		Log.d(locationTag,"Location service unavailable");
    	}
    	
    	
    	if(location!=null)
    	{
    		Log.d(locationTag, location.getCity()+" "+location.getState());
    		
    	}
    	else
    	{
    		Log.d(locationTag, "No location available from IP");
    	}
    	
        
        */
    }
    
    /**
     * Recollects GPS information maps it to a Location.  If GPS is unavailable
     * collects IP and maps it to a Location.  Queries Joke server for joke.
     * Presents updated information to user.  While these are processing they are
     * the components are set to processing state.
     * @param v  Current screen user is looking at.  In this case the only screen
     * in the application
     */
    private void refresh(View v)
    {
    	
    }
    
    /**
     * Terminates application normally.  Cleans up data.
     * 
     * @param v Current screen user is looking at.  In this case the only screen
     * in the application
     */
    private void exit(View v)
    {
    	//Not implement see OneNote Assignment 1 @ Mike TableTop
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    
    
    
    
    
    private void getDeviceIP()
    {
    	GetDeviceIPTask getIP = new GetDeviceIPTask();
		getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
		Log.d(locationTag,"IP after post ip");
    }
    
    private void getDeviceLocationFromIP(String ip)
    {
    	GetLocationFromIPTask getIPLocation = new GetLocationFromIPTask();
    	getIPLocation.execute(new String[] {ip});
    }
    
    private void determineLocationEnabled()
    {
    	//boolean ret = false;
    	Log.d(locationTag,"Start Location Enabled");
    	if(locationManager==null)
    	{
    		locationManager = (android.location.LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    	}
    	
    	try
    	{
    		gpsLocationEnabled = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    		networkLocationEnabled = locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error");
    		e.printStackTrace();
    	}
    	//return ret;
    }
    
    private void getDeviceLocation()
    {
    	locationTask = new LocationTask();
    	
    	if(gpsLocationEnabled)
    	{
    		locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 0, 0, locationTask);
    	}
    	else
    	{
    		if(networkLocationEnabled)
    		{
    			locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 0, 0, locationTask);
    		}
    	}
    }
    
    
    
    
    public class LocationTask implements LocationListener{
		 

		public void onLocationChanged(android.location.Location location) {
			//commons.Coordinate coordinate = 
			if(LocationManager.isMoreAccurate(location,coordinate))
			{
				commons.Coordinate coordinate = new commons.Coordinate(location.getLongitude(), location.getLatitude());
		    	if(gpsLocationEnabled)
		    	{
		    		locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER,5 * 60 * 1000 , 0, locationTask);
		    	}
		    	else
		    	{
		    		if(networkLocationEnabled)
		    		{
		    			locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 5 * 60 * 1000, 0, locationTask);
		    		}
		    	}
				//protocol.deltaCoordinate(coordinate);
				
			}
		}

		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			String msg = "GPS disabled";
			determineLocationEnabled();
			System.out.println(msg);
		
		}

		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			String msg = "GPS enabled";
			determineLocationEnabled();
			System.out.println(msg);
		}


		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
	//Structure of <> is Parameters, where to place update, and where to place result
	class GetDeviceIPTask extends AsyncTask<String, Void, String> {

		//logic.Protocol protocol;
		@Override
		protected String doInBackground(String... params) {
			String ret = null;
			Log.d(locationTag,"start device ip task :\t"+params[0]);
			ret = IPServiceProviderUtil.getRawData(params[0]);
			return ret;
		}

	    protected void onPostExecute(String feed) {
	        // TODO: do somethisng with the feed
	    	String result = IPServiceProviderUtil.parseIPFromCheckIP(feed);
	    	Log.d(locationTag,"finished everything:\t"+result);
	    	ip=result;
	    	//getDeviceLocationFromIP(ip);
	    	
	    }
		
	}//End GetDeviceIPTask

	class GetLocationFromIPTask extends AsyncTask<String,Void,commons.Location>
	{

		@Override
		protected commons.Location doInBackground(String... params) {
			Log.d(locationTag,"Starting getLocationFromIPTask with param: "+params[0]);
			commons.Location ret = null;
			ret = processor.RequestProcessor.getLocationFromIP(params[0]);
			Log.d(locationTag,"getLocationFromIPTask:\t "+ret.getCity()+" "+ret.getState());
			return ret;
		}

		@Override
	    protected void onPostExecute(commons.Location feed) {
	        // TODO: do somethisng with the feed
	    	Log.d(locationTag,"End of IPTASK:\tcity:"+feed.getCity()+" state:"+feed.getState());
	    	
	    }
		
	}
}
