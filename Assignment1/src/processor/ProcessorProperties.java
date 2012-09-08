package processor;

/**
 * 
 * @author Shoaib
 *
 */

public class ProcessorProperties {

	/** 
	 * Static properties pointing to the URL to get location from IP. Also included Tags to parse the returned information (XML is being returned).
	 */
	public static String LocationFromIPGetURL = "http://services.ipaddresslabs.com/iplocation/locateip?key=demo&ip=";
	public static String LocationFromIPStateElement = "<region_name>";
	public static String LocationFromIPStateElementEnd = "</region_name>";
	public static String LocationFromIPCityElement = "<city>";
	public static String LocationFromIPCityElementEnd = "</city>";
	
	/** 
	 * Static properties pointing to the URL to get location from Coordinate. Also included Tags to parse the returned information (XML is being returned).
	 */
	public static String LocationFromCoordinateGetURL = "http://where.yahooapis.com/geocode?q=";
	public static String LocationFromCoordinateGetURL2 = "&gflags=R";
	public static String LocationFromCoordinateStateElement = "<state>";
	public static String LocationFromCoordinateStateElementEnd = "</state>";
	public static String LocationFromCoordinateCityElement = "<city>";
	public static String LocationFromCoordinateCityElementEnd = "</city>";
}
