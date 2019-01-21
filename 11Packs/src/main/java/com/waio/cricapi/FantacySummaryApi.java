package com.waio.cricapi;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FantacySummaryApi {

	// https://cricapi.com/api/cricket?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92
	// https://cricapi.com/api/fantasySummary?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92&unique_id=1119536
	private Data data;
	private String test;
	private Date dateTimeGMT;
	private boolean cache3;
	private int creditsLeft;
	private int v;
	private int ttl;
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public Date getDateTimeGMT() {
		return dateTimeGMT;
	}
	public void setDateTimeGMT(Date dateTimeGMT) {
		this.dateTimeGMT = dateTimeGMT;
	}
	public boolean isCache3() {
		return cache3;
	}
	public void setCache3(boolean cache3) {
		this.cache3 = cache3;
	}
	public int getCreditsLeft() {
		return creditsLeft;
	}
	public void setCreditsLeft(int creditsLeft) {
		this.creditsLeft = creditsLeft;
	}
	public int getV() {
		return v;
	}
	public void setV(int v) {
		this.v = v;
	}
	public int getTtl() {
		return ttl;
	}
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}	
}
