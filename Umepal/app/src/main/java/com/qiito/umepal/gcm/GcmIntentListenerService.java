package com.qiito.umepal.gcm;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.qiito.umepal.R;
import com.qiito.umepal.util.SharedPreference;


import java.util.List;

/**
 * Created by abin on 26/05/16.
 */
public class GcmIntentListenerService extends GcmListenerService {
    private SharedPreference sharedPreference;
    private String type;
    private Intent in;
    private String jobId;
    private String quoteId;
    private TaskStackBuilder stackBuilder;
    private String quoteStatus;
    private String job_status;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        stackBuilder = TaskStackBuilder.create(this);
    }


    @Override
    public void onMessageReceived(String from, Bundle data) {

        sharedPreference = new SharedPreference(this);
        String message = data.getString("message");
        String url = data.getString("url");
        type = data.getString("type");
        String title = data.getString("title");
        jobId = data.getString("job_id");
        quoteId = data.getString("quote_id");
        quoteStatus = data.getString("quote_status");
        job_status = data.getString("job_status");

        Log.e("MESSAGE", "DATA: " + data.getString("message"));
        Log.e("URL", "DATA: " + data.getString("url"));
        Log.e("TYPE", "DATA: " + data.getString("type"));
        Log.e("TITLE", "DATA: " + data.getString("title"));
        Log.e("JOBID", "DATA: " + data.getString("job_id"));
        Log.e("QUOTEID", "DATA: " + data.getString("quote_id"));
        Log.e("QUOTEID", "quote_status: " + data.getString("quote_status"));
        Log.e("QUOTEID", "job_status: " + data.getString("job_status"));

        if (sharedPreference.getToken() != null) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
           /* Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("type", type);*/
            Intent intent = MultipleNotification();
            // stackBuilder.addNextIntentWithParentStack(intent);
            //PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);
            PendingIntent pIntent = stackBuilder.create(this)
                    .addNextIntentWithParentStack(intent)
                    .getPendingIntent((int) System.currentTimeMillis(), PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_ALL);
            notificationManager.notify(1, mBuilder.build());


            if (checkApp()) {
                Intent broadcastIntent = new Intent("nofication_received");
                broadcastIntent.putExtra("notification_jobid", jobId);
                getApplicationContext().sendBroadcast(broadcastIntent);
            }

        }

    }

    private Intent MultipleNotification() {



        return in;
    }

    public boolean checkApp() {
        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        Log.e("TTT", "YY: " + componentInfo.getPackageName());
        if (componentInfo.getPackageName().equalsIgnoreCase("com.qiito.umepal")) {
            return true;
        } else {
            return false;
        }
    }
}

