package girnarsoft.com.demoapp.fragment.model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import girnarsoft.com.demoapp.utils.imageloader.ImageLoader;

public class LeagueModel extends BaseObservable {

    private  String leagueName;
    private  int teamSize;
    private  int joinedTeam;
    private  int winningAmount;
    private  int winners;
    private  int breakupId;
    private  int entryFee;
    private  String leagueId;
    private  String matchId;

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public int getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public int getJoinedTeam() {
        return joinedTeam;
    }

    public void setJoinedTeam(int joinedTeam) {
        this.joinedTeam = joinedTeam;
    }

    public int getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(int winningAmount) {
        this.winningAmount = winningAmount;
    }

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public int getBreakupId() {
        return breakupId;
    }

    public void setBreakupId(int breakupId) {
        this.breakupId = breakupId;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(int entryFee) {
        this.entryFee = entryFee;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
