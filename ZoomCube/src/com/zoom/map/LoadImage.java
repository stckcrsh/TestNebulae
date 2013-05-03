package com.zoom.map;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class LoadImage extends Activity {

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.image_test);
		
		final ImageView iView = (ImageView) this.findViewById(R.id.imageView1);
		
		AsyncTask<Void, Void, Bitmap> task = new AsyncTask<Void, Void, Bitmap>(){
                       
			@Override
			protected Bitmap doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				URL url;
				Bitmap bmp = null;
				try {
					url = new URL("http://pulsemates.azurewebsites.net/api/item/517e3d07aca778097c3a6b6b");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty("content-type", "image/gif");
			        conn.connect();
	
			        InputStream in = conn.getInputStream();
			        bmp = BitmapFactory.decodeStream(in);
			        in.close();
			        System.out.println("Image loaded");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return bmp;
			}
			
			protected void onPostExecute(Bitmap _result){
				System.out.println("showing image");
				iView.setImageBitmap(_result);
			}
			
		}.execute();
	}
}
