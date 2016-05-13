package com.qiito.umepal.holder;

/**
 * Created by abin on 31/8/15.
 */
public class ProductOfferDate {
    private String date;
    private int timezone_type;
    private String timezone;

    public String getDate() {
        return date;
    }

    public int getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(int timezone_type) {
        this.timezone_type = timezone_type;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
