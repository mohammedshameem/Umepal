package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ProductBaseHolder;
import com.qiito.umepal.holder.ProductCategoryBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;


public class ProductManager implements ApiConstants {

	private static final String TAG = CategoryManager.class.getSimpleName();

	private static ProductManager mInstance = null;

	private ProductBaseHolder productBaseHolder;

	private ProductCategoryBaseHolder productCategoryBaseHolder;

	public static ProductManager getInstance() {

		if (mInstance == null) {
			mInstance = new ProductManager();
		}
		return mInstance;
	}

	/**
	 * 
	 * @param activity
	 * @param asynchTaskCallBack
	 * @param session_id
	 */
	public void getAllProducts(final Activity activity,final AsyncTaskCallBack asynchTaskCallBack, String session_id,
			int categoryid, int subcategoryid,int offset,int limit) {

		RequestParams params = new RequestParams();
		params.put(Products.SESSION_ID, session_id);
		params.put(Products.CATEGORY_ID, ""+categoryid);
		params.put(Products.SUB_CATEGORY_ID, ""+subcategoryid);
		params.put(Products.OFFSET, offset+"");
		params.put(Products.LIMIT, limit+"");

		// catgory_id,sub_catgory_id,offset


		UMEPALAppRestClient.get(Products.GET_PRODUCTS, params, null, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {

				String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

				if (i == WebResponseConstants.ResponseCode.OK) {

					Log.e(TAG, "PRODUCT RESPONSE "+responseBody);
					Gson gson = new Gson();
					productBaseHolder = new ProductBaseHolder();
					productBaseHolder = gson.fromJson(responseBody,ProductBaseHolder.class);
					if (UtilValidate.isNotNull(asynchTaskCallBack)) {

						asynchTaskCallBack.onFinish(i, productBaseHolder);

					}else{
						Log.e(" ",".......call back null........");
					}
				}
				if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

					productBaseHolder = new ProductBaseHolder();
					Gson gson = new Gson();
					productBaseHolder = gson.fromJson(responseBody,ProductBaseHolder.class);

					if (UtilValidate.isNotNull(asynchTaskCallBack)) {

						asynchTaskCallBack.onFinish(i,productBaseHolder);
					}
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


				if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

						Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
					}

				}
               /* if(i==WebResponseConstants.ResponseCode.UN_SUCCESSFULL){

                }else {
//                    asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));



                    if(NetChecker.isConnected(activity)){

                    }else {
                        asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));

                    }


                }*/


                       /* if(NetChecker.isConnected(activity)){

                        }else {
                            asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));

                        }*/
				/*if (!(NetChecker.isConnected(activity))) {

					if (!(NetChecker.isConnectedWifi(activity) && NetChecker
							.isConnectedMobile(activity))) {

						asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
					}

				} else {
					asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));

				}*/
			}
		});
	}
}
