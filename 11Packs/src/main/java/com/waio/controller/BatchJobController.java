package com.waio.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waio.cricapi.MatchesDTO;
import com.waio.cricapi.NewMatchesData;
import com.waio.model.LeagueDTO;
import com.waio.service.IBatchJobService;
import com.waio.service.IMatchService;

@RestController
@RequestMapping({"/api/job"})
public class BatchJobController {

	@Autowired
	private IBatchJobService batchJobService;
	
	@Autowired
	IMatchService matchService;

	@Scheduled(cron = "0 0 1 * * ?")
	@PostMapping("/v1/matchesAPI")
	public NewMatchesData matchesAPI() {
		try {
			// update score first before updating matches status
			updateScore();
			// update match score
			return batchJobService.insertNewMatches();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error.."+e.getMessage());
			return null;
		}
	}
	
	@Scheduled(cron = "30 * * * * ?")
	@PostMapping("/v1/updateMatchesStatus")
	public NewMatchesData updateMatchesStatus() {
		try {
			// update score first before updating matches status
			updateScore();
			// update match score
			return batchJobService.updateMatchesStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error.."+e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/v1/createLeague")
	public @ResponseBody String createLeague(@RequestBody LeagueDTO leagueDTO) {
		return batchJobService.createLeague(leagueDTO);
	}

	@PostMapping("/v1/updateScoreAndPoints")
	public @ResponseBody String updateScore() {
		List<MatchesDTO> matches = new ArrayList<MatchesDTO>();
		try {
			matches = matchService.getActiveMatches();
			for(MatchesDTO match : matches) {
				System.out.println("Job updateScoreAndPoints started for "+match.getTeam1()+" Vs "+match.getTeam2()+" .....");
				batchJobService.fantasySummaryApi(match.getUnique_id());
				System.out.println("Job executed successfully for "+match.getTeam1()+" Vs "+match.getTeam2());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error.."+e.getMessage());
		}
		return "updateScoreAndPoints Job executed successfully";
	}

	// No API used
	@PostMapping("/v1/declareWinner/{matchId}")
	public String declareWinner(@PathVariable String matchId) {
		//List<MatchesDTO> matches = new ArrayList<MatchesDTO>();
		try {
			//matches = matchService.getCompletedMatchesForWinning();
			batchJobService.declareWinner(matchId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error.."+e.getMessage());
			return "Error.."+e.getMessage();
		}
		return "Declare Winner Job executed successfully";
	}

	// No API used
	@PostMapping("/v1/distributeWinning/{matchId}")
	public String distributeWinning(@PathVariable String matchId) {
		try {
			batchJobService.distributeWinnersWinning(matchId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error.."+e.getMessage());
		}
		return "Distribute Winning Job executed successfully";
	}
	
	// No API used
	@PostMapping("/v1/returnCanceledLeagueAmount/{matchId}")
	public String cancelAmount(@PathVariable String matchId) {
		try {
			batchJobService.cancelAmount(matchId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error.."+e.getMessage());
		}
		return "Cancel league return amount Job executed successfully";
	}
}
