package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiya on 6/6/16.
 */
public class RebateListHolder implements Serializable {

    private String year;
    private String month;
    private String total;
    private List<RebateDetailsHolder> details;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<RebateDetailsHolder> getDetails() {
        return details;
    }

    public void setDetails(List<RebateDetailsHolder> details) {
        this.details = details;
    }
}
