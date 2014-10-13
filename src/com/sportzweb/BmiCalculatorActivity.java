package com.sportzweb;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;

public class BmiCalculatorActivity extends Activity {

	EditText weightText, heightText;
	TextView resultBmiText,resultBmiCategoryText;
	private int selectedColor = Color.GREEN;
	private int defaultColor = Color.GRAY;
	
	private float weight=0,height=0,bmiValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bmi_calculator);
		
		
		final Button btnMale = (Button) findViewById(R.id.btnMale);
		final Button btnFemale = (Button) findViewById(R.id.btnFemale);
		final Button btnMetric = (Button) findViewById(R.id.btnMetric);
		final Button btnImperical = (Button) findViewById(R.id.btnImperial);
		Button btnCalculateBMI = (Button) findViewById(R.id.btnCalculate);
		
		btnMale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnMale.setBackgroundColor(selectedColor);
				btnFemale.setBackgroundColor(defaultColor);
			}
		});
		
		
		btnFemale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMale.setBackgroundColor(defaultColor);
				btnFemale.setBackgroundColor(selectedColor);
			}
		});
		
		
		btnMetric.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMetric.setBackgroundColor(selectedColor);
				btnImperical.setBackgroundColor(defaultColor);
			}
		});
		
		
		btnImperical.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMetric.setBackgroundColor(defaultColor);
				btnImperical.setBackgroundColor(selectedColor);
			}
		});
		
		btnCalculateBMI.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					// get the references to the widgets
					weightText = (EditText) findViewById(R.id.bmiWeightEdtTxt);
					heightText = (EditText) findViewById(R.id.bmiHeightEdtTxt);
					
					resultBmiText = (TextView)findViewById(R.id.bmiResultLabel);
					resultBmiCategoryText = (TextView)findViewById(R.id.bmiCategoryResultLabel);
					
					
					 
					 if (bmiFieldValidation()) {
						// get the users values from the widget references
						 weight = Float.parseFloat(weightText.getText().toString());
						 height = Float.parseFloat(heightText.getText().toString());
						 
						 int metricColor = ((ColorDrawable) btnMetric.getBackground()).getColor();
						 if(metricColor == selectedColor){
							 //metric is selected
							 //calculate the bmi value
							 bmiValue = calculate(weight, height);

						 }
						 else{
							 //imperical is selected
							 bmiValue = Math.round(( (weight * 703) / (height * height) ) * 100) / 100;
						 }
						
						// interpret the meaning of the bmi value
						String bmiInterpretation = interpretBMI(bmiValue);

						// now set the value in the result text
						resultBmiText.setText(" "+ bmiValue);
						resultBmiText.setTextColor(Color.parseColor("#FF0000"));
						
						resultBmiCategoryText.setText(" "+bmiInterpretation);
						resultBmiCategoryText.setTextColor(Color.parseColor("#FF0000"));
					 }

				}
		});
		
		btnMale.setBackgroundColor(selectedColor);
		btnMetric.setBackgroundColor(selectedColor);

	}

	// the formula to calculate the BMI index
	private float calculate(float weight, float height) {
		return (float) Math.round((weight / (height * height) * 100)) / 100 ;
	}

	// interpret what BMI means
	private String interpretBMI(float bmiValue) {
		if (bmiValue < 16) {
			return "Severely underweight";
		} else if (bmiValue < 18.5) {

			return "Underweight";
		} else if (bmiValue < 25) {

			return "Normal";
		} else if (bmiValue < 30) {

			return "Overweight";
		} else {
			return "Obese";
		}

	}
	
	private boolean bmiFieldValidation(){
		if(heightText.getText().length() <= 0) {
			Toast.makeText(getApplicationContext(), getString(R.string.heightRequiered),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		if(weightText.getText().length() <= 0) {
			Toast.makeText(getApplicationContext(), getString(R.string.weightRequiered),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		
		return true;
	}
	
}
