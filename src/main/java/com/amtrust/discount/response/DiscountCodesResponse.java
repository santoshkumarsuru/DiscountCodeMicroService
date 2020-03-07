package com.amtrust.discount.response;

import java.util.ArrayList;
import java.util.List;

import com.amtrust.discount.entity.DiscountCode;

public class DiscountCodesResponse {
	private ResponseStatus responseStatus;
	List<DiscountCode> discountCodes;
	public List<DiscountCode> getDiscountCodes() {
		if(discountCodes == null) {
			discountCodes = new ArrayList<DiscountCode>();
		}
		return discountCodes;
	}
	public void setDiscountCodes(List<DiscountCode> discountCodes) {
		this.discountCodes = discountCodes;
	}
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}
	@Override
	public String toString() {
		return "DiscountCodesResponse [responseStatus=" + responseStatus + ", discountCodes=" + discountCodes + "]";
	}
	
	
}
