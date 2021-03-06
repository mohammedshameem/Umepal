package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.MessageHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 3/6/16.
 */
public class RealTimePaymentManager implements ApiConstants {

    private static final String TAG = "RealTimePaymentManager";
    private UserBaseHolder userBaseHolder;
    private MessageHolder messageHolder;

    private static RealTimePaymentManager realTimePaymentManager;

    public static RealTimePaymentManager getInstance() {

        if (null == realTimePaymentManager) {

            realTimePaymentManager = new RealTimePaymentManager();
        }
        return realTimePaymentManager;
    }


    public void verifyMember(final Activity activity, String session_id, String member_id,
                             final AsyncTaskCallBack verifyCallBack) {
        // TODO Auto-generated method stub
        RequestParams params = new RequestParams();
        params.put(VerifyMemberParams.SESSION_ID, session_id);
        params.put(VerifyMemberParams.MEMBER_ID, member_id);


        UMEPALAppRestClient.post(VerifyMemberParams.VERIFY_MEMBER_URL, params, activity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {


                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));


                        Log.i(TAG, "RESPONSE" + responseBody);

                        Gson gson = new Gson();

                        userBaseHolder = gson.fromJson(responseBody, UserBaseHolder.class);

                        if (UtilValidate.isNotNull(verifyCallBack)) {
                            verifyCallBack.onFinish(i, userBaseHolder);


                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

                                Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                            }

                        }
                        if (UtilValidate.isNotNull(verifyCallBack)) {

                            verifyCallBack.onFinish(1, "No Internet");
                        }
                    }
                });
    }

    public void RealTimePayment(final Activity activity, final String session_id, final int member_id, final String price, final AsyncTaskCallBack paymentCallBack) {

        RequestParams params = new RequestParams();
        params.put(RealTimePaymentParams.SESSION_ID, session_id);
        params.put(RealTimePaymentParams.MEMBER_ID, member_id);
        params.put(RealTimePaymentParams.PRICE, price);

        Log.e(" Payment params >> ", ">> " + params);


        UMEPALAppRestClient.post(RealTimePaymentParams.REAL_TIME_PAYMENT_URL, params, activity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {


                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));


                        Log.i(TAG, "RESPONSE" + responseBody);

                        Gson gson = new Gson();

                        messageHolder = gson.fromJson(responseBody, MessageHolder.class);

                        if (UtilValidate.isNotNull(paymentCallBack)) {
                            paymentCallBack.onFinish(i, messageHolder);


                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

                                Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                            }

                        }
                        if (UtilValidate.isNotNull(paymentCallBack)) {

                            paymentCallBack.onFinish(1, "No Internet");
                        }
                    }
                });

    }
}
