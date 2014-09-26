package com.dk.core.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dk.core.model.bean.base.UUIDIdEntity;

@Entity
@Table(name = "t_user")
public class User extends UUIDIdEntity {

	private static final long serialVersionUID = 1910274099399700585L;

	private String status;
	private String name;
	private String email;
	private String cellPhone;
	private String userName;
	private String password;
	private String lastAccessIP;
	private Boolean isAdmin;
	private Date updatedTime;
	private Date createdTime;
	private String updatedBy;
	private String createdBy;

	@Column(name = "`STATUS`")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "CELL_PHONE")
	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "LAST_ACCESS_IP")
	public String getLastAccessIP() {
		return lastAccessIP;
	}

	public void setLastAccessIP(String lastAccessIP) {
		this.lastAccessIP = lastAccessIP;
	}

	@Column(name = "USER_ADMIN")
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "UPDATED_TIME")
	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Column(name = "CREATED_TIME")
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "UPDATED_BY")
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
}
