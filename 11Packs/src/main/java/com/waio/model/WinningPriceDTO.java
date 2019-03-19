package com.waio.model;

import java.math.BigDecimal;

public class WinningPriceDTO {

	private String userName;
	private String leagueId;
	private String matchId;
	private String teamId;
	private String teamRank;
	private BigDecimal winningAmount;
	private String toWallet;
	/**
	 * @return the toWallet
	 */
	public String getToWallet() {
		return toWallet;
	}
	/**
	 * @param toWallet the toWallet to set
	 */
	public void setToWallet(String toWallet) {
		this.toWallet = toWallet;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the leagueId
	 */
	public String getLeagueId() {
		return leagueId;
	}
	/**
	 * @param leagueId the leagueId to set
	 */
	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}
	/**
	 * @return the matchId
	 */
	public String getMatchId() {
		return matchId;
	}
	/**
	 * @param matchId the matchId to set
	 */
	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}
	/**
	 * @return the teamId
	 */
	public String getTeamId() {
		return teamId;
	}
	/**
	 * @param teamId the teamId to set
	 */
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	/**
	 * @return the teamRank
	 */
	public String getTeamRank() {
		return teamRank;
	}
	/**
	 * @param teamRank the teamRank to set
	 */
	public void setTeamRank(String teamRank) {
		this.teamRank = teamRank;
	}
	/**
	 * @return the winningAmount
	 */
	public BigDecimal getWinningAmount() {
		return winningAmount;
	}
	/**
	 * @param winningAmount the winningAmount to set
	 */
	public void setWinningAmount(BigDecimal winningAmount) {
		this.winningAmount = winningAmount;
	}
	
	
}
