package com.waio.fast2sms.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuickTemplatesAPI {
	@JsonProperty("return")
	private boolean status;
	private List<QuickTemplates> data;
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
	 * @return the data
	 */
	public List<QuickTemplates> getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(List<QuickTemplates> data) {
		this.data = data;
	}
}
