/**
 * 
 */
package com.waio.model;

/**
 * @author Viramm
 *
 */
public class PlayerPointsDTO {

	private String playerId;
	private String matchId;
	private double points;
	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
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
