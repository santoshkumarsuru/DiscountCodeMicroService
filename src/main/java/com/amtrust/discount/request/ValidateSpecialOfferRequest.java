package com.amtrust.discount.request;

import javax.validation.constraints.NotEmpty;

public class ValidateSpecialOfferRequest {

	@NotEmpty
	private String specialOfferName;

	@NotEmpty
	private String email;

	public String getSpecialOfferName() {
		return specialOfferName;
	}

	public void setSpecialOfferName(String specialOfferName) {
		this.specialOfferName = specialOfferName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ValidateSpecialOfferRequest [specialOfferName=" + specialOfferName + ", email=" + email + "]";
	}

}
