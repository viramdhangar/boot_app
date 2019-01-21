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
public class BattingStats {

	private Double Ave;
	private int HS;
	private int Runs;
	private int NO;
	private int Inns;
	private int Mat;
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
	 * @return the hS
	 */
	public int getHS() {
		return HS;
	}
	/**
	 * @param hS the hS to set
	 */
	public void setHS(int hS) {
		HS = hS;
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
	 * @return the nO
	 */
	public int getNO() {
		return NO;
	}
	/**
	 * @param nO the nO to set
	 */
	public void setNO(int nO) {
		NO = nO;
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
}
