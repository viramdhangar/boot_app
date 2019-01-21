package com.waio.cricapi;

import java.util.List;

public class Fielding {

	private String title;
	private List<FieldingScore> scores;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<FieldingScore> getScores() {
		return scores;
	}
	public void setScores(List<FieldingScore> scores) {
		this.scores = scores;
	}
	

}
