package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 6/6/16.
 */
public class RebateDetailsHolder implements Serializable {

    private String price;
    private String type;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
