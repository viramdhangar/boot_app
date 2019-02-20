package com.waio.model;

import java.math.BigDecimal;

public class AccountDTO {

	private String username;
	private BigDecimal amount;
	private BigDecimal depositedAmount;
	private BigDecimal bonusAmount;
	private BigDecimal winning;
	private String status;
	private String promotion;
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
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
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
}
