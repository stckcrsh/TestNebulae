package com.zoom.map;

public class Image {
	private String name;
	public String GetName(){return name;}
	public void SetName(String _value){name = _value;}
	
	private String ownerId;
	public String GetownerId(){return ownerId;}
	public void SetOwnerId(String _value){ownerId = _value;}
	
	private String path;
	public String GetPath(){return path;}
	public void SetPath(String _value){path = _value;}
	
	private String id;
	public String GetId(){return id;}
	public void SetId(String _value){id = _value;}
	
	private String description;
	public String GetDescription(){return description;}
	public void SetDescription(String _value){description = _value;}
	
	public Image(){
		
	}
	public Image(String _name, String _ownerId, String _path, String _description, String _id){
		ownerId = _ownerId;
		path = _path;
		description = _description;
		id = _id;
		name = _name;
	}
}
