package com.waio.model;

import java.util.List;

public class SquedResponse {

	private List<PlayerDTO> players; 
	private int matchId;
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
	public int getMatchId() {
		return matchId;
	}
	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}
}
