package eu.danman.mediacenter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.app.Activity;
import android.app.Application;

public class MediaCenter extends Application {
	
	public class programmeType {
		int start;
		int end;
		String title;
		String desc;
		int lasting;
	};
	
	CookieStore cookieStore;
	
	public Document userProfile;
	
	

	public programmeType[] epgFromXMLTV(String uri){
		
		XMLParser parser = new XMLParser();
		
		String xmlch = get(uri);
		
//		Log.d("xmltv",xmlch);
		
        InputSource isch = new InputSource();

		isch.setCharacterStream(new StringReader(xmlch));
        
		Document xmlEPG = parser.getDomElement(isch);
		
	    NodeList programmes = xmlEPG.getElementsByTagName("programme");
	    
	    programmeType[] output =  new programmeType[programmes.getLength()] ; 
	    
	    //  pre tento kanal nahadz programy
	    for (int i=0; i< programmes.getLength(); i++){
	    	
	    	programmeType current = new programmeType();
	    	
	    	Element element = (Element) programmes.item(i);
	    	
	    	current.title = "";
	    	current.desc = "";
//	    	String channel_id = programmes.item(i).getAttributes().getNamedItem("channel").getTextContent();
	    	current.start = date2sec(element.getAttributes().getNamedItem("start").getTextContent());
	    	current.end = date2sec(element.getAttributes().getNamedItem("stop").getTextContent());
	    	
	    	if (current.end < current.start)
	    		current.end += (24*60*60);
	    	
	    	current.lasting = current.end - current.start; //hhmmss
	    	
	    	current.title = element.getElementsByTagName("title").item(0).getTextContent();
	    	
	    	current.desc = element.getElementsByTagName("desc").item(0).getTextContent();
	    	
	    	output[i] = current;
	    	
	    }
	    
	    return output;
	    	
	}
	    
	    int date2sec(String totok){

			int y = Integer.parseInt(totok.substring(0,4));
			int mo = Integer.parseInt(totok.substring(4,6));
			int d = Integer.parseInt(totok.substring(6,8));
			int h = Integer.parseInt(totok.substring(8,10));
			int m = Integer.parseInt(totok.substring(10,12));
			int s = Integer.parseInt(totok.substring(12,14));
			

			return s + m*60 + h*3600;
		}
	
	String baseurl = "http://danman.eu/mediaserver/?";
	
	public boolean getLogin(){
		
		cookieStore = new BasicCookieStore();
		Intent login = new Intent(this, LoginActivity.class);
		login.setFlags(0x10000000);
        startActivity(login);
        return true;
	}
	
	
	public boolean loadProfile(){
		
		XMLParser parser = new XMLParser();
		
		String get = get("do=getProfile");
		
		if (get.contains("xml")) {
			
			InputSource is = new InputSource();
	        is.setCharacterStream(new StringReader(get));
			
			userProfile = parser.getDomElement(is);
			
			return true;
			
		} 
			
		return false;
		
	}
	
	public String profileVar(String var){
		try {
			NodeList cont = userProfile.getElementsByTagName(var);
			if (cont.getLength() != 0){
				return cont.item(0).getTextContent();
			} else {
			return "";
		}
		} catch (NullPointerException e){
			return "";
		}
	}
	
	public boolean str2bool(String bool){
		if (bool.contains("true")){
			return true;
		}
		return false;
		
	}
	
	public String get(String request) {
		String body = "";
		
		String url = baseurl + request;
		
		Log.d("get url",url);
		

		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			// Set the store 
			httpClient.setCookieStore(cookieStore);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
	        
	        cookieStore = httpClient.getCookieStore();
			
			body = EntityUtils.toString(httpEntity, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return body
		//Log.d("body",body);
		
		if (body.contains("UNAUTHORIZED")){

			Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);

		}
		
		return body;
	}
	
	public boolean fileDownload(String url, String filename){

		String to = this.getFilesDir().toString() + "/" + filename;
		
		Log.d("downloading",url +" => "+ to);
		
		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			// Set the store 
			httpClient.setCookieStore(cookieStore);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
	        
	        cookieStore = httpClient.getCookieStore();

	        if (httpEntity != null) {
	            File file = new File(to);
	            
                FileOutputStream fos = new FileOutputStream(file);
                
	            httpEntity.writeTo(fos);
                           
                fos.close();

	        }
			

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

        
        return true; 
	}
	
	public Bitmap getLogoBitmap(String url){
		
	    String icon_name = url.substring(url.lastIndexOf('/')+1);   
	    
	    try {
			openFileInput(icon_name);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
		    Log.d("icon download",url+":"+icon_name);
		    fileDownload(url, icon_name);
		}
	    
        Bitmap logoBitmap;
        
		try {
		  InputStream instream = openFileInput(icon_name);
		  
      	  logoBitmap = BitmapFactory.decodeStream(instream);

       	} catch (IOException e) {
      	  e.printStackTrace();
      	  //logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);  //tuto dat neajku ikonku co bude v apk-cku
      	  Config conf = Bitmap.Config.ARGB_8888;
      	  logoBitmap = Bitmap.createBitmap(1, 1, conf);
      	}
		
		return logoBitmap;
	}
	
	public String fileUpload(String request, String filepath, String filename) { 
		DefaultHttpClient httpClient = new DefaultHttpClient(); 
        String body = "";
        
        try { 
                httpClient.getParams().setParameter("http.socket.timeout", new Integer(90000)); // 90 second 
                HttpPost post = new HttpPost(new URI(baseurl + request)); 
                
                File file = new File(filepath); 
                FileEntity entity; 

                entity = new FileEntity(file,"binary/octet-stream"); 
                entity.setChunked(true); 

                post.setEntity(entity); 
                post.addHeader("FILENAME_STR", filename); 
                httpClient.setCookieStore(cookieStore);
                
                HttpResponse response = httpClient.execute(post); 
                
    			HttpEntity httpEntity = response.getEntity();
    	        
    	        cookieStore = httpClient.getCookieStore();
    			
    			body = EntityUtils.toString(httpEntity, "UTF-8");
                
        } catch (Exception ex) { 
                Log.e("UploadFile","---------Error-----"+ex.getMessage()); 
                ex.printStackTrace(); 
        } finally { 
                  httpClient.getConnectionManager().shutdown(); 
        } 
        
        return body;
	} 
	
	
	
}
