package com.qiito.umepal.holder;

/**
 * Created by abin on 23/9/15.
 */
public class ReviewBaseHolder  {

    private String status;
    private int code;
    private String message;
    private ReviewDataObject data;


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

    public ReviewDataObject getData() {
        return data;
    }

    public void setData(ReviewDataObject data) {
        this.data = data;
    }
}
