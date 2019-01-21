package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class FieldingScore {

	private String pid;
	private String name;
	private String runout;
	private String stumped;
	private String bowled;
	private String lbw;
	@JsonProperty("catch")
	private String catches;
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
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
	 * @return the runout
	 */
	public String getRunout() {
		return runout;
	}
	/**
	 * @param runout the runout to set
	 */
	public void setRunout(String runout) {
		this.runout = runout;
	}
	/**
	 * @return the stumped
	 */
	public String getStumped() {
		return stumped;
	}
	/**
	 * @param stumped the stumped to set
	 */
	public void setStumped(String stumped) {
		this.stumped = stumped;
	}
	/**
	 * @return the bowled
	 */
	public String getBowled() {
		return bowled;
	}
	/**
	 * @param bowled the bowled to set
	 */
	public void setBowled(String bowled) {
		this.bowled = bowled;
	}
	/**
	 * @return the lbw
	 */
	public String getLbw() {
		return lbw;
	}
	/**
	 * @param lbw the lbw to set
	 */
	public void setLbw(String lbw) {
		this.lbw = lbw;
	}
	/**
	 * @return the catches
	 */
	public String getCatches() {
		return catches;
	}
	/**
	 * @param catches the catches to set
	 */
	public void setCatches(String catches) {
		this.catches = catches;
	}
}
