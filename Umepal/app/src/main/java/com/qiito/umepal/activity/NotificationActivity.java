package com.qiito.umepal.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qiito.umepal.R;
import com.qiito.umepal.Application.TodaysParentApp;
import com.qiito.umepal.Utilvalidate.NetChecker;
import com.qiito.umepal.Utilvalidate.UtilValidate;
import com.qiito.umepal.adapter.NotificationListAdapter;
import com.qiito.umepal.holder.AdvertisementData;
import com.qiito.umepal.holder.NotificationBaseHolder;
import com.qiito.umepal.holder.ProductNotificationBaseHolder;
import com.qiito.umepal.managers.DbManager;
import com.qiito.umepal.managers.NotificationManager;
import com.qiito.umepal.webservice.AsyncTaskCallBack;
import com.qiito.umepal.webservice.WebResponseConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import com.github.mrengineer13.snackbar.SnackBar;

public class NotificationActivity extends Activity {

    private String TAG = NotificationActivity.class.getName();

    private NotificationCallBackDelete notificationCallBackdelete;
    private NotificationBaseHolder noticationBaseHolder;
    private NotificationCallBack notificationCallBack;
    private NotificationListAdapter notificationListAdapter;

    private List<ProductNotificationBaseHolder> notificationBaseHoldersListtoshow;
    private List<AdvertisementData> advertisementList;

    private LinearLayout back_icon_with_app_image;
    private LinearLayout linear_notification;
    private LinearLayout cart_layout;


    private TextView notification_clear_all_layout;
    private TextView page_heading;

    private ImageView add_to_cart_icon;

    private ListView listview_notification;

    private ProgressDialog dialog;

    private String sessionid;
    private String notificationid;

    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        initViews();
        initManagers();

        HashMap<String, String> session = DbManager.getInstance().fetchCurrentUserDetails();
        sessionid = session.get("session_id");

        if (UtilValidate.isNotEmpty(TodaysParentApp.getNotificationBaseHoldersList())) {
            notificationBaseHoldersListtoshow = new ArrayList<>();
            notificationBaseHoldersListtoshow.addAll(TodaysParentApp.getNotificationBaseHoldersList());
            listview_notification.setAdapter(notificationListAdapter);
            notificationListAdapter.notifyDataSetChanged();
        } else {
            if (!(NetChecker.isConnected(NotificationActivity.this))) {

                if (!(NetChecker.isConnectedWifi(NotificationActivity.this) && NetChecker.isConnectedMobile(NotificationActivity.this))) {

                    Toast.makeText(NotificationActivity.this, "Please check your internet connection...",Toast.LENGTH_LONG).show();
                }

            } else {
                dialog = ProgressDialog.show(NotificationActivity.this, "", "Please wait...", true);
                dialog.show();
                NotificationManager.getInstance().getAllNotification(NotificationActivity.this, notificationCallBack, sessionid);
            }

        }

        notification_clear_all_layout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                notification_clear_all_layout.setClickable(false);
                NotificationManager.getInstance().deleteAllNotification(NotificationActivity.this, notificationCallBackdelete, sessionid);
            }
        });

        listview_notification.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

                String product_id = String.valueOf(notificationBaseHoldersListtoshow.get(position).getProduct().getId());
                dialog = ProgressDialog.show(NotificationActivity.this, "", "Please wait...", true);
                dialog.show();

            }
        });

        listview_notification.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                // TODO Auto-generated method stub

                notificationid = String.valueOf(notificationBaseHoldersListtoshow.get(pos).getId());
                Log.e(TAG, "Notification id" + notificationid);
                deletePopup(notificationid, pos);

                return true;
            }
        });

    }

    // Call Back Class For User Notification
    public class NotificationCallBack implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub
            dialog.dismiss();
            if (responseCode == WebResponseConstants.ResponseCode.OK) {

                noticationBaseHolder = (NotificationBaseHolder) result;
                if (UtilValidate.isNotNull(noticationBaseHolder)) {

                    if (UtilValidate.isNotNull(noticationBaseHolder.getData())) {
                        TodaysParentApp.setNotificationBaseHoldersList(null);
                        TodaysParentApp.setNotificationBaseHoldersList(noticationBaseHolder.getData());
                        Log.e(TAG, "size of notification list" + TodaysParentApp.getNotificationBaseHoldersList().size());

                        notificationBaseHoldersListtoshow = new ArrayList<ProductNotificationBaseHolder>();
                        notificationBaseHoldersListtoshow.addAll(TodaysParentApp.getNotificationBaseHoldersList());
                        listview_notification.setAdapter(notificationListAdapter);
                        notificationListAdapter.notifyDataSetChanged();
                    }
                }

            }
        }

        @Override
        public void onFinish(int responseCode, String result) {
            // TODO Auto-generated method stub
            dialog.dismiss();
        }

    }

    public void showClearAllNotificationLayout() {
        notification_clear_all_layout.setVisibility(View.VISIBLE);
        cart_layout.setVisibility(View.GONE);
    }

    public void hideClearAllNotificationLayout() {
        notification_clear_all_layout.setVisibility(View.GONE);
        cart_layout.setVisibility(View.VISIBLE);
    }

    private void initManagers() {
        // TODO Auto-generated method stub
        notificationCallBackdelete = new NotificationCallBackDelete();
        notificationCallBack = new NotificationCallBack();

    }

    private void initViews() {
        // TODO Auto-generated method stub
        add_to_cart_icon = (ImageView) findViewById(R.id.add_to_cart_icon);
        page_heading = (TextView) findViewById(R.id.page_heading);
        listview_notification = (ListView) findViewById(R.id.listview_notification);
        linear_notification = (LinearLayout) findViewById(R.id.linear_notification);
        notification_clear_all_layout = (TextView) findViewById(R.id.clear_all);
        cart_layout = (LinearLayout) findViewById(R.id.cart_layout);

    }

    public void deletePopup(final String notificationID, final int pos) {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.delete_popup_menu, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        popupWindow.update();
        popupWindow.showAtLocation(linear_notification, Gravity.CENTER, 0, 0);

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

                /**API FOR SINGLE DELETE**/
                NotificationManager.getInstance().deleteSingleNotification(NotificationActivity.this, notificationCallBackdelete, sessionid, notificationid);

            }
        });

    }

    //Call Back Class For User Notification
    public class NotificationCallBackDelete implements AsyncTaskCallBack {

        @Override
        public void onFinish(int responseCode, Object result) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onFinish(int pos, String result) {
            // TODO Auto-generated method stub

            if (result.equals("success")) {

                notificationBaseHoldersListtoshow.remove(pos);
                Log.e("", "after deletion size" + notificationBaseHoldersListtoshow.size());
                TodaysParentApp.setNotificationBaseHoldersList(notificationBaseHoldersListtoshow);
                notificationListAdapter.notifyDataSetChanged();

            }

            if (result.equals("cleared")) {

                notificationBaseHoldersListtoshow = new ArrayList<ProductNotificationBaseHolder>();
                TodaysParentApp.setNotificationBaseHoldersList(null);
                listview_notification.setAdapter(notificationListAdapter);
                notificationListAdapter.notifyDataSetChanged();

            }

        }

    }

}
