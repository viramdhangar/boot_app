package girnarsoft.com.demoapp.fragment.viewModel;

import java.util.List;

import girnarsoft.com.demoapp.fragment.model.LeagueModel;
import girnarsoft.com.demoapp.fragment.model.MatchModel;


public class LeagueViewModel {

    List<LeagueModel> leagueModelList;

    public List<LeagueModel> getLeagueModelList() {
        return leagueModelList;
    }

    public void setLeagueModelList(List<LeagueModel> leagueModelList) {
        this.leagueModelList = leagueModelList;

    }
}
