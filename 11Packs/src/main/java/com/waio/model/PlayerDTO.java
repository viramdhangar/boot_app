/**
 * 
 */
package com.waio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.waio.cricapi.PlayerProfileInfo;

/**
 * @author Viramm
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDTO {

    private String pid;
    private String name;
    private String imageURL;
    private String country;
    private String playingRole;
    private double credit;
    private String majorTeams;
	private String currentAge;
    private String born;
    private String battingStyle;
    private String bowlingStyle;
    private boolean selected;
    private boolean captain;
    private boolean viceCaptain;
    private String playingTeamName;
    private double points;
    private String xi;
    private PlayerProfileInfo data;
	/**
	 * @return the xi
	 */
	public String getXi() {
		return xi;
	}
	/**
	 * @param xi the xi to set
	 */
	public void setXi(String xi) {
		this.xi = xi;
	}
	/**
	 * @return the points
	 */
	public double getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(double points) {
		this.points = points;
	}
	/**
	 * @return the playingTeamName
	 */
	public String getPlayingTeamName() {
		return playingTeamName;
	}
	/**
	 * @param playingTeamName the playingTeamName to set
	 */
	public void setPlayingTeamName(String playingTeamName) {
		this.playingTeamName = playingTeamName;
	}
	/**
	 * @return the captain
	 */
	public boolean isCaptain() {
		return captain;
	}
	/**
	 * @param captain the captain to set
	 */
	public void setCaptain(boolean captain) {
		this.captain = captain;
	}
	/**
	 * @return the viceCaptain
	 */
	public boolean isViceCaptain() {
		return viceCaptain;
	}
	/**
	 * @param viceCaptain the viceCaptain to set
	 */
	public void setViceCaptain(boolean viceCaptain) {
		this.viceCaptain = viceCaptain;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return the pid
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * @param pid the pid to set
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the playingRole
	 */
	public String getPlayingRole() {
		return playingRole;
	}
	/**
	 * @param playingRole the playingRole to set
	 */
	public void setPlayingRole(String playingRole) {
		this.playingRole = playingRole;
	}
	/**
	 * @return the credit
	 */
	public double getCredit() {
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(double credit) {
		this.credit = credit;
	}
	/**
	 * @return the majorTeams
	 */
	public String getMajorTeams() {
		return majorTeams;
	}
	/**
	 * @param majorTeams the majorTeams to set
	 */
	public void setMajorTeams(String majorTeams) {
		this.majorTeams = majorTeams;
	}
	/**
	 * @return the currentAge
	 */
	public String getCurrentAge() {
		return currentAge;
	}
	/**
	 * @param currentAge the currentAge to set
	 */
	public void setCurrentAge(String currentAge) {
		this.currentAge = currentAge;
	}
	/**
	 * @return the born
	 */
	public String getBorn() {
		return born;
	}
	/**
	 * @param born the born to set
	 */
	public void setBorn(String born) {
		this.born = born;
	}
	/**
	 * @return the battingStyle
	 */
	public String getBattingStyle() {
		return battingStyle;
	}
	/**
	 * @param battingStyle the battingStyle to set
	 */
	public void setBattingStyle(String battingStyle) {
		this.battingStyle = battingStyle;
	}
	/**
	 * @return the bowlingStyle
	 */
	public String getBowlingStyle() {
		return bowlingStyle;
	}
	/**
	 * @param bowlingStyle the bowlingStyle to set
	 */
	public void setBowlingStyle(String bowlingStyle) {
		this.bowlingStyle = bowlingStyle;
	}
	/**
	 * @return the data
	 */
	public PlayerProfileInfo getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(PlayerProfileInfo data) {
		this.data = data;
	}
}
