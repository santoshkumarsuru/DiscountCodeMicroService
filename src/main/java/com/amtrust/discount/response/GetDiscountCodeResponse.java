package com.amtrust.discount.response;

import java.util.List;

public class GetDiscountCodeResponse {

	List<DiscountInfo> discountInfos;
	private ResponseStatus responseStatus;

	public List<DiscountInfo> getDiscountInfos() {
		return discountInfos;
	}

	public void setDiscountInfos(List<DiscountInfo> discountInfos) {
		this.discountInfos = discountInfos;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	@Override
	public String toString() {
		return "GetDiscountCodeResponse [discountInfos=" + discountInfos + ", responseStatus=" + responseStatus + "]";
	}

}
