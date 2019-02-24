package com.waio.dao.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IMatchDao;
import com.waio.model.AccountDTO;
import com.waio.model.JoinLeague;
import com.waio.model.LeagueDTO;
import com.waio.model.MatchLeaguesBean;
import com.waio.model.MatchTeam;
import com.waio.model.MatchTeamBean;
import com.waio.model.PlayerDTO;
import com.waio.model.TeamRankPoints;
import com.waio.model.WinningBreakupDTO;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

@Repository("MatchDao")
public class MatchDao extends AbstractDaoSupport implements IMatchDao {
	
	/*@Override
	@Resource(name="matchDataSqlMap")
	public void setSqlMap(Map<String, String> sqlMap) {
		super.setSqlMap(sqlMap);
	}*/

	@Override
	public List<MatchesDTO> getMatches() {
		String sql = "SELECT unique_id, DATE_FORMAT(datetime, '%Y-%m-%d') date, DATE_FORMAT(datetime,'%H:%i:%s') time, team1, datetime, team2, type, squad, toss_winner_team, winner_team, matchStarted, is_active FROM MATCHES where datetime>current_date() and date<=(current_date()+2) and matchStarted='false' order by datetime asc";
		// AND MATCH_START_TIME>=CURRENT_TIME
		List<MatchesDTO> matches = getJdbcTemplate().query(sql, new Object[] {},
				new BeanPropertyRowMapper<MatchesDTO>(MatchesDTO.class));
		return matches;
	}
	@Override
	public MatchesDTO getMatch(String matchId) {
		String sql = "SELECT unique_id, DATE_FORMAT(datetime, '%Y-%m-%d') date, DATE_FORMAT(datetime,'%H:%i:%s') time, team1, datetime, team2, type, squad, toss_winner_team, winner_team, matchStarted, is_active FROM MATCHES where unique_id=?";
		// AND MATCH_START_TIME>=CURRENT_TIME
		MatchesDTO match = getJdbcTemplate().queryForObject(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<MatchesDTO>(MatchesDTO.class));
		return match;
	}
	
	@Override
	public List<LeagueDTO> getLeagues(String matchId) {
		String sql = "select ml.id, ml.league_id, league.league, league.size, league.entry_fee, league.winning_amount, league.winners, ml.match_id, ml.league_id breakupId, ml.joined_team from league, match_leagues ml where league.id = ml.league_id and ml.match_id=? and ml.status is null";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		return leagues;
	}
	
	@Override
	public LeagueDTO getLeague(String leagueId) {
		LeagueDTO leagueDTO = new LeagueDTO();
		String sql = "select ml.id, ml.league_id, league.league, league.size, league.entry_fee, league.winning_amount, league.winners, ml.match_id, ml.league_id breakupId, ml.joined_team from league, match_leagues ml where league.id = ml.league_id and ml.id=? and ml.status is null";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { leagueId },
				new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		if(leagues.size()>0) {
			leagueDTO = leagues.get(0);
		}
		return leagueDTO;
	}
	
	@Override
	public List<LeagueDTO> getJoinedLeagues(String uniqueNumber, String matchId) {
		String sql = "select distinct ml.id, league.league, league.size, league.entry_fee, league.winning_amount, league.winners, ml.joined_team, ml.status  from league_teams lt, match_leagues ml, league where lt.league_id = ml.id and ml.league_id= league.id and lt.match_id = ml.match_id and lt.created_id = ? and lt.match_id=?";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { uniqueNumber, matchId },
				new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		return leagues;
	}
	
	@Override
	public List<MatchLeaguesBean> getJoinedMatchLeagues(String uniqueNumber) {
		String sql = "select matches.unique_id, matches.team1, matches.team2, matches.datetime, DATE_FORMAT(datetime,'%H:%i:%s') time, matches.matchStarted, matches.match_status, matches.is_active, matches.type, ml.id, league.league, league.size, league.entry_fee, league.winning_amount, league.winners, ml.joined_team, ml.status, ml.league_id breakupId, matches.winner_team from league_teams lt, match_leagues ml, league, matches where lt.league_id = ml.id and ml.league_id= league.id and lt.match_id = ml.match_id and ml.match_id = matches.unique_id and lt.created_id = ? and matches.is_active='Y' order by matches.unique_id";
		List<MatchLeaguesBean> matchLeagues = getJdbcTemplate().query(sql, new Object[] { uniqueNumber },
				new BeanPropertyRowMapper<MatchLeaguesBean>(MatchLeaguesBean.class));
		return matchLeagues;
	}
	
	@Override
	public List<WinningBreakupDTO> getWinningBreakup(String breakupId) {
		String sql = "select id, prizeMoney, prizeRank from winning_breakup where id=?";
		List<WinningBreakupDTO> breakup = getJdbcTemplate().query(sql, new Object[] { breakupId },
				new BeanPropertyRowMapper<WinningBreakupDTO>(WinningBreakupDTO.class));
		return breakup;
	}

	@Override
	public List<PlayerDTO> getSquad(String matchId) {
		String sql = "select p.pid, p.name, p.imageURL, p.playingRole, p.country, s.credit, s.team playingTeamName, p.major_teams, p.current_age, p.born, p.battingStyle, p.bowlingStyle from player p, match_squad s where s.match_id=? and s.player_id=p.pid order by p.playingRole";
		List<PlayerDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId },
				new BeanPropertyRowMapper<PlayerDTO>(PlayerDTO.class));
		return leagues;
	}

	@Override
	public int createTeam(MatchTeam team) {
		// create team
		String sql = "insert into team_player (team_id, player_id, captain, vice_captain, team) values (?, ?, ?, ?, ?)";
		int[] insertedRecords = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerDTO players = team.getPlayers().get(i);
				ps.setString(1, team.getId());
				ps.setString(2, players.getPid());
				ps.setString(3, String.valueOf(players.isCaptain()));
				ps.setString(4, String.valueOf(players.isViceCaptain()));
				ps.setString(5, players.getPlayingTeamName());
			}

			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return team.getPlayers().size();
			}
		});		
		return insertedRecords.length;
	}
	
	@Override
	public int deleteTeam(String teamId) {
		String sql = "delete from team_player where team_id=?";
		return getJdbcTemplate().update(sql, teamId);
	}

	@Override
	public synchronized BigInteger insertTeam(MatchTeam matchTeam) {
		// insert team name
		setTeamName(matchTeam);
		String sql = "insert into team (name, match_id, created_id) values (?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] { matchTeam.getTeamName(), matchTeam.getMatchId(), matchTeam.getUniqueNumber() });
		return getJdbcTemplate().queryForObject("SELECT id from team where name=? and match_id=? and created_id=?", new Object[] { matchTeam.getTeamName(), matchTeam.getMatchId(), matchTeam.getUniqueNumber() }, BigInteger.class);
	}
	
	@Override
	public MatchTeam getTeam(String teamId) {
		MatchTeam matchTeam = new MatchTeam();
		String sql = "select id, name teamName, match_id, created_id from team where id=?";
		List<MatchTeam> teamList = getJdbcTemplate().query(sql, new Object[] { teamId }, new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		if(teamList.size() > 0) {
			matchTeam = teamList.get(0);
		}
		return matchTeam;
	}
	
	@Override
	public void setTeamName (MatchTeam matchTeam) {
		String sql = "select count(id) from team where match_id=? and created_id=?";
		int teamCount = getJdbcTemplate().queryForObject(sql, new Object[] { matchTeam.getMatchId(), matchTeam.getUniqueNumber() }, Integer.class);
		matchTeam.setTeamName("Team "+(teamCount+1));
	}
	
	@Override
	public MatchesDTO getMatchLiveStatus (String matchId) {
		String sql = "select * from matches where unique_id=?";
		return getJdbcTemplate().queryForObject(sql, new Object[] { matchId }, new BeanPropertyRowMapper<MatchesDTO>(MatchesDTO.class));
	}
	
	@Override
	public List<MatchTeam> getCreatedTeams(String uniqueNumber){
		String sql = "select team.id, team.name teamName, team.match_id, team.created_id uniqueNumber from team where team.created_id=? order by team.id";
		List<MatchTeam> teamList = getJdbcTemplate().query(sql, new Object[] {uniqueNumber}, new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		return teamList;
	}
	
	public List<MatchTeam> getCreatedTeamsOfMatch(String uniqueNumber, String matchId){
		String sql = "select distinct team.id, team.name teamName, team.match_id, team.created_id uniqueNumber from team where team.created_id=? and team.match_id=? order by team.id";
		List<MatchTeam> teamList = getJdbcTemplate().query(sql, new Object[] {uniqueNumber, matchId}, new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		return teamList;
	}

	@Override
	public List<MatchTeamBean> getTeam(String uniqueNumber, String matchId, String teamId) {
		String sql = "select team.id, team.name teamName, team.match_id, team.created_id uniqueNumber, p.pid, p.name, p.playingRole, p.imageURL, p.country, tp.captain, tp.vice_captain, tp.team playingTeamName from team, team_player tp, player p where team.id=? and team.id=tp.team_id and tp.player_id = p.pid order by team.id";
		List<MatchTeamBean> team = getJdbcTemplate().query(sql, new Object[] { teamId }, new BeanPropertyRowMapper<MatchTeamBean>(MatchTeamBean.class));
		return team;
	}
	
	@Override
	public synchronized String joinLeague(MatchTeam team, String leagueId) {

		SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(getJdbcTemplate())
				.withProcedureName("join_league");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("teamId", team.getId());
		inParamMap.put("leagueId", leagueId);
		inParamMap.put("matchId", team.getMatchId());
		inParamMap.put("createdId", team.getUniqueNumber());
		inParamMap.put("result", Types.VARCHAR);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);

		Map<String, Object> simpleJdbcCallResult = simpleJdbcCall.execute(in);

		if (simpleJdbcCallResult.get("result").toString().equalsIgnoreCase("SUCCESS")) {
			
			// deduct entry fee amount from wallet
			AccountDTO accountDTO = account(team.getUniqueNumber());
			LeagueDTO leagueDTO = getLeague(leagueId);
			BigDecimal entryFee = BigDecimal.valueOf(leagueDTO.getEntryFee());
			BigDecimal hundred = BigDecimal.valueOf(100);
			BigDecimal ten = BigDecimal.valueOf(10);
			BigDecimal tenPercentOfJoining =  (entryFee.divide(hundred)).multiply(ten);
			BigDecimal remainingBonus = null;
			if(accountDTO.getBonusAmount()!=null) {
				if(accountDTO.getBonusAmount().compareTo(tenPercentOfJoining) >= 0 ) {
					remainingBonus = accountDTO.getBonusAmount().subtract(tenPercentOfJoining);
				}else {
					tenPercentOfJoining = accountDTO.getBonusAmount();
					remainingBonus = null;
				}
			}else {
				tenPercentOfJoining = null;
			}
			accountDTO.setBonusAmount(remainingBonus);
			// deduct tenPerFrom deposited
			BigDecimal totalAmountTodeduct = entryFee; 
			if(tenPercentOfJoining != null) {
				totalAmountTodeduct = totalAmountTodeduct.subtract(tenPercentOfJoining);
				if(accountDTO.getDepositedAmount().compareTo(totalAmountTodeduct) >= 0) {
					accountDTO.setDepositedAmount(accountDTO.getDepositedAmount().subtract(totalAmountTodeduct));									
				}else {
					// deposited is less
					return "Balance is not sufficiant to join this group";
				}
			}else {
				if(accountDTO.getDepositedAmount().compareTo(entryFee) >= 0) {
					accountDTO.setDepositedAmount(accountDTO.getDepositedAmount().subtract(entryFee));									
				} else {
					// deposited is less
					return "Balance is not sufficiant to join this group";	
				}
			}
			
			// update account wallet
			accountDTO.setStatus("Debit");
			addBalance(accountDTO, true);
			
			return "League joined successfully, please join other league now.";
		} else {
			return "League is full, please join other league now.";
		}
	}
	
	@Override
	public int validateSmallOrGrand(String leagueId, String matchId, String createdId) {
		String sql = "select count(league_teams.league_id) count from league_teams, match_leagues where league_teams.league_id = ? and league_teams.match_id = ? and league_teams.created_id = ? and league_teams.league_id = match_leagues.id and match_leagues.size < 11";
		return getJdbcTemplate().queryForObject(sql, new Object[] { leagueId, matchId, createdId }, Integer.class);
	}
	@Override
	public List<MatchTeam> getJoinedLeagueTeams(String uniqueNumber, String matchId, String leagueId) {
		String sql = "select team.id, team.name teamName, team.match_id, team.created_id uniqueNumber from league_teams lt, team where lt.team_id = team.id and lt.created_id = team.created_id and lt.match_id = team.match_id and lt.created_id = ? and lt.match_id = ? and lt.league_id = ?";
		List<MatchTeam> matchTeam = getJdbcTemplate().query(sql, new Object[] { uniqueNumber, matchId, leagueId },
				new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		return matchTeam;
	}
	
	@Override
	public List<MatchTeam> getJoinedLeagueAllTeams(String matchId, String leagueId) {
		String sql = "select team.id, team.name teamName, team.match_id, team.created_id uniqueNumber, users.username from league_teams lt, team, users where team.created_id = users.unique_number and lt.team_id = team.id and lt.created_id = team.created_id and lt.match_id = team.match_id and lt.match_id = ? and lt.league_id = ?";
		List<MatchTeam> matchTeam = getJdbcTemplate().query(sql, new Object[] { matchId, leagueId },
				new BeanPropertyRowMapper<MatchTeam>(MatchTeam.class));
		return matchTeam;
	}
	
	@Override
	public int switchTeam(MatchTeam matchTeam, String leagueId, String teamIdOld) {
		String sql = "update league_teams set team_id = ? where league_id = ? and team_id = ? and match_id = ? and created_id = ? ";
		int updatedTeams = getJdbcTemplate().update(sql, matchTeam.getId(), leagueId, teamIdOld, matchTeam.getMatchId(), matchTeam.getUniqueNumber());
		return updatedTeams;
	}
	
	@Override
	public List<TeamRankPoints> getTeamsRankAndPoints(String uniqueNumber, String matchId, String leagueId){
		
		//String sql="select count(player_id) from player_points where match_id=?";
		//int point = getJdbcTemplate().queryForObject(sql, new Object[] { matchId }, Integer.class);
		
		//String sql = "select SUM(player_points.points) as points, team_player.team_id, team.created_id, team.name teamName, ROW_NUMBER() OVER (ORDER BY SUM(player_points.points) desc) teamRank, ROW_NUMBER() OVER (ORDER BY CASE when team.created_id =? then 1 else 2 end, SUM(player_points.points) desc) row_num from team_player, player_points, team, league_teams where team_player.player_id= player_points.player_id and player_points.Match_id=? and team_player.team_id=team.id and team.id = league_teams.team_id and league_teams.league_id=? group by team_player.team_id";
		String sql="select s.points, s.team_id , s.teamName, s.match_id, s.created_id, IF(s.points=@_last_age,@curRank:=@curRank,@curRank:=@_sequence) AS teamRank, @_sequence:=@_sequence+1,@_last_age:=s.points from (select IFNULL(SUM(pp.points), 0) as points,  tp.team_id, team.name teamName, team.match_id, team.created_id from league_teams lt, team, team_player tp left join player_points pp ON tp.player_id = pp.player_id and pp.match_id=? where lt.team_id = tp.team_id and lt.team_id= team.id and lt.league_id=? group by lt.team_id order by IFNULL(SUM(pp.points), 0) desc) s, (SELECT @curRank := 1, @_sequence:=1, @_last_age:=0) r";
		
		List<TeamRankPoints> teamRank = getJdbcTemplate().query(sql, new Object[] { matchId, leagueId },
				new BeanPropertyRowMapper<TeamRankPoints>(TeamRankPoints.class));
		return teamRank;	
	}
	
	@Override
	public List<MatchTeamBean> getTeamDetailsWithPoints(String teamId){	
		String sql = "select player.pid, player.name, player.playingRole, player.imageURL, team.id, team.name teamName, team.created_id uniqueNumber, player_points.points from team, team_player, player_points, player where team.id = team_player.team_id and team_player.team_id = ? and team_player.player_id = player_points.player_id and player_points.Match_id=team.match_id and team_player.player_id = player.pid";
		List<MatchTeamBean> teamDetailWithPoints = getJdbcTemplate().query(sql, new Object[] { teamId },
				new BeanPropertyRowMapper<MatchTeamBean>(MatchTeamBean.class));
		return teamDetailWithPoints;	
	}
	
	@Override
	public AccountDTO account(String userName) {
		String sql = "select username, amount, deposited_amount, bonus_amount, winning from account where username=?";
		AccountDTO dto = new AccountDTO();
		try {
		List<AccountDTO> accountList = getJdbcTemplate().query(sql, new Object[] { userName },
				new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class));
		if(accountList.size()>0) {
			dto = accountList.get(0);
		}
		return dto;
		}catch(Exception e) {
			return null;
		}
	}
	
	@Override
	public AccountDTO addBalance(AccountDTO account, boolean debit) {
		// add transaction
		AccountDTO acc = new AccountDTO();
		
		AccountDTO existing  = account(account.getUsername());
		if(existing!=null && debit==false) {
			if(existing.getDepositedAmount()!=null ) {
				account.setDepositedAmount(account.getDepositedAmount().add(existing.getDepositedAmount()));
			}
			if(existing.getBonusAmount()!=null ) {
				account.setBonusAmount(account.getBonusAmount().add(existing.getBonusAmount()));
			}	
		}
		try {
			if (addTransaction(account) > 0) {
				String sql = "insert into account ( username, amount, deposited_amount, bonus_amount, updated) values (?, ?, ?, ?, current_date()) ON DUPLICATE KEY UPDATE  amount = ?, deposited_amount = ?, bonus_amount = ?, updated=current_date() ";
				BigDecimal bd = null;
				if(account.getBonusAmount() !=null) {
					bd = account.getDepositedAmount().add(account.getBonusAmount());
				}else {
					bd = account.getDepositedAmount();
				}
				
				int i = getJdbcTemplate().update(sql,
						new Object[] { account.getUsername(),
								bd,
								account.getDepositedAmount(), account.getBonusAmount(), bd, account.getDepositedAmount(), account.getBonusAmount() });
				if (i > 0) {
					acc = account(account.getUsername());
				} else {
					throw new InternalException("Somthing is wrong");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return acc;
	}

	private int addTransaction(AccountDTO account) {
		String sql = "insert into account_transaction ( username, deposited_amount, bonus_amount, promotion, status, created,  created_id) values (?, ?, ?, ?, ?, current_date(), ?)";
		try {
			int i = getJdbcTemplate().update(sql, new Object[] { account.getUsername(), account.getDepositedAmount(),
					account.getBonusAmount(), account.getPromotion(), account.getStatus(), account.getUsername() });
			return i;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
