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
public class PlayerProfileInfo {

	private MatchesTypesBowling bowling;
	private MatchesTypesBatting batting;
	/**
	 * @return the bowling
	 */
	public MatchesTypesBowling getBowling() {
		return bowling;
	}
	/**
	 * @param bowling the bowling to set
	 */
	public void setBowling(MatchesTypesBowling bowling) {
		this.bowling = bowling;
	}
	/**
	 * @return the batting
	 */
	public MatchesTypesBatting getBatting() {
		return batting;
	}
	/**
	 * @param batting the batting to set
	 */
	public void setBatting(MatchesTypesBatting batting) {
		this.batting = batting;
	}
}
