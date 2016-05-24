package com.qiito.umepal.managers;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class PaypalManager implements ApiConstants {

    private static final String TAG = PaypalManager.class.getName();
    private static PaypalManager paypalManager;


    public static PaypalManager getInstance() {

        if (paypalManager == null) {

            paypalManager = new PaypalManager();
        }

        return paypalManager;
    }


    public void getPayPalTransactionDetails(final Activity activity,String session_id, List<String> product_ids,
                    List<String> product_quantities, List<String> colours, List<String> dimen, final AsyncTaskCallBack callBack) {

        Log.e("", "in pay pal manager");
        RequestParams params = new RequestParams();
        params.put(PayPalRequestParams.SESSION_ID, session_id);
        params.put(PayPalRequestParams.PRODUCT_IDS, toJsonArray(product_ids).toString());
        params.put(PayPalRequestParams.PRODUCT_QUANTITIES, toJsonArray(product_quantities).toString());
        params.put(PayPalRequestParams.PRODUCT_COLOR, toJsonArray(colours).toString());
        params.put(PayPalRequestParams.PRODUCT_DIMENSION, toJsonArray(dimen).toString());

        Log.i(TAG, "PAYPAL SESSIONID" + session_id);
        Log.i(TAG, "PAYPAL PRODUCT_IDS" + product_ids);
        Log.i(TAG, "PAYPAL PRODUCT_QUANTITIES" + product_quantities);
        Log.e(TAG,"Params>> "+params);

        UMEPALAppRestClient.post(
                PayPalRequestParams.PAYPAL_PURCHASE_URL, params,
                activity, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate
                                .getStringFromInputStream(new ByteArrayInputStream(
                                        bytes));


                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e("","PAYPAL RESPONSE>>>>"+responseBody);

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(callBack)) {
                                callBack.onFinish(i,
                                        palTransactionResponseHolder);
                            }

                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(callBack)) {
                                callBack.onFinish(i,
                                        palTransactionResponseHolder);
                            }

                        }


                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


//						if (TodaysParentLog.IS_INFO_ENABLED)
//							Log.i(TAG, "paypal transaction api call FAILED!!! " + content);

                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker
                                    .isConnectedMobile(activity))) {

                                Toast.makeText(
                                        activity,
                                        "please check your internet connection",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

    }


    /**
     * @param stringArray
     * @return jArray
     */
    private JSONArray toJsonArray(List<String> stringArray) {


        JSONArray jArray = new JSONArray();

        for (String items : stringArray) {

            jArray.put(items);

        }

        Log.v(TAG, "JSON ARRAY ######## : " + jArray.toString());

        return jArray;


    }
    public void joinviaPaypal(final Activity activity,
                              String session_id, final AsyncTaskCallBack callBack) {

        RequestParams params = new RequestParams();
        params.put(JoinMembershipRequestParams.SESSION_ID, session_id);

        UMEPALAppRestClient.post(
                JoinMembershipRequestParams.JOIN_MEMBERSHIP, params,
                activity, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate
                                .getStringFromInputStream(new ByteArrayInputStream(
                                        bytes));


                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e("", "PAYPAL RESPONSE>>>>" + responseBody);

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(callBack)) {
                                callBack.onFinish(i,
                                        palTransactionResponseHolder);
                            }

                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(callBack)) {
                                callBack.onFinish(i,
                                        palTransactionResponseHolder);
                            }

                        }


                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker
                                    .isConnectedMobile(activity))) {

                                Toast.makeText(
                                        activity,
                                        "please check your internet connection",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                });

    }
}
