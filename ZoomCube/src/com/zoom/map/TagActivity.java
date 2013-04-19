package com.zoom.map;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zoom.map.TagDAO;
import com.zoom.map.TagDialog;
import com.zoom.map.TagDialogListener;

public class TagActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final Context context = this;

		setContentView(R.layout.activity_tag);

		Button testButton = (Button)this.findViewById(R.id.button1);
		TextView tView = (TextView)this.findViewById(R.id.tag_itemId);
		testButton.setOnClickListener(new OnClickListener(){

			public int itemId;
			
//			public View.OnClickListener init(){
//				itemId = _itemId;
//				return this;
//			}
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				itemId = Integer.parseInt( ( (TextView)findViewById(R.id.tag_itemId) ).getText().toString());
				TagDialog tagDialog = new TagDialog(itemId, context, new TagDialogListener(){

					@Override
					public void resultReturned(ArrayList<String> _tags) {
						// TODO Auto-generated method stub
						TextView tView = (TextView)findViewById(R.id.activity_tag_text_view);
						tView.setText("");
						for(String tag : _tags){
							tView.setText(tView.getText()+" "+tag);
						}
					}

				});
				tagDialog.show();

			}

		});

		Button resultButton = (Button)this.findViewById(R.id.button2);
		resultButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				TextView tView = (TextView)findViewById(R.id.activity_tag_text_view);
				tView.setText("");
				TagDAO tDAO = new TagDAO(context);
				tDAO.open();
				tDAO.emptyTags();
				tDAO.close();
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tag, menu);
		return true;
	}

}