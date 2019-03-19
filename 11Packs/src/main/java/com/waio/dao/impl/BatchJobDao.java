package com.waio.dao.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IBatchJobDao;
import com.waio.model.AccountDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
import com.waio.model.PlayerPointsDTO;
import com.waio.model.PlayerScoreBean;
import com.waio.model.PointSystemDTO;
import com.waio.model.WinningBreakupDTO;
import com.waio.model.WinningPriceDTO;

@Repository("BatchJobDao")
public class BatchJobDao extends JdbcDaoSupport implements IBatchJobDao{

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public int insertMatches(final List<MatchesDTO> matchesList) {

		String sql = "insert into matches (unique_id, date, datetime, team1, team2, type, squad, toss_winner_team, winner_team, matchStarted, match_status ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE team1=?, team2=?, matchStarted=?, winner_team=?, match_status=?";
		for (MatchesDTO match : matchesList) {
			getJdbcTemplate().update(sql, new Object[] { 
					match.getUnique_id(), 
					match.getDate(), 
					match.getDatetime(),
					match.getTeam1(), 
					match.getTeam2(), 
					match.getType(), 
					match.getSquad(), 
					match.getToss_winner_team(),
					match.getWinner_team(), 
					match.getMatchStarted(),
					match.getMatchStatus(),
					match.getTeam1(), 
					match.getTeam2(), 
					match.getMatchStarted(),
					match.getWinner_team(),
					match.getMatchStatus()
				});
		}
		return matchesList.size();
	}

	@Override
	public int insertSquad(final String uniqueId, final List<PlayerDTO> playerList) {
		String sql = "insert into match_squad (match_id, player_id, credit, team ) values (?, ?, ?, ?) ON DUPLICATE KEY update xi=?, team=?";		
		
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	PlayerDTO player = playerList.get(i);
	                    ps.setString(1, uniqueId);
	                    ps.setString(2, player.getPid());
	                    ps.setDouble(3, player.getCredit());
	                    ps.setString(4, player.getPlayingTeamName());
	                    ps.setString(5, player.getXi());
	                    ps.setString(6, player.getPlayingTeamName());
	                }

	                @Override
	                public int getBatchSize() {
	                    return playerList.size();
	                }
	            });
		return insertedPlayers.length;
	}
	
	@Override
	public int playing11Declared(final String uniqueId, final List<PlayerDTO> playerList) {
		String sql = "update match_squad set xi = 'Y' where match_id = ? and player_id = ?";		
		
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	PlayerDTO player = playerList.get(i);
	                    ps.setString(1, uniqueId);
	                    ps.setString(2, player.getPid());
	                }
	                @Override
	                public int getBatchSize() {
	                    return playerList.size();
	                }
	            });
		return insertedPlayers.length;
	}
	
	@Override
	public int insertPlayerInfo(final List<PlayerDTO> playerList) {
		String sql = "insert into player (pid, name, imageURL, country, playingRole, major_teams, current_age, born, battingStyle, bowlingStyle) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE imageURL=?";
		int[] insertedPlayers = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	PlayerDTO player = playerList.get(i);
	                    ps.setString(1, player.getPid());
	                    ps.setString(2, player.getName());
	                    ps.setString(3, player.getImageURL());
	                    ps.setString(4, player.getCountry());
	                    ps.setString(5, player.getPlayingRole());
	                   // ps.setDouble(6, player.getCredit());
	                    ps.setString(6, player.getMajorTeams());
	                    ps.setString(7, player.getCurrentAge());
	                    ps.setString(8, player.getBorn());
	                    ps.setString(9, player.getBattingStyle());
	                    ps.setString(10, player.getBowlingStyle());
	                    
	                    ps.setString(11, player.getImageURL());
	                }

	                @Override
	                public int getBatchSize() {
	                    return playerList.size();
	                }
	            });
		return insertedPlayers.length;
	}
	
	@Override
	public List<LeagueDTO> getLeagues() {
		String sql = "select * from league";
		return getJdbcTemplate().query(sql, new Object[] {}, new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
	}
	
	@Override
	public int insertLeagues(final List<MatchesDTO> matchesList) {
		String sql = "insert into match_leagues (league_id, match_id, size) select league.id, ? match_id, league.size from league on duplicate key update created=current_timestamp";
		int[] insertedLeagues = getJdbcTemplate().batchUpdate(sql,
	            new BatchPreparedStatementSetter() {
	                @Override
	                public void setValues(PreparedStatement ps, int i)
	                        throws SQLException {
	                	MatchesDTO matches = matchesList.get(i);
	                    ps.setString(1, matches.getUnique_id());
	                }

	                @Override
	                public int getBatchSize() {
	                    return matchesList.size();
	                }
	            });
		System.out.println("Leagues added for matches "+insertedLeagues.length);
		return insertedLeagues.length;
	}
	
	@Override
	public String createLeague(LeagueDTO leagueDTO) {
		
		int totalAmount = leagueDTO.getEntryFee()*leagueDTO.getSize();
		int percentDeducted = 15;
		int winningAmountDeducted = (totalAmount/100)*percentDeducted;
		int totalWwinningAmount = totalAmount-winningAmountDeducted;
		
		String sql = "insert into league (league, entry_fee, winning_amount, size, winners) values (?, ?, ?, ?, ?)";
		getJdbcTemplate().update(sql, new Object[] {
				leagueDTO.getLeague(),
				leagueDTO.getEntryFee(),
				totalWwinningAmount,
				leagueDTO.getSize(),
				leagueDTO.getWinners()
		});
		
		int leagueId = getJdbcTemplate().queryForObject("SELECT max(id) last from league", Integer.class);
		leagueDTO.setId(String.valueOf(leagueId));
		leagueDTO.setWinningAmount(totalWwinningAmount);
		insertWinningRank(leagueDTO);
		return "League created successfully.";
	}
	
	public int insertWinningRank(LeagueDTO leagueDTO) {
		String sql = "insert into winning_breakup (id, prizeMoney, prizeRank) values (?, ?, ?)";
		final List<WinningBreakupDTO> breakup = leagueDTO.getBreakup();
		int[] breakupInserted = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				WinningBreakupDTO wbDTO = breakup.get(i);
				ps.setString(1, leagueDTO.getId());
				ps.setInt(2, ((leagueDTO.getWinningAmount()/100)*wbDTO.getWinningPercent()));
				ps.setString(3, wbDTO.getPrizeRank());
			}
			@Override
			public int getBatchSize() {
				return breakup.size();
			}
		});
		return breakupInserted.length;
	}
	
	@Override
	public int saveBattingScore(final List<PlayerScoreBean> playerScoreBattingList) {
		String sql = "insert into batting (player_id, match_id, run, ball, fours, sixes, sRate, dismissal) values (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE run=?, ball=?, fours=?, sixes=?, sRate=?";
		int[] insertedScores = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerScoreBean playerScore = playerScoreBattingList.get(i);
				ps.setString(1, playerScore.getPlayerId());
				ps.setString(2, playerScore.getMatchId());
				ps.setString(3, playerScore.getRun());
				ps.setString(4, playerScore.getBall());
				ps.setString(5, playerScore.getFours());
				ps.setString(6, playerScore.getSixes());
				ps.setString(7, playerScore.getsRate());
				ps.setString(8, playerScore.getDismissal());
				ps.setString(9, playerScore.getRun());
				ps.setString(10, playerScore.getBall());
				ps.setString(11, playerScore.getFours());
				ps.setString(12, playerScore.getSixes());
				ps.setString(13, playerScore.getsRate());
			}

			@Override
			public int getBatchSize() {
				return playerScoreBattingList.size();
			}
		});
		return insertedScores.length;
	}

	@Override
	public int saveBowlingScore(final List<PlayerScoreBean> playerScoreBowlingList) {
		String sql = "insert into bowling (player_id, match_id, overs, maiden, runsgiven, wicket, economy) values (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE overs=?, maiden=?, runsgiven=?, wicket=?, economy=?";
		int[] insertedScores = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerScoreBean playerScore = playerScoreBowlingList.get(i);
				ps.setString(1, playerScore.getPlayerId());
				ps.setString(2, playerScore.getMatchId());
				ps.setString(3, playerScore.getOvers());
				ps.setString(4, playerScore.getMaiden());
				ps.setString(5, playerScore.getRunsgiven());
				ps.setString(6, playerScore.getWicket());
				ps.setString(7, playerScore.getEconomy());
				ps.setString(8, playerScore.getOvers());
				ps.setString(9, playerScore.getMaiden());
				ps.setString(10, playerScore.getRunsgiven());
				ps.setString(11, playerScore.getWicket());
				ps.setString(12, playerScore.getEconomy());
			}

			@Override
			public int getBatchSize() {
				return playerScoreBowlingList.size();
			}
		});
		return insertedScores.length;
	}

	@Override
	public int saveFieldingScore(final List<PlayerScoreBean> playerScoreFieldingList) {
		String sql = "insert into fielding (player_id, match_id, catch, stumped, run_out) values (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE catch=?, stumped=?, run_out=?";
		int[] insertedScores = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerScoreBean playerScore = playerScoreFieldingList.get(i);
				ps.setString(1, playerScore.getPlayerId());
				ps.setString(2, playerScore.getMatchId());
				ps.setString(3, playerScore.getCatches());
				ps.setString(4, playerScore.getStumped());
				ps.setString(5, playerScore.getRunOut());
				ps.setString(6, playerScore.getCatches());
				ps.setString(7, playerScore.getStumped());
				ps.setString(8, playerScore.getRunOut());
			}

			@Override
			public int getBatchSize() {
				return playerScoreFieldingList.size();
			}
		});
		return insertedScores.length;
	}
	
	@Override
	public List<PlayerScoreBean> getPlayersScore(String matchId) {
		String sql = "select player.player_id, player.match_id, batting.run, batting.ball, batting.fours, batting.sixes, batting.sRate, batting.dismissal, fielding.catch catches, fielding.stumped, fielding.run_out runOut, bowling.overs, bowling.maiden, bowling.runsgiven, bowling.wicket, bowling.economy from (select match_id, player_id from match_squad where match_id=?) player LEFT JOIN batting ON batting.player_id = player.player_id  and batting.match_id = player.match_id LEFT JOIN fielding ON fielding.player_id = player.player_id and fielding.match_id = player.match_id LEFT JOIN bowling ON bowling.player_id = player.player_id and bowling.match_id = player.match_id and player.match_id=?";
		List<PlayerScoreBean> playerScoreList = getJdbcTemplate().query(sql, new Object[] { matchId, matchId }, new BeanPropertyRowMapper<PlayerScoreBean>(PlayerScoreBean.class));
		return playerScoreList;
	}
	
	@Override
	public Map<String, Object> getConfiguredPoints(String type){
		String sql = "select id, caption, value, type, point_type from point_system where type=?";
		Map<String, Object> map = new HashMap<>();
		List<PointSystemDTO> configuredPoints = getJdbcTemplate().query(sql, new Object [] {type}, new BeanPropertyRowMapper<PointSystemDTO>(PointSystemDTO.class));
		for(PointSystemDTO ps : configuredPoints) {
			map.put(ps.getId(), ps.getValue());
		}
		return map;
	}
	
	@Override
	public int insertPlayerPoints(final List<PlayerPointsDTO> playerPointsList) {
		String sql = "insert into player_points (player_id, Match_id, points) values (?, ?, ?) ON DUPLICATE KEY UPDATE points=?";
		int[] insertedScores = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PlayerPointsDTO playerPoints = playerPointsList.get(i);
				ps.setString(1, playerPoints.getPlayerId());
				ps.setString(2, playerPoints.getMatchId());
				ps.setDouble(3, playerPoints.getPoints());
				ps.setDouble(4, playerPoints.getPoints());
			}
			@Override
			public int getBatchSize() {
				return playerPointsList.size();
			}
		});
		return insertedScores.length;
	}
	@Override
	public List<LeagueDTO> getEligibleLeaguesOfMatch(String matchId) {
		String sql = "select * from match_leagues where match_id= ? and size=joined_team or (size/2 < joined_team and size > 10 and match_id= ?)";
		List<LeagueDTO> leagues = getJdbcTemplate().query(sql, new Object[] { matchId, matchId }, new BeanPropertyRowMapper<LeagueDTO>(LeagueDTO.class));
		return leagues;
	}
	
	@Override
	public int saveWinning(final List<WinningPriceDTO> winningList) {
		String sql = "insert into winning (username,  league_id, match_id, team_id, team_rank, winningAmount) values (?, ?, ?, ?, ?, ?)"; // ON DUPLICATE KEY UPDATE points=?
		int[] insertedRecords = getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				WinningPriceDTO winningPriceDTO = winningList.get(i);
				ps.setString(1, winningPriceDTO.getUserName());
				ps.setString(2, winningPriceDTO.getLeagueId());
				ps.setString(3, winningPriceDTO.getMatchId());
				ps.setString(4, winningPriceDTO.getTeamId());
				ps.setString(5, winningPriceDTO.getTeamRank());
				ps.setBigDecimal(6, winningPriceDTO.getWinningAmount());
			}
			@Override
			public int getBatchSize() {
				return winningList.size();
			}
		});
		return insertedRecords.length;
	}
	
	@Override
	public int distributeWinnersWinning(String matchId) {
		String sql = "insert into account_transaction (username, status, created, created_id, deposited_amount, match_id, league_id, team_id) select username, 'WON', current_timestamp(), 'SystemJob', winningAmount, match_id, league_id, team_id from winning where to_wallet='N' and match_id=?"; 
		int insertedRecords = getJdbcTemplate().update(sql, new Object[] {matchId});
		return insertedRecords;
	}
	
	@Override
	public int distributeWinnersWinningInWallet(String matchId) {
		
		// insert winners winning in account transaction
		int updatedAccountTransaction = distributeWinnersWinning(matchId);
		System.out.println("updatedAccountTransaction table "+updatedAccountTransaction+" Fpr Match "+matchId);
		
		String sql = "select username, match_id, league_id, winningAmount, team_id from winning where to_wallet='N' and match_id=?"; 
		List<WinningPriceDTO> winningList = getJdbcTemplate().query(sql, new Object[] {matchId}, new BeanPropertyRowMapper<WinningPriceDTO>(WinningPriceDTO.class));
		System.out.println("total winning teams list winningList "+winningList.size());
		
		// update into account 
		
		String updateSql = "update account set winning = ?, amount=? where username=?";
		for(WinningPriceDTO winning: winningList) {
			// account select
			String accountSql = "select amount, winning from account where username=?";
			AccountDTO account = getJdbcTemplate().queryForObject(accountSql, new Object[] { winning.getUserName() }, new BeanPropertyRowMapper<AccountDTO>(AccountDTO.class));
			System.out.println("total winning for user  "+winning.getUserName()+" is :"+account.getWinning()+" for user: "+winning.getUserName());
			if(account.getAmount() == null) {
				account.setAmount(new BigDecimal(0));
			}
			
			if (account.getWinning() == null) {
				int i = getJdbcTemplate().update(updateSql, winning.getWinningAmount(), account.getAmount().add(winning.getWinningAmount()), winning.getUserName());
				if (i > 0) {
					System.out.println("total existing winning was empty updated "+winning.getWinningAmount()+" amount for user :"+winning.getUserName());
					int walletUpdated = updateToWallet(winning.getUserName(), winning.getMatchId(), winning.getLeagueId(), winning.getTeamId());
					if(walletUpdated>0) {
						System.out.println("wallet flag is updated for user: "+winning.getUserName()+", Match: "+winning.getMatchId()+", League: "+winning.getLeagueId()+", Team: "+winning.getTeamId());
					}
				}
			} else {
				int i = getJdbcTemplate().update(updateSql, winning.getWinningAmount().add(account.getWinning()), account.getAmount().add(winning.getWinningAmount()), winning.getUserName());
				if (i > 0) {
					System.out.println("total existing winning was "+account.getWinning()+" updated with"+winning.getWinningAmount()+" amount, Total winning is :"+winning.getWinningAmount().add(account.getWinning())+" for user "+winning.getUserName());
					int walletUpdated = updateToWallet(winning.getUserName(), winning.getMatchId(), winning.getLeagueId(), winning.getTeamId());
					if(walletUpdated>0) {
						System.out.println("wallet flag is updated for user: "+winning.getUserName()+", Match: "+winning.getMatchId()+", League: "+winning.getLeagueId()+", Team: "+winning.getTeamId());
					}
				}
			}
		}
		
		// mark match is FINISHED
		int matchClosed = finishMatch(matchId);
		if(matchClosed > 0) {
			System.out.println("::Match finished now::"+matchId);
		}
		
		return winningList.size();
	}
	
	@Override
	public int updateToWallet(String username, String matchId, String leagueId, String teamId) {
		String sql = "update winning set to_wallet='Y' where to_wallet='N' and username=? and match_id=? and league_id=? and team_id=? "; 
		int insertedRecords = getJdbcTemplate().update(sql, new Object[] { username, matchId, leagueId, teamId });
		return insertedRecords;
	}
	
	private int finishMatch(String matchId) {
		String sql = "update matches set is_active = 'FINISHED' where unique_id=?";
		int insertedRecords = getJdbcTemplate().update(sql, new Object[] { matchId });
		return insertedRecords;
	}
	
}
