package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ProductDetailsBaseHolder;
import com.qiito.umepal.holder.ProductLikeHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by abin on 29/9/15.
 */
public class ProductLikeManager implements ApiConstants{

    private static final String TAG = ProductLikeManager.class.getName();
    private static ProductLikeManager productLikeManager;
    private ProductDetailsBaseHolder productDetailsBaseHolder;
    private ProductLikeHolder productLikeHolder;


    /**
     * @return productDetailsManager instance
     */

    public static ProductLikeManager getInstance() {
        if (productLikeManager == null) {

            productLikeManager = new ProductLikeManager();
        }

        return productLikeManager;
    }


    public void setLike(final Activity activity,String product_id,String session_id,
                                  final AsyncTaskCallBack asyncTaskCallBack) {

        RequestParams params = new RequestParams();

        params.put(ProductLike.PRODUCT_ID, product_id);
        params.put(ProductLike.SESSION_ID, session_id);

        Log.e("PRoduct id", ""+product_id);
        Log.e("sesssion id",""+session_id);

        UMEPALAppRestClient.post(ProductLike.LIKE_URL, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int requestCode, Header[] headers, byte[] bytes) {


                        String responseBody = UtilValidate
                                .getStringFromInputStream(new ByteArrayInputStream(
                                        bytes));

                        Log.e(TAG, " SET LIKE RESPONSE:" + responseBody);

                        if (requestCode == WebResponseConstants.ResponseCode.OK) {
                            Gson gson = new Gson();

                            productLikeHolder=new ProductLikeHolder();
                            productLikeHolder=gson.fromJson(responseBody,ProductLikeHolder.class);

                            Log.e("","CODE>>"+productLikeHolder.getCode());


                          /*  productDetailsBaseHolder = new ProductDetailsBaseHolder();
                            productDetailsBaseHolder = gson.fromJson(responseBody, ProductDetailsBaseHolder.class);*/


                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(requestCode, productLikeHolder);


                            } else {
                                Log.e(" ", ".......call back null........");
                            }

                            /*if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(requestCode, productDetailsBaseHolder);


                            } else {
                                Log.e(" ", ".......call back null........");
                            }*/


                        } else {
                            asyncTaskCallBack.onFinish(requestCode, productLikeHolder);

                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

                                Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });
    }
}
