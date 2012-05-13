package eu.danman.mediacenter;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

public class IntroActivity extends Activity {
	
	Timer timer;
	TimerTask task;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
        setContentView(R.layout.intro);
        
        timer = new Timer();
        
        task = new TimerTask() {
			
			@Override
			public void run() {
		        startActivity(new Intent(getBaseContext(), LoginActivity.class));
		        timer.cancel();
		        finish();
			}
			
		};
        
        timer.schedule(task, 1000, 1000);
  

	}
	

}
