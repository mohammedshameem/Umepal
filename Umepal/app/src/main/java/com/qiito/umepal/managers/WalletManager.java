package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.MessageHolder;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.ProductBaseHolder;
import com.qiito.umepal.holder.ProductCategoryBaseHolder;
import com.qiito.umepal.holder.WalletBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 6/6/16.
 */
public class WalletManager implements ApiConstants {

    private static final String TAG = WalletManager.class.getSimpleName();

    private static WalletManager mInstance = null;

    private WalletBaseHolder walletBaseHolder;

    public static WalletManager getInstance() {

        if (mInstance == null) {
            mInstance = new WalletManager();
        }
        return mInstance;
    }

    public void getWalletData(final Activity activity, String sessionId, final AsyncTaskCallBack asyncTaskCallBack) {

        RequestParams params = new RequestParams();
        params.put(WalletDataParams.SESSION_ID, sessionId);

        Log.e("!!", "paramsss>>>> " + params);

        UMEPALAppRestClient.get(WalletDataParams.WALLET_DATA_URL, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e(TAG, "PRODUCT RESPONSE " + responseBody);
                            Gson gson = new Gson();
                            walletBaseHolder = new WalletBaseHolder();
                            walletBaseHolder = gson.fromJson(responseBody, WalletBaseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i, walletBaseHolder);

                            } else {
                                Log.e(" ", ".......call back null........");
                            }
                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            walletBaseHolder = new WalletBaseHolder();
                            Gson gson = new Gson();
                            walletBaseHolder = gson.fromJson(responseBody, WalletBaseHolder.class);

                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i, walletBaseHolder);
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


    public void topUp(final Activity activity, final String session_id, final String creditAmount, final AsyncTaskCallBack topUpCallBack) {


        RequestParams params = new RequestParams();
        params.put(TopUpParams.SESSION_ID, session_id);
        params.put(TopUpParams.CREDIT_AMOUNT, creditAmount);


        UMEPALAppRestClient.post(TopUpParams.TOP_UP_URL, params,
                activity, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate
                                .getStringFromInputStream(new ByteArrayInputStream(
                                        bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e("response", "PAYPAL RESPONSE>>>>" + responseBody);

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(topUpCallBack)) {
                                topUpCallBack.onFinish(i,
                                        palTransactionResponseHolder);
                            }

                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {
                            Log.e("response", "un auth RESPONSE>>>>" + responseBody);

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(topUpCallBack)) {
                                topUpCallBack.onFinish(i,
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
