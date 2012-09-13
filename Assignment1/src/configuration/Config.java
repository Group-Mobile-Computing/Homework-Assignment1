package configuration;

import java.util.concurrent.TimeUnit;

public class Config {

	private static final long TIMEOUT = 3;
	private static final TimeUnit TIMEUNIT= TimeUnit.SECONDS;
	private static long IPREFRESHWINDOW= 1*60*1000;
	private static long LOCATIONREFRESHWINDOW= 1*60*1000;
	private static long SENSORWINDOW = 1*30*1000;
	
	public static long getTimeout()
	{
		return TIMEOUT;
	}
	
	public static TimeUnit getTimeoutTimeUnit()
	{
		return TIMEUNIT;
	}
	
	public static long getIPRefreshWindow()
	{
		return IPREFRESHWINDOW;
	}
	
	public static long getLocationRefreshWindow()
	{
		return LOCATIONREFRESHWINDOW;
	}
	
	public static long getSensorRefreshWindow()
	{
		return SENSORWINDOW;
	}
	
}
