package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Constants.User;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayInputStream;
import java.io.File;


public class EditProfileManager implements ApiConstants,User {
	private static final String TAG = EditProfileManager.class.getName();
	private static EditProfileManager userManager;
	private UserBaseHolder userBaseHolder;
	/**
	 * 
	 * @return userManager instance
	 */
	public static EditProfileManager getInstance() {

		if (userManager == null) {

			userManager = new EditProfileManager();
		}

		return userManager;
	}
	public void editProfile(final Activity activity,String session_id,
			String firstname,String lastname,String email,
							String city,String mobile,String pic,
							final AsyncTaskCallBack edit_profilecallback,final int requestcode) {
		// TODO Auto-generated method stub

		RequestParams params = new RequestParams();
		params.put(UserEditProfileRequestParams.SESSION, session_id);
		params.put(UserEditProfileRequestParams.FIRSTNAME, firstname);
		params.put(UserEditProfileRequestParams.LASTNAME, lastname);
		params.put(UserEditProfileRequestParams.EMAIL, email);
		params.put(UserEditProfileRequestParams.CITY, city);
		params.put(UserEditProfileRequestParams.MOBILE, mobile);
		Log.e("", "##########" + pic);
		params.put(UserEditProfileRequestParams.PICTURE, pic);


		UMEPALAppRestClient.post(
				UserEditProfileRequestParams.USER_EDIT_PROFILE_URL, params, activity,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {


						userBaseHolder = new UserBaseHolder();
						Gson gson = new Gson();

						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(bytes));

						Log.e("@@@", "##responseBody#" + responseBody);
						userBaseHolder = gson.fromJson(responseBody,
								UserBaseHolder.class);
						if (UtilValidate.isNotNull(edit_profilecallback)) {
							edit_profilecallback.onFinish(requestcode,
									userBaseHolder);
						}
					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
						if(UtilValidate.isNetworkAvailable(activity)){
						if (bytes != null){
							String responseBody = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											bytes));

						Log.i(TAG, "asyncTaskCallBack on failure" + responseBody);
						if (edit_profilecallback == null) {
							Log.i(TAG, "asyncTaskCallBack is null : ");
						} else {
							edit_profilecallback.onFinish(0, responseBody);
						}
					}
					}
					else {
						Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
							edit_profilecallback.onFinish(0,"FAIL");
					}
					}
				});

	}


	public void userEditProfilewithImage(Activity activity,String session_id,
										 String firstname,String lastname,String email,
										 String city,String mobile,String pic,
										 final AsyncTaskCallBack edit_profilecallback,final int requestcode) {
		// TODO Auto-generated method stub
		//MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		//builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


		RequestParams params = new RequestParams();
		params.put(UserEditProfileRequestParams.SESSION, session_id);
		params.put(UserEditProfileRequestParams.FIRSTNAME, firstname);
		params.put(UserEditProfileRequestParams.LASTNAME, lastname);
		params.put(UserEditProfileRequestParams.EMAIL, email);
		params.put(UserEditProfileRequestParams.CITY, city);
		params.put(UserEditProfileRequestParams.MOBILE, mobile);

		if (UtilValidate.isNotNull(pic)) {

			final File file = new File(pic);
			FileBody fb = new FileBody(file);

			Log.e("", "in manager> pic>as file>>" + fb);

			params.put(UserEditProfileRequestParams.PICTURE, fb);
			//builder.addPart(UserEditProfileRequestParams.PICTURE, fb);

		}

		/*builder.addTextBody(UserEditProfileRequestParams.SESSION, session_id);
		builder.addTextBody(UserEditProfileRequestParams.FIRSTNAME, firstname);
		builder.addTextBody(UserEditProfileRequestParams.LASTNAME, lastname);
		builder.addTextBody(UserEditProfileRequestParams.EMAIL, email);
		builder.addTextBody(UserEditProfileRequestParams.CITY, city);
		builder.addTextBody(UserEditProfileRequestParams.MOBILE, mobile);
*/
	//	final HttpEntity httpEntity = builder.build();

		UMEPALAppRestClient.post(
				UserEditProfileRequestParams.USER_EDIT_PROFILE_URL, params,
				 activity, null, new AsyncHttpResponseHandler() {


					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {


						userBaseHolder = new UserBaseHolder();
						Gson gson = new Gson();

						String responseBody = UtilValidate
								.getStringFromInputStream(new ByteArrayInputStream(bytes));

						Log.e("@@@", "##responseBody#" + responseBody);
						userBaseHolder = gson.fromJson(responseBody,
								UserBaseHolder.class);
						if (UtilValidate.isNotNull(edit_profilecallback)) {
							edit_profilecallback.onFinish(requestcode,
									userBaseHolder);
						}
					}
					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
						if(bytes!=null) {
							String responseBody = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											bytes));

							Log.i(TAG, "asyncTaskCallBack on failure" + responseBody);
							if (edit_profilecallback == null) {
								Log.i(TAG, "asyncTaskCallBack is null : ");
							} else {
								edit_profilecallback.onFinish(i, responseBody);
							}
						}
					}
				});
	}
}
