/**
 * 
 */
package com.waio.service;

import java.util.List;

import com.waio.cricapi.NewMatchesData;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
/**
 * @author Viramm
 *
 */
public interface IBatchJobService {

	/**
	 * @return
	 */
	public NewMatchesData insertNewMatches(); 
	/**
	 * @param leagueDTO
	 * @return
	 */
	String createLeague(LeagueDTO leagueDTO);
	/**
	 * @param matchId
	 * @return
	 */
	String fantasySummaryApi(String matchId);
	/**
	 * @return
	 */
	int updateScoreAndCreatePoints(List<PlayerDTO> playerList, String matchId);
}
