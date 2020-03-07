package com.amtrust.discount.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.amtrust.discount.constant.ServicesErrorCode;
import com.amtrust.discount.entity.DiscountCode;
import com.amtrust.discount.entity.Recipient;
import com.amtrust.discount.entity.SpecialOffer;
import com.amtrust.discount.request.ValidateDiscountCodeRequest;
import com.amtrust.discount.response.ResponseStatus;
import com.amtrust.discount.response.ValidationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ValidatorUtils {

	public ResponseStatus validateSpecialOfferRequest(Recipient recipient, SpecialOffer specialOffer) {
		ResponseStatus responseStatus = new ResponseStatus();
		if (recipient == null) {
			responseStatus.populateErrorResponseStatus(ServicesErrorCode.RECIPIENT_NOT_EXIST);
		} else if (specialOffer == null) {
			responseStatus.populateErrorResponseStatus(ServicesErrorCode.OFFER_NOT_EXIST);
		}
		// Find if already this discount is mapped to the recipient
		else {
			if (!CollectionUtils.isEmpty(recipient.getDiscountCodes())) {
				DiscountCode dc = recipient.getDiscountCodes().stream()
						.filter(predicate -> specialOffer.getId() == predicate.getSpecialOffer().getId()).findAny()
						.orElse(null);
				if (dc != null) {
					responseStatus.populateErrorResponseStatus(ServicesErrorCode.ALREADY_OFFER_MAPPED);
				}
			}
		}

		return responseStatus;
	}

	public ResponseStatus validateDiscountRequest(Recipient recipient,
			ValidateDiscountCodeRequest validateDiscountRequest) {
		ResponseStatus responseStatus = new ResponseStatus();
		if (recipient == null) {
			responseStatus.populateErrorResponseStatus(ServicesErrorCode.RECIPIENT_NOT_EXIST);
		}
		// Find if Discount code exists and is valid
		else if (!CollectionUtils.isEmpty(recipient.getDiscountCodes())) {
			DiscountCode dc = recipient.getDiscountCodes().stream()
					.filter(predicate -> validateDiscountRequest.getDiscountCode().equals(predicate.getDiscountCode()))
					.findAny().orElse(null);
			if (dc != null && dc.isUsed()) {
				responseStatus.populateErrorResponseStatus(ServicesErrorCode.DISCOUNT_CODE_USED);
			} else if (dc != null && !dc.isUsed()) {
				if (dc.getExpiryDate().isBefore(LocalDate.now())) {
					responseStatus.populateErrorResponseStatus(ServicesErrorCode.DISCOUNT_CODE_EXPIRED);
				}
			} else if (dc == null) {
				responseStatus.populateErrorResponseStatus(ServicesErrorCode.DISCOUNT_CODE_NOT_MAPPED);
			}
		} else {
			responseStatus.populateErrorResponseStatus(ServicesErrorCode.RECIPIENT_NOT_MAPPED_TO_ANY_DISCOUNTS);
		}

		return responseStatus;
	}

	public ValidationResponse isValid(Object validateObject) {
		ValidationResponse validationResponse = new ValidationResponse();
		validationResponse.setValid(true);
		Set<ConstraintViolation<Object>> constraintViolations = Validation.buildDefaultValidatorFactory().getValidator()
				.validate(validateObject);

		if (!constraintViolations.isEmpty()) {
			validationResponse.setValid(false);
			StringBuilder errorMessage = new StringBuilder();

			for (ConstraintViolation<Object> voilation : constraintViolations) {
				if (errorMessage.length() != 0) {
					errorMessage.append(",").append(" ");
				}
				errorMessage.append(String.format("%s: %s", voilation.getPropertyPath(), voilation.getMessage()));
			}

			validationResponse.setErrorResponse(errorMessage.toString());
		}
		return validationResponse;
	}

	public static String writeObjectToJson(Object obj) {
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapperObj.writeValueAsString(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

}
