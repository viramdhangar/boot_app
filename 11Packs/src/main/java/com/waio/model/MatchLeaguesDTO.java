/**
 * 
 */
package com.waio.model;

import java.util.List;

import com.waio.cricapi.MatchesDTO;

/**
 * @author Viramm
 *
 */
public class MatchLeaguesDTO {

	private MatchesDTO match;
	private List<LeagueDTO> leagues;
	private String matchStatus;
	/**
	 * @return the matchStatus
	 */
	public String getMatchStatus() {
		return matchStatus;
	}
	/**
	 * @param matchStatus the matchStatus to set
	 */
	public void setMatchStatus(String matchStatus) {
		this.matchStatus = matchStatus;
	}
	/**
	 * @return the match
	 */
	public MatchesDTO getMatch() {
		return match;
	}
	/**
	 * @param match the match to set
	 */
	public void setMatch(MatchesDTO match) {
		this.match = match;
	}
	/**
	 * @return the leagues
	 */
	public List<LeagueDTO> getLeagues() {
		return leagues;
	}
	/**
	 * @param leagues the leagues to set
	 */
	public void setLeagues(List<LeagueDTO> leagues) {
		this.leagues = leagues;
	}
}
