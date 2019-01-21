package com.waio.cricapi;

import java.util.List;

public class Bowling {

	private String title;
	private List<BowlingScore> scores;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<BowlingScore> getScores() {
		return scores;
	}
	public void setScores(List<BowlingScore> scores) {
		this.scores = scores;
	}
	

}
