package com.qiito.umepal.webservice;

public interface AsyncTaskCallBack {

	public void onFinish(int responseCode, Object result);

	public void onFinish(int responseCode, String result);

	

}
