package com.fatin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User {

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "ADRESS")
	private String address;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name="IS_APPROVE")
	private Integer isApprove;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getIsApprove() {
		return isApprove;
	}
	public void setIsApprove(Integer isApprove) {
		this.isApprove = isApprove;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
