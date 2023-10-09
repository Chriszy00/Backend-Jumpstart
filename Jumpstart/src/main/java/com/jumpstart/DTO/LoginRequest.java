package com.jumpstart.DTO;

import javax.validation.constraints.NotBlank;

public class LoginRequest {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
	private String memberNumber;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(String memberNumber) {
		this.memberNumber = memberNumber;
	}
	
}
