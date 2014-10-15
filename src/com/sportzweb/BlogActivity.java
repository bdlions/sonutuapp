package com.sportzweb;

import com.sunuto.tabsswipe.adapter.BmiPagerAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class BlogActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi_app);
	    /** Getting a reference to the ViewPager defined the layout file */
	    ViewPager pager = (ViewPager) findViewById(R.id.pager);

	    /** Getting fragment manager */
	    FragmentManager fm = getSupportFragmentManager();

	    /** Instantiating FragmentPagerAdapter */
	    BmiPagerAdapter bmiPagerAdapter = new BmiPagerAdapter(fm);

	    /** Setting the pagerAdapter to the pager object */
	    // Here you would declare which page to visit on creation
	    pager.setAdapter(bmiPagerAdapter);
	    //pager.setCurrentItem(1);
	    pager.setCurrentItem(0);
				
	}

}
