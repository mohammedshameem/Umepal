package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ShippingAddress;
import com.qiito.umepal.holder.ShippingDetailsBaseHolder;
import com.qiito.umepal.holder.ShoppingCartBaseHolder;
import com.qiito.umepal.holder.ShoppingCartResponseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by abin on 27/11/15.
 */
public class ShoppingCartManager implements ApiConstants {

    private static final String TAG = ShoppingCartManager.class.getSimpleName();


    private ShoppingCartBaseHolder shoppingCartBaseHolder;

    private ShoppingCartResponseHolder deleteFromCart;

    private ShippingDetailsBaseHolder shippingDetailsBaseHolder;

    private static ShoppingCartManager mInstance = null;

    public static ShoppingCartManager getInstance() {

        if (mInstance == null) {
            mInstance = new ShoppingCartManager();
        }
        return mInstance;
    }

    public void addToCart(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack,
                          String userId, String productId,String quantity,String color, String dimension,String session_id){

        RequestParams params = new RequestParams();
        params.put(ShoppingCartRequestParams.SESSION_ID,""+session_id);
        params.put(ShoppingCartRequestParams.USER_ID,""+userId);
        params.put(ShoppingCartRequestParams.PRODUCT_ID,""+productId);
        params.put(ShoppingCartRequestParams.QUANTITY,""+quantity);
        params.put(ShoppingCartRequestParams.PRODUCT_COLOR,""+color);
        params.put(ShoppingCartRequestParams.PRODUCT_DIMENSION, ""+dimension);

        UMEPALAppRestClient.post(ShoppingCartRequestParams.ADD_TO_CART_URL, params, activity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                if (i == WebResponseConstants.ResponseCode.OK) {

                    Log.e(TAG, "ADD TO CART RESPONSE " + responseBody);


                    Gson gson = new Gson();
                    ShoppingCartResponseHolder addToCart = gson.fromJson(responseBody, ShoppingCartResponseHolder.class);
                    if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                        asyncTaskCallBack.onFinish(i, addToCart);    //requestCode to be sent instead of i
                    }
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if(UtilValidate.isNetworkAvailable(activity)) {
                    if (bytes != null) {
                        Log.e("", "FAiled!!!");
                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                        if (asyncTaskCallBack == null) {
                            Log.i(TAG, "asyncTaskCallBack is null : ");
                        } else {

                            asyncTaskCallBack.onFinish(0, responseBody);
                        }
                    }
                }else {
                    Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
                    asyncTaskCallBack.onFinish(0,"FAIL");

                }
            }
        });

    }

    public void deleteFromCart(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack,String userId,String productId,String session_id){
        RequestParams params = new RequestParams();
        params.put(ShoppingCartDeleteRequestParams.USER_ID,userId);
        params.put(ShoppingCartDeleteRequestParams.PRODUCT_ID,productId);
        params.put(ShoppingCartDeleteRequestParams.SESSION_ID,session_id);


        UMEPALAppRestClient.post(ShoppingCartDeleteRequestParams.DELETE_FROM_CART_URL, params, activity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i== WebResponseConstants.ResponseCode.OK){

                            Log.e(TAG, "LOGIN RESPONSE " + responseBody);

                            Gson gson = new Gson();
                            deleteFromCart = gson.fromJson(responseBody,ShoppingCartResponseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(i, deleteFromCart);    //requestCode to be sent instead of i
                            }
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        if(UtilValidate.isNetworkAvailable(activity)){
                        if(bytes!=null) {
                            Log.e("", "FAiled!!!");
                            String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                            if (asyncTaskCallBack == null) {
                                Log.i(TAG, "asyncTaskCallBack is null : ");
                            } else {

                                asyncTaskCallBack.onFinish(0, responseBody);
                            }
                        }
                    }else {
                        Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
                        asyncTaskCallBack.onFinish(0,"FAIL");

                    }
                    }
                });
    }
    public void getfromcart(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack,String userId,String sessionId){
        RequestParams params = new RequestParams();
        params.put(GetProductsfromCartRequestParams.USER_ID,userId);
        params.put(GetProductsfromCartRequestParams.SESSION_ID,sessionId);

        UMEPALAppRestClient.get(GetProductsfromCartRequestParams.GET_PRODUCTS_FROM_CART, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e(TAG, "LOGIN RESPONSE " + responseBody);

                            Gson gson = new Gson();
                            shoppingCartBaseHolder = gson.fromJson(responseBody, ShoppingCartBaseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(i, shoppingCartBaseHolder);    //requestCode to be sent instead of i
                            }
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        if(UtilValidate.isNetworkAvailable(activity)) {
                            if (bytes != null) {
                                Log.e("", "FAiled!!!");
                                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                                if (asyncTaskCallBack == null) {
                                    Log.i(TAG, "asyncTaskCallBack is null : ");
                                } else {

                                    asyncTaskCallBack.onFinish(0, responseBody);
                                }
                            }
                        }else {
                            Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
                            asyncTaskCallBack.onFinish(0,"FAIL");

                        }
                    }
                });
    }

    public void ShippingAddressDetails( final Activity activity, final AsyncTaskCallBack asyncTaskCallBack,String session_id,ShippingAddress shippingAddress){
        RequestParams params = new RequestParams();
        params.put(ShippingDetailsRequestParams.SESSION_ID,session_id);
        params.put(ShippingDetailsRequestParams.FULL_NAME,shippingAddress.getShippingfullname());
        params.put(ShippingDetailsRequestParams.STREET_ADDRESS,shippingAddress.getShippingstreetaddress());
        params.put(ShippingDetailsRequestParams.UNIT,shippingAddress.getShippingunit());
        params.put(ShippingDetailsRequestParams.COUNTRY,shippingAddress.getShippingcountry());
        params.put(ShippingDetailsRequestParams.STATE,shippingAddress.getShippingstate());
        params.put(ShippingDetailsRequestParams.CITY,shippingAddress.getShippingcity());
        params.put(ShippingDetailsRequestParams.POSTAL_CODE,shippingAddress.getShippingpostalcode());
        params.put(ShippingDetailsRequestParams.PHONE,shippingAddress.getShippingphone());


        UMEPALAppRestClient.post(ShippingDetailsRequestParams.SHIPPING_DETAILS_URL, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e(TAG, "SHIPPING RESPONSE " + responseBody);

                            Gson gson = new Gson();
                            shippingDetailsBaseHolder = gson.fromJson(responseBody, ShippingDetailsBaseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(i, shippingDetailsBaseHolder);    //requestCode to be sent instead of i
                            }
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        if(UtilValidate.isNetworkAvailable(activity)) {
                            if (bytes != null) {
                                Log.e("", "FAiled!!!");
                                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                                if (asyncTaskCallBack == null) {
                                    Log.i(TAG, "asyncTaskCallBack is null : ");
                                } else {

                                    asyncTaskCallBack.onFinish(0, responseBody);
                                }
                            }
                        }else {

                            Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
                            asyncTaskCallBack.onFinish(0,"FAIL");
                        }
                    }
                });
    }


}
