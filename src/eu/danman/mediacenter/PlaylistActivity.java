package eu.danman.mediacenter;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class PlaylistActivity extends Activity {
	
	ImageView img;
	TextView title;
	TextView desc;
	MediaCenter global;
	ListView list;
	
	XMLParser parser;
	NodeList playlist;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.playlist);
	    
	    img = (ImageView) findViewById(R.id.imgIcon);
	    title = (TextView) findViewById(R.id.textTitle);
	    desc = (TextView) findViewById(R.id.textDesc);
	    list = (ListView) findViewById(R.id.listItems);
	    
	    global = (MediaCenter) getApplicationContext();
	    
	   	// prevzatie URL playlistu
    	Bundle bundle = this.getIntent().getExtras();
    	final String playlistURL = bundle.getString("playlistURL");
    	String playlistItem = bundle.getString("playlistItem");
    	
    	// nacitanie XML playlistu
    	parser = new XMLParser();
    	    	
        String xml = global.get(playlistURL);    
    	
        if (!(xml.contains("<track>"))) {
        	finish();
        	return;
        }
        
		InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        
    	Document doc = parser.getDomElement(is); // getting DOM element
    	
    	playlist = doc.getElementsByTagName("track");
    	
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
    	
    	for (int i=0; i < playlist.getLength(); i++){
            HashMap<String, String> map = new HashMap<String, String>();

            map.put("rowid", "" + i);

            map.put("title", parser.getValue((Element) playlist.item(i), "title"));

            map.put("desc", parser.getValue((Element) playlist.item(i), "annotation"));

            fillMaps.add(map);
    	}
    	
        String[] from = new String[] {"title", "desc"};

        int[] to = new int[] { R.id.itemTitle, R.id.itemDesc };

        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.plitem, from, to);
        
        list.setAdapter(adapter);
        
        list.setOnItemLongClickListener(new OnItemLongClickListener() {

          public boolean onItemLongClick(AdapterView adapterView, View view, int position, long id) {
        	  
          	Bundle bundle = new Bundle();
          	bundle.putString("playlistURL", playlistURL);
          	bundle.putInt("playlistItem", position);
          	
			Intent myIntent = new Intent(getBaseContext(), PlayerActivity.class);
			myIntent.putExtras(bundle);
			startActivity(myIntent);

            return true;

          }
        });

        list.setOnItemClickListener(new OnItemClickListener() {

          public void onItemClick(AdapterView adapterView, View view, int position, long id) {

            showItem(position);

          }
        });


	}
	
	private void showItem(int num){
		
		
		title.setText(parser.getValue((Element) playlist.item(num), "title"));
		img.setImageBitmap(global.getLogoBitmap(parser.getValue((Element) playlist.item(num), "image")));
		
		String desctxt = parser.getValue((Element) playlist.item(num), "annotation");
		desctxt = desctxt.replace("[", "<");
		desctxt = desctxt.replace("]", ">");
		
		//desc.setText(Html.fromHtml(desctxt));
		desc.setText(desctxt);
		
	}

}
