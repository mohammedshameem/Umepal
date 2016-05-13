package com.qiito.umepal.holder;

import java.io.Serializable;

/**
 * Created by shiya on 16/12/15.
 */
public class ShippingDetailsHolder implements Serializable{

    private ShippingAddressHolder shippingaddress;

    public ShippingAddressHolder getShippingaddress() {
        return shippingaddress;
    }

    public void setShippingaddress(ShippingAddressHolder shippingaddress) {
        this.shippingaddress = shippingaddress;
    }
}
