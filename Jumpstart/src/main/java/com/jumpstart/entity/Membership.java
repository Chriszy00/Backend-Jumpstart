package com.jumpstart.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;

    private String membershipType;
    
    private String membershipNumber;
    
    private double price;
    
    private String status;
    
    private Long roleId;
    
    private LocalDate approvedDate;
    
	public Membership() {
		this.approvedDate = LocalDate.now();
	}

	public Membership(Long id, User member, String membershipType, String membershipNumber, double price, String status,
			Long roleId, LocalDate approvedDate) {
		this.id = id;
		this.member = member;
		this.membershipType = membershipType;
		this.membershipNumber = membershipNumber;
		this.price = price;
		this.status = status;
		this.roleId = roleId;
		this.approvedDate = approvedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public String getMembershipNumber() {
		return membershipNumber;
	}

	public void setMembershipNumber(String membershipNumber) {
		this.membershipNumber = membershipNumber;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public LocalDate getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(LocalDate approvedDate) {
		this.approvedDate = approvedDate;
	}
	
}

