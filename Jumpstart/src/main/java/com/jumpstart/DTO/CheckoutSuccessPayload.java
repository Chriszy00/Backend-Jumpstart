package com.jumpstart.DTO;

public class CheckoutSuccessPayload {

	private String membershipType;
	private double price;
	private String paymentMethod;
	private Long userId;
	
	public CheckoutSuccessPayload() {}

	public CheckoutSuccessPayload(String membershipType, double price, String paymentMethod, Long userId) {
		super();
		this.membershipType = membershipType;
		this.price = price;
		this.paymentMethod = paymentMethod;
		this.userId = userId;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
