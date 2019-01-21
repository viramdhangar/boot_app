package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BattingScore {

	private String pid;
	@JsonProperty("6s")
	private String sixes;
	@JsonProperty("4s")
	private String fours;
	@JsonProperty("R")
	private String run;
	@JsonProperty("B")
	private String ball;
	@JsonProperty("SR")
	private String sRate;
	private String dismissal;
/*	@JsonProperty("dismissal-info")
	private String dismissalInfo;*/
	private String batsman;
/*	@JsonProperty("dismissal-by")
	private Players dismissalBy;*/
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
	 * @return the sixes
	 */
	public String getSixes() {
		return sixes;
	}
	/**
	 * @param sixes the sixes to set
	 */
	public void setSixes(String sixes) {
		this.sixes = sixes;
	}
	/**
	 * @return the fours
	 */
	public String getFours() {
		return fours;
	}
	/**
	 * @param fours the fours to set
	 */
	public void setFours(String fours) {
		this.fours = fours;
	}
	/**
	 * @return the run
	 */
	public String getRun() {
		return run;
	}
	/**
	 * @param run the run to set
	 */
	public void setRun(String run) {
		this.run = run;
	}
	/**
	 * @return the ball
	 */
	public String getBall() {
		return ball;
	}
	/**
	 * @param ball the ball to set
	 */
	public void setBall(String ball) {
		this.ball = ball;
	}
	/**
	 * @return the sRate
	 */
	public String getsRate() {
		return sRate;
	}
	/**
	 * @param sRate the sRate to set
	 */
	public void setsRate(String sRate) {
		this.sRate = sRate;
	}
	/**
	 * @return the dismissal
	 */
	public String getDismissal() {
		return dismissal;
	}
	/**
	 * @param dismissal the dismissal to set
	 */
	public void setDismissal(String dismissal) {
		this.dismissal = dismissal;
	}
	/**
	 * @return the batsman
	 */
	public String getBatsman() {
		return batsman;
	}
	/**
	 * @param batsman the batsman to set
	 */
	public void setBatsman(String batsman) {
		this.batsman = batsman;
	}
}
