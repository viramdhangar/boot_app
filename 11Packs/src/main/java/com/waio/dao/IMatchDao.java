package com.waio.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.AccountDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesBean;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.PointSystemDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;

/**
 * @author Viramm
 *
 */
public interface IMatchDao {

	public List<MatchesDTO> getMatches();
	public List<MatchesDTO> getActiveMatches();
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
	List<MatchTeam> getJoinedLeagueAllTeams(String uniqueNumber, String matchId, String leagueId);
	List<TeamRankPoints> getTeamsRankAndPoints(String matchId, String leagueId);
	List<MatchTeamBean> getTeamDetailsWithPoints(String matchId, String teamId);
	int validateSmallOrGrand(String leagueId, String matchId, String createdId);
	MatchesDTO getMatch(String matchId);
	AccountDTO account(String userName);
	AccountDTO addBalance(AccountDTO account, boolean debit);
	MatchTeam getTeam(String teamId);
	LeagueDTO getLeague(String leagueId);
	List<WinningBreakupDTO> getWinningBreakupByLeagueId(String LeagueId);
	List<MatchesDTO> getCompletedMatchesForWinning();
	boolean validateMatchInSquad(String matchId);
	List<PointSystemDTO> getFantasyPoints();
}
