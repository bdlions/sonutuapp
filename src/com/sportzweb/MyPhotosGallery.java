package com.sportzweb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class MyPhotosGallery extends Activity{
	ImageView selectedImage; 
    private Integer[] mImageIds={
    		R.drawable.fail,
            R.drawable.followers,
            R.drawable.gear,
            R.drawable.like
       };
   @Override
   public void onCreate(Bundle savedInstanceState)
   {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_profile_my_photos);
     
            Gallery gallery = (Gallery) findViewById(R.id.gallery1);
       selectedImage=(ImageView)findViewById(R.id.imageView1);
       gallery.setSpacing(1);
       gallery.setAdapter(new GalleryImageAdapter(this));       
       
       gallery.setOnItemClickListener(new OnItemClickListener() {
    	   @Override
           public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               Toast.makeText(MyPhotosGallery.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
               // show the selected Image
               selectedImage.setImageResource(mImageIds[position]);
           }		
       });
   }

}
