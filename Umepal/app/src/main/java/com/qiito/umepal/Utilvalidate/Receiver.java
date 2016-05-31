/*
package com.qiito.umepal.Utilvalidate;

import android.content.Context;
import android.content.Intent;

import com.parse.ParsePushBroadcastReceiver;
import com.qiito.umepal.activity.Loginactivity;
import com.qiito.umepal.activity.MainActivity;
import com.qiito.umepal.managers.DbManager;

*/
/**
 * Created by aswathy on 20/10/15.
 *//*

public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {


        if (DbManager.getInstance().isUserExisting()) {

            Intent newintent = new Intent(context, MainActivity.class);
            newintent.putExtras(intent.getExtras());
            newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newintent);
        } else {
            Intent newintent = new Intent(context, Loginactivity.class);
            newintent.putExtras(intent.getExtras());
            newintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(newintent);
        }

       */
/* Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);*//*

        //PushService.setDefaultPushCallback(this, NavigationDrawerActivity.class);
    }
}*/
