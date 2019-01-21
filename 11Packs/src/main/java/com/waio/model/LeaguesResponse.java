package com.waio.model;

import java.util.List;

public class LeaguesResponse {

	private List<LeagueDTO> leagues; 
	private int matchId;
	public List<LeagueDTO> getLeagues() {
		return leagues;
	}
	public void setLeagues(List<LeagueDTO> leagues) {
		this.leagues = leagues;
	}
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
}
