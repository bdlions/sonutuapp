package com.sonuto.tabsswipe;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.BusinessRegistrationActivity;
import com.sportzweb.LoginActivity;
import com.sportzweb.R;
import com.sportzweb.UserProfileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsFragment extends Fragment {

	SessionManager manager;
	String[] values = new String[]{"Create Business Profile", "Account Settings", "Profile Settings", "Log out"};
	ListView lv;
	ImageView userImage;
	ISessionManager session;
	ImageLoader imageLoader;
	TextView userProfileNameTxt;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		session = new SessionManager(getActivity().getApplicationContext());

		userProfileNameTxt = (TextView) v.findViewById(R.id.userProfileNameTxt);
		userImage = (ImageView) v.findViewById(R.id.userImage);
		LinearLayout linearLayoutUserProfile = (LinearLayout) v.findViewById(R.id.linearLayoutUserProfile);
		linearLayoutUserProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), UserProfileActivity.class);
				i.putExtra("userId", session.getUserId());
				startActivity(i);
			}
		});

		imageLoader = new ImageLoader(getActivity());
		User user = new User();

		user.userProfile(new ICallBack() {

			@Override
			public void callBackResultHandler(Object object) {
				JSONObject jsonUserObj = (JSONObject) object;
				try {
					if (jsonUserObj != null) {
						JSONObject UserObj = jsonUserObj.getJSONObject("user_info");

						if (UserObj != null && UserObj.getString("first_name") != null) {
							userProfileNameTxt.setText(UserObj.getString("first_name") + " " + UserObj.getString("last_name"));
						}

						String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/profile_picture/32x32/";

						userImage.setImageResource(R.drawable.upload_img_icon);
						imageLoader.DisplayImage(imagePath + UserObj.getString("photo"), userImage);
					}

				}
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}
		}, session.getUserId());

		lv = (ListView) v.findViewById(R.id.listViewSettings);
		perform(v);
		manager = new SessionManager(getActivity());
		// when session active
		/*
		 * if(manager.getUsersBusinessProfileId() > 0){ values[0] =
		 * manager.getUsersBusinessProfileName(); }
		 */

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				String category = values[position];
				if (position == 0) {
					Intent intent = new Intent(getActivity(), BusinessRegistrationActivity.class);
					startActivity(intent);
				}
				else if (position == 3) {
					if (manager.logoutUser()) {
						Intent intent = new Intent(getActivity(), LoginActivity.class);
						startActivity(intent);
					}
				}

			}
		});

		return v;
	}

	public void perform(View v) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		lv.setAdapter(adapter);

	}
}
