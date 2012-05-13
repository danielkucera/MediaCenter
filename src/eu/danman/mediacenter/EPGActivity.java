package eu.danman.mediacenter;

import android.app.Activity;
import android.app.Dialog;
import java.io.StringReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import eu.danman.mediacenter.MediaCenter.programmeType;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EPGActivity extends Activity {

	Document xmlEPG;
	private int scroll_px;
	Dialog mDialog;
	MediaCenter global;
	Handler handler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		setContentView(R.layout.epg);

		global = ((MediaCenter) getApplicationContext());



		// NodeList channels =
		// server.userProfile.getElementsByTagName("channel");

		handler = new Handler();

		Runnable render = new Runnable() {
			public void run() {
				Time now = new Time();
				now.setToNow();
				loadEPG(now);
			}

		};

		Runnable finish = new Runnable() {
			public void run() {
				//mDialog.cancel();
				HorizontalScrollView scroll = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
				// scroll.scrollTo(scroll_px, 0);
				scroll.smoothScrollTo(scroll_px, 0);

			}
		};

		handler.postDelayed(render, 100);

		handler.postDelayed(finish, 1000);

	}

	
	
	private void loadEPG(Time date){
		
		setContentView(R.layout.epg);
		
		final LinearLayout chanNames = (LinearLayout)findViewById(R.id.chanNames); 
		
		final LinearLayout stations = (LinearLayout)findViewById(R.id.programs); 
		
		final TextView title = (TextView)findViewById(R.id.titleEPG); 
		
		title.setText("EPG for " + date.format("%e. %b"));
		
		final Button prev = (Button)findViewById(R.id.prevDate); 
    	final Time prevDate = new Time();
    	prevDate.set(date.toMillis(false) - 1000*60*60*24);
    	prev.setText("< "+prevDate.format("%e. %b"));
		prev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	Runnable render = new Runnable() {
        			public void run() {
        				setContentView(R.layout.epg);
        			}

        		};

        		Runnable finish = new Runnable() {
        			public void run() {
        				loadEPG(prevDate);
        			}
        		};

        		handler.post(render);

        		handler.postDelayed(finish, 200);
        		
            }
        });
		
		
		final Button next = (Button)findViewById(R.id.nextDate); 
    	final Time nextDate = new Time();
    	nextDate.set(date.toMillis(false) + 1000*60*60*24);
    	next.setText(nextDate.format("%e. %b")+" >");
    	
    	next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	Runnable render = new Runnable() {
        			public void run() {
        				setContentView(R.layout.epg);
        			}

        		};

        		Runnable finish = new Runnable() {
        			public void run() {
        				loadEPG(nextDate);
        			}
        		};

        		handler.post(render);

        		handler.postDelayed(finish, 200);
        		
            }
        });

		
	    // ziskaj zoznam kanalov a ich id-cka
    	String mychans = global.profileVar("mychans");
    	
    	String[] channels = mychans.split(",");
  /*  	
    	String xml = global.get("do=playlistTV");
        
        if (!(xml.contains("track"))) {
        	finish();
        	return;
        }
   	
		XMLParser parserch = new XMLParser();
		
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		
		Document doc = parserch.getDomElement(is); // getting DOM element
		 
		NodeList channels = doc.getElementsByTagName("track");
	
		*/
		
		int scale = 12; //mierka akou stlacam sekundy na pixely
		
		//zobraz timeline
		RelativeLayout timeline_layout = new RelativeLayout(global);
		stations.addView(timeline_layout);
		
		//RelativeLayout timetitle_layout = new RelativeLayout(global);
		TextView timetitle = new TextView(global); 
		chanNames.addView(timetitle);
		
		for (int h=0; h < 25; h++){
			
			TextView hour = new TextView(global); 
			
			//hour.setHeight(80);
			hour.setWidth(3600/scale);
			hour.setText( h + ":00");
			hour.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
			//hour.setBackgroundResource(R.drawable.rectangle);
		
			timeline_layout.addView(hour); 
			
		    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) hour.getLayoutParams();
		    
		    mlp.leftMargin = (h*3600-1800)/scale;
			
		}
		
		//pre vsetky ziskane kanaly urob
		for(int j=0; j< channels.length; j++)
		{
			
		//Element track = (Element) channels.item(j);
		
		// ziskaj channel_id z elementu
		//String channel_id = parserch.getValue(track, "channel");
		
		String channel_id = channels[j];
			
		XMLParser parser = new XMLParser();
		
		// ziskaj programy a channel desc pre dany den channel_id
		
		Log.d("get","do=getEPG&date="+date.format("%Y%m%d")+"&channel="+channel_id);
		
		String xmlch = global.get("do=getEPG&date="+date.format("%Y%m%d")+"&channel="+channel_id);
		
		if (!xmlch.contains("tv")){
			break;
		}
		
		InputSource isch = new InputSource();
		
		isch.setCharacterStream(new StringReader(xmlch));
		
		xmlEPG = parser.getDomElement(isch);
		
		// dostan odtial channel info ako napr ikona a pripadne channel name
		NodeList channel_info = xmlEPG.getElementsByTagName("channel").item(0).getChildNodes();
		
		String icon_src = "nejaka ikona ktora fakt existuje"; //netreba ale potesi
		
		for (int p=0; p < channel_info.getLength(); p++){
			if (channel_info.item(p).getNodeName().equals("icon")) {
				NamedNodeMap attrs = channel_info.item(p).getAttributes();
				icon_src =attrs.getNamedItem("src").getNodeValue();
			}
		}
		
		//pridaj ikonu (button) pre dany kanal
		ImageView channel_button = new ImageView(global); 
		chanNames.addView(channel_button); 
			
		channel_button.setImageBitmap(global.getLogoBitmap(icon_src));	    
		channel_button.setAdjustViewBounds(true);
		channel_button.setMaxHeight(80);
		channel_button.setMinimumHeight(80);
		
		final int chid = j;
		channel_button.setOnClickListener(new View.OnClickListener() {
		        public void onClick(View v) {
		            // Perform action on click
		        	Bundle bundle = new Bundle();
		        	bundle.putString("playlistURL", "do=playlistTV");
		        	bundle.putInt("playlistItem", chid);
		        	
		            Intent myIntent = new Intent(getBaseContext(), PlayerActivity.class);
		            myIntent.putExtras(bundle);
		            startActivity(myIntent);
		        }
		    });
		    
		RelativeLayout channel_layout = new RelativeLayout(global);
		
		stations.addView(channel_layout);
		
		// ziskaj z toho epg ziskaneho vyssie nody s programami
		
		programmeType[] epg = global.epgFromXMLTV("do=getEPG&date="+date.format("%Y%m%d")+"&channel="+channel_id);
		
		for (int i=0; i< epg.length; i++){
		
			//Log.d("item",start + ":" + end + ":" + lasting + title );
			
			TextView program = new TextView(global); 
			
		    program.setHeight(80);
		    program.setWidth(epg[i].lasting/scale);
		    program.setText(epg[i].title);
		    program.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		    program.setBackgroundResource(R.drawable.rectangle);
		
		    channel_layout.addView(program);           
		
		    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) program.getLayoutParams();
		    
		    mlp.leftMargin = epg[i].start/scale;	
		    
		}
		
		TextView pointer = new TextView(global); 
		
		pointer.setHeight(80);
		pointer.setWidth(3);
		pointer.bringToFront();
		pointer.setBackgroundResource(R.drawable.ruler);
		
		channel_layout.addView(pointer);           
		
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) pointer.getLayoutParams();
		
		mlp.leftMargin = ((date.hour*60 + date.minute)*60 + date.second)/scale;	
		
		}
		
		
		scroll_px = ((date.hour*60 + date.minute - 30)*60 + date.second)/scale;

    }
}
