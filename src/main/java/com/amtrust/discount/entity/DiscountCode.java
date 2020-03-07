package com.amtrust.discount.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DISCOUNT_CODE")
public class DiscountCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String discountCode;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "RECIPIENT_ID", nullable = false)
	private Recipient recipient;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIAL_OFFER_ID", nullable = false)
	private SpecialOffer specialOffer;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate expiryDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate usedDate;

	private boolean isUsed;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public SpecialOffer getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(SpecialOffer specialOffer) {
		this.specialOffer = specialOffer;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getUsedDate() {
		return usedDate;
	}

	public void setUsedDate(LocalDate usedDate) {
		this.usedDate = usedDate;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	@Override
	public String toString() {
		return "DiscountCode [id=" + id + ", discountCode=" + discountCode + ", recipient=" + recipient
				+ ", specialOffer=" + specialOffer + ", expiryDate=" + expiryDate + ", usedDate=" + usedDate
				+ ", isUsed=" + isUsed + "]";
	}

}
