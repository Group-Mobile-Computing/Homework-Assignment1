package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class IPServiceProviderUtil {

	private static final String IP_SERVICE = "http://checkip.dyndns.com";
	
	public static String getIPServiceURL()
	{
		return IP_SERVICE;
	}
	public static String getRawData(String url)
	{
		StringBuilder interim = new StringBuilder();
		String ret=null;
		try {
			//This task only supports working with first url
			URL HTTPGetURLLocation = new URL(url);
			
			URLConnection HTTPGetURLLocationConnection = HTTPGetURLLocation.openConnection();
			
			BufferedReader ResponseData = new BufferedReader(
					new InputStreamReader(
							HTTPGetURLLocationConnection.getInputStream()));
			
			if (ResponseData != null)
			{
				String inputLine;
				while ((inputLine = ResponseData.readLine()) != null) 
					interim.append(inputLine);
				ResponseData.close();		        	
			}		       

			//Ensure there is something there for us to return 
			if(interim.length()>0)
			{
				//System.out.println("Data from "+url+" "+ interim.toString());
				//There is data present so convert it String and place it in return value
				ret = interim.toString();
			}
			else
			{
				System.out.println("No data from "+url);
				//There is no data present so place null into return value
				ret=null;
			}
			

		} catch (Exception e) {
			System.out.println("ERROR\n");
			e.printStackTrace();
		}


		return ret;	
	}

	public static String parseIPFromCheckIP(String rawResult)
	{
		String ret = null;
		String startTagForIP = "Current IP Address: ";
		String endTagForIP = "</body>";
		//Ensure there is data to parse
		if(rawResult!=null)
		{
			//Ensure data contains tag we know how to handle
			if(rawResult.contains(startTagForIP))
			{
				int endOfIP = rawResult.indexOf(endTagForIP);
				int startOfIP = rawResult.indexOf(startTagForIP);
				ret= rawResult.substring(startOfIP+startTagForIP.length(), endOfIP);
			}
			else
			{
				//Unable to parse, unexpected formating
				ret=null;
			}
		}
		else
		{
			//No data was passed to method
			ret=null;
		}

		return ret;
	}
}
