package com.amtrust.discount;

import static org.junit.Assert.assertNotNull;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.web.client.RestTemplate;

import com.amtrust.discount.entity.Recipient;
import com.amtrust.discount.entity.SpecialOffer;
import com.amtrust.discount.request.ValidateSpecialOfferRequest;
import com.amtrust.discount.response.DiscountCodeResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscountControllerDataSetupTest {
	private static RestTemplate restTemplate = new RestTemplate();
	static String localHostUrl = "http://localhost:8080";

	@Test
	public void addRecipient() {
		Recipient recipient = new Recipient();
		assertRecipient(recipient, "kalyan@gmail.com", "kalyan");
		assertRecipient(recipient, "raghu@gmail.com", "Raghu");
		assertRecipient(recipient, "vardhan@gmail.com", "Vardhan");
	}

	/**
	 * @param recipient
	 * @param email
	 * @param name
	 */
	private void assertRecipient(Recipient recipient, String email, String name) {
		recipient.setEmail(email);
		recipient.setName(name);
		Recipient response = restTemplate.postForObject(localHostUrl + "/addRecipient", recipient, Recipient.class);
		assertNotNull(response);
		assertNotNull(response.getId());
	}

	@Test
	public void addSpecialOffer() {
		SpecialOffer specialOffer = new SpecialOffer();
		assertSpecialOffer(specialOffer, 10, "Discount10");
		assertSpecialOffer(specialOffer, 20, "Discount20");
		assertSpecialOffer(specialOffer, 30, "Discount30");
	}

	/**
	 * @param so
	 * @param discountPercent
	 * @param discountName
	 */
	private void assertSpecialOffer(SpecialOffer specialOffer, double discountPercent, String discountName) {
		specialOffer.setDiscountPercent(discountPercent);
		specialOffer.setOfferName(discountName);
		SpecialOffer response = restTemplate.postForObject(localHostUrl + "/addSpecialOffer", specialOffer,
				SpecialOffer.class);
		assertNotNull(response);
		assertNotNull(response.getId());
	}

	@Test
	public void validateAndGetDiscountCode() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		String email = "kalyan@gmail.com";
		assertValidateAndGetDiscountCode(request, email, "Discount10");
		assertValidateAndGetDiscountCode(request, email, "Discount20");
		assertValidateAndGetDiscountCode(request, email, "Discount30");

		email = "raghu@gmail.com";
		assertValidateAndGetDiscountCode(request, email, "Discount10");
		assertValidateAndGetDiscountCode(request, email, "Discount20");
		assertValidateAndGetDiscountCode(request, email, "Discount30");

		email = "vardhan@gmail.com";
		assertValidateAndGetDiscountCode(request, email, "Discount10");
		assertValidateAndGetDiscountCode(request, email, "Discount20");
		assertValidateAndGetDiscountCode(request, email, "Discount30");
	}

	/**
	 * @param request
	 * @param email
	 * @param specialOfferName
	 */
	private void assertValidateAndGetDiscountCode(ValidateSpecialOfferRequest request, String email,
			String specialOfferName) {
		request.setEmail(email);
		request.setSpecialOfferName(specialOfferName);
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertNotNull(response.getDiscountCode());
		assertNotNull(response.getDiscountCode().getDiscountCode());
		assertNotNull(response.getDiscountCode().getExpiryDate());
	}
}
