package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.IntentConstants;
import com.qiito.umepal.Constants.Notification;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.NavDrawerListAdapter;
import com.qiito.umepal.adapter.NotificationListAdapter;
import com.qiito.umepal.dao.CurrentlyLoggedUserDAO;
import com.qiito.umepal.fragments.Contact_us;
import com.qiito.umepal.fragments.General_terms;
import com.qiito.umepal.fragments.MyAccountFragment;
import com.qiito.umepal.fragments.NavigationDrawerFragment;
import com.qiito.umepal.fragments.NewRefereeFragment;
import com.qiito.umepal.fragments.Notifica;
import com.qiito.umepal.fragments.OrderHistory;
import com.qiito.umepal.fragments.RealTimePaymentFragment;
import com.qiito.umepal.fragments.SlidingFragment;
import com.qiito.umepal.fragments.WalletFragment;
import com.qiito.umepal.holder.Category;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.holder.UserLogoutHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.NotificationManager;
import com.qiito.umepal.managers.UserManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private boolean disableButtonFlag = false;
    private DeleteAllNotificationCallBack deleteAllNotificationCallBack;
    private NotificationListAdapter notificationListAdapter;
    private Notifica notifica;
    private LogoutCallBack logoutCallback;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private NavDrawerListAdapter adapter;
    private NotificationBaseHolder notificationBaseHolder;
    private NotificationAsynchTaskCallBack notificationasynchTaskCallBack;
    private NotificationListAdapter notificationsListAdapter;
    private List<ProductNotificationBaseHolder> productNotificationList = new ArrayList<>();
    private List<Category> categorynames = new ArrayList<>();
    private String fragmentTrueFragment = "Browse";
    //private List<ProductNotificationBaseHolder> notificationList = new ArrayList<ProductNotificationBaseHolder>();
    private List<ProductNotificationBaseHolder> notificationLists = new ArrayList<ProductNotificationBaseHolder>();
    private FragmentTransaction searchFragmentTransaction;
    private FragmentTransaction myAccountFragmentTransaction;
    private FragmentTransaction browseFragmentTransaction;
    private FragmentTransaction shoppingCartFragmentTransaction;
    private FragmentTransaction notificationFragmentTransaction;
    private FragmentTransaction orderHistoryFragmentTransaction;
    private FragmentTransaction customerSupportFragmentTransaction;
    private FragmentTransaction inviteFriendsFragmentTransaction;
    private FragmentTransaction contactusFragmentTransaction;
    private FragmentTransaction generalTermsFragmentTransaction;
    private FragmentTransaction searchTransation;
    private Dialog dialogTransparent;
    private View progressview;
    private ProgressDialog pDialog, deleteDialog;
    private LinearLayout search_layout;
    private LinearLayout search;
    private LinearLayout yesLinear;
    private LinearLayout cancelLinear;
    private LinearLayout notification;
    private LinearLayout noNotification;
    private LinearLayout notificationCount;
    private DrawerLayout mDrawerLayout;
    private LayoutInflater inflater;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private EditText edtSearch;
    private EditText search_text;
    private TextView action_bar_title;
    private TextView clearAll;
    private ImageView carticon;
    private ImageView searchicon;
    private ImageView dosearchicon;
    private ListView mDrawerList;
    private View logoutLayout;
    private View view;
    private ActionBar action;
    private Toolbar toolbar;
    private String searchword;
    private String session;
    private static final String TAG = Notifica.class.getName();
    private static final int REQUEST_CODE = 1;
    private String appLinkURL = "https://fb.me/999378886770057";
    private String previewImageURL = "";
    private List<ProductNotificationBaseHolder> notificationList = new ArrayList<ProductNotificationBaseHolder>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected(MainActivity.this)) {

        } else {

            builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Network Unavailable!!");
            builder.setCancelable(true);
            builder.setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();

                            if (isConnected(MainActivity.this)) {

                            } else {
                                finish();
                            }

                        }

                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // dialogTransparent.show();
                            dialog.cancel();
                        }
                    });

            alert = builder.create();
            alert.show();
        }
        TodaysParentApp.setIsinwebview("");

        initView();
        initManager();
        searchicon.setVisibility(View.VISIBLE);
        clearAll.setVisibility(View.INVISIBLE);
        session = CurrentlyLoggedUserDAO.getInstance().getSessionId();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        action = getSupportActionBar();
        action.setDisplayShowCustomEnabled(true);
        action.setCustomView(R.layout.search_bar);
        search.setVisibility(View.GONE);
        edtSearch = (EditText) action.getCustomView().findViewById(R.id.edtSearch);
        if (session != null) {
            if (!session.equals("")) {
                carticon.setVisibility(View.VISIBLE);
            }
        }
        carticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShoppingCart.class);
                startActivity(intent);

            }
        });

        searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                action_bar_title.setVisibility(View.GONE);
                carticon.setVisibility(View.GONE);
                searchicon.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
                open_keyboard();


            }
        });
        dosearchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                action_bar_title.setVisibility(View.VISIBLE);
                //carticon.setVisibility(View.VISIBLE);
                searchicon.setVisibility(View.VISIBLE);
                searchword = search_text.getText().toString();
                search.setVisibility(View.GONE);
                if (searchword.equals("")) {
                    search.setVisibility(View.GONE);
                    action_bar_title.setVisibility(View.VISIBLE);
                    searchicon.setVisibility(View.VISIBLE);
                    if (CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentTable()) {
                        carticon.setVisibility(View.VISIBLE);
                    } else {
                        carticon.setVisibility(View.GONE);
                    }
                    close_keyboard();
                } else {
                    search_text.setText("");
                    Intent searchIntent = new Intent(MainActivity.this, SearchProductActivity.class);
                    searchIntent.putExtra("searchWord", searchword);
                    startActivity(searchIntent);
                }

            }
        });
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll.setClickable(false);
                dialogTransparent = new Dialog(MainActivity.this, android.R.style.Theme_Black);
                progressview = LayoutInflater.from(MainActivity.this).inflate(R.layout.progrssview, null);
                dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
                dialogTransparent.setContentView(progressview);
                dialogTransparent.show();
                productNotificationList.clear();
                // NotificationManager.getInstance().deleteSingleNotification(MainActivity.this, deleteAllNotificationCallBack, DbManager.getInstance().getSessionId(), "");
                NotificationManager.getInstance().deleteAllNotification(MainActivity.this, deleteAllNotificationCallBack, session);

            }
        });
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        action_bar_title.setVisibility(View.VISIBLE);
        //carticon.setVisibility(View.VISIBLE);
        searchicon.setVisibility(View.VISIBLE);
        action_bar_title.setText("Discover");

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, new SlidingFragment()).commit();


    }

    private void initManager() {
        logoutCallback = new LogoutCallBack();
        deleteAllNotificationCallBack = new DeleteAllNotificationCallBack();
        notificationasynchTaskCallBack = new NotificationAsynchTaskCallBack();

    }

    private void initView() {
        action_bar_title = (TextView) findViewById(R.id.app_action_bar_title);
        search = (LinearLayout) findViewById(R.id.searchlay);
        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        carticon = (ImageView) findViewById(R.id.carticon);
        searchicon = (ImageView) findViewById(R.id.search);
        dosearchicon = (ImageView) findViewById(R.id.searchicon);
        notificationCount = (LinearLayout) findViewById(R.id.linear_num_of_notification);
        clearAll = (TextView) findViewById(R.id.clear_alls);
        notification = (LinearLayout) findViewById(R.id.notification_layout);
        noNotification = (LinearLayout) findViewById(R.id.no_notification_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_listview);
        search_text = (EditText) findViewById(R.id.search_text);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mNavigationDrawerFragment.getDrawerToggle().syncState();


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        action = getSupportActionBar();
        switch (position) {

            /*** Browse ***/
            case 0:
                fragmentTrueFragment = "Browse";
                toolbar.setVisibility(View.VISIBLE);
                action_bar_title.setText("Discover");
                action_bar_title.setVisibility(View.VISIBLE);
                action.setDisplayShowCustomEnabled(true);
                search.setVisibility(View.GONE);
                searchicon.setVisibility(View.VISIBLE);
                clearAll.setVisibility(View.GONE);
                //notificationCount.setVisibility(View.GONE);
                action = getSupportActionBar();
                action.setDisplayShowCustomEnabled(true);
                action.setCustomView(R.layout.search_bar);
                edtSearch = (EditText) action.getCustomView().findViewById(R.id.edtSearch);
                Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent1);


              /*  fragmentTrueFragment="Search";
                close_keyboard();
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                toolbar.setVisibility(View.VISIBLE);
                action.setDisplayShowCustomEnabled(true); *//*  Remove Search view  *//*
                search.setVisibility(View.VISIBLE);
                action_bar_title.setVisibility(View.GONE);
                //carticon.setVisibility(View.GONE);
                searchicon.setVisibility(View.GONE);
                clearAll.setVisibility(View.INVISIBLE);
                carticon.setVisibility(View.GONE);*/
                break;

            /*** Shopping Cart ***/
            case 1:
                close_keyboard();
                if(session!=null){
                    if(!session.equalsIgnoreCase("")){
                        search.setVisibility(View.GONE);
                        action_bar_title.setVisibility(View.VISIBLE);
                        action.setDisplayShowCustomEnabled(false);/*  Remove Search view  */
                        carticon.setVisibility(View.GONE);
                        searchicon.setVisibility(View.GONE);
                        clearAll.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(MainActivity.this, ShoppingCart.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivityForResult(intent, 1);
                    }
                }else {
                    fragmentTrueFragment = "INVITE";
                    toolbar.setVisibility(View.VISIBLE);
                    action_bar_title.setVisibility(View.VISIBLE);
                    search.setVisibility(View.GONE);
                    action.setDisplayShowCustomEnabled(false);  //Remove Search view
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Hey check out my app at:  https://play.google.com/store/apps/details?id=com.qiito.umepal&hl=en");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }


                break;

            /*** Real Time Payment ***/
            case 2:
                close_keyboard();
                if(session!=null) {
                    if (!session.equalsIgnoreCase("")) {
                        fragmentTrueFragment = "RealTimePayment";
                        toolbar.setVisibility(View.VISIBLE);
                        action_bar_title.setVisibility(View.VISIBLE);
                        action_bar_title.setText("Real Time Payment");
                        search.setVisibility(View.GONE);
                        action.setDisplayShowCustomEnabled(false);
                        myAccountFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                        myAccountFragmentTransaction.replace(R.id.container, new RealTimePaymentFragment(), "fragment");
                        myAccountFragmentTransaction.commit();
                        searchicon.setVisibility(View.GONE);
                        carticon.setVisibility(View.GONE);
                        clearAll.setVisibility(View.INVISIBLE);
                    }
                }else {

                }

                break;

            /*** My Account ***/
            case 3:
                close_keyboard();
                if(session!=null) {
                    if (!session.equalsIgnoreCase("")) {
                        fragmentTrueFragment = "MyAccount";
                        toolbar.setVisibility(View.VISIBLE);
                        action_bar_title.setVisibility(View.VISIBLE);
                        action_bar_title.setText("My Account");
                        search.setVisibility(View.GONE);
                        action.setDisplayShowCustomEnabled(false);
                        myAccountFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                        myAccountFragmentTransaction.replace(R.id.container, new MyAccountFragment(), "fragment");
                        myAccountFragmentTransaction.commit();
                        searchicon.setVisibility(View.GONE);
                        carticon.setVisibility(View.GONE);
                        clearAll.setVisibility(View.INVISIBLE);
                    }
                }else {
                    fragmentTrueFragment = "Contact Us";
                    close_keyboard();
                    toolbar.setVisibility(View.VISIBLE);
                    action_bar_title.setVisibility(View.VISIBLE);
                    action_bar_title.setText("Contact Us");
                    searchicon.setVisibility(View.GONE);
                    carticon.setVisibility(View.GONE);
                    clearAll.setVisibility(View.INVISIBLE);
                    action.setDisplayShowCustomEnabled(false);         /*Remove Search view*/
                    generalTermsFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    generalTermsFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                    generalTermsFragmentTransaction.replace(R.id.container, new Contact_us(), "fragment");
                    generalTermsFragmentTransaction.commit();
                }

                break;

            /*wallet*/
            case 4:
                close_keyboard();
                if(session!=null) {
                    if (!session.equalsIgnoreCase("")) {
                        fragmentTrueFragment = "Wallet";
                        toolbar.setVisibility(View.VISIBLE);
                        action_bar_title.setVisibility(View.VISIBLE);
                        action_bar_title.setText("WALLET");
                        search.setVisibility(View.GONE);
                        action.setDisplayShowCustomEnabled(false);
                        myAccountFragmentTransaction = getSupportFragmentManager().beginTransaction();
                        myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                        myAccountFragmentTransaction.replace(R.id.container, new WalletFragment(), "fragment");
                        myAccountFragmentTransaction.commit();
                        searchicon.setVisibility(View.GONE);
                        carticon.setVisibility(View.GONE);
                        clearAll.setVisibility(View.INVISIBLE);
                    }
                }else {
                    fragmentTrueFragment = "General";
                    close_keyboard();
                    toolbar.setVisibility(View.VISIBLE);
                    clearAll.setVisibility(View.INVISIBLE);
                    action_bar_title.setVisibility(View.VISIBLE);
                    carticon.setVisibility(View.GONE);
                    searchicon.setVisibility(View.GONE);
                    action_bar_title.setText("General Terms");
                    search.setVisibility(View.GONE);
                    action.setDisplayShowCustomEnabled(false);         /*Remove Search view*/
                    generalTermsFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    generalTermsFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                    generalTermsFragmentTransaction.replace(R.id.container, new General_terms(), "fragment");
                    generalTermsFragmentTransaction.commit();
                }
                break;

            /*new refferee*/
            case 5:
                close_keyboard();
                if(session!=null){
                    if(!session.equalsIgnoreCase("")){

                fragmentTrueFragment = "New Referee";
                toolbar.setVisibility(View.VISIBLE);
                action_bar_title.setVisibility(View.VISIBLE);
                action_bar_title.setText("New Referee");
                search.setVisibility(View.GONE);
                action.setDisplayShowCustomEnabled(false);
                myAccountFragmentTransaction = getSupportFragmentManager().beginTransaction();
                myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                myAccountFragmentTransaction.replace(R.id.container, new NewRefereeFragment(), "fragment");
                myAccountFragmentTransaction.commit();
                searchicon.setVisibility(View.GONE);
                carticon.setVisibility(View.GONE);
                clearAll.setVisibility(View.INVISIBLE);
                    }
                }else {
                    loginPopup();
                }
                break;

            /*** Notification ***/
            case 6:
                fragmentTrueFragment = "Notification";
                action_bar_title.setText("Notification");
                search.setVisibility(View.GONE);
                action.setDisplayShowCustomEnabled(false);
                notificationFragmentTransaction = getSupportFragmentManager().beginTransaction();
                notificationFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                notificationFragmentTransaction.replace(R.id.container, new Notifica(), "fragment");
                carticon.setVisibility(View.GONE);
                searchicon.setVisibility(View.GONE);

                NotificationManager.getInstance().getAllNotification(MainActivity.this,
                        DbManager.getInstance().getSessionId(),
                        notificationasynchTaskCallBack, REQUEST_CODE, pDialog);
                clearAll.setVisibility(View.VISIBLE);
                notificationFragmentTransaction.commit();
                Log.e("user", "notification");


                break;

            /*Order History*/
            case 7:
                close_keyboard();
                Log.e("order", "history");
                fragmentTrueFragment = "Order History";
                toolbar.setVisibility(View.VISIBLE);
                action_bar_title.setVisibility(View.VISIBLE);
                action_bar_title.setText("Order History");
                search.setVisibility(View.GONE);
                action.setDisplayShowCustomEnabled(false);
                orderHistoryFragmentTransaction = getSupportFragmentManager().beginTransaction();
                carticon.setVisibility(View.GONE);
                searchicon.setVisibility(View.GONE);
                clearAll.setVisibility(View.INVISIBLE);
                orderHistoryFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                orderHistoryFragmentTransaction.replace(R.id.container, new OrderHistory(), "fragment");
                orderHistoryFragmentTransaction.commit();


                break;

            /*Invite Friends*/
            case 8:
                close_keyboard();
                fragmentTrueFragment = "INVITE";
                toolbar.setVisibility(View.VISIBLE);
                action_bar_title.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                action.setDisplayShowCustomEnabled(false);  //Remove Search view
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out my app at:  https://play.google.com/store/apps/details?id=com.qiito.umepal&hl=en");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            /*** Customer Support ***/
            case 9:
                close_keyboard();
                action_bar_title.setVisibility(View.VISIBLE);
                // break;


            /*** Contact Us ***/
            case 10:

                fragmentTrueFragment = "Contact Us";
                close_keyboard();
                toolbar.setVisibility(View.VISIBLE);
                action_bar_title.setVisibility(View.VISIBLE);
                action_bar_title.setText("Contact Us");
                searchicon.setVisibility(View.GONE);
                carticon.setVisibility(View.GONE);
                clearAll.setVisibility(View.INVISIBLE);
                action.setDisplayShowCustomEnabled(false);         /*Remove Search view*/
                generalTermsFragmentTransaction = getSupportFragmentManager().beginTransaction();
                generalTermsFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                generalTermsFragmentTransaction.replace(R.id.container, new Contact_us(), "fragment");
                generalTermsFragmentTransaction.commit();
                break;

            /*** General ***/
            case 11:

                fragmentTrueFragment = "General";
                close_keyboard();
                toolbar.setVisibility(View.VISIBLE);
                clearAll.setVisibility(View.INVISIBLE);
                action_bar_title.setVisibility(View.VISIBLE);
                carticon.setVisibility(View.GONE);
                searchicon.setVisibility(View.GONE);
                action_bar_title.setText("General Terms");
                search.setVisibility(View.GONE);
                action.setDisplayShowCustomEnabled(false);         /*Remove Search view*/
                generalTermsFragmentTransaction = getSupportFragmentManager().beginTransaction();
                generalTermsFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                generalTermsFragmentTransaction.replace(R.id.container, new General_terms(), "fragment");
                generalTermsFragmentTransaction.commit();

                break;

            /*** Logout ***/
            case 12:
                if (session != null) {
                    if (!session.equals("")) {
                        initiatePopupWindow();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"no session id",Toast.LENGTH_LONG).show();
                }

        }
    }

    private void loginPopup() {
        Intent login = new Intent(MainActivity.this, Loginactivity.class);
        startActivity(login);
    }

    private PopupWindow popupexit;

    private void initiatePopupWindow() {
        try {

            inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            logoutLayout = inflater.inflate(R.layout.logout_popup, null);

            pwindo = new PopupWindow(logoutLayout, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, true);

            pwindo.showAtLocation(logoutLayout, Gravity.FILL, 0, 0);

            /*LOGOUT*/
            yesLinear = (LinearLayout) logoutLayout.findViewById(R.id.yes_linear);

            yesLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();
                    UserManager.getInstance().userLogout(MainActivity.this, session, logoutCallback);
                    DbManager.getInstance().deleteCurrentlyLoggedUserTable();
                    TodaysParentApp.setShippingAddress(null);
                    // ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "Logging out...", true);

                    finish();

                }
            });

            /*CANCEL LOGOUT*/
            cancelLinear = (LinearLayout) logoutLayout.findViewById(R.id.cancel_linear);
            cancelLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class LogoutCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            UserLogoutHolder userLogoutHolder = (UserLogoutHolder) result;

            if (userLogoutHolder.getStatus().equals("success")) {

                if (userLogoutHolder.getData() == null) {
                    Intent i = new Intent(MainActivity.this, Splashscreen.class);
                    startActivity(i);
                    CurrentlyLoggedUserDAO.getInstance().deleteAllRows();
                    Log.e("IN LOGOUT", "SUCCESS");
                } else {
                    Log.e("IN", "ELSE CASE");

                }
            } else {
                Log.e("LOGOUT FAILED", "" + userLogoutHolder.getStatus());

            }
        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.action_search);
        if (disableButtonFlag) {
            menu.findItem(R.id.action_search).setEnabled(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.search:
                //handleMenuSearch();

                action_bar_title.setText("");
                // findViewById(R.id.action_search).setVisibility(View.GONE);
                return true;

        }
        action_bar_title.setText("My Account");
        return super.onOptionsItemSelected(item);

    }

    public void search() {
        close_keyboard();
        searchFragmentTransaction = getSupportFragmentManager().beginTransaction();
        searchFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
        searchFragmentTransaction.replace(R.id.container, new SlidingFragment(), "fragment");
        searchFragmentTransaction.commit();

        categorynames = TodaysParentApp.getCategory();


        TodaysParentApp.setSearchword(searchword);


    }


    @Override
    public void onBackPressed() {
        if (TodaysParentApp.getIsinwebview() != null) {
            if (!TodaysParentApp.getIsinwebview().equals("")) {
                if (TodaysParentApp.getIsinwebview().equals("yes")) {
                    toolbar.setVisibility(View.VISIBLE);
                    myAccountFragmentTransaction = getSupportFragmentManager().beginTransaction();
                    myAccountFragmentTransaction.setCustomAnimations(R.anim.left, R.anim.slideoutleft);
                    myAccountFragmentTransaction.replace(R.id.container, new MyAccountFragment(), "fragment");
                    myAccountFragmentTransaction.commit();
                }
            } else {
                quit();
            }
        }
    }

    private PopupWindow pwindo;

    private void quit() {
        try {
            LayoutInflater inflater = (LayoutInflater) MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.exit_pop_up, null);

            pwindo = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, true);

            pwindo.showAtLocation(layout, Gravity.FILL, 0, 0);

            /*QUIT*/
            yesLinear = (LinearLayout) layout.findViewById(R.id.yes_linear);

            yesLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("YES BUTTON", "CLICK");
                    pwindo.dismiss();
                    moveTaskToBack(true);
                    finish();
                }
            });

            /*CANCEL LOGOUT*/
            cancelLinear = (LinearLayout) layout.findViewById(R.id.cancel_linear);
            cancelLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pwindo.dismiss();

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close_keyboard() {
        if (isSearchOpened) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(search_text.getWindowToken(), 0);
        }
    }

    public void open_keyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(search_text, InputMethodManager.SHOW_IMPLICIT);
    }

    /*
 * ALL NOTIFICATION CALLBACK .....
 */
    public class NotificationAsynchTaskCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            // TODO Auto-generated method stub
            if (UtilValidate.isNotNull(result)) {

                NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder) result;

                if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                    if (!UtilValidate.isEmpty(notificationBaseHolder.getData())) {
                        clearAll.setVisibility(View.VISIBLE);

                        /**
                         *  updating notification count table....
                         */

                        DbManager.getInstance().updateNotificationDetails(
                                Notification._ID,
                                notificationBaseHolder.getData().size());
                        if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                            notificationLists = notificationBaseHolder.getData();
                            TodaysParentApp.setNotificationBaseHoldersList(notificationBaseHolder.getData());
                            TodaysParentApp.setNotification_count(notificationBaseHolder.getData().size());


                            Log.d(TAG, "notificationList size in main activity %%%%%%%% " + TodaysParentApp.getNotificationBaseHoldersList().size());


                            if (UtilValidate.isNotNull(notificationsListAdapter)) {

                                notificationsListAdapter.notifyDataSetChanged();
                            }
                            if (UtilValidate.isNotNull(adapter)) {

                                adapter.notifyDataSetChanged();
                            }

                            /**
                             *  trigger BroadCastReceiver .....
                             */
                            Intent notifIntent = new Intent(IntentConstants.NOTIFICATION_INTENT);
                            notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount()));
                            MainActivity.this.sendBroadcast(notifIntent);

                        } else {

                            notificationLists = notificationBaseHolder.getData();

                            /**
                             *  updating notification count table....
                             */

                            DbManager.getInstance().updateNotificationDetails(
                                    Notification._ID, 0);

                            /**
                             *  trigger BroadCastReceiver .....
                             */
                            Intent notifIntent = new Intent(IntentConstants.NOTIFICATION_INTENT);
                            notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount()));
                            MainActivity.this.sendBroadcast(notifIntent);


                        }

                    } else {
                        Log.e("ELSe of notificaton", "else");
                        clearAll.setVisibility(View.INVISIBLE);
                    }

                }

            }


        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    public class DeleteAllNotificationCallBack implements AsyncTaskCallBack {
        @Override
        public void onFinish(int responseCode, Object result) {

            NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder) result;
            if (notificationBaseHolder.getStatus().equals("success")) {
                Log.e(">>>", "All Notification Cleared");

                if (UtilValidate.isNotNull(notificationListAdapter)) {
                    notificationListAdapter.notifyDataSetChanged();
                }
                clearAll.setVisibility(View.INVISIBLE);
                DbManager.getInstance().deleteNotifications();
                DbManager.getInstance().updateNotificationDetails(Notification._ID, 0);
                notificationFragmentTransaction = getSupportFragmentManager().beginTransaction();
                notificationFragmentTransaction.replace(R.id.container, new Notifica(), "fragment");
                notificationFragmentTransaction.commit();

                Toast.makeText(MainActivity.this, "Notification List Cleared", Toast.LENGTH_SHORT).show();
                dialogTransparent.dismiss();
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {


        }
    }


    public void DrawerOpen() {
        mDrawerLayout.openDrawer(Gravity.LEFT);

    }

    @Override
    protected void onResume() {
        super.onResume();
        session = DbManager.getInstance().getSessionId();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        Log.e("Fragment", "" + currentFragment);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            if (fragmentTrueFragment.equalsIgnoreCase("Browse")) {
                searchicon.setVisibility(View.VISIBLE);
                carticon.setVisibility(View.VISIBLE);
            } else if (fragmentTrueFragment.equalsIgnoreCase("Notification")) {
                clearAll.setVisibility(View.VISIBLE);

            }

        }
    }

}
