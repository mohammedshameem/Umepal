package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.util.MessagePopup;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;

/**
 * Created by shiya on 8/6/16.
 */
public class RefererPayment extends Activity {

    private TextView heading;
    private LinearLayout backIcon;
    private LinearLayout payNowLayout;
    private CheckBox checkCredit;
    private TextView makePaymentText;
    private TextView makePaymentAmount;
    private ProductNotificationBaseHolder notificationDetails;
    private UserObjectHolder refererDetails;
    private UserBaseHolder userBaseHolder;
    private PayFromCreditCallBack payFromCreditCallBack;
    private String membershipId;
    private String refereeId;
    private String refereeUserId;
    private MembershipPaymentCallBack membershipPaymentCallBack;
    private WebView webview_paypal;
    private LinearLayout paymentMainLayout;
    private LinearLayout paypal_webviews_layout;
    private ProgressBar progressBar1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);
        initViews();
        if(getIntent().hasExtra("referee_object")){
            refererDetails=(UserObjectHolder)getIntent().getSerializableExtra("referee_object");
        }
        if (refererDetails!=null){
            if(refererDetails.getMembershipId()>=1){
                membershipId=String.valueOf(refererDetails.getMembershipId());

            }
            if (!refererDetails.getMembershipPrice().equalsIgnoreCase("")){
                makePaymentAmount.setText("$ "+refererDetails.getMembershipPrice());
            }


                StringBuilder sb = new StringBuilder();
                if (refererDetails.getFirstName() != null) {

                    if (!refererDetails.getFirstName().equalsIgnoreCase("")) {

                        sb.append(getString(R.string.make_payment) + refererDetails.getFirstName());
                        if (!refererDetails.getLastName().equalsIgnoreCase("")) {
                            sb.append(" " + refererDetails.getLastName() + getString(R.string.make_payment_continuation));
                        } else {
                            sb.append(getString(R.string.make_payment_continuation));
                        }
                        makePaymentText.setText(sb.toString());
                    }

                }

            if (refererDetails.getId() >0){
                refereeUserId = String.valueOf(refererDetails.getId());
            }

            if (refererDetails.getUmeId() != null){

                if (!refererDetails.getUmeId().equalsIgnoreCase("")){
                    refereeId = refererDetails.getUmeId();
                }

            }
        }
        backIcon.setOnClickListener(backListener);
        payNowLayout.setOnClickListener(payNowListener);














        if (getIntent().hasExtra("notification_object")) {

            notificationDetails = (ProductNotificationBaseHolder) getIntent().getSerializableExtra("notification_object");
        }

        if (notificationDetails != null) {
            if (!notificationDetails.getMembershipId().equalsIgnoreCase("")) {
                membershipId = notificationDetails.getMembershipId().toString();
            }

            if (notificationDetails.getMembershipPrice() != null) {
                makePaymentAmount.setText("$ " + notificationDetails.getMembershipPrice());
            }
            if (notificationDetails.getReferer() != null) {

                if (notificationDetails.getReferer().getUmeId() != null) {

                    refereeId = notificationDetails.getReferer().getUmeId();

                }

                if (notificationDetails.getReferer().getId() <= 0) {
                    refereeUserId = String.valueOf(notificationDetails.getReferer().getId());
                }

                StringBuilder sb = new StringBuilder();
                if (notificationDetails.getReferer().getFirstName() != null) {

                    if (!notificationDetails.getReferer().getFirstName().equalsIgnoreCase("")) {

                        sb.append(getString(R.string.make_payment) + notificationDetails.getReferer().getFirstName());
                        if (!notificationDetails.getReferer().getLastName().equalsIgnoreCase("")) {
                            sb.append(" " + notificationDetails.getReferer().getLastName() + getString(R.string.make_payment_continuation));
                        } else {
                            sb.append(getString(R.string.make_payment_continuation));
                        }
                        makePaymentText.setText(sb.toString());
                    }

                }
            }


        }
        backIcon.setOnClickListener(backListener);
        payNowLayout.setOnClickListener(payNowListener);
    }

    private void initViews() {
        heading = (TextView) findViewById(R.id.page_heading);
        heading.setVisibility(View.VISIBLE);
        heading.setText("Payment");
        backIcon = (LinearLayout) findViewById(R.id.back_icon_with_app_image);
        payNowLayout = (LinearLayout) findViewById(R.id.pay_now_layout);
        checkCredit = (CheckBox) findViewById(R.id.check_credit);
        makePaymentText = (TextView) findViewById(R.id.make_payment_text);
        makePaymentAmount = (TextView) findViewById(R.id.make_payment_amount);
        webview_paypal = (WebView) findViewById(R.id.webview_paypal);
        paymentMainLayout = (LinearLayout) findViewById(R.id.payment_main_layout);
        paypal_webviews_layout = (LinearLayout) findViewById(R.id.paypal_webviews_layout);
        progressBar1 = (ProgressBar) findViewById(R.id.progress_bar);

        payFromCreditCallBack = new PayFromCreditCallBack();
        userBaseHolder = new UserBaseHolder();
        membershipPaymentCallBack = new MembershipPaymentCallBack();

    }

    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    View.OnClickListener payNowListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (checkCredit.isChecked()) {

                Log.e("is checked >> ", DbManager.getInstance().getSessionId() + " " + membershipId + " " + refereeId);

                UserManager.getInstance().payFromCredit(RefererPayment.this, DbManager.getInstance().getSessionId(), membershipId, refereeId, payFromCreditCallBack);

            } else {

                Log.e("is not checked >> ", " >>> " + notificationDetails.getMembershipId().toString() + " && " + String.valueOf(notificationDetails.getReferer().getId()));

                LoginManager.getInstance().membershipPaypal(RefererPayment.this, notificationDetails.getMembershipId().toString(),
                        String.valueOf(notificationDetails.getReferer().getId()),String.valueOf(notificationDetails.getId()), membershipPaymentCallBack);
            }
        }
    };

    private class PayFromCreditCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            userBaseHolder = (UserBaseHolder) result;
            if (userBaseHolder != null) {
                if (userBaseHolder.getStatus().equalsIgnoreCase("success")) {

                    MessagePopup messagePopup = new MessagePopup("Success!", userBaseHolder.getMessage(), RefererPayment.this);
                    messagePopup.show();

                } else {
                    MessagePopup messagePopup = new MessagePopup("Error!", userBaseHolder.getMessage(), RefererPayment.this);
                    messagePopup.show();
                }
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    private class MembershipPaymentCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            PayPalTransactionResponseHolder responseHolder = (PayPalTransactionResponseHolder) result;
            Log.e("$$", " in call back of payment>>>>>>>>>>>>>>>>>>>>>>    " + responseHolder.getStatus() + " ### " + responseHolder.getMessage() + "  >>> " + responseHolder.getData());
            if (UtilValidate.isNotNull(responseHolder)) {
                if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.OK) {
                    if (UtilValidate.isNotNull(responseHolder.getData())) {
                        Log.e("$$", "response not null");
                        paypal_webviews_layout.setVisibility(View.VISIBLE);
                        paymentMainLayout.setVisibility(View.GONE);
                        progressBar1.setVisibility(View.VISIBLE);
                        final String URL = responseHolder.getData().getTransaction_url().toString();
                        RefererPayment.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                webview_paypal.clearCache(true);
                                webview_paypal.clearHistory();
                                webview_paypal.getSettings().setJavaScriptEnabled(true);
                                webview_paypal.getSettings().setBuiltInZoomControls(true);
                                webview_paypal.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                                webview_paypal.getSettings().setLoadWithOverviewMode(true);
                                webview_paypal.getSettings().setUseWideViewPort(true);
                                webview_paypal.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.2.1; en-us; Nexus One Build/FRG83) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
                                webview_paypal.setWebViewClient(new WebViewClient() {

                                    @Override
                                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                        // TODO Auto-generated method stub
                                        super.onPageStarted(view, url, favicon);
                                    }

                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        super.onPageFinished(view, url);
                                        progressBar1.setVisibility(View.GONE);

                                    }

                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/nmembersuccess")) {
                                            view.loadUrl(url);
                                            paypal_webviews_layout.setVisibility(View.GONE);
                                            Toast.makeText(RefererPayment.this, "Payment Completed Successfully!", Toast.LENGTH_LONG).show();


                                            paymentMainLayout.setVisibility(View.VISIBLE);

                                            LayoutInflater inflater = (LayoutInflater) RefererPayment.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            View popupView = inflater.inflate(R.layout.payment_completed_popup, null);
                                            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                            //popupWindow.setAnimationStyle(R.style.dialog_animation);
                                            popupWindow.update();
                                            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                                            // popupWindow.setAnimationStyle(R.style.dialog_animation);
                                            final Button close = (Button) popupView.findViewById(R.id.closeButton);


                                            close.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    // TODO Auto-generated method stub
                                                    popupWindow.dismiss();
                                                    /*refferee_ID = DbManager.getInstance().getCurrentUserDetails().getUmeId();
                                                    Log.e("PASSWORD >> ", "" + password);
                                                    LoginManager.getInstance().emailLogin(RefererPayment.this, refferee_ID, password, loginCallBackClass, requestcode);*/

                                                   /* Intent intent = new Intent(RefererPayment.this, Loginactivity.class);
                                                    startActivity(intent);*/
                                                    finish();
                                                }
                                            });


                                            //finish();
                                        } else {
                                            view.loadUrl(url);
                                        }

                                        return true;

                                    }

                                    @Override
                                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                                        // TODO Auto-generated method stub
                                        super.onReceivedSslError(view, handler, error);
                                    }

                                    @Override
                                    public void onLoadResource(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        super.onLoadResource(view, url);

                                        if (url.equalsIgnoreCase("http://umepal-s.x-minds.org/api/paypal/cancel")) {
                                       /* Intent in = new Intent(RefererPayment.this, ShoppingCart.class);
                                        startActivity(in);
                                        finish();*/
                                            Toast.makeText(RefererPayment.this, "Problem !!!", Toast.LENGTH_LONG).show();

                                        }


                                    }

                                });
                                webview_paypal.loadUrl(URL);
                            }
                        });


                    } else {
                    }

                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_AUTHORIZED) {

                    Toast.makeText(RefererPayment.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.METHODNOT_ALLOWED) {

                    Toast.makeText(RefererPayment.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.NOT_ACCEPTABLE) {

                    Toast.makeText(RefererPayment.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.PRECONDITIONFAILED) {

                    Toast.makeText(RefererPayment.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.SERVICE_UNAVAILABLE) {

                    Toast.makeText(RefererPayment.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_SUCCESSFULL) {

                    Toast.makeText(RefererPayment.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }
}
