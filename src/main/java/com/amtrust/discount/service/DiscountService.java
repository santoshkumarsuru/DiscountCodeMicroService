package com.amtrust.discount.service;

import java.util.List;

import com.amtrust.discount.entity.DiscountCode;
import com.amtrust.discount.entity.Recipient;
import com.amtrust.discount.entity.SpecialOffer;
import com.amtrust.discount.request.ValidateDiscountCodeRequest;
import com.amtrust.discount.request.ValidateSpecialOfferRequest;
import com.amtrust.discount.response.DiscountCodeResponse;
import com.amtrust.discount.response.GetDiscountCodeResponse;
import com.amtrust.discount.response.RedeemCodeResponse;

public interface DiscountService {

	public SpecialOffer addSpecialOffer(SpecialOffer specialOffer);

	public List<SpecialOffer> getSpecialOffers();

	public List<DiscountCode> getDiscountCodes();

	public List<Recipient> getRecipients();

	public Recipient addRecipient(Recipient recipient);

	DiscountCodeResponse validateAndGetDiscountCode(ValidateSpecialOfferRequest validateDiscountRequest);

	RedeemCodeResponse redeemDiscountCode(ValidateDiscountCodeRequest validateDiscountRequest);

	public void deleteSpecialOffer(Long offerId);

	public void deleteRecipient(Long recipientId);

	public GetDiscountCodeResponse getAllDiscountCodesByEmail(String email);

}
