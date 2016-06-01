package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.dao.CheckoutDAO;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;

/**
 * Created by abin on 19/5/16.
 */
public class MembershipSelectionActivity extends Activity {

    private MembershipCallBackClass membershipCallback;
    private RequestForPayment requestForPayment;

    private Button requestpaymentButton;
    private Button paynowButton;

    private ImageView MemAcheck;
    private ImageView MemAuncheck;
    private ImageView MemBcheck;
    private ImageView MemBuncheck;
    private ImageView MemCcheck;
    private ImageView MemCuncheck;

    private TextView membershipfeeText;

    private String MembershipId;
    private String session;

    private WebView webview_paypal;
    private LinearLayout membershipselectionpage;
    private LinearLayout paypal_webviews_layout;
    private ProgressBar progressBar1;
    private String reffer_ID;
    private String refferee_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.membership_selection_page);
        initViews();
        initManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.HeaderRed));
        }
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();

        MemAuncheck.setOnClickListener(A_uncheckListener);
        MemBuncheck.setOnClickListener(B_uncheckListener);
        MemCuncheck.setOnClickListener(C_uncheckListener);

        MemAcheck.setOnClickListener(A_checkListener);
        MemBcheck.setOnClickListener(B_checkListener);
        MemCcheck.setOnClickListener(C_checkListener);

        paynowButton.setOnClickListener(paynow_Listener);

        requestpaymentButton.setOnClickListener(requestforpaymentListener);

    }

    private void initManager() {
        membershipCallback = new MembershipCallBackClass();
        requestForPayment = new RequestForPayment();
    }

    View.OnClickListener paynow_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refferee_ID = DbManager.getInstance().getCurrentUserDetails().getUmeId();
            Log.e("ID ID ID",""+DbManager.getInstance().getCurrentUserDetails().getUmeId());
            LoginManager.getInstance().membershipPaypal(MembershipSelectionActivity.this, MembershipId, refferee_ID, membershipCallback);

        }
    };

    View.OnClickListener requestforpaymentListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("UME ID", ":::" + DbManager.getInstance().getCurrentUserDetails().getUmeId());
            Log.e("Reffer ID", ":::" + DbManager.getInstance().getCurrentUserDetails().getReferrerId());
            Log.e("Refferal ID", ":::" + DbManager.getInstance().getCurrentUserDetails().getReferralmember_id());

            refferee_ID = DbManager.getInstance().getCurrentUserDetails().getUmeId();
            reffer_ID = DbManager.getInstance().getCurrentUserDetails().getReferralmember_id();

            UserManager.getInstance().RequestforpaymentParams(MembershipSelectionActivity.this, reffer_ID, refferee_ID, MembershipId, requestForPayment);

        }
    };

    View.OnClickListener A_uncheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MembershipId = "1";
            MemAuncheck.setVisibility(View.GONE);
            MemAcheck.setVisibility(View.VISIBLE);

            MemBcheck.setVisibility(View.GONE);
            MemCcheck.setVisibility(View.GONE);
            MemBuncheck.setVisibility(View.VISIBLE);
            MemCuncheck.setVisibility(View.VISIBLE);


            membershipfeeText.setText("150");
        }
    };
    View.OnClickListener B_uncheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MembershipId = "2";
            MemBuncheck.setVisibility(View.GONE);
            MemBcheck.setVisibility(View.VISIBLE);

            MemAcheck.setVisibility(View.GONE);
            MemCcheck.setVisibility(View.GONE);
            MemAuncheck.setVisibility(View.VISIBLE);
            MemCuncheck.setVisibility(View.VISIBLE);


            membershipfeeText.setText("200");
        }
    };
    View.OnClickListener C_uncheckListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MembershipId = "3";
            MemCuncheck.setVisibility(View.GONE);
            MemCcheck.setVisibility(View.VISIBLE);

            MemAcheck.setVisibility(View.GONE);
            MemBcheck.setVisibility(View.GONE);
            MemAuncheck.setVisibility(View.VISIBLE);
            MemBuncheck.setVisibility(View.VISIBLE);


            membershipfeeText.setText("180");

        }
    };
    View.OnClickListener A_checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemAuncheck.setVisibility(View.VISIBLE);
            MemAcheck.setVisibility(View.GONE);


        }
    };
    View.OnClickListener B_checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemBuncheck.setVisibility(View.VISIBLE);
            MemBcheck.setVisibility(View.GONE);
        }
    };
    View.OnClickListener C_checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MemCuncheck.setVisibility(View.VISIBLE);
            MemCcheck.setVisibility(View.GONE);
        }
    };


    private void initViews() {
        requestpaymentButton = (Button) findViewById(R.id.requestpaymentButton);
        paynowButton = (Button) findViewById(R.id.paynowButton);

        MemAcheck = (ImageView) findViewById(R.id.checkA);
        MemAuncheck = (ImageView) findViewById(R.id.uncheckA);
        MemBcheck = (ImageView) findViewById(R.id.checkB);
        MemBuncheck = (ImageView) findViewById(R.id.uncheckB);
        MemCcheck = (ImageView) findViewById(R.id.checkC);
        MemCuncheck = (ImageView) findViewById(R.id.uncheckC);

        membershipfeeText = (TextView) findViewById(R.id.membershipfeeText);

        webview_paypal = (WebView) findViewById(R.id.webview_paypal);
        membershipselectionpage = (LinearLayout) findViewById(R.id.membershipselectionpage);
        paypal_webviews_layout = (LinearLayout) findViewById(R.id.paypal_webviews_layout);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);


    }

    private class RequestForPayment implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {


        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


    private class MembershipCallBackClass implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub
            // dialog.dismiss();
            PayPalTransactionResponseHolder responseHolder = (PayPalTransactionResponseHolder) result;
            Log.e("$$", " in call back of payment>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + responseHolder.getMessage());
            if (UtilValidate.isNotNull(responseHolder)) {
                if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.OK) {
                    if (UtilValidate.isNotNull(responseHolder.getData())) {
                        Log.e("$$", "response not null");
                        paypal_webviews_layout.setVisibility(View.VISIBLE);
                        membershipselectionpage.setVisibility(View.GONE);
                        progressBar1.setVisibility(View.VISIBLE);
                        final String URL = responseHolder.getData().getTransaction_url().toString();
                        MembershipSelectionActivity.this.runOnUiThread(new Runnable() {

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
                                        Log.e("URL",""+url);
                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/nmembersuccess")) {
                                            paypal_webviews_layout.setVisibility(View.GONE);
                                            Toast.makeText(MembershipSelectionActivity.this, "Payment Completed Successfully!", Toast.LENGTH_LONG).show();


                                            membershipselectionpage.setVisibility(View.VISIBLE);

                                            LayoutInflater inflater = (LayoutInflater) MembershipSelectionActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                                        Log.e("URL",""+url);

                                        if (url.equalsIgnoreCase("http://umepal-s.x-minds.org/api/paypal/cancel")) {
                                       /* Intent in = new Intent(MembershipSelectionActivity.this, ShoppingCart.class);
                                        startActivity(in);
                                        finish();*/
                                            Toast.makeText(MembershipSelectionActivity.this, "Problem !!!", Toast.LENGTH_LONG).show();

                                        }


                                    }

                                });
                                webview_paypal.loadUrl(URL);
                            }
                        });


                    } else {
                    }

                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_AUTHORIZED) {

                    Toast.makeText(MembershipSelectionActivity.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.METHODNOT_ALLOWED) {

                    Toast.makeText(MembershipSelectionActivity.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.NOT_ACCEPTABLE) {

                    Toast.makeText(MembershipSelectionActivity.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.PRECONDITIONFAILED) {

                    Toast.makeText(MembershipSelectionActivity.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.SERVICE_UNAVAILABLE) {

                    Toast.makeText(MembershipSelectionActivity.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_SUCCESSFULL) {

                    Toast.makeText(MembershipSelectionActivity.this,
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
