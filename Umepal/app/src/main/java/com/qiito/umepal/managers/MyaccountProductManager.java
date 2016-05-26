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
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;

/**
 * Created by abin on 31/8/15.
 */
public class MyaccountProductManager implements ApiConstants{




    private static final String TAG = MyaccountProductManager.class.getSimpleName();

    private static MyaccountProductManager mInstance = null;

    private UserBaseHolder userBaseHolder;

        public static MyaccountProductManager getInstance() {

            if (mInstance == null) {
                mInstance = new MyaccountProductManager();
            }
            return mInstance;
        }

        /**
         *
         * @param activity
         * @param asyncTaskCallBack
         * @param session_id
         */
        public void getProducts(final Activity activity,final AsyncTaskCallBack asyncTaskCallBack,String session_id,int offset) {

            RequestParams params = new RequestParams();
            params.put(UserProfileRequestParams.SESSION, session_id);
            params.put(UserProfileRequestParams.OFFSET, offset+"");



            UMEPALAppRestClient.get(UserProfileRequestParams.USERPROFILE_URL,
                    params, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {

                            String responseBody = UtilValidate
                                    .getStringFromInputStream(new ByteArrayInputStream(bytes));
                            if (i == WebResponseConstants.ResponseCode.OK) {
                                Log.e("@@@","##responseBody#"+responseBody);
                                Gson gson = new Gson();


                                userBaseHolder = gson.fromJson(responseBody,UserBaseHolder.class);
                                if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                    asyncTaskCallBack.onFinish(i,userBaseHolder);
                                }

                            }
                            if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {


                                userBaseHolder = new UserBaseHolder();
                                Gson gson = new Gson();
                                userBaseHolder = gson.fromJson(responseBody,UserBaseHolder.class);
                                if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                    asyncTaskCallBack.onFinish(i,userBaseHolder);
                                }

                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            if (!(NetChecker.isConnected(activity))) {

                                if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {


                                    asyncTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
                                }

                            } else {
                                asyncTaskCallBack.onFinish(0, activity.getResources().getString(R.string.nointernet));
                            }
                        }
                    });
               }

    }

