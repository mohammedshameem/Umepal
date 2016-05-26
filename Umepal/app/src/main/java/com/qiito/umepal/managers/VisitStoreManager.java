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
import com.qiito.umepal.holder.ProductCategoryBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 29/9/15.
 */
public class VisitStoreManager implements ApiConstants {

    private static final String TAG = VisitStoreManager.class.getSimpleName();

    private static VisitStoreManager mInstance = null;

    private ProductBaseHolder productBaseHolder;

    private ProductCategoryBaseHolder productCategoryBaseHolder;

    public static VisitStoreManager getInstance() {

        if (mInstance == null) {
            mInstance = new VisitStoreManager();
        }
        return mInstance;
    }

    public void getStoreDetails(final Activity activity,final AsyncTaskCallBack asyncTaskCallBack,String sessionId,int offset,int storeid){

        RequestParams params = new RequestParams();
        params.put(StoreDetailRequestParams.OFFSET,""+offset);
        params.put(StoreDetailRequestParams.SESSION_ID,sessionId);
        params.put(StoreDetailRequestParams.STORE_ID,""+storeid);

        Log.e("!!","paramsss>>>> "+params);

        UMEPALAppRestClient.get(StoreDetailRequestParams.STORE_DETAIL_URL, params, null,
                new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                if (i == WebResponseConstants.ResponseCode.OK) {

                    Log.e(TAG, "PRODUCT RESPONSE " + responseBody);
                    Gson gson = new Gson();
                    productBaseHolder = new ProductBaseHolder();
                    productBaseHolder = gson.fromJson(responseBody,ProductBaseHolder.class);
                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                        asyncTaskCallBack.onFinish(i, productBaseHolder);

                    }else{
                        Log.e(" ",".......call back null........");
                    }
                }
                if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                    productBaseHolder = new ProductBaseHolder();
                    Gson gson = new Gson();
                    productBaseHolder = gson.fromJson(responseBody,ProductBaseHolder.class);

                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                        asyncTaskCallBack.onFinish(i,productBaseHolder);
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
