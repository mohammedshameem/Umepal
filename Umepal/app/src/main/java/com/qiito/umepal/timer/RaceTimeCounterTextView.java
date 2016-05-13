package com.qiito.umepal.timer;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.TextView;

import com.qiito.umepal.Utilvalidate.Utils;


public class RaceTimeCounterTextView extends TextView {

    private boolean mAttached = false;

    private String offerDate;

    public RaceTimeCounterTextView(Context context) {
        super(context);
    }

    public RaceTimeCounterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RaceTimeCounterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRaceStartTime(String offerDate) {
        this.offerDate = offerDate;
        onTimeChanged();
    }


    private Runnable ticker = new Runnable() {
        @Override
        public void run() {
            onTimeChanged();
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);
            getHandler().postAtTime(this, next);
        }
    };

    private void onTimeChanged() {

        if (offerDate == null) {
            return;
        }

        String hms = Utils.getTimeDifference(offerDate);
        setText(hms);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (offerDate == null) {
            return;
        }

        if (!mAttached) {
            mAttached = true;
            ticker.run();
        }

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAttached) {
            mAttached = false;
            getHandler().removeCallbacks(ticker);
        }
    }
}