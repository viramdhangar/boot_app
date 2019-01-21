package com.waio.cricapi;

import java.util.List;

public class Batting {

	private String title;
	private List<BattingScore> scores;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<BattingScore> getScores() {
		return scores;
	}
	public void setScores(List<BattingScore> scores) {
		this.scores = scores;
	}
	
}
