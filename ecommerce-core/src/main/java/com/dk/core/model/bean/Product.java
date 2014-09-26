package com.dk.core.model.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dk.core.model.bean.base.UUIDIdEntity;

@Entity
@Table(name = "t_product")
public class Product extends UUIDIdEntity {

	private static final long serialVersionUID = -6040789957633126203L;

	private Brand brand = new Brand();
	private Category category=new Category();
	private String name;
	private String brandName;
	private String status;

	private String desc;
	private String productImg1;
	private String productImg2;
	private String productImg3;
	private String productImg4;
	private String productImg5;

	private String productUrlImg1;
	private String productUrlImg2;
	private String productUrlImg3;
	private String productUrlImg4;
	private String productUrlImg5;

	private String modelNo;
	private String yearMade;
	private String serialNo;
	private Double unitPrice;
	private Double sellPrice;
	private Boolean isDiscount;
	private Integer stock;
	private String sku;

	private String approvedBy;
	private Date approvedTime;
	private String tokenOffBy;
	private Date tokenOffTime;
	private Date updatedTime;
	private Date createdTime;
	private String updatedBy;
	private String createdBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRAND_ID")
	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "BRAND_NAME")
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "`DESC`")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "PRODUCT_IMG1")
	public String getProductImg1() {
		return productImg1;
	}

	public void setProductImg1(String productImg1) {
		this.productImg1 = productImg1;
	}

	@Column(name = "PRODUCT_IMG2")
	public String getProductImg2() {
		return productImg2;
	}

	public void setProductImg2(String productImg2) {
		this.productImg2 = productImg2;
	}

	@Column(name = "PRODUCT_IMG3")
	public String getProductImg3() {
		return productImg3;
	}

	public void setProductImg3(String productImg3) {
		this.productImg3 = productImg3;
	}

	@Column(name = "PRODUCT_IMG4")
	public String getProductImg4() {
		return productImg4;
	}

	public void setProductImg4(String productImg4) {
		this.productImg4 = productImg4;
	}

	@Column(name = "PRODUCT_IMG5")
	public String getProductImg5() {
		return productImg5;
	}

	public void setProductImg5(String productImg5) {
		this.productImg5 = productImg5;
	}

	@Column(name = "PRODUCT_URL_IMG1")
	public String getProductUrlImg1() {
		return productUrlImg1;
	}

	public void setProductUrlImg1(String productUrlImg1) {
		this.productUrlImg1 = productUrlImg1;
	}

	@Column(name = "PRODUCT_URL_IMG2")
	public String getProductUrlImg2() {
		return productUrlImg2;
	}

	public void setProductUrlImg2(String productUrlImg2) {
		this.productUrlImg2 = productUrlImg2;
	}

	@Column(name = "PRODUCT_URL_IMG3")
	public String getProductUrlImg3() {
		return productUrlImg3;
	}

	public void setProductUrlImg3(String productUrlImg3) {
		this.productUrlImg3 = productUrlImg3;
	}

	@Column(name = "PRODUCT_URL_IMG4")
	public String getProductUrlImg4() {
		return productUrlImg4;
	}

	public void setProductUrlImg4(String productUrlImg4) {
		this.productUrlImg4 = productUrlImg4;
	}

	@Column(name = "PRODUCT_URL_IMG5")
	public String getProductUrlImg5() {
		return productUrlImg5;
	}

	public void setProductUrlImg5(String productUrlImg5) {
		this.productUrlImg5 = productUrlImg5;
	}

	@Column(name = "MODEL_NO")
	public String getModelNo() {
		return modelNo;
	}

	public void setModelNo(String modelNo) {
		this.modelNo = modelNo;
	}

	@Column(name = "YEAR_MADE")
	public String getYearMade() {
		return yearMade;
	}

	public void setYearMade(String yearMade) {
		this.yearMade = yearMade;
	}

	@Column(name = "SERIAL_NO")
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "UNIT_PRICE")
	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "SELL_PRICE")
	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	@Column(name = "IS_DISCOUNT")
	public Boolean getIsDiscount() {
		return isDiscount;
	}

	public void setIsDiscount(Boolean isDiscount) {
		this.isDiscount = isDiscount;
	}

	@Column(name = "STOCK")
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	@Column(name = "SKU")
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name = "APPROVED_TIME")
	public Date getApprovedTime() {
		return approvedTime;
	}
	
	public void setApprovedTime(Date approvedTime) {
		this.approvedTime = approvedTime;
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

	@Column(name = "TOKEN_OFF_BY")
	public String getTokenOffBy() {
		return tokenOffBy;
	}

	public void setTokenOffBy(String tokenOffBy) {
		this.tokenOffBy = tokenOffBy;
	}

	@Column(name = "TOKEN_OFF_TIME")
	public Date getTokenOffTime() {
		return tokenOffTime;
	}

	public void setTokenOffTime(Date tokenOffTime) {
		this.tokenOffTime = tokenOffTime;
	}

	
}
