package com.qiito.umepal.holder;

import java.io.Serializable;

public class ProductDetailsBaseHolder implements Serializable {
	/*implements Serializable*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private int code;
	private ProductDetailDataHolder data;


	public static long getSerialVersionUID() {
		return serialVersionUID;
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

	public ProductDetailDataHolder getData() {
		return data;
	}

	public void setData(ProductDetailDataHolder data) {
		this.data = data;
	}
}
