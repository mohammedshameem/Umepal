package com.qiito.umepal.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.Notification;

import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.NavDrawerListAdapter;
import com.qiito.umepal.adapter.NotificationListAdapter;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.NotificationManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aswathy on 9/10/15.
 */
public class Notifi extends Fragment {

    private NotificationManager notificationManager;
    private NotificationAsynchTaskCallBack notificationCallBack;
    private DeleteAllNotificationCallBack deleteAllNotificationCallBack;
    private DeleteNotificationAsynchTaskCallBack singleNotificationDeleteCallBack;
    private LinearLayout notification;
    private LinearLayout noNotification;
    private MenuItem mSearchAction;
    private boolean disableButtonFlag = false;
    private ListView notificationListView;
    private ImageView remove;
    private TextView clearAll;
    private View content;
    private List<ProductNotificationBaseHolder> productNotificationList = new ArrayList<>();
    private String sessionId;
    private LinearLayout not;
    private LinearLayout notificationCount;
    private NotificationListAdapter notificationListAdapter;
    private ProgressDialog deleteDialog;
    private ProgressDialog pDialog;
    private NotificationBaseHolder notificationBaseHolder;
    private NavDrawerListAdapter adapter;
    private String refferID;
    private String reffereeID;
    private String membershipID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onResume() {
        super.onResume();
        NotificationManager.getInstance().getAllNotification(getActivity(), notificationCallBack, DbManager.getInstance().getSessionId());
        NotificationManager.getInstance().getRefferNotification(getActivity(),refferID,reffereeID,membershipID, notificationCallBack);

        /* TodaysParentApp.setNotification_count(notificationBaseHolder.getData().size());
        Log.d("","LIST COUNT ON RESUME"+notificationBaseHolder.getData().size());*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        content = inflater.inflate(R.layout.notification, container, false);

        initView();
        initManager();
       // clearAll.setVisibility(View.VISIBLE);

        NotificationManager.getInstance().getAllNotification(getActivity(), notificationCallBack, DbManager.getInstance().getSessionId());

        notificationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub

                if (UtilValidate.isNotNull(DbManager.getInstance()
                        .getSessionId())) {

                    deletePopup(productNotificationList.get(position).getId());

                } /*else {

                    Toast.makeText(getActivity(),
                            "please login before delete...",
                            Toast.LENGTH_SHORT).show();
                    Intent mainlandingintent = new Intent(
                            getActivity(),
                            MainLandingPageActivity.class);
                    startActivity(mainlandingintent);

                }*/

                return true;
            }
        });


       // clearAll.setVisibility(View.VISIBLE);



      /* clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager.getInstance().deleteSingleNotification(getActivity(),deleteAllNotificationCallBack,sessionId,"");
            }
        });*/

        return content;
    }

    private void initManager() {

        notificationCallBack = new NotificationAsynchTaskCallBack();
        singleNotificationDeleteCallBack = new DeleteNotificationAsynchTaskCallBack();
        deleteAllNotificationCallBack = new DeleteAllNotificationCallBack();

    }

    private void initView() {

        notification = (LinearLayout) content.findViewById(R.id.notification_layout);
        noNotification = (LinearLayout) content.findViewById(R.id.no_notification_layout);
        notificationListView = (ListView) content.findViewById(R.id.listview_notification);
        clearAll = (TextView)content.findViewById(R.id.clear_all);


    }

    /**
     * @param notificationID
     */
    public void deletePopup(final int notificationID) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.delete_pop_menu, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        popupWindow.update();
        popupWindow.showAtLocation(content, Gravity.CENTER, 0, 0);

        final TextView cancelText = (TextView) popupView
                .findViewById(R.id.cancel);
        final TextView deleteText = (TextView) popupView
                .findViewById(R.id.delete);

        cancelText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        deleteText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                Log.i("", "DELETE NOTIFICATION WITH ID " + notificationID);

                // API CALL FOR DELETE SINGLE NOTIFICATION...

                deleteDialog = new ProgressDialog(getActivity());
                deleteDialog.setMessage("Loading...");
                deleteDialog.show();
                //NotificationManager.getInstance().deleteNotification(getActivity(), DBManager.getInstance().getCurrentUserSession(), String.valueOf(notificationID), deleteNotificationAsynchTaskCallBack);
                NotificationManager.getInstance().deleteSingleNotification(getActivity(), singleNotificationDeleteCallBack, DbManager.getInstance().getSessionId(),String.valueOf(notificationID));

            }
        });

    }


    /*
 * ALL NOTIFICATION CALLBACK .....
 */
    public class NotificationAsynchTaskCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            Log.d("", "in onfinish of notification" + responseCode);

            // TODO Auto-generated method stub
            if (UtilValidate.isNotNull(result)) {

                NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder) result;

                if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                    if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                        /**
                         *  updating notification count table....
                         */
                        productNotificationList = notificationBaseHolder.getData();
                        DbManager.getInstance().updateNotificationDetails(
                                Notification._ID,
                                notificationBaseHolder.getData().size());
                        TodaysParentApp.setNotification_count(notificationBaseHolder.getData().size());
                        if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                           /* ((NotificationsActivity) getActivity()).setNotificationList(notificationBaseHolder.getData()
                                    .getNotifications());}

                            Log.d(TAG, "notificationList size %%%%%%%% "+ ((NotificationsActivity) getActivity()).getNotificationList().size());
*/
                          /*  notificationListAdapter = new NotificationListAdapter(
                                    getActivity(), notificationBaseHolder.getData(),Notifi.this);
*/
                            notificationListView
                                    .setAdapter(notificationListAdapter);


                            if (UtilValidate.isNotNull(notificationListAdapter)) {

                                notificationListAdapter.notifyDataSetChanged();
                            }
                            if (UtilValidate.isNotNull(adapter)) {

                                adapter.notifyDataSetChanged();
                            }


                            /**
                               *  trigger BroadCastReceiver .....
                               */
                            Intent notifIntent = new Intent("NOTIFICATION");
                            notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount() ));
                            getActivity().sendBroadcast(notifIntent);

                        } else {


                            /**
                             *  updating notification count table....
                             */

                            DbManager.getInstance().updateNotificationDetails(
                                    Notification._ID, 0);
                            TodaysParentApp.setNotification_count(0);

                           /**
                             *  trigger BroadCastReceiver .....
                             */
                            Intent notifIntent = new Intent("NOTIFICATION");
                            notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount() ));
                            getActivity().sendBroadcast(notifIntent);


                        }

                    }

                }
            }


        }

        @Override
        public void onFinish(int responseCode, String result) {

        }


    }

/*
	 * DELETE SINGLE NOTIFICATION CALLBACK .....
	 */

    public class DeleteNotificationAsynchTaskCallBack implements AsyncTaskCallBack{

        /* (non-Javadoc)
         */
        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub

            if(deleteDialog!=null)
                deleteDialog.dismiss();

            if(responseCode == WebResponseConstants.ResponseCode.OK){

                notificationBaseHolder = (NotificationBaseHolder)result;
                if(UtilValidate.isNotNull(notificationBaseHolder)){

                    if(notificationBaseHolder.getStatus().equalsIgnoreCase("SUCCESS")){

                        Toast.makeText(getActivity(), "Notification deleted successfully", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getActivity(), "Notification not deleted!", Toast.LENGTH_SHORT).show();

                    }


                }

                /*pDialog = new ProgressDialog(getActivity());

                pDialog.setMessage("Loading...");
                pDialog.show();*/

                /**
                 * API call for Retrieving all Notifications
                 */
                /*NotificationManager.getInstance().allNotification(getActivity(),
                        DbManager.getInstance().getCurrentUserSession(),
                        notificationasynchTaskCallBack, REQUEST_CODE, pDialog);*/
                NotificationManager.getInstance().getAllNotification(getActivity(), notificationCallBack, DbManager.getInstance().getSessionId());
                TodaysParentApp.setNotification_count(notificationBaseHolder.getData().size());

            }

        }

        /* (non-Javadoc)
         */
        @Override
        public void onFinish(int responseCode, String result) {
            // TODO Auto-generated method stub

        }


    }

    public class DeleteAllNotificationCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {

            NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder)result;
            if(notificationBaseHolder.getStatus().equals("success")) {
                Log.e("", "All Notification Cleared");
                notification.setVisibility(View.GONE);
                noNotification.setVisibility(View.VISIBLE);
                DbManager.getInstance().deleteNotifications();
                Toast.makeText(getActivity(), "Notification List Cleared", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void onFinish(int responseCode, String result) {


        }
    }
}
