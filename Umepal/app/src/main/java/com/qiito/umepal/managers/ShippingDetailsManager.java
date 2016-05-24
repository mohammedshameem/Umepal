package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ShippingDetailsBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by shiya on 16/12/15.
 */
public class ShippingDetailsManager implements ApiConstants {

    private static final String TAG = ShippingDetailsManager.class.getSimpleName();

    private static ShippingDetailsManager mInstance = null;

    private ShippingDetailsBaseHolder shippingDetailsBaseHolder;



    public static ShippingDetailsManager getInstance() {

        if (mInstance == null) {
            mInstance = new ShippingDetailsManager();
        }
        return mInstance;
    }

    public void postShippingDetails(Activity activity, final AsyncTaskCallBack asyncTaskCallBack,String fullName, String streetAddress,
            String unit,String country, String state,String city,String postalcode,String phone,String session_id,final  int requestCode){


        RequestParams params = new RequestParams();
        params.put(ShippingDetailsRequestParams.FULL_NAME,fullName);
        params.put(ShippingDetailsRequestParams.STREET_ADDRESS,streetAddress);
        params.put(ShippingDetailsRequestParams.CITY,city);
        params.put(ShippingDetailsRequestParams.COUNTRY,country);
        params.put(ShippingDetailsRequestParams.UNIT,unit);
        params.put(ShippingDetailsRequestParams.PHONE,phone);
        params.put(ShippingDetailsRequestParams.POSTAL_CODE,postalcode);
        params.put(ShippingDetailsRequestParams.STATE,state);
        params.put(ShippingDetailsRequestParams.SESSION_ID,session_id);

        Log.e("*******PARAMS********",""+params);

        UMEPALAppRestClient.post(ShippingDetailsRequestParams.SHIPPING_DETAILS_URL,params,activity,
                new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                Log.e("***","in success");
                shippingDetailsBaseHolder = new ShippingDetailsBaseHolder();
                Gson gson = new Gson();
                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                Log.e("RESPONSE>>>>   ",responseBody);
                shippingDetailsBaseHolder = gson.fromJson(responseBody, ShippingDetailsBaseHolder.class);
                if (UtilValidate.isNotNull(asyncTaskCallBack)){
                    asyncTaskCallBack.onFinish(requestCode,shippingDetailsBaseHolder);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                String responseBody = UtilValidate
                        .getStringFromInputStream(new ByteArrayInputStream(
                                bytes));

                Log.i(TAG, "asyncTaskCallBack on failure" + responseBody);
                if (asyncTaskCallBack == null) {
                    Log.i(TAG, "asyncTaskCallBack is null : ");
                }
                else {
                    asyncTaskCallBack.onFinish(0, responseBody);
                }

            }
        });

    }
}
