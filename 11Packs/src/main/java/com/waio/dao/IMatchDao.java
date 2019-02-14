package com.waio.dao;

import java.math.BigInteger;
import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesBean;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;

public interface IMatchDao {

	public List<MatchesDTO> getMatches();
	public List<LeagueDTO> getLeagues(String matchId);
	public List<PlayerDTO> getSquad(String matchId);
	int createTeam(MatchTeam team);
	BigInteger insertTeam(MatchTeam matchTeam);
	List<MatchTeam> getCreatedTeams(String uniqueNumber);
	List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId);
	List<MatchTeamBean> getTeam(String uniqueNumber, String matchId, String teamId);
	String joinLeague(MatchTeam team, String leagueId);
	List<WinningBreakupDTO> getWinningBreakup(String LeagueId);
	void setTeamName (MatchTeam matchTeam);
	int deleteTeam(String teamId);
	MatchesDTO getMatchLiveStatus (String matchId);
	List<LeagueDTO> getJoinedLeagues(String uniqueNumber, String matchId);
	List<MatchLeaguesBean> getJoinedMatchLeagues(String uniqueNumber);
	List<MatchTeam> getJoinedLeagueTeams(String uniqueNumber, String matchId, String leagueId);
	int switchTeam(MatchTeam matchTeam, String leagueId, String teamIdOld);
	List<MatchTeam> getJoinedLeagueAllTeams(String matchId, String leagueId);
	List<TeamRankPoints> getTeamsRankAndPoints(String uniqueNumber, String matchId, String leagueId);
	List<MatchTeamBean> getTeamDetailsWithPoints(String teamId);
	int validateSmallOrGrand(String leagueId, String matchId, String createdId);
}
