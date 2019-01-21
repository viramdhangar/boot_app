package com.waio.cricapi;

import java.util.List;

import com.waio.model.PlayerDTO;

public class Team {

	private String name;
	private List<PlayerDTO> players;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
}
