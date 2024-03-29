package eu.danman.mediacenter;

import java.io.StringReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

public class PlayerActivity extends Activity implements OnTouchListener {
	
	private static final int GONE = 0;

	float downXValue;
	float downYValue;
	
	@Override
    public boolean onTouch(View arg0, MotionEvent arg1) {

		topBar.setVisibility(View.VISIBLE);
        mHandler.removeCallbacks(hideTopbar);
		
		if (videoView.isPlaying()){
	        mHandler.postDelayed(hideTopbar, 3000);
		}

        // Get the action that was done on this touch event
        switch (arg1.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                // store the X value when the user's finger was pressed down
            	downXValue = arg1.getX();
            	downYValue = arg1.getY();
          
            	//return true;

            }
            break;
            
            case MotionEvent.ACTION_UP:
            {
                // Get the X value when the user released his/her finger
                float lenghtX = downXValue - arg1.getX();   
                float lenghtY = downYValue - arg1.getY();    

                // going backwards: pushing stuff to the right
                if (lenghtX < -15)
                {

                    videoView.stopPlayback();
                	
                    // Get a reference to the ViewFlipper
                     ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);
                     // Set the animation
                      vf.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left));
                      // Flip!
                      vf.showPrevious();
                      
                      pid--;
                      
                      playCurrent();
                }

                // going forwards: pushing stuff to the left
                if (lenghtX > 15)
                {

                    videoView.stopPlayback();
                	

                     // Set the animation
                     topBar.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_right));
                      // Flip!
                     topBar.showNext();                     
                     pid++;
                     
                     playCurrent();

                }
            }
            break;

        }

        // if you return false, these actions will not be recorded
        return true;
    }
	
	int pid;
	VideoView videoView;
    private RelativeLayout layoutPerc;
    private NodeList playlist;
    private XMLParser parser;
	private Bitmap logoBitmap;
	SharedPreferences sharedPreferences;
	
	int buffered;
	
	private Handler mHandler;
	
	private Handler startfirst;
	
    // Get a reference to the ViewFlipper
    ViewFlipper topBar;
    
    ImageView logoView;
    
    PowerManager pm;
    PowerManager.WakeLock wl;
    
    MediaCenter global;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
	    sharedPreferences = getSharedPreferences("Settings",MODE_PRIVATE);

        setContentView(R.layout.player);
        
        videoView = (VideoView) findViewById(R.id.videoView1);
        videoView.setOnPreparedListener(new OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {

        	   layoutPerc.setVisibility(View.INVISIBLE);   		
        		
               mHandler.postDelayed(hideTopbar, 3000);  
               
               videoView.start();
            }
        });
        
        RelativeLayout layMain = (RelativeLayout) findViewById(R.id.layMain);
        
        logoView = (ImageView)findViewById(R.id.logoTV);
        
        logoView.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View v) {
    			startActivity(new Intent (getApplicationContext(), EPGActivity.class));	
    	        
    		}
        });
        
        topBar = (ViewFlipper) findViewById(R.id.details);
        
        mHandler = new Handler();
        
        layMain.setOnTouchListener((OnTouchListener) this); 
        
    	layoutPerc = (RelativeLayout)findViewById(R.id.layoutPerc);
    	
    	MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    	
    	// prevzatie URL playlistu
    	Bundle bundle = this.getIntent().getExtras();
    	String playlistURL = bundle.getString("playlistURL");
    	int chid = bundle.getInt("playlistItem");

    	
    	// nacitanie XML playlistu
    	parser = new XMLParser();
    	    	
    	global = ((MediaCenter)getApplicationContext());
    	
        String xml = global.get(playlistURL);    
    	
        if (!(xml.contains("<track>"))) {
        	finish();
        	return;
        }
        
		InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        
    	Document doc = parser.getDomElement(is); // getting DOM element
    	
    	playlist = doc.getElementsByTagName("track");
    	
       	pid = chid;

       	/*
		startfirst = new Handler();

		Runnable first = new Runnable() {
			public void run() {
		        playCurrent();  
			}

		};
		
		startfirst.postDelayed(first, 100);
		*/
       	
       	playCurrent();
       	
	}
	

	
	@Override
	public void onPause() {
		super.onPause();
		
        videoView.stopPlayback();
    	wl.release();
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Player");
        wl.acquire();
		
	}
	
    public void playCurrent(){
    	
    	if (pid < 0) {
    		pid = playlist.getLength()-1;
    	}
    	
    	if (pid >= playlist.getLength()) {
    		pid = 0;
    	}
    	
    	Element track = (Element) playlist.item(pid);
    	
    	//nazov daj do top bar
    	TextView topBarText = (TextView) findViewById(R.id.textBig);
    	topBarText.setText(" "+(pid+1) + " " + parser.getValue(track, "title"));

    	//popis daj do top bar
    	TextView textSmall = (TextView) findViewById(R.id.textSmall);
    	textSmall.setText(parser.getValue(track, "annotation"));    	

    	//nacitanie loga televizie
    	loadLogo(parser.getValue(track, "image"));
        	
        //zobraz buffer indicator
		layoutPerc.setVisibility(View.VISIBLE);

        //spusti nove video
        videoView.stopPlayback();
        
    	String url = parser.getValue(track, "location");
        videoView.setVideoURI(Uri.parse(url));
        
       }
    
    public void loadLogo(final String url){
    	Thread t = new Thread(){
    		public void run(){
    			// Long time comsuming operation
    		    logoBitmap = global.getLogoBitmap(url);
    			   
	   		    Message myMessage=new Message();
	   		    Bundle resBundle = new Bundle();
	   		    resBundle.putString("status", "SUCCESS");
	   		    myMessage.obj=resBundle;
	   		    handler.sendMessage(myMessage);
    		   }
    		};
    	t.start();

    }
    
	private Runnable hideTopbar = new Runnable() {
		   public void run() {
		       topBar.setVisibility(View.INVISIBLE);
		   }
	};
	    
    private Handler handler = new Handler() {
    	@Override
    	  public void handleMessage(Message b) {
    	    // Code to process the response and update UI.
      	  	logoView.setImageBitmap(logoBitmap);
    	}
    	
    	
    };
    
}
