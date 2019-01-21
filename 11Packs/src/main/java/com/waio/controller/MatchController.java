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
import com.waio.exceptions.ResourceNotFoundException;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;
import com.waio.service.IMatchService;

@CrossOrigin(origins = "http://localhost:8100", maxAge = 3600)
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
		try {
			matches = matchService.getMatches();
			return matches;
		}catch(Exception e) {
			throw new ResourceNotFoundException("Please Connect with Support team");
		}
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

	@PostMapping(value = "/v1/createTeam")
	public String createTeam(@RequestBody MatchTeam matchTeam,
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

	@PostMapping(value = "/v1/joinLeague")
	public @ResponseBody String joinLeague(@RequestBody JoinLeague joinLeague) {
		String message = StringUtils.EMPTY;
		if(matchService.getMatchLiveStatus(joinLeague.getTeam().getMatchId()).getMatchStarted().equalsIgnoreCase("false")) {
			message = matchService.joinLeague(joinLeague);
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
	public List<MatchLeaguesDTO> getJoinedLeagues(@PathVariable String uniqueNumber) {
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
	
	@GetMapping(value = "/v1/joinedLeagueAllTeams/{matchId}/{leagueId}")
	public List<MatchTeam> getJoinedLeagueAllTeams(@PathVariable String matchId,
			@PathVariable String leagueId) {
		List<MatchTeam> matchTeam = new ArrayList<MatchTeam>();
		matchTeam = matchService.getJoinedLeagueAllTeams(matchId, leagueId);
		return matchTeam;
	}
	
	@PostMapping(value = "/v1/switchTeam/{leagueId}")
	public String switchTeam(@RequestBody MatchTeam matchTeam, @PathVariable String leagueId) {
		String result = StringUtils.EMPTY;
		if(matchService.getMatchLiveStatus(matchTeam.getMatchId()).getMatchStarted().equalsIgnoreCase("false")) {
			result = matchService.switchTeam(matchTeam, leagueId);
		}
		return result;
	}
	
	@GetMapping(value = "/v1/teamsRankAndPointsInLeague/{uniqueNumber}/{matchId}/{leagueId}")
	public List<TeamRankPoints> getTeamsRankAndPoints(@PathVariable String uniqueNumber, @PathVariable String matchId,
			@PathVariable String leagueId) {
		List<TeamRankPoints> rankPoints = new ArrayList<TeamRankPoints>();
		rankPoints = matchService.getTeamsRankAndPoints(uniqueNumber, matchId, leagueId);
		return rankPoints;
	}
	
	@GetMapping(value = "/v1/teamDetailWithPoints/{teamId}")
	public List<MatchTeamBean> getTeamsRankAndPoints(@PathVariable String teamId) {
		List<MatchTeamBean> teamDetailWithPoints = matchService.getTeamDetailsWithPoints(teamId);
		return teamDetailWithPoints;
	}
}
