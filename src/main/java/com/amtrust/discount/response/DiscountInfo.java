package com.amtrust.discount.response;

import lombok.Data;

@Data
public class DiscountInfo {

	private String discountCode;
	private String specialOfferName;

	public DiscountInfo(String discountCode, String specialOfferName) {
		super();
		this.discountCode = discountCode;
		this.specialOfferName = specialOfferName;
	}

	public DiscountInfo() {
		super();
	}
}
