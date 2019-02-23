package com.waio.service.impl;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.AccountDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesBean;
import com.waio.model.MatchLeaguesDTO;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;
import com.waio.service.IMatchService;
import com.waio.util.DataUtils;

@Service("MatchService")
public class MatchService implements IMatchService{

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	IMatchDao matchDao;
	
	@Override
	@Cacheable(value = "matches")
	public List<MatchesDTO> getMatches() throws Exception {
		List<MatchesDTO> matchesList = matchDao.getMatches();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
		Date today = new Date();
		SimpleDateFormat formatterrr = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		Iterator<MatchesDTO> it = matchesList.iterator();
		while(it.hasNext()) {
			MatchesDTO matches = it.next();
			String strrDate= formatterrr.format(matches.getDatetime());  
			Date matchDate = format.parse(strrDate);
			String strToday= formatterrr.format(today);  
			Date todayDate = format.parse(strToday);
			
			if(matchDate.before(todayDate)) {
				it.remove();
			}else {
				matches.setTeam1Short(DataUtils.getShortForm( matches.getTeam1()));
				matches.setTeam2Short(DataUtils.getShortForm(matches.getTeam2()));
				matches.setFormattedTeamName(matches.getTeam1Short()+"  vs  "+matches.getTeam2Short());
				matches.setTeam1Name(matches.getTeam1());
				matches.setTeam2Name(matches.getTeam2());
				DataUtils.getMatchTypeShort(matches);  
			    String strDate= formatter.format(matches.getDatetime());  
			    matches.setDateShow(strDate);
			}
		}
		LOG.info("Getting matches list.");
		return matchesList;
	}

	@Override
	public MatchesDTO getMatch(String matchId) {
		MatchesDTO matches = matchDao.getMatch(matchId);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		matches.setTeam1Short(DataUtils.getShortForm(matches.getTeam1()));
		matches.setTeam2Short(DataUtils.getShortForm(matches.getTeam2()));
		matches.setFormattedTeamName(matches.getTeam1Short() + "  vs  " + matches.getTeam2Short());
		matches.setTeam1Name(matches.getTeam1());
		matches.setTeam2Name(matches.getTeam2());
		DataUtils.getMatchTypeShort(matches);
		String strDate = formatter.format(matches.getDatetime());
		matches.setDateShow(strDate);
		LOG.info("Getting matches list.");
		return matches;
	}
	
	@Override
	public List<LeagueDTO> getLeagues(String matchId) {
		return matchDao.getLeagues(matchId);
	}
	@Override
	@Cacheable(value="Squad", key = "#matchId")
	public List<PlayerDTO> getSquad(String matchId) {
		return matchDao.getSquad(matchId);
	}

	@Override
	public String createTeam(MatchTeam team) {
		String result = StringUtils.EMPTY;
		if (StringUtils.isNotEmpty(team.getId())) {
			// update existing team
			// 1. delete team first from team_player
			matchDao.deleteTeam(team.getId());
			// 2. insert team again with new players
			int insertedPlayers = matchDao.createTeam(team);
			if (insertedPlayers > 0) {
				result = team.getId();
			}
		} else {
			// create new team
			// 1. insert team info
			BigInteger teamId = matchDao.insertTeam(team);
			// 2. set team id
			team.setId(String.valueOf(teamId));
			// 3. insert new team
			int insertedPlayers = matchDao.createTeam(team);
			if (insertedPlayers > 0) {
				result = team.getId();
			}
		}
		return result;
	}
	
	/**
	 * @param team
	 * @return validation message
	 */
	@Override
	public String teamValidations(MatchTeam team) {
		List<PlayerDTO> listBat = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listBowl = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listAll = new ArrayList<PlayerDTO>();
		List<PlayerDTO> listWk = new ArrayList<PlayerDTO>();
		
		List<PlayerDTO> team1 = new ArrayList<PlayerDTO>();
		List<PlayerDTO> team2 = new ArrayList<PlayerDTO>();
		
		String str = "";
		String teamName = StringUtils.EMPTY;
		double totalCredits = 0;
		for(PlayerDTO player : team.getPlayers()) {
			if("BAT".equalsIgnoreCase(player.getPlayingRole())) {
				listBat.add(player);
			} else if("BOWL".equalsIgnoreCase(player.getPlayingRole())) {
				listBowl.add(player);
			} else if("ALL".equalsIgnoreCase(player.getPlayingRole())) {
				listAll.add(player);
			}else if("WK".equalsIgnoreCase(player.getPlayingRole())) {
				listWk.add(player);
			}
			
			// create separate team list to check each team size
			if(StringUtils.isEmpty(teamName)) {
				team1.add(player);
				teamName = player.getPlayingTeamName();
			}else if(player.getPlayingTeamName().equalsIgnoreCase(teamName)) {
				team1.add(player);
			} else {
				team2.add(player);
			}
			
			// add credit
			totalCredits = totalCredits + player.getCredit();
		}
		
		// check each team size validations
		if(team1.size() > 7 || team2.size() > 7) {
			str = "Maximum 7 players allowed from one team";
		}
		
		// validate credits should not go beyond 100
		if(totalCredits >100) {
			str = "You are crossing maximum salary limit of 100 Cr";
		}
		
		if(listBat.size()<3 || listBat.size()>5) {
			str = "3 to 5 batsman are compulsory";
		} else if(listBowl.size()<3 || listBowl.size()>5) {
			str = "3 to 5 bowlers are compulsory";
		} else if(listAll.size()<1 || listAll.size()>3) {
			str = "1 to 3 allrounders are compulsory";
		} else if(listWk.size()<1 || listWk.size()>1) {
			str = "Maximum or Minimun 1 Wicktkeeper allowed.";
		} else if(!((listBat.size()+listBowl.size()+listAll.size()+listWk.size()) == 11)) {
			str = "There should be 11 players in the team.";
		}
		return str;
	}

	@Override
	public List<MatchTeam> getCreatedTeams(String uniqueNumber) {
		return matchDao.getCreatedTeams(uniqueNumber);
	}

	@Override
	public List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId) {
		return matchDao.getCreatedTeamsOfMatch(uniqueNumber, matchId);
	}
	
	@Override
	public MatchTeam getTeam(String uniqueNumber, String matchId, String teamId) {
		List<MatchTeamBean> team = matchDao.getTeam(uniqueNumber, matchId, teamId);
		return setValuesInMatchTeam(team, uniqueNumber, matchId, teamId);
	}
	
	public MatchTeam setValuesInMatchTeam(List<MatchTeamBean> team, String uniqueNumber, String matchId, String teamId){	
		MatchTeam matchTeam = new MatchTeam();
		List<PlayerDTO> players = new ArrayList<PlayerDTO>();
		for(MatchTeamBean matchBean : team) {
			PlayerDTO playerDTO = new PlayerDTO();
			playerDTO.setPid(matchBean.getPid());
			playerDTO.setName(matchBean.getName());
			playerDTO.setPlayingRole(matchBean.getPlayingRole());
			playerDTO.setImageURL(matchBean.getImageURL());
			playerDTO.setCountry(matchBean.getCountry());
			playerDTO.setCaptain(Boolean.parseBoolean(matchBean.getCaptain()));
			playerDTO.setViceCaptain(Boolean.parseBoolean(matchBean.getViceCaptain()));
			playerDTO.setPlayingTeamName(matchBean.getPlayingTeamName());
			players.add(playerDTO);
		}
		matchTeam.setId(teamId);
		if(team.size()>0) {
			matchTeam.setTeamName(team.get(0).getTeamName());
		}
		matchTeam.setMatchId(matchId);
		matchTeam.setUniqueNumber(uniqueNumber);
		matchTeam.setPlayers(players);
		return matchTeam;
	}

	@Override
	public List<PlayerDTO> setSelectedPlayersInSquad(List<PlayerDTO> squad, MatchTeam matchTeam) {
		Collection<String> playerIds = CollectionUtils.collect(matchTeam.getPlayers(), new Transformer<PlayerDTO, String>(){
			@Override
			public String transform(PlayerDTO object) {
				return object.getPid();
			}
		});
		for(PlayerDTO squa : squad) {
			if(playerIds.contains(squa.getPid())) {
				squa.setSelected(true);
			}
		}
		return squad;
	}

	@Override
	public String joinLeague(MatchTeam team, String leagueId) {
		return matchDao.joinLeague(team, leagueId);
	}

	@Override
	public List<WinningBreakupDTO> getWinningBreakup(String breakupId) {
		return matchDao.getWinningBreakup(breakupId);
	}

	@Override
	public MatchesDTO getMatchLiveStatus(String matchId) {
		return matchDao.getMatchLiveStatus(matchId);
	}

	@Override
	public List<LeagueDTO> getJoinedLeagues(String uniqueNumber, String matchId) {
		return matchDao.getJoinedLeagues(uniqueNumber, matchId);
	}

	@Override
	public List<MatchLeaguesDTO> getJoinedMatchLeagues(String uniqueNumber) throws Exception {
		return setMatchesLeagues(matchDao.getJoinedMatchLeagues(uniqueNumber));
	}

	@Override
	public List<MatchTeam> getJoinedLeagueTeams(String uniqueNumber, String matchId, String leagueId) {
		return matchDao.getJoinedLeagueTeams(uniqueNumber, matchId, leagueId);
	}

	@Override
	public String switchTeam(MatchTeam matchTeam, String leagueId, String teamIdOld) {
		String result = StringUtils.EMPTY;
		if(matchDao.switchTeam(matchTeam, leagueId, teamIdOld) > 0) {
			result = "Team swithed successfully.";
		} else {
			result = "Team Not switched.";
		}
		return result;
	}
	
	public List<MatchLeaguesDTO> setMatchesLeagues(List<MatchLeaguesBean> list) throws Exception{
		List<MatchLeaguesDTO> matchLeaguesList = new ArrayList<MatchLeaguesDTO>();
		MatchLeaguesDTO matchLeagueObject = null;
		Date today = new Date();
		List<LeagueDTO> leagueList = null;
		String matchId = StringUtils.EMPTY;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		for(MatchLeaguesBean matchLeague : list) {
			if(!matchLeague.getUniqueId().equalsIgnoreCase(matchId)) {
				matchId = matchLeague.getUniqueId();
				
				matchLeagueObject = new MatchLeaguesDTO();
				leagueList = new ArrayList<LeagueDTO>();
				
				String strDate= formatter.format(matchLeague.getDatetime());  
				Date matchDate = format.parse(strDate);
				
				String strToday= formatter.format(today);  
				Date todayDate = format.parse(strToday);
				
				if(matchDate.after(todayDate)) {
					matchLeagueObject.setMatchStatus("UPCOMING");
				}else if(matchDate.before(todayDate) && StringUtils.isNotEmpty(matchLeague.getWinner_team())) {
					matchLeagueObject.setMatchStatus("COMPLETED");
				}else if(matchDate.before(todayDate) && (matchLeague.getMatchStarted().equalsIgnoreCase("true") || StringUtils.isEmpty(matchLeague.getWinner_team()) || matchLeague.getMatchStatus().equalsIgnoreCase("LIVE"))) {
					matchLeagueObject.setMatchStatus("LIVE");
				}
				
				/*if(matchDate.after(todayDate)) {
					matchLeagueObject.setMatchStatus("UPCOMING");
				}else if(matchDate.before(todayDate) && (matchLeague.getMatchStarted().equalsIgnoreCase("true") || StringUtils.isEmpty(matchLeague.getWinner_team()) || matchLeague.getMatchStatus().equalsIgnoreCase("LIVE"))) {
					matchLeagueObject.setMatchStatus("LIVE");
				}else if(matchDate.before(todayDate) && StringUtils.isNotEmpty(matchLeague.getWinner_team())) {
					matchLeagueObject.setMatchStatus("COMPLETED");
				}*/
				
				matchLeagueObject.setMatch(setMatchObject(matchLeague));
				leagueList.add(setLeagueObject(matchLeague));
				matchLeagueObject.setLeagues(leagueList);
				matchLeaguesList.add(matchLeagueObject);
			} else {
				leagueList.add(setLeagueObject(matchLeague));
				matchLeagueObject.setLeagues(leagueList);
			}
		}
		return matchLeaguesList;
	}

	/**
	 * @param matchLeague
	 */
	private LeagueDTO setLeagueObject(MatchLeaguesBean matchLeague) {
		LeagueDTO leagueDTO = new LeagueDTO();
		leagueDTO.setId(matchLeague.getId());
		leagueDTO.setLeague(matchLeague.getLeague());
		leagueDTO.setSize(matchLeague.getSize());
		leagueDTO.setEntryFee(matchLeague.getEntryFee());
		leagueDTO.setBreakupId(matchLeague.getBreakupId());
		leagueDTO.setJoinedTeam(matchLeague.getJoinedTeam());
		leagueDTO.setStatus(matchLeague.getStatus());
		leagueDTO.setWinningAmount(matchLeague.getWinningAmount());
		leagueDTO.setWinners(matchLeague.getWinners());
		return leagueDTO;
	}

	/**
	 * @param matchLeague
	 */
	private MatchesDTO setMatchObject(MatchLeaguesBean matchLeague) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");  
		MatchesDTO matcheDTO = new MatchesDTO();
		matcheDTO.setUnique_id(matchLeague.getUniqueId());
		matcheDTO.setTeam1(matchLeague.getTeam1());
		matcheDTO.setTeam2(matchLeague.getTeam2());
		matcheDTO.setType(matchLeague.getType());
		matcheDTO.setDatetime(matchLeague.getDatetime());
		matcheDTO.setTime(matchLeague.getTime());
		matcheDTO.setMatchStarted(matchLeague.getMatchStarted());
		matcheDTO.setIsActive(matchLeague.getIsActive());
		matcheDTO.setFormattedTeamName(DataUtils.getShortForm(matcheDTO.getTeam1())+"  vs  "+DataUtils.getShortForm(matcheDTO.getTeam2()));
		matcheDTO.setTeam1Name(matcheDTO.getTeam1());
		matcheDTO.setTeam2Name(matcheDTO.getTeam2());
		String strDate= formatter.format(matcheDTO.getDatetime());  
		matcheDTO.setDateShow(strDate);
		DataUtils.getMatchTypeShort(matcheDTO);
		return matcheDTO;
	}

	@Override
	public List<MatchTeam> getJoinedLeagueAllTeams(String matchId, String leagueId) {
		return matchDao.getJoinedLeagueAllTeams(matchId, leagueId);
	}

	@Override
	public List<TeamRankPoints> getTeamsRankAndPoints(String uniqueNumber, String matchId, String leagueId) {
		return matchDao.getTeamsRankAndPoints(uniqueNumber, matchId, leagueId);
	}

	@Override
	public List<MatchTeamBean> getTeamDetailsWithPoints(String teamId) {
		List<MatchTeamBean> teamDetailWithPoints = matchDao.getTeamDetailsWithPoints(teamId);
		return teamDetailWithPoints;
	}

	@Override
	public int validateSmallOrGrand(String leagueId, String matchId, String createdId) {
		return matchDao.validateSmallOrGrand(leagueId, matchId, createdId);
	}

	@Override
	public AccountDTO account(String userName) {
		return matchDao.account(userName);
	}

	@Override
	public AccountDTO addBalance(AccountDTO account) {
		account.setStatus("Credit");
		return matchDao.addBalance(account);
	}

	@Override
	public MatchTeam getTeam(String teamId) {
		return matchDao.getTeam(teamId);
	}

	@Override
	public LeagueDTO getLeague(String leagueId) {
		return matchDao.getLeague(leagueId);
	}


}
