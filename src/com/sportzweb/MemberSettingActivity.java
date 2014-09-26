package com.sportzweb;


import java.text.DecimalFormat;
import java.util.Properties;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.alexd.jsonrpc.JSONRPCClient;
import org.alexd.jsonrpc.JSONRPCException;
import org.alexd.jsonrpc.JSONRPCParams.Versions;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sampanit.sonutoapp.utils.AssetsPropertyReader;
import com.sampanit.sonutoapp.utils.UserSessionManager;
import com.sampanit.sonutoapp.utils.WebUtil;





import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MemberSettingActivity extends Activity {

	private Context mContext;
	private GraphicalView mChart;
	private String[] code;
	
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_setting);

		mContext = this;
		
		// Ploting the chart
        openChart();
	}
	
	
private void openChart(){
    	
    	// Pie Chart Slice Names
    	code = new String[] {
    			"Eclair & Older", "Froyo", "Gingerbread", "Honeycomb",
    			"IceCream Sandwich", "Jelly Bean" 
    	};    	
    	
    	// Pie Chart Slice Values
    	double[] distribution = { 3.9, 12.9, 55.8, 1.9, 23.7, 1.8 } ;
    	
    	// Color of each Pie Chart Slices
    	int[] colors = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN, Color.RED,
    					 Color.YELLOW };
    	
    	// Instantiating CategorySeries to plot Pie Chart    	
    	CategorySeries distributionSeries = new CategorySeries(" Android version distribution as on October 1, 2012");
    	for(int i=0 ;i < distribution.length;i++){
    		// Adding a slice with its values and name to the Pie Chart
    		distributionSeries.add(code[i], distribution[i]);
    	}   
    	
    	// Instantiating a renderer for the Pie Chart
    	DefaultRenderer defaultRenderer  = new DefaultRenderer();    	
    	for(int i = 0 ;i<distribution.length;i++){ 
    		
    		// Instantiating a render for the slice
    		SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();    	
    		seriesRenderer.setColor(colors[i]);
    		seriesRenderer.setDisplayChartValues(true);
    		
    		// Adding the renderer of a slice to the renderer of the pie chart
    		defaultRenderer.addSeriesRenderer(seriesRenderer);
    	}
    	
    	defaultRenderer.setChartTitle("Android version distribution as on October 1, 2012 ");
    	defaultRenderer.setChartTitleTextSize(20);
    	defaultRenderer.setZoomButtonsVisible(true);    	    		
    		
    	// Getting a reference to view group linear layout chart_container
    	LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
    	
    	
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
        			String selectedSeries="";
        			selectedSeries = code[seriesIndex];        			
                    
        			// Getting the value of the clicked slice
        			double value = seriesSelection.getXValue();
        			DecimalFormat dFormat = new DecimalFormat("#.#");
        			 
        			// Displaying the message
        			Toast.makeText(
        					getBaseContext(),
        					selectedSeries + " : "  + Double.valueOf(dFormat.format(value)) + " % " ,
        					Toast.LENGTH_SHORT).show();
        		}
        }
        });
        
        // Adding the pie chart to the custom layout
    	chartContainer.addView(mChart);
    }
	

	/*
	 * Back to login click action
	 */
	public void skipthisStep(View view) {
		//MemberSettingActivity.this.finish();
		Intent intent = new Intent(mContext, LoginActivity.class);
		startActivity(intent);
		finish();
	}

}
