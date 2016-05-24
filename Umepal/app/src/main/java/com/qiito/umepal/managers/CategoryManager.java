package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.CategoryResponseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;


public class CategoryManager implements ApiConstants {
	
	
	private static final String TAG = CategoryManager.class.getSimpleName();

	private static CategoryManager mInstance = null;
	
	private CategoryResponseHolder categoryResponseHolder;



	public static CategoryManager getInstance() {

		if (mInstance == null) {
			mInstance = new CategoryManager();
		}
		return mInstance;
	}

	/**
	 * 
	 * @param activity
	 * @param asynchTaskCallBack
	 * @param session_id
	 */
	public void getAllCategories(final Activity activity,
			final AsyncTaskCallBack asynchTaskCallBack,String session_id,final int requestcode){
		
		RequestParams params = new RequestParams();
		params.put(Categories.SESSION_ID, session_id);


		UMEPALAppRestClient.post(Categories.GET_CATEGORY_URL, params, activity,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int i, Header[] headers, byte[] bytes) {


						try {

							String responseBody = UtilValidate
									.getStringFromInputStream(new ByteArrayInputStream(
											bytes));



								Gson gson = new Gson();

								categoryResponseHolder = gson.fromJson(responseBody, CategoryResponseHolder.class);

								asynchTaskCallBack.onFinish(requestcode, categoryResponseHolder);




						} catch (Exception e) {

							Log.e(TAG, "JSONException ", e);
						}


					}

					@Override
					public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


						//asynchTaskCallBack.onFinish(1, "No Internet");

						if (!(NetChecker.isConnected(activity))) {

							if (!(NetChecker.isConnectedWifi(activity) && NetChecker
									.isConnectedMobile(activity))) {


								//asynchTaskCallBack.onFinish(1, "No Internet");

							/*	Toast.makeText(
										activity,
										"Please check your internet connection...",
										Toast.LENGTH_LONG).show();*/
							}

						} else {

							asynchTaskCallBack.onFinish(1, "No Internet");
						}


					}
				});


	}
}
