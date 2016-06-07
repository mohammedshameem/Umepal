package com.qiito.umepal.holder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by abin on 17/11/15.
 */
public class MembershipBaseHolder implements Serializable{

    private String status;
    private int code;
    private List<MembershipObject> data;

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

    public List<MembershipObject> getData() {
        return data;
    }

    public void setData(List<MembershipObject> data) {
        this.data = data;
    }
}
