package com.qiito.umepal.fragments;
/**
 * Created by abin on 5/8/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.ApiConstants;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.activity.EditProfileActivity;
import com.qiito.umepal.activity.ProductDetails;
import com.qiito.umepal.activity.QRcodeActivity;
import com.qiito.umepal.adapters.MyLikesAdapter;
import com.qiito.umepal.adapters.MyPurchasesAdapter;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.holder.MembershipBaseHolder;
import com.qiito.umepal.holder.PayPalTransactionResponseHolder;
import com.qiito.umepal.holder.ProductObject;
import com.qiito.umepal.holder.PurchasedItems;
import com.qiito.umepal.holder.UserBaseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.LoginManager;
import com.qiito.umepal.managers.MyaccountProductManager;
import com.qiito.umepal.managers.PaypalManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.managers.WalletManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;

public class MyAccountFragment extends Fragment {


    private UserProfileCallback userProfileCallBack;
    private MembershipBaseHolder membershipBaseHolder;
    private ListAllMembershipCallBack listAllMembershipCallBack;
    private MyLikesAdapter myLikesAdapter;
    private MyPurchasesAdapter myPurchasesAdapter;
    private UserObjectHolder userObjectHolder;
    //private CallbackManager callbackManager;
    //private ShareDialog shareDialog;
    private FragmentTransaction myAccountFragmentTransaction;
    private TextView my_likes_btn;
    private TextView my_purchaces_btn;
    private ProgressDialog progress;
    private TextView profile_name;
    private TextView city;
    private TextView joined_date;
    private ProgressBar progressBar;
    private View content;
    private ImageView profile_pic;
    private ImageView edit_profile;
    private ListView likes_list;
    private ListView purchases_list;
    private String joinedDate;
    private List<ProductObject> likesitem = new ArrayList<>();
    private List<PurchasedItems> purchasesitemlist = new ArrayList<>();
    private String session;
    private PaymentTransactionDetailCallBack paymentTransactionDetailCallBack;
    private int offset;
    private Dialog dialogTransparent;
    private View progressview;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private LinearLayout membership;
    private LinearLayout joinmembershipnow;
    private RelativeLayout relative_web;
    private WebView webview_paypal;
    private LinearLayout myAccountLayout;
    private TextView joinmembershiptxt;
    private Button join_button;
    private Toolbar toolbar;
    private LinearLayout listLayout;
    private boolean likeListClick = true;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
    private ImageView qrcode_button;
    private LayoutInflater vi;
    private String reffereeId;

    public MyAccountFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        userObjectHolder = new UserObjectHolder();
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        profile_name.setText(userObjectHolder.getFirstName() + " " + userObjectHolder.getLastName());
        city.setText(userObjectHolder.getCity());
        joinedDate = userObjectHolder.getCreatedDate().substring(0, userObjectHolder.getCreatedDate().length() - 8);
        joined_date.setText(joinedDate);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = formatter.parse(joinedDate);
            Log.e("DATE >> ", "" + date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.e("userobjectholder>>", userObjectHolder.getProfilePic());
        if ((userObjectHolder.getProfilePic() != null) && (!userObjectHolder.getProfilePic().isEmpty())) {
            Picasso.with(getActivity()).load(userObjectHolder.getProfilePic())
                    .placeholder(R.drawable.logo_splash)
                    .error(R.drawable.logo_splash).fit().into(profile_pic);
        }
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();
        offset = 0;
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.my_account_page, container, false);
        if (isConnected(getActivity())) {
        } else {
            builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Network Unavailable!!");
            builder.setCancelable(true);
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    if (isConnected(getActivity())) {
                    }
                }

            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            alert = builder.create();
            alert.show();
        }
        TodaysParentApp.setIsinwebview("");

        initViews();
        initManagers();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        userObjectHolder = DbManager.getInstance().getCurrentUserDetails();
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();
        dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        dialogTransparent.getWindow().setGravity(Gravity.BOTTOM);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();

        MyaccountProductManager.getInstance().getProducts(getActivity(), userProfileCallBack, session, offset);


        if(DbManager.getInstance().getCurrentUserDetails().getMembershipType().equalsIgnoreCase("ADVANCE")){
            joinmembershipnow.setVisibility(View.GONE);
        }else {
            joinmembershipnow.setVisibility(View.VISIBLE);

        }
        relative_web.setVisibility(View.GONE);

        /*join membership*/
        join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserManager.getInstance().ListAllMembership(getActivity(),listAllMembershipCallBack);
            }
        });
        qrcode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent qrcode=new Intent(getActivity(), QRcodeActivity.class);
                Log.e("ume id ,.,.,",""+userObjectHolder.getUmeId());
                qrcode.putExtra("UMEID",userObjectHolder.getUmeId());
                startActivity(qrcode);
            }
        });

        /*MY LIKES*/

        my_likes_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeListClick = true;
                my_likes_btn.setBackgroundResource(R.drawable.curved_greenbutton);
                my_likes_btn.setTextColor(getResources().getColor(R.color.white));
                my_purchaces_btn.setBackgroundResource(R.color.transparent);
                my_purchaces_btn.setTextColor(getResources().getColor(R.color.dark_gray));
                likes_list.setAdapter(myLikesAdapter);
                likes_list.setEmptyView(getActivity().findViewById(R.id.No_likesitems));
                getActivity().findViewById(R.id.No_purchaseitems).setVisibility(View.INVISIBLE);

            }
        });

        likes_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (likeListClick) {
                    Intent likes = new Intent(getActivity(), ProductDetails.class);
                    likes.putExtra("productid", likesitem.get(position).getId());
                    startActivity(likes);
                }

            }
        });

        /*MY PURCHACES*/

        my_purchaces_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeListClick = false;
                my_likes_btn.setBackgroundResource(R.color.transparent);
                my_purchaces_btn.setBackgroundResource(R.drawable.curved_greenbutton);
                my_likes_btn.setTextColor(getResources().getColor(R.color.dark_gray));
                my_purchaces_btn.setTextColor(getResources().getColor(R.color.white));
                purchases_list.setAdapter(myPurchasesAdapter);
                purchases_list.setEmptyView(getActivity().findViewById(R.id.No_purchaseitems));
                getActivity().findViewById(R.id.No_likesitems).setVisibility(View.INVISIBLE);


            }
        });
        /*EDIT PROFILE*/

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent edit = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(edit);
            }
        });

        return content;
    }


    public class UserProfileCallback implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {


            final UserBaseHolder userBaseHolder = (UserBaseHolder) result;

            if (userBaseHolder.getStatus().equals("success")) {

                if (userBaseHolder.getData().getUser() != null) {

                    DbManager.getInstance().updateUserTable(userBaseHolder.getData().getUser());
                    if (userBaseHolder.getData().getUser().getFirstName() != null) {
                        profile_name.setText(userBaseHolder.getData().getUser().getFirstName() + " " + userBaseHolder.getData().getUser().getLastName());
                        joinedDate = userBaseHolder.getData().getUser().getCreatedDate().substring(0, userBaseHolder.getData().getUser().getCreatedDate().length() - 8);

                    }
                }
                    /*check member or not*/
                if (UtilValidate.isNotNull(userBaseHolder.getData())) {

                    if (UtilValidate.isNotNull(userBaseHolder.getData().getUser())) {
                        //userBaseHolder = userBaseHolder.getData().getUser();
                        DbManager.getInstance().deleteAllRowsFromUserTable();
                        DbManager.getInstance().insertIntoUserTable(userBaseHolder.getData().getUser());

                    }
                }


                if (userBaseHolder.getData().getUser().getLiked_products() != null) {

                    likesitem.clear();
                    likesitem.addAll(userBaseHolder.getData().getUser().getLiked_products());
                    myLikesAdapter = new MyLikesAdapter(getActivity(), likesitem);
                    likes_list.setAdapter(myLikesAdapter);
                    myLikesAdapter.notifyDataSetChanged();
                    likes_list.setEmptyView(getActivity().findViewById(R.id.No_likesitems));

                } else {
                    likes_list.setEmptyView(getActivity().findViewById(R.id.No_likesitems));
                }

                if (userBaseHolder.getData().getUser().getPurchased_items() != null) {
                    purchasesitemlist.clear();
                    purchasesitemlist.addAll(userBaseHolder.getData().getUser().getPurchased_items());
                    myPurchasesAdapter = new MyPurchasesAdapter(getActivity(), purchasesitemlist);


                } else {

                }

                if (UtilValidate.isNotEmpty(userBaseHolder.getData().getUser().getProfilePic())) {
                    if (userBaseHolder.getData().getUser().getProfilePic() != "") {


                        Picasso.with(getActivity())
                                .load(userBaseHolder.getData().getUser().getProfilePic())
                                .placeholder(R.drawable.logo_splash)
                                .error(R.drawable.logo_splash).fit()
                                .into(profile_pic);

                    } else {
                    }
                } else {
                    Picasso.with(getActivity()).load(R.drawable.logo_splash).into(profile_pic);
                    Log.e("33333333", "profile pic" + userBaseHolder.getData().getUser().getProfilePic());

                }


                dialogTransparent.dismiss();

            } else {
                Toast.makeText(getActivity(), " Server " + userBaseHolder.getStatus(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFinish(int responseCode, String result) {
            Toast.makeText(getActivity(), "server problem...", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("Internet Not Available!! Please check your connection")
                    .setCancelable(false)
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            MyaccountProductManager.getInstance().getProducts(getActivity(), userProfileCallBack, session, offset);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //  Action for 'NO' Button
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            //Setting the title manually
            alert.setTitle("AlertDialogExample");
            alert.show();
        }
    }

    private void initManagers() {
        userObjectHolder = new UserObjectHolder();
        userProfileCallBack = new UserProfileCallback();
        membershipBaseHolder =new MembershipBaseHolder();
        listAllMembershipCallBack = new ListAllMembershipCallBack();
        paymentTransactionDetailCallBack = new PaymentTransactionDetailCallBack();
    }


    private void initViews() {


        profile_name = (TextView) content.findViewById(R.id.my_account_username);
        city = (TextView) content.findViewById(R.id.my_account_user_location);
        joined_date = (TextView) content.findViewById(R.id.my_account_user_joining_date);
        profile_pic = (ImageView) content.findViewById(R.id.imageview_Myimage);
        city = (TextView) content.findViewById(R.id.my_account_user_location);
        joined_date = (TextView) content.findViewById(R.id.my_account_user_joining_date);
        my_likes_btn = (TextView) content.findViewById(R.id.my_account_my_likes_btn);
        my_purchaces_btn = (TextView) content.findViewById(R.id.my_account_my_purchases_btn);
        edit_profile = (ImageView) content.findViewById(R.id.edit_profile_imgview);
        likes_list = (ListView) content.findViewById(R.id.list_view_my_likes);
        purchases_list = (ListView) content.findViewById(R.id.list_view_my_likes);
        membership = (LinearLayout) content.findViewById(R.id.joinmembership);
        join_button = (Button) content.findViewById(R.id.join_button);
        progressBar = (ProgressBar) content.findViewById(R.id.progressBar2);
        relative_web = (RelativeLayout) content.findViewById(R.id.relative_web);
        joinmembershipnow = (LinearLayout) content.findViewById(R.id.joinmembership);
        webview_paypal = (WebView) content.findViewById(R.id.webview_paypal);
        listLayout = (LinearLayout) content.findViewById(R.id.list_layout);
        joinmembershiptxt = (TextView) content.findViewById(R.id.joinmembershiptxt);
        qrcode_button = (ImageView)content.findViewById(R.id.qrcode_button);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {

        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.removeItem(R.id.action_search);
        // Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //  Log.e("", "in onOptionsItemSelected");
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private class PaymentTransactionDetailCallBack implements AsyncTaskCallBack {


        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub
            PayPalTransactionResponseHolder responseHolder = (PayPalTransactionResponseHolder) result;

            Log.e("$$", " in call back of payment>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            userObjectHolder = DbManager.getInstance().getCurrentUserDetails();

            if (UtilValidate.isNotNull(responseHolder)) {

                if (responseHolder.getCode() == WebResponseConstants.CodeFromApi.OK) {

                    if (UtilValidate.isNotNull(responseHolder.getData())) {

                        Log.e("$$", "response not null");
                        progressBar.setVisibility(View.VISIBLE);
                        webview_paypal.setVisibility(View.VISIBLE);
                        TodaysParentApp.setIsinwebview("yes");
                        final String URL = responseHolder.getData().getTransaction_url().toString();
                        Log.e("URL>>", URL);

                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                webview_paypal.clearCache(true);
                                webview_paypal.clearHistory();
                                webview_paypal.canGoBack();
                                webview_paypal.getSettings().getJavaScriptEnabled();
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
                                        Log.e("url1>>", url);

                                    }

                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        // TODO Auto-generated method stub
                                        progressBar.setVisibility(View.GONE);
                                        super.onPageFinished(view, url);
                                        Log.e("url2>>", url);
                                    }

                                    @Override
                                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                        // TODO Auto-generated method stub

                                        Log.e("url>>", url);

                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/nmembersuccess")) {
                                            view.loadUrl(url);
                                            Log.e("inside success", ">>>>>>>");
                                            webview_paypal.setVisibility(View.GONE);
                                            toolbar.setVisibility(View.VISIBLE);
                                            Toast.makeText(getActivity(), "Payment Completed Successfully!", Toast.LENGTH_LONG).show();
                                            myAccountFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                                            myAccountFragmentTransaction.replace(R.id.container, new MyAccountFragment(), "fragment");
                                            myAccountFragmentTransaction.commit();
                                            //WalletManager.getInstance().getWalletData(getActivity(), DbManager.getInstance().getCurrentUserDetails().getSession_id(), walletCallBack);

                                            //finish();
                                        } else {
                                            view.loadUrl(url);
                                        }

                                        if (url.contains(ApiConstants.BASE_URL + "api/paypal/success")) {

                                            Log.e("%%%%", "SUCCESS");
                                            webview_paypal.setVisibility(View.GONE);
                                            toolbar.setVisibility(View.VISIBLE);
                                            Fragment frg = null;
                                            myAccountFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                                            myAccountFragmentTransaction.replace(R.id.container, new MyAccountFragment(), "fragment");
                                            myAccountFragmentTransaction.commit();

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
                                        super.onLoadResource(view, url);
                                        //	Log.i(TAG, "onLoadResource() "+ url);
                                    }

                                });

                                webview_paypal.loadUrl(URL);

                            }
                        });


                    } else {
                        Log.e("response", "is null");
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

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //callbackManager.onActivityResult(requestCode, resultCode, data);

    }*/

    private class ListAllMembershipCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {
            membershipBaseHolder =(MembershipBaseHolder) result;
            if(membershipBaseHolder.getStatus().equalsIgnoreCase("success")){
                Log.e("::::::","success>>>>>");

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.membership_upgrade_dialog, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                //popupWindow.setAnimationStyle(R.style.dialog_animation);
                popupWindow.update();
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                // popupWindow.setAnimationStyle(R.style.dialog_animation);
                final Button close = (Button) popupView.findViewById(R.id.cancel_button);
                final LinearLayout linearLayout=(LinearLayout) popupView.findViewById(R.id.membership_detail_layout);

                linearLayout.removeAllViews();

                vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout layout = new LinearLayout(getActivity());
                layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


                if (membershipBaseHolder != null) {
                    for (int i = 0; i < membershipBaseHolder.getData().size(); i++) {
                        Log.e("1111111",""+DbManager.getInstance().getCurrentUserDetails().getMembershipType());
                        Log.e("2222222",""+membershipBaseHolder.getData().get(i).getMembershipname());

                        if (DbManager.getInstance().getCurrentUserDetails().getMembershipType() == membershipBaseHolder.getData().get(i).getMembershipname()) {
                            continue;
                        } else {

                            final int pos = i;
                            View v = vi.inflate(R.layout.membership_upgrade_button, null);
                            final TextView membership = (TextView) v.findViewById(R.id.membershipTypeTxt);
                            final TextView price = (TextView) v.findViewById(R.id.textView);
                            final Button upgradeButton = (Button) v.findViewById(R.id.button);


                            if (membershipBaseHolder.getData().get(i) != null) {
                                if (membershipBaseHolder.getData().get(i).getMembershipname() != null) {
                                    membership.setText(membershipBaseHolder.getData().get(i).getMembershipname());
                                }
                                if (membershipBaseHolder.getData().get(i).getPrice() != null) {
                                    price.setText(membershipBaseHolder.getData().get(i).getPrice());
                                }
                            }

                            upgradeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popupWindow.dismiss();
                                    reffereeId = String.valueOf(+DbManager.getInstance().getCurrentUserDetails().getId());
                                    LoginManager.getInstance().membershipPaypal(getActivity(), String.valueOf(membershipBaseHolder.getData().get(pos).getId()), reffereeId,"", paymentTransactionDetailCallBack);
                                }
                            });
                                linearLayout.addView(v, i,
                                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        }
                    }
                }



                close.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });


            }else {
                Log.e("::::::","error>>>>>");

            }

        }

        @Override
        public void onFinish(int responseCode, String result) {
            Log.e("::::::","finish>>>>>");

        }
    }
}