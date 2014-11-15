package com.sportzweb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.sonuto.rpc.AppBMICalculator;
import com.sonuto.rpc.ICallBack;
import com.sonuto.utils.component.ArrayListFragment;
import com.sportzweb.JSONObjectModel.BMIQuestion;
import com.sportzweb.JSONObjectModel.BlogCategory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class BmiCalculatorActivity extends Fragment {

	View rootView;

	EditText weightText, heightText;
	TextView resultBmiText, resultBmiCategoryText;
	private int selectedColor = 0xFF22B14C;
	private int defaultColor = 0xFFD6D6D6;
	LinearLayout bmi_question_ll;
	// process dialer
	ProgressDialog pDialog;

	private float weight = 0, height = 0, bmiValue;

	Button btnMale, btnFemale, btnMetric, btnImperical;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.activity_bmi_calculator,
				container, false);
		bmi_question_ll = (LinearLayout) rootView
				.findViewById(R.id.bmi_question_ll);

		// pDialog = new ProgressDialog(getActivity());
		// pDialog.setMessage("Fetching data..");
		// pDialog.setCancelable(false);
		// pDialog.show();

		AppBMICalculator bmiCalculator = new AppBMICalculator();
		bmiCalculator.getBMIQuestionList(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				// pDialog.dismiss();
				JSONObject blogListJsonObj = (JSONObject) object;

				try {
					JSONArray bmiQuestionListArray = blogListJsonObj
							.getJSONArray("question_list");
					Gson gson = new Gson();
					int toalQuestion = bmiQuestionListArray.length();
					for (int i = 0; i < toalQuestion; i++) {
						BMIQuestion bmiQuestion = gson.fromJson(bmiQuestionListArray.get(i).toString(), BMIQuestion.class);
						LayoutParams lparams = new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
						lparams.setMargins(0, 10, 0, 10);

						TextView tvHeading = new TextView(getActivity());
						TextView tvDescription = new TextView(getActivity());

						tvHeading.setLayoutParams(lparams);
						tvHeading.setTextColor(Color.parseColor("#ff33CC00"));
						tvHeading.setTextSize(15f);
						tvHeading.setText(bmiQuestion.getQuestion());

						tvDescription.setLayoutParams(lparams);
						tvDescription.setText(bmiQuestion.getAnswer());
						tvDescription.setTextSize(15f);

						bmi_question_ll.addView(tvHeading);
						bmi_question_ll.addView(tvDescription);
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void callBackErrorHandler(Object object) {

			}
		});

		btnMale = (Button) rootView.findViewById(R.id.btnMale);
		btnFemale = (Button) rootView.findViewById(R.id.btnFemale);
		btnMetric = (Button) rootView.findViewById(R.id.btnMetric);
		btnImperical = (Button) rootView.findViewById(R.id.btnImperial);

		weightText = (EditText) rootView.findViewById(R.id.bmiWeightEdtTxt);
		heightText = (EditText) rootView.findViewById(R.id.bmiHeightEdtTxt);

		btnMale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				btnMale.setBackgroundColor(selectedColor);
				btnFemale.setBackgroundColor(defaultColor);
				int metricColor = ((ColorDrawable) btnMetric.getBackground())
						.getColor();
				if (metricColor == selectedColor) {
					weightText.setText("");
					weightText.setHint("Enter your weight (kg)");
					heightText.setText("");
					heightText.setHint("Enter your height (m)");
				} else {
					weightText.setText("");
					weightText.setHint("Enter your weight (lb)");
					heightText.setText("");
					heightText.setHint("Enter your height (\")");
				}
			}
		});

		btnFemale.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMale.setBackgroundColor(defaultColor);
				btnFemale.setBackgroundColor(selectedColor);
				int imperialColor = ((ColorDrawable) btnImperical
						.getBackground()).getColor();
				if (imperialColor == selectedColor) {
					weightText.setText("");
					weightText.setHint("Enter your weight (lb)");
					heightText.setText("");
					heightText.setHint("Enter your height (\")");
				} else {
					weightText.setText("");
					weightText.setHint("Enter your weight (kg)");
					heightText.setText("");
					heightText.setHint("Enter your height (m)");
				}
			}
		});

		btnMetric.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMetric.setBackgroundColor(selectedColor);
				btnImperical.setBackgroundColor(defaultColor);
				weightText.setText("");
				weightText.setHint("Enter your weight (kg)");
				heightText.setText("");
				heightText.setHint("Enter your height (m)");
			}
		});

		btnImperical.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnMetric.setBackgroundColor(defaultColor);
				btnImperical.setBackgroundColor(selectedColor);
				weightText.setText("");
				weightText.setHint("Enter your weight (lb)");
				heightText.setText("");
				heightText.setHint("Enter your height (\")");
			}
		});

		weightText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				displayBmiResult();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		heightText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				displayBmiResult();

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		btnMale.setBackgroundColor(selectedColor);
		btnMetric.setBackgroundColor(selectedColor);
		btnFemale.setBackgroundColor(defaultColor);
		btnImperical.setBackgroundColor(defaultColor);
		return rootView;
	}

	public void displayBmiResult() {
		resultBmiText = (TextView) rootView.findViewById(R.id.bmiResultLabel);
		resultBmiCategoryText = (TextView) rootView
				.findViewById(R.id.bmiCategoryResultLabel);

		resultBmiText.setText("");
		resultBmiCategoryText.setText("");
		if (heightText.getText().length() > 0
				&& weightText.getText().length() > 0) {
			// get the users values from the widget references
			weight = Float.parseFloat(weightText.getText().toString());
			height = Float.parseFloat(heightText.getText().toString());

			int metricColor = ((ColorDrawable) btnMetric.getBackground())
					.getColor();
			if (metricColor == selectedColor) {
				// metric is selected
				// calculate the bmi value
				bmiValue = calculate(weight, height);

			} else {
				// imperical is selected
				bmiValue = Math
						.round(((weight * 703) / (height * height)) * 100) / 100;
			}

			// interpret the meaning of the bmi value
			String bmiInterpretation = interpretBMI(bmiValue);

			// now set the value in the result text
			resultBmiText.setText(" " + bmiValue);
			resultBmiText.setTextColor(Color.parseColor("#FF0000"));

			resultBmiCategoryText.setText(" " + bmiInterpretation);
			resultBmiCategoryText.setTextColor(Color.parseColor("#FF0000"));
		}

	}

	// the formula to calculate the BMI index
	private float calculate(float weight, float height) {
		return (float) Math.round((weight / (height * height) * 100)) / 100;
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

}
