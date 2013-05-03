package com.zoom.map;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LocationDAO {
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	private String[] allColumns;

	public LocationDAO(Context _context) {
		dbHelper = new SQLiteHelper(_context);
		ArrayList<String> tempColumns = new ArrayList<String>();
		for(Location.COLUMNS_LOCATIONS col : Location.COLUMNS_LOCATIONS.values()){
			tempColumns.add(col.Value());
		}
		allColumns = (String[]) tempColumns.toArray();
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	
	public Location insertLocation(double _lattitude, double _longitude, long _itemId, Date _timestamp){
		ContentValues values = new ContentValues();
		values.put(Location.COLUMNS_LOCATIONS.LATTITUDE.Value(), _lattitude);
		values.put(Location.COLUMNS_LOCATIONS.LONGITUDE.Value(), _longitude);
		values.put(Location.COLUMNS_LOCATIONS.ITEMID.Value(), _itemId);
		values.put(Location.COLUMNS_LOCATIONS.TIMESTAMP.Value(), _timestamp.toString());
		long insertId = database.insert(Location.TABLE_NAME, null, values);
		Cursor cursor = database.query(Location.TABLE_NAME, allColumns, 
				Location.COLUMNS_LOCATIONS.ITEMID.Value() + " = " + _itemId, null, null, null, null);
		cursor.moveToFirst();
		Location newLocation = cursorToLocation(cursor);
		cursor.close();
		return newLocation;
	}
	
	public List<Location> getAlllocations() {
	    List<Location> locations = new ArrayList<Location>();

	    Cursor cursor = database.query(Location.TABLE_NAME,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	Location location = cursorToLocation(cursor);
	    	locations.add(location);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return locations;
	}
	
	public void deleteLocation(Location _location) {
	}

	private Location cursorToLocation(Cursor _cursor) {
		// TODO Auto-generated method stub
		return null;
	}

}
