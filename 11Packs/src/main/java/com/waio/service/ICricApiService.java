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

	public NewMatchesData newMatches(String API_KEY);
	public TeamSquad getSquad(String uniqueId, String API_KEY);
	public PlayerDTO playerInfo(String id, String API_KEY);
	public FantacySummaryApi fantacySummaryApi(String id, String API_KEY);
}
