package com.sportzweb;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.bdlions.achartengine.ChartFactory;
import com.bdlions.achartengine.GraphicalView;
import com.bdlions.achartengine.model.CategorySeries;
import com.bdlions.achartengine.model.SeriesSelection;
import com.bdlions.achartengine.renderer.DefaultRenderer;
import com.bdlions.achartengine.renderer.SimpleSeriesRenderer;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.MemberProfile;
import com.sonuto.session.SessionManager;
import com.sportzweb.AccountSettingsInterest.SpecialInterestItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.View.OnClickListener;;

public class MemberSettingActivity extends Activity {

	class SpecialInterestItem{ int id; String code; int isSelected;}
	private GraphicalView mChart;
	private SpecialInterestItem[] interests;
	private ArrayList<Integer> selectedInterestIndices;
	DefaultRenderer defaultRenderer  = new DefaultRenderer(); 
	private Context mContext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_setting);
        
        mContext = this;
        
        new MemberProfile().getSpecialInterests(new ICallBack() {
			
			@Override
			public void callBackResultHandler(Object object) {
				JSONObject jsonObject = (JSONObject) object;
				
				try {
					JSONArray specialInterests = jsonObject.getJSONArray("special_interest_list");
					interests = new SpecialInterestItem[specialInterests.length()];
					for(int i = 0; i < specialInterests.length(); i ++){
						JSONObject specialInterest  = (JSONObject)specialInterests.get(i);
						interests[ i ] = new SpecialInterestItem();
						interests[ i ].code = specialInterest.getString("description");
						interests[ i ].id = specialInterest.getInt("special_interest_id");
						interests[ i ].isSelected = specialInterest.getInt("is_selected");
						
					}
					// Ploting the chart
			        openChart();
				}
				catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub
				
			}
		});
        
    }
    
    private void openChart(){
    	Display display = getWindowManager().getDefaultDisplay();
    	Point size = new Point();
    	display.getSize(size);
    	int width = size.x;
    	
    	//code = new String[] {"Fitness", "Health", "Sports"};
    	double[] distribution = { 33.3, 33.3, 33.3 } ;
    	int[] colors = { 0xFFDC3812, 0xFF3266CC, 0xFFFE9900};
    	
    	// Instantiating CategorySeries to plot Pie Chart    	
    	CategorySeries distributionSeries = new CategorySeries("");
    	for(int i=0 ;i < distribution.length;i++){
    		// Adding a slice with its values and name to the Pie Chart
    		distributionSeries.add(interests[i].code, distribution[i]);
    	}   
    	
    	// Instantiating a renderer for the Pie Chart
    	for(int i = 0 ;i<distribution.length;i++){ 
    		// Instantiating a render for the slice
    		SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();    	
    		seriesRenderer.setColor(colors[i]);
    		// Adding the renderer of a slice to the renderer of the pie chart
    		defaultRenderer.addSeriesRenderer(seriesRenderer);
    	}

    	defaultRenderer.setPanEnabled(false);
    	defaultRenderer.setShowLabels(true);
    	defaultRenderer.setFitLegend(true);
    	defaultRenderer.setInScroll(true);
    	defaultRenderer.setLegendTextSize(20);
    	
    	// Getting a reference to view group linear layout chart_container
    	LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_containe);
    	LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams(width, width);
    	chartContainer.setLayoutParams(params);
    	
    	// Getting PieChartView to add to the custom layout
    	mChart = ChartFactory.getPieChartView(getBaseContext(), distributionSeries, defaultRenderer);
    	
    	defaultRenderer.setClickEnabled(true);//
        defaultRenderer.setSelectableBuffer(10);
        mChart.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {

        		SeriesSelection seriesSelection = mChart.getCurrentSeriesAndPoint();
        		if (seriesSelection != null) {
        			// Getting the name of the clicked slice
        			int seriesIndex = seriesSelection.getPointIndex();
        			defaultRenderer.getSeriesRendererAt(seriesIndex).setHighlighted(!defaultRenderer.getSeriesRendererAt(seriesIndex).isHighlighted());
        	        mChart.repaint();
        		}
        }
        });
        // Adding the pie chart to the custom layout
    	chartContainer.addView(mChart);
    	
    }

    
    /*
	 * Save and continue click action
	 */
	public void saveANDcontinue(View view) {
		selectedInterestIndices = new ArrayList<Integer>();
		for(int i = 0; i < interests.length; i ++){
			if( defaultRenderer.getSeriesRendererAt(i).isHighlighted() ){
				selectedInterestIndices.add(interests[ i ].id);
			}
		}
		
		new MemberProfile().storeSpecialInterests(new ICallBack() {
			@Override
			public void callBackResultHandler(Object object) {
				Intent intent = new Intent(mContext, GenderSelectionActivity.class);
				startActivity(intent);
				finish();
			}
			@Override
			public void callBackErrorHandler(Object object) {
				
			}
		}, SessionManager.getInstance().getUserId(), new JSONArray( selectedInterestIndices).toString());
		
	}
	

	/*
	 * Skip this step open next activity click action
	 */
	public void skipthisStep(View view) {
		//MemberSettingActivity.this.finish();
		Intent intent = new Intent(mContext, GenderSelectionActivity.class);
		startActivity(intent);
		finish();
	}

}
