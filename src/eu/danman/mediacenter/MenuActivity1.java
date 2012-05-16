package eu.danman.mediacenter;

import android.app.Activity;
import 	android.util.FloatMath;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;



public class MenuActivity1 extends Activity implements SensorEventListener{

	SensorManager sensorManager = null;
	
	//for accelerometer values
	TextView outputX;
	 
	//for orientation values
	TextView outputX2;
	
    Button btnTV;
    Button btnVOD;
    Button btnEPG;
    Button btnLogout;
    Button btnSettings;
    Button btnListTV;
    ImageButton btnRefresh;
    
    MediaCenter global;

	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.menu1);
        
	    outputX = (TextView) findViewById(R.id.textView1);	 
	    outputX2 = (TextView) findViewById(R.id.textView4);
	    
	    btnRefresh = (ImageButton) findViewById(R.id.btnRefresh);
	    btnTV = (Button) findViewById(R.id.btnTV);
	    btnVOD = (Button) findViewById(R.id.btnVOD);
	    btnLogout = (Button) findViewById(R.id.btnLogout);
	    btnEPG = (Button) findViewById(R.id.btnEPG);
	    btnSettings = (Button) findViewById(R.id.btnSettings);
	    btnListTV = (Button) findViewById(R.id.btnListTV);
	          
        initBall();
	    
        btnTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Bundle bundle = new Bundle();
            	bundle.putString("playlistURL", "do=playlistTV");
            	
                Intent myIntent = null;
                myIntent = new Intent(getBaseContext(), PlayerActivity.class);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        
        btnVOD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Bundle bundle = new Bundle();
            	bundle.putString("playlistURL", "do=playlistVOD");
            	
                Intent myIntent = null;
                myIntent = new Intent(getBaseContext(), PlaylistActivity.class);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        
        btnListTV.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	Bundle bundle = new Bundle();
            	bundle.putString("playlistURL", "do=playlistTV");
            	
                Intent myIntent = null;
                myIntent = new Intent(getBaseContext(), PlaylistActivity.class);
                myIntent.putExtras(bundle);
                startActivity(myIntent);
            }
        });
        

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	global.get("do=logout");
                //no need to start login activity because the get function starts it when it recieves unauthorized response
            }
        });
        
        btnSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(getBaseContext(), SettingsActivity.class));
            }
        });
        
        btnEPG.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                startActivity(new Intent(getBaseContext(), EPGActivity.class));
            }
        });
        

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	startX = 999999;
            	startY = 999999;
            	ball.scrollTo(0,0);
            }
        });
        
        global = ((MediaCenter)getApplicationContext());
    	
 //       String xml = server.get("do=playlistTV");
        
 //       Log.d("totooooo",xml);

    }
    
	@Override
	 protected void onResume() {
	    super.onResume();
	    
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	    //sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);

        
        gravity = (global.profileVar("invert_gravity").contains("true"))?-1:1;
        
        gravity *= 1;

	    startX = 999999;
    	startY = 999999;
    	ball.scrollTo(0,0);

        if (!global.str2bool(global.profileVar("disable_gravity"))){
        	sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), sensorManager.SENSOR_DELAY_GAME);	
        	sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);	
        }

    	
    	((TextView) findViewById(R.id.textWelcome)).setText("Welcome, " + global.profileVar("fullname"));
    	
	 }
	
	@Override
	 protected void onPause() {
	    super.onPause();
	    sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
	    sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));

	 }
	
	float startX;
	float startY;
	
	public void onSensorChanged(SensorEvent event) {
		if (ball == null) return;
		
	    synchronized (this) {
	        switch (event.sensor.getType()){
	            case Sensor.TYPE_ACCELEROMETER:
//	            	Log.d("accel","x:"+Float.toString(event.values[0])+"\ny:"+Float.toString(event.values[1])+"\nz:"+Float.toString(event.values[2]));
	            	
	                break;
	        case Sensor.TYPE_ORIENTATION:
           
	        	//Log.d("X",""+event.values[1]+" : "+event.values[2]);
	        	//Log.d("start",""+startX+" : "+startY);
	        	
	        	
	                if (startX == 999999) {
	                	startX = event.values[1];
	                	break;
	                }
	                if (startY == 999999) {
	                	startY = event.values[2];
	                	break;
	                }
	                
	                int mvX = (int)(startX - event.values[1]);
	                int mvY = (int)(startY - event.values[2]);
	                
	                
	                if (abs(ball.getScrollX() - mvX * gravity) < maxX){
	                	ball.scrollBy(-mvX * gravity, 0);
	                }
	                
	                if (abs(ball.getScrollY() + mvY * gravity) < maxY){
	                	ball.scrollBy(0, +mvY * gravity);
	                }
	                
	                checkDist(btnTV);
	                checkDist(btnEPG);
	                checkDist(btnLogout);
	                checkDist(btnSettings);
	                checkDist(btnVOD);
	                checkDist(btnListTV);

	                
//                	outputX.setText("x:"+abs(ball.getLeft() + ball.getScrollX() - btnTV.getLeft())+"\ny:"+abs(ball.getTop() + ball.getScrollY() - btnTV.getTop()));
//                	outputX.setText("x:"+ distX(btnTV) + "\ny:"+ distY(btnTV));

	                
	        break;
	 
	        }
	    }
	 }
	
	 private int abs(int val){
		 if (val < 0) return -val;
		 return val;
	 }
	 
	 private void checkDist(View obj){
         if ((distX(obj) < 35) && (distY(obj) < 35)) {
         	
         	ball.scrollTo(0,0);
         	obj.performClick();
         }
	 }
	 
	 private int distX(View obj){
		 return abs(maxX - ball.getScrollX() - obj.getLeft() - obj.getHeight()/2);
	 }
	 
	 private int distY(View obj){
		 return abs(maxY - ball.getScrollY() - obj.getTop() - obj.getWidth()/2);
	 }
	
	 @Override
	 public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    startX = 999999;
	    	startY = 999999;
	    	Log.d("accuracy","changed");
	 }  
	 
	 Bitmap bitmapOrg;
	 float scaleWidth;
	 float scaleHeight;
	 int width;
	 int height;
	 ImageView ball;
	 LayoutParams params;
	 
	 int maxX, maxY;
	 int gravity;
	 
	 float[] degs= {0, 0, 0, 0, 0};
	 
	 private void initBall(){

	        ball = (ImageView) findViewById(R.id.ball);
	        
	        Display display = getWindowManager().getDefaultDisplay();
	     
	        maxX = display.getWidth()/2;
	        maxY = display.getHeight()/2;

	        
	 }	 

}
