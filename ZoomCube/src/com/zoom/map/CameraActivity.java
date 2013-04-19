package com.zoom.map;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class CameraActivity extends Activity implements PictureCallback {
	
	Context context;
	//Camera camera;
	CameraSurfaceView cameraSurfaceView;
	Button shutterButton;
	Button changeCamButton;
	Button flashButton;
	
	boolean isFlashEnabled;
	boolean isRearEnabled;
	
	String DEBUG_TAG = "CameraActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_camera);

		//Set application context for Toasts
		context = getApplicationContext();
		
		//TODO get saved state for this var.
		isFlashEnabled = false;
		isRearEnabled = true;
		
		// set up our preview surface
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		cameraSurfaceView = new CameraSurfaceView(this);
		preview.addView(cameraSurfaceView);
		
		//Set up OnClickListeners
		View.OnClickListener shutterListener = new View.OnClickListener() {
			public void onClick(View v) {
				takePicture();
		    }
		};
		
		View.OnClickListener changeCamListener = new View.OnClickListener() {
			public void onClick(View v) {
				
				
				cameraSurfaceView.switchCamera(isRearEnabled, flashButton);
				isRearEnabled = !isRearEnabled;
		    }
		};
		
		View.OnClickListener flashListener = new View.OnClickListener() {
			public void onClick(View v) {
				/*
				String message;
				
				//set toast message
				if(isFlashEnabled){
					message = "Flash Disabled!";
				}else{
					message = "Flash Enabled!";
				}
				
				//Toggle Flash
				isFlashEnabled = !isFlashEnabled;
				
				//Toast Flash state
				Toast.makeText(context, message,
			         	Toast.LENGTH_SHORT).show();
			         	*/
				
				cameraSurfaceView.toggleFlash(isFlashEnabled);
				
				isFlashEnabled = !isFlashEnabled;
		    }
		};

		// grab out buttons so we can reference them later
		// and add listeners
		shutterButton = (Button) findViewById(R.id.shutter_button);
		shutterButton.setOnClickListener(shutterListener);
		changeCamButton = (Button) findViewById(R.id.change_cam_button);
		changeCamButton.setOnClickListener(changeCamListener);
		flashButton = (Button) findViewById(R.id.flash_button);
		flashButton.setOnClickListener(flashListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	private void takePicture() {
		shutterButton.setEnabled(false);
		//camera.autoFocus(myAutoFocusCallback);
		cameraSurfaceView.takePicture(this);
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {

		File pictureFileDir = getDir();

	    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

	      Log.d(DEBUG_TAG, "Can't create directory to save image.");
	      Toast.makeText(context, "Can't create directory to save image.",
	          Toast.LENGTH_LONG).show();
	      return;
	    }

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
	    String date = dateFormat.format(new Date());
	    String photoFile = "Picture_" + date + ".jpg";

	    String filename = pictureFileDir.getPath() + File.separator + photoFile;

	    File pictureFile = new File(filename);

	    try {
	      FileOutputStream fos = new FileOutputStream(pictureFile);
	      fos.write(data);
	      fos.close();
	      
	      Toast.makeText(context, "New Image saved: " + photoFile,
	         	Toast.LENGTH_LONG).show();
	    } catch (Exception error) {
	    	//write error to logcat
	      Log.d(DEBUG_TAG, "File" + filename + "not saved: "
	          + error.getMessage());
	      //toast to inform user of issue
	      Toast.makeText(context, "Image could not be saved.",
	          Toast.LENGTH_LONG).show();
	    }
		
		// Restart the preview and re-enable the shutter button so that we can take another picture
		camera.startPreview();
		shutterButton.setEnabled(true);
	}
	
	private File getDir() {
		File sdDir = Environment
			   .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	   return new File(sdDir, "CameraAPIDemo");
	} 
}