/**
 *
 */
package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.AdvertisementResponseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;


public class AdvertisementManager implements ApiConstants {

    private static final String TAG = AdvertisementManager.class.getName();
    private static AdvertisementManager advertisementManager;
    private AdvertisementResponseHolder responseHolder;

    /**
     * @return appointmentManager
     */
    public static AdvertisementManager getinstance() {

        if (advertisementManager == null) {
            advertisementManager = new AdvertisementManager();
        }
        return advertisementManager;

    }

    /**
     * @param activity
     * @param asynchTaskCallBack
     */
    public void getAllAds(final Activity activity, final AsyncTaskCallBack asynchTaskCallBack, final int requestcode) {

        RequestParams params = new RequestParams();

        UMEPALAppRestClient.get(AdvertisementRequestParams.ADS_END_POINT, params, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Gson gson = new Gson();


                String responseBody = UtilValidate
                        .getStringFromInputStream(new ByteArrayInputStream(
                                bytes));
                Log.e(TAG, "advertisment RESPONSE " + responseBody);
                responseHolder = gson.fromJson(responseBody,
                        AdvertisementResponseHolder.class);
                if (UtilValidate.isNotNull(asynchTaskCallBack))

                {
                    asynchTaskCallBack.onFinish(requestcode,
                            responseHolder);
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


                Log.i(TAG, "ads api  call failed!");

                if (!(NetChecker.isConnected(activity))) {

                    if (!(NetChecker.isConnectedWifi(activity) && NetChecker
                            .isConnectedMobile(activity))) {

                        Toast.makeText(
                                activity,
                                "Please check your internet connection...",
                                Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

    }


}
