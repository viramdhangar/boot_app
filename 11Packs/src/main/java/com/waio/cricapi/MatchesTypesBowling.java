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
public class MatchesTypesBowling {

	private BowlingStats listA;
	private BowlingStats firstClass;
	private BowlingStats T20Is;
	private BowlingStats ODIs;
	private BowlingStats tests;
	/**
	 * @return the listA
	 */
	public BowlingStats getListA() {
		return listA;
	}
	/**
	 * @param listA the listA to set
	 */
	public void setListA(BowlingStats listA) {
		this.listA = listA;
	}
	/**
	 * @return the firstClass
	 */
	public BowlingStats getFirstClass() {
		return firstClass;
	}
	/**
	 * @param firstClass the firstClass to set
	 */
	public void setFirstClass(BowlingStats firstClass) {
		this.firstClass = firstClass;
	}
	/**
	 * @return the t20Is
	 */
	public BowlingStats getT20Is() {
		return T20Is;
	}
	/**
	 * @param t20Is the t20Is to set
	 */
	public void setT20Is(BowlingStats t20Is) {
		T20Is = t20Is;
	}
	/**
	 * @return the oDIs
	 */
	public BowlingStats getODIs() {
		return ODIs;
	}
	/**
	 * @param oDIs the oDIs to set
	 */
	public void setODIs(BowlingStats oDIs) {
		ODIs = oDIs;
	}
	/**
	 * @return the tests
	 */
	public BowlingStats getTests() {
		return tests;
	}
	/**
	 * @param tests the tests to set
	 */
	public void setTests(BowlingStats tests) {
		this.tests = tests;
	}
}
