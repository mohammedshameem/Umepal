package com.qiito.umepal.holder;

import java.io.Serializable;

public class UserBaseHolder implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;
	private int code;
	private String message;
	private UserResponseHolder data;
	

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
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
	public UserResponseHolder getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(UserResponseHolder data) {
		this.data = data;
	}

	
	
}
