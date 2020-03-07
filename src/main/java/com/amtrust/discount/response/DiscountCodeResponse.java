package com.amtrust.discount.response;

import com.amtrust.discount.entity.DiscountCode;

public class DiscountCodeResponse {
	private ResponseStatus responseStatus;
	DiscountCode discountCode;

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public DiscountCode getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(DiscountCode discountCode) {
		this.discountCode = discountCode;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	@Override
	public String toString() {
		return "DiscountCodeResponse [responseStatus=" + responseStatus + ", discountCode=" + discountCode + "]";
	}

}
