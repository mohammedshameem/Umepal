package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.UtilValidate;

import com.qiito.umepal.holder.StoreRatingBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 9/10/15.
 */
public class StoreRatingManager implements ApiConstants {

    private StoreRatingBaseHolder storeRatingBaseHolder;

    private static final String TAG = StoreRatingManager.class.getSimpleName();

    private static StoreRatingManager mInstance = null;

    public static StoreRatingManager getInstance() {

        if (mInstance == null) {
            mInstance = new StoreRatingManager();
        }
        return mInstance;
    }

    public void setRating(final Activity activity,final AsyncTaskCallBack asyncTaskCallBack,final String sessionid, final String storeid,final String rating){

        RequestParams params = new RequestParams();
        params.put(StoreRatingParams.SESSION_ID,sessionid);
        params.put(StoreRatingParams.STORE_ID,storeid);
        params.put(StoreRatingParams.RATING,rating);

        UMEPALAppRestClient.post(StoreRatingParams.RATING_URL, params, activity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                if (i == WebResponseConstants.ResponseCode.OK) {

                    Log.e(TAG, "RATING RESPONSE " + responseBody);
                    Gson gson = new Gson();
                    storeRatingBaseHolder = new StoreRatingBaseHolder();
                    storeRatingBaseHolder = gson.fromJson(responseBody, StoreRatingBaseHolder.class);
                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                        asyncTaskCallBack.onFinish(i, storeRatingBaseHolder);


                    } else {
                        Log.e(" ", ".......call back null........");
                    }
                }
                if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                    storeRatingBaseHolder = new StoreRatingBaseHolder();
                    Gson gson = new Gson();
                    storeRatingBaseHolder = gson.fromJson(responseBody, StoreRatingBaseHolder.class);

                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                        asyncTaskCallBack.onFinish(i, storeRatingBaseHolder);
                    }

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Log.e("","failed");

            }
        });
    }




}
