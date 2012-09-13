package logic;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;

//Structure of <> is Parameters, where to place update, and where to place result
public class GetDeviceIPTask1 extends AsyncTask<String, Void, String> {

	//logic.Protocol protocol;
	@Override
	protected String doInBackground(String... params) {
		String ret = null;
		
		ret = IPServiceProviderUtil.getRawData(params[0]);
		/*StringBuilder interim = new StringBuilder();
		String ret=null;
		try {
			
			protocol = (logic.Protocol)params[1];
			
			//This task only supports working with first url
			URL HTTPGetURLLocation = new URL((String)params[0]);
			
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
				System.out.println("Data from "+params[0]+" "+ interim.toString());
				//There is data present so convert it String and place it in return value
				ret = interim.toString();
			}
			else
			{
				System.out.println("No data from "+params[0]);
				//There is no data present so place null into return value
				ret=null;
			}
			

		} catch (Exception e) {
			System.out.println("ERROR\n");
			e.printStackTrace();
		}


		System.out.println("end");
		System.out.println(parseIPFromCheckIP(interim.toString()));		
		*/
		return ret;
	}

    protected void onPostExecute(String feed) {
        // TODO: check this.exception 
        // TODO: do somethisng with the feed
    	String result = parseIPFromCheckIP(feed);
    	System.out.println("finished everything:\t"+result);
    	//ipObtained(feed);
    	//protocol.deltaIPAddress(result);
    	
    }
	
	private static String parseIPFromCheckIP(String rawResult)
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
