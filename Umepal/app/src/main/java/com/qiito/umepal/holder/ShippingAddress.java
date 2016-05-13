package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 30/12/15.
 */
public class ShippingAddress implements Serializable {
    private String shippingfullname;
    private String shippingstreetaddress;
    private String shippingunit;
    private String shippingcountry;
    private String shippingstate;
    private String shippingcity;
    private String shippingpostalcode;
    private String shippingphone;

    public String getShippingfullname() {
        return shippingfullname;
    }

    public void setShippingfullname(String shippingfullname) {
        this.shippingfullname = shippingfullname;
    }

    public String getShippingstreetaddress() {
        return shippingstreetaddress;
    }

    public void setShippingstreetaddress(String shippingstreetaddress) {
        this.shippingstreetaddress = shippingstreetaddress;
    }

    public String getShippingstate() {
        return shippingstate;
    }

    public void setShippingstate(String shippingstate) {
        this.shippingstate = shippingstate;
    }

    public String getShippingcountry() {
        return shippingcountry;
    }

    public void setShippingcountry(String shippingcountry) {
        this.shippingcountry = shippingcountry;
    }

    public String getShippingunit() {
        return shippingunit;
    }

    public void setShippingunit(String shippingunit) {
        this.shippingunit = shippingunit;
    }

    public String getShippingcity() {
        return shippingcity;
    }

    public void setShippingcity(String shippingcity) {
        this.shippingcity = shippingcity;
    }

    public String getShippingpostalcode() {
        return shippingpostalcode;
    }

    public void setShippingpostalcode(String shippingpostalcode) {
        this.shippingpostalcode = shippingpostalcode;
    }

    public String getShippingphone() {
        return shippingphone;
    }

    public void setShippingphone(String shippingphone) {
        this.shippingphone = shippingphone;
    }
}
