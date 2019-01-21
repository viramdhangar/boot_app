/**
 * 
 */
package com.waio.cricapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Viramm
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BowlingStats {

	private Double Ave;
	private int Wkts;
	private int Runs;
	private int Balls;
	private int Mat;
	private int Inns;
	/**
	 * @return the ave
	 */
	public Double getAve() {
		return Ave;
	}
	/**
	 * @param ave the ave to set
	 */
	public void setAve(Double ave) {
		Ave = ave;
	}
	/**
	 * @return the wkts
	 */
	public int getWkts() {
		return Wkts;
	}
	/**
	 * @param wkts the wkts to set
	 */
	public void setWkts(int wkts) {
		Wkts = wkts;
	}
	/**
	 * @return the runs
	 */
	public int getRuns() {
		return Runs;
	}
	/**
	 * @param runs the runs to set
	 */
	public void setRuns(int runs) {
		Runs = runs;
	}
	/**
	 * @return the balls
	 */
	public int getBalls() {
		return Balls;
	}
	/**
	 * @param balls the balls to set
	 */
	public void setBalls(int balls) {
		Balls = balls;
	}
	/**
	 * @return the mat
	 */
	public int getMat() {
		return Mat;
	}
	/**
	 * @param mat the mat to set
	 */
	public void setMat(int mat) {
		Mat = mat;
	}
	/**
	 * @return the inns
	 */
	public int getInns() {
		return Inns;
	}
	/**
	 * @param inns the inns to set
	 */
	public void setInns(int inns) {
		Inns = inns;
	}
}
