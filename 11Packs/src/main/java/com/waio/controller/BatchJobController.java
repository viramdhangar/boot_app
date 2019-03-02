package com.waio.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
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

//	@Scheduled(cron = "0 0 1 * * ?")
	@GetMapping("/v1/matchesAPI")
	public NewMatchesData matchesAPI() {
		try {
			return batchJobService.insertNewMatches();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
//	@Scheduled(cron = "20 * * * * ?")
	@GetMapping("/v1/updateMatchesStatus")
	public NewMatchesData updateMatchesStatus() {
		try {
			return batchJobService.updateMatchesStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	@PostMapping("/v1/createLeague")
	public @ResponseBody String createLeague(@RequestBody LeagueDTO leagueDTO) {
		return batchJobService.createLeague(leagueDTO);
	}

	//@Scheduled(cron = "15 * * * * ?")
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
			System.out.println("Some issue with the job please check logs..");
		}
		return "Job executed successfully";
	}
}
