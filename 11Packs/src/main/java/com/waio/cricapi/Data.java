package com.waio.cricapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

	private List<Batting> batting;
	private List<Bowling> bowling;
	private List<Fielding> fielding;
	private List<Team> team;
	private String manOfTheMatch;
	private String tossWinnerTeam;
	private boolean matchStarted;
	public List<Batting> getBatting() {
		return batting;
	}
	public void setBatting(List<Batting> batting) {
		this.batting = batting;
	}
	public List<Bowling> getBowling() {
		return bowling;
	}
	public void setBowling(List<Bowling> bowling) {
		this.bowling = bowling;
	}
	public List<Fielding> getFielding() {
		return fielding;
	}
	public void setFielding(List<Fielding> fielding) {
		this.fielding = fielding;
	}
	public List<Team> getTeam() {
		return team;
	}
	public void setTeam(List<Team> team) {
		this.team = team;
	}
	public String getManOfTheMatch() {
		return manOfTheMatch;
	}
	public void setManOfTheMatch(String manOfTheMatch) {
		this.manOfTheMatch = manOfTheMatch;
	}
	public String getTossWinnerTeam() {
		return tossWinnerTeam;
	}
	public void setTossWinnerTeam(String tossWinnerTeam) {
		this.tossWinnerTeam = tossWinnerTeam;
	}
	public boolean isMatchStarted() {
		return matchStarted;
	}
	public void setMatchStarted(boolean matchStarted) {
		this.matchStarted = matchStarted;
	}
}
