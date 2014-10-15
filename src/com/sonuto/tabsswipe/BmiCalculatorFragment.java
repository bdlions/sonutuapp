package com.sonuto.tabsswipe;

import com.sportzweb.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BmiCalculatorFragment extends Fragment {

	String tag = this.getClass().getSimpleName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(tag, "onCreate");
		super.onCreate(savedInstanceState);
		/** Getting the arguments to the Bundle object */
		Bundle data = getArguments();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_bmi_calculator, container,
				false);

		return view;
	}

}