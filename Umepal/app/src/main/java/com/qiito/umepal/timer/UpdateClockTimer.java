package com.qiito.umepal.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.widget.TextView;

public class UpdateClockTimer implements Runnable {
	private TextView timer;
    private String tag;
    private Handler handler;

    public UpdateClockTimer(TextView timer, String tag,Handler handler) {
		this.timer = timer;
		this.tag = tag;
		this.handler=handler;
	}

	@Override
	public void run() {
		if(timer.getTag().toString().equals(tag)){
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
        	String start_time=timer.getText().toString();
			if(start_time!="") {
				sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));
				Date date1 = null;
				long different = 0;
				try {
					date1 = sdf1.parse("1970-01-01 " +start_time);

				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
					different = date1.getTime();

					if (different > 0) {
						different = different - 1000;
						String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(different),
								TimeUnit.MILLISECONDS.toMinutes(different) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(different)),
								TimeUnit.MILLISECONDS.toSeconds(different) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(different)));
						timer.setText(hms);
					} else {
						timer.setText("00:00:00");
						handler.removeCallbacks(this);
					        }

				handler.postDelayed(this, 1000);
			}else {timer.setText("");
			}
		}
	}



}