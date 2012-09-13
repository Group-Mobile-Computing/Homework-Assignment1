package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import commons.Coordinate;

import processor.ProcessorProperties;

import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class Protocol {


	//private final String IP_SERVICE= "http://wwww.ipchicken.com";
	private String IP = null;

	LocationListener mloclistener = null;
	
	private commons.Coordinate coordinate;
	public Protocol()
	{
		
	}
	
	/**
	 * Sets the Location and Joke fields in the UI to waiting state.
	 * If this is an invalid transition from another state will I.E. onCreate should only be called once
	 * will do nothing and return false;
	 * @return True if valid transition into onCreate state and able to set joke/location in UI to waiting
	 * <br>False otherwise
	 */
	public boolean deltaOnCreate()
	{
		//Add in checks to ensure we are in the correct state
		return true;
	}
	
	/**
	 * Uses this.getLocationInformation and this.getJoke.  
	 * @return True if valid transition into onCreate state and able to set joke/location in UI to waiting
	 * <br>False otherwise
	 */
	public boolean getAllRemoteInformation()
	{
		this.getAndroidIP();
		this.getDeviceCoordinate();
		return true;
	}
	
	/**
	 * Iterates over the network interfaces the device possess and selects the 
	 * first non-loopback IP address to return.
	 * <br> Uses Permissions:
	 * <br>android.permission.INTERNET
	 * <br>android.permission.ACCESS_NETWORK_STATE
	 * @return String representation of 1st non-loopback IP address
	 * <br> null if none found or error has occurred
	 */
	public void  getAndroidIP()
	{
		GetDeviceIPTask getIP = new GetDeviceIPTask();
		getIP.execute(new String[] {IPServiceProviderUtil.getIPServiceURL()});
	}
	
	public String toString()
	{
		return "protocol";
	}
	
	public void deltaIPAddress(String IP)
	{
		System.out.println("End Getting IP Method");
		System.out.println("Async returned:\t"+IP);
		this.IP = IP;
	}
	
	public void deltaCoordinate(commons.Coordinate coordinate)
	{
		System.out.println("Coordinate available");
		this.coordinate = coordinate;
	}
	
	public commons.Coordinate getDeviceCoordinate()
	{
		return this.coordinate;
	}
	
	
	
	//Structure of <> is Parameters, where to place update, and where to place result
	class GetDeviceIPTask extends AsyncTask<String, Void, String> {

		//logic.Protocol protocol;
		@Override
		protected String doInBackground(String... params) {
			String ret = null;
			ret = IPServiceProviderUtil.getRawData(params[0]);
			return ret;
		}

	    protected void onPostExecute(String feed) {
	        // TODO: do somethisng with the feed
	    	String result = IPServiceProviderUtil.parseIPFromCheckIP(feed);
	    	//System.out.println("finished everything:\t"+result);
	    	deltaIPAddress(result);
	    }
		
	}//End GetDeviceIPTask
	
	
	
	 
}

/*class IpService extends AsyncTask<String, Void, String> {

//private Exception exception;
String ret = null;
StringBuilder interim = new StringBuilder();

protected String doInBackground(String... urls) {
    try {
    	URL HTTPGetURLLocation = new URL(IP_SERVICE);
        URLConnection HTTPGetURLLocationConnection = HTTPGetURLLocation.openConnection();
        BufferedReader ResponseData = new BufferedReader(
                                new InputStreamReader(
                                		HTTPGetURLLocationConnection.getInputStream()));
        if (ResponseData != null)
        {
        	String inputLine;
        	while ((inputLine = ResponseData.readLine()) != null) 
        		interim.append( "\n" + inputLine);
        	ResponseData.close();		        	
        }		       
		
        if(interim.length()>0)
        {
        	System.out.println("Data from "+IP_SERVICE+" "+ interim.toString());
        }
        else
        {
        	System.out.println("No data from "+IP_SERVICE);
        }
		return "hi";
		
    } catch (Exception e) {
        //this.exception = e;
        return null;
    }
}

protected void onPostExecute(String feed) {
    // TODO: check this.exception 
    // TODO: do something with the feed
	System.out.println("Completed correctly");
}
}*/
