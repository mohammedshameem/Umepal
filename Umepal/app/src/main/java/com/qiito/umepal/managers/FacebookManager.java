package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.Utilvalidate.Utils;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;

public class FacebookManager implements ApiConstants.Facebook {

	private static final String TAG = FacebookManager.class.getSimpleName();

	private static FacebookManager mInstance = null;

	private UserBaseHolder userBaseHolder;


	public static FacebookManager getInstance() {

		if (mInstance == null) {
			mInstance = new FacebookManager();
		}
		return mInstance;
	}


	public void callUserLoginApi(final Activity activity,
			final AsyncTaskCallBack asynchTaskCallBack, final int requestCode,
			 String accesstoken,double latitude,double longitude,String fbid) {

		userBaseHolder = new UserBaseHolder();


		RequestParams params = new RequestParams();
		params.put(FacebookRequestParams.FB_ACCESS_TOKEN, accesstoken);
		params.put(FacebookRequestParams.CURRENT_LOCATION_LAT, String.valueOf(latitude));
		params.put(FacebookRequestParams.CURRENT_LOCATION_LONG, String.valueOf(longitude));
		params.put(FacebookRequestParams.FB_ID, fbid);

		Log.e("", "fb_access_token:" + accesstoken);
		Log.e("", "latitude:" + String.valueOf(latitude));
		Log.e("", "longitude:" + String.valueOf(longitude));
		Log.e("", "fb_id:" + fbid);

		UMEPALAppRestClient.post(LOGIN_URL, params, activity,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {

						Log.e("","In manager success");

						String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
						if (i == WebResponseConstants.ResponseCode.OK) {

							try {

								if (UtilValidate.isNotNull(responseBody)) {

									JSONObject responseJSONObject = new JSONObject(responseBody);

									if (responseJSONObject.has(ApiConstants.STATUS)) {

										String status = Utils.getJSONString(responseJSONObject, ApiConstants.STATUS);


										Log.i(TAG, "Success " + status);
										Log.e("","Login Response:"+responseBody);

										Gson gson = new Gson();

										userBaseHolder = gson.fromJson(responseBody,UserBaseHolder.class);

										asynchTaskCallBack.onFinish(requestCode, userBaseHolder);


									}

								}

							} catch (Exception e) {

								Log.e(TAG, "JSONException ", e);
							}

						}
						if (i == WebResponseConstants.ResponseCode.SERVER_ERROR) {

							try {

								if (UtilValidate.isNotNull(responseBody)) {

									JSONObject responseJSONObject = new JSONObject(
											responseBody);

									if (responseJSONObject
											.has(ApiConstants.STATUS)) {

										String status = Utils.getJSONString(
												responseJSONObject,
												ApiConstants.STATUS);

										if (status
												.equalsIgnoreCase(ApiConstants.ERROR)) {

											int code = 0;

											if (responseJSONObject.has("code")) {

												code = Utils.getJSONint(
														responseJSONObject,
														"code");

											}

											if (responseJSONObject
													.has("message")) {

												Log.i(TAG,
														"Error message from server "
																+ status);
												String message = Utils
														.getJSONString(
																responseJSONObject,
																"message");

												asynchTaskCallBack.onFinish(
														code, message);
											}

										}

									}

								}

							} catch (Exception e) {

								Log.e(TAG, "JSONException ############ ", e);
							}

						}

					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {



						asynchTaskCallBack.onFinish(requestCode,
								"Server temporarily unavailable");
//						super.onFailure(throwable, content);
						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {

								Toast.makeText(
										  activity,
									"Please check your internet connection...",
										Toast.LENGTH_LONG).show();
							}
						}


					}
				});

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

