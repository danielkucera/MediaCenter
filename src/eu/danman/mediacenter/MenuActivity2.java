package eu.danman.mediacenter;

import android.app.Activity;
import 	android.util.FloatMath;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



public class MenuActivity2 extends Activity implements SensorEventListener{

	SensorManager sensorManager = null;
	
	//for accelerometer values
	TextView outputX;
	TextView outputY;
	TextView outputZ;
	 
	//for orientation values
	TextView outputX2;
	TextView outputY2;
	TextView outputZ2;

	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        
        setContentView(R.layout.menu1);
        
        initArrow();
        
	    outputX = (TextView) findViewById(R.id.textView1);
	    outputY = (TextView) findViewById(R.id.textView2);
	    outputZ = (TextView) findViewById(R.id.textView3);
	 
	    outputX2 = (TextView) findViewById(R.id.textView4);
	    outputY2 = (TextView) findViewById(R.id.textView5);
	    outputZ2 = (TextView) findViewById(R.id.textView6);
	    
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
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
        
        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
    		    SharedPreferences sharedPreferences = getSharedPreferences("Settings",MODE_PRIVATE);
    		    SharedPreferences.Editor editor = sharedPreferences.edit();

    		    editor.putString("username", "");
    		    editor.putString("password", "");
    		    editor.putString("remember_login", "false");
    		    editor.commit();

                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
        
        MediaCenter server = ((MediaCenter)getApplicationContext());
    	
 //       String xml = server.get("do=playlistTV");
        
 //       Log.d("totooooo",xml);

    }
    
	@Override
	 protected void onResume() {
	    super.onResume();
	    sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
	    sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), sensorManager.SENSOR_DELAY_GAME);
	 }
	
	@Override
	 protected void onStop() {
	    super.onStop();
	    sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
	    sensorManager.unregisterListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
	 }
	
	public void onSensorChanged(SensorEvent event) {
	    synchronized (this) {
	        switch (event.sensor.getType()){
	            case Sensor.TYPE_ACCELEROMETER:
	                outputX.setText("x:"+Float.toString(event.values[0]));
	                outputY.setText("y:"+Float.toString(event.values[1]));
	                outputZ.setText("z:"+Float.toString(event.values[2]));
	                
	                //float sipke = -event.values[1]*10;
	    	        //outputZ.setText("z:"+Float.toString(sipke));	                

	            break;
	        case Sensor.TYPE_ORIENTATION:
	                outputX2.setText("x:"+Float.toString(event.values[0]));
	                outputY2.setText("y:"+Float.toString(event.values[1]));
	                outputZ2.setText("z:"+Float.toString(event.values[2]));
	                turnArrow((float)(2*event.values[1]));
	        break;
	 
	        }
	    }
	 }
	
	 @Override
	 public void onAccuracyChanged(Sensor sensor, int accuracy) {}  
	 
	 Bitmap bitmapOrg;
	 float scaleWidth;
	 float scaleHeight;
	 int width;
	 int height;
	 ImageView rotacka;
	 float[] degs= {0, 0, 0, 0, 0};
	 
	 private void initArrow(){
	        // load the origial BitMap (500 x 500 px)
	        bitmapOrg = BitmapFactory.decodeResource(getResources(), R.drawable.arrow2);
	       
	  //      width = bitmapOrg.getWidth();
	  //      height = bitmapOrg.getHeight();
	        
	        width=100;
	        height=100;
       
     
	        rotacka = (ImageView) findViewById(R.id.arrowImage);
	        
	 }
	 

	 
	 private void turnArrow(float deg){
		 	degs[4]=degs[3];
		 	degs[3]=degs[2];
		 	degs[2]=degs[1];
		 	degs[1]=degs[0];
		 	degs[0]=deg;
		 	
		 	float averdeg = (degs[0]+degs[1]+degs[2])/5; 
		 
	        // createa matrix for the manipulation
	        Matrix matrix = new Matrix();
	        // resize the bit map
	        
	//        int newWidth = (int)(width*FloatMath.cos(deg) + height*FloatMath.sin(deg));
	//       int newHeight = (int)(width*FloatMath.sin(deg) + height*FloatMath.cos(deg));
	        
	        // calculate the scale - in this case = 0.4f
	 //       scaleWidth = ((float) newWidth) / width;
	  //      scaleHeight = ((float) newHeight) / height;
	        
	 //       matrix.postScale(scaleWidth, scaleHeight);
	        // rotate the Bitmap
	        
	        
	        matrix.postRotate(averdeg);
	 
	        // recreate the new Bitmap
	        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
	   
	        // make a Drawable from Bitmap to allow to set the BitMap
	        // to the ImageView, ImageButton or what ever
	        BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);
	       
  
	        // set the Drawable on the ImageView
	        rotacka.setImageDrawable(bmd);
	 }
	 

}
