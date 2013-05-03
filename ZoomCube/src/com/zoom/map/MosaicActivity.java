package com.zoom.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.koushikdutta.urlimageviewhelper.UrlImageViewCallback;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.zoom.map.QueryBuilder.SEARCHABLES;

public class MosaicActivity extends Activity implements QueryListener{

	JSONArray items;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_mosaic);
		System.out.println("Starting query builder");
		new QueryBuilder().SearchableItem(SEARCHABLES.ITEM).executeQuery(this);
		System.out.println("ending query builder");
	}
	
	@Override
	public void QueryResultsReturned(JSONArray _results) throws JSONException {
		// TODO Auto-generated method stub
		System.out.println("Query Result returned");
		items = _results;
		ArrayList<Image> items = new ArrayList<Image>();
		JSONObject obj;
		JSONArray JSONTags;
		ArrayList<String> tags;
		Image image;
		for (int i=0;i<_results.length();i++){
			image = new Image();
			obj = (JSONObject) _results.get(i);
			image.SetOnlineId(obj.getString("Id"));
			image.SetDescription(obj.getString("Description"));
			image.SetName(obj.getString("Name"));
			tags = new ArrayList<String>();
			JSONTags = obj.getJSONArray("Tags");
			for (int j = 0;j<JSONTags.length();j++){
				tags.add((String) JSONTags.get(j));
			}
			image.SetTags(tags.toArray());
			items.add(image);
			System.out.println(image.GetDescription());
		}
		
		ListView listView = (ListView) this.findViewById(R.id.listView1);
		final StableArrayAdapter adapter = new StableArrayAdapter(this,
	            R.layout.list_test, items);
	        listView.setAdapter(adapter);
	}
	
	private class StableArrayAdapter extends ArrayAdapter<Image> {

  	  private Context context;

  	  public StableArrayAdapter(Context _context, int _textViewResourceId,
	          List<Image> _images) {
	          super(_context, _textViewResourceId, _images);
	          context = _context;
      }

      @Override
      public boolean hasStableIds() {
      	
      	return true;
      }
      
      	public View getView(int _position, View _convertView, ViewGroup _parent) {
      		LayoutInflater inflater = (LayoutInflater) context
      				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  
      		ImageView iv;
            if (_convertView == null)
                _convertView = getLayoutInflater().inflate(R.layout.tag_stream_single, null);

            iv = (ImageView)_convertView.findViewById(R.id.imageView1);
            
            iv.setAnimation(null);
            // yep, that's it. it handles the downloading and showing an interstitial image automagically.
            UrlImageViewHelper.setUrlDrawable(iv, "http://pulsemates.azurewebsites.net/api/item/"+getItem(_position).GetOnlineId(), R.drawable.ic_launcher, new UrlImageViewCallback() {
                @Override
                public void onLoaded(ImageView imageView, Bitmap loadedBitmap, String url, boolean loadedFromCache) {
                    if (!loadedFromCache) {
                        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, .5f, ScaleAnimation.RELATIVE_TO_SELF, .5f);
                        scale.setDuration(140);
                        imageView.startAnimation(scale);
                    }
                }
            });

            
            
            _convertView.setOnClickListener(new View.OnClickListener() {
				private int position;
				private ViewGroup parent;
            	
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					Toast toast = Toast.makeText(parent.getContext(), getItem(position).GetName(), Toast.LENGTH_SHORT);
					toast.show();
				}
				
				public View.OnClickListener init(int _position, ViewGroup _parent){
					parent = _parent;
					position = _position;
					
					return this;
				}
			}.init(_position, _parent));
           return _convertView;
      	}
      
    }
	
	
	
}
