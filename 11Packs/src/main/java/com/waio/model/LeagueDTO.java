package com.waio.model;

import java.util.List;

public class LeagueDTO {

	private String id;
	private String league;
	private int size;
	private int joinedTeam;
	private int entryFee;
	private String matchId;
	private int winners;
	private int winningAmount;
	private int breakupId;
	private String status;
	private List<WinningBreakupDTO> breakup;
	
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
	 * @return the joinedTeam
	 */
	public int getJoinedTeam() {
		return joinedTeam;
	}
	/**
	 * @param joinedTeam the joinedTeam to set
	 */
	public void setJoinedTeam(int joinedTeam) {
		this.joinedTeam = joinedTeam;
	}
	/**
	 * @return the breakupId
	 */
	public int getBreakupId() {
		return breakupId;
	}
	/**
	 * @param breakupId the breakupId to set
	 */
	public void setBreakupId(int breakupId) {
		this.breakupId = breakupId;
	}
	/**
	 * @return the breakup
	 */
	public List<WinningBreakupDTO> getBreakup() {
		return breakup;
	}
	/**
	 * @param breakup the breakup to set
	 */
	public void setBreakup(List<WinningBreakupDTO> breakup) {
		this.breakup = breakup;
	}
	/**
	 * @return the winningAmount
	 */
	public int getWinningAmount() {
		return winningAmount;
	}
	/**
	 * @param winningAmount the winningAmount to set
	 */
	public void setWinningAmount(int winningAmount) {
		this.winningAmount = winningAmount;
	}
	/**
	 * @return the winners
	 */
	public int getWinners() {
		return winners;
	}
	/**
	 * @param winners the winners to set
	 */
	public void setWinners(int winners) {
		this.winners = winners;
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
	 * @return the league
	 */
	public String getLeague() {
		return league;
	}
	/**
	 * @param league the league to set
	 */
	public void setLeague(String league) {
		this.league = league;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the entryFee
	 */
	public int getEntryFee() {
		return entryFee;
	}
	/**
	 * @param entryFee the entryFee to set
	 */
	public void setEntryFee(int entryFee) {
		this.entryFee = entryFee;
	}
}
