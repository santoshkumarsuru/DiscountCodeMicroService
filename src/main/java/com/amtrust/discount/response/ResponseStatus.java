package com.amtrust.discount.response;

import java.io.Serializable;

import com.amtrust.discount.constant.ServicesErrorCode;

public class ResponseStatus implements Serializable {

	private static final long serialVersionUID = -7788619177798333712L;

	private String status = ResponseStatusConstants.SUCCESS;
	private String errorCode;
	private String errorMessage;

	public void populateResponseStatus(String status, String errorCode, String errorMessage) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public void populateErrorResponseStatus(ServicesErrorCode errorCode) {
		this.status = ResponseStatusConstants.ERROR;
		this.errorCode = errorCode.getErrorCode();
		this.errorMessage = errorCode.getErrorMessage();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ResponseStatus [status=" + status + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}

}