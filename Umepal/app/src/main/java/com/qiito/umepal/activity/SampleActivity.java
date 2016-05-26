package com.qiito.umepal.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.qiito.umepal.R;
import com.qiito.umepal.adapters.ArrayWheelAdapter;
import com.qiito.umepal.listeners.OnWheelChangedListener;
import com.qiito.umepal.listeners.OnWheelScrollListener;
import com.qiito.umepal.whellview.WheelView;

/**
 * Created by shiya on 17/12/15.
 */
public class SampleActivity extends Activity {

    private Button showPopup;
    private PopupWindow mPopup;
    private WheelView wheelView;
    private boolean mWheelScrolled = false;
    private String mIntialMeeting = "";
    private String[] mMeeting;
    private String[] Numbers={"1","2","3","4","5","6","7","8","9","ALL"};
    private TextView dismissText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_activity);

        showPopup = (Button)findViewById(R.id.show_popup);

        showPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = (LayoutInflater) SampleActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View layout = layoutInflater.inflate(R.layout.wheel_view_popup, null);
                mPopup = new PopupWindow(layout,
                        android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);

                wheelView = (WheelView)layout.findViewById(R.id.wheel_view);
                //dismissText = (TextView)layout.findViewById(R.id.dismiss_text);
                initWheelkm(R.id.wheel_view, Numbers, wheelView);

                mPopup.showAtLocation(wheelView, Gravity.CENTER, 0, 0);

                /*dismissText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopup.dismiss();
                    }
                });*/


            }
        });




    }

    private void initWheelkm(int id, String meetingid[], View view) {

        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
                SampleActivity.this, meetingid);
        WheelView wheel = getWheelkm(id, view);
        wheel.setViewAdapter(adapter);
        wheel.setCurrentItem(0);
        wheel.setVisibleItems(5);
        wheel.addChangingListenerkm(changedListenerkm);
        wheel.addScrollingListenerkm(scrolledListenerkm);
        wheel.setCyclic(false);
        wheel.setEnabled(true);
    }

    private WheelView getWheelkm(int id, View view) {
        return (WheelView) view.findViewById(id);
    }

    private void mixWheelkm(int id, View view) {
        WheelView wheel = getWheel(id, view);
        wheel.scroll(-350 + (int) (Math.random() * 50), 2000);

    }

    private WheelView getWheel(int id, View view) {
        return (WheelView) view.findViewById(id);
    }

    private void mixWheel(int id, View view) {
        WheelView wheel = getWheel(id, view);
        wheel.scroll(-350 + (int) (Math.random() * 50), 2000);

    }

    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!mWheelScrolled) {

            }
        }
    };

    private OnWheelChangedListener changedListenerkm = new OnWheelChangedListener() {
        @Override
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!mWheelScrolled) {
                // updateStatus();
            }
        }
    };

    OnWheelScrollListener scrolledListenerkm = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
            mWheelScrolled = true;
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            mWheelScrolled = false;
            mIntialMeeting = mMeeting[wheel.getCurrentItem()];

        }

    };
}
