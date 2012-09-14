package edu.cse535.assignment1;


import java.util.Date;

import commons.Coordinate;
import commons.Location;

import logic.IPServiceProviderUtil;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Color;
import android.widget.Button;


public class MainActivity extends Activity {

	/*
	 * Start UI variables
	 */
	//Initializations
	TextView joke;
	Button getjoke;
	TextView locationview;
	private static final String JOKE_ERROR 		= "Jokes on you!  In otherwords, no joke is currently available.";
	private static final String LOCATION_ERROR 	= "Location is currently unavailable.";
	private static final String JOKE_WAITING	= "Waiting on Joke Server";
	private static final String LOCATION_WAITING= "Waiting on Location Fix";

	/*
	 * End UI variables
	 */

	/*
	 * Start workflow variables
	 */
	static String 			IP 					= 	null;
	static Date				VALID_IP			= 	null;
	
	static String 			JOKE				= 	null;
	static Date				VALID_JOKE			= 	null;

	static LocationTask 	LOCATION_TASK 		= 	null;
	static Location 		LOCATION			=	null;
	static LocationManager 	LOCATION_MANAGER 	= 	null;
	static Date				VALID_LOCATION		=	null;

	static Coordinate 		COORDINATE 			= 	null;
	static Date				VALID_COORDINATE	=	null;

	boolean networkLocationEnabled = false;
	boolean gpsLocationEnabled= false;
	
	static boolean			FROM_IP				=	false;
	static boolean			FROM_COORDINATE		=	false;
	

	/*
	 * End workflow variables
	 */









	//Testing only
	public static int counter = 0;
	public static int created=0;
	String locationTag="location";

	@Override
	public void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);

		//Create UI
		setContentView(R.layout.activity_main);

		//Reference fields in UI
		instantiateUIVariables();

		//Set the UI elements to waiting state
		if(created<1)
		{
			setJokeWaiting();
			setLocationWaiting();
			//Get the Public IP associated with this device
			updateDeviceIP();

			//Determine is gps or network geolocation (coordinate based) are available
			determineLocationEnabled();

			//Launch another thread to Contact webservice and look up our city and state
			updateDeviceLocation();

			//Launt another threat to Contact server and get Joke for today.. or is it everyday as in
			//the joke does not change
			updateJoke();
		}
		else
		{
			setJoke(JOKE);
			setLocation(LOCATION);
		}

		
		created++;
		//Launch background process to updateLocationInformation
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

	/*------------------------------------------------------------------------------
	 * UI related
	 *------------------------------------------------------------------------------*/

	/**
	 * Initializes pointers to joke, getjoke, and locationview UI elements
	 */
	private void instantiateUIVariables()
	{
		joke=(TextView) findViewById(R.id.joke);

		getjoke=(Button) findViewById(R.id.getjoke);

		locationview = (TextView) findViewById(R.id.location); 
	}

	/**
	 * Sets joke of the day field in UI.  If error has occurred the styling will be
	 * different and message will be of type error
	 * @param jokeoftheday String to display on Jokefield of UI
	 * @param errorflag	True if error has occurred,<br>false otherwise
	 */
	public void setJokeText(String jokeoftheday,Boolean errorflag)
	{
		joke.setText("");
		if(errorflag)
		{
			joke.setBackgroundColor(Color.DKGRAY);
			joke.setTextColor(Color.RED);
			joke.setText(JOKE_ERROR);
		}
		else
		{
			joke.setBackgroundColor(Color.TRANSPARENT);
			joke.setTextColor(Color.WHITE);
			joke.setText(jokeoftheday);
		}
	}

	/**
	 * Sets the Joke element of UI to waiting state
	 */
	public void setJokeWaiting()
	{
		joke.setBackgroundColor(Color.DKGRAY);
		joke.setTextColor(Color.GREEN);
		joke.setText(JOKE_WAITING);
	}

	/**
	 * Sets location field in UI.  If error has occurred the styling will be
	 * different and message will be of type error
	 * @param locationObj Location object that support getCity():String and getState():String 
	 * and will display on Location field of UI
	 * @param errorflag True if error has occurred,<br>false otherwise
	 */
	public void setLocationText(Location locationObj,Boolean errorflag)
	{
		locationview.setText("");
		locationview.setBackgroundColor(Color.TRANSPARENT);
		if(errorflag)
		{
			
			locationview.setBackgroundColor(Color.DKGRAY);
			locationview.setTextColor(Color.RED);
			locationview.setText(LOCATION_ERROR);

		}			
		else
		{
			locationview.setBackgroundColor(Color.TRANSPARENT);
			locationview.setTextColor(Color.WHITE);
			locationview.setText("City: " +  locationObj.getCity() + " " +  "State: " + locationObj.getState());;
		}
	}	

	/**
	 * Sets the Location Element of UI to waiting state
	 */
	public void setLocationWaiting()
	{
		locationview.setBackgroundColor(Color.DKGRAY);
		locationview.setTextColor(Color.GREEN);
		locationview.setText(LOCATION_WAITING);
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

		FROM_COORDINATE = FROM_IP=false;
		setJokeWaiting();
		setLocationWaiting();
		updateLocation();
		updateJoke();
	}



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



	/*------------------------------------------------------------------------------
	 * IP related
	 *------------------------------------------------------------------------------*/

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


		getIPSituation();

		String ret=null;
		Toast msg = Toast.makeText(getApplicationContext(), "Nothing to say", Toast.LENGTH_LONG);
		switch(getIPSituation())
		{
		case 0:

			try
			{
				GetDeviceIPTask getIP = new GetDeviceIPTask();
				getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
				ret = getIP.get(configuration.Config.getTimeout(), configuration.Config.getTimeoutTimeUnit());
				setIP(ret);

			}
			catch(Exception e)
			{
				e.printStackTrace();
				ret=null;
			}
			msg = Toast.makeText(getApplicationContext(), "IP first update:\t"+IP, Toast.LENGTH_LONG);
			break;
		case 1:
			GetDeviceIPTask getIP = new GetDeviceIPTask();
			getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
			msg = Toast.makeText(getApplicationContext(), "IP not fresh:\t"+IP, Toast.LENGTH_LONG);
			break;
		case 2:
			msg = Toast.makeText(getApplicationContext(), "IP still fresh:\t"+IP, Toast.LENGTH_LONG);
			break;
		case -1:
			break;
		default:
			break;
		}
		/*if(VALID_IP==null)
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
			msg = Toast.makeText(getApplicationContext(), "IP first update:\t"+IP, Toast.LENGTH_LONG);
		}
		else
		{
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(VALID_IP.getTime()+configuration.Config.getIPRefreshWindow());
			if(currentTime.compareTo(windowTime)<1)
			{
				//Last refresh time was recent
				//we are doing nothing
				msg = Toast.makeText(getApplicationContext(), "IP still fresh:\t"+IP, Toast.LENGTH_LONG);
			}
			else
			{
				//last refresh time was not recent
				//we are using the async thread but not waiting
				GetDeviceIPTask getIP = new GetDeviceIPTask();
				getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
				msg = Toast.makeText(getApplicationContext(), "IP not fresh:\t"+IP, Toast.LENGTH_LONG);
			}
		}*/
		msg.show();
	}

	/**
	 * Determines what situation the ip is currently in:<br>
	 * Case 0: 1st ip update<br>
	 * Case 1: nth ip update and ip is Out of date<br>
	 * Case 2: nth ip update and ip is current<br>
	 * Case -1: Error has occured
	 * @return int representing status of ip.<br> 0 -> Case 0<br>1 -> Case 1 <br> 2 -> Case 2 <br> -1 -> Error
	 */
	private int getIPSituation()
	{
		int ret = -1;
		if(VALID_IP==null)
		{
			//CASE 0
			//First IP update
			ret=0;

		}
		else
		{
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(VALID_IP.getTime()+configuration.Config.getIPRefreshWindow());
			if(currentTime.compareTo(windowTime)>0)
			{
				//CASE 1
				//current time is after allowed time
				//Current IP is out of date
				ret = 1;
			}
			else
			{
				//CASE 2
				//current time is before allowed time
				//Current IP is current
				ret = 2;
			}
		}
		return ret;
	}

	/**
	 * Sets current IP to that of ip and updates the timestamp associated with ip to the current time
	 * @param ip String to set as new IP of activity
	 */
	private void setIP(String ip)
	{
		IP = ip;
		VALID_IP = new Date();
	}



	/*------------------------------------------------------------------------------
	 * Location related
	 *------------------------------------------------------------------------------*/

	/**
	 * Uses getLocationSitutation and does the following depending on the result of getLocationSitutation
	 * 1 -> updateLocation is called
	 * 2 -> nothing
	 * 3 -> updateLocation is called
	 * 4 -> updateLocation is called
	 */
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
			msg = Toast.makeText(getApplicationContext(), "Location Still Fresh city "+LOCATION.getCity()+" state "+LOCATION.getState(), Toast.LENGTH_LONG);
			break;
		case 3:
			//nth location gathering, location is still fresh but no location
			//object is null
			msg = Toast.makeText(getApplicationContext(), "Location null and Fresh", Toast.LENGTH_LONG);
			updateLocation();
			break;
		case 4:
			//nth location gathering, location is outdated
			msg = Toast.makeText(getApplicationContext(), "Location Outdated city "+LOCATION.getCity()+" state "+LOCATION.getState(), Toast.LENGTH_LONG);
			updateLocation();
			break;
		default:
			break;
		}

		msg.show();
	}

	/**
	 * Uses Async thread to gather location of device by using the url of web service
	 * If webservice is null then the operation exits immediately without calling web service.
	 * @param webservice String url of web service that provides location of device
	 */
	private void getDeviceLocationFromIP(String webservice)
	{
		if(webservice!=null)
		{
			GetLocationFromIPTask getIPLocation = new GetLocationFromIPTask();
			getIPLocation.execute(new String[] {webservice});
		}
	}

	/**
	 * Creates a LocationManager object if one is not present.  Checks to see if device support gps and/or network geolocation services
	 * Stores True in gpsLocationEnabled if gps is available
	 * Stores True int networkLocationEnabled if network is available 
	 */
	private void determineLocationEnabled()
	{
		//boolean ret = false;
		//Log.d(locationTag,"Start Location Enabled");
		if(LOCATION_MANAGER==null)
		{
			LOCATION_MANAGER = (android.location.LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
		}

		try
		{
			gpsLocationEnabled = LOCATION_MANAGER.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
			networkLocationEnabled = LOCATION_MANAGER.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);

		}
		catch(Exception e)
		{
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new LocationListener class that will receive updates every 30 seconds.  If a locationlistener is already created no new locationlistener will
	 * be created.  If GPS and network sensors are not available no location listener will be created
	 */
	private void createLocationListener()
	{
		if(LOCATION_TASK==null)
		{
			LOCATION_TASK = new LocationTask();

			if(gpsLocationEnabled)
			{
				LOCATION_MANAGER.requestLocationUpdates(android.location.LocationManager.GPS_PROVIDER, configuration.Config.getSensorRequestTime(), 0, LOCATION_TASK);
			}
			else
			{
				if(networkLocationEnabled)
				{
					LOCATION_MANAGER.requestLocationUpdates(android.location.LocationManager.NETWORK_PROVIDER, configuration.Config.getSensorRequestTime(), 0, LOCATION_TASK);
				}
			}
		}
	}

	/**
	 * Sets current LOCATION to that of location and updates the timestamp associated with LOCATION to the current time
	 * @param local Location object to set as new LOCATION of activity
	 */
	private void setLocation(commons.Location local)
	{
		
			LOCATION = local;
			VALID_LOCATION = new Date();
			if(LOCATION!=null)
			{
				setLocationText(LOCATION,false);
			}
			else
			{
				if(FROM_COORDINATE&&FROM_IP)
				{
					setLocationText(LOCATION,true);
				}
			}
		
	}

	/**
	 * Updates LOCATION of device by calling getDeviceLocationFromIP if COORDINATE is unavailable and by getDeviceLocationFromGPS otherwise.  Calls createLocationListener
	 * if COORDINATE is unavailable
	 */
	private void updateLocation()
	{

		if(COORDINATE == null)
		{
			print("Updating Location From IP");
			//We do not have a gps coordinate
			//This can be from locationTask not being created, or no sensors available

			//IF we do not have a sensor listener then create one
			createLocationListener();

			//If we do not have gps coordinates because we do not have sensors or we do not have a fix lets get the location from IP
			getDeviceLocationFromIP(IP);

		}
		else
		{
			//We have sensor data so lets use the other service
			print("Updating Location From Coordinate");
			getDeviceLocationFromCoordinate(COORDINATE);
		}
	}

	/**
	 * Determines what situation the location is currently in:<br>
	 * Case 1: 1st location update<br>
	 * Case 2: nth location update and location is current<br>
	 * Case 3: nth location update and location is null<br>
	 * Case 4: nth location update and location is out of date<br>
	 * 
	 * @return int representing status of location.<br> 1 -> Case 1<br>2 -> Case 2 <br> 3 -> Case 3 <br> 4 -> Case 4 <br> 0 -> Error
	 */
	private int getLocationSitutation()
	{
		int ret =0;
		if(VALID_LOCATION == null)
		{
			//first location gathering
			ret = 1;
		}
		else
		{
			//nth location gathering
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(VALID_LOCATION.getTime()+configuration.Config.getLocationRefreshWindow());
			if(currentTime.compareTo(windowTime)<1)
			{
				//Location is still fresh
				ret = 2;
				if(LOCATION==null)
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

	/**
	 * Uses getLocationSitutation and does the following depending on the result of getLocationSitutation
	 * 1 -> false
	 * 2 -> true
	 * 3 -> false
	 * 4 -> false
	 * @return boolean value according the above cases
	 */
	private boolean isCurrentLocationValid()
	{
		boolean ret = false;
		
		switch(getLocationSitutation())
		{
		case 1:
			ret = false;
			break;
		case 2:
			ret=true;
			break;
		case 3:
			ret=false;
			break;
		case 4:
			ret=false;
			break;
		default:
			ret = false;
			break;
		}
		return ret;
	}
	
	/**
	 * Uses Async thread to gather location of device by using the coordinate and 
	 * getLocationFromCoordinate operation of RequestProcessor class
	 * If coordinate is null then the operation exits immediately without calling RequestProcessor.
	 * @param coordiante Coordinate object that is used when calling getLocationFromCoordinate
	 */
	private void getDeviceLocationFromCoordinate(Coordinate coordinate)
	{
		if(coordinate!=null)
		{
			GetLocationFromCoordinateTask getCoordinateLocation = new GetLocationFromCoordinateTask();
			getCoordinateLocation.execute(new Coordinate[] {coordinate});
		}
	}



	/*------------------------------------------------------------------------------
	 * Coordinate related
	 *------------------------------------------------------------------------------*/

	/**
	 * Uses getCoordinateSituation and does the following depending on the result of getCoordinateSituation
	 * 1 -> false
	 * 2 -> true
	 * 3 -> false
	 * 4 -> false
	 */
	private boolean isCoordinateValid() 
	{
		boolean ret=false;
		switch(getCoordinateSitutation())
		{
		case 1:
			print("Coordiante uninstantiated");
			ret = false;
			break;
		case 2:
			print("Coordiante is valid");
			ret = true;
			break;
		case 3:
			print("Coordiante uninstantiated");
			ret = false;
			break;
		case 4:
			print("Coordiante is out of date");
			ret = false;
			break;
		default:
			break;
		}
		return ret;
	}

	/**
	 * Determines what situation the COORDINATE is currently in:<br>
	 * Case 1: 1st COORDINATE update has not been performed<br>
	 * Case 2: nth COORDINATE update have been performed and COORDINATE is current<br>
	 * Case 3: nth COORDINATE update have been performed and COORDINATE is null<br>
	 * Case 4: nth COORDINATE update have been performed and COORDINATE is out of date<br>
	 * 
	 * @return int representing status of location.<br> 1 -> Case 1<br>2 -> Case 2 <br> 3 -> Case 3 <br> 4 -> Case 4 <br> 0 -> Error
	 */
	private int getCoordinateSitutation()
	{
		int ret =0;
		if(VALID_COORDINATE == null)
		{
			//first COORDINATE gathering
			ret = 1;
		}
		else
		{
			//nth COORDINATE gathering
			Date currentTime = new Date();
			Date windowTime = new Date();
			windowTime.setTime(VALID_COORDINATE.getTime()+configuration.Config.getSensorRefreshWindow());
			if(currentTime.compareTo(windowTime)<1)
			{
				//COORDINATE is still fresh
				ret = 2;
				if(COORDINATE==null)
				{
					ret =3;
				}
			}
			else
			{
				//COORDINATE is outdated
				ret = 4;
			}

		}

		return ret;
	}

	/**
	 * Sets current COORDINATE to that of coordinate and updates the timestamp associated with COORDINATE to the current time
	 * @param local Coordinate object to set as new COORDINATE of activity
	 */
	private void setCoordinate(Coordinate coordinate)
	{
		COORDINATE = coordinate;
		VALID_COORDINATE = new Date();
		if(COORDINATE!=null)
		{
			print(COORDINATE.toString());
			updateLocation();
		}
		else
		{
			print("Coordinate is null");
		}
	}

	/*------------------------------------------------------------------------------
	 * Joke related
	 *------------------------------------------------------------------------------*/
	
	private void updateJoke() {
		GetJokeTask getJoke = new GetJokeTask();
		getJoke.execute(new Object[]{null});

	}
	
	private void setJoke(String joke)
	{
		JOKE = joke;
		VALID_JOKE = new Date();
		if(JOKE!=null)
		{
			setJokeText(JOKE,false);
		}
		else
		{
			setJokeText(JOKE_ERROR,true);
		}
	}
	
	private boolean isCurrentJokeValid()
	{
		boolean ret = false;
		if(JOKE!=null)
		{
			ret = true;
		}
		else
		{
			ret = false;
		}
		return ret;
	}
	
	public class LocationTask implements LocationListener{


		public void onLocationChanged(android.location.Location location) {
			//if(LocationManager.isAllowed(location,coordinate))
			if(!isCoordinateValid())
			{
				setCoordinate(new commons.Coordinate(location.getLongitude(), location.getLatitude()));

			}
			else
			{

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

		@Override
		protected String doInBackground(String... params) {
			String ret = null;
			ret = IPServiceProviderUtil.getRawData(params[0]);
			ret = IPServiceProviderUtil.parseIPFromCheckIP(ret);
			return ret;
		}

		protected void onPostExecute(String feed) {
			setIP(feed);
			print(IP);

		}

	}//End GetDeviceIPTask

	class GetLocationFromIPTask extends AsyncTask<String,Void,commons.Location>
	{

		@Override
		protected commons.Location doInBackground(String... params) {;
		commons.Location ret = null;
		ret = processor.RequestProcessor.getLocationFromIP(params[0]);
		return ret;
		}

		@Override
		protected void onPostExecute(commons.Location feed) {
			// TODO: do somethisng with the feed
			if(feed!=null)
			{
				if(feed.getCity().equals("-")&&feed.getState().equals("-"))
				{
					feed=null;
				}
			}
			FROM_COORDINATE=true;
			setLocation(feed);
		}

	}

	class GetLocationFromCoordinateTask extends AsyncTask<Coordinate,Void,commons.Location>
	{

		@Override
		protected commons.Location doInBackground(Coordinate... params) {
			commons.Location ret = null;
			ret = processor.RequestProcessor.getLocationFromCoordinate(params[0]);
			return ret;
		}

		@Override
		protected void onPostExecute(commons.Location feed) {
			// TODO: do somethisng with the feed
			if(feed!=null)
			{
				if(feed.getCity().equals("-")&&feed.getState().equals("-"))
				{
					feed=null;
				}
			}
			FROM_COORDINATE=true;
			setLocation(feed);
		}

	}

	class GetJokeTask extends AsyncTask<Object,Void,String>
	{

		@Override
		protected String doInBackground(Object... params) {
			String ret = null;
			ret = processor.RequestProcessor.getJoke();
			return ret;
		}

		@Override
		protected void onPostExecute(String feed) {
			// TODO: do somethisng with the feed
			setJoke(feed);
		}

	}

	/*------------------------------------------------------------------------------
	 * Testing related
	 *------------------------------------------------------------------------------*/
	private void print(String text)
	{
		Toast msg = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
		msg.show();
	}

}
