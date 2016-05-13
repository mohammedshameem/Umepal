package com.qiito.umepal.webservice;

public interface WebResponseConstants {

	interface ResponseCode {

		int OK = 200;

		int CREATED = 201;

		int UN_SUCCESSFULL = 400;

		int UN_AUTHORIZED = 401;

		int SERVER_ERROR = 500;

	}
	interface CodeFromApi{

		int UN_SUCCESSFULL = 400;

		int OK = 200;

		int METHODNOT_ALLOWED = 405;

		int NOT_ACCEPTABLE = 406;

		int UN_AUTHORIZED = 401;

		int PRECONDITIONFAILED = 412;

		int SERVICE_UNAVAILABLE = 503;

	}

}
