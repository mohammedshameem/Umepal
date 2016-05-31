package com.qiito.umepal.managers;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Constants.User;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.ForgotPasswordBaseHolder;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.ResetPasswordBaseHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.UMEPALAppRestClient;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayInputStream;
import java.io.File;


public class LoginManager implements ApiConstants, User {
    private static final String TAG = LoginManager.class.getName();
    private static LoginManager userManager;
    private UserBaseHolder userBaseHolder;
    private ForgotPasswordBaseHolder forgotPasswordBaseHolder;
    private ResetPasswordBaseHolder resetPasswordBaseHolder;

    /**
     * @return userManager instance
     */
    public static LoginManager getInstance() {

        if (userManager == null) {

            userManager = new LoginManager();
        }

        return userManager;
    }

    public void emailLogin(final Activity activity, String email_address,
                           String password, final AsyncTaskCallBack signUpCallBack, final int requestcode) {
        // TODO Auto-generated method stub

        RequestParams params = new RequestParams();
        params.put(EmailLoginRequestParams.EMAIL, email_address);
        params.put(EmailLoginRequestParams.PASSWORD, password);


        UMEPALAppRestClient.post(EmailLoginRequestParams.EMAILLOGIN_URL, params, activity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        userBaseHolder = new UserBaseHolder();
                        Gson gson = new Gson();

                        String responseBody = UtilValidate.getStringFromInputStream(new ByteArrayInputStream(bytes));
                        Log.e("@@@", "##responseBody#" + responseBody);
                        userBaseHolder = gson.fromJson(responseBody, UserBaseHolder.class);
                        if (UtilValidate.isNotNull(signUpCallBack)) {
                            signUpCallBack.onFinish(requestcode,
                                    userBaseHolder);
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        if (UtilValidate.isNetworkAvailable(activity)) {
                            if (bytes != null) {
                                String responseBody = UtilValidate
                                        .getStringFromInputStream(new ByteArrayInputStream(
                                                bytes));

                                Log.i(TAG, "asyncTaskCallBack on failure" + responseBody);
                                if (signUpCallBack == null) {
                                    Log.i(TAG, "asyncTaskCallBack is null : ");
                                } else {
                                    signUpCallBack.onFinish(0, responseBody);
                                }
                            }
                        } else {
                            Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
                            signUpCallBack.onFinish(0, "FAIL");
                        }

                    }
                });

    }


    //API CALL FOR SIGNUP

    public void emailSignup(final Activity activity, final AsyncTaskCallBack signUpCallBack, String firstname,
                            String lastname, String email, String cea, String mobile, String bank, String estateagency,
                            String bankaccount, String password, String uniquedeviceid, String referralmemberid, String profilePic, final int requestCode)
    //include requestCode as a paramter
    {
        // TODO Auto-generated method stub

        RequestParams params = new RequestParams();
        params.put(EmailSignUpRequestParams.FIRSTNAME, firstname);
        params.put(EmailSignUpRequestParams.LASTNAME, lastname);
        params.put(EmailSignUpRequestParams.EMAIL, email);
        params.put(EmailSignUpRequestParams.CEA, cea);
        params.put(EmailSignUpRequestParams.MOBILE, mobile);
        params.put(EmailSignUpRequestParams.BANK, bank);
        params.put(EmailSignUpRequestParams.ESTATEAGENCY, estateagency);
        params.put(EmailSignUpRequestParams.BANKACCOUNT, bankaccount);
        params.put(EmailSignUpRequestParams.PASSWORD, password);
        params.put(EmailSignUpRequestParams.UNIQUEDEVICEID, uniquedeviceid);
        params.put(EmailSignUpRequestParams.REFERRALMEMBERID, referralmemberid);

        if (UtilValidate.isNotNull(profilePic)) {

            final File file = new File(profilePic);
            FileBody fb = new FileBody(file);

            Log.e("", "in manager> pic>as file>>" + fb);

            params.put(EmailSignUpRequestParams.PROFILE_PIC, profilePic);
            //builder.addPart(UserEditProfileRequestParams.PICTURE, fb);

        }

        UMEPALAppRestClient.post(EmailSignUpRequestParams.EMAILSIGNUP_URL, params, activity,
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {

                        String responseBody = UtilValidate
                                .getStringFromInputStream(new ByteArrayInputStream(bytes));
                        if (i == WebResponseConstants.ResponseCode.OK) {

                            Log.e(TAG, "LOGIN RESPONSE " + responseBody);

                            Gson gson = new Gson();
                            userBaseHolder = gson.fromJson(responseBody, UserBaseHolder.class);
                            if (UtilValidate.isNotNull(signUpCallBack)) {
                                signUpCallBack.onFinish(i, userBaseHolder);    //requestCode to be sent instead of i
                            }
                        }

                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        if (UtilValidate.isNetworkAvailable(activity)) {
                            if (bytes != null) {
                                Log.e("", "FAiled!!!");
                                String responseBody = UtilValidate
                                        .getStringFromInputStream(new ByteArrayInputStream(
                                                bytes));
                                if (signUpCallBack == null) {
                                    Log.i(TAG, "asyncTaskCallBack is null : ");
                                } else {

                                    signUpCallBack.onFinish(0, responseBody);
                                }
                            }

                        } else {
                            Toast.makeText(activity, "No active network!!", Toast.LENGTH_LONG).show();
                            signUpCallBack.onFinish(0, "FAIL");
                        }
                    }
                });

    }


    public void membershipPaypal(final Activity activity,

                                 String membershipID, String reffereeID, final AsyncTaskCallBack asyncTaskCallBack) {
        RequestParams params = new RequestParams();
        params.put(MembershipPaypalParams.MEMBERSHIPID, membershipID);
        params.put(MembershipPaypalParams.REFFEREE_ID, reffereeID);


        UMEPALAppRestClient.post(
                MembershipPaypalParams.MEMBERPAYPAL, params,
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
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(i,
                                        palTransactionResponseHolder);
                            }

                        }
                        if (i == WebResponseConstants.ResponseCode.UN_AUTHORIZED) {

                            PayPalTransactionResponseHolder palTransactionResponseHolder = new PayPalTransactionResponseHolder();
                            Gson gson = new Gson();
                            palTransactionResponseHolder = gson.fromJson(
                                    responseBody,
                                    PayPalTransactionResponseHolder.class);
                            if (UtilValidate.isNotNull(asyncTaskCallBack)) {
                                asyncTaskCallBack.onFinish(i,
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


    public void forgotPassword(Activity activity, String email, final AsyncTaskCallBack forgotPasswordCallBack) {
        // TODO Auto-generated method stub
        RequestParams params = new RequestParams();
        params.put(ForgotPasswordRequestParams.EMAIL, email);

        UMEPALAppRestClient.post(ForgotPasswordRequestParams.FORGOTPASSWORD_URL, params, activity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {


                String responseBody = UtilValidate
                        .getStringFromInputStream(new ByteArrayInputStream(bytes));

                Log.e("", "Response>>>>>>>>>>>>>>>>" + responseBody);

                forgotPasswordBaseHolder = new ForgotPasswordBaseHolder();
                Gson gson = new Gson();

                forgotPasswordBaseHolder = gson.fromJson(responseBody, ForgotPasswordBaseHolder.class);

                if (UtilValidate.isNotNull(forgotPasswordCallBack)) {

                    forgotPasswordCallBack.onFinish(i, forgotPasswordBaseHolder);
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {


                Log.i(TAG, "FORGOT PASSWORD API CALL FAILED");

            }
        });

    }

    public void resetPassword(Activity activity,
                              String email, String password, String token, final AsyncTaskCallBack resetPasswordCallBack) {
        // TODO Auto-generated method stub
        RequestParams params = new RequestParams();
        params.put(RestPasswordRequestParams.EMAIL, email);
        params.put(RestPasswordRequestParams.PASSWORD, password);
        params.put(RestPasswordRequestParams.TOKEN, token);


        UMEPALAppRestClient.post(RestPasswordRequestParams.RESET_PASSWORD_URL, params, activity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {


                String responseBody = UtilValidate
                        .getStringFromInputStream(new ByteArrayInputStream(bytes));
                Gson gson = new Gson();
                Log.e("", "Response>>>>>>>>>>>>>>>>" + responseBody);

                resetPasswordBaseHolder = gson.fromJson(responseBody, ResetPasswordBaseHolder.class);

                if (
                        UtilValidate.isNotNull(resetPasswordCallBack)) {

                    resetPasswordCallBack.onFinish(i, resetPasswordBaseHolder);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                Log.i(TAG, "RESET PASSWORD API CALL FAILED");


            }
        });

    }


}
