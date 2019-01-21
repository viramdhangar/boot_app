package girnarsoft.com.demoapp.network.communication.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SquadResponse {

    @SerializedName("items")
    @Expose
    private List<SquadResponseItem> players = null;

    @SerializedName("matchId")
    @Expose
    private int matchId;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public List<SquadResponseItem> getItems() {
        return players;
    }

    public void setItems(List<SquadResponseItem> items) {
        this.players = items;
    }


    public class SquadResponseItem {
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