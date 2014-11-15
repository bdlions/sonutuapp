package com.sportzweb;

import org.json.JSONException;
import org.json.JSONObject;

import com.bdlions.load.image.ImageLoader;
import com.sampanit.sonutoapp.utils.AlertDialogManager;
import com.sonuto.Config;
import com.sonuto.rpc.ICallBack;
import com.sonuto.rpc.register.User;
import com.sonuto.session.ISessionManager;
import com.sonuto.session.SessionManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfileActivity extends Activity {
	// Alert Dialog Manager
	AlertDialogManager alert = new AlertDialogManager();
	Button btnEditProfile;
	public ImageLoader imageLoader;
	private Context mContext;
	ISessionManager session;
	int userId;
	ImageView userProfileImage, myFollowingsImageView, myPhotosImageView,
			myInfoImageView;
	TextView userProfileName, userAddress, userProfession;

	private void initUI() {
		this.imageLoader = new ImageLoader(this.mContext);

		this.userProfileImage = (ImageView) findViewById(R.id.userProfileImage);
		this.myInfoImageView = (ImageView) findViewById(R.id.myInfoImageView);
		this.myPhotosImageView = (ImageView) findViewById(R.id.myPhotosImageView);
		this.myFollowingsImageView = (ImageView) findViewById(R.id.myFollowingsImageView);
		this.btnEditProfile = (Button) findViewById(R.id.btnEditProfile);

		this.userProfileName = (TextView) findViewById(R.id.userProfileName);
		this.userAddress = (TextView) findViewById(R.id.userAddress);
		this.userProfession = (TextView) findViewById(R.id.userProfession);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		this.mContext = this;
		// Session Manager
		this.session = new SessionManager(getApplicationContext());
		this.userId = this.session.getUserId();

		initUI();
		process();
	}

	private void process() {
		final User user = new User();

		user.userProfile(new ICallBack() {

			@Override
			public void callBackErrorHandler(Object object) {
				// TODO Auto-generated method stub

			}

			@Override
			public void callBackResultHandler(Object object) {
				final JSONObject jsonUserObj = (JSONObject) object;
				try {
					// JSONArray user_info_array =
					// jsonUserObj.getJSONArray("user_info");
					// Gson gson = new Gson();
					// System.out.print(user_info_array);
					final JSONObject UserObj = jsonUserObj.getJSONObject("user_info");

					if ((UserObj.getString("last_name") != null) | (UserObj.getString("last_name").length() != 0)) {
						UserProfileActivity.this.userProfileName.setText(UserObj.getString("first_name") + " " + UserObj.getString("last_name"));
					}
					else {
						UserProfileActivity.this.userProfileName.setText(UserObj.getString("first_name"));
					}

					if ((UserObj.getString("address") != null) | (UserObj.getString("address").length() != 0)) {
						UserProfileActivity.this.userAddress.setText(UserObj.getString("address"));
					}
					else {
						UserProfileActivity.this.userAddress.setText(" ");
					}

					if ((UserObj.getString("occupation") != null) | (UserObj.getString("occupation").length() != 0)) {
						UserProfileActivity.this.userProfession.setText(UserObj.getString("occupation"));
					}
					else {
						UserProfileActivity.this.userProfession.setText(" ");
					}

					final String imagePath = Config.SERVER_ROOT_URL + "resources/uploads/profile_picture/";

					UserProfileActivity.this.userProfileImage.setImageResource(R.drawable.upload_img_icon);
					if ((UserObj.getString("photo") != null) | (UserObj.getString("photo").length() != 0)) {
						UserProfileActivity.this.imageLoader.DisplayImage(imagePath + UserObj.getString("photo"), UserProfileActivity.this.userProfileImage);
					}

				}
				catch (final JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, this.userId);
	}

}
