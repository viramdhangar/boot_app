package com.waio.cricapi;

import org.springframework.web.client.RestTemplate;

public class SquadAPI {

	public void getTeamSquad()
	{
		final String uri = "https://cricapi.com/api/fantasySquad?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92&unique_id=1034809";
	    RestTemplate restTemplate = new RestTemplate();
	     try {
	    	 TeamSquad result = restTemplate.getForObject(uri, TeamSquad.class);
	     
	    System.out.println(result);
	     }catch(Exception ex ) {
	 	    System.out.println(ex.getMessage());
	    	 
	     }
	}
}
