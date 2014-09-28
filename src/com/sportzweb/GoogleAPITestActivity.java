package com.sportzweb;

import java.text.DecimalFormat;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;





import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GoogleAPITestActivity extends Activity {

	private GraphicalView mChart;
	private String[] code;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_apitest);
        
        // Ploting the chart
        openChart();        
    }
    
    private void openChart(){
    	
    	// Pie Chart Slice Names
 
    	code = new String[] {"Fitness", "Health", "Sports"};
    	
    	// Pie Chart Slice Values
    	double[] distribution = { 33.3, 33.3, 33.3 } ;
    	
    	// Color of each Pie Chart Slices
    	//int[] colors = { Color.BLUE, Color.YELLOW, Color.MAGENTA};

    	int[] colors = { 0xFFDC3812, 0xFF3266CC, 0xFFFE9900};
    	
    	// Instantiating CategorySeries to plot Pie Chart    	
    	CategorySeries distributionSeries = new CategorySeries("");
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
    		//seriesRenderer.setDisplayChartValues(true);
    		
    		// Adding the renderer of a slice to the renderer of the pie chart
    		defaultRenderer.addSeriesRenderer(seriesRenderer);
    	}
    	
//    	defaultRenderer.setChartTitle("");
//    	defaultRenderer.setChartTitleTextSize(20);
//    	defaultRenderer.setZoomButtonsVisible(true);    	    		
    	
//    	defaultRenderer.setPanEnabled(false);
//    	defaultRenderer.setZoomRate(0.2f);
//    	defaultRenderer.setZoomEnabled(false);
//    	defaultRenderer.setInScroll(true);
    	
    	defaultRenderer.setZoomEnabled(false);

    	defaultRenderer.setPanEnabled(false);

    	defaultRenderer.setZoomRate(6.0f);

    	defaultRenderer.setShowLabels(true);

    	defaultRenderer.setFitLegend(true);

    	defaultRenderer.setInScroll(true);
    	defaultRenderer.setLegendTextSize(10);
    	
    	// Getting a reference to view group linear layout chart_container
    	LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_containe);
    	LinearLayout.LayoutParams params = new  LinearLayout.LayoutParams(200,200);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.google_apitest, menu);
        return true;
    }

}
