package com.qiito.umepal.holder;

/**
 * Created by abin on 27/11/15.
 */
public class ShoppingCartBaseHolder {
    private String status;
    private int code;
    private String data;
    private CartDetailsObject message;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    public CartDetailsObject getMessage() {
        return message;
    }

    public void setMessage(CartDetailsObject message) {
        this.message = message;
    }

}
