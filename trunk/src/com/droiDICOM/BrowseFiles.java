package com.droiDICOM;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class BrowseFiles extends Activity {
	GridView grid_main;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse);
		
		parseDicomFiles();
		
		grid_main = (GridView)findViewById(R.id.GridView01);
		grid_main.setAdapter(new ImageAdapter(this));
	}
	
	protected void parseDicomFiles() {
		// check sdcard for proper directory and if there are any files
	}
	
	//Nested class that handles creating and filling the gridview of DICOM thumbnails
	public class ImageAdapter extends BaseAdapter{
		Context mContext;
		public static final int ACTIVITY_CREATE = 10;
		public ImageAdapter(Context c){
			mContext = c;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v;
			if(convertView==null){
				LayoutInflater li = getLayoutInflater();
				v = li.inflate(R.layout.icon, null);
				TextView tv = (TextView)v.findViewById(R.id.icon_text);
				tv.setText("Profile "+position);
				ImageView iv = (ImageView)v.findViewById(R.id.icon_image);
				iv.setImageResource(R.drawable.icon);

			}
			else
			{
				v = convertView;
			}
			return v;
		}
		
		
		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
}
