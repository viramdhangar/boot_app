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
	public NewMatchesData newMatches(String API_KEY) {
		final String uri = "https://cricapi.com/api/matches?apikey="+API_KEY+"";
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
	public TeamSquad getSquad(String uniqueId, String API_KEY) {
		final String uri = "https://cricapi.com/api/fantasySquad?apikey=VXGb0jCOh4SG9mOIEqa3qQqJbGO2&unique_id="+uniqueId+"";
		RestTemplate restTemplate = new RestTemplate();
		TeamSquad result = null;
		try {
			result = restTemplate.getForObject(uri, TeamSquad.class);
			System.out.println(result);
			return result;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	@Override
	public PlayerDTO playerInfo(String id, String API_KEY) {
		final String uri = "https://cricapi.com/api/playerStats?apikey=VXGb0jCOh4SG9mOIEqa3qQqJbGO2&pid="+id+"";
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
	public FantacySummaryApi fantacySummaryApi(String id, String API_KEY) {
		final String uri = "https://cricapi.com/api/fantasySummary?apikey=VXGb0jCOh4SG9mOIEqa3qQqJbGO2&unique_id="+id+"";
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
