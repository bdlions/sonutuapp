package com.sportzweb;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MyPhotos extends Activity{
	private GridView gridView;
	private GridViewAdapter customGridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_photos);

		gridView = (GridView) findViewById(R.id.gridView);
		customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, getData());
		gridView.setAdapter(customGridAdapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
				int position, long id) {
				Toast.makeText(MyPhotos.this, position + "#Selected",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private ArrayList getData() {
		final ArrayList<String> imagesPath = new ArrayList<String>();
		
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/1.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/5.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/4.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/1.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/5.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/4.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/1.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/5.jpg");
		imagesPath.add("http://10.0.2.2/sportzweb/resources/images/applications/blog_app/4.jpg");
			
		return imagesPath;
	}

}
