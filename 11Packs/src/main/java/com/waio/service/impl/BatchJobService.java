/**
 * 
 */
package com.waio.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waio.cricapi.Batting;
import com.waio.cricapi.BattingScore;
import com.waio.cricapi.Bowling;
import com.waio.cricapi.BowlingScore;
import com.waio.cricapi.FantacySummaryApi;
import com.waio.cricapi.Fielding;
import com.waio.cricapi.FieldingScore;
import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.Team;
import com.waio.cricapi.TeamSquad;
import com.waio.dao.IBatchJobDao;
import com.waio.dao.IMatchDao;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
import com.waio.model.PlayerPointsDTO;
import com.waio.model.PlayerScoreBean;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;
import com.waio.model.WinningPriceDTO;
import com.waio.service.IBatchJobService;
import com.waio.service.ICricApiService;
import com.waio.util.AppConstants;
import com.waio.util.DataUtils;

import io.jsonwebtoken.lang.Collections;

/**
 * @author Viramm
 *
 */
@Service("BatchJobService")
public class BatchJobService implements IBatchJobService{

	private static final Logger LOGGER = Logger.getLogger( BatchJobService.class.getName());
	
	@Autowired
	private ICricApiService cricApiService;
	
	@Autowired
	private IBatchJobDao batchJobDao;
	
	@Autowired
	IMatchDao matchDao;
	
	@Override //@Autowired
	public NewMatchesData insertNewMatches() throws Exception {
		
		NewMatchesData newMatchesData = cricApiService.newMatches(AppConstants.CRIC_API_KEY1);
		if(Collections.isEmpty(newMatchesData.getMatches())) {
			System.out.println("First API_key is returning empty, trying another");
			newMatchesData = cricApiService.newMatches(AppConstants.CRIC_API_KEY2);
			if(Collections.isEmpty(newMatchesData.getMatches())) {
				System.out.println("Second API_key also returns empty");
				return null;
			}
		}
		List<MatchesDTO> matchesList = newMatchesData.getMatches();
		SimpleDateFormat istTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		TimeZone istTime = TimeZone.getTimeZone("IST");
		istTimeFormat.setTimeZone(istTime);
		//DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		
		Date today = new Date();	
		long ltime=today.getTime()+6*24*60*60*1000;
		long oneDay=today.getTime()-1*24*60*60*1000;
		Date plusThreeDays=new Date(ltime);
		Date minusOneDay=new Date(oneDay);
		
		Iterator<MatchesDTO> it = matchesList.iterator();
		while(it.hasNext()) {
			MatchesDTO matches = it.next();
			
			
			//Date matchDate = matches.getDatetime(); // format.parse(istTimeFormat.format(matches.getDatetime()));
			//matches.setDatetime(matchDate);
						
			//Date todayDate = format.parse(istTimeFormat.format(today));
			
			/*if(StringUtils.isNotEmpty(matches.getWinner_team()) || StringUtils.isNotBlank(matches.getWinner_team())) {
				matches.setMatchStatus("COMPLETED");
			}else if ((StringUtils.isEmpty(matches.getWinner_team()) || StringUtils.isBlank(matches.getWinner_team())) && matches.getDatetime().after(todayDate)) {
				matches.setMatchStatus("UPCOMING");
			}else {
				matches.setMatchStatus("LIVE");
			}*/
			
			if((matches.getDatetime().equals(minusOneDay) || matches.getDatetime().after(minusOneDay)) && matches.getDatetime().before(plusThreeDays)) {
				if(StringUtils.isEmpty(matches.getUnique_id()) || StringUtils.isEmpty(matches.getType()) || StringUtils.isEmpty(matches.getTeam1()) || StringUtils.isEmpty(matches.getTeam2()) || matches.getDatetime()==null) {
					it.remove();
				}
			}else {
				it.remove();
			}
		}
		
		if(batchJobDao.insertMatches(matchesList)>0) {
			// set leagues bean for all matches
			for(MatchesDTO matches : matchesList) {
				insertSquad(matches);
			}
			
			// insert leagues for all matches
			insertLeagues(matchesList);
			
			return newMatchesData;	
		}else {
			return null;
		}
	}

	public NewMatchesData updateMatchesStatus() throws Exception {
		
		NewMatchesData newMatchesData = cricApiService.newMatches(AppConstants.CRIC_API_KEY1);
		if(Collections.isEmpty(newMatchesData.getMatches())) {
			System.out.println("First API_key is returning empty, trying another");
			newMatchesData = cricApiService.newMatches(AppConstants.CRIC_API_KEY2);
			if(Collections.isEmpty(newMatchesData.getMatches())) {
				System.out.println("Second API_key also returns empty");
				return null;
			}
		}
		
		List<MatchesDTO> matchesList = newMatchesData.getMatches();
		
		Date today = new Date();	
		long ltime=today.getTime()+3*24*60*60*1000;
		long oneDay=today.getTime()-3*24*60*60*1000;
		Date plusThreeDays=new Date(ltime);
		Date minusOneDay=new Date(oneDay);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");  
		DateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String strToday= formatter.format(today);  
		Date todayDate = format.parse(strToday);
		
		if(Collections.isEmpty(matchesList)) {
			return newMatchesData;
		}
		
		Iterator<MatchesDTO> it = matchesList.iterator();
		while(it.hasNext()) {
		
			MatchesDTO matches = it.next();
			if(StringUtils.isNotEmpty(matches.getWinner_team()) || StringUtils.isNotBlank(matches.getWinner_team())) {
				matches.setMatchStatus("COMPLETED");
			}else if ((StringUtils.isEmpty(matches.getWinner_team()) || StringUtils.isBlank(matches.getWinner_team())) && matches.getDatetime().after(todayDate)) {
				matches.setMatchStatus("UPCOMING");
			}else {
				matches.setMatchStatus("LIVE");
			}

			if((matches.getDatetime().equals(minusOneDay) || matches.getDatetime().after(minusOneDay)) && matches.getDatetime().before(plusThreeDays)) {
				if(StringUtils.isEmpty(matches.getUnique_id()) || StringUtils.isEmpty(matches.getType()) || StringUtils.isEmpty(matches.getTeam1()) || StringUtils.isEmpty(matches.getTeam2()) || matches.getDatetime()==null) {
					it.remove();
				}
			}else {
				it.remove();
			}
		}
				
		batchJobDao.insertMatches(matchesList);
		return newMatchesData;
	}

	public int insertSquad(MatchesDTO matches) {
		try {
		TeamSquad teamSquad = cricApiService.getSquad(matches.getUnique_id(), AppConstants.CRIC_API_KEY1);
		if(teamSquad == null) {
			System.out.println("First API_key is returning empty, trying another");
			teamSquad = cricApiService.getSquad(matches.getUnique_id(), AppConstants.CRIC_API_KEY2);
			if(teamSquad == null) {
				System.out.println("Second API_key also returns empty");
				return 0;
			}
		}
		
		List<Team> squad = teamSquad.getSquad();
		if(Collections.isEmpty(squad)) {
			System.out.println("Squad not present in squad api checking in summary := "+matches.getUnique_id());
			FantacySummaryApi apiData = cricApiService.fantacySummaryApi(matches.getUnique_id(), AppConstants.CRIC_API_KEY1);
			
			// checking data with multiple API_Key
			if(null == apiData) {
				System.out.println("First API_key is returning empty, trying another");
				apiData = cricApiService.fantacySummaryApi(matches.getUnique_id(), AppConstants.CRIC_API_KEY2);
				if(null == apiData) {
					System.out.println("Second API_key also returns empty");
					return 0;
				}
			}
			squad = apiData.getData().getTeam();
			
			if(Collections.isEmpty(squad)) {
				System.out.println("Squad not present in summary api too:= "+matches.getUnique_id());
				return 0;
			}
			System.out.println("Squad found in summary API:= "+matches.getUnique_id());
		}
		
		List<PlayerDTO> playerList = insertPlayerInfo(squad, matches);
		// insert player information
		batchJobDao.insertPlayerInfo(playerList);
		// insert match squad
		int insertedRecords = batchJobDao.insertSquad(matches.getUnique_id(), playerList);
		System.out.println("Squad added for match "+matches.getUnique_id()+" added "+insertedRecords+" players");
		return insertedRecords;
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
	}
	
	public int insertLeagues(List<MatchesDTO> matchesList) {
		return batchJobDao.insertLeagues(matchesList);
	}
	
	public List<PlayerDTO> insertPlayerInfo(List<Team> squad, MatchesDTO matches) {
		List<PlayerDTO> playerList = new ArrayList<PlayerDTO>();
		for(Team team : squad) {
			for(PlayerDTO player : team.getPlayers()) {
				PlayerDTO playerDTO = cricApiService.playerInfo(player.getPid(), AppConstants.CRIC_API_KEY1);
				if(null == playerDTO) {
					System.out.println("First API_key is returning empty, trying another");
					playerDTO = cricApiService.playerInfo(player.getPid(), AppConstants.CRIC_API_KEY2);
					if(null == playerDTO) {
						System.out.println("Second API_key also returns empty");
						return null;
					}
				}

				if(playerDTO!=null) {
					playerDTO.setPlayingTeamName(DataUtils.getShortForm(team.getName()));
					if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("Bowler")){
						playerDTO.setPlayingRole("BOWL");
					} else if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("Allrounder")){
						playerDTO.setPlayingRole("ALL");
					} else if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("Wicketkeeper")){
						playerDTO.setPlayingRole("WK");
					} else if(playerDTO.getPlayingRole()!=null && playerDTO.getPlayingRole().contains("batsman")){
						playerDTO.setPlayingRole("BAT");
					} else {
						playerDTO.setPlayingRole("ALL");
					}
					if(matches.getType().equalsIgnoreCase("Twenty20")  && playerDTO.getData().getBowling().getT20Is()!=null) {
						if(playerDTO.getPlayingRole().contains("BOWL")){
							if(playerDTO.getData().getBowling().getT20Is().getAve()<20) {
								playerDTO.setCredit(9.5);
							}else if (playerDTO.getData().getBowling().getT20Is().getAve()>=20 && playerDTO.getData().getBatting().getT20Is().getAve()==24.99) {
								playerDTO.setCredit(9);
							}else if (playerDTO.getData().getBowling().getT20Is().getAve()>25 && playerDTO.getData().getBatting().getT20Is().getAve()<=29.99) {
								playerDTO.setCredit(8.5);
							}else if (playerDTO.getData().getBowling().getT20Is().getAve()>30) {
								playerDTO.setCredit(8);
							}
						} else {
							if(playerDTO.getData().getBatting().getT20Is().getAve()>50) {
								playerDTO.setCredit(10);
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>35 && playerDTO.getData().getBatting().getT20Is().getAve()<=49.99) {
								playerDTO.setCredit(9.5);
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>25 && playerDTO.getData().getBatting().getT20Is().getAve()<=34.99) {
								playerDTO.setCredit(9);
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>15 && playerDTO.getData().getBatting().getT20Is().getAve()<=24.99) {
								playerDTO.setCredit(8.5);
							}else if (playerDTO.getData().getBatting().getT20Is().getAve()>0 && playerDTO.getData().getBatting().getT20Is().getAve()<=14.99) {
								playerDTO.setCredit(8);
							}						
						}
					}else {
						playerDTO.setCredit(9);
					}
				}
				playerList.add(playerDTO);
			}
		}
		
	return playerList;
	}

	@Override
	public String createLeague(LeagueDTO leagueDTO) {
		return batchJobDao.createLeague(leagueDTO);
	}
	
	@Override //@Autowired
	public String fantasySummaryApi(String matchId) {

		FantacySummaryApi apiData = cricApiService.fantacySummaryApi(matchId, AppConstants.CRIC_API_KEY1);
		if(null == apiData.getData()) {
			System.out.println("First API_key is returning empty, trying another");
			apiData = cricApiService.fantacySummaryApi(matchId, AppConstants.CRIC_API_KEY2);
			if(null == apiData) {
				System.out.println("Second API_key also returns empty");
				return null;
			}
		}
		
		// update playing 11 if declared
		List<PlayerDTO> playerList = checkUpdatePlaying11(apiData, matchId);
		
		List<PlayerScoreBean> playerScoreBattingList = new ArrayList<>();
		List<PlayerScoreBean> playerScoreBowlingList = new ArrayList<>();
		List<PlayerScoreBean> playerScoreFieldingList = new ArrayList<>();
		
		PlayerScoreBean playerScoreBattingBean = null;
		PlayerScoreBean playerScoreBowlingBean = null;
		PlayerScoreBean playerScoreFieldingBean = null;
		
		if(apiData.getData() == null) {
			LOGGER.info("team api data not available in summary api");
			return "summary data not present";
		}
		
		for (Batting bating : apiData.getData().getBatting()) {
			for(BattingScore bs : bating.getScores()) {
				playerScoreBattingBean = new PlayerScoreBean();
				playerScoreBattingBean.setPlayerId(bs.getPid());
				playerScoreBattingBean.setMatchId(matchId);
				playerScoreBattingBean.setRun(bs.getRun());
				playerScoreBattingBean.setBall(bs.getBall());
				playerScoreBattingBean.setSixes(bs.getSixes());
				playerScoreBattingBean.setFours(bs.getFours());
				playerScoreBattingBean.setsRate(bs.getsRate());
				playerScoreBattingBean.setDismissal(bs.getDismissal());
				playerScoreBattingList.add(playerScoreBattingBean);
			}
		}
		for (Bowling bowling : apiData.getData().getBowling()) {
			for(BowlingScore bs : bowling.getScores()) {
				playerScoreBowlingBean = new PlayerScoreBean();
				playerScoreBowlingBean.setPlayerId(bs.getPid());
				playerScoreBowlingBean.setMatchId(matchId);
				playerScoreBowlingBean.setOvers(bs.getOver());
				playerScoreBowlingBean.setWicket(bs.getWicket());
				playerScoreBowlingBean.setMaiden(bs.getMaiden());
				playerScoreBowlingBean.setEconomy(bs.getEconomy());
				playerScoreBowlingBean.setRunsgiven(bs.getRun());
				playerScoreBowlingList.add(playerScoreBowlingBean);
			}
		}
		for (Fielding fielding : apiData.getData().getFielding()) {
			for(FieldingScore fs : fielding.getScores()) {
				playerScoreFieldingBean = new PlayerScoreBean();
				playerScoreFieldingBean.setPlayerId(fs.getPid());
				playerScoreFieldingBean.setMatchId(matchId);
				playerScoreFieldingBean.setCatches(fs.getCatches());
				playerScoreFieldingBean.setRunOut(fs.getRunout());
				playerScoreFieldingBean.setStumped(fs.getStumped());
				playerScoreFieldingList.add(playerScoreFieldingBean);
			}
		}
		
		// saving of three list
		batchJobDao.saveBattingScore(playerScoreBattingList);
		batchJobDao.saveBowlingScore(playerScoreBowlingList);
		batchJobDao.saveFieldingScore(playerScoreFieldingList);
		
		// calculate points for players
		updateScoreAndCreatePoints(playerList, matchId);
	
		return "Job executed successfully";
	}
	
	public List<PlayerDTO> checkUpdatePlaying11(FantacySummaryApi apiData, String matchId) {
		List<PlayerDTO> playerList = new ArrayList<>();
		if(apiData != null && apiData.getData() != null && apiData.getData().getTeam() != null) {
			for (Team team : apiData.getData().getTeam()) {
				playerList.addAll(team.getPlayers());
			}
			if(playerList.size() <= 22) {
				batchJobDao.playing11Declared(matchId, playerList);
			}			
		}else {
			LOGGER.info("team api data not available in summary api");
		}
		return playerList;
	}
	
	@Override
	public int updateScoreAndCreatePoints(List<PlayerDTO> playerList, String matchId) {
		List<PlayerPointsDTO> playerPointList = calculatePlayerPoints(playerList, matchId);
		// insert points
		int insertedPoints = batchJobDao.insertPlayerPoints(playerPointList, matchId);
		return insertedPoints;
	}
	
	public List<PlayerPointsDTO> calculatePlayerPoints(List<PlayerDTO> playerList, String matchId) {
		try {
		List<PlayerScoreBean> playerScoreList = batchJobDao.getPlayersScore(matchId);
		Map<String, Object> configuredPoints = batchJobDao.getConfiguredPoints("T20");
		List<PlayerPointsDTO> playerPointsList = new ArrayList<>();
		
		// check if playing but not yet scored
		Collection<String> list22 = CollectionUtils.collect(playerList, new Transformer<PlayerDTO, String>(){
			@Override
			public String transform(PlayerDTO input) {
				return input.getPid();
			}
		});
		
		for(PlayerScoreBean playerScore : playerScoreList) {
			Double totalPoints = 0.0;
			PlayerPointsDTO playerPointsDTO = new PlayerPointsDTO();
			playerPointsDTO.setPlayerId(playerScore.getPlayerId());
			playerPointsDTO.setMatchId(playerScore.getMatchId());
			
			// In 11
			if(list22.contains(playerScore.getPlayerId())) {
				if(configuredPoints.containsKey("INELEVEN")) {
					totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("INELEVEN"));
				}
			}
			
			// BATTING Points
			if(configuredPoints.containsKey("RUN")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("RUN"))*DataUtils.valueInDouble(playerScore.getRun());
			}
			if(configuredPoints.containsKey("SIX")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("SIX"))*DataUtils.valueInDouble(playerScore.getSixes());
			}
			if(configuredPoints.containsKey("FOUR")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("FOUR"))*DataUtils.valueInDouble(playerScore.getFours());
			}
			if(DataUtils.valueInDouble(playerScore.getRun())>=50) {
				if(configuredPoints.containsKey("HALFCENTURY")) {
					totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("HALFCENTURY"));
				}
			}
			
			if(DataUtils.valueInDouble(playerScore.getRun())>=100) {
				if(configuredPoints.containsKey("CENTURY")) {
					totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("CENTURY"));
				}
			}
			
			if(StringUtils.isNotEmpty(playerScore.getDismissal()) && !StringUtils.equalsIgnoreCase(playerScore.getDismissal(), "not out") && DataUtils.valueInDouble(playerScore.getRun()) == 0) {
				if(configuredPoints.containsKey("DUCK")) {
					totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("DUCK"));
				}
			}
			
			// BOWLING points
			if(configuredPoints.containsKey("WICKET")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("WICKET"))*DataUtils.valueInDouble(playerScore.getWicket());
			}
			if(DataUtils.valueInDouble(playerScore.getWicket()) == 4) {
				if(configuredPoints.containsKey("FOURWICKETHAUL")) {
					totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("FOURWICKETHAUL"));
				}
			}
			if(DataUtils.valueInDouble(playerScore.getWicket()) >= 5) {
				if(configuredPoints.containsKey("FIVEWICKETHAUL")) {
					totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("FIVEWICKETHAUL"));
				}
			}
			if(configuredPoints.containsKey("MAIDEN")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("MAIDEN"))*DataUtils.valueInDouble(playerScore.getMaiden());
			}
			
			// FIELDING Points
			if(configuredPoints.containsKey("CATCH")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("CATCH"))*DataUtils.valueInDouble(playerScore.getCatches());
			}
			if(configuredPoints.containsKey("RUNOUT")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("RUNOUT"))*DataUtils.valueInDouble(playerScore.getRunOut());
			}
			if(configuredPoints.containsKey("STUMP")) {
				totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("STUMP"))*DataUtils.valueInDouble(playerScore.getStumped());
			}
			
			// STRIKE RATE Points will be calculated if runs more then or equals 10
			if(DataUtils.valueInDouble(playerScore.getRun()) >= 10) {
				if(DataUtils.valueInDouble(playerScore.getsRate()) >=70 && DataUtils.valueInDouble(playerScore.getsRate()) < 90) {
					if(configuredPoints.containsKey("SEVENTYTONINTY")) {
						totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("SEVENTYTONINTY"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getsRate()) >=50 && DataUtils.valueInDouble(playerScore.getsRate()) < 70) {
					if(configuredPoints.containsKey("FIFTYTOSEVENTY")) {
						totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("FIFTYTOSEVENTY"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getsRate()) < 50) {
					if(configuredPoints.containsKey("BELOWFIFTY")) {
						totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("BELOWFIFTY"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getsRate()) >=120 && DataUtils.valueInDouble(playerScore.getsRate()) < 151) {
					if(configuredPoints.containsKey("ONETWENTYTOFIFTY")) {
						totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("ONETWENTYTOFIFTY"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getsRate()) >= 151) {
					if(configuredPoints.containsKey("ABOVEONEFIFTY")) {
						totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("ABOVEONEFIFTY"));
					}
				}
			}
			
			// ECONOMY RATE Points
			if(DataUtils.valueInDouble(playerScore.getOvers()) >= 1) {
				if(DataUtils.valueInDouble(playerScore.getEconomy()) < 4) {
					if(configuredPoints.containsKey("BELOWFOURRUN")) {
						totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("BELOWFOURRUN"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getEconomy()) >= 4 && DataUtils.valueInDouble(playerScore.getEconomy()) < 5) {
					if(configuredPoints.containsKey("FOURTOFIVE")) {
						totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("FOURTOFIVE"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getEconomy()) >= 5 && DataUtils.valueInDouble(playerScore.getEconomy()) <= 6) {
					if(configuredPoints.containsKey("FIVETOSIX")) {
						totalPoints = totalPoints + DataUtils.valueInDoubleFromObj(configuredPoints.get("FIVETOSIX"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getEconomy()) >= 9 && DataUtils.valueInDouble(playerScore.getEconomy()) <= 10) {
					if(configuredPoints.containsKey("NINETOTEN")) {
						totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("NINETOTEN"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getEconomy()) > 10 && DataUtils.valueInDouble(playerScore.getEconomy()) <= 11) {
					if(configuredPoints.containsKey("TENTOELEVEN")) {
						totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("TENTOELEVEN"));
					}
				}
				if(DataUtils.valueInDouble(playerScore.getEconomy()) > 11) {
					if(configuredPoints.containsKey("ABOVEELEVEN")) {
						totalPoints = totalPoints - DataUtils.valueInDoubleFromObj(configuredPoints.get("ABOVEELEVEN"));
					}
				}
			}
			playerPointsDTO.setPoints(totalPoints);
			playerPointsList.add(playerPointsDTO);
		}
		return playerPointsList;
		}catch(Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	@Override
	public void declareWinner(String matchId) {

		/*
		 * Collection<String> matchIds = CollectionUtils.collect(matchesList, new
		 * Transformer<MatchesDTO, String>() {
		 * 
		 * @Override public String transform(MatchesDTO object) { return
		 * object.getUnique_id(); } });
		 */

		// String matchesIDs = StringUtils.join(matchIds, ",");
		List<LeagueDTO> leagues = batchJobDao.getEligibleLeaguesOfMatch(matchId);

		List<WinningPriceDTO> wpd = new ArrayList<>();
		WinningPriceDTO wpdDTO = null;

		for (LeagueDTO league : leagues) {
			List<WinningBreakupDTO> wbList = matchDao.getWinningBreakupByLeagueId(league.getId());

			Map<String, List<TeamRankPoints>> map = new HashMap<>();
			List<TeamRankPoints> trpList = null;

			Map<Integer, BigDecimal> breakUpMap = new HashMap<>();
			for (WinningBreakupDTO wb : wbList) {
				String rank = wb.getPrizeRank();
				String[] rankArray = rank.split("-");
				if (rankArray.length == 1) {
					int firstValue = Integer.parseInt(rankArray[0]);
					breakUpMap.put(firstValue, wb.getPrizeMoney());
				}
				if (rankArray.length == 2) {
					int firstValue = Integer.parseInt(rankArray[0]);
					int secondValue = Integer.parseInt(rankArray[1]);
					for (int i = firstValue; i <= secondValue; i++) {
						breakUpMap.put(i, wb.getPrizeMoney());
					}
				}
			}
			
			if (CollectionUtils.isNotEmpty(wbList)) {
				List<TeamRankPoints> teamRank = matchDao.getTeamsRankAndPoints(league.getMatchId(), league.getId());

				for (Map.Entry<Integer, BigDecimal> wb : breakUpMap.entrySet()) {
					trpList = new ArrayList<>();
					for (TeamRankPoints trp : teamRank) {
						if (wb.getKey() == trp.getTeamRank()) {
							trpList.add(trp);
						} else {

						}
					}
					if (CollectionUtils.isNotEmpty(trpList)) {
						map.put(String.valueOf(wb.getKey()), trpList);
					}
				}
			}

			for (Map.Entry<String, List<TeamRankPoints>> entry : map.entrySet()) {
				int size = entry.getValue().size();

				int whatIsRank = Integer.parseInt(entry.getKey());

				BigDecimal totalAmount = new BigDecimal(0);
				for (TeamRankPoints trp : entry.getValue()) {
					if(breakUpMap.containsKey(whatIsRank)) {
						totalAmount = totalAmount.add(breakUpMap.get(whatIsRank));
						whatIsRank++;
					}
				}
				for (TeamRankPoints trp : entry.getValue()) {
					wpdDTO = new WinningPriceDTO();
					wpdDTO.setTeamRank(entry.getKey());
					wpdDTO.setTeamId(trp.getTeamId());
					wpdDTO.setLeagueId(league.getId());
					wpdDTO.setMatchId(league.getMatchId());
					wpdDTO.setUserName(trp.getCreatedId());
					BigDecimal bd = new BigDecimal(size);
					wpdDTO.setWinningAmount(totalAmount.divide(bd, MathContext.DECIMAL128));
					wpd.add(wpdDTO);
				}
			}
		}
		// save winnings
		System.out.println("inserting winning for match: " + matchId);
		try {
			int insertedRecords = batchJobDao.saveWinning(wpd);
			System.out.println("inserted " + insertedRecords + " records for match in winning: " + matchId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public int distributeWinnersWinning(String matchId) {
		int insertedRecords = batchJobDao.distributeWinnersWinningInWallet(matchId);
		if (insertedRecords > 0) {
			System.out.println("inserted " + insertedRecords + " records for match in winning: " + matchId);
		}
		return insertedRecords;
	}
}
