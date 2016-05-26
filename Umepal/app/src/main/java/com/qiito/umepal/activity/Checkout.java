package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.dao.CheckoutDAO;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.dao.UserDAO;
import com.qiito.umepal.holder.CheckoutProductsHolder;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiya on 18/9/15.
 */
public class Checkout extends Activity {

    private CurrentlyLoggedUserDAO currentlyLoggedUserDAO;

    private TextView creditCard;
    private TextView paypal;
    private TextView heading;

    private EditText name;
    private EditText mobile;
    private EditText email;
    private EditText address;
    private EditText creditCardNumber;
    private EditText ccvNumber;
    private EditText expiryDate;

    private ImageView creditCardView;
    private ImageView paypalView;
    private ImageView backIcon;

    private Button payNow;
    private String session_id;
    private String userId;

    private PaymentTransactionDetailCallBack paymentTransactionDetailCallBack;
    private List<CheckoutProductsHolder> checkoutProductsList = new ArrayList<>();
    private UserObjectHolder userObjectHolder;
    private List<String> product_quantity = new ArrayList<String>();
    private List<String> product_ids = new ArrayList<String>();
    private WebView _paypal;
    private LinearLayout include_backbutton;
    private LinearLayout paypal_webview_layout;
    private ProgressBar progressBar;
    private WebView webview_paypal;
    private ScrollView shopping_cart_checkout_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        initViews();
        initManagers();
        heading.setVisibility(View.VISIBLE);
        heading.setText("Checkout");

        setText();

        currentlyLoggedUserDAO.getCurrentUserDetails();

        paypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paypalView.setVisibility(View.VISIBLE);
                creditCardView.setVisibility(View.INVISIBLE);
                paypal.setTextColor(getResources().getColor(R.color.green));
                creditCard.setTextColor(getResources().getColor(R.color.black));

            }
        });

        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paypalView.setVisibility(View.INVISIBLE);
                creditCardView.setVisibility(View.VISIBLE);
                paypal.setTextColor(getResources().getColor(R.color.black));
                creditCard.setTextColor(getResources().getColor(R.color.green));
            }
        });
        /*payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code for pay palzzz

                session_id = currentlyLoggedUserDAO.getSessionId();
                //List<String> product_quantities;
                userObjectHolder = DbManager.getInstance().getCurrentUserDetails();

                userId = String.valueOf(userObjectHolder.getId());
                checkoutProductsList.clear();
                checkoutProductsList = CheckoutDAO.getInstance().getProductDetailsFromCheckout(userId);


                for (int i = 0; i <= checkoutProductsList.size() - 1; i++) {


                    product_quantity.add(checkoutProductsList.get(i).getQuantity());
                    product_ids.add(checkoutProductsList.get(i).getProductid());

                }

                PaypalManager.getInstance().getPayPalTransactionDetails(Checkout.this, session_id, product_ids, product_quantity, paymentTransactionDetailCallBack);
            }
        });
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                PaypalManager.getInstance().getPayPalTransactionDetails(Checkout.this, session_id, product_ids, product_quantity, paymentTransactionDetailCallBack);

            }
        });*/


    }

    private void setText() {

        if ((UserDAO.getInstance().getUserDetails().getFirstName() != null) && (UserDAO.getInstance().getUserDetails().getLastName() != null)) {

            name.setText(UserDAO.getInstance().getUserDetails().getFirstName() + " " + UserDAO.getInstance().getUserDetails().getLastName());
        } else {

            name.setText(UserDAO.getInstance().getUserDetails().getFirstName());
        }

        if (UserDAO.getInstance().getUserDetails().getEmail() != null) {

            email.setText(UserDAO.getInstance().getUserDetails().getEmail());

        }

        if (UserDAO.getInstance().getUserDetails().getTelephone() != null) {

            mobile.setText(UserDAO.getInstance().getUserDetails().getTelephone());
        }

        if (UserDAO.getInstance().getUserDetails().getAddressLine1() != null && UserDAO.getInstance().getUserDetails().getAddressLine2() != null) {

            address.setText(UserDAO.getInstance().getUserDetails().getAddressLine1() + " " + UserDAO.getInstance().getUserDetails().getAddressLine2());
        } else if (UserDAO.getInstance().getUserDetails().getAddressLine1() != null) {

            address.setText(UserDAO.getInstance().getUserDetails().getAddressLine1());

        }

        creditCardNumber.setText("435467 78787 7878 89");
        ccvNumber.setText("238");
        expiryDate.setText("16 november 2015");


    }

    private void initViews() {
        creditCardView = (ImageView) findViewById(R.id.creditcard_view);
        paypalView = (ImageView) findViewById(R.id.paypal_view);
        creditCard = (TextView) findViewById(R.id.credit_debit_textview);
        paypal = (TextView) findViewById(R.id.paypal_textview);
        payNow = (Button) findViewById(R.id.payNow_button);
        name = (EditText) findViewById(R.id.name_edittext);
        mobile = (EditText) findViewById(R.id.mobile_edittext);
        email = (EditText) findViewById(R.id.email_edittext);
        address = (EditText) findViewById(R.id.address_edittext);
        creditCardNumber = (EditText) findViewById(R.id.credit_card_number);
        ccvNumber = (EditText) findViewById(R.id.card_ccv_code);
        expiryDate = (EditText) findViewById(R.id.card_expiry_date);

        include_backbutton = (LinearLayout) findViewById(R.id.include_backbutton);
        paypal_webview_layout = (LinearLayout) findViewById(R.id.paypal_webview_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        webview_paypal = (WebView) findViewById(R.id.webview_paypal);
        shopping_cart_checkout_button = (ScrollView) findViewById(R.id.shopping_cart_checkout_button);
        backIcon = (ImageView) findViewById(R.id.back_menu_icon);
        heading = (TextView) findViewById(R.id.page_heading);

    }

    private void initManagers() {
        currentlyLoggedUserDAO = new CurrentlyLoggedUserDAO();
        paymentTransactionDetailCallBack = new PaymentTransactionDetailCallBack();
    }

    public class PaymentTransactionDetailCallBack implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub
            // dialog.dismiss();
            PayPalTransactionResponseHolder responseHolder = (PayPalTransactionResponseHolder) result;

            Log.e("", " in call back of payment>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            if (UtilValidate.isNotNull(responseHolder)) {

                if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.OK) {

                    if (UtilValidate.isNotNull(responseHolder.getData())) {

                        paypal_webview_layout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        payNow.setVisibility(View.GONE);
                        include_backbutton.setVisibility(View.GONE);
                        shopping_cart_checkout_button.setVisibility(View.GONE);


                        final String URL = responseHolder.getData()
                                .getTransaction_url().toString();


                        Checkout.this.runOnUiThread(new Runnable() {

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
                                    public void onPageStarted(WebView view,
                                                              String url, Bitmap favicon) {
                                        // TODO Auto-generated method stub
                                        super.onPageStarted(view, url, favicon);
                                    }

                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        super.onPageFinished(view, url);
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        // TODO Auto-generated method stub

                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/success")) {

                                            paypal_webview_layout.setVisibility(View.GONE);
                                            payNow.setVisibility(View.VISIBLE);
                                            CheckoutDAO.getInstance().deleteAllCheckout();
                                            Toast.makeText(Checkout.this, "Payment Completed Successfully!", Toast.LENGTH_LONG).show();
                                            Intent returnToHome = new Intent(Checkout.this,
                                                    Checkout.class);
                                            startActivity(returnToHome);
                                            finish();

                                        } else {

                                            view.loadUrl(url);
                                        }

                                        return true;

                                    }

                                    @Override
                                    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                                                   SslError error) {
                                        // TODO Auto-generated method stub
                                        super.onReceivedSslError(view, handler, error);

                                        // Log.i(TAG, "ssl error = " + "SSL Error received: " + error.getPrimaryError());
                                    }

                                    @Override
                                    public void onLoadResource(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        super.onLoadResource(view, url);
                                        //	Log.i(TAG, "onLoadResource() "+ url);
                                    }

                                });

                                webview_paypal.loadUrl(URL);

                            }
                        });


                    }

                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_AUTHORIZED) {

                    Toast.makeText(Checkout.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.METHODNOT_ALLOWED) {

                    Toast.makeText(Checkout.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.NOT_ACCEPTABLE) {

                    Toast.makeText(Checkout.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.PRECONDITIONFAILED) {

                    Toast.makeText(Checkout.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.SERVICE_UNAVAILABLE) {

                    Toast.makeText(Checkout.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_SUCCESSFULL) {

                    Toast.makeText(Checkout.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


            }

        }

        /*
         * (non-Javadoc)
         *
         * @see
         * com.xminds.annietiangapp.webservice.AsyncTaskCallBack#onFinish(int,
         * java.lang.String)
         */
        @Override
        public void onFinish(int responseCode, String result) {
            // TODO Auto-generated method stub
            //dialog.dismiss();

            if (UtilValidate.isNull(result) || (UtilValidate.isNull(result))) {
                Toast.makeText(Checkout.this,
                        "Server Down",
                        Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(Checkout.this,
                        "" + result,
                        Toast.LENGTH_SHORT).show();

            }
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * com.xminds.annietiangapp.webservice.AsyncTaskCallBack#onFinish(int,
         * java.lang.Object, boolean)
         */


    }
}
