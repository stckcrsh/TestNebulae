package com.zoom.map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;

import android.os.AsyncTask;

public class QueryBuilder {

	public class JSONFetcher extends AsyncTask<URL, Void, String>{

		@Override
		protected String doInBackground(URL... params) {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		protected void onPostExecute(String _result) {
				// TODO Auto-generated method stub
				super.onPostExecute(_result);
				JSONStringReturned(_result);
		}
	}
	
	public enum ARGUMENTS{
		TAG			("tag"),
		LATTITUDE	("lattitude"),
		LONGITUDE	("longitude"),
		RADIUS		("radius");
		
		private String value;
		public String Value(){return value;}
		ARGUMENTS(String _value){
			value = _value;
		}
	}
	
	public enum SEARCHABLES{
		ITEM		("item");
		
		
		private String value;
		public String Value(){return value;}
		SEARCHABLES(String _value){
			value = _value;
		}
	}
	
	
	
	private final String PULSEMATESURL = "http://pulsemates.azurewebsites.net/api/";
	private SEARCHABLES searchItem;
	private HashMap<String, String> arguments;
	private QueryListener listener;
	
	QueryBuilder(){
		arguments = new HashMap<String, String>();
	}
	
	public QueryBuilder SearchableItem(SEARCHABLES _searchable){
		System.out.println("searchable step start");
		searchItem = _searchable;
		System.out.println("searchable step finish");
		return this;
	}
	
	public QueryBuilder addArguments(ARGUMENTS _argument, String _value){
		System.out.println("addargument step start");
		if(!arguments.isEmpty()){
			if(arguments.containsKey(_argument.Value())){
				System.out.println("addargument step end");
				return this;
			} else {
				arguments.put(_argument.Value(),_value);
				System.out.println("addargument step end");
				return this;
			}
		} else {
			arguments.put(_argument.Value(),_value);
			System.out.println("addargument step end");
			return this;
		}
		
	}
	
	public void JSONStringReturned(String _JSONString){
		//TODO here we parse our json string into an array and save that
		JSONArray jArray;
		try {
			jArray = new JSONArray(_JSONString);
			System.out.println("Async finished and returned");
			listener.QueryResultsReturned(jArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public QueryBuilder executeQuery(QueryListener _listener){
		System.out.println("execute step start");
		listener = _listener;
		StringBuilder query = new StringBuilder();
		query.append(PULSEMATESURL);
		query.append(searchItem.Value());
		
		if (!arguments.isEmpty()){
			query.append("?");
			int x = 0;
			for (HashMap.Entry<String, String> entry : arguments.entrySet()) {
			    String key = entry.getKey();
			    Object value = entry.getValue();
			    if(x==0){
			    	x++;
			    } else {
			    	query.append("&");
			    }
			    
			    query.append(key+"="+value);
			}
		}
		
		System.out.println("QUERY: "+query.toString());
		try {
			new AsyncTask<URL, Void, String>(){

				@Override
				protected String doInBackground(URL... arg0) {
					// TODO Auto-generated method stub
					StringBuilder response = new StringBuilder();
					try {
						
						System.out.println("Started AsyncTask");
						HttpURLConnection urlConnection = (HttpURLConnection) arg0[0].openConnection();
						System.out.println("UrlConnection successful");
						if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
					    {
					        BufferedReader input = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()),8192);
					        String strLine = null;
					        while ((strLine = input.readLine()) != null)
					        {
					            response.append(strLine);
					        }
					        input.close();
					    }
						System.out.println("finished task");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return response.toString();
				}
				
				protected void onPostExecute(String _result) {
					JSONStringReturned(_result);
			    }

				
				
			}.execute(new URL(query.toString()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("execute step end");
		return this;
	}
}
