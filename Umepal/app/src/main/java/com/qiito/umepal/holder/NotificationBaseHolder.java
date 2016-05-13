package com.qiito.umepal.holder;

import java.util.List;

public class NotificationBaseHolder {

	private String status;
	private List<ProductNotificationBaseHolder> data;
	private int code;
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the data
	 */
	public List<ProductNotificationBaseHolder> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<ProductNotificationBaseHolder> data) {
		this.data = data;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	
	
}
