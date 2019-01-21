package com.waio.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.waio.cricapi.MatchesDTO;
import com.waio.dao.IBatchJobDao;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
import com.waio.model.PlayerPointsDTO;
import com.waio.model.PlayerScoreBean;
import com.waio.model.PointSystemDTO;
import com.waio.model.WinningBreakupDTO;

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

		String sql = "insert into matches (unique_id, date, datetime, team1, team2, type, squad, toss_winner_team, winner_team, matchStarted ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE matchStarted=?";
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
					match.getMatchStarted() 
				});
		}
		return matchesList.size();
	}

	@Override
	public int insertSquad(final String uniqueId, final List<PlayerDTO> playerList) {
		String sql = "insert into match_squad (match_id, player_id, credit, team ) values (?, ?, ?, ?) ON DUPLICATE KEY update xi=?";		
		
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
	public int insertLeagues(List<MatchesDTO> matchesList) {
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
}
