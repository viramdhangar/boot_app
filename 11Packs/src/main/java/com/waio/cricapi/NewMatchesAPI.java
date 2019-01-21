package com.waio.cricapi;

import org.springframework.web.client.RestTemplate;

public class NewMatchesAPI {

	public void newMatches()
	{
		final String uri = "https://cricapi.com/api/matches?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92";
	    RestTemplate restTemplate = new RestTemplate();
	     try {
	    	 NewMatchesData result = restTemplate.getForObject(uri, NewMatchesData.class);
	     
	    System.out.println(result);
	     }catch(Exception ex ) {
	 	    System.out.println(ex.getMessage());
	    	 
	     }
	}
}
