package com.qiito.umepal.holder;


public class PayPalTransactionResponseHolder {
	
	private String status;
	private TransactionData data;
	private int code;
	private String message;
	
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public TransactionData getData() {
		return data;
	}
	public void setData(TransactionData data) {
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

}
