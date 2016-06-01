package com.qiito.umepal.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.listeners.ClickListener;

/**
 * Created by abin on 1/6/16.
 */
public class YesNoPopUp {
    String message;
    Activity activity;
    Typeface tf;
    ClickListener<Boolean> listener;

    public YesNoPopUp(String message, Activity activity, ClickListener<Boolean> listener) {
        this.message = message;
        this.activity = activity;
        this.listener = listener;
    }

    public void show() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.yesnopopup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //popupWindow.setAnimationStyle(R.style.dialog_animation);
        popupWindow.update();
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        //popupWindow.setAnimationStyle(R.style.dialog_animation);
//        tf = Typeface.createFromAsset(activity.getAssets(), "Avenir-Medium.ttf");
        final TextView heading = (TextView) popupView.findViewById(R.id.popup_heading);
        final TextView yesText = (TextView) popupView.findViewById(R.id.yes_text);
        final TextView noText = (TextView) popupView.findViewById(R.id.no_text);
        final LinearLayout yes = (LinearLayout) popupView.findViewById(R.id.popup_yes);
        final LinearLayout no = (LinearLayout) popupView.findViewById(R.id.popup_no);
        heading.setTypeface(tf);
        yesText.setTypeface(tf);
        noText.setTypeface(tf);
        heading.setText(message);

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
                listener.onClick(true);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                popupWindow.dismiss();
            }
        });
    }
}
