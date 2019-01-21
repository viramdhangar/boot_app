/**
 * 
 */
package com.waio.model;

import java.sql.Date;
import java.sql.Time;

/**
 * @author Viramm
 *
 */
public class MatchDTO {

	private int id;
	private Date matchStartDate;
	private Time matchStartTime;
	private String team1;
	private String team2;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the matchStartDate
	 */
	public Date getMatchStartDate() {
		return matchStartDate;
	}
	/**
	 * @param matchStartDate the matchStartDate to set
	 */
	public void setMatchStartDate(Date matchStartDate) {
		this.matchStartDate = matchStartDate;
	}
	/**
	 * @return the matchStartTime
	 */
	public Time getMatchStartTime() {
		return matchStartTime;
	}
	/**
	 * @param matchStartTime the matchStartTime to set
	 */
	public void setMatchStartTime(Time matchStartTime) {
		this.matchStartTime = matchStartTime;
	}
	/**
	 * @return the team1
	 */
	public String getTeam1() {
		return team1;
	}
	/**
	 * @param team1 the team1 to set
	 */
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	/**
	 * @return the team2
	 */
	public String getTeam2() {
		return team2;
	}
	/**
	 * @param team2 the team2 to set
	 */
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
}
