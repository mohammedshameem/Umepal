package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 6/6/16.
 */
public class WalletBaseHolder implements Serializable {

    private String status;
    private int code;
    private WalletDataHolder data;

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

    public WalletDataHolder getData() {
        return data;
    }

    public void setData(WalletDataHolder data) {
        this.data = data;
    }
}
