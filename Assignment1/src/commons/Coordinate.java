package commons;
/**
 * Provides object representation for Coordinate.  Currently Coordinate is comprised of Latitude,Longitude pair.
 * There is No sanity checking currently as of revision 0
 * @author Mike S.
 *
 */
public class Coordinate {
	private static double UNINITIALIZED = 900;
	private double latitude=UNINITIALIZED, longitude=UNINITIALIZED;
	
	/**
	 * Sets the fields of Longitude and Latitude of the newly created object to
	 * longitude and latitude (respectively) of the arguements.
	 * @param longitude double value between -180.00000000 and 180.00000000 inclusive
	 * @param latitude double value between -90.000000 and 90.000000 inclusive
	 */
	public Coordinate(double longitude, double latitude)
	{
		setLongitude(longitude);
		setLatitude(latitude);
	}
	
	public String getLongitude()
	{
		String ret = null;
		if(longitude!=UNINITIALIZED)
		{
			ret = longitude+"";
		}
		
		return ret;
	}
	private void setLongitude(double longitude)
	{
		this.longitude=longitude;
	}
	
	
	public String getLatitude()
	{
		String ret = null;
		if(latitude!=UNINITIALIZED)
		{
			ret = latitude+"";
		}
		
		return ret;
	}
	private void setLatitude(double latitude)
	{
		this.latitude=latitude;
	}

}
