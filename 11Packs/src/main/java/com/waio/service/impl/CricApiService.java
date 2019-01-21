/**
 * 
 */
package com.waio.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.waio.cricapi.FantacySummaryApi;
import com.waio.cricapi.NewMatchesData;
import com.waio.cricapi.TeamSquad;
import com.waio.model.PlayerDTO;
import com.waio.service.ICricApiService;

/**
 * @author Viramm
 *
 */
@Service("CricApiService")
public class CricApiService implements ICricApiService {

	@Override
	public NewMatchesData newMatches() {
		final String uri = "https://cricapi.com/api/matches?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92";
		RestTemplate restTemplate = new RestTemplate();
		try {
			NewMatchesData result = restTemplate.getForObject(uri, NewMatchesData.class);

			System.out.println(result);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}

	@Override
	public TeamSquad getSquad(String uniqueId) {
		final String uri = "https://cricapi.com/api/fantasySquad?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92&unique_id="+uniqueId+"";
		RestTemplate restTemplate = new RestTemplate();
		try {
			TeamSquad result = restTemplate.getForObject(uri, TeamSquad.class);

			System.out.println(result);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	@Override
	public PlayerDTO playerInfo(String id) {
		final String uri = "https://cricapi.com/api/playerStats?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92&pid="+id+"";
		RestTemplate restTemplate = new RestTemplate();
		try {
			PlayerDTO result = restTemplate.getForObject(uri, PlayerDTO.class);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	@Override
	public FantacySummaryApi fantacySummaryApi(String id) {
		final String uri = "https://cricapi.com/api/fantasySummary?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92&unique_id="+id+"";
		RestTemplate restTemplate = new RestTemplate();
		try {
			FantacySummaryApi result = restTemplate.getForObject(uri, FantacySummaryApi.class);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
}
