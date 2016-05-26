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
import com.qiito.umepal.holder.ReviewBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by abin on 23/9/15.
 */
public class ReviewManager implements ApiConstants {

    private static final String TAG = ReviewManager.class.getSimpleName();

    private static ReviewManager mInstance = null;

    private ReviewBaseHolder reviewBaseHolder;


    public static ReviewManager getInstance() {

        if (mInstance == null) {
            mInstance = new ReviewManager();
        }
        return mInstance;
    }



    public void saveReview(final Activity activity,final AsyncTaskCallBack asynchTaskCallBack, String session_id,
                               int product_id,String review,String rating) {

        RequestParams params = new RequestParams();
        params.put(ReviewProductRequestParams.SESSION_ID, session_id);
        params.put(ReviewProductRequestParams.PRODUCT_ID, product_id);
        params.put(ReviewProductRequestParams.REVIEW, review);
        params.put(ReviewProductRequestParams.RATING, rating);


       UMEPALAppRestClient.post(ReviewProductRequestParams.REVIEW_URL,params,activity, new AsyncHttpResponseHandler() {
           @Override
           public void onSuccess(int i, Header[] headers, byte[] bytes) {
               String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

               if (i == WebResponseConstants.ResponseCode.OK) {

                   Log.e(TAG, "LOGIN RESPONSE " + responseBody);
                   Gson gson = new Gson();


                   reviewBaseHolder=new ReviewBaseHolder();
                   reviewBaseHolder = gson.fromJson(responseBody, ReviewBaseHolder.class);

                   if (UtilValidate.isNotNull(asynchTaskCallBack)) {

                       asynchTaskCallBack.onFinish(i, reviewBaseHolder);


                   }else
                   {
                       Log.e(" ",".......call back null........");}
               }
               if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {


                   reviewBaseHolder=new ReviewBaseHolder();
                   Gson gson = new Gson();
                   reviewBaseHolder = gson.fromJson(responseBody,ReviewBaseHolder.class);

                   if (UtilValidate.isNotNull(asynchTaskCallBack)) {

                       asynchTaskCallBack.onFinish(i,reviewBaseHolder);
                   }

               }


           }

           @Override
           public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
               if (!(NetChecker.isConnected(activity))) {

                   if (!(NetChecker.isConnectedWifi(activity) && NetChecker
                           .isConnectedMobile(activity))) {


                       asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
                   }

               } else {
                   asynchTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
               }
           
           }
       });

        }
    }
