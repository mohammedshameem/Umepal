package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ProductCategoryBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by abin on 2/9/15.
 */




    public class ProductCategoryManager implements ApiConstants {

        private static final String TAG = ProductCategoryManager.class.getName();
        private static ProductCategoryManager productCategoryManager;
        private ProductCategoryBaseHolder productCategoryBaseHolder;

        /**
         * @return productDetailsManager instance
         */

        public static ProductCategoryManager getInstance() {
            if (productCategoryManager == null) {

                productCategoryManager = new ProductCategoryManager();
            }

            return productCategoryManager;
        }


        public void getProductCategory(final Activity activity, String session_id,
                                      final AsyncTaskCallBack asyncTaskCallBack, final int requestCode) {

            RequestParams params = new RequestParams();

            params.put(ProductCategories.SESSION_ID, session_id);

            UMEPALAppRestClient.get(ProductCategories.GET_CATEGORY, params, null,
                    new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {

                            String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                            Log.i(TAG, "RESPONSE:" + responseBody);

                            if (i == WebResponseConstants.ResponseCode.OK) {
                                Gson gson = new Gson();
                                productCategoryBaseHolder=new ProductCategoryBaseHolder();
                                productCategoryBaseHolder=gson.fromJson(responseBody,ProductCategoryBaseHolder.class);

                                Log.e(TAG, "LOGIN RESPONSE " + responseBody);
                                if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                    asyncTaskCallBack.onFinish(i, productCategoryBaseHolder);
                                }


                            } else {
                                asyncTaskCallBack.onFinish(i, productCategoryBaseHolder);
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




