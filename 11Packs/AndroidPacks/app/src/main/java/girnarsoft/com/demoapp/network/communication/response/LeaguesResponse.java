package girnarsoft.com.demoapp.network.communication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LeaguesResponse {

    @SerializedName("items")
    @Expose
    private List<LeaguesResponseItem> items = null;

    @SerializedName("matchId")
    @Expose
    private int matchId;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public List<LeaguesResponseItem> getItems() {
        return items;
    }

    public void setItems(List<LeaguesResponseItem> items) {
        this.items = items;
    }


    public class LeaguesResponseItem {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("league")
        @Expose
        private String league;
        @SerializedName("size")
        @Expose
        private int size;
        @SerializedName("entryFee")
        @Expose
        private int entryFee;

        @SerializedName("joinedTeam")
        @Expose
        private int joinedTeam;

        @SerializedName("winners")
        @Expose
        private int winners;

        @SerializedName("winningAmount")
        @Expose
        private int winningAmount;

        @SerializedName("breakupId")
        @Expose
        private int breakupId;

        @SerializedName("matchId")
        @Expose
        private String matchId;


        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public int getJoinedTeam() {
            return joinedTeam;
        }

        public void setJoinedTeam(int joinedTeam) {
            this.joinedTeam = joinedTeam;
        }

        public int getWinners() {
            return winners;
        }

        public void setWinners(int winners) {
            this.winners = winners;
        }

        public int getWinningAmount() {
            return winningAmount;
        }

        public void setWinningAmount(int winningAmount) {
            this.winningAmount = winningAmount;
        }

        public int getBreakupId() {
            return breakupId;
        }

        public void setBreakupId(int breakupId) {
            this.breakupId = breakupId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeague() {
            return league;
        }

        public void setLeague(String league) {
            this.league = league;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getEntryFee() {
            return entryFee;
        }

        public void setEntryFee(int entryFee) {
            this.entryFee = entryFee;
        }
    }

}