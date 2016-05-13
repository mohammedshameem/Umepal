package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by shiya on 3/9/15.
 */
public class UserLogoutHolder implements Serializable {

    private String status;
    private List<UserResponseHolder> data;
    private int code;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UserResponseHolder> getData() {
        return data;
    }

    public void setData(List<UserResponseHolder> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
