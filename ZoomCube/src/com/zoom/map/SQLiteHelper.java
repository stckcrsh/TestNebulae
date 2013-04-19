package com.zoom.map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "ZoomCubeDB";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_TAGS = "tags";
	
	public enum COLUMNS_TAGS{
		TAG 		("tag"),
		ITEMID		("itemId");
		
		private String value;
		public String Value(){return value;}
		
		COLUMNS_TAGS(String _value){
			value = _value;
		}
	}
	
	public static final String TABLE_IMAGES = "images";
	
	public enum COLUMNS_IMAGES{
		ID			("id"),
		PATH		("path"),
		NAME		("name"),
		OWNERID		("ownerId"),
		DESCRIPTION	("description");
		
		private String value;
		public String Value(){return value;}
		
		COLUMNS_IMAGES(String _value){
			value = _value;
		}
	}
	
	
	public final String CREATE_TABLE_TAGS = "CREATE TABLE IF NOT EXISTS '" + TABLE_TAGS + "' ('" + 
				COLUMNS_TAGS.ITEMID.Value() + "' INTEGER NOT NULL , " +
				"'" + COLUMNS_TAGS.TAG.Value() + "' VARCHAR NOT NULL , " +
				"PRIMARY KEY ('" + COLUMNS_TAGS.ITEMID.Value() + "', '" + COLUMNS_TAGS.TAG.Value()+"'));";
	
	public final String CREATE_TABLE_IMAGES = "CREATE TABLE IF NOT EXISTS '"+TABLE_IMAGES+"' " +
				"('"+COLUMNS_IMAGES.ID.Value()+"' VARCHAR PRIMARY KEY  NOT NULL  UNIQUE , " +
				"'"+COLUMNS_IMAGES.NAME.Value()+"' VARCHAR NOT NULL , " +
				"'"+COLUMNS_IMAGES.PATH.Value()+"' VARCHAR NOT NULL , " +
				"'"+COLUMNS_IMAGES.DESCRIPTION.Value()+"' VARCHAR NOT NULL , " +
				"'"+COLUMNS_IMAGES.OWNERID.Value()+"' VARCHAR NOT NULL );";
			
	public SQLiteHelper(Context _context) {
		super(_context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase _database) {
		// TODO Auto-generated method stub
		//_database.execSQL("DELETE * FROM '"+TABLE_TAGS+"'");
		_database.execSQL(CREATE_TABLE_TAGS);
		_database.execSQL(CREATE_TABLE_IMAGES);
	}
	
	

	@Override
	public void onUpgrade(SQLiteDatabase _database, int _old, int _new) {
		// TODO Auto-generated method stub
		Log.w(SQLiteHelper.class.getName(),
		        "Upgrading database from version " + _old + " to "
		            + _new + ", which will destroy all old data");
		_database.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);
		_database.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
		onCreate(_database);
	}
}
