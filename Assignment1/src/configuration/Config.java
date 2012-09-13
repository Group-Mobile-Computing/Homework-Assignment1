package configuration;

import java.util.concurrent.TimeUnit;

public class Config {

	private static final long TIMEOUT = 3;
	private static final TimeUnit TIMEUNIT= TimeUnit.SECONDS;
	
	public static long getTimeout()
	{
		return TIMEOUT;
	}
	
	public static TimeUnit getTimeoutTimeUnit()
	{
		return TIMEUNIT;
	}
	
}
