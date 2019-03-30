package com.waio.model;

import java.math.BigDecimal;
import java.util.Date;

public class AccountDTO {

	private String username;
	private BigDecimal totalAmount;
	private BigDecimal depositedAmount;
	private BigDecimal bonusAmount;
	private BigDecimal winning;
	//private String status;
	private String promotion;
	
	// payment gateway parameter
	private String id;
	private String title;
	private String status;
	private String link;
	private String product;
	private String seller;
	private String currency;
	private String amount;
	private String name;
	private String email;
	private String phone;
	private String payout;
	private String fees;
	private String total_taxes;
//	private String cases;
	private String instrument_type;
	private String billing_instrument;
	private String failure;
	private Date created_at;
	private Date updated_at;
	private String tax_invoice_id;
	private String resource_uri;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}
	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}
	/**
	 * @return the seller
	 */
	public String getSeller() {
		return seller;
	}
	/**
	 * @param seller the seller to set
	 */
	public void setSeller(String seller) {
		this.seller = seller;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the payout
	 */
	public String getPayout() {
		return payout;
	}
	/**
	 * @param payout the payout to set
	 */
	public void setPayout(String payout) {
		this.payout = payout;
	}
	/**
	 * @return the fees
	 */
	public String getFees() {
		return fees;
	}
	/**
	 * @param fees the fees to set
	 */
	public void setFees(String fees) {
		this.fees = fees;
	}
	/**
	 * @return the total_taxes
	 */
	public String getTotal_taxes() {
		return total_taxes;
	}
	/**
	 * @param total_taxes the total_taxes to set
	 */
	public void setTotal_taxes(String total_taxes) {
		this.total_taxes = total_taxes;
	}
	/**
	 * @return the instrument_type
	 */
	public String getInstrument_type() {
		return instrument_type;
	}
	/**
	 * @param instrument_type the instrument_type to set
	 */
	public void setInstrument_type(String instrument_type) {
		this.instrument_type = instrument_type;
	}
	/**
	 * @return the billing_instrument
	 */
	public String getBilling_instrument() {
		return billing_instrument;
	}
	/**
	 * @param billing_instrument the billing_instrument to set
	 */
	public void setBilling_instrument(String billing_instrument) {
		this.billing_instrument = billing_instrument;
	}
	/**
	 * @return the failure
	 */
	public String getFailure() {
		return failure;
	}
	/**
	 * @param failure the failure to set
	 */
	public void setFailure(String failure) {
		this.failure = failure;
	}
	/**
	 * @return the created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the updated_at
	 */
	public Date getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}
	/**
	 * @return the tax_invoice_id
	 */
	public String getTax_invoice_id() {
		return tax_invoice_id;
	}
	/**
	 * @param tax_invoice_id the tax_invoice_id to set
	 */
	public void setTax_invoice_id(String tax_invoice_id) {
		this.tax_invoice_id = tax_invoice_id;
	}
	/**
	 * @return the resource_uri
	 */
	public String getResource_uri() {
		return resource_uri;
	}
	/**
	 * @param resource_uri the resource_uri to set
	 */
	public void setResource_uri(String resource_uri) {
		this.resource_uri = resource_uri;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the depositedAmount
	 */
	public BigDecimal getDepositedAmount() {
		return depositedAmount;
	}
	/**
	 * @param depositedAmount the depositedAmount to set
	 */
	public void setDepositedAmount(BigDecimal depositedAmount) {
		this.depositedAmount = depositedAmount;
	}
	/**
	 * @return the bonusAmount
	 */
	public BigDecimal getBonusAmount() {
		return bonusAmount;
	}
	/**
	 * @param bonusAmount the bonusAmount to set
	 */
	public void setBonusAmount(BigDecimal bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
	/**
	 * @return the winning
	 */
	public BigDecimal getWinning() {
		return winning;
	}
	/**
	 * @param winning the winning to set
	 */
	public void setWinning(BigDecimal winning) {
		this.winning = winning;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the promotion
	 */
	public String getPromotion() {
		return promotion;
	}
	/**
	 * @param promotion the promotion to set
	 */
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}
	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
}
