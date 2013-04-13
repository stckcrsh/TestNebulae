package com.zoom.map;


import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

	
public class MainActivity extends FragmentActivity 
	{
	//create the map and location manager for screen
	private GoogleMap map = null;
	private Location location;
	private LocationManager locationManager;
	private String provider;	
	
	private static final String TAG = "MapsActivity";

	@Override
    protected void onCreate(Bundle savedInstanceState) 
	{	
		Log.d(TAG, "On Create Start!");
		
        super.onCreate(savedInstanceState);	    
        setContentView(R.layout.activity_main);
        
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d(TAG, "Location Manager get");
        
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        
        if(location != null)
        	{
        		//System.out.println("Provider " + provider + "has been selected.");
        		onLocationChanged(location);
        	} else {
        	
        		//last and long set text fields
        	
        		}        
        
        //setting up the map
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();     
        Log.d(TAG, "Map Support Fragment Start!");
        //anon inner class 
        //toast test that opens new activity
        
        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() 
        {

			@Override
			public void onInfoWindowClick(Marker marker)
			{
				Log.d(TAG, "On InfoWindowClick Error!");
					// TODO Auto-generated method stub
					Toast.makeText(getBaseContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent(MainActivity.this, PhotoViewActivity.class);
				    //intent.putExtra(provider, location);
				    startActivity(intent);
					//startActivity(new Intent(MainActivity.this, PhotoViewActivity.class));
			}
        	
        });
        
        //Animating camera smoothly to fixed position
        	
        
        	if(savedInstanceState == null)
        		{        	
        			CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(41.896731001085115, -87.62794017791748));        	
        			CameraUpdate zoom = CameraUpdateFactory.zoomTo(5);        	
        			map.moveCamera(center);
        			map.animateCamera(zoom); 
        			Log.d(TAG, "Camera Animate Start!");
        		}
        	
        	
        //testing myLocation features
        map.setMyLocationEnabled(true); 
       map.setOnMyLocationChangeListener(null);        
        
        //Marker Testing with phone's current
       
       if(location != null)
       map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Marker is here bitches!").snippet("This is a marker at our location..."));
       
       Log.d(TAG, "Add Marker myLoc!");
        
	}
	
	/**********************************************
	 * stuff for user location finding and changing
	 *********************************************/
	
	public void onLocationChanged(Location location)
	{
		int lat = (int) (location.getLatitude());
		int lng = (int) (location.getLongitude());
		//lat and lng field set text
		Log.d(TAG, "On Location Changed Error!");
	}
	
	public void onMyLocationChange(Location lastKnownLocation)
	{
		Log.d(getClass().getSimpleName(),
				String.format("%f:%f", lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude()));
	}
	
	/************************
	 * Marker click tests
	 ***********************/
	public boolean onMarkerClick(Marker marker)
	{
		Log.d(TAG, "on Marker click Error!");
		//Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
		
		return(false);
	}	
	
	
	
	/**************************
	** options stuff goes here
	**************************/
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	Log.d(TAG, "Options Error!");
        // Inflate the menu;
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }	    
    
}
