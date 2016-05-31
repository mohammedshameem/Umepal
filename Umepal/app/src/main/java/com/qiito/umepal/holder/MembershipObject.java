package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by abin on 26/5/16.
 */
public class MembershipObject implements Serializable {

    private String created;
    private int id;
    private String membershipname;
    private String price;
    private int status;
    private int isDeleted;
    private int isDisplay;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMembershipname() {
        return membershipname;
    }

    public void setMembershipname(String membershipname) {
        this.membershipname = membershipname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(int isDisplay) {
        this.isDisplay = isDisplay;
    }
}
