package com.waio.service;

import java.util.Date;
import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.AccountDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;

public interface IMatchService {

	public List<MatchesDTO> getMatches() throws Exception;
	public List<MatchesDTO> getActiveMatches();
	public List<LeagueDTO> getLeagues(String matchId);
	public List<PlayerDTO> getSquad(String matchId);
	public String createTeam(MatchTeam team);
	List<MatchTeam> getCreatedTeams(String uniqueNumber);
	List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId);
	MatchTeam getTeam(String uniqueNumber, String matchId, String teamId);
	List<PlayerDTO> setSelectedPlayersInSquad(List<PlayerDTO> squad, MatchTeam matchTeam);
	String joinLeague(MatchTeam team, String leagueId);
	List<WinningBreakupDTO> getWinningBreakup(String LeagueId);
	String teamValidations(MatchTeam team);
	MatchesDTO getMatchLiveStatus (String matchId);
	List<LeagueDTO> getJoinedLeagues(String uniqueNumber, String matchId);
	List<MatchLeaguesDTO> getJoinedMatchLeagues(String uniqueNumber) throws Exception;
	List<MatchTeam> getJoinedLeagueTeams(String uniqueNumber, String matchId, String leagueId);
	String switchTeam(MatchTeam matchTeam, String leagueId, String teamIdOld);
	List<MatchTeam> getJoinedLeagueAllTeams(String matchId, String leagueId);
	List<TeamRankPoints> getTeamsRankAndPoints(String uniqueNumber, String matchId, String leagueId);
	List<MatchTeamBean> getTeamDetailsWithPoints(String matchId, String teamId);
	int validateSmallOrGrand(String leagueId, String matchId, String createdId);
	MatchesDTO getMatch(String matchId) throws Exception;
	AccountDTO account(String userName);
	AccountDTO addBalance(AccountDTO account);
	MatchTeam getTeam(String teamId);
	LeagueDTO getLeague(String leagueId);
}
