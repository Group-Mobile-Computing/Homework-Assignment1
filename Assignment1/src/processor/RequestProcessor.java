package processor;

import java.net.*;
import java.io.*;
import commons.*;

/**
 * 
 * @author Shoaib
 *
 */
public class RequestProcessor {

	/** 
	 * Get Location in format "City, State" on the basis of provided IP.	 
	 */
	public static String getJoke()
	{
		return null;
	}
	
	/** 
	 * Get Location in format "City, State" on the basis of provided IP.	 
	 *  @param ipaddressofdevice string based IP address of the host.
	 */
	public static String getLocationFromIP(String ipaddressofdevice)
	{
		String Location = null;
		try
		{        
	        if (ipaddressofdevice != null)
	        {
	        	String Data = DoHTTPGet(ProcessorProperties.LocationFromIPGetURL +  ipaddressofdevice);
	        	
		        if (Data != null) 
		        {	        	
		        	Location = 	Data.substring(
		        				Data.indexOf(ProcessorProperties.LocationFromIPCityElement), Data.length()).
		        				substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromIPCityElement), Data.length()).
		        				indexOf(ProcessorProperties.LocationFromIPCityElementEnd)).replace(ProcessorProperties.LocationFromIPCityElement, "") + ", " +
		        				Data.substring(
		    	        		Data.indexOf(ProcessorProperties.LocationFromIPStateElement), Data.length()).
		    	        		substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromIPStateElement), Data.length()).
		    	        		indexOf(ProcessorProperties.LocationFromIPStateElementEnd)).replace(ProcessorProperties.LocationFromIPStateElement, "");
		        } 
	        }       
		}
		catch (Exception e)
		{
			Location = null;		
		}
		
		return Location;
	}

	/** 
	 * Get Location in format "City, State" on the basis of provided Coordinates (Longitude and Latitude Values).
	 * *  @param ipaddressofdevice string based IP address of the host.
	 */
	public static String getLocationFromCoordinate(Coordinate coordinate)
	{
		String Location = null;
		try
		{
			if (coordinate.getLongitude() != null & coordinate.getLatitude() != null)
			{	      
				String Data = DoHTTPGet(ProcessorProperties.LocationFromCoordinateGetURL + 
						coordinate.getLongitude().toString() + "," + 
						coordinate.getLatitude().toString() + ProcessorProperties.LocationFromCoordinateGetURL2);
				
		        if (Data != null)
		        {		        	
			        if (Data != null & !Data.isEmpty()) 
			        {	        	
			        	Location = 	Data.substring(
			        				Data.indexOf(ProcessorProperties.LocationFromCoordinateCityElement), Data.length()).
			        				substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromCoordinateCityElement), Data.length()).
			        				indexOf(ProcessorProperties.LocationFromCoordinateCityElementEnd)).replace(ProcessorProperties.LocationFromCoordinateCityElement, "") + ", " +
			        				Data.substring(
			    	        		Data.indexOf(ProcessorProperties.LocationFromCoordinateStateElement), Data.length()).
			    	        		substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromCoordinateStateElement), Data.length()).
			    	        		indexOf(ProcessorProperties.LocationFromCoordinateStateElementEnd)).replace(ProcessorProperties.LocationFromCoordinateStateElement, "");
			        }  
		        }		        		              	  
			}
		}
		catch (Exception e)
		{
			Location = null;			
		}
		
		return Location;
	}
	
	/** 
	 * Make a HTTP Get request against a provided URL.	 
	 *  @param URL of the address from where to acquire data.
	 */
	public static String DoHTTPGet(String URL)
	{
		String Data = null;
		try
		{
			if (URL != null)
			{
				URL HTTPGetURLLocation = new URL(URL);
		        URLConnection HTTPGetURLLocationConnection = HTTPGetURLLocation.openConnection();
		        BufferedReader ResponseData = new BufferedReader(
		                                new InputStreamReader(
		                                		HTTPGetURLLocationConnection.getInputStream()));
		        		        
		        if (ResponseData != null)
		        {
		        	String inputLine;
		        	while ((inputLine = ResponseData.readLine()) != null) 
		        		Data = Data + "\n" + inputLine;
		        	ResponseData.close();		        	
		        }		        		              	  
			}
		}
		catch (Exception e)
		{
			Data = null;			
		}
		
		return Data;
	}
	
}