package com.waio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.waio.cricapi.NewMatchesData;
import com.waio.model.LeagueDTO;
import com.waio.service.IBatchJobService;

@RestController
@RequestMapping({"/api/job"})
public class BatchJobController {

	@Autowired
	private IBatchJobService batchJobService;
	
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
	
	@PostMapping("/v1/createLeague")
	public @ResponseBody String createLeague(@RequestBody LeagueDTO leagueDTO) {
		return batchJobService.createLeague(leagueDTO);
	}
	
	@PostMapping("/v1/updateScoreAndPoints/{matchId}")
	public @ResponseBody String updateScore(@PathVariable String matchId) {
		return batchJobService.fantasySummaryApi(matchId);
	}
}
