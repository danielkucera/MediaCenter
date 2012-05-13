package eu.danman.mediacenter;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.*;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class LoginActivity extends Activity implements SurfaceHolder.Callback {
	
	static MediaCenter global;
	Camera mCamera;
	boolean mPreviewRunning = false;
	private static final String TAG = "CameraTest";
	private Context mContext = this;
	String users[];
	
    //polia
    EditText uname;
    EditText pass;
    CheckBox remember;
    View LoginControlls;
    
    ProgressDialog progressIndicator;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    
	    getWindow().setFormat(PixelFormat.TRANSLUCENT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

        WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.login);
        
        progressIndicator = new ProgressDialog (mContext);
		progressIndicator.setMessage("Logging in...");
        
        uname = (EditText) findViewById(R.id.unameBox);
        pass = (EditText) findViewById(R.id.passwordBox);
        remember = (CheckBox) findViewById(R.id.rememberBox);
        LoginControlls = (View) findViewById(R.id.loginControlls);
        final Button btnAddUser = (Button) findViewById(R.id.addUser);
        

        SurfaceView mSurfaceView = (SurfaceView)findViewById(R.id.surface_camera);

        SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.addCallback(this);

        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  
        global = ((MediaCenter)getApplicationContext());
        
        //tuto pridavaj butony pre userov
        getUsers();
        final LinearLayout layout = (LinearLayout)findViewById(R.id.userBtns); 
        
        for (int i=1; i < users.length; i++){
        	Button btn = new Button(this); 
            layout.addView(btn); 
            
            //LayoutParams params = (LayoutParams) btnAddUser.getLayoutParams();
            //params.gravity = Gravity.BOTTOM;
			//btn.setLayoutParams(params);
			btn.setGravity(btnAddUser.getGravity());
            //btn.setMargins()
			btn.setGravity(Gravity.BOTTOM | Gravity.CENTER);

        	btn.setText(users[i]); // TODO: realne meno? 
            btn.setId(i);

            btn.setBackgroundResource(R.drawable.user_icon);
            btn.setTextColor(Color.WHITE);

            btn.setWidth(120);
            btn.setHeight(148);
            btn.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			String name = users[v.getId()];
        			uname.setText(name);
        			
        			if (getPreference(name+"-remember").equals("true")){
        				pass.setText(getPreference(name+"-pass"));
        				LoginControlls.setVisibility(0);
        				remember.setChecked(true);
            			progressIndicator.show();
        				passwordLogin();
        			} else {
        				LoginControlls.setVisibility(0);
        			}
        	        
        		}
            });
            
            btn.setOnLongClickListener(new OnLongClickListener() { 
                @Override
                public boolean onLongClick(View v) {
                    // TODO Auto-generated method stub
                	delUser(users[v.getId()]);
                	layout.removeView(v);
                    return true;
                }
            });


            
        }
        
        //vypln polia
        uname.setText(getPreference("username"));
        pass.setText(getPreference("password"));
        if (getPreference("remember_login").equals("true") ) remember.setChecked(true);
        
        //Login button
        final Button btnLogin = (Button) findViewById(R.id.loginBtn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			// Perform action on click
        			progressIndicator.show();
        			passwordLogin();
        		}
        });
        
        //Add User button
        btnAddUser.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View v) {
        			// Perform action on click
        	        LoginControlls.setVisibility(0);
        		
        }
        });

        //Photo button
        mSurfaceView.setOnClickListener(new SurfaceView.OnClickListener() {
        		public void onClick(View v) {

        			progressIndicator.show();
    				Log.e("Debug","photo button");
        			mCamera.takePicture(null, mPictureCallback, mPictureCallback);
        		
        		}
        });
                     
	}
	
	Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] imageData, Camera c) {

			if (imageData != null) {
				
				Log.e("Debug","starting intent");

				Intent mIntent = new Intent();

				photoLogin(mContext, imageData, 50,	"ImageName");
				mCamera.startPreview();

				setResult(0, mIntent);

			}
		}
	};
	
	
	public void surfaceCreated(SurfaceHolder holder) {
		Log.e(TAG, "surfaceCreated");
		mCamera = openFrontFacingCamera();

	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		Log.e(TAG, "surfaceChanged");
		
		if (mCamera == null) 
			return;

		// XXX stopPreview() will crash if preview is not running
		if (mPreviewRunning) {
			mCamera.stopPreview();
		}

		Camera.Parameters p = mCamera.getParameters();

		//p.setPreviewSize(w, h);
//		Size tato = p.getPreviewSize();
		
//		holder.setFixedSize(tato.width, tato.height);
		//mCamera.setParameters(p);
		try {
			mCamera.setPreviewDisplay(holder);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mCamera.startPreview();
		mPreviewRunning = true;
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		
		if (mCamera == null)
			return;
		
		Log.e(TAG, "surfaceDestroyed");
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
	}
	
	private Camera openFrontFacingCamera() 
	{
	    int cameraCount = 0;
	    Camera cam = null;
	    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
	    cameraCount = Camera.getNumberOfCameras();
	    for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
	        Camera.getCameraInfo( camIdx, cameraInfo );
	        if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
	            try {
	                cam = Camera.open( camIdx );
	            } catch (RuntimeException e) {
	                Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
	            }
	        }
	    }

	    return cam;
	}

	public boolean photoLogin(Context mContext, byte[] imageData, int quality, String expName) {

		try {

			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inSampleSize = 1; //resize photo
			
			Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0, imageData.length,options);

			FileOutputStream fileOutputStream = mContext.openFileOutput("login-photo.jpg", Context.MODE_PRIVATE);
			
			BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			myImage.compress(CompressFormat.JPEG, quality, bos);

			bos.flush();
			bos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	   	String result = global.fileUpload("do=loginPhoto", mContext.getFilesDir().toString() + "/login-photo.jpg", "login-photo.jpg");

    	finishLogin(result);
    	
		return true;
	}
	
	public void passwordLogin(){
		
		String usname = uname.getText().toString();

    	String result = global.get("do=loginPass&username="+usname+"&pass="+pass.getText());

        if (remember.isChecked()){
        	setPreference(usname+"-pass", pass.getText().toString());
        	setPreference(usname+"-remember", "true");
        } else {
        	setPreference(usname+"-pass", "");
        	setPreference(usname+"-remember", "false");
        }
	
    	finishLogin(result);
    	
    	if (result.contains("SUCCESS") && !knownUser(usname)) addUser(usname);
    	
    	
	}
	
	private void finishLogin(String returned){
		
		Log.d("finishLogin", returned);
		
		if (returned.equals("SUCCESS")) {
			
			if (!global.loadProfile()) return;
			
			String menu = global.profileVar("menu");
			
			Log.d("menu from xml",menu);
			
			if (menu.equals("menu1")){
	    		startActivity(new Intent (getApplicationContext(), MenuActivity1.class));	
			}
			if (menu.equals("menu2")){
	    		startActivity(new Intent (getApplicationContext(), MenuActivity2.class));	
			}
			
			progressIndicator.dismiss();			

        	finish();
        	return;
    	}

		progressIndicator.hide();
       		
    	Toast.makeText(getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
		
	}
	
	private boolean knownUser(String usname){
		getUsers();
		
		if (users.length < 2) return false;
		
		for (int i=1; i < users.length; i++){
			if (users[i].equals(usname)) return true;
		}
		
		return false;
	}
	
	private void delUser(String usname){
		getUsers();
		setPreference("users", "");
		for (int i=1; i < users.length; i++){
			if (!users[i].equals(usname)) addUser(usname);
		}
	}
	
	private void addUser(String usname){
		setPreference("users", getPreference("users") + ":" + usname);
	}
	
	private void getUsers(){
		
		String usrs = getPreference("users");
		users = usrs.split(":");
		
	}
	
	private void setPreference(String key, String value){
		    SharedPreferences sharedPreferences = getSharedPreferences("Settings",MODE_PRIVATE);
		    SharedPreferences.Editor editor = sharedPreferences.edit();
		    editor.putString(key, value);
		    editor.commit();
		   }
		  
	private String getPreference(String key){
		    SharedPreferences sharedPreferences = getSharedPreferences("Settings",MODE_PRIVATE);
		    String preference = sharedPreferences.getString(key, "");
		    return preference;
		   }
	


}
