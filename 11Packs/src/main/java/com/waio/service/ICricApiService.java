/**
 * 
 */
package com.waio.service;

import com.waio.cricapi.FantacySummaryApi;
import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.TeamSquad;
import com.waio.model.PlayerDTO;

/**
 * @author Viramm
 *
 */
public interface ICricApiService {

	public NewMatchesData newMatches();
	public TeamSquad getSquad(String uniqueId);
	public PlayerDTO playerInfo(String id);
	public FantacySummaryApi fantacySummaryApi(String id);
}
