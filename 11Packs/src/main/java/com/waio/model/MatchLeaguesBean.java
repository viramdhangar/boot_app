/**
 * 
 */
package com.waio.model;

import java.sql.Time;
import java.util.Date;


/**
 * @author Viramm
 *
 */
public class MatchLeaguesBean {

	private String uniqueId;
	private Date date;
	private Time time;
	private Date datetime;
	private String team1;
	private String team2;
	private String type;
	private String squad;
	private String toss_winner_team;
	private String winner_team;
	private String matchStarted;
	private String team1Short;
	private String team2Short;
	private String typeShort;
	private String formattedTeamName;	
	private String tournamentName;
	private String isActive;
	private String matchStatus;
	
	// leagues
	private String id;
	private String league;
	private int size;
	private int joinedTeam;
	private int entryFee;
	private String matchId;
	private int winners;
	private int winningAmount;
	private int breakupId;
	private String status;
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
	 * @return the uniqueId
	 */
	public String getUniqueId() {
		return uniqueId;
	}
	/**
	 * @param uniqueId the uniqueId to set
	 */
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the time
	 */
	public Time getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Time time) {
		this.time = time;
	}
	/**
	 * @return the datetime
	 */
	public Date getDatetime() {
		return datetime;
	}
	/**
	 * @param datetime the datetime to set
	 */
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
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
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the squad
	 */
	public String getSquad() {
		return squad;
	}
	/**
	 * @param squad the squad to set
	 */
	public void setSquad(String squad) {
		this.squad = squad;
	}
	/**
	 * @return the toss_winner_team
	 */
	public String getToss_winner_team() {
		return toss_winner_team;
	}
	/**
	 * @param toss_winner_team the toss_winner_team to set
	 */
	public void setToss_winner_team(String toss_winner_team) {
		this.toss_winner_team = toss_winner_team;
	}
	/**
	 * @return the winner_team
	 */
	public String getWinner_team() {
		return winner_team;
	}
	/**
	 * @param winner_team the winner_team to set
	 */
	public void setWinner_team(String winner_team) {
		this.winner_team = winner_team;
	}
	/**
	 * @return the matchStarted
	 */
	public String getMatchStarted() {
		return matchStarted;
	}
	/**
	 * @param matchStarted the matchStarted to set
	 */
	public void setMatchStarted(String matchStarted) {
		this.matchStarted = matchStarted;
	}
	/**
	 * @return the team1Short
	 */
	public String getTeam1Short() {
		return team1Short;
	}
	/**
	 * @param team1Short the team1Short to set
	 */
	public void setTeam1Short(String team1Short) {
		this.team1Short = team1Short;
	}
	/**
	 * @return the team2Short
	 */
	public String getTeam2Short() {
		return team2Short;
	}
	/**
	 * @param team2Short the team2Short to set
	 */
	public void setTeam2Short(String team2Short) {
		this.team2Short = team2Short;
	}
	/**
	 * @return the typeShort
	 */
	public String getTypeShort() {
		return typeShort;
	}
	/**
	 * @param typeShort the typeShort to set
	 */
	public void setTypeShort(String typeShort) {
		this.typeShort = typeShort;
	}
	/**
	 * @return the formattedTeamName
	 */
	public String getFormattedTeamName() {
		return formattedTeamName;
	}
	/**
	 * @param formattedTeamName the formattedTeamName to set
	 */
	public void setFormattedTeamName(String formattedTeamName) {
		this.formattedTeamName = formattedTeamName;
	}
	/**
	 * @return the tournamentName
	 */
	public String getTournamentName() {
		return tournamentName;
	}
	/**
	 * @param tournamentName the tournamentName to set
	 */
	public void setTournamentName(String tournamentName) {
		this.tournamentName = tournamentName;
	}
	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	 * @return the league
	 */
	public String getLeague() {
		return league;
	}
	/**
	 * @param league the league to set
	 */
	public void setLeague(String league) {
		this.league = league;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the joinedTeam
	 */
	public int getJoinedTeam() {
		return joinedTeam;
	}
	/**
	 * @param joinedTeam the joinedTeam to set
	 */
	public void setJoinedTeam(int joinedTeam) {
		this.joinedTeam = joinedTeam;
	}
	/**
	 * @return the entryFee
	 */
	public int getEntryFee() {
		return entryFee;
	}
	/**
	 * @param entryFee the entryFee to set
	 */
	public void setEntryFee(int entryFee) {
		this.entryFee = entryFee;
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
	 * @return the winners
	 */
	public int getWinners() {
		return winners;
	}
	/**
	 * @param winners the winners to set
	 */
	public void setWinners(int winners) {
		this.winners = winners;
	}
	/**
	 * @return the winningAmount
	 */
	public int getWinningAmount() {
		return winningAmount;
	}
	/**
	 * @param winningAmount the winningAmount to set
	 */
	public void setWinningAmount(int winningAmount) {
		this.winningAmount = winningAmount;
	}
	/**
	 * @return the breakupId
	 */
	public int getBreakupId() {
		return breakupId;
	}
	/**
	 * @param breakupId the breakupId to set
	 */
	public void setBreakupId(int breakupId) {
		this.breakupId = breakupId;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
