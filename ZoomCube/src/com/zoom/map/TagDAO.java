package com.zoom.map;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class TagDAO {
	private SQLiteDatabase database;
	private SQLiteHelper dbHelper;

	private String[] allColumns = { SQLiteHelper.COLUMNS_TAGS.ITEMID.Value(),
		SQLiteHelper.COLUMNS_TAGS.TAG.Value() };

	public TagDAO(Context _context) {
		dbHelper = new SQLiteHelper(_context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}

	public Tag createTag(String _tag, int _itemId) {
	    ContentValues values = new ContentValues();
	    values.put(SQLiteHelper.COLUMNS_TAGS.TAG.Value(), _tag);
	    values.put(SQLiteHelper.COLUMNS_TAGS.ITEMID.Value(), _itemId);
	    long insertId = database.insert(SQLiteHelper.TABLE_TAGS, null,
	        values);
	    Cursor cursor = database.query(SQLiteHelper.TABLE_TAGS,
	        allColumns, SQLiteHelper.COLUMNS_TAGS.ITEMID.Value() + " = " + _itemId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Tag newTag = cursorToTag(cursor);
	    cursor.close();
	    return newTag;
	}
	
	public List<Tag> getAllTags() {
	    List<Tag> tags = new ArrayList<Tag>();

	    Cursor cursor = database.query(SQLiteHelper.TABLE_TAGS,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Tag tag = cursorToTag(cursor);
	      tags.add(tag);
	      cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return tags;
	  }
	
	public List<Tag> getAllTagsByItemId(int _itemId){
		List<Tag> tags = new ArrayList<Tag>();
		Cursor cursor = database.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_TAGS+" WHERE "+SQLiteHelper.COLUMNS_TAGS.ITEMID.Value()+" = " +
				_itemId, null);
//		Cursor cursor = database.query(SQLiteHelper.TABLE_TAGS, allColumns, SQLiteHelper.COLUMNS_TAGS.ITEMID.Value()+" = "+_itemId, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()){
			Tag tag = cursorToTag(cursor);
			tags.add(tag);
			cursor.moveToNext();
		}
		cursor.close();
		return tags;
	}
	
	 public void deleteTag(Tag _tag) {
		int id = _tag.GetItemId();
		String tag = _tag.GetTag();
		System.out.println("Tag deleted with id: " + id);
		
		database.delete(SQLiteHelper.TABLE_TAGS, SQLiteHelper.COLUMNS_TAGS.ITEMID.Value() + " = "+id+"  AND " +
				SQLiteHelper.COLUMNS_TAGS.TAG.Value() + " = '"+tag+"'", new String[0]);
	 }

	
	 private Tag cursorToTag(Cursor _cursor) {
		 Tag tag = new Tag();
		 tag.SetItemId(_cursor.getInt(0));
		 tag.SetTag(_cursor.getString(1));
		 return tag;
	 }

	 public void emptyTags(){
		 database.delete(SQLiteHelper.TABLE_TAGS, null, null);
	 }
}
