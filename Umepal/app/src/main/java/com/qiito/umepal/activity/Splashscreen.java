package com.qiito.umepal.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.qiito.umepal.R;
import com.qiito.umepal.Constants.Notification;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.Utilvalidate.Utils;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.holder.UserObjectHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.NotificationManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.qiito.umepal.Utilvalidate.NetChecker.isConnected;
import static com.qiito.umepal.Utilvalidate.NetChecker.isConnectedMobile;
import static com.qiito.umepal.Utilvalidate.NetChecker.isConnectedWifi;

public class Splashscreen extends ActionBarActivity {
    private Dialog dialogTransparent;
    private View progressview;
    private AlertDialog.Builder builder;
    private AlertDialog alert;
    private List<ProductNotificationBaseHolder> productNotificationList = new ArrayList<>();
    private static int SPLASH_TIME_OUT = 2000;
    private String sessionId;
    private NotificationCallBack notificationCallBack;
    private Handler handler = new Handler();
    private String broadCastExitMsg = null;
    private ExitBroadcastReceiver mReceiver;

    public class ExitBroadcastReceiver extends BroadcastReceiver {

        private final Handler handler; // Handler used to execute code on the UI
        // thread

        public ExitBroadcastReceiver(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(final Context context, Intent intent) {

            if (intent.getAction().equals("EXIT")) {

                // extract our message from intent
                broadCastExitMsg = intent.getStringExtra("EXIT");
                Log.i("", "In ExitBroadcastReceiver ###");
                // Post the UI updating code to our Handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        finish();

                    }
                });

            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_page);

        Utils.showHashKey(this);

        dialogTransparent = new Dialog(this, android.R.style.Theme_Black);
        progressview = LayoutInflater.from(this).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();
        sessionId = DbManager.getInstance().getSessionId();

        initManager();
        NotificationManager.getInstance().getAllNotification(Splashscreen.this, notificationCallBack, sessionId);
        // dialogTransparent.show();


                    /* CHECK  INTERNET */

                        /*  WIFI  */
        if (isConnectedWifi(Splashscreen.this)) {
            // dialogTransparent.dismiss();
            // Toast.makeText(Splashscreen.this, "Connected to wifi ", Toast.LENGTH_SHORT).show();
        }

                        /*  MOBILE NETWORK  */
        if (isConnectedMobile(Splashscreen.this)) {
            // dialogTransparent.dismiss();

            //  Toast.makeText(Splashscreen.this, "Connected to mobile network ", Toast.LENGTH_SHORT).show();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                check_net_and_go();
            }
        }, SPLASH_TIME_OUT);

        try {
            // inserting default values to notification table ....

            if (DbManager.getInstance().fetchNotificationTableCount() == 0) {

                DbManager.getInstance().insertNotificationDetails(
                        Notification._ID, 0);
                Log.d("", "Inserting default values to notification table");

            } else {
                Log.d("", "db not empty");
            }
        } catch (Exception e) {

            Log.e("",
                    "ERROR!!! Inserting default values to DB "
                            + e);
        }

/*

        if (DbManager.getInstance().fetchNotificationTableCount() == 0) {

            DbManager.getInstance().insertNotificationDetails(
                    Notification._ID, 0);
                Log.d("", "Inserting default values to notification table");

        }
*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*IntentFilter intentFilter = new IntentFilter("NOTIFICATION");
        mReceiver = new ExitBroadcastReceiver(handler);
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);*/

    }

    private void initManager() {

        notificationCallBack = new NotificationCallBack();
    }

/*NET CHECK*/


    public void check_net_and_go() {
        if (isConnected(Splashscreen.this)) {
            dialogTransparent.dismiss();
            Intent intent = new Intent(Splashscreen.this, MainActivity.class);
            startActivity(intent);
            UserObjectHolder userObjectHolder=new UserObjectHolder();
            userObjectHolder.setCreated("");
           /* if (DbManager.getInstance().isUserExisting()) {
                Log.e("", "Already loggged in user");
                Log.e("", "+++++++++++++++++++++++++++++++++++++++++++++++++");
                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                startActivity(intent);
            } else {
                dialogTransparent.dismiss();
                Intent intent = new Intent(Splashscreen.this, Loginactivity.class);
                startActivity(intent);
            }*/
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        } else {
            dialogTransparent.dismiss();
            builder = new AlertDialog.Builder(Splashscreen.this);
            builder.setMessage("Network Unavailable!!");
            builder.setCancelable(true);
            builder.setPositiveButton("Retry",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialogTransparent.show();
                            dialog.cancel();
                            startActivity(getIntent());
                            if (isConnected(Splashscreen.this)) {
                                Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                                startActivity(intent);
                               /* if (DbManager.getInstance().isUserExisting()) {
                                    Log.e("", "Already loggged in user");
                                    dialogTransparent.dismiss();
                                    Intent intent = new Intent(Splashscreen.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(Splashscreen.this, Loginactivity.class);
                                    startActivity(intent);
                                }*/
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    }


    public class NotificationCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            Log.e("", "IN Notification call back");

            final NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder) result;

            if (notificationBaseHolder.getStatus().equals("success")) {

                Log.e("", "Call Back Success in splash screen");
                if (notificationBaseHolder.getData() != null) {

                    /**
                     *  updating notification count table....
                     */
                    productNotificationList = notificationBaseHolder.getData();
                    DbManager.getInstance().updateNotificationDetails(
                            Notification._ID,
                            notificationBaseHolder.getData().size());
                    if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                           /* ((NotificationsActivity) getActivity()).setNotificationList(notificationBaseHolder.getData()
                                    .getNotifications());}

                            Log.d(TAG, "notificationList size %%%%%%%% "+ ((NotificationsActivity) getActivity()).getNotificationList().size());
*/
                            /*notificationListAdapter = new NotificationListAdapter(
                                    getActivity(), notificationBaseHolder.getData());

                            notificationListView
                                    .setAdapter(notificationListAdapter);
*/

                       /* if (UtilValidate.isNotNull(notificationListAdapter)) {

                            notificationListAdapter.notifyDataSetChanged();
                        }*/

                          /*  *//**
                         *  trigger BroadCastReceiver .....
                         *//*
                            Intent notifIntent = new Intent(IntentConstants.NOTIFICATION_INTENT);
                            notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount() + DBManager.getInstance().getPromotionListCount()));
                            getActivity().sendBroadcast(notifIntent);*/

                    } else {


                        /**
                         *  updating notification count table....
                         */

                        DbManager.getInstance().updateNotificationDetails(
                                Notification._ID, 0);

                    }


                } else {
                    //notificationBaseHolder.getData() is null
                }
            } else {
                Log.e("", "CAll BAck error");
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }
}
