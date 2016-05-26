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
import com.qiito.umepal.holder.StoreDetailBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 8/10/15.
 */
public class StoreRatingDetailManager implements ApiConstants {

    private StoreDetailBaseHolder storeDetailBaseHolder;

    private static final String TAG = StoreRatingDetailManager.class.getSimpleName();

    private static StoreRatingDetailManager mInstance = null;

    public static StoreRatingDetailManager getInstance() {

        if (mInstance == null) {
            mInstance = new StoreRatingDetailManager();
        }
        return mInstance;
    }

    public void getRatingDetail(final Activity activity,
            final AsyncTaskCallBack asyncTaskCallBack,String sessionId,int storeid){

        RequestParams params = new RequestParams();
        params.put(StoreRatingRequestParams.SESSION_ID,sessionId);
        params.put(StoreRatingRequestParams.STORE_ID,storeid);

        UMEPALAppRestClient.get(StoreRatingRequestParams.STORE_RATING_URL, params, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                if (i == WebResponseConstants.ResponseCode.OK) {

                    Log.e(TAG, "RATING RESPONSE " + responseBody);
                    Gson gson = new Gson();
                    storeDetailBaseHolder = new StoreDetailBaseHolder();
                    storeDetailBaseHolder = gson.fromJson(responseBody, StoreDetailBaseHolder.class);
                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                        asyncTaskCallBack.onFinish(i, storeDetailBaseHolder);


                    } else {
                        Log.e(" ", ".......call back null........");
                    }
                }
                if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                    storeDetailBaseHolder = new StoreDetailBaseHolder();
                    Gson gson = new Gson();
                    storeDetailBaseHolder = gson.fromJson(responseBody, StoreDetailBaseHolder.class);

                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                        asyncTaskCallBack.onFinish(i, storeDetailBaseHolder);
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

    /*public void setStoreRating(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack, String rating,int storeid){

        RequestParams params = new RequestParams();
        params.put(StoreRatingRequestParams.RATING,rating);
        params.put(StoreRatingRequestParams.STORE_ID,storeid);

        UMEPALAppRestClient.post(StoreRatingRequestParams.STORE_RATING_URL, params, activity, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }*/
}
