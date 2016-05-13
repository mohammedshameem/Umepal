package com.qiito.umepal.holder;

import java.util.List;

/**
 * Created by abin on 29/9/15.
 */
public class ProductLikeHolder {

    private String status;
    private List<String>data;
    private int code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
