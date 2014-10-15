package com.sunuto.tabsswipe.adapter;

import com.sonuto.tabsswipe.BmiQuestionFragment;
import com.sonuto.tabsswipe.BmiCalculatorFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class BmiPagerAdapter extends FragmentPagerAdapter {

	final int PAGE_COUNT = 2;

	public BmiPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
	    switch (position) {
	    case 0:
	        // Your current main fragment showing how to send arguments to fragment
	    	BmiCalculatorFragment bmiCalculatorFragment = new BmiCalculatorFragment();
	        Bundle data = new Bundle();
	        data.putInt("current_page", position+1);
	        bmiCalculatorFragment.setArguments(data);
	        return bmiCalculatorFragment;
	    case 1:
	        // Calling a Fragment without sending arguments
	    	//BmiQuestionFragment bmiQuestionFragment = new BmiQuestionFragment();
	    	
	        //return bmiQuestionFragment;
	    	return new BmiQuestionFragment(); 
//	    	BmiCalculatorFragment bmiCalculatorFragment1 = new BmiCalculatorFragment();
//	        Bundle data1 = new Bundle();
//	        data1.putInt("current_page", position+1);
//	        bmiCalculatorFragment1.setArguments(data1);
//	        return bmiCalculatorFragment1;
	    }
	    
	    return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return PAGE_COUNT;
	}

}
