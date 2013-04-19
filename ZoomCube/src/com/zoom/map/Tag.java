package com.zoom.map;

public class Tag {	
	private String tag;
	public String GetTag(){return tag;}
	public void SetTag(String _value){tag = _value;}
	
	private int itemId;
	public int GetItemId(){return itemId;}
	public void SetItemId(int _value){itemId = _value;}	
	
	public Tag(){
		
	}
	public Tag(String _tag, int _itemId){
		tag = _tag;
		itemId = _itemId;
	}
}
