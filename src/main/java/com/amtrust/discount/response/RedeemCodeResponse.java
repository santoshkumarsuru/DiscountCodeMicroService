package com.amtrust.discount.response;

public class RedeemCodeResponse {
	private ResponseStatus responseStatus;
	private double discountPercent;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	@Override
	public String toString() {
		return "RedeemCodeResponse [responseStatus=" + responseStatus + ", discountPercent=" + discountPercent + "]";
	}

}
