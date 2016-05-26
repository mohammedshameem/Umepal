package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;

import com.qiito.umepal.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ProductBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;


import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 4/11/15.
 */
public class SearchProductManager implements ApiConstants {
    private static final String TAG = SearchProductManager.class.getSimpleName();

    private static SearchProductManager mInstance = null;

    private ProductBaseHolder productBaseHolder;

   // private ProductCategoryBaseHolder productCategoryBaseHolder;

    public static SearchProductManager getInstance() {

        if (mInstance == null) {
            mInstance = new SearchProductManager();
        }
        return mInstance;
    }

    public void getSearchProductDetails(final Activity activity,final AsyncTaskCallBack asyncTaskCallBack,String sessionId,String searchKey, int offset,int limit){

        RequestParams params = new RequestParams();

        params.put(SearchProductRequestParams.SESSION_ID,sessionId);
        params.put(SearchProductRequestParams.SEARCH_KEY,""+searchKey);
        params.put(SearchProductRequestParams.OFFSET,""+offset);
        params.put(SearchProductRequestParams.LIMIT,""+limit);

        Log.e("", "paramsss>>>> " + params);

        UMEPALAppRestClient.get(SearchProductRequestParams.SEARCH_URL, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e(TAG, "PRODUCT RESPONSE " + responseBody);
                            Gson gson = new Gson();
                            productBaseHolder = new ProductBaseHolder();
                            productBaseHolder = gson.fromJson(responseBody, ProductBaseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i, productBaseHolder);

                            } else {
                                Log.e(" ", ".......call back null........");
                            }
                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            productBaseHolder = new ProductBaseHolder();
                            Gson gson = new Gson();
                            productBaseHolder = gson.fromJson(responseBody, ProductBaseHolder.class);

                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i, productBaseHolder);
                            }

                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker
                                    .isConnectedMobile(activity))) {

                                asyncTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
                            }

                        } else {
                            asyncTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
                        }

                    }
                });
    }

}
