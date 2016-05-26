package com.qiito.umepal.holder;

import java.util.List;

/**
 * Created by abin on 26/5/16.
 */
public class ListRefereeBaseHolder  {

    private String status;
    private  int code;
    private List<UserObjectHolder> data;

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

    public List<UserObjectHolder> getData() {
        return data;
    }

    public void setData(List<UserObjectHolder> data) {
        this.data = data;
    }
}
