package logic;

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
		
		
		return true;
	}
	
}
