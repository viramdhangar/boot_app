/**
 * 
 */
package com.waio.model;

import java.util.List;

/**
 * @author Viramm
 *
 */
public class MatchTeam {
	private String id;
	private String teamName;
	private List<PlayerDTO> players;
	private String matchId;
	private String uniqueNumber;
	private String username;
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return the players
	 */
	public List<PlayerDTO> getPlayers() {
		return players;
	}
	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<PlayerDTO> players) {
		this.players = players;
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
	 * @return the uniqueNumber
	 */
	public String getUniqueNumber() {
		return uniqueNumber;
	}
	/**
	 * @param uniqueNumber the uniqueNumber to set
	 */
	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}
}
