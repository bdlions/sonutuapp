package com.sportzweb;

import com.sonuto.tabsswipe.adapter.BmiPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;

public class CreateBlogActivity extends Activity {
private Context mContext;
	
	FrameLayout bcategory_selection_step_layout,
	bcreate_title_step_layout, bcreate_main_step_layout,bcreate_add_pic_step_layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_create_blog);
        mContext = this;
        initUi();		
	}
	
	
public void initUi() {
    	
    	bcategory_selection_step_layout = (FrameLayout) findViewById(R.id.create_blog_box1);
    	bcreate_title_step_layout = (FrameLayout) findViewById(R.id.create_blog_box2);
    	bcreate_main_step_layout = (FrameLayout) findViewById(R.id.create_blog_box3);
    	bcreate_add_pic_step_layout = (FrameLayout) findViewById(R.id.create_blog_box4);
		
	}
    
    
    public void createBlogCategorySelectionStep(View view) {
    	bcreate_title_step_layout.setVisibility(View.VISIBLE);
    	bcategory_selection_step_layout.setVisibility(View.GONE);
    }
    
    
    public void createBlogTitleStep(View v) {
    	bcreate_main_step_layout.setVisibility(View.VISIBLE);
    	bcreate_title_step_layout.setVisibility(View.GONE);
    }
    
    public void createBlogMainTextStep(View v) {
    	bcreate_add_pic_step_layout.setVisibility(View.VISIBLE);
    	bcreate_main_step_layout.setVisibility(View.GONE);
    }
    
    
    public void browseFile(View v){
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
	}

}
