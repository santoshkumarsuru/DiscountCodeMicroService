package com.amtrust.discount.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "RECIPIENT")
public class Recipient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@OneToMany(mappedBy = "recipient", fetch = FetchType.LAZY, orphanRemoval = true)
	@Cascade({ CascadeType.ALL })
	@Fetch(FetchMode.SELECT)
	private List<DiscountCode> discountCodes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<DiscountCode> getDiscountCodes() {
		if(discountCodes == null) {
			discountCodes = new ArrayList<DiscountCode>();
		}
		return discountCodes;
	}

	public void setDiscountCodes(List<DiscountCode> discountCodes) {
		this.discountCodes = discountCodes;
	}

	@Override
	public String toString() {
		return "Recipient [id=" + id + ", name=" + name + ", email=" + email + ", discountCodes=" + discountCodes + "]";
	}
	
}
