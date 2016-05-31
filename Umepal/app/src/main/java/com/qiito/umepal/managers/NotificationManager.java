package com.qiito.umepal.managers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;


public class NotificationManager implements ApiConstants {

    private static final String TAG = CategoryManager.class.getSimpleName();

    private static NotificationManager mInstance = null;

    private NotificationBaseHolder notificationBaseHolder;

    private UserBaseHolder userBaseHolder;


    public static NotificationManager getInstance() {

        if (mInstance == null) {
            mInstance = new NotificationManager();
        }
        return mInstance;
    }


    /**
     * @param activity
     * @param asyncTaskCallBack
     * @param session_id
     */
    public void getAllNotification(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack, String session_id) {

        RequestParams params = new RequestParams();
        params.put(NotificationRequestParams.SESSION_ID, session_id);

       // Log.e("", "get notification" + params);

        // catgory_id,sub_catgory_id,offset


        UMEPALAppRestClient.get(NotificationRequestParams.NOTIFICATION_URL, params, null,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {


                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            //Log.e(TAG, "Notification RESPONSE  " + responseBody);


                            Gson gson = new Gson();
                            notificationBaseHolder = new NotificationBaseHolder();
                            notificationBaseHolder = gson.fromJson(responseBody, NotificationBaseHolder.class);

                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                                asyncTaskCallBack.onFinish(i, notificationBaseHolder);
                            }

                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            Log.e(TAG, "unauthorise in manager" + i);

                            notificationBaseHolder = new NotificationBaseHolder();
                            Gson gson = new Gson();
                            notificationBaseHolder = gson.fromJson(responseBody, NotificationBaseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(i, notificationBaseHolder);
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
	
/*	RequestParams params = new RequestParams();
	params.put(NotificationDeleteRequestParams.SESSION_ID, session_id);
	params.put(NotificationDeleteRequestParams.NOTIFICATION_ID, notificationid);
*/

    public void deleteSingleNotification(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack,
                                         String session_id, String notificationid) {
        // TODO Auto-generated method stub

        Log.e("", "manager session id>>" + session_id);

        RequestParams params = new RequestParams();
        params.put(NotificationDeleteRequestParams.SESSION_ID, session_id);
        params.put(NotificationDeleteRequestParams.NOTIFICATION_ID, notificationid);

        Log.e("", "manager params>>>>>>>" + params);

        UMEPALAppRestClient.post(NotificationDeleteRequestParams.NOTIFICATION_DELETE_URL, params, activity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {

                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                Log.i(TAG, "deleteNotification API CALL SUCCESS");
                Log.i(TAG, "RESPONSE" + responseBody);

                Gson gson = new Gson();
                notificationBaseHolder = new NotificationBaseHolder();
                notificationBaseHolder = gson.fromJson(responseBody, NotificationBaseHolder.class);
                if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                    asyncTaskCallBack.onFinish(i, notificationBaseHolder);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Log.i(TAG, "deleteSingleNotification API CALL FAILED");

            }
        });
    }


    public void deleteAllNotification(final Activity activity, final AsyncTaskCallBack asyncTaskCallBack, String session_id) {
        // TODO Auto-generated method stub

        RequestParams params = new RequestParams();
        params.put(NotificationDeleteRequestParams.SESSION_ID, session_id);
        params.put(NotificationDeleteRequestParams.NOTIFICATION_ID, "");

        UMEPALAppRestClient.post(NotificationDeleteRequestParams.NOTIFICATION_DELETE_URL, params, activity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        Log.i(TAG, "deleteNotification API CALL SUCCESS");
                        Log.i(TAG, "RESPONSE" + responseBody);
                        Gson gson = new Gson();
                        notificationBaseHolder = new NotificationBaseHolder();
                        notificationBaseHolder = gson.fromJson(responseBody, NotificationBaseHolder.class);
                        if (UtilValidate.isNotNull(asyncTaskCallBack)) {

                            asyncTaskCallBack.onFinish(i, notificationBaseHolder);
                        }


                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        asyncTaskCallBack.onFinish(i, "cleared");

                    }
                });
    }

    public void getAllNotification(final Activity activity, String sessionId, final AsyncTaskCallBack notificationasynchTaskCallBack, int requestCode, final ProgressDialog pDialog) {

        notificationBaseHolder = new NotificationBaseHolder();
        RequestParams params = new RequestParams();
        params.put(NotificationRequestParams.SESSION_ID, sessionId);

        UMEPALAppRestClient.get(NotificationRequestParams.NOTIFICATION_URL, params, null,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {


                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));

                        if (i == WebResponseConstants.ResponseCode.OK) {

                            //Log.e(TAG, "Notification RESPONSE  " + responseBody);


                            Gson gson = new Gson();
                            notificationBaseHolder = new NotificationBaseHolder();
                            notificationBaseHolder = gson.fromJson(responseBody, NotificationBaseHolder.class);

                            if (UtilValidate.isNotNull(notificationasynchTaskCallBack)) {
                                if (notificationBaseHolder != null) {
                                    notificationasynchTaskCallBack.onFinish(i, notificationBaseHolder);
                                }
                            }

                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                         //   Log.e(TAG, "unauthorise in manager" + i);

                            notificationBaseHolder = new NotificationBaseHolder();
                            Gson gson = new Gson();
                            notificationBaseHolder = gson.fromJson(responseBody, NotificationBaseHolder.class);
                            if (UtilValidate.isNotNull(notificationasynchTaskCallBack)) {
                                notificationasynchTaskCallBack.onFinish(i, notificationBaseHolder);
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
                                        "Please check your internet connection...",
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    }




                   /* @Override
                    public void onStart() {
                        super.onStart();


                        if(pDialog!=null)

                        {
                            pDialog.show();
                        }
                        Log.i(TAG, "notification api call started...");
                    }
*/



                   /* @Override
                    public void onFailure(Throwable error, String content) {

                        if (!(NetChecker.isConnected(activity))) {

                            if (!(NetChecker.isConnectedWifi(activity) && NetChecker
                                    .isConnectedMobile(activity))) {

                                Toast.makeText(
                                        activity,
                                        "Please check your internet connection...",
                                        Toast.LENGTH_LONG).show();
                            }

                        }

                    }*/


                });
    }
    public void getRefferNotification(final Activity activity, String reffer_id,String refferee_id,
                                         String membership_id,final AsyncTaskCallBack reffernotificationCallBack) {
        // TODO Auto-generated method stub
        RequestParams params = new RequestParams();
        params.put(RefferNotification.REFFER_ID, reffer_id);
        params.put(RefferNotification.REFFEREE_ID, refferee_id);
        params.put(RefferNotification.MEMBERSHIP_ID, membership_id);


        UMEPALAppRestClient.get(RefferNotification.REFFER_NOTIFICATION_URL,params,null,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                Log.e("RESPONSE", "RESPONSE" + responseBody);
                userBaseHolder = new UserBaseHolder();
                Gson gson = new Gson();
                notificationBaseHolder = gson.fromJson(responseBody,NotificationBaseHolder.class);
                if(UtilValidate.isNotNull(reffernotificationCallBack)){
                    reffernotificationCallBack.onFinish(i,notificationBaseHolder);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                if (!(NetChecker.isConnected(activity))) {

                    if (!(NetChecker.isConnectedWifi(activity) && NetChecker.isConnectedMobile(activity))) {

                        Toast.makeText(activity, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                    }

                }

                if (UtilValidate.isNotNull(reffernotificationCallBack)) {

                    reffernotificationCallBack.onFinish(1, "No Internet");
                }
            }
        });
    }
}