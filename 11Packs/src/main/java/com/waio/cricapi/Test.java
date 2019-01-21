
package com.waio.cricapi;
import java.io.IOException;

import com.waio.service.impl.CricApiService;

public class Test {
   
	 
     
	public static void main(String[] args) throws IOException{
    	
    	API api = new API();
    	
    	api.getEmployees();
    	
		//NewMatchesAPI api = new NewMatchesAPI();
		//api.newMatches();
		
		//SquadAPI api = new SquadAPI();
		//api.getTeamSquad();
		/*CricApiService api = new CricApiService();
		api.playerInfo("35320");*/
    }
    
}
    

