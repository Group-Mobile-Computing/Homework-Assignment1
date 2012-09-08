package commons;

/**
 * Provides object representation for Location.  Currently Location is comprised of State name, and City name.
 * There is No sanity checking currently as of revision 0
 * @author Mike
 *
 */
public class Location {

	private String state, city;
	
	/**
	 * Sets the state and city values of the object to those given as arguments to constructor respectively.
	 * @param state String of length greater than 1.
	 * @param city String of length greater than 1.
	 */
	Location(String state, String city)
	{
		setState(state);
		setCity(city);
	}
	
	/**
	 * Returns the state contained in the object, or null if the object has not been initialized correctly.
	 * @return String value of length >= 1 of state or null
	 */
	public String getState()
	{
		return this.state;
	}
	
	private void setState(String state)
	{
		this.state = state;
	}
	
	/**
	 * Return the city contained in the object, or null if the object has not been initialized correctly.
	 * @return String of length >= 1 value or null
	 */
	public String getCity()
	{
		return this.city;
	}
	
	private void setCity(String city)
	{
		this.city=city;
	}
	
}
