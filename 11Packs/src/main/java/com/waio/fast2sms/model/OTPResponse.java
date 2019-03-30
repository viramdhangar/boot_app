package com.waio.fast2sms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OTPResponse {
	@JsonProperty("return")
	private boolean status;
	private String request_id;
	private List<String> message;
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
	 * @return the request_id
	 */
	public String getRequest_id() {
		return request_id;
	}
	/**
	 * @param request_id the request_id to set
	 */
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	/**
	 * @return the message
	 */
	public List<String> getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(List<String> message) {
		this.message = message;
	} 
}
