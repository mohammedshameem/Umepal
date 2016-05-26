package com.qiito.umepal.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Constants.IntentConstants;
import com.qiito.umepal.Constants.Notification;
import com.qiito.umepal.R;
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

public class Notifica extends Fragment {

    private static final String TAG = Notifica.class.getName();
    private View view;
    private ListView notificationsList;
    private Dialog dialogTransparent;
    private View progressview;
    private NotificationListAdapter notificationsListAdapter;
    private static final int REQUEST_CODE = 1;
    private ProgressDialog pDialog, deleteDialog;
    private NotificationAsynchTaskCallBack notificationasynchTaskCallBack;
    private List<ProductNotificationBaseHolder> notificationList = new ArrayList<ProductNotificationBaseHolder>();
    private DeleteNotificationAsynchTaskCallBack deleteNotificationAsynchTaskCallBack = new DeleteNotificationAsynchTaskCallBack();
    private DeleteAllNotificationCallBack deleteAllNotificationCallBack = new DeleteAllNotificationCallBack();
    private LinearLayout notification;
    private LinearLayout noNotification;
    private NavDrawerListAdapter adapter;
    private TextView clearAll;
    private TextView heading;
    private LinearLayout customActionBar;
    private ImageView menuIcon;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        notificationasynchTaskCallBack = new NotificationAsynchTaskCallBack();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        view = inflater.inflate(R.layout.notification, null);
        notificationsList = (ListView) view.findViewById(R.id.listview_notification);
        notification = (LinearLayout)view.findViewById(R.id.notification_layout);
        noNotification = (LinearLayout)view.findViewById(R.id.no_notification_layout);
        clearAll = (TextView)view.findViewById(R.id.clear_all);
        heading = (TextView)view.findViewById(R.id.menu_heading);
        menuIcon = (ImageView)view.findViewById(R.id.menu_icon);
        customActionBar = (LinearLayout)view.findViewById(R.id.custom_action_bar);
        dialogTransparent = new Dialog(getActivity(), android.R.style.Theme_Black);
        progressview = LayoutInflater.from(getActivity()).inflate(R.layout.progrssview, null);
        dialogTransparent.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogTransparent.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialogTransparent.setContentView(progressview);
        dialogTransparent.show();

        NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);

        Log.e("!!","called notification list");
        Log.e("##","SIZE>>>>>>"+notificationList.size());


        /*if (notificationList.size()<=0){
            notification.setVisibility(View.GONE);
            noNotification.setVisibility(View.VISIBLE);



        }else{
            notification.setVisibility(View.VISIBLE);
            noNotification.setVisibility(View.GONE);
        }*/



        notificationsList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // TODO Auto-generated method stub

                if (UtilValidate.isNotNull(DbManager.getInstance().getSessionId())) {

                    deletePopup(TodaysParentApp.getNotificationBaseHoldersList().get(position).getId());

                }

                return true;
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        view=null;
        notificationsList=null;
        notificationsListAdapter=null;
        pDialog=null;
        deleteDialog=null;
        notificationasynchTaskCallBack=null;
        notificationList = null;
        deleteNotificationAsynchTaskCallBack = null;

    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }

    /**
     *
     * @param notificationID
     */
    public void deletePopup(final int notificationID) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.delete_pop_menu, null);
        final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        popupWindow.update();
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        final TextView cancelText = (TextView) popupView.findViewById(R.id.cancel);
        final TextView deleteText = (TextView) popupView.findViewById(R.id.delete);

        cancelText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

        deleteText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();

                Log.i(TAG, "DELETE NOTIFICATION WITH ID " + notificationID);

                // API CALL FOR DELETE SINGLE NOTIFICATION...
                deleteDialog = new ProgressDialog(getActivity());
                deleteDialog.setMessage("Loading...");
                deleteDialog.show();
                NotificationManager.getInstance().deleteSingleNotification(getActivity(), deleteNotificationAsynchTaskCallBack, DbManager.getInstance().getSessionId(), String.valueOf(notificationID));

            }
        });

    }
	
	
	/*
	 * DELETE SINGLE NOTIFICATION CALLBACK .....
	 */

    public class DeleteNotificationAsynchTaskCallBack implements AsyncTaskCallBack {

        /* (non-Javadoc)
         */
        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub

            if(deleteDialog!=null)
                deleteDialog.dismiss();

            if(responseCode == WebResponseConstants.ResponseCode.OK){

                NotificationBaseHolder deleteNotificationResponseHolder = (NotificationBaseHolder)result;

                if(UtilValidate.isNotNull(deleteNotificationResponseHolder)){

                    if(deleteNotificationResponseHolder.getStatus().equalsIgnoreCase("SUCCESS")){

                        NotificationManager.getInstance().getAllNotification(getActivity(),DbManager.getInstance().getSessionId(),
                                notificationasynchTaskCallBack, REQUEST_CODE, pDialog);
                        DbManager.getInstance().updateNotificationDetails(Notification._ID, notificationList.size() );

                        if (UtilValidate.isNotNull(adapter)) {
                            adapter.notifyDataSetChanged();
                        }
                        Toast.makeText(getActivity(), "Notification deleted successfully", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(getActivity(), "Notification not deleted!", Toast.LENGTH_SHORT).show();

                    }


                }

            }

        }

        /* (non-Javadoc)
         */
        @Override
        public void onFinish(int responseCode, String result) {
            // TODO Auto-generated method stub

        }

    }


    /*
     * ALL NOTIFICATION CALLBACK .....
     */
    public class NotificationAsynchTaskCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {

            Log.d(TAG, "in onfinish of notification" + responseCode);

            // TODO Auto-generated method stub
            if (UtilValidate.isNotNull(result)) {

                NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder) result;

                if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                    if (UtilValidate.isNotNull(notificationBaseHolder.getData())) {

                        notificationList = notificationBaseHolder.getData();

                        DbManager.getInstance().updateNotificationDetails(
                                Notification._ID,
                                notificationBaseHolder.getData().size());
                        if (notificationList.size() > 0) {

                            notification.setVisibility(View.VISIBLE);
                            noNotification.setVisibility(View.GONE);
                            //clearAll.setVisibility(View.VISIBLE);
                            TodaysParentApp.setNotification_count(notificationList.size());
                            NotificationListAdapter notificationsListAdapter = new NotificationListAdapter(getActivity(), notificationList, Notifica.this);
                            notificationsList.setAdapter(notificationsListAdapter);
                            notificationsListAdapter.notifyDataSetChanged();

                            if (UtilValidate.isNotNull(adapter)) {
                                adapter.notifyDataSetChanged();
                            }

                        }
                        if (notificationList.size() <= 0) {
                            noNotification.setVisibility(View.VISIBLE);
                            notification.setVisibility(View.GONE);
                            TodaysParentApp.setNotification_count(notificationList.size());
                            NotificationListAdapter notificationsListAdapter = new NotificationListAdapter(getActivity(), notificationList, Notifica.this);
                            notificationsList.setAdapter(notificationsListAdapter);
                            notificationsListAdapter.notifyDataSetChanged();
                            if (UtilValidate.isNotNull(adapter)) {
                                adapter.notifyDataSetChanged();
                            }

                        }

                        /**
                         *  updating notification count table....
                         */

                        DbManager.getInstance().updateNotificationDetails(
                                Notification._ID,
                                notificationBaseHolder.getData().size());

                        dialogTransparent.dismiss();
                    }

                }
            }


        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }



        @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (UtilValidate.isNotNull(DbManager.getInstance().getSessionId())) {

            /**
             * API call for Retrieving all Notifications
             */
            NotificationManager.getInstance().getAllNotification(getActivity(), DbManager.getInstance().getSessionId(),
                    notificationasynchTaskCallBack, REQUEST_CODE, pDialog);

        }/* else {

            Toast.makeText(getActivity(),"please login to see the notification...",Toast.LENGTH_SHORT).show();
            Intent mainlandingintent = new Intent(getActivity(),MainLandingPageActivity.class);
            startActivity(mainlandingintent);

        }*/

        if(UtilValidate.isNotNull(notificationsListAdapter)){

            notificationsListAdapter.notifyDataSetChanged();
        }

        /**
         *  trigger BroadCastReceiver .....
         */
        Intent notifIntent = new Intent(IntentConstants.NOTIFICATION_INTENT);
        notifIntent.putExtra("notification_count", String.valueOf(DbManager.getInstance().getOpenNotificationListCount()));
        getActivity().sendBroadcast(notifIntent);

    }



    public class DeleteAllNotificationCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {
            NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder)result;
            if(notificationBaseHolder.getStatus().equals("success")){
                Log.e("", "All Notification Cleared");
                notification.setVisibility(View.GONE);
                noNotification.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Notification List Cleared"+notificationBaseHolder.getData().size(),Toast.LENGTH_SHORT).show();
                TodaysParentApp.setNotification_count(notificationBaseHolder.getData().size());
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

            Log.e("", "All Notification Cleared");
            notification.setVisibility(View.GONE);
            noNotification.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(),"Cleared All Notifications",Toast.LENGTH_LONG).show();
        }
    }

    public void setLayout(){
        notification.setVisibility(View.GONE);
        noNotification.setVisibility(View.VISIBLE);

    }



}