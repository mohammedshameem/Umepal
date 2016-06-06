package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
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

    public void getWalletData(final Activity activity, String sessionId, final AsyncTaskCallBack asyncTaskCallBack){

        RequestParams params = new RequestParams();
        params.put(WalletDataParams.SESSION_ID,sessionId);

        Log.e("!!","paramsss>>>> "+params);

        UMEPALAppRestClient.get(WalletDataParams.WALLET_DATA_URL, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e(TAG, "PRODUCT RESPONSE " + responseBody);
                            Gson gson = new Gson();
                            walletBaseHolder = new WalletBaseHolder();
                            walletBaseHolder = gson.fromJson(responseBody,WalletBaseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i, walletBaseHolder);

                            }else{
                                Log.e(" ",".......call back null........");
                            }
                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            walletBaseHolder = new WalletBaseHolder();
                            Gson gson = new Gson();
                            walletBaseHolder = gson.fromJson(responseBody,WalletBaseHolder.class);

                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i,walletBaseHolder);
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
