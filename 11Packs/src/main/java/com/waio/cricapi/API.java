package com.waio.cricapi;

import org.springframework.web.client.RestTemplate;

public class API {

	public void getEmployees()
	{
		final String uri = "https://cricapi.com/api/fantasySummary?apikey=VC60hvq1q4N0UK2m0gZwyTfvcl92&unique_id=1152526";
	    RestTemplate restTemplate = new RestTemplate();
	     try {
	    FantacySummaryApi result = restTemplate.getForObject(uri, FantacySummaryApi.class);
	     
	    System.out.println(result);
	     }catch(Exception ex ) {
	 	    System.out.println(ex.getMessage());
	    	 
	     }
	}
}
