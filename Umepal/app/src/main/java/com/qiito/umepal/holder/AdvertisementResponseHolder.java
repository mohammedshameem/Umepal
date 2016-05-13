/**
 * 
 */
package com.qiito.umepal.holder;

import java.util.List;


public class AdvertisementResponseHolder {
	
	
	private String status;
	private int code;
	private List<AdvertisementData> data;
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
	/**
	 * @return the data
	 */
	public List<AdvertisementData> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<AdvertisementData> data) {
		this.data = data;
	}
	
	
}
