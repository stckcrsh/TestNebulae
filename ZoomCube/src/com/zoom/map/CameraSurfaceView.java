package com.zoom.map;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.Toast;

class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback, AutoFocusCallback {

	Camera camera;
	AutoFocusCallback myAutoFocusCallback;
	Context context;
	private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;
	List<String> focusModes;
	List<String> flashModes;
	List<String> availableCameras;
	
	String DEBUG_TAG = "CameraSurfaceView";
	
	@Override
	public void onAutoFocus(boolean arg0, Camera arg1) {
		// TODO Auto-generated method stub
		//shutterButton.setEnabled(true);
	}
	
	//CameraSurfaceView Constructor
	CameraSurfaceView(Context context) {
		super(context);
		this.context = context;

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		//TODO fix orientations!
		
		// The default orientation is landscape, so for a portrait app like this
		// one we need to rotate the view 90 degrees.
		camera.setDisplayOrientation(90);

		// IMPORTANT: We must call startPreview() on the camera before we take
		// any pictures
		camera.startPreview();
		//camera.autoFocus(myAutoFocusCallback);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// Open the Camera in preview mode
			this.camera = Camera.open();
			
			Camera.Parameters parameters = camera.getParameters();
			
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
			
			// Set AutoFocus mode
			focusModes = parameters.getSupportedFocusModes();
			if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
			    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			    camera.setParameters(parameters);
			    Toast.makeText(context, "Autofocus set!",
						Toast.LENGTH_SHORT).show();	
			}else{
				Toast.makeText(context, "Autofocus not set!",
						Toast.LENGTH_SHORT).show();	
			}
			
		this.camera.setPreviewDisplay(holder);
		
		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when replaced with a new screen
		// Always make sure to release the Camera instance
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
	}

	public void takePicture(PictureCallback imageCallback) {
		//camera.autoFocus(myAutoFocusCallback);
		camera.takePicture(null, null, imageCallback);	
	}
	
	public void switchCamera(boolean isRearEnabled, Button flashButton){
		
		//TODO Check available cameras and set 
		//camera dynamically between front/back
		
		String message;
		Camera.Parameters parameters = camera.getParameters();
		
		/*
		if(isRearEnabled){
			if (Camera.getNumberOfCameras() > 1 && camId < Camera.getNumberOfCameras() - 1) {
	            startCamera(camId + 1);
	        } else {
	            startCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
	        }
		
		//Toast Active Camera
		Toast.makeText(context, message,
	         	Toast.LENGTH_SHORT).show();
		*/
		try{
			
		
			//disable flash if front camera active
			flashButton.setEnabled(isRearEnabled);
			camera.setParameters(parameters);
		}catch (Exception error){
			Toast.makeText(context, "Flash Toggle Failed!", Toast.LENGTH_SHORT).show();
		
		}
	}
	
	public void toggleFlash(boolean isFlashEnabled){
		
		//TODO Check available flash parameters and
		//set flash mode dynamically between on/off/auto
		
		String message;
		Camera.Parameters parameters = camera.getParameters();
		
		
		if(isFlashEnabled){
			message = "Flash disabled!";
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			
		}else{
			message = "Flash enabled!";
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
			
		}
		
		//Toast Flash state
		Toast.makeText(context, message,
	         	Toast.LENGTH_SHORT).show();
		
		try{
			
			camera.setParameters(parameters);
		}catch (Exception error){
			Toast.makeText(context, "Flash Toggle Failed!", Toast.LENGTH_SHORT).show();
		
		}
	}
}