package eu.danman.mediacenter;

import java.io.StringReader;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity  {

	MediaCenter global;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    addPreferencesFromResource(R.xml.settings); 
	    
	    global = (MediaCenter) getApplicationContext();
	    
	    global.loadProfile();
	    
    	final OnPreferenceChangeListener save = new Preference.OnPreferenceChangeListener() {            
        	public boolean onPreferenceChange(Preference preference, Object newValue) {
        	    
        	    String res = global.get("do=setProfile&var=" + preference.getKey() + "&val=" + URLEncoder.encode(newValue.toString()));
        	    
        	    if (res.contains("SUCCESS")){
        	    	setVal(preference, newValue.toString());
        	    }
        	    
        	    Toast.makeText(getApplicationContext(), preference.getKey() + ": " + newValue.toString() + " :" + res, Toast.LENGTH_SHORT).show();
        	    
        	    return false;
        	}
        };
	    
        Preference unamePref = (Preference) findPreference("profile_title");
        unamePref.setTitle("Profile settings for user: "+global.profileVar("uname"));
        
		XMLParser parser = new XMLParser();
		String xmlch = global.get("do=getChannels");
        InputSource isch = new InputSource();
		isch.setCharacterStream(new StringReader(xmlch));
		Document xmlEPG = parser.getDomElement(isch);
		
    	NodeList idN = xmlEPG.getElementsByTagName("id");
    	NodeList nameN = xmlEPG.getElementsByTagName("name");
	    
	    String[] chanNames = new String[idN.getLength()];
	    String[] chanIds = new String[idN.getLength()];
	    
	    for (int i=0; i < idN.getLength(); i++){

	    	chanIds[i] = idN.item(i).getTextContent();
	    	chanNames[i] = nameN.item(i).getTextContent();

	    }
        
   
        ListPreferenceMultiSelect chans = (ListPreferenceMultiSelect) findPreference("mychans");
        chans.setEntries(chanNames);
        chans.setEntryValues(chanIds);
        chans.setOnPreferenceChangeListener(save);
        
 
        String[] prefs = {"fullname", "invert_gravity","disable_gravity"};
        
        for (int i = 0; i < prefs.length ; i++)
        {
	    	final Preference thisPref = getPreferenceManager().findPreference(prefs[i]);
	    	
	    	thisPref.setPersistent(false);
	    	
	    	setVal(thisPref, global.profileVar(prefs[i]));
	
	    	thisPref.setOnPreferenceChangeListener(save);
        }
        
        
        
	}
	
    //android:entries="@array/channel_names"
    //android:entryValues="@array/channel_ids"/>
	
	private void setVal(Preference mPref, String val){
		
    	if (mPref.getClass().equals(new CheckBoxPreference(global).getClass())){
        	((CheckBoxPreference) mPref).setChecked(global.str2bool(val));
    	}
    	
    	if (mPref.getClass().equals(new EditTextPreference(global).getClass())){
        	((EditTextPreference) mPref).setText("" + val); 
    	}
		
	}
	

	
	@Override
	public void onPause(){
		super.onPause();
 		
		//reload profile
	    global.loadProfile();		
		
	}

}
