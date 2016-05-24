package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ProductDetailsBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;


public class ProductDetailsManager implements ApiConstants {
	
	 private static final String TAG = ProductDetailsManager.class.getName();
	 private static ProductDetailsManager productDetailsManager;
	 private ProductDetailsBaseHolder productDetailsBaseHolder;


	 /**
	     * @return productDetailsManager instance
	     */
	 
	 public static ProductDetailsManager getInstance() {
	        if (productDetailsManager == null) {

	        	productDetailsManager = new ProductDetailsManager();
	        }

	        return productDetailsManager;
	    }
	 

	 public void getProductDetails(final Activity activity,String product_id,String session_id,
								   final AsyncTaskCallBack asyncTaskCallBack) {
	
	        RequestParams params = new RequestParams();

		 params.put(ProductDetails.PRODUCT_ID, product_id);
		 params.put(ProductDetails.SESSION_ID, session_id);



	        UMEPALAppRestClient.get(ProductDetails.PRODUCTDETAIL_URL, params, null,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int requestCode, Header[] headers, byte[] bytes) {



								String responseBody = UtilValidate
										.getStringFromInputStream(new ByteArrayInputStream(
												bytes));

								Log.e(TAG, "RESPONSE:" + responseBody);

								if (requestCode == WebResponseConstants.ResponseCode.OK) {
									Gson gson = new Gson();

									productDetailsBaseHolder = new ProductDetailsBaseHolder();
									productDetailsBaseHolder = gson.fromJson(responseBody, ProductDetailsBaseHolder.class);


									if (UtilValidate.isNotNull(asyncTaskCallBack)) {
										asyncTaskCallBack.onFinish(requestCode, productDetailsBaseHolder);


									} else {
										Log.e(" ", ".......call back null........");
									}


								} else {
									asyncTaskCallBack.onFinish(requestCode, productDetailsBaseHolder);

								}

							}

						@Override
						public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

							if (!(NetChecker.isConnected(activity))) {

								if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

									Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
								}

							}
						}
					});
	 }
	}
