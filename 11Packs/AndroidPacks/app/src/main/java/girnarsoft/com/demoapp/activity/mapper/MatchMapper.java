package girnarsoft.com.demoapp.activity.mapper;

import java.util.ArrayList;
import java.util.List;

import girnarsoft.com.demoapp.activity.model.MatchModel;
import girnarsoft.com.demoapp.activity.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.communication.response.MatchesResponse;
import girnarsoft.com.demoapp.network.viewcallback.IMapper;
import girnarsoft.com.demoapp.utils.PacksConstants;
import girnarsoft.com.demoapp.utils.ValidationUtils;

public class MatchMapper implements IMapper<List<MatchesResponse.MatchesResponseItem>, MatchViewModel> {
    @Override
    public MatchViewModel toViewModel(List<MatchesResponse.MatchesResponseItem> matchesResponse, int matchType) {
        MatchViewModel matchViewModel = new MatchViewModel();
        if(ValidationUtils.isValidResponse(matchesResponse)){
            if(ValidationUtils.isListNotEmpty(matchesResponse)){
                List<MatchesResponse.MatchesResponseItem> matchesResponseItemList = matchesResponse;
                List<MatchModel> matchModelList = new ArrayList<>();
                for (MatchesResponse.MatchesResponseItem matchesResponseItem :matchesResponseItemList) {
//                    if(PacksConstants.MatchType.COMPLETED == matchType){
//                       if(null != matchesResponseItem.getMatchStarted() && Boolean.parseBoolean(matchesResponseItem.getMatchStarted()))
//                    }
                    MatchModel matchModel = new MatchModel();
                    matchModel.setMatchStarted(Boolean.parseBoolean(matchesResponseItem.getMatchStarted()));
                    matchModel.setSquad(Boolean.parseBoolean(matchesResponseItem.getSquad()));
                    matchModel.setTeamOne(matchesResponseItem.getTeam1());
                    matchModel.setTeamtwo(matchesResponseItem.getTeam2());
                    matchModel.setTime(matchesResponseItem.getTime());
                    matchModel.setShortName(matchesResponseItem.getFormattedTeamName());
                    matchModel.setShortType(matchesResponseItem.getTypeShort());
                    matchModelList.add(matchModel);
                }
                matchViewModel.setMatchModelList(matchModelList);
            }
        }
        return matchViewModel;
    }
}
