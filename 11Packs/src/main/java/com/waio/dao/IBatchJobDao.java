/**
 * 
 */
package com.waio.dao;

import java.util.List;
import java.util.Map;

import com.waio.cricapi.MatchesDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
import com.waio.model.PlayerPointsDTO;
import com.waio.model.PlayerScoreBean;

/**
 * @author Viramm
 *
 */
public interface IBatchJobDao {

	/**
	 * @param newMatchesData
	 * @return
	 */
	public int insertMatches(final List<MatchesDTO> matchesList);
	/**
	 * @param uniqueId
	 * @return
	 */
	public int insertSquad(String uniqueId, List<PlayerDTO> playerList);
	/**
	 * @return
	 */
	public List<LeagueDTO> getLeagues();
	/**
	 * @param matchesLeagesList
	 * @return
	 */
	public int insertLeagues(List<MatchesDTO> matchesList);
	/**
	 * @param playerList
	 * @return
	 */
	int insertPlayerInfo(final List<PlayerDTO> playerList);
	/**
	 * @param leagueDTO
	 * @return
	 */
	String createLeague(LeagueDTO leagueDTO);
	/**
	 * @param playerScoreBattingList
	 * @return
	 */
	int saveBattingScore(final List<PlayerScoreBean> playerScoreBattingList);
	/**
	 * @param playerScoreBattingList
	 * @return
	 */
	int saveBowlingScore(final List<PlayerScoreBean> playerScoreBattingList);
	/**
	 * @param playerScoreBattingList
	 * @return
	 */
	int saveFieldingScore(final List<PlayerScoreBean> playerScoreBattingList);
	/**
	 * @return
	 */
	List<PlayerScoreBean> getPlayersScore(String matchId);
	/**
	 * @param type
	 * @return
	 */
	Map<String, Object> getConfiguredPoints(String type);
	/**
	 * @param playerPointsList
	 * @return
	 */
	int insertPlayerPoints(final List<PlayerPointsDTO> playerPointsList);
	/**
	 * @param uniqueId
	 * @param playerList
	 */
	int playing11Declared(final String uniqueId, final List<PlayerDTO> playerList);
}
