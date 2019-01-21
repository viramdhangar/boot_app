package girnarsoft.com.demoapp.activity.viewModel;

import java.util.List;

import girnarsoft.com.demoapp.activity.model.MatchModel;

public class MatchViewModel {

    List<MatchModel> matchModelList;

    public List<MatchModel> getMatchModelList() {
        return matchModelList;
    }

    public void setMatchModelList(List<MatchModel> matchModelList) {
        this.matchModelList = matchModelList;
    }
}
