package edu.cse535.assignment1;

//import ui.Dummy;
//import logic.GetDeviceIPTask;
import java.util.Date;

import commons.Coordinate;
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
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

//import com.google.android.maps.GeoPoint;
//import com.google.android.maps.MapActivity;
//import com.google.android.maps.MapController;
//import com.google.android.maps.MapView;
//import com.google.android.maps.MyLocationOverlay;
//import com.google.android.maps.Overlay;
//import com.google.android.maps.OverlayItem;
//import com.google.android.maps.Projection;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	//Initializations
	TextView joke;
	Button getjoke;
    //GeoPoint geoPoint;
    TextView locationview;
    //Initializations

	static Date		ipRefreshed			= 	null;
	static Date		locationRefreshed	=	null;
	static Date		coordinateRefreshed	=	null;

	logic.Protocol protocol = null;
	commons.Coordinate coordinate = null;
	static String ip = null;
	android.location.LocationManager locationManager = null;
	LocationTask locationTask = null;
	boolean networkLocationEnabled = false;
	boolean gpsLocationEnabled= false;


	static commons.Location location	=	null;

	public static int counter = 0;
	public static int created=0;
	String locationTag="location";

	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//--------------------------------------------View-----------------------------------------//
		 //Map View
//	        MapView mapview=(MapView) findViewById(R.id.mapview);
//	        MapController mc=mapview.getController();	        
//	        GeoPoint geoPoint = new GeoPoint( (int) (33.82* 1E6), (int) (-111.7 * 1E6));	        
//	        mc.setZoom(10);
//	        mc.animateTo(geoPoint);	        
//	        mc.setCenter(geoPoint);	
//	        mapview.setBuiltInZoomControls(true);
        //Map View
        
        //Display Joke of the Day
        
	
//        String location = "Tempe,Pheonix";
		joke=(TextView) findViewById(R.id.joke);
        
        getjoke=(Button) findViewById(R.id.getjoke);
        
        locationview = (TextView) findViewById(R.id.location); 

		//Display Joke of the Day
		
		//Display Locations
//		if(location.trim().length() != 0)
//		{
//			locationview.setText(location);
//		}
		//Display Locations
	
		//Refresh Button Click Listener
//		getjoke.setOnClickListener(new OnClickListener()
//        {
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				String jokeoftheday="Life isn't like a box of chocolates. It's more like a jar of jalapenos. What you do today, might burn your butt tomorrow.";
//				
//				if(jokeoftheday.trim().length()==0)
//				{
//					joke.setBackgroundColor(Color.DKGRAY);
//					joke.setTextColor(Color.RED);
//					joke.setText("Unable to reach the server. Please try again later.");
//				}
//				/*Call  method to get joke and assigne to jokeoftheday*/
//				else
//				{
//					joke.setText(jokeoftheday);
//				}
//			}
//        	
//        });
		//Refresh Joke Button Listener
		
		
		//--------------------------------------------View-----------------------------------------//
		
		
		

	

//		//Load in the layout from UI
//		setContentView(ui.Dummy.getContentView());
//
//		//Create the Protocol object we need
//		// protocol = new logic.Protocol();
//
//		//Set Location and Joke to processing state
//		ui.Dummy.setWaitingJoke();
//		ui.Dummy.setWaitingLocation();

//		//Gather Device IP
//		Log.d(locationTag,"Starting application");
//		updateDeviceIP();
//		//Toast msg = Toast.makeText(getApplicationContext(), "ip: "+ip, Toast.LENGTH_LONG);
//		//msg.show();
//		Log.d(locationTag, "getDeviceIP returned:\t"+ip);
//
//		//Determine if some sort of location service is available on device
//		Log.d("locationServiceAvailable", "Start determineLocationEnabled gps:\t"+gpsLocationEnabled+" network: "+networkLocationEnabled);
//		determineLocationEnabled();
//		Log.d("locationServiceAvailable", "End determineLocationEnabled gps:\t"+gpsLocationEnabled+" network: "+networkLocationEnabled);
//
//		//Get Location from ip and or sensors using remote service
//		//If both are unavailable then we will display no Location information available
//		if(ip==null && !gpsLocationEnabled && !networkLocationEnabled)
//		{
//			Log.d("LocationGathering", "No Location Information Available");
//		}
//
//		//If IP address is available get location from remote service
//		if(ip!=null)
//		{
//			Log.d("LocationGathering","Start getDeviceLocationFromIP:\t"+ip);
//			getDeviceLocationFromIP(ip);
//
//			Log.d("LocationGathering","End getDeviceLocationFromIP:\t"+ip+"\n");
//		}
//		else
//		{
//			//Ip is not available
//		}
//
//
//		//While getting location from IP process is running in async thread
//		//Start up the location listening which will update the Location UI section
//		updateDeviceLocation();

	}

	/**
	 * Recollects GPS information maps it to a Location.  If GPS is unavailable
	 * collects IP and maps it to a Location.  Queries Joke server for joke.
	 * Presents updated information to user.  While these are processing they are
	 * the components are set to processing state.
	 * @param v  Current screen user is looking at.  In this case the only screen
	 * in the application
	 */
	public void refresh(View v)
	{
		//Call the method setjoketext(jokeoftheday, flag) with two parameters to set the view
		
		setjoketext("Joke Refresh", false);
		setlocationtext(null, true);
	}
	
	
	// Sets the joke of the day field
	public void setjoketext(String jokeoftheday,Boolean errorflag)
	{
		if(errorflag)
		{
			joke.setBackgroundColor(Color.DKGRAY);
			joke.setTextColor(Color.RED);
			joke.setText(jokeoftheday);
		}
		else
		{
			joke.setText(jokeoftheday);
		}
	}
	// Sets the joke of the day field
	
	// Sets the Location of the day field
		public void setlocationtext(Location locationObj,Boolean errorflag)
		{
			if(errorflag)
			{
				locationview.setBackgroundColor(Color.DKGRAY);
				locationview.setTextColor(Color.RED);
				locationview.setText("Unable to detect the location. Please try again!");
				
			}			
			else
			{
				locationview.setText("City: " +  locationObj.getCity() + " " +  "State: " + locationObj.getState());;
			}
		}
		// Sets the joke of the day field
	
	/**
	 * Terminates application normally.  Cleans up data.

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







	/**
	 * If IP is null we will wait 3 seconds for the operation to complete and produce a new possibly null ip
	 * according to the following cases:
	 * Ip has never been updated
	 * 	last update is null and up is null
	 * 	we will use async getdeviceIPTask and wait for it to finish up to 3 seconds
	 * 
	 * IP has been updated recently
	 * 	last update is not null and ip may or may not be null 
	 * 	last update is within allowed timewindow
	 * 	we will do nothing
	 * 
	 * IP has been updated long
	 * 	last update is not null, ip may or may not be null
	 * 	last update is out of allowed timewindow
	 * 	we will use async getdeviceIPtask and not wait for it to finish
	 * 
	 * @return void
	 */
	private void updateDeviceIP()
	{

	

		String ret=null;
		Toast msg;
		if(ipRefreshed==null)
		{
			//never updated
			//we are using async thread and waiting 3 seconds
			GetDeviceIPTask getIP = new GetDeviceIPTask();
			getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
			try
			{
				ret = getIP.get(configuration.Config.getTimeout(), configuration.Config.getTimeoutTimeUnit());
				setIP(ret);

			}
			catch(Exception e)
			{
				e.printStackTrace();
				ret=null;
			}
			msg = Toast.makeText(getApplicationContext(), "IP first update:\t"+ip, Toast.LENGTH_LONG);
		}
		else
		{
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(ipRefreshed.getTime()+configuration.Config.getIPRefreshWindow());
			if(currentTime.compareTo(windowTime)<1)
			{
				//Last refresh time was recent
				//we are doing nothing
				msg = Toast.makeText(getApplicationContext(), "IP still fresh:\t"+ip, Toast.LENGTH_LONG);
			}
			else
			{
				//last refresh time was not recent
				//we are using the async thread but not waiting
				GetDeviceIPTask getIP = new GetDeviceIPTask();
				getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
				msg = Toast.makeText(getApplicationContext(), "IP not fresh:\t"+ip, Toast.LENGTH_LONG);
			}
		}
		msg.show();
	}



	private void setIP(String ip)
	{
		this.ip = ip;
		ipRefreshed = new Date();
	}

	private void getDeviceLocationFromIP(String ip)
	{
		if(ip!=null)
		{
			GetLocationFromIPTask getIPLocation = new GetLocationFromIPTask();
			getIPLocation.execute(new String[] {ip});
		}
	}

	private void determineLocationEnabled()
	{
		//boolean ret = false;
		//Log.d(locationTag,"Start Location Enabled");
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

	/**
	 * Creates a new LocationListener class that will receive updates every 30 seconds.  If a locationlistener is already created no new locationlistener will
	 * be created.  If GPS and network sensors are not available no location listener will be created
	 */
	private void createLocationListener()
	{
		if(locationTask==null)
		{
			locationTask = new LocationTask();

			if(gpsLocationEnabled)
			{
				locationManager.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, 30*1000, 0, locationTask);
			}
			else
			{
				if(networkLocationEnabled)
				{
					locationManager.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, 30*1000, 0, locationTask);
				}
			}
		}
	}

	private void setLocation(commons.Location local)
	{
		this.location = local;
		locationRefreshed = new Date();
	}


	
	private void updateDeviceLocation()
	{
		//int situation = 0;
		//Have we gotten a location previously
		Toast msg = Toast.makeText(getApplicationContext(), "Nothing to say", Toast.LENGTH_LONG);
		switch(getLocationSitutation())
		{
		case 1:
			//first location gathering
			msg = Toast.makeText(getApplicationContext(), "First Location Gathering", Toast.LENGTH_LONG);
			updateLocation();
			break;
		case 2:
			//nth location gathering, location is still fresh
			//Do nothing location is still up to date
			msg = Toast.makeText(getApplicationContext(), "Location Still Fresh city "+location.getCity()+" state "+location.getState(), Toast.LENGTH_LONG);
			break;
		case 3:
			//nth location gathering, location is still fresh but no location
			//object is null
			msg = Toast.makeText(getApplicationContext(), "Location null and Fresh", Toast.LENGTH_LONG);
			updateLocation();
			break;
		case 4:
			//nth location gathering, location is outdated
			msg = Toast.makeText(getApplicationContext(), "Location Outdated city "+location.getCity()+" state "+location.getState(), Toast.LENGTH_LONG);
			updateLocation();
			break;
		default:
			break;
		}
		
		msg.show();
	}
	
	private void updateLocation()
	{
		if(coordinate == null)
		{

			//We do not have a gps coordinate
			//This can be from locationTask not being created, or no sensors available

			//IF we do not have a sensor listener then create one
			createLocationListener();

			//If we do not have gps coordinates because we do not have sensors or we do not have a fix lets get the location from IP
			getDeviceLocationFromIP(ip);

		}
		else
		{
			//We have sensor data so lets use the other service
			getDeviceLocationFromGPS(coordinate);
		}
	}

	/*
	 * This is first location gathering
	 * 	locationRefreshed==null, 
	 * 
	 * This is nth location gathering and Location is fresh
	 * 
	 * This is nth location gathering and Location is not fresh
	 *
	 * Location is outdated
	 * 	IP is null
	 * 	coordinates are null
	 * 	is location Listener is null
	 * Location is not outdated
	 * 
	 * output 1 
	 */
	private int getLocationSitutation()
	{
		int ret =0;
		if(locationRefreshed == null)
		{
			//first location gathering
			ret = 1;
		}
		else
		{
			//nth location gathering
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(locationRefreshed.getTime()+configuration.Config.getLocationRefreshWindow());
			if(currentTime.compareTo(windowTime)<1)
			{
				//Location is still fresh
				ret = 2;
				if(location==null)
				{
					ret =3;
				}
			}
			else
			{
				//Location is outdated
				ret = 4;
			}

		}

		return ret;
	}


	


	private void getDeviceLocationFromGPS(Coordinate coordinate2) {
		// TODO Auto-generated method stub

	}




	public class LocationTask implements LocationListener{


		public void onLocationChanged(android.location.Location location) {
			if(LocationManager.isAllowed(location,coordinate))
			{
				coordinate = new commons.Coordinate(location.getLongitude(), location.getLatitude());
				Toast msg = Toast.makeText(getApplicationContext(), "counter: "+counter +"Coordinate: "+coordinate.toString(), Toast.LENGTH_LONG);
				msg.show();
				counter++;
				Log.d("SensorLocation", "coordinate more accurate:\t"+coordinate.toString());
				//setCoordinate
			}
			else
			{
				Log.d("SensorLocation", "Previous coordinate more accurate");
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
			ret = IPServiceProviderUtil.parseIPFromCheckIP(ret);
			return ret;
		}

		protected void onPostExecute(String feed) {
			// TODO: do somethisng with the feed
			//String result = IPServiceProviderUtil.parseIPFromCheckIP(feed);
			setIP(feed);
			Log.d(locationTag,"finished everything:\t"+feed);
			//getDeviceLocationFromIP(ip);

		}

	}//End GetDeviceIPTask

	class GetLocationFromIPTask extends AsyncTask<String,Void,commons.Location>
	{

		@Override
		protected commons.Location doInBackground(String... params) {
			//Log.d(locationTag,"Starting getLocationFromIPTask with param: "+params[0]);
			commons.Location ret = null;
			ret = processor.RequestProcessor.getLocationFromIP(params[0]);
			Log.d(locationTag,"getLocationFromIPTask:\t "+ret.getCity()+" "+ret.getState());
			/*Toast msg = Toast.makeText(getApplicationContext(), "IP Background Task location: "+ret.getCity()+" state:"+ret.getState(), Toast.LENGTH_LONG);
			msg.show();*/
			return ret;
		}

		@Override
		protected void onPostExecute(commons.Location feed) {
			// TODO: do somethisng with the feed

			Log.d("LocationGathering","End of IPTASK:\tcity:"+feed.getCity()+" state:"+feed.getState());
			Toast msg = Toast.makeText(getApplicationContext(), "IP Task location: "+feed.getCity()+" state:"+feed.getState(), Toast.LENGTH_LONG);
			setLocation(feed);
			msg.show();

		}

	}
}
