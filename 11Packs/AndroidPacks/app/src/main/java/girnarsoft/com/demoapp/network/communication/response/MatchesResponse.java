package girnarsoft.com.demoapp.network.communication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import girnarsoft.com.demoapp.utils.ValidationUtils;

public class MatchesResponse {

    @SerializedName("items")
    @Expose
    private List<MatchesResponseItem> items = null;

    public List<MatchesResponseItem> getItems() {
        return items;
    }

    public void setItems(List<MatchesResponseItem> items) {
        this.items = items;
    }


    public class MatchesResponseItem {
        @SerializedName("unique_id")
        @Expose
        private String uniqueId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("squad")
        @Expose
        private String squad;
        @SerializedName("toss_winner_team")
        @Expose
        private Object tossWinnerTeam;
        @SerializedName("winner_team")
        @Expose
        private Object winnerTeam;
        @SerializedName("matchStarted")
        @Expose
        private String matchStarted;
        @SerializedName("dateTimeGMT")
        @Expose
        private String dateTimeGMT;
        @SerializedName("team-1")
        @Expose
        private String team1;
        @SerializedName("team-2")
        @Expose
        private String team2;

        @SerializedName("formattedTeamName")
        @Expose
        private String formattedTeamName;

        @SerializedName("typeShort")
        @Expose
        private String typeShort;

        @SerializedName("teamOneUrl")
        @Expose
        private String teamOneUrl;
        @SerializedName("teamTwoUrl")
        @Expose
        private String teamTwoUrl;

        @SerializedName("isActive")
        @Expose
        private String isActive;

        @SerializedName("tournamentName")
        @Expose
        private String tournamentName;

        public String getIsActive() {
            return isActive;
        }

        public void setIsActive(String isActive) {
            this.isActive = isActive;
        }

        public String getTournamentName() {
            return tournamentName;
        }

        public void setTournamentName(String tournamentName) {
            this.tournamentName = tournamentName;
        }

        public String getTeamOneUrl() {
            return teamOneUrl;
        }

        public void setTeamOneUrl(String teamOneUrl) {
            this.teamOneUrl = teamOneUrl;
        }

        public String getTeamTwoUrl() {
            return teamTwoUrl;
        }

        public void setTeamTwoUrl(String teamTwoUrl) {
            this.teamTwoUrl = teamTwoUrl;
        }

        public String getTypeShort() {
            return typeShort;
        }

        public void setTypeShort(String typeShort) {
            this.typeShort = typeShort;
        }

        public String getFormattedTeamName() {
            return formattedTeamName;
        }

        public void setFormattedTeamName(String formattedTeamName) {
            this.formattedTeamName = formattedTeamName;
        }

        public String getUniqueId() {
            return uniqueId;
        }

        public void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSquad() {
            return squad;
        }

        public void setSquad(String squad) {
            this.squad = squad;
        }

        public Object getTossWinnerTeam() {
            return tossWinnerTeam;
        }

        public void setTossWinnerTeam(Object tossWinnerTeam) {
            this.tossWinnerTeam = tossWinnerTeam;
        }

        public Object getWinnerTeam() {
            return winnerTeam;
        }

        public void setWinnerTeam(Object winnerTeam) {
            this.winnerTeam = winnerTeam;
        }

        public String getMatchStarted() {
            return matchStarted;
        }

        public void setMatchStarted(String matchStarted) {
            this.matchStarted = matchStarted;
        }

        public String getDateTimeGMT() {
            return dateTimeGMT;
        }

        public void setDateTimeGMT(String dateTimeGMT) {
            this.dateTimeGMT = dateTimeGMT;
        }

        public String getTeam1() {
            return team1;
        }

        public void setTeam1(String team1) {
            this.team1 = team1;
        }

        public String getTeam2() {
            return team2;
        }

        public void setTeam2(String team2) {
            this.team2 = team2;
        }
    }

}