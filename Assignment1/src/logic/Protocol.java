package logic;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import android.util.Log;

public class Protocol {


	
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
		ui.Dummy.setWaitingJoke();
		ui.Dummy.setWaitingLocation();
		return true;
	}
	
	/**
	 * Uses this.getLocationInformation and this.getJoke.  
	 * @return True if valid transition into onCreate state and able to set joke/location in UI to waiting
	 * <br>False otherwise
	 */
	public boolean getAllRemoteInformation()
	{
		
		System.out.println(this.getAndroidIP());
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
	public String  getAndroidIP()
	{
		boolean found = false;
		String ret = null;
		
		System.out.println("Start get IP");
		try
		{
			//Iterate over all the network interfaces the device has
			Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while(networkInterfaces.hasMoreElements())
			{
				
				java.net.NetworkInterface networkInterface = networkInterfaces.nextElement();
				
				//Iterate over all the IPs an interface has
				Enumeration<InetAddress> ipAddresses = networkInterface.getInetAddresses();
				System.out.println("Interface:\t"+networkInterface.getDisplayName());
				while(ipAddresses.hasMoreElements())
				{
					java.net.InetAddress ipAddress = ipAddresses.nextElement();
					System.out.println("Interface IP:\t"+ipAddress.getHostAddress());
					//Look for non-loopback IPs
					if(!ipAddress.isLoopbackAddress())
					{
						ret= ipAddress.getHostAddress();
						System.out.println("IP:\t"+ret);
						found=true;
					}
					
				}//End while over ipaddress
				
				
			}//End while over networkinterfaces
			
		}
		catch(java.net.SocketException se)
		{
			Log.e("getAndroidIP", se.toString());
		}
		
		
		return ret;
	}
	
	public commons.Coordinate getCoordinate()
	{
		commons.Coordinate ret = null;
		
		return ret;
	}
	
	
}
