package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 16/12/15.
 */
public class ShippingDetailsBaseHolder implements Serializable {


    private String status;
    private int code;
    private ShippingDetailsHolder details;

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

    public ShippingDetailsHolder getDetails() {
        return details;
    }

    public void setDetails(ShippingDetailsHolder details) {
        this.details = details;
    }
}
