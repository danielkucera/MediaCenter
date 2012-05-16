package eu.danman.mediacenter;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

public class IntroActivity extends Activity {
	
	Timer timer;
	TimerTask task;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
        setContentView(R.layout.intro);
        
        timer = new Timer();
        
        final MediaCenter global = ((MediaCenter)getApplicationContext());

        
        task = new TimerTask() {
			
			@Override
			public void run() {
				
		        global.loadProfile();
				
				String menu = global.profileVar("menu");
				
				Log.d("menu from xml",menu);
				
				if (menu.equals("menu1")){
		    		startActivity(new Intent (getApplicationContext(), MenuActivity1.class));	
				}

		        timer.cancel();
		        finish();
			}
			
		};
        
        timer.schedule(task, 1000, 1000);
  

	}
	

}
