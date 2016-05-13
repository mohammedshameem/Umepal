package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 9/10/15.
 */
public class StoreRatingBaseHolder implements Serializable {

    private String status;
    private int code;
    private String message;
    private RatingData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RatingData getData() {
        return data;
    }

    public void setData(RatingData data) {
        this.data = data;
    }
}
