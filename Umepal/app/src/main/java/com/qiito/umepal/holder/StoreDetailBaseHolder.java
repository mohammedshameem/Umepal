package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 9/10/15.
 */
public class StoreDetailBaseHolder implements Serializable {

    private String status;
    private int code;
    private StoreDetailsHolder data;

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

    public StoreDetailsHolder getData() {
        return data;
    }

    public void setData(StoreDetailsHolder data) {
        this.data = data;
    }
}
