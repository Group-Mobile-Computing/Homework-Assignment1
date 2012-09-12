package edu.cse535.assignment1;

//import ui.Dummy;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Load in the layout from UI
        setContentView(ui.Dummy.getContentView());
        
        //Create the Protocol object we need
        logic.Protocol protocol = new logic.Protocol();
        
        //Set Location and Joke to processing state
        protocol.deltaOnCreate();
        
        //Begin Gathering all the data required
        protocol.getAndroidIP();
        
    }
    
    /**
     * Recollects GPS information maps it to a Location.  If GPS is unavailable
     * collects IP and maps it to a Location.  Queries Joke server for joke.
     * Presents updated information to user.  While these are processing they are
     * the components are set to processing state.
     * @param v  Current screen user is looking at.  In this case the only screen
     * in the application
     */
    private void refresh(View v)
    {
    	
    }
    
    /**
     * Terminates application normally.  Cleans up data.
     * 
     * @param v Current screen user is looking at.  In this case the only screen
     * in the application
     */
    private void exit(View v)
    {
    	//Not implement see OneNote Assignment 1 @ Mike TableTop
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
