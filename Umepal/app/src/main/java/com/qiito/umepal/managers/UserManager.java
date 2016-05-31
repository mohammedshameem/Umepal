package com.qiito.umepal.managers;

import java.io.ByteArrayInputStream;
import java.io.File;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ListRefereeBaseHolder;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.holder.UserLogoutHolder;
import com.qiito.umepal.holder.UserResponseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UserManager implements ApiConstants {

	private static final String TAG = "UserManager";

	private static UserManager userManager;

	private UserBaseHolder userBaseHolder;
	private UserLogoutHolder userLogoutHolder;
	private ListRefereeBaseHolder listRefereeBaseHolder;
    private UserResponseHolder userResponseHolder;
    private NotificationBaseHolder notificationBaseHolder;

	/**
	 * Default Constructor
	 */
	public UserManager() {

	}

	/**
	 * 
	 * @return userManager object
	 */

	public static UserManager getInstance() {

		if (null == userManager) {

			userManager = new UserManager();
		}
		return userManager;
	}

	// Fetching user Details
	public void userProfile(final Activity activity, String session_id,int offset,
			final AsyncTaskCallBack userProfileCallBack) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put(UserProfileRequestParams.SESSION, session_id);
		params.put(UserProfileRequestParams.OFFSET,offset+"");


		UMEPALAppRestClient.post(UserProfileRequestParams.USERPROFILE_URL, params, activity,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {


						String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));


						Log.i(TAG, "RESPONSE" + responseBody);

						Gson gson = new Gson();

						userBaseHolder = gson.fromJson(responseBody,UserBaseHolder.class);

						if (UtilValidate.isNotNull(userProfileCallBack)) {
                            userProfileCallBack.onFinish(i,userBaseHolder);


						}
					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

								Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
							}

						}
						if (UtilValidate.isNotNull(userProfileCallBack)) {

							userProfileCallBack.onFinish(1, "No Internet");
						}
					}
				});
	}

	// Fetching user Details
	public void userLogout(final Activity activity, String session, final AsyncTaskCallBack userProfileCallBack) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put(userLogoutRequestParams.SESSION_ID, session);

		UMEPALAppRestClient.post(userLogoutRequestParams.LOGOUT_URL,params,activity,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {
						String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
						Log.e("RESPONSE", "RESPONSE" + responseBody);
						Gson gson = new Gson();
						userLogoutHolder = gson.fromJson(responseBody,UserLogoutHolder.class);
						if(UtilValidate.isNotNull(userProfileCallBack)){
							userProfileCallBack.onFinish(i,userLogoutHolder);
						}
					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

								Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
							}

						}

						if (UtilValidate.isNotNull(userProfileCallBack)) {

							userProfileCallBack.onFinish(1, "No Internet");
						}
					}
				});
	}

	// Editing User Details
	public void userEditProfile(final Activity activity, String session, String firstName, String lastName, String email, String city,
			String mobile, String picturePath,final AsyncTaskCallBack userEditProfileCallBack) {
		// TODO Auto-generated method stub

		/*
		 * if (UtilValidate.isNotNull(picturePath)) {
		 * 
		 * final File file = new File(picturePath); FileBody fb = new
		 * FileBody(file);
		 * 
		 * builder.addPart(AnswerQuestionRequestParams.IMAGE_FILE, fb);
		 * 
		 * }
		 */

		RequestParams params = new RequestParams();
		params.put(UserEditProfileRequestParams.SESSION, session);
		params.put(UserEditProfileRequestParams.FIRSTNAME, firstName);
		params.put(UserEditProfileRequestParams.LASTNAME, lastName);
		params.put(UserEditProfileRequestParams.EMAIL, email);
		params.put(UserEditProfileRequestParams.CITY, city);
		params.put(UserEditProfileRequestParams.MOBILE, mobile);

		UMEPALAppRestClient.post(
				UserEditProfileRequestParams.USER_EDIT_PROFILE_URL, params,
				activity, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {
						String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
						Log.i(TAG, "RESPONSE" + responseBody);

						Gson gson = new Gson();
						userBaseHolder = gson.fromJson(responseBody,UserBaseHolder.class);

						if (UtilValidate.isNotNull(userEditProfileCallBack)) {

							userEditProfileCallBack.onFinish(i,userBaseHolder);
						}

					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

						Log.i(TAG, "USER EDIT PROFILE API CALL FAILED");
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

								Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
							}

						}

					}
				});
	}

	// Editing User Details
	public void userEditProfilewithImage(final Activity activity, String session, String firstName, String lastName, String email, String city,
			String mobile, String picturePath, final AsyncTaskCallBack userEditProfileCallBack) {
		// TODO Auto-generated method stub

		Log.e("managerPicPath>>>>>>>>",picturePath);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		if (UtilValidate.isNotNull(picturePath)) {

			final File file = new File(picturePath);
			FileBody fb = new FileBody(file);

			Log.e("", "in manager> pic>as file>>" + fb);
			builder.addPart(UserEditProfileRequestParams.PICTURE, fb);

		}

		builder.addTextBody(UserEditProfileRequestParams.SESSION, session);
		builder.addTextBody(UserEditProfileRequestParams.FIRSTNAME, firstName);
		builder.addTextBody(UserEditProfileRequestParams.LASTNAME, lastName);
		builder.addTextBody(UserEditProfileRequestParams.EMAIL, email);
		builder.addTextBody(UserEditProfileRequestParams.CITY, city);
		builder.addTextBody(UserEditProfileRequestParams.MOBILE, mobile);

		final HttpEntity httpEntity = builder.build();



		/*RequestParams params = new RequestParams();
		params.put(UserEditProfileRequestParams.SESSION,session);
		params.put(UserEditProfileRequestParams.FIRSTNAME, firstName);
		params.put(UserEditProfileRequestParams.LASTNAME, lastName);
		params.put(UserEditProfileRequestParams.EMAIL, email);
		params.put(UserEditProfileRequestParams.CITY, city);
		params.put(UserEditProfileRequestParams.MOBILE, mobile);
		if (UtilValidate.isNotNull(picturePath)) {

			final File file = new File(picturePath);
			FileBody fb = new FileBody(file);

			Log.e("", "in manager> pic>as file>>" + fb);
			params.put(UserEditProfileRequestParams.PICTURE, fb);

		}*/

		Log.e("paramsPic>>>", UserEditProfileRequestParams.PICTURE);
		//Log.e("manager params&&&&&&>>",""+params);

		UMEPALAppRestClient.post(UserEditProfileRequestParams.USER_EDIT_PROFILE_URL,httpEntity,null, activity,null,
				new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
				Log.i(TAG, "USER EDIT PROFILE API CALL SUCCESS");
				Log.i(TAG, "RESPONSE" + responseBody);


				Gson gson = new Gson();
				userBaseHolder = gson.fromJson(responseBody,UserBaseHolder.class);
				Log.e("pictureResponse@@@@@@@",userBaseHolder.getData().getUser().getProfilePic());
				if (UtilValidate.isNotNull(userEditProfileCallBack)) {

					userEditProfileCallBack.onFinish(i,userBaseHolder);
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

				Log.i(TAG, "USER EDIT PROFILE API CALL FAILED");
				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

						Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
					}

				}

			}

		});

	}
	public void ListReferees(final Activity activity, String session, final AsyncTaskCallBack listrefereesCallback) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put(userLogoutRequestParams.SESSION_ID, session);

		UMEPALAppRestClient.post(ListRefereesParams.LIST_REFEREES_URL,params,activity,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
				Log.e("RESPONSE", "RESPONSE" + responseBody);
				userBaseHolder = new UserBaseHolder();
				Gson gson = new Gson();
				listRefereeBaseHolder = gson.fromJson(responseBody,ListRefereeBaseHolder.class);
				if(UtilValidate.isNotNull(listrefereesCallback)){
					listrefereesCallback.onFinish(i,listRefereeBaseHolder);
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

						Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
					}

				}

				if (UtilValidate.isNotNull(listrefereesCallback)) {

					listrefereesCallback.onFinish(1, "No Internet");
				}
			}
		});
	}


	public void RequestforpaymentParams(final Activity activity, String reffer_id,String refferee_id,
										String membership_id,final AsyncTaskCallBack listrefereesCallback) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put(Requestforpayment.REFFER_ID, reffer_id);
		params.put(Requestforpayment.REFFEREE_ID, refferee_id);
		params.put(Requestforpayment.MEMBERSHIP_ID, membership_id);


		UMEPALAppRestClient.post(Requestforpayment.REQUEST_FOR_PAYMENT,params,activity,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
				Log.e("RESPONSE", "RESPONSE" + responseBody);
				userBaseHolder = new UserBaseHolder();
				Gson gson = new Gson();
				listRefereeBaseHolder = gson.fromJson(responseBody,ListRefereeBaseHolder.class);
				if(UtilValidate.isNotNull(listrefereesCallback)){
					listrefereesCallback.onFinish(i,listRefereeBaseHolder);
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

						Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
					}

				}

				if (UtilValidate.isNotNull(listrefereesCallback)) {

					listrefereesCallback.onFinish(1, "No Internet");
				}
			}
		});
	}


}
