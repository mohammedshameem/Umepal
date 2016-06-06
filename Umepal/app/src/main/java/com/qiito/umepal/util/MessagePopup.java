package com.qiito.umepal.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qiito.umepal.R;


/**
 * Created by shiya on 18/3/16.
 */
public class MessagePopup {
    String message;
    String heading;
    Activity activity;

    public MessagePopup(String heading,String message, Activity activity) {
        this.heading = heading;
        this.message = message;
        this.activity = activity;

    }

    public void show() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.message_popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.dialog_animation);
        popupWindow.update();
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        popupWindow.setAnimationStyle(R.style.dialog_animation);
        final TextView headingText = (TextView) popupView.findViewById(R.id.heading);
        final TextView messageText = (TextView)popupView.findViewById(R.id.message);
        final LinearLayout yes = (LinearLayout) popupView.findViewById(R.id.yes_layout);

        headingText.setText(heading);
        messageText.setText(message);


        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });

    }

}
