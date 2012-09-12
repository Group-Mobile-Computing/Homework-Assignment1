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
	 * Get Joke Of The Day From TCP Based JOTD Server.	 
	 */
	public static String getJoke ()
	{
		String joke = null;
		try {
	          Socket jokeClientsocket = new Socket(ProcessorProperties.JokeServerAddress, ProcessorProperties.JokeServerPort);
	          BufferedReader socketReader = new BufferedReader(new
	             InputStreamReader(jokeClientsocket.getInputStream()));
	          
	          PrintWriter socketWriter = new PrintWriter(jokeClientsocket.getOutputStream(), 
	                  true);
	          
	          String Servername = "";
	          boolean finished = false;
	          
	          do {	          
		          String serverMessage = "";
		          int asciiValue = 0;
		          while (!socketReader.ready()) {}
		          do {
		        	  asciiValue = socketReader.read();
		        	  if (asciiValue != 0)
		        		  serverMessage += Character.toString ((char) asciiValue);
		          } while (!MessageEnd(serverMessage));
		          
		          String messageType = GetMessageType(serverMessage);
		        
		          String clientMessage = "";
		          
		          if (messageType != null & messageType.length() > 0)
		          {
			          switch (ProcessorProperties.MessageType.GetMessageType(messageType)) {
			            case HELLO:  {
			            	Servername = GetServerName(serverMessage);
			            	clientMessage = "<AA1 HELLO THIS IS " + ProcessorProperties.ClientIdentity + ">";;		            
			                     break;
			            }
			            case HI:  clientMessage = "<AA1 REQUEST " + Servername + " JOTD>";
	                    		 break;
			            case GOODBYE:  {
			            	finished = true;
			                break;
			            }
			            case REQUEST:
			                     break;
			            case RESPONSE:  {
			            	joke = ExtractJoke(serverMessage);
			            	clientMessage = "<AA1 GOODBYE " + Servername + ">";
			            	break;
			            }			                     
			            default:  {
			            	clientMessage = "<AA1 You Sent Invalid Message .....";
			            	finished = true;
			                break;
			            }
			          }		          		          		          
		          }
		          
		          if (clientMessage.length() > 0)
		          {
		        	  socketWriter.write(clientMessage);
		        	  socketWriter.flush();
		          }		
		          else
		          {
		        	  finished = true;
		          }		      
	          }while (!finished);
	          
	          socketWriter.close();
	          socketReader.close();
	          jokeClientsocket.close();	          
	       }
	       catch(Exception e) {
	    	   joke = null;
	       }       
		
		return joke;
	}
	
	/** 
	 * Determine if the end of message has been received or not.	
	 * @param String representing the current message. 
	 */
	public static boolean MessageEnd(String serverMessage)
	{
		boolean status = false;
		try
		{									
			if (serverMessage.charAt(serverMessage.length() - 1) == ProcessorProperties.GreaterThanSignASCII )
			{
				int PrecedingBackslahes = 0;
				int iterator = 2;
				char temp = 0;
				do
				{
					temp = serverMessage.charAt(serverMessage.length()- iterator);
					if (temp == ProcessorProperties.BackslashASCII)
					{
						PrecedingBackslahes++;
					}
					iterator++;
				}while (temp == ProcessorProperties.BackslashASCII); 				
				
				if ((PrecedingBackslahes % 2) == 0)
				{
					return true;
				}				
			}
		}
		catch (Exception ex)
		{
			status = false;
		}
		
		return status;
	}
	
	/** 
	 * Extract Message Type from the current completed message.	
	 * @param String representing the current message. 
	 */
	public static String GetMessageType(String serverMessage)
	{
		try
		{
		String MessageType = serverMessage.substring
				(serverMessage.indexOf(ProcessorProperties.Space) + 1
						, serverMessage.indexOf(ProcessorProperties.Space) + 1 + 
						serverMessage.substring(serverMessage.indexOf(ProcessorProperties.Space) + 1).indexOf(ProcessorProperties.Space)).toUpperCase();
		return MessageType;
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	/** 
	 * Extract Server Name from the Initial Message Received from TCP server on establishing connection.	
	 * @param String representing the current message. 
	 */
	public static String GetServerName(String serverMessage)
	{
		try
		{
			String ServerName = serverMessage.substring
					(serverMessage.indexOf(ProcessorProperties.ServerNameStartConstant) + ProcessorProperties.ServerNameStartConstant.length()
							, serverMessage.indexOf(ProcessorProperties.ServerNameStartConstant) + 
							serverMessage.substring(serverMessage.indexOf(ProcessorProperties.ServerNameStartConstant)).indexOf(ProcessorProperties.ServerNameEndConstant));
			return ServerName;
		}
		catch (Exception ex)
		{
			return null;
		}
	}

	/** 
	 * Extract JOTD from the current completed message.	
	 * @param String representing the current message. 
	 */
	public static String ExtractJoke(String serverMessage)
	{
		try
		{
			String Joke = serverMessage.substring
					(serverMessage.indexOf(ProcessorProperties.JokeStartConstant) + ProcessorProperties.JokeStartConstant.length()
							, serverMessage.indexOf(ProcessorProperties.JokeStartConstant) + 
							(serverMessage.substring(serverMessage.indexOf(ProcessorProperties.JokeStartConstant)).length() - 1 ));
			return Joke;
		}
		catch (Exception ex)
		{
			return null;
		}
	}
	
	/** 
	 * Get Location as a Location object on the basis of provided IP.	 
	 *  @param ipaddressofdevice string based IP address of the host.
	 */
	public static Location getLocationFromIP(String ipaddressofdevice)
	{
		Location location = null;
		try
		{        
	        if (ipaddressofdevice != null)
	        {
	        	String Data = DoHTTPGet(ProcessorProperties.LocationFromIPGetURL +  ipaddressofdevice);
	        	
		        if (Data != null & Data.length() > 0) 
		        {	        	
		        	location = 	new Location(Data.substring(
		    	        		Data.indexOf(ProcessorProperties.LocationFromIPStateElement), Data.length()).
		    	        		substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromIPStateElement), Data.length()).
		    	        		indexOf(ProcessorProperties.LocationFromIPStateElementEnd)).replace(ProcessorProperties.LocationFromIPStateElement, ""),
		    	        		Data.substring(
		        				Data.indexOf(ProcessorProperties.LocationFromIPCityElement), Data.length()).
		        				substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromIPCityElement), Data.length()).
		        				indexOf(ProcessorProperties.LocationFromIPCityElementEnd)).replace(ProcessorProperties.LocationFromIPCityElement, ""));
		        } 
	        }       
		}
		catch (Exception e)
		{
			location = null;		
		}
		
		return location;
	}

	/** 
	 * Get Location as a Location object on the basis of provided Coordinates (Longitude and Latitude Values).
	 * *  @param ipaddressofdevice string based IP address of the host.
	 */
	public static Location getLocationFromCoordinate(Coordinate coordinate)
	{
		Location location = null;
		try
		{
			if (coordinate.getLongitude() != null & coordinate.getLatitude() != null)
			{	      
				String Data = DoHTTPGet(ProcessorProperties.LocationFromCoordinateGetURL + 
						coordinate.getLongitude().toString() + "," + 
						coordinate.getLatitude().toString() + ProcessorProperties.LocationFromCoordinateGetURL2);
				
		        if (Data != null)
		        {		        	
			        if (Data.length() > 0) 
			        {	        	
			        	location = 	new Location(Data.substring(
			        				Data.indexOf(ProcessorProperties.LocationFromCoordinateStateElement), Data.length()).
			        				substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromCoordinateStateElement), Data.length()).
			        				indexOf(ProcessorProperties.LocationFromCoordinateStateElementEnd)).replace(ProcessorProperties.LocationFromCoordinateStateElement, ""),
			        				Data.substring(
			        				Data.indexOf(ProcessorProperties.LocationFromCoordinateCityElement), Data.length()).
			        				substring(0, Data.substring(Data.indexOf(ProcessorProperties.LocationFromCoordinateCityElement), Data.length()).
			        				indexOf(ProcessorProperties.LocationFromCoordinateCityElementEnd)).replace(ProcessorProperties.LocationFromCoordinateCityElement, ""));
			        }  
		        }		        		              	  
			}
		}
		catch (Exception e)
		{
			location = null;			
		}
		
		return location;
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