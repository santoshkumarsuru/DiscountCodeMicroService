package com.amtrust.discount.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amtrust.discount.constant.ServicesErrorCode;
import com.amtrust.discount.entity.DiscountCode;
import com.amtrust.discount.entity.Recipient;
import com.amtrust.discount.entity.SpecialOffer;
import com.amtrust.discount.request.ValidateDiscountCodeRequest;
import com.amtrust.discount.request.ValidateSpecialOfferRequest;
import com.amtrust.discount.response.DiscountCodeResponse;
import com.amtrust.discount.response.DiscountCodesResponse;
import com.amtrust.discount.response.GetDiscountCodeResponse;
import com.amtrust.discount.response.RedeemCodeResponse;
import com.amtrust.discount.response.ResponseStatus;
import com.amtrust.discount.response.ResponseStatusConstants;
import com.amtrust.discount.response.ValidationResponse;
import com.amtrust.discount.service.DiscountService;
import com.amtrust.discount.utils.ValidatorUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "This controller handles all services related to Discounts codes allocated to recipients", tags = {
		"Discount Controller" })
@RestController
@RequestMapping("/")
public class DiscountController {

	@Autowired
	DiscountService discountService;

	@Autowired
	ValidatorUtils validatorUtils;

	@ApiOperation(value = "This service adds a new recipient")
	@PostMapping(value="/addRecipient", produces = { "application/json" })
	public Recipient addRecipient(@RequestBody Recipient recipient) {
		return discountService.addRecipient(recipient);
	}

	@ApiOperation(value = "This service adds a new Special Offer")
	@PostMapping("/addSpecialOffer")
	public SpecialOffer addSpecialOffer(@RequestBody SpecialOffer specialOffer) {
		return discountService.addSpecialOffer(specialOffer);
	}

	@ApiOperation(value = "This service deletes Special Offer")
	@PostMapping("/deleteSpecialOffer")
	public ResponseStatus deleteSpecialOffer(@RequestParam("specialOfferId") Long specialOfferId) {
		discountService.deleteSpecialOffer(specialOfferId);
		return new ResponseStatus();
	}

	@ApiOperation(value = "This service deletes Recipient")
	@PostMapping("/deleteRecipient")
	public ResponseStatus deleteRecipient(@RequestParam("recipientId") Long recipientId) {
		discountService.deleteRecipient(recipientId);
		return new ResponseStatus();
	}

	@ApiOperation(value = "This service gives list of all recipients")
	@GetMapping(value="/getRecipients", produces = { "application/json" })
	public List<Recipient> getRecipients() {
		return discountService.getRecipients();
	}

	@ApiOperation(value = "This service gives available Special offers accesible for all the recipients")
	@GetMapping("/getSpecialOffers")
	public List<SpecialOffer> getSpecialOffers() {
		return discountService.getSpecialOffers();
	}

	@ApiOperation(value = "This service gives available List of Discount Codes", response = DiscountCodesResponse.class)
	@GetMapping(value="/getDiscountCodes", produces = { "application/json" })
	public DiscountCodesResponse getDiscountCodes() {
		List<DiscountCode> result = discountService.getDiscountCodes();
		DiscountCodesResponse response = new DiscountCodesResponse();

		response.setDiscountCodes(result);
		response.setResponseStatus(new ResponseStatus());
		return response;
	}

	@ApiOperation(value = "Validates And Generates Discount Code", response = DiscountCodeResponse.class)
	@PostMapping(value="/validateAndGetDiscountCode", produces = { "application/json" })
	public DiscountCodeResponse validateAndGetDiscountCode(@RequestBody ValidateSpecialOfferRequest validateSpecialOfferRequest) {
		// Validation Start -- Validating Request Object for Mandatory params
		ValidationResponse validationResponse = validatorUtils.isValid(validateSpecialOfferRequest);
		if (!validationResponse.isValid()) {
			DiscountCodeResponse response = new DiscountCodeResponse();
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.populateResponseStatus(ResponseStatusConstants.ERROR,
					ServicesErrorCode.INVALID_REQUEST.getErrorCode(), validationResponse.getErrorResponse());
			response.setResponseStatus(responseStatus);
			return response;
		}
		return discountService.validateAndGetDiscountCode(validateSpecialOfferRequest);
	}

	@ApiOperation(value = "This service helps to check if user can redeem Discount based on Discount Code and Email", response = RedeemCodeResponse.class)
	@PostMapping(value="/redeemDiscountCode", produces = { "application/json" })
	public RedeemCodeResponse redeemDiscountCode(@RequestBody ValidateDiscountCodeRequest validateDiscountRequest) {
		// Validation Start -- Validating Request Object for Mandatory params
		ValidationResponse validationResponse = validatorUtils.isValid(validateDiscountRequest);
		if (!validationResponse.isValid()) {
			RedeemCodeResponse response = new RedeemCodeResponse();
			ResponseStatus responseStatus = new ResponseStatus();
			responseStatus.populateResponseStatus(ResponseStatusConstants.ERROR,
					ServicesErrorCode.INVALID_REQUEST.getErrorCode(), validationResponse.getErrorResponse());
			response.setResponseStatus(responseStatus);
			return response;
		}

		return discountService.redeemDiscountCode(validateDiscountRequest);
	}

	@ApiOperation(value = "Get All Discount Codes By Email")
	@PostMapping(value="/getAllDiscountCodesByEmail", produces = { "application/json" })
	public GetDiscountCodeResponse getAllDiscountCodesByEmail(@RequestParam("email") String email) {
		return discountService.getAllDiscountCodesByEmail(email);
	}

}
