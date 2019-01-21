/**
 * 
 */
package com.waio.model;

/**
 * @author Viramm
 *
 */
public class TeamRankPoints {

	private int teamRank;
	private String createdId;
	private String teamId;
	private String teamName;
	private double points;
	/**
	 * @return the teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * @param teamName the teamName to set
	 */
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	/**
	 * @return the teamRank
	 */
	public int getTeamRank() {
		return teamRank;
	}
	/**
	 * @param teamRank the teamRank to set
	 */
	public void setTeamRank(int teamRank) {
		this.teamRank = teamRank;
	}
	/**
	 * @return the createdId
	 */
	public String getCreatedId() {
		return createdId;
	}
	/**
	 * @param createdId the createdId to set
	 */
	public void setCreatedId(String createdId) {
		this.createdId = createdId;
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
	 * @return the points
	 */
	public double getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(double points) {
		this.points = points;
	}
}
