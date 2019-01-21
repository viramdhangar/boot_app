package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BowlingScore {

	private String pid;
	@JsonProperty("6s")
	private String sixes;
	@JsonProperty("4s")
	private String fours;
	@JsonProperty("0s")
	private String zeros;
	@JsonProperty("W")
	private String wicket;
	@JsonProperty("R")
	private String run;
	@JsonProperty("M")
	private String maiden;
	@JsonProperty("O")
	private String over;
	@JsonProperty("Econ")
	private String economy;
	private String bowler;
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
	 * @return the zeros
	 */
	public String getZeros() {
		return zeros;
	}
	/**
	 * @param zeros the zeros to set
	 */
	public void setZeros(String zeros) {
		this.zeros = zeros;
	}
	/**
	 * @return the wicket
	 */
	public String getWicket() {
		return wicket;
	}
	/**
	 * @param wicket the wicket to set
	 */
	public void setWicket(String wicket) {
		this.wicket = wicket;
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
	 * @return the maiden
	 */
	public String getMaiden() {
		return maiden;
	}
	/**
	 * @param maiden the maiden to set
	 */
	public void setMaiden(String maiden) {
		this.maiden = maiden;
	}
	/**
	 * @return the over
	 */
	public String getOver() {
		return over;
	}
	/**
	 * @param over the over to set
	 */
	public void setOver(String over) {
		this.over = over;
	}
	/**
	 * @return the economy
	 */
	public String getEconomy() {
		return economy;
	}
	/**
	 * @param economy the economy to set
	 */
	public void setEconomy(String economy) {
		this.economy = economy;
	}
	/**
	 * @return the bowler
	 */
	public String getBowler() {
		return bowler;
	}
	/**
	 * @param bowler the bowler to set
	 */
	public void setBowler(String bowler) {
		this.bowler = bowler;
	}
}
