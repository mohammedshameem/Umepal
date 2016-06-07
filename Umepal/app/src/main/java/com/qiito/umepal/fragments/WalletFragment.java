package com.qiito.umepal.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.WalletExpandableListViewAdapter;
import com.qiito.umepal.adapter.WalletListViewAdapter;
import com.qiito.umepal.holder.MembershipBaseHolder;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.RebateDetailsHolder;
import com.qiito.umepal.holder.RebateListHolder;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.holder.WalletBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.managers.WalletManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shiya on 30/5/16.
 */
public class WalletFragment extends Fragment {

    private View content;
    private TextView totalAvailableBalance;
    private TextView totalLeBadgerBalance;
    private LinearLayout topUpLayout;
    private LinearLayout upgradeMembershipLayout;
    private LinearLayout rebatesLayout;
    private LinearLayout commissionsLayout;
    private View rebatesView;
    private View commissionsView;
    private ExpandableListView expandableListView;
    private ListView listView;
    private WalletExpandableListViewAdapter walletExpandableListViewAdapter;
    private WalletListViewAdapter walletListViewAdapter;
    private WalletCallBack walletCallBack = new WalletCallBack();
    private WalletBaseHolder walletBaseHolder = new WalletBaseHolder();
    private WebView webview_paypal;
    private LinearLayout walletMainLayout;
    private LinearLayout paypal_webviews_layout;
    private ProgressBar progressBar1;
    private RotateLoading rotateLoading;
    private TopUpCallBack topUpCallBack;
    private ListAllMembershipCallBack listAllMembershipCallBack;
    private MembershipBaseHolder membershipBaseHolder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.wallet_layout, container, false);
        initViews();
        rotateLoading.start();
        WalletManager.getInstance().getWalletData(getActivity(), DbManager.getInstance().getCurrentUserDetails().getSession_id(), walletCallBack);
        expandableListView.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        rebatesLayout.setOnClickListener(rebateClickListener);
        commissionsLayout.setOnClickListener(commissionClickListener);
        topUpLayout.setOnClickListener(topUpListner);
        upgradeMembershipLayout.setOnClickListener(upgradeListener);

        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViews() {
        totalAvailableBalance = (TextView) content.findViewById(R.id.total_available_balance);
        totalLeBadgerBalance = (TextView) content.findViewById(R.id.total_lebadger_balance);
        topUpLayout = (LinearLayout) content.findViewById(R.id.topup_layout);
        upgradeMembershipLayout = (LinearLayout) content.findViewById(R.id.upgrade_membership_layout);
        rebatesLayout = (LinearLayout) content.findViewById(R.id.rebates_layout);
        commissionsLayout = (LinearLayout) content.findViewById(R.id.commissions_layout);
        rebatesView = (View) content.findViewById(R.id.rebates_view);
        commissionsView = (View) content.findViewById(R.id.commissions_view);
        expandableListView = (ExpandableListView) content.findViewById(R.id.expandable_listview);
        listView = (ListView) content.findViewById(R.id.listview);
        webview_paypal = (WebView) content.findViewById(R.id.webview_paypal);
        walletMainLayout = (LinearLayout) content.findViewById(R.id.wallet_main_layout);
        paypal_webviews_layout = (LinearLayout) content.findViewById(R.id.paypal_webviews_layout);
        progressBar1 = (ProgressBar) content.findViewById(R.id.progress_bar);
        rotateLoading = (RotateLoading) content.findViewById(R.id.rotateloading_history);

        topUpCallBack = new TopUpCallBack();
        listAllMembershipCallBack = new ListAllMembershipCallBack();

    }

    View.OnClickListener rebateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rebatesView.setVisibility(View.VISIBLE);
            commissionsView.setVisibility(View.GONE);
            expandableListView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    };

    View.OnClickListener commissionClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rebatesView.setVisibility(View.GONE);
            commissionsView.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

        }
    };

    View.OnClickListener topUpListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            //WalletManager.getInstance().topUp();

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View popupView = inflater.inflate(R.layout.top_up_popup, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.update();
            popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            final EditText amount = (EditText) popupView.findViewById(R.id.amount_edit_text);
            final Button done = (Button) popupView.findViewById(R.id.done_button);
            final Button cancel = (Button) popupView.findViewById(R.id.cancel_button);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (amount.getText() != null) {
                        if (!amount.getText().equals("")) {
                            popupWindow.dismiss();
                            WalletManager.getInstance().topUp(getActivity(), DbManager.getInstance().getCurrentUserDetails().getSession_id(), amount.getText().toString(), topUpCallBack);

                        }
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                }
            });


        }
    };

    View.OnClickListener upgradeListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            UserManager.getInstance().ListAllMembership(getActivity(), listAllMembershipCallBack);


        }
    };

    private class WalletCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            walletBaseHolder = (WalletBaseHolder) result;
            if (walletBaseHolder != null) {
                if (walletBaseHolder.getStatus() != null) {

                    if (walletBaseHolder.getStatus().equalsIgnoreCase("success")) {

                        if (walletBaseHolder.getData() != null) {

                            if (walletBaseHolder.getData().getTotal_credit_amount() != null) {
                                if (walletBaseHolder.getData().getTotal_credit_amount() != "") {
                                    totalAvailableBalance.setText(getString(R.string.dollar) + " " + walletBaseHolder.getData().getTotal_credit_amount());
                                } else {
                                    totalAvailableBalance.setText("$ 00.00");
                                }
                            }

                            if (walletBaseHolder.getData().getLedger_amount() != null) {

                                if (walletBaseHolder.getData().getLedger_amount() != "") {

                                    totalLeBadgerBalance.setText(getString(R.string.dollar) + " " + walletBaseHolder.getData().getLedger_amount());
                                }
                            }

                            if (walletBaseHolder.getData().getRebate() != null) {

                                if (!walletBaseHolder.getData().getRebate().isEmpty()) {

                                    walletExpandableListViewAdapter = new WalletExpandableListViewAdapter(getActivity(), walletBaseHolder.getData().getRebate());
                                    expandableListView.setAdapter(walletExpandableListViewAdapter);

                                }
                            }

                            if (walletBaseHolder.getData().getCommission() != null) {

                                if (!walletBaseHolder.getData().getCommission().isEmpty()) {

                                    walletListViewAdapter = new WalletListViewAdapter(getActivity(), walletBaseHolder.getData().getCommission());
                                    listView.setAdapter(walletListViewAdapter);

                                }
                            }


                        }
                    }
                }
            }
            rotateLoading.stop();
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


    private class TopUpCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub
            // dialog.dismiss();
            PayPalTransactionResponseHolder responseHolder = (PayPalTransactionResponseHolder) result;
            Log.e("$$", " in call back of payment>>>>>>>>>>>>>>>>>>>>>>    " + responseHolder.getStatus() + " ### " + responseHolder.getMessage() + "  >>> " + responseHolder.getData());
            if (UtilValidate.isNotNull(responseHolder)) {
                if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.OK) {
                    if (UtilValidate.isNotNull(responseHolder.getData())) {
                        Log.e("$$", "response not null");
                        paypal_webviews_layout.setVisibility(View.VISIBLE);
                        walletMainLayout.setVisibility(View.GONE);
                        progressBar1.setVisibility(View.VISIBLE);
                        final String URL = responseHolder.getData().getTransaction_url().toString();
                        getActivity().runOnUiThread(new Runnable() {

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
                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/creditnsuccess")) {
                                            view.loadUrl(url);
                                            Log.e("inside success", ">>>>>>>");
                                            paypal_webviews_layout.setVisibility(View.GONE);
                                            walletMainLayout.setVisibility(View.VISIBLE);
                                            Toast.makeText(getActivity(), "Payment Completed Successfully!", Toast.LENGTH_LONG).show();
                                            rotateLoading.start();
                                            WalletManager.getInstance().getWalletData(getActivity(), DbManager.getInstance().getCurrentUserDetails().getSession_id(), walletCallBack);

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
                                       /* Intent in = new Intent(getActivity(), ShoppingCart.class);
                                        startActivity(in);
                                        finish();*/
                                            Toast.makeText(getActivity(), "Problem !!!", Toast.LENGTH_LONG).show();

                                        }


                                    }

                                });
                                webview_paypal.loadUrl(URL);
                            }
                        });


                    } else {
                    }

                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_AUTHORIZED) {

                    Toast.makeText(getActivity(),
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.METHODNOT_ALLOWED) {

                    Toast.makeText(getActivity(),
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.NOT_ACCEPTABLE) {

                    Toast.makeText(getActivity(),
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.PRECONDITIONFAILED) {

                    Toast.makeText(getActivity(),
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.SERVICE_UNAVAILABLE) {

                    Toast.makeText(getActivity(),
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                } else if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.UN_SUCCESSFULL) {

                    Toast.makeText(getActivity(),
                            "" + responseHolder.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }


            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }


    private class ListAllMembershipCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {
            membershipBaseHolder = (MembershipBaseHolder) result;
            if (membershipBaseHolder.getStatus().equalsIgnoreCase("success")) {
                Log.e("::::::", "success>>>>>");


                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View popupView = inflater.inflate(R.layout.membership_upgrade_dialog, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.update();
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                final Button cancel = (Button) popupView.findViewById(R.id.cancel_button);
                final LinearLayout membershipDetailLayout = (LinearLayout) popupView.findViewById(R.id.membership_detail_layout);

                if (membershipBaseHolder.getData() != null) {
                    if (!membershipBaseHolder.getData().isEmpty()) {

                    }
                }


            } else {
                Log.e("::::::", "error>>>>>");

            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            Log.e("::::::", "finish>>>>>");

        }
    }
}
