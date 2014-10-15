package com.sportzweb;



import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sonuto.session.SessionManager;
import com.sonuto.users.UserInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AddProfilePictureActivity extends Activity {
	private Context mContext;
	private String upLoadServerUri;
	ProgressDialog dialog = null;
	private String imagepath = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_picture);
        
        upLoadServerUri = "http://31.222.168.64:8084/service/media/upload_profile_picture";	
        //upLoadServerUri = "http://192.168.0.101/sportzweb/service/media/upload_profile_picture";
        mContext = this;       
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            imagepath = getPath(selectedImageUri);
            ImageView imageview = (ImageView) findViewById(R.id.imageView1);
            imageview.setImageURI(selectedImageUri);
		}
	}
	
	public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
	
	/*
	 * Save and continue click action
	 */
	public void saveANDcontinue(View v) {
		dialog = ProgressDialog.show(AddProfilePictureActivity.this, "", "Uploading file...", true);
		 //messageText.setText("uploading started.....");
		 new Thread(new Runnable() {
           public void run() {
                               
                //uploadFile(imagepath);
          	 try {
          		 uploadFile(imagepath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                                        
           }
         }).start(); 
		
	}
	
	
	public void browseFile(View v){
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
	}

	
	private void uploadFile(String filePath) {

		InputStream inputStream;
		try {
			File file = new File(filePath);
			inputStream = new FileInputStream(file);
			byte[] data;
			try {
				data = IOUtils.toByteArray(inputStream);

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(upLoadServerUri);

				InputStreamBody inputStreamBody = new InputStreamBody(new ByteArrayInputStream(data), file.getName());
				
				MultipartEntityBuilder builder = MultipartEntityBuilder.create();        

				builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				builder.addPart("userfile", inputStreamBody); 

				SessionManager manager = new SessionManager(getApplicationContext());
				
				builder.addPart("name", new StringBody("Test", ContentType.TEXT_PLAIN));
				builder.addPart("user_id", new StringBody(Integer.toString(manager.getUserId()), ContentType.TEXT_PLAIN));
				builder.addPart("data", new StringBody("This is test report", ContentType.TEXT_PLAIN));
		        
				HttpEntity entity = builder.build();
				
				httpPost.setEntity(entity);

				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				HttpEntity resEntity = httpResponse.getEntity();
				String response = EntityUtils.toString(resEntity);

				JSONArray jsonarray = new JSONArray("[" + response + "]");
				JSONObject jsonobject = jsonarray.getJSONObject(0);
				
				
				final String msg = (jsonobject.getString("message"));
				
				runOnUiThread(new Runnable() {
					public void run() {
						//messageText.setText(msg);
						TextView textViewMessage = (TextView)findViewById(R.id.textViewMessage);
						textViewMessage.setText(msg);
					}
				});
				dialog.dismiss();
				
				// Close the connection
				httpClient.getConnectionManager().shutdown();
				
				Intent intent = new Intent(mContext, NewsFeedActivity.class);
				startActivity(intent);
				finish();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}
