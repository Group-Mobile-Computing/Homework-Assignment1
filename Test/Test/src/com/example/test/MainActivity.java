package com.example.test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
	      View infoButton = findViewById(R.id.Info_button);
	      infoButton.setOnClickListener(this);
	      View exitButton = findViewById(R.id.Exit_button);
	      exitButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	   
	   // ...
	   public void onClick(View v) {
	      switch (v.getId()) {
	      case R.id.Info_button:
	         Intent i = new Intent(this, Info.class);
	         startActivity(i);
	         break;     	      
	      case R.id.Exit_button:
	         finish();
	         break;
	      
	      
	      }
	   }
}
