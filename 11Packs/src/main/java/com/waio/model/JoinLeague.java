package com.waio.model;

public class JoinLeague {

	private MatchTeam team;
	private LeagueDTO league;
	/**
	 * @return the team
	 */
	public MatchTeam getTeam() {
		return team;
	}
	/**
	 * @param team the team to set
	 */
	public void setTeam(MatchTeam team) {
		this.team = team;
	}
	/**
	 * @return the league
	 */
	public LeagueDTO getLeague() {
		return league;
	}
	/**
	 * @param league the league to set
	 */
	public void setLeague(LeagueDTO league) {
		this.league = league;
	}
}
