package com.amtrust.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.amtrust.discount.constant.ServicesErrorCode;
import com.amtrust.discount.entity.Recipient;
import com.amtrust.discount.request.ValidateDiscountCodeRequest;
import com.amtrust.discount.request.ValidateSpecialOfferRequest;
import com.amtrust.discount.response.DiscountCodeResponse;
import com.amtrust.discount.response.DiscountInfo;
import com.amtrust.discount.response.GetDiscountCodeResponse;
import com.amtrust.discount.response.RecipientResponse;
import com.amtrust.discount.response.RedeemCodeResponse;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DiscountControllerTest {

	private static RestTemplate restTemplate = new RestTemplate();
	static String localHostUrl = "http://localhost:8080";
	static List<DiscountInfo> discountInfos;

	@Test(expected = HttpServerErrorException.class)
	public void addRecipient_null_email() {
		Recipient r = new Recipient();
		r.setEmail(null);
		r.setName("kalyan");
		RecipientResponse response = restTemplate.postForObject(localHostUrl + "/addRecipient", r, RecipientResponse.class);
	}

	@Test
	public void a_getAllDiscountCodesByEmail() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("email", "kalyan@gmail.com");
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(bodyMap, headers);

		GetDiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/getAllDiscountCodesByEmail",
				requestEntity, GetDiscountCodeResponse.class);
		assertNotNull(response);
		assertTrue(!CollectionUtils.isEmpty(response.getDiscountInfos()));
		discountInfos = response.getDiscountInfos();
	}

	// Redeem Discount Tests
	@Test
	public void redeemDiscountCode() {
		ValidateDiscountCodeRequest request = new ValidateDiscountCodeRequest();
		request.setEmail("kalyan@gmail.com");
		for(DiscountInfo discountInfo : discountInfos){
			request.setDiscountCode(discountInfo.getDiscountCode());
			RedeemCodeResponse response = restTemplate.postForObject(localHostUrl + "/redeemDiscountCode", request,
					RedeemCodeResponse.class);
			assertNotNull(response);
			assertNotNull(response.getDiscountPercent());
		}
	}

	@Test
	public void redeemDiscountCode_invalid_discount_code() {
		ValidateDiscountCodeRequest request = new ValidateDiscountCodeRequest();
		request.setEmail("kalyan@gmail.com");
		request.setDiscountCode("Invalid code");
		RedeemCodeResponse response = restTemplate.postForObject(localHostUrl + "/redeemDiscountCode", request,
				RedeemCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(),
				ServicesErrorCode.DISCOUNT_CODE_NOT_MAPPED.getErrorCode());
	}

	@Test
	public void redeemDiscountCode_invalid_email() {
		ValidateDiscountCodeRequest request = new ValidateDiscountCodeRequest();
		request.setEmail("12131@gmail.com");
		request.setDiscountCode("Invalid code");
		RedeemCodeResponse response = restTemplate.postForObject(localHostUrl + "/redeemDiscountCode", request,
				RedeemCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(), ServicesErrorCode.RECIPIENT_NOT_EXIST.getErrorCode());
	}

	// Validate Discount Code Tests
	@Test
	public void validateAndGetDiscountCode_already_mapped() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		request.setEmail("kalyan@gmail.com");
		request.setSpecialOfferName("Discount10");
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(),
				ServicesErrorCode.ALREADY_OFFER_MAPPED.getErrorCode());
	}

	@Test
	public void validateAndGetDiscountCode_null_email() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		request.setEmail(null);
		request.setSpecialOfferName("Discount10");
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(), ServicesErrorCode.INVALID_REQUEST.getErrorCode());
	}

	@Test
	public void validateAndGetDiscountCode_null_offer() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		request.setEmail("kalyan@gmail.com");
		request.setSpecialOfferName(null);
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(), ServicesErrorCode.INVALID_REQUEST.getErrorCode());
	}

	@Test
	public void validateAndGetDiscountCode_null_request() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		request.setEmail(null);
		request.setSpecialOfferName(null);
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(), ServicesErrorCode.INVALID_REQUEST.getErrorCode());
	}

	@Test
	public void validateAndGetDiscountCode_no_recipient() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		request.setEmail("Junk Email");
		request.setSpecialOfferName("Discount10");
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(), ServicesErrorCode.RECIPIENT_NOT_EXIST.getErrorCode());
	}

	@Test
	public void validateAndGetDiscountCode_no_offer() {
		ValidateSpecialOfferRequest request = new ValidateSpecialOfferRequest();
		request.setEmail("kalyan@gmail.com");
		request.setSpecialOfferName("Junk Discount");
		DiscountCodeResponse response = restTemplate.postForObject(localHostUrl + "/validateAndGetDiscountCode",
				request, DiscountCodeResponse.class);
		assertNotNull(response);
		assertEquals(response.getResponseStatus().getErrorCode(), ServicesErrorCode.OFFER_NOT_EXIST.getErrorCode());
	}

}
