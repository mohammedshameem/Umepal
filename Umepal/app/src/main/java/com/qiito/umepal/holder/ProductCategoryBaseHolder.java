package com.qiito.umepal.holder;

/**
 * Created by abin on 2/9/15.
 */
public class ProductCategoryBaseHolder {

    private  String status;
    private int code;
    private ProductCategoryData data;

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

    public ProductCategoryData getData() {
        return data;
    }

    public void setData(ProductCategoryData data) {
        this.data = data;
    }
}
