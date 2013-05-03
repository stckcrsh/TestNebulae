package com.zoom.map;

import org.json.JSONArray;
import org.json.JSONException;

public interface QueryListener {
	public void QueryResultsReturned(JSONArray _results) throws JSONException;
}
