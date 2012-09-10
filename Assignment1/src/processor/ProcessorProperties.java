package processor;

/**
 * 
 * @author Shoaib
 *
 */

public class ProcessorProperties {	

	/** 
	 * Enumeration against Message Types.
	 */
	public enum MessageType
	{ HELLO , HI , GOODBYE , REQUEST , RESPONSE , NONE ;
		public static MessageType GetMessageType(String Str)
		{
			try {return valueOf(Str);}
			catch (Exception ex){return NONE;}
		}
	};
	
	/** 
	 * Static properties pointing to the URL to get location from IP. Also include Tags to parse the returned information (XML is being returned).
	 */	
	public static String LocationFromIPCityElement = "<city>";
	public static String LocationFromIPCityElementEnd = "</city>";	
	public static String LocationFromIPStateElement = "<region_name>";
	public static String LocationFromIPStateElementEnd = "</region_name>";
	public static String LocationFromIPGetURL = "http://services.ipaddresslabs.com/iplocation/locateip?key=demo&ip=";
	
	/** 
	 * Static properties pointing to the URL to get location from Coordinate. Also include Tags to parse the returned information (XML is being returned).
	 */
	public static String LocationFromCoordinateGetURL2 = "&gflags=R";
	public static String LocationFromCoordinateCityElement = "<city>";
	public static String LocationFromCoordinateStateElement = "<state>";
	public static String LocationFromCoordinateCityElementEnd = "</city>";
	public static String LocationFromCoordinateStateElementEnd = "</state>";	
	public static String LocationFromCoordinateGetURL = "http://where.yahooapis.com/geocode?q=";	
	
	/** 
	 * Static properties Required for communicating with TCP server to retrieve JOTD.
	 */
	public static char Space = ' ';
	public static int BackslashASCII = 92;
	public static int JokeServerPort = 8000;
	public static int GreaterThanSignASCII = 62;
	public static String JokeStartConstant = "JOTD ";
	public static String JokeServerAddress = "IMPACT.ASU.EDU";
	public static String ServerNameEndConstant = " WHO ARE YOU";
	public static String ServerNameStartConstant = "AA1 HELLO THIS IS ";
	public static String ClientIdentity = "Shoaib-Ahmed-1205058656|Manikandan-Vijayakumar-1204624377|Michael-Sanchez-1000899816";
}
