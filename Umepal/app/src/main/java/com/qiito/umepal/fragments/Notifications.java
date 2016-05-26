package com.qiito.umepal.fragments;
/**
 * Created by abin on 5/8/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.R;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.activity.ProductDetails;
import com.qiito.umepal.adapter.NotificationListAdapter;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.NotificationManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;

import java.util.ArrayList;
import java.util.List;


public class Notifications extends Fragment {

    private NotificationManager notificationManager;
    private NotificationCallBack notificationCallBack;
    private DeleteAllNotificationCallBack deleteAllNotificationCallBack;
    private SingleNotificationDeleteCallBack singleNotificationDeleteCallBack;
    private LinearLayout notification;
    private LinearLayout noNotification;
    private MenuItem mSearchAction;
    private boolean disableButtonFlag = false;
    private ListView notificationListView;
    private ImageView remove;
    private TextView clearAll;
    private View content;
    private List<ProductNotificationBaseHolder> productNotificationList=new ArrayList<>();
    private String sessionId;
    private LinearLayout not;
    private LinearLayout notificationCount;
    private NotificationListAdapter notificationListAdapter;

    public Notifications() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("", "IN ONRESUME");

        if(UtilValidate.isNotNull(notificationListAdapter)){

            notificationListAdapter.notifyDataSetChanged();
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        content = inflater.inflate(R.layout.notification, container, false);
        notification = (LinearLayout)content.findViewById(R.id.notification_layout);
        noNotification = (LinearLayout)content.findViewById(R.id.no_notification_layout);
        notificationListView = (ListView)content.findViewById(R.id.listview_notification);


        //clearAll = (TextView)content.findViewById(R.id.clear_all);
         clearAll = (TextView)content.findViewById(R.id.clear_all);



        not=(LinearLayout)content.findViewById(R.id.notificationCount);
        notificationCount = (LinearLayout)content.findViewById(R.id.notificationCount);

        sessionId = DbManager.getInstance().getSessionId();

        initManager();
        //clearAll.setVisibility(View.VISIBLE);


      /* clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager.getInstance().deleteSingleNotification(getActivity(),deleteAllNotificationCallBack,sessionId,"",0);
            }
        });*/

        return content;
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
        Log.e("","in onCreateOptionsMenu");
         // Do something that differs the Activity's menu here
        menu.removeItem(R.id.action_search);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.e("", "in onOptionsItemSelected");
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void initManager(){
        notificationCallBack = new NotificationCallBack();
        notificationManager = new NotificationManager();
        deleteAllNotificationCallBack = new DeleteAllNotificationCallBack();
        singleNotificationDeleteCallBack = new SingleNotificationDeleteCallBack();

    }


    public class NotificationCallBack implements AsyncTaskCallBack{

        @Override
        public void onFinish(int responseCode, Object result) {
            Log.e("","IN Notification call back");

            final NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder)result;

            if (notificationBaseHolder.getStatus().equals("success")){

                Log.e("", "Call Back Success");
                if(notificationBaseHolder.getData()!=null) {

                    productNotificationList = notificationBaseHolder.getData();
                    if (productNotificationList.size() > 0) {

                        notification.setVisibility(View.VISIBLE);
                        noNotification.setVisibility(View.GONE);
                        //clearAll.setVisibility(View.VISIBLE);
                        TodaysParentApp.setNotification_count(productNotificationList.size());
                       // NotificationListAdapter notificationListAdapter = new NotificationListAdapter(getActivity(), productNotificationList, Notifications.this);
                        notificationListView.setAdapter(notificationListAdapter);
                        notificationListAdapter.notifyDataSetChanged();




                    }
                    if (productNotificationList.size() <= 0) {
                        noNotification.setVisibility(View.VISIBLE);
                        notification.setVisibility(View.GONE);

                    }

                    notificationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                            Intent notfication = new Intent(getActivity(), ProductDetails.class);
                            notfication.putExtra("productid", productNotificationList.get(position).getProduct().getId());
                            Log.e("product id ", "notification" + productNotificationList.get(position).getProduct().getId());
                            startActivity(notfication);


                        }
                    });

                    notificationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                           String id = String.valueOf(notificationBaseHolder.getData().get(position).getId());

                            deleteSingleNotification(id,position);


                            return false;
                        }
                    });
                }else{
                    //notificationBaseHolder.getData() is null
                }
            }
            else{
                Log.e("", "CAll BAck error");
            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    private void deleteSingleNotification(final String notificationid,final int pos){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("You Want To Delete The Notification Now");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog,int which) {

               // NotificationManager.getInstance().deleteSingleNotification(getActivity(), singleNotificationDeleteCallBack, sessionId, notificationid, pos);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,	int which) {

                dialog.cancel();
            }
        });

        alertDialog.show();


    }

    public class SingleNotificationDeleteCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {
            NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder)result;
            if(notificationBaseHolder.getStatus().equals("success")){
                Toast.makeText(getActivity(), "Delete Succesfull", Toast.LENGTH_SHORT).show();

                //NotificationManager.getInstance().getAllNotification(getActivity(), notificationCallBack, sessionId);
                int j = TodaysParentApp.getNotification_count();
                TodaysParentApp.setNotification_count(j - 1);


            }

        }

        @Override
        public void onFinish(int responseCode, String result) {

        }
    }

    public class DeleteAllNotificationCallBack implements AsyncTaskCallBack{
        @Override
        public void onFinish(int responseCode, Object result) {
            NotificationBaseHolder notificationBaseHolder = (NotificationBaseHolder)result;
            if(notificationBaseHolder.getStatus().equals("success")){
                Log.e("", "All Notification Cleared");
                notification.setVisibility(View.GONE);
                noNotification.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"Notification List Cleared",Toast.LENGTH_SHORT).show();
                TodaysParentApp.setNotification_count(productNotificationList.size());
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



}
