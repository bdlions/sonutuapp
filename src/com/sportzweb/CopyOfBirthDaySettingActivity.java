package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sonuto.users.Gender;
import com.sonuto.users.UserInfo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

public class CopyOfBirthDaySettingActivity extends Activity {
	private Context mContext;
	private DatePicker dp;
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	ISessionManager session;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_birth_day_setting);
		session = new SessionManager(getApplicationContext());
		dp = (DatePicker) findViewById(R.id.dpDOB);
		mContext = this;
	}

	/*
	 * Save and continue click action
	 */
	public void saveANDcontinue(View v) {
		int day = dp.getDayOfMonth();
		int month = dp.getMonth();
		int year = dp.getYear();
		Toast.makeText(
				getApplicationContext(),
				"Your date of birth: (dd/mm/yyyy)" + day + "/" + (month + 1)
						+ "/" + year, Toast.LENGTH_LONG).show();

		User user = new User();

		try {
			JSONObject jsonUser = new JSONObject();


			jsonUser.put("user_id", session.getUserId());
			jsonUser.put("dob", year + "/" + (month + 1) + "/" + day);

			user.updateDateOfBirth(new ICallBack() {
				@Override
				public void callBackResultHandler(final Object object) {
					JSONObject jsonObject = (JSONObject) object;
					try {

						Toast.makeText(getApplicationContext(),
								jsonObject.get("status").toString(),
								Toast.LENGTH_SHORT).show();

						if (jsonObject.get("status").toString()
								.equalsIgnoreCase("1")) {
							Intent intent = new Intent(mContext,
									AddProfilePictureActivity.class);
							startActivity(intent);
							finish();
							Toast.makeText(getApplicationContext(), "Success",
									Toast.LENGTH_SHORT).show();

						} else {
							// Login unsuccessful
							alert.showAlertDialog(CopyOfBirthDaySettingActivity.this,
									"User update failed..",
									"Update unsuccessfull", false);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void callBackErrorHandler(Object object) {
					// TODO Auto-generated method stub
					System.out.println(object);
				}
			}, jsonUser.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
