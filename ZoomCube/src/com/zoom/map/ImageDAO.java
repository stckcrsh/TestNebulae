package com.zoom.map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ImageDAO {
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	private String[] allColumns = { SQLiteHelper.COLUMNS_IMAGES.ID.Value(),
		SQLiteHelper.COLUMNS_IMAGES.DESCRIPTION.Value(),  SQLiteHelper.COLUMNS_IMAGES.NAME.Value(),
		SQLiteHelper.COLUMNS_IMAGES.OWNERID.Value(), SQLiteHelper.COLUMNS_IMAGES.PATH.Value()};

	public ImageDAO(Context _context) {
		dbHelper = new SQLiteHelper(_context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	
	public Image createImage(String _description, String _name, String _ownerId, String _path){
		ContentValues values = new ContentValues();
		values.put(SQLiteHelper.COLUMNS_IMAGES.DESCRIPTION.Value(), _description);
		values.put(SQLiteHelper.COLUMNS_IMAGES.NAME.Value(), _name);
		values.put(SQLiteHelper.COLUMNS_IMAGES.OWNERID.Value(), _ownerId);
		values.put(SQLiteHelper.COLUMNS_IMAGES.PATH.Value(), _path);
		long insertId = database.insert(SQLiteHelper.TABLE_IMAGES, null, values);
		Cursor cursor = database.query(SQLiteHelper.TABLE_IMAGES, allColumns, 
				SQLiteHelper.COLUMNS_IMAGES.ID.Value() + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		Image newImage = cursorToImage(cursor);
		cursor.close();
		return newImage;
	}

	private Image cursorToImage(Cursor _cursor) {
		// TODO Auto-generated method stub
		return null;
	}

}
