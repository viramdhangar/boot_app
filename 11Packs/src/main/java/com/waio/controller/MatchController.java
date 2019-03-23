package com.waio.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waio.cricapi.MatchesDTO;
import com.waio.exceptions.ConflictedException;
import com.waio.exceptions.ResourceNotFoundException;
import com.waio.model.AccountDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.PointSystemDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;
import com.waio.service.IMatchService;

@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RestController
@RequestMapping({ "/api" })
public class MatchController {

	@Autowired
	IMatchService matchService;

	@GetMapping("/v1/matches")
	public List<MatchesDTO> getMatches() throws ResourceNotFoundException {
		List<MatchesDTO> matches = new ArrayList<MatchesDTO>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("currentPrincipalName ::"+currentPrincipalName);
		try {
			matches = matchService.getMatches();
			return matches;
		}catch(Exception e) {
			throw new ResourceNotFoundException("Please Connect with Support team");
		}
	}
	
	@GetMapping("/v1/match/{matchId}")
	public MatchesDTO getMatch(@PathVariable String matchId) throws Exception {
		return matchService.getMatch(matchId);
	}

	@RequestMapping(value = "/v1/leagues/{matchId}", produces = { "application/JSON" })
	public List<LeagueDTO> getLeagues(@PathVariable String matchId) throws ResourceNotFoundException {
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		try {
			if(matchService.getMatchLiveStatus(matchId).getIsActive().equalsIgnoreCase("Y")){
				leagues = matchService.getLeagues(matchId);
			}
			return leagues;	
		}catch(Exception e) {
			throw new ResourceNotFoundException("Please Connect with Support team");
		}
	}
	
	@RequestMapping(value = "/v1/league/{leagueId}", produces = { "application/JSON" })
	public LeagueDTO getLeague(@PathVariable String leagueId) throws ResourceNotFoundException {
		LeagueDTO league = new LeagueDTO();
		try {
			league = matchService.getLeague(leagueId);
			return league;
		} catch (Exception e) {
			throw new ResourceNotFoundException("Please Connect with Support team");
		}
	}

	@GetMapping(value = "/v1/winningBreakup/{breakupId}")
	public List<WinningBreakupDTO> getWinningBreakup(@PathVariable String breakupId) throws ResourceNotFoundException {
		List<WinningBreakupDTO> breakup = new ArrayList<WinningBreakupDTO>();
		try {
			breakup = matchService.getWinningBreakup(breakupId);
			return breakup;
		}catch(Exception e) {
			throw new ResourceNotFoundException("Please Connect with Support team");
		}
	}

	@RequestMapping(value = "/v1/squad/{matchId}", produces = { "application/JSON" })
	public MatchTeam getSquad(@PathVariable String matchId) {
		MatchTeam matchSquad = new MatchTeam();
		List<PlayerDTO> squad = new ArrayList<PlayerDTO>();
		squad = matchService.getSquad(matchId);
		matchSquad.setPlayers(squad);
		matchSquad.setMatchId(matchId);
		return matchSquad;
	}

	@RequestMapping(value = "/v1/createTeam", produces = { "application/JSON" }, method=RequestMethod.POST)
	public @ResponseBody String createTeam(@RequestBody MatchTeam matchTeam,
			@RequestParam(value = "id", required = false) String id) throws Exception {
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(id)) {
			matchTeam.setId(id);
		}
		// performing validations
		result = matchService.teamValidations(matchTeam);
		if (StringUtils.isNotEmpty(result)) {
			throw new ValidationException(result);
		}
		// check if match is not started then only insert/update
		if (StringUtils.equalsIgnoreCase("false", matchService.getMatchLiveStatus(matchTeam.getMatchId()).getMatchStarted())) {
			// insert/update team
			try {
				result = matchService.createTeam(matchTeam);
			}catch(Exception ex) {
				throw new Exception("Please connect with support team");
			}
		} else {
			result = "Match is already started.";
		}
		return result;
	}

	@GetMapping(value = "/v1/allMatchesTeams/{uniqueNumber}")
	public @ResponseBody List<MatchTeam> getAllMatchesTeams(@PathVariable String uniqueNumber) {
		List<MatchTeam> matchTeam = matchService.getCreatedTeams(uniqueNumber);
		return matchTeam;
	}

	@GetMapping(value = "/v1/teamsOfMatch/{uniqueNumber}/{matchId}")
	public @ResponseBody List<MatchTeam> teamsOfMatch(@PathVariable String uniqueNumber, @PathVariable String matchId) {
		List<MatchTeam> matchTeam = matchService.getCreatedTeamsOfMatch(uniqueNumber, matchId);
		return matchTeam;
	}

	@GetMapping(value = "/v1/teamView/{uniqueNumber}/{matchId}/{teamId}")
	public @ResponseBody MatchTeam getTeamView(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String teamId) {
		MatchTeam matchTeam = matchService.getTeam(uniqueNumber, matchId, teamId);
		return matchTeam;
	}

	@GetMapping(value = "/v1/teamEdit/{uniqueNumber}/{matchId}/{teamId}")
	public @ResponseBody MatchTeam getSquad(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String teamId) {
		List<PlayerDTO> squad = new ArrayList<PlayerDTO>();
		MatchTeam matchTeam = matchService.getTeam(uniqueNumber, matchId, teamId);
		squad = matchService.setSelectedPlayersInSquad(matchService.getSquad(matchId), matchTeam);
		matchTeam = new MatchTeam();
		matchTeam.setPlayers(squad);
		return matchTeam;
	}

	//@PostMapping(value = "/v1/joinLeague/{leagueId}")
	@RequestMapping(value = "/v1/joinLeague/{leagueId}", produces = { "application/JSON" }, method=RequestMethod.POST)
	public String joinLeague(@PathVariable String leagueId, @RequestBody MatchTeam team) throws ConflictedException {
		String message = StringUtils.EMPTY;
			if (matchService.getMatchLiveStatus(team.getMatchId()).getMatchStarted().equalsIgnoreCase("false")) {
				if (matchService.validateSmallOrGrand(leagueId, team.getMatchId(), team.getUniqueNumber()) > 0) {
					message = "Small leagues can not be joined with multiple teams, less then or equals 10 members leagues considered as small league";
					throw new ValidationException(message);
				} else {
					try {
						message = matchService.joinLeague(team, leagueId);
					} catch (Exception e) {
						throw new ConflictedException("Selected team allready Joined");
					}
				}
			}
		return message;
	}

	@GetMapping(value = "/v1/joinedLeagues/{uniqueNumber}/{matchId}")
	public List<LeagueDTO> getJoinedLeagues(@PathVariable String uniqueNumber, @PathVariable String matchId) {
		List<LeagueDTO> leagues = new ArrayList<LeagueDTO>();
		leagues = matchService.getJoinedLeagues(uniqueNumber, matchId);
		return leagues;
	}

	@GetMapping(value = "/v1/joinedMatchesAndLeagues/{uniqueNumber}")
	public List<MatchLeaguesDTO> getJoinedLeagues(@PathVariable String uniqueNumber) throws Exception {
		List<MatchLeaguesDTO> leagues = new ArrayList<MatchLeaguesDTO>();
		leagues = matchService.getJoinedMatchLeagues(uniqueNumber);
		return leagues;
	}

	@GetMapping(value = "/v1/joinedLeagueTeams/{uniqueNumber}/{matchId}/{leagueId}")
	public List<MatchTeam> getJoinedLeagueTeams(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String leagueId) {
		List<MatchTeam> matchTeam = new ArrayList<MatchTeam>();
		matchTeam = matchService.getJoinedLeagueTeams(uniqueNumber, matchId, leagueId);
		return matchTeam;
	}
	
	@GetMapping(value = "/v1/joinedLeagueAllTeams/{uniqueNumber}/{matchId}/{leagueId}")
	public List<MatchTeam> getJoinedLeagueAllTeams(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String leagueId) {
		List<MatchTeam> matchTeam = new ArrayList<MatchTeam>();
		matchTeam = matchService.getJoinedLeagueAllTeams(uniqueNumber, matchId, leagueId);
		return matchTeam;
	}
	
	//@PostMapping(value = "/v1/switchTeam/{leagueId}/{teamIdOld}")
	@RequestMapping(value = "/v1/switchTeam/{leagueId}/{teamIdOld}", produces = { "application/JSON" }, method=RequestMethod.POST)
	public String switchTeam(@RequestBody MatchTeam matchTeam, @PathVariable String leagueId, @PathVariable String teamIdOld) {
		String result = StringUtils.EMPTY;
		if(matchService.getMatchLiveStatus(matchTeam.getMatchId()).getMatchStarted().equalsIgnoreCase("false")) {
			result = matchService.switchTeam(matchTeam, leagueId, teamIdOld);
		}
		return result;
	}
	
	@GetMapping(value = "/v1/teamsRankAndPointsInLeague/{uniqueNumber}/{matchId}/{leagueId}")
	public List<TeamRankPoints> getTeamsRankAndPoints(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String leagueId) {
		try {
			List<TeamRankPoints> rankPoints = new ArrayList<TeamRankPoints>();
			rankPoints = matchService.getTeamsRankAndPoints(uniqueNumber, matchId, leagueId);
			return rankPoints;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	@GetMapping(value = "/v1/teamDetailWithPoints/{matchId}/{teamId}")
	public List<MatchTeamBean> getTeamsRankAndPoints(@PathVariable String matchId, @PathVariable String teamId) {
		try {
		List<MatchTeamBean> teamDetailWithPoints = matchService.getTeamDetailsWithPoints(matchId, teamId);
		return teamDetailWithPoints;
		}catch(Exception e) {
			return null;
		}
	}
	
	@RequestMapping(value = "/v1/addMoney", produces = { "application/JSON" }, method=RequestMethod.POST)
	public @ResponseBody AccountDTO addBalance(@RequestBody AccountDTO account) {
		AccountDTO acc = matchService.addBalance(account);
		return acc;
	}
	
	@GetMapping("/v1/account/{userName}")
	public AccountDTO account(@PathVariable String userName) throws ResourceNotFoundException {
		AccountDTO account = matchService.account(userName);
		return account;
	}
	
	@GetMapping("/v1/fantasyPoints")
	public List<PointSystemDTO> fantasyPoints() throws ResourceNotFoundException {
		List<PointSystemDTO> fantasyPointsList = matchService.getFantasyPoints();
		return fantasyPointsList;
	}
}
