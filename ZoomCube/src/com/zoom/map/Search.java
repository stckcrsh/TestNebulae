package com.zoom.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Search extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	}
	
	public void sendLocation(View view)
	{
		Intent intent = new Intent(this, MainActivity.class);
	    EditText editText = (EditText) findViewById(R.id.edit_message);
	    String message = editText.getText().toString();
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

}
