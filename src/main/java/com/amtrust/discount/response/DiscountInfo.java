package com.amtrust.discount.response;

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

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getSpecialOfferName() {
		return specialOfferName;
	}

	public void setSpecialOfferName(String specialOfferName) {
		this.specialOfferName = specialOfferName;
	}

	@Override
	public String toString() {
		return "DiscountInfo [discountCode=" + discountCode + ", specialOfferName=" + specialOfferName + "]";
	}

}
