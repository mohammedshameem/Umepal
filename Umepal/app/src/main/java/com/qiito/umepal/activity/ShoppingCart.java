package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapters.ShoppingCartAdapter;
import com.qiito.umepal.dao.CheckoutDAO;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.ShippingAddress;
import com.qiito.umepal.holder.ShippingAddressHolder;
import com.qiito.umepal.holder.ShippingDetailsBaseHolder;
import com.qiito.umepal.holder.ShoppingCartBaseHolder;
import com.qiito.umepal.holder.ShoppingCartList;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.MyaccountProductManager;
import com.qiito.umepal.managers.PaypalManager;
import com.qiito.umepal.managers.ShoppingCartManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiya on 16/12/15.
 */
public class ShoppingCart extends Activity {

    private ShippingAddress shippingAddress;
    private UserObjectHolder userObjectHolder;
    private CartProductsCallback cartproductsCallback;
    private MyaccountProductManager myaccountProductManager;
    private UserBaseHolder userBaseHolder;
    private ShoppingCartAdapter shoppingCartAdapter;
    private CurrentlyLoggedUserDAO currentlyLoggedUserDAO;
    private PaymentTransactionDetailCallBack paymentTransactionDetailCallBack;
    private ShippingDetailAsyncTask shippingDetailAsyncTask;
    public LinearLayout shoppingCart;
    public LinearLayout emptyCart;
    private LinearLayout ClearAll;
    private LinearLayout shoppingCartLayout;
    private LinearLayout backbutton;
    private LinearLayout paypal_webview_layout;
    private ProgressBar progressBar;
    private List<ShoppingCartList> checkoutProductsList1 = new ArrayList<>();
    private List<String> product_quantity = new ArrayList<String>();
    private List<String> product_ids = new ArrayList<String>();
    private List<String> color = new ArrayList<>();
    private List<String> dimen = new ArrayList<>();
    private ListView shoppingCartList;
    private TextView totalPrice;
    private TextView clearAll;
    private TextView heading;
    private ImageView backMenuIcon;
    private TextView savings;
    private TextView total;
    private WebView webview_paypal;
    private Button checkout_button;
    private Button discoverPromotionItems;
    private Double totalAmount = 0.00;
    private Double shippingTotalAmount = 0.00;
    private Double amount;
    private Double shippingamount = 0.00;
    private Double orderTotal;
    private Double savingsAmount = 0.00;
    private String session;
    private String session_id;
    private String userId;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    /*private LayoutInflater inflater;
    private PopupWindow popupWindow;
    private View shippingDetailLayout;*/
    private boolean isStorePickupOnly = true;
    /*private ImageView closeimg;
    private TextView cancel;
    private TextView continueShopping;
    private boolean firstNameValidationFlag = false;
    private boolean streetAddressValidationFlag = false;
    private boolean cityvalidationFlag = false;
    private boolean phoneNoValidationFlag = false;
    private ShippingAddressHolder shippingDetailsHolder;*/
    private int requestcode = 1;
    private List<ShoppingCartList> shoppingcartList;
    private boolean stockCountFlag = false;
    private boolean flag = true;
    private StringBuilder builder;
    private int count = 0;


    public ShoppingCart() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_cart_page);
        initViews();
        initManagers();

        //FacebookSdk.sdkInitialize(ShoppingCart.this);
        heading.setText("Shopping Cart");
        heading.setVisibility(View.VISIBLE);
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        userId = String.valueOf(userObjectHolder.getId());
        shoppingCart.setVisibility(View.GONE);
        session_id = currentlyLoggedUserDAO.getSessionId();
        ShoppingCartManager.getInstance().getfromcart(ShoppingCart.this, cartproductsCallback, userId, session_id);

        backMenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!", "In clear all click");
                CheckoutDAO.getInstance().deleteAllCheckout();
                shoppingCartAdapter.notifyDataSetChanged();
                Log.e("!!", "exiting clear all click");
                shoppingCart.setVisibility(View.GONE);
                emptyCart.setVisibility(View.VISIBLE);
                clearAll.setVisibility(View.GONE);
                savings.setText(" 0.00");
                totalPrice.setText(" 0.00");
            }
        });
        checkout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockCountFlag = true;
                ShoppingCartManager.getInstance().getfromcart(ShoppingCart.this, cartproductsCallback, userId, session_id);


            }
        });
        discoverPromotionItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShoppingCart.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        initViews();
        initManagers();
        super.onResume();
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();

    }

    public void decrementPrice(List<ShoppingCartList> itemlist, TextView mPrice, TextView mSavings, LinearLayout mShoppingCart, LinearLayout mEmptyCart) {
        if (UtilValidate.isEmpty(itemlist)) {
            mEmptyCart.setVisibility(View.VISIBLE);
            mShoppingCart.setVisibility(View.GONE);
        } else {
            checkoutProductsList1 = itemlist;
            totalAmount = 0.00;
            if (DbManager.getInstance().getCurrentUserDetails().getMembership_status().equalsIgnoreCase("false")) {

                /* expired or not a member */

                for (int i = 0; i < checkoutProductsList1.size(); i++) {
                    Double product_quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                    Double product_amount = Double.parseDouble(checkoutProductsList1.get(i).getPromoprice());
                    amount = product_quantity * product_amount;
                    totalAmount = totalAmount + amount;
                }

                Log.e("EXPIRED TOTAL AFTER DEL", "" + totalAmount);
                TodaysParentApp.setItemTotalValue(String.valueOf(totalAmount));
                shippingTotalAmount = 0.00;
            } else {

                /* member */
                for (int i = 0; i < checkoutProductsList1.size(); i++) {
                    Double product_quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                    Double product_amount = Double.parseDouble(checkoutProductsList1.get(i).getDiscountprice());
                    amount = product_quantity * product_amount;
                    totalAmount = totalAmount + amount;
                }
                TodaysParentApp.setItemTotalValue(String.valueOf(totalAmount));
                shippingTotalAmount = 0.00;

            }

            for (int i = 0; i < checkoutProductsList1.size(); i++) {
                Double product_quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                Double shippingAmount = Double.parseDouble(checkoutProductsList1.get(i).getShippingcharge());
                shippingamount = product_quantity * shippingAmount;
                shippingTotalAmount = shippingTotalAmount + shippingamount;

            }
            savingsAmount = 0.00;
            if (DbManager.getInstance().getCurrentUserDetails().getMembership_status().equalsIgnoreCase("false")) {
                for (int i = 0; i < checkoutProductsList1.size(); i++) {
                    double pp = Double.parseDouble(checkoutProductsList1.get(i).getPromoprice());
                    double p = Double.parseDouble(checkoutProductsList1.get(i).getPrice());
                    double savedamount = p - pp;
                    double quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                    double tot = savedamount * quantity;
                    savingsAmount = savingsAmount + tot;

                }
            } else {
                for (int i = 0; i < checkoutProductsList1.size(); i++) {
                    double pp = Double.parseDouble(checkoutProductsList1.get(i).getDiscountprice());
                    double p = Double.parseDouble(checkoutProductsList1.get(i).getPrice());
                    double savedamount = p - pp;
                    double quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                    double tot = savedamount * quantity;
                    savingsAmount = savingsAmount + tot;

                }
            }
            orderTotal = totalAmount + shippingTotalAmount;
            String total = String.format("%.2f", orderTotal);
            mPrice.setText(" " + total);

            String saving = String.format("%.2f", savingsAmount);
            mSavings.setText(" " + saving);
            TodaysParentApp.setShippingValue(String.valueOf(shippingTotalAmount));
        }
    }

    private void initViews() {
        totalPrice = (TextView) findViewById(R.id.shopping_cart_total_price);
        shoppingCart = (LinearLayout) findViewById(R.id.shopping_cart);
        emptyCart = (LinearLayout) findViewById(R.id.empty_cart);
        shoppingCartList = (ListView) findViewById(R.id.shopping_cart_listview);
        backMenuIcon = (ImageView) findViewById(R.id.back_menu_icon);
        clearAll = (TextView) findViewById(R.id.notification_clear_all);
        checkout_button = (Button) findViewById(R.id.shopping_cart_checkout_button);
        discoverPromotionItems = (Button) findViewById(R.id.discover_promotion_item);
        ClearAll = (LinearLayout) findViewById(R.id.clear_all_layout);
        total = (TextView) findViewById(R.id.shopping_cart_total_text);
        heading = (TextView) findViewById(R.id.page_heading);
        savings = (TextView) findViewById(R.id.cart_saving_amount);
        paypal_webview_layout = (LinearLayout) findViewById(R.id.paypal_webviews_layout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        webview_paypal = (WebView) findViewById(R.id.webview_paypal);
        shoppingCartLayout = (LinearLayout) findViewById(R.id.shopping_cart_layout);
        backbutton = (LinearLayout) findViewById(R.id.back_button);
    }

    private void initManagers() {
        userObjectHolder = new UserObjectHolder();
        userBaseHolder = new UserBaseHolder();
        myaccountProductManager = new MyaccountProductManager();
        currentlyLoggedUserDAO = new CurrentlyLoggedUserDAO();
        paymentTransactionDetailCallBack = new PaymentTransactionDetailCallBack();
        cartproductsCallback = new CartProductsCallback();
        shippingDetailAsyncTask = new ShippingDetailAsyncTask();
        shippingAddress = new ShippingAddress();
    }

    public class PaymentTransactionDetailCallBack implements AsyncTaskCallBack {
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
                        paypal_webview_layout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        checkout_button.setVisibility(View.GONE);
                        emptyCart.setVisibility(View.GONE);
                        shoppingCart.setVisibility(View.GONE);
                        backbutton.setVisibility(View.GONE);
                        final String URL = responseHolder.getData().getTransaction_url().toString();
                        ShoppingCart.this.runOnUiThread(new Runnable() {

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
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/success")) {
                                            paypal_webview_layout.setVisibility(View.GONE);
                                            //checkout_button.setVisibility(View.VISIBLE);
                                            CheckoutDAO.getInstance().deleteAllCheckout();
                                            //backbutton.setVisibility(View.VISIBLE);
                                            Toast.makeText(ShoppingCart.this, "Payment Completed Successfully!", Toast.LENGTH_LONG).show();
                                            Intent returnToHome = new Intent(ShoppingCart.this, MainActivity.class);
                                            startActivity(returnToHome);
                                            /*if (!userObjectHolder.getFacebookId().equals("")) {
                                                for (int i = 0; i <= checkoutProductsList1.size() - 1; i++) {
                                                    callbackManager = CallbackManager.Factory.create();
                                                    shareDialog = new ShareDialog(ShoppingCart.this);
                                                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                                                        Log.e("fb share^^^", checkoutProductsList1.get(i).getName());
                                                        ShareLinkContent linkContent = new ShareLinkContent.Builder().setImageUrl(Uri.parse(checkoutProductsList1.get(i).getImage().toString()))
                                                                .setContentTitle("lufluf")
                                                                .setContentDescription(userObjectHolder.getFirstName() + " has purchased " + checkoutProductsList1.get(i).getName() + " from lufluf")
                                                                .setContentUrl(null*//*Uri.parse("https://play.google.com/store/apps/details?id=com.qiito.aromania&hl=en")*//*)
                                                                .build();
                                                        shareDialog.show(linkContent);
                                                    }
                                                }
                                            }*/
                                            finish();
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
                                        if (url.equalsIgnoreCase(" http://umepal-s.x-minds.org/api/paypal/cancel")) {
                                            Intent in = new Intent(ShoppingCart.this, ShoppingCart.class);
                                            startActivity(in);
                                            finish();
                                        }

                                    }

                                });
                                webview_paypal.loadUrl(URL);
                            }
                        });


                    } else {
                    }

                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_AUTHORIZED) {

                    Toast.makeText(ShoppingCart.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.METHODNOT_ALLOWED) {

                    Toast.makeText(ShoppingCart.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.NOT_ACCEPTABLE) {

                    Toast.makeText(ShoppingCart.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.PRECONDITIONFAILED) {

                    Toast.makeText(ShoppingCart.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.SERVICE_UNAVAILABLE) {

                    Toast.makeText(ShoppingCart.this,
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_SUCCESSFULL) {

                    Toast.makeText(ShoppingCart.this,
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
                Toast.makeText(ShoppingCart.this, "Server Down", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(ShoppingCart.this, "" + result, Toast.LENGTH_SHORT).show();
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


    private class CartProductsCallback implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {
            ShoppingCartBaseHolder shoppingCartBaseHolder = (ShoppingCartBaseHolder) result;

            if (shoppingCartBaseHolder.getStatus().equalsIgnoreCase("success")) {

                if (stockCountFlag) {
                    stockCountFlag = false;
                    flag = true;
                    builder = new StringBuilder();
                    count = 0;
                    flag = true;
                    if (shoppingCartBaseHolder.getMessage().getCartdetails().size() > 0) {
                        for (int i = 0; i < shoppingCartBaseHolder.getMessage().getCartdetails().size(); i++) {
                            shoppingcartList = new ArrayList<>();
                            shoppingcartList = shoppingCartBaseHolder.getMessage().getCartdetails();
                            if (shoppingcartList.get(i).getStockCount().equalsIgnoreCase("0")) {
                                count++;
                                flag = false;
                                if (count == 1) {
                                    builder.append(shoppingcartList.get(i).getName());
                                } else {
                                    builder.append("," + shoppingcartList.get(i).getName());
                                }

                            }
                        }
                        if (!flag) {
                            if (count == 1) {
                                Toast.makeText(ShoppingCart.this, builder.toString() + " is out of stock!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ShoppingCart.this, builder.toString() + " are out of stock!", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                    if (flag) {
                        initiatePopupWindow();
                    }

                } else {

                    if (UtilValidate.isNotNull(shoppingCartBaseHolder.getMessage() != null)) {
                        if (shoppingCartBaseHolder.getMessage().getCartdetails().isEmpty()) {
                            Log.e("####", "The list is empty");
                            emptyCart.setVisibility(View.VISIBLE);
                            clearAll.setVisibility(View.INVISIBLE);
                            shoppingCart.setVisibility(View.GONE);
                        } else {
                            shoppingCart.setVisibility(View.VISIBLE);
                        }
                    }

                    if (UtilValidate.isNotNull(shoppingCartBaseHolder.getMessage())) {
                        if (UtilValidate.isNotNull(shoppingCartBaseHolder.getMessage().getCartdetails())) {
                            checkoutProductsList1.clear();
                            checkoutProductsList1.addAll(shoppingCartBaseHolder.getMessage().getCartdetails());
                            totalAmount = 0.00;
                            savingsAmount = 0.00;


                            /*if (UtilValidate.isNotNull(DbManager.getInstance().getCurrentUserDetails())) {
                                if ((!DbManager.getInstance().getCurrentUserDetails().getCreated().equals(""))) {
                                    if (DbManager.getInstance().getCurrentUserDetails().getMembership_status().equalsIgnoreCase("true")) {
                                        Log.e("***MEMBER***", "LOGGED IN");
                                        //Toast.makeText(ShoppingCart.this,"member",Toast.LENGTH_SHORT).show();
                                        total.setText("Total for Member : ");
                                        totalAmount = 0.00;
                                        for (int i = 0; i < checkoutProductsList1.size(); i++) {
                                            Double product_quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                                            Double product_amount = Double.parseDouble(checkoutProductsList1.get(i).getDiscountprice());
                                            amount = product_quantity * product_amount;
                                            totalAmount = totalAmount + amount;
                                        }
                                        // String total = String.format("%.2f", totalAmount);
                                        // TodaysParentApp.setItemTotalValue(total);

                                        for (int j = 0; j < checkoutProductsList1.size(); j++) {
                                            double promoPrice = Double.parseDouble(checkoutProductsList1.get(j).getDiscountprice());
                                            double price = Double.parseDouble(checkoutProductsList1.get(j).getPrice());
                                            double savedamount = price - promoPrice;
                                            double quantity = Double.parseDouble(checkoutProductsList1.get(j).getQuantity());
                                            double tot = savedamount * quantity;

                                            Log.e("SAVED>>", "" + tot);
                                            savingsAmount = savingsAmount + tot;

                                            Log.e("SAVINGS 1>>", ">>" + savingsAmount);
                                            // savings.setText(String.valueOf(savingsAmount));
                                        }

                                        String total = String.format("%.2f", totalAmount);
                                        TodaysParentApp.setItemTotalValue(total);
                                    } else {
                                    */
                                        /*  an expired member */

                                        /*Toast.makeText(ShoppingCart.this,"non member",Toast.LENGTH_SHORT).show();

                                        totalAmount = 0.00;
                                        total.setText(" Total : ");
                                        for (int i = 0; i < checkoutProductsList1.size(); i++) {
                                            Double product_quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                                            Double product_amount = Double.parseDouble(checkoutProductsList1.get(i).getPromoprice());
                                            amount = product_quantity * product_amount;
                                            totalAmount = totalAmount + amount;

                                            String total = String.format("%.2f", totalAmount);

                                            Log.e(total + "%^&%^&%^&%&%&", "" + totalAmount);
                                            TodaysParentApp.setItemTotalValue(total);


                                            Log.e("EXPIRED TOTAL>>>>", "" + totalAmount);
                                        }
                                        // String total = String.format("%.2f", totalAmount);
                                        //TodaysParentApp.setItemTotalValue(total);

                                        savingsAmount = 0.00;
                                        for (int j = 0; j < checkoutProductsList1.size(); j++) {
                                            double promoPrice = Double.parseDouble(checkoutProductsList1.get(j).getPromoprice());
                                            double price = Double.parseDouble(checkoutProductsList1.get(j).getPrice());
                                            double savedamount = price - promoPrice;
                                            double quantity = Double.parseDouble(checkoutProductsList1.get(j).getQuantity());
                                            double tot = savedamount * quantity;
                                            Log.e("SAVE tot 2>>>", "" + tot);
                                            savingsAmount = savingsAmount + tot;
                                            Log.e("SAVINGS 3>>", "" + savingsAmount);
                                            // savings.setText(String.valueOf(savingsAmount));
                                        }


                                    }

                                } else {
                                    //Toast.makeText(ShoppingCart.this, "new account", Toast.LENGTH_SHORT).show();

                                    total.setText(" Total : ");
                                    for (int i = 0; i < checkoutProductsList1.size(); i++) {
                                        Double product_quantity = Double.parseDouble(checkoutProductsList1.get(i).getQuantity());
                                        Double product_amount = Double.parseDouble(checkoutProductsList1.get(i).getPromoprice());
                                        amount = product_quantity * product_amount;
                                        totalAmount = totalAmount + amount;

                                        // String total = String.format("%.2f", totalAmount);
                                        //TodaysParentApp.setItemTotalValue(total);
                                    }
                                    savingsAmount = 0.00;
                                    for (int j = 0; j < checkoutProductsList1.size(); j++) {
                                        double promoPrice = Double.parseDouble(checkoutProductsList1.get(j).getPromoprice());
                                        double price = Double.parseDouble(checkoutProductsList1.get(j).getPrice());
                                        double savedamount = price - promoPrice;
                                        double quantity = Double.parseDouble(checkoutProductsList1.get(j).getQuantity());
                                        double tot = savedamount * quantity;
                                        savingsAmount = savingsAmount + tot;
                                        // savings.setText(String.valueOf(savingsAmount));
                                    }

                                    String total = String.format("%.2f", totalAmount);
                                    TodaysParentApp.setItemTotalValue(total);
                                }
                            }*/

                            for (int i = 0; i < checkoutProductsList1.size(); i++) {
                                amount = Double.parseDouble(checkoutProductsList1.get(i).getQuantity()) *
                                        Double.parseDouble(checkoutProductsList1.get(i).getDiscountprice());
                                totalAmount = totalAmount + amount;
                            }
                            TodaysParentApp.setItemTotalValue(String.format("%.2f", totalAmount));

                            for (int i = 0; i < checkoutProductsList1.size(); i++) {
                                shippingamount = Double.parseDouble(checkoutProductsList1.get(i).getQuantity()) *
                                        Double.parseDouble(checkoutProductsList1.get(i).getShippingcharge());
                                shippingTotalAmount = shippingTotalAmount + shippingamount;
                            }
                            TodaysParentApp.setShippingValue(String.format("%.2f", shippingTotalAmount));

                            for (int j = 0; j < checkoutProductsList1.size(); j++) {
                                double savedamount = Double.parseDouble(checkoutProductsList1.get(j).getPrice()) - Double.parseDouble(checkoutProductsList1.get(j).getDiscountprice());
                                double tot = savedamount * Double.parseDouble(checkoutProductsList1.get(j).getQuantity());
                                savingsAmount = savingsAmount + tot;

                            }
                            orderTotal = totalAmount + shippingTotalAmount;
                            String saving = String.format("%.2f", savingsAmount);
                            savings.setText(" " + saving);
                            String total1 = String.format("%.2f", orderTotal);
                            totalPrice.setText(" " + total1);
                            TodaysParentApp.setOrderTotalValue(String.valueOf(orderTotal));
                            shoppingCartAdapter = new ShoppingCartAdapter(ShoppingCart.this, checkoutProductsList1, totalPrice, savings, emptyCart, shoppingCart);
                            shoppingCartList.setAdapter(shoppingCartAdapter);
                            shoppingCartAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }
        }

        @Override
        public void onFinish(int responseCode, String result) {
            //  dialogTransparent.dismiss();
        }
    }

    private void initiatePopupWindow() {


        try {
            isStorePickupOnly = true;
            for (int i = 0; i <= checkoutProductsList1.size() - 1; i++) {
                product_quantity.add(checkoutProductsList1.get(i).getQuantity());
                String productId = String.valueOf(checkoutProductsList1.get(i).getId());
                product_ids.add(productId);
                color.add(checkoutProductsList1.get(i).getProduct_color());
                dimen.add(checkoutProductsList1.get(i).getProduct_dimension());
                if (checkoutProductsList1.get(i).getCollect_at_store() == 0) {
                    isStorePickupOnly = false;
                }
            }
            if (!isStorePickupOnly) {
                Intent in = new Intent(ShoppingCart.this, PopUpActivity.class);
                startActivityForResult(in, requestcode);
            } else {
                makePayment();
            }

        } catch (Exception e) {

        }
    }

    private class ShippingDetailAsyncTask implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            ShippingDetailsBaseHolder shippingDetailsBaseHolder = (ShippingDetailsBaseHolder) result;
            if (UtilValidate.isNotNull(shippingDetailsBaseHolder)) {
                if (shippingDetailsBaseHolder.getStatus().equalsIgnoreCase("success")) {
                    Log.e("^^^SUCCESS^^^", "****SUCCESS****");

                    session_id = currentlyLoggedUserDAO.getSessionId();
                    //List<String> product_quantities;
                    userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
                    userId = String.valueOf(userObjectHolder.getId());
                    for (int i = 0; i <= checkoutProductsList1.size() - 1; i++) {
                        product_quantity.add(checkoutProductsList1.get(i).getQuantity());
                        product_ids.add(String.valueOf(checkoutProductsList1.get(i).getId()));
                        color.add(String.valueOf(checkoutProductsList1.get(i).getProduct_color()));
                        dimen.add(String.valueOf(checkoutProductsList1.get(i).getProduct_dimension()));
                    }
                    PaypalManager.getInstance().getPayPalTransactionDetails(ShoppingCart.this, session_id, product_ids,
                            product_quantity, color, dimen, paymentTransactionDetailCallBack);
                }
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    /*private View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
        }
    };*/

    private void makePayment() {
        session_id = currentlyLoggedUserDAO.getSessionId();
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        userId = String.valueOf(userObjectHolder.getId());

        PaypalManager.getInstance().getPayPalTransactionDetails(ShoppingCart.this, session_id, product_ids, product_quantity, color, dimen, paymentTransactionDetailCallBack);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("RESULT_OK", "" + requestCode + resultCode);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                makePayment();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result

            }
        }


    }


}
