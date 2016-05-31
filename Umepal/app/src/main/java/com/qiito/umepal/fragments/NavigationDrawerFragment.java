/**
 * Created by abin on 5/8/15.
 */
package com.qiito.umepal.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.IntentConstants;
import com.qiito.umepal.Constants.Notification;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.NavDrawerListAdapter;
import com.qiito.umepal.adapter.NotificationListAdapter;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.NotificationManager;
import com.qiito.umepal.model.NavDrawerItem;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;


public class NavigationDrawerFragment extends Fragment {


    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private NavigationDrawerCallbacks mCallbacks;
    private NotificationAsynchTaskCallBack notificationasynchTaskCallBack;
    private ActionBarDrawerToggle mDrawerToggle;
    private LinearLayout notificationCount;
    private TextView action_bar_title;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private ListView mDrawersubListView;
    private View mFragmentContainerView;
    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private boolean disableButtonFlag = false;
    private EditText edtSearch;
    private LinearLayout search;
    private int mCurrentSelectedPosition = 0;
    //private NotificationCallBack notificationCallBack;
    private String session;
    private BadgeView notificationbadgeView;
    private NotificationListAdapter notificationsListAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private String[] navMenuTitles;
    private NavDrawerListAdapter adapter;
    private NotificationBaseHolder notificationBaseHolder;
    private Handler handler = new Handler();
    private String broadCastMsg = null;
    //private ExitBroadcastReceiver mReceiver;
    private EditText search_text;
    private TextView app_action_bar_title;
    private BadgeView NotificationbadgeView;
    private static final int REQUEST_CODE = 1;
    private ProgressDialog pDialog, deleteDialog;
    public ActionBarDrawerToggle getDrawerToggle() {
        return mDrawerToggle;
    }
    public NavigationDrawerFragment() {

    }


    public class NotificationReceiver extends BroadcastReceiver {

        private final Handler handler; // Handler used to execute code on the UI thread

        public NotificationReceiver(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(final Context context, Intent intent) {

            if (intent.getAction().equals(IntentConstants.NOTIFICATION_INTENT)) {

                //extract our message from intent
                broadCastMsg = intent.getStringExtra("notification_count");


               /* Log.i("", "In NotificationBroadCastReceiver ####### " + DbManager
                        .getInstance().getOpenNotificationListCount());*/
            }

            // Post the UI updating code to our Handler
            handler.post(new Runnable() {
                @Override
                public void run() {

                    updateTheBadgeView();
                }
            });

        }
    }

    public void updateTheBadgeView() {

        NotificationbadgeView = new BadgeView(getActivity(),
                notificationCount);
        NotificationbadgeView.setText(String.valueOf(DbManager
                .getInstance().getOpenNotificationListCount()));
        //NotificationbadgeView.setBackgroundResource(R.drawable.rounded);
        NotificationbadgeView.setBadgePosition(BadgeView.POSITION_CENTER);
        NotificationbadgeView.setGravity(Gravity.CENTER);
        NotificationbadgeView.setTextSize(12);
        NotificationbadgeView.setTextColor(getResources().getColor(
                R.color.red_cherry_red));
        NotificationbadgeView.toggle(true);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
        notificationasynchTaskCallBack = new NotificationAsynchTaskCallBack();

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        search_text = (EditText) mDrawerListView.findViewById(R.id.search_text);

        search = (LinearLayout) mDrawerListView.findViewById(R.id.searchlay);
        app_action_bar_title = (TextView) mDrawerListView.findViewById(R.id.app_action_bar_title);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        notificationCount = (LinearLayout) mDrawerListView.findViewById(R.id.linear_num_of_notification);

        NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);

        session = DbManager.getInstance().getSessionId();
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.new_nav_drawer_items);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], 0));
        if(session!=null) {
            if (!session.equals("")) {

                // adding nav drawer items to array
                // Home

                // Find People
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], 0));
                // Photos
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], 0));
                // Communities, Will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], 0));
                // Pages
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], 0, true, DbManager.getInstance().getOpenNotificationListCount() + ""));

                // What's hot, We  will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));


                navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], 0));
        /*mDrawerListView.setAdapter(new DrawerAdapter(this.getActivity(), drawerItems,NavigationDrawerFragment.this));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);*/
            }
        }else {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], 0));
            // Customer Support
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));
            // Contact Us
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));
            // General terms
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], 0));
        }
        adapter = new NavDrawerListAdapter(getActivity(),
        navDrawerItems, NavigationDrawerFragment.this);
        mDrawerListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return mDrawerListView;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;

        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
               // Log.e("drawer", "closed.......###.......$$$$$$$$$......................");

                super.onDrawerClosed(drawerView);
               // Log.e("drawer", "closed.......###.............................");
                NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                        notificationasynchTaskCallBack, REQUEST_CODE, pDialog);

                adapter.notifyDataSetChanged();

                if (!isAdded()) {
                   // Log.e("drawer", "closed....................................");

                    return;
                }
                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
               // Log.e("drawer", "open.......###.......$$$$$$$$$......................");

                TodaysParentApp.setIsinwebview("");

                super.onDrawerOpened(drawerView);
                //Log.e("drawer", "opened................############....................");
                NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                        notificationasynchTaskCallBack, REQUEST_CODE, pDialog);
                adapter.notifyDataSetChanged();

                if (!isAdded()) {
               //     Log.e("drawer", "opened....................................");


                    return;
                }

            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        if (mDrawerLayout.isDrawerOpen(mDrawerListView)) {

            //Log.d("", "INSIDE OPEN DRAWER");

        }

        mDrawerLayout.setDrawerListener(mDrawerToggle);


        NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);
        adapter.notifyDataSetChanged();
    }

    private void selectItem(int position) {

        mCurrentSelectedPosition = position;

        if (position == 7) {

        } else {
            if (mDrawerListView != null) {
                mDrawerListView.setItemChecked(position, true);
            }
            if (mDrawerLayout != null) {
                mDrawerLayout.closeDrawer(mFragmentContainerView);
            }
            if (mCallbacks != null) {
                mCallbacks.onNavigationDrawerItemSelected(position);
            }
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // Log.e("", "in onCreateOptionsMenu in navigation");
        //   inflater.inflate(R.menu.menu_main, menu);
        //        mSearchAction = menu.findItem(R.id.action_search);
// Do something that differs the Activity's menu here
        super.onCreateOptionsMenu(menu, inflater);

        NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);


        adapter = new NavDrawerListAdapter(getActivity(),
                navDrawerItems, NavigationDrawerFragment.this);
        mDrawerListView.setAdapter(adapter);

        if (UtilValidate.isNotNull(adapter)) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       // Log.e("", "in onOptionsItemSelected in navigation");
        int id = item.getItemId();
        NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);
        adapter.notifyDataSetChanged();


        switch (id) {
            case R.id.action_search:

                return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();
         session= DbManager.getInstance().getSessionId();
        NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);

        navMenuTitles = getResources().getStringArray(R.array.new_nav_drawer_items);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], 0));
      /*  if(session!=null) {
            if (!session.equals("")) {

                // adding nav drawer items to array
                // Home

                // Find People
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], 0));
                // Photos
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], 0));
                // Communities, Will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], 0));
                // Pages
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], 0, true, DbManager.getInstance().getOpenNotificationListCount() + ""));

                // What's hot, We  will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));


                navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], 0));

        *//*mDrawerListView.setAdapter(new DrawerAdapter(this.getActivity(), drawerItems,NavigationDrawerFragment.this));
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);*//*
            }
        }else {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], 0));
            // Customer Support
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));
            // Contact Us
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));
            // General terms
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], 0));
        }
*/
        if(session!=null) {
            if (!session.equals("")) {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], 0));
                // Photos
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], 0));
                // Communities, Will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], 0));

                navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], 0));

                navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], 0, true, DbManager.getInstance().getOpenNotificationListCount() + ""));
                // Pages
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], 0));

                // What's hot, We  will add a counter here

                navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));


                navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[12], 0));
            }else {
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));

                navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], 0));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], 0));

            }
        }else {
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], 0));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], 0));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], 0));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], 0));
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[13], 0));

        }

        adapter = new NavDrawerListAdapter(getActivity(),
                navDrawerItems, NavigationDrawerFragment.this);
        mDrawerListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    /*
    * ALL NOTIFICATION CALLBACK .....
    */
    public class NotificationAsynchTaskCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

         //   Log.d("", "in onfinish of notification" + responseCode);

            // TODO Auto-generated method stub
            if (UtilValidate.isNotNull(result)) {

                NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder) result;

                if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                    if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                        /**
                         *  updating notification count table....
                         */

                        DbManager.getInstance().updateNotificationDetails(
                                Notification._ID,
                                notificationBaseHolder.getData().size());
                        if (UtilValidate.isNotNull(notificationBaseHolder.getData()
                        )) {


                            TodaysParentApp.setNotificationBaseHoldersList(notificationBaseHolder.getData());
                            TodaysParentApp.setNotification_count(notificationBaseHolder.getData().size());

                         //   Log.d("", "notificationList size  NAVIGATION DRAWER %%%%%%%% " + notificationBaseHolder.getData().size());
                          //  Log.d("", "notificationList size  NAVIGATION DRAWER %%%%%%%% " + TodaysParentApp.getNotificationBaseHoldersList().size());

                            if (UtilValidate.isNotNull(notificationsListAdapter)) {
                             //   Log.e("", "going from navigation drawer fragment");
                                notificationsListAdapter.notifyDataSetChanged();
                            }
                            if (UtilValidate.isNotNull(adapter)) {
                                adapter.notifyDataSetChanged();
                            } else {
                                Log.e("", "not invoking adapter");
                            }

                            /**
                             *  trigger BroadCastReceiver .....
                             */
                            Intent notifIntent = new Intent(IntentConstants.NOTIFICATION_INTENT);
                            notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount()));
                          //  Log.e("notification_count",DbManager.getInstance().getOpenNotificationListCount()+"");
                            if(getActivity()!=null){
                                getActivity().sendBroadcast(notifIntent);
                            }
                        } else {

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
                            if(getActivity()!=null){
                                getActivity().sendBroadcast(notifIntent);
                            }
                            else{
                                TodaysParentApp.getCurrentcontext().sendBroadcast(notifIntent);
                            }


                        }

                    }

                }
            }


        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }
}
