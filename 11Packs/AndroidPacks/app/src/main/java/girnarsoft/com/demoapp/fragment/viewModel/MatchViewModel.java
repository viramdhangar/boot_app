package girnarsoft.com.demoapp.fragment.viewModel;

import java.util.List;

import girnarsoft.com.demoapp.fragment.model.MatchModel;


public class MatchViewModel {

    List<MatchModel> matchModelList;

    public List<MatchModel> getMatchModelList() {
        return matchModelList;
    }

    public void setMatchModelList(List<MatchModel> matchModelList) {
        this.matchModelList = matchModelList;
    }
}
