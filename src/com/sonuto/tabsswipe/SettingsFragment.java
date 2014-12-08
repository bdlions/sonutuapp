package com.sonuto.tabsswipe;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.helper.ListViewHelper;
import com.bdlions.load.image.ImageLoader;
import com.google.gson.Gson;
import com.sonuto.Config;
import com.sonuto.accountsettings.AccountSettingsActivity;
import com.sonuto.businessprofile.BusinessProfileActivity;
import com.sonuto.businessprofile.BusinessRegistrationActivity;
import com.sonuto.privecysettings.PrivecySettingsActivity;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.BusinessProfile;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import com.sportzweb.ActivitySearch;
import com.sportzweb.LoginActivity;
import com.sportzweb.R;
import com.sportzweb.UserProfileActivity;
import com.sportzweb.JSONObjectModel.StatusInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	String[] values = new String[]{"Create Business Profile", "Account Settings", "Profile Settings", "Log out"};
	ListView lvSettings;
	ImageView userImage;
	ImageLoader imageLoader;
	TextView userProfileNameTxt;
	JSONObject bObject;
	private ArrayAdapter<String> adapter;
	ProgressDialog pDialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_settings, container, false);
		setHasOptionsMenu(true);
		
		userProfileNameTxt = (TextView) v.findViewById(R.id.userProfileNameTxt);
		userImage = (ImageView) v.findViewById(R.id.userImage);
		LinearLayout linearLayoutUserProfile = (LinearLayout) v.findViewById(R.id.linearLayoutUserProfile);
		linearLayoutUserProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(getActivity(), UserProfileActivity.class);
				i.putExtra("userId", SessionManager.getInstance().getUserId());
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

						String imagePath = Config.PROFILE_PIC_DIR_MD;

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
		}, SessionManager.getInstance().getUserId());

		lvSettings = (ListView) v.findViewById(R.id.listViewSettings);
		adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
		lvSettings.setAdapter(adapter);
		ListViewHelper.setFullHeightListView(lvSettings);
		// when session active
		if(SessionManager.getInstance().getIsBusinessProfile()){
			if(SessionManager.getInstance().getUsersBusinessProfileId() > 0){
				  values[0] = SessionManager.getInstance().getUsersBusinessProfileName();
			}
		}

		lvSettings.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				if (position == 0) {
					if(SessionManager.getInstance().getIsBusinessProfile() && SessionManager.getInstance().getUsersBusinessProfileId()>0){
						pDialog = new ProgressDialog(getActivity());
						pDialog.setMessage("Loading Business Profile Info..");
						pDialog.setCancelable(false);
						pDialog.show();
						new BusinessProfile().getBusinessProfileInfo(new ICallBack() {						
							@Override
							public void callBackResultHandler(Object object) {
								JSONObject jsonObject = (JSONObject)object;
								
								try {
									bObject = jsonObject.getJSONObject("business_profile_info");
									Intent i = new Intent(getActivity(), BusinessProfileActivity.class);
									i.putExtra("business_profile_info", bObject.toString());
									startActivity(i);
								} catch (JSONException e) {
									e.printStackTrace();
								}
								pDialog.dismiss();
							}
							
							@Override
							public void callBackErrorHandler(Object object) {
								// TODO Auto-generated method stub
								pDialog.dismiss();
							}
						}, SessionManager.getInstance().getUserId());
					} 
					else {
						Intent intent = new Intent(getActivity(), BusinessRegistrationActivity.class);
						//startActivity(intent);
						//getActivity().finish();
						
						startActivityForResult(intent, 1);
					}
					
				}
				else if (position == 1) {
					Intent i = new Intent(getActivity(), AccountSettingsActivity.class);
					i.putExtra("user_id", SessionManager.getInstance().getUserId());
					startActivity(i);
					//getActivity().finish();

				}else if (position == 2) {
					Intent i = new Intent(getActivity(), PrivecySettingsActivity.class);
					i.putExtra("user_id", SessionManager.getInstance().getUserId());
					startActivity(i);
					//getActivity().finish();
				}
				else if (position == 3) {
					if (SessionManager.getInstance().logoutUser()) {
						Intent intent = new Intent(getActivity(), LoginActivity.class);
						startActivity(intent);
						getActivity().finish();
					}
				}
			}
		});

		return v;
	}
	@Override 
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
	    menu.findItem(R.id.action_post_status).setVisible(false);
	    super.onCreateOptionsMenu(menu, inflater);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_search){
			Intent searchIntent = new Intent(getActivity(), ActivitySearch.class);
			startActivity(searchIntent);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == 1 && getActivity().RESULT_OK == resultCode){
			values[0] = SessionManager.getInstance().getUsersBusinessProfileName();
			//Gson gson = new Gson();
			
			//StatusInfo statusInfo = gson.fromJson(data.getStringExtra("statusInfo"), StatusInfo.class);
			
			//adapter.addItemAt(0,statusInfo);
			adapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
