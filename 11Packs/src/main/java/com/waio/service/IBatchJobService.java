/**
 * 
 */
package com.waio.service;

import java.util.List;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.NewMatchesData;
import com.waio.model.AccountDTO;
import com.waio.model.LeagueDTO;
import com.waio.model.PlayerDTO;
import com.waio.model.WinningPriceDTO;
/**
 * @author Viramm
 *
 */
public interface IBatchJobService {

	/**
	 * @return
	 */
	public NewMatchesData insertNewMatches() throws Exception; 
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
	/**
	 * @return
	 * @throws Exception
	 */
	NewMatchesData updateMatchesStatus() throws Exception;
	/**
	 * @param matchesList
	 */
	void declareWinner(String matchId);
	/**
	 * @return
	 */
	int distributeWinnersWinning(String matchId);
	/**
	 * @param matchId
	 */
	void cancelAmount(String matchId);
}
