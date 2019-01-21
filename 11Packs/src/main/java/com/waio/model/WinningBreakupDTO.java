package com.waio.model;

public class WinningBreakupDTO {
	private String id;
	private int prizeMoney;
	private String prizeRank;
	private int winningPercent;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @return the winningPercent
	 */
	public int getWinningPercent() {
		return winningPercent;
	}
	/**
	 * @param winningPercent the winningPercent to set
	 */
	public void setWinningPercent(int winningPercent) {
		this.winningPercent = winningPercent;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the prizeMoney
	 */
	public int getPrizeMoney() {
		return prizeMoney;
	}
	/**
	 * @param prizeMoney the prizeMoney to set
	 */
	public void setPrizeMoney(int prizeMoney) {
		this.prizeMoney = prizeMoney;
	}
	/**
	 * @return the prizeRank
	 */
	public String getPrizeRank() {
		return prizeRank;
	}
	/**
	 * @param prizeRank the prizeRank to set
	 */
	public void setPrizeRank(String prizeRank) {
		this.prizeRank = prizeRank;
	}
}
