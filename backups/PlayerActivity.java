package eu.danman.mediacenter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import eu.danman.mediacenter.MediaCenter.programmeType;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.text.format.Time;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class PlayerActivity extends Activity implements OnTouchListener, OnBufferingUpdateListener, OnPreparedListener {
	
	private static final int GONE = 0;

	float downXValue;
	float downYValue;
	
	@Override
    public boolean onTouch(View arg0, MotionEvent arg1) {

		topBar.setVisibility(View.VISIBLE);
        mHandler.removeCallbacks(mUpdateTimeTask);
		
		if (buffered == 100){
	        mHandler.postDelayed(mUpdateTimeTask, 3000);
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

                	mMediaPlayer.reset();
                	
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
                	mMediaPlayer.reset();
                	

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
    private MediaPlayer mMediaPlayer;
    private SurfaceHolder holder;
    private SurfaceView mPreview;
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
        
        mPreview = (SurfaceView) findViewById(R.id.surfacePlayer);
        holder = mPreview.getHolder();
        
//        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
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
        
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setDisplay(holder);
        
        
    	layoutPerc = (RelativeLayout)findViewById(R.id.layoutPerc);
    	
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
    	
    	mMediaPlayer.reset();
    	
       	pid = chid;

		startfirst = new Handler();

		Runnable first = new Runnable() {
			public void run() {
		        playCurrent();  
			}

		};
		
		startfirst.postDelayed(first, 100);
    	
  

	}
	

	
	@Override
	public void onPause() {
		
		super.onPause();
		
    	mMediaPlayer.reset();
    	
    	wl.release();
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Player");
        wl.acquire();
		
	}

	
	private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
		       topBar.setVisibility(View.INVISIBLE);
		   }
		};
	
    public void playCurrent(){
    	
    	if (pid < 0) {
    		pid = playlist.getLength()-1;
    	}
    	
    	if (pid >= playlist.getLength()) {
    		pid = 0;
    	}
    	
    	Element track = (Element) playlist.item(pid);
    	
    	//nazov daj to top bar
    	TextView topBarText = (TextView) findViewById(R.id.textBig);
    	topBarText.setText(" "+(pid+1) + " " + parser.getValue(track, "title"));

    	/*
    	//show epg
		Time now = new Time();
		now.setToNow();
    	
    	programmeType[] epg = server.epgFromXMLTV("do=getEPG&date="+now.format("%Y%m%d")+"&channel="+parser.getValue(track, "annotation"));
    	
    	int nowsec = server.date2sec(now.format("%Y%m%d%H%M%S"));
    	

		int nowIndex = -1;
    	
    	for (int p=0; p < (epg.length); ++p){
    		if ((epg[p].start < nowsec) && (epg[p].end > nowsec)) {
    			nowIndex = p;
    			break;
    		}
    	}
		    	

    	String nowString = "";
    	
    	if (nowIndex > -1) {
    		nowString = sec2time(epg[nowIndex].start) + " - " + sec2time(epg[nowIndex].end) +" " +epg[nowIndex].title + "";
    	}
    	
    	int nextIndex=-1;
    	
    	int min = 3600*49; //prehnane sekundy
    	
    	for (int q=0; q < (epg.length); q++){
    		if (nowIndex == -1) break;
    		
    		if ((epg[q].start > epg[nowIndex].end-1) && (epg[q].end < min)) {
    			min = epg[q].end;
    			nextIndex = q;
    		}
    	}

       	String nextString = "";
       	
       	if (nextIndex > -1){
       		nextString = sec2time(epg[nextIndex].start) + " - " + sec2time(epg[nextIndex].end) +" " +epg[nextIndex].title + "";
       	}
       	*/
       	
    	TextView textSmall = (TextView) findViewById(R.id.textSmall);
    	
    	textSmall.setText(parser.getValue(track, "annotation"));    	

    	
    	//nacitanie loga televizie
    	loadLogo(parser.getValue(track, "image"));
        	
        //zobraz buffer indicator
		layoutPerc.setVisibility(View.VISIBLE);
		
        TextView textPerc = (TextView) findViewById(R.id.textPerc);
        
        textPerc.setText("0%");
        
        //toto len pre moje

        //spusti nove video
    	mMediaPlayer.reset();
    	
    	String url = parser.getValue(track, "location");
 
        try {
			mMediaPlayer.setDataSource(url);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        mMediaPlayer.setOnPreparedListener(this);
		mMediaPlayer.prepareAsync();
        
       }
    
    public String sec2time(int secs){
    	int h = secs/3600;
    	int m = (secs - h*3600)/60;
    	
    	if (h>=24) h -= 24;
    	return String.format("%02d", h) + ":" + String.format("%02d", m);
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
    
    private Handler handler = new Handler() {
    	@Override
    	  public void handleMessage(Message b) {
    	    // Code to process the response and update UI.
     	
      	  	logoView.setImageBitmap(logoBitmap);
    	  }
    	};
    
    
    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
    	
    	Log.v("Test",percent+"%");
    	buffered = percent;
    	
    	if (percent == 100){
    		
    		layoutPerc.setVisibility(View.INVISIBLE);   		
    		
            mHandler.postDelayed(mUpdateTimeTask, 3000);   
    		
    	}else{
    		layoutPerc.setVisibility(View.VISIBLE);
    		
    		topBar.setVisibility(View.VISIBLE);
            mHandler.removeCallbacks(mUpdateTimeTask);
    		
            TextView textPerc = (TextView) findViewById(R.id.textPerc);
            
            textPerc.setText(percent+"%");
    	}

    }
    
    public void onPrepared(MediaPlayer mediaplayer) {
//      mIsVideoReadyToBePlayed = true;
//      if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
//      holder.setFixedSize(mVideoWidth, mVideoHeight);
		layoutPerc.setVisibility(View.INVISIBLE);   		
		
        mHandler.postDelayed(mUpdateTimeTask, 3000);   
    	
    	mMediaPlayer.start();
    	
		topBar.bringToFront();
//       }
    }
    
}
