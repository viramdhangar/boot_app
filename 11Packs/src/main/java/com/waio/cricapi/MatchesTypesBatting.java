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
public class MatchesTypesBatting {

	private BattingStats listA;
	private BattingStats firstClass;
	private BattingStats T20Is;
	private BattingStats ODIs;
	private BattingStats tests;
	/**
	 * @return the listA
	 */
	public BattingStats getListA() {
		return listA;
	}
	/**
	 * @param listA the listA to set
	 */
	public void setListA(BattingStats listA) {
		this.listA = listA;
	}
	/**
	 * @return the firstClass
	 */
	public BattingStats getFirstClass() {
		return firstClass;
	}
	/**
	 * @param firstClass the firstClass to set
	 */
	public void setFirstClass(BattingStats firstClass) {
		this.firstClass = firstClass;
	}
	/**
	 * @return the t20Is
	 */
	public BattingStats getT20Is() {
		return T20Is;
	}
	/**
	 * @param t20Is the t20Is to set
	 */
	public void setT20Is(BattingStats t20Is) {
		T20Is = t20Is;
	}
	/**
	 * @return the oDIs
	 */
	public BattingStats getODIs() {
		return ODIs;
	}
	/**
	 * @param oDIs the oDIs to set
	 */
	public void setODIs(BattingStats oDIs) {
		ODIs = oDIs;
	}
	/**
	 * @return the tests
	 */
	public BattingStats getTests() {
		return tests;
	}
	/**
	 * @param tests the tests to set
	 */
	public void setTests(BattingStats tests) {
		this.tests = tests;
	}
}
