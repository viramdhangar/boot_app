package com.waio.fast2sms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WalletApi {

	@JsonProperty("return")
	private boolean status;
	private String wallet;
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * @return the wallet
	 */
	public String getWallet() {
		return wallet;
	}
	/**
	 * @param wallet the wallet to set
	 */
	public void setWallet(String wallet) {
		this.wallet = wallet;
	}
}
