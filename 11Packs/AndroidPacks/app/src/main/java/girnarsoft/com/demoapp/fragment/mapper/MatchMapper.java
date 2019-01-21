package girnarsoft.com.demoapp.fragment.mapper;

import java.util.ArrayList;
import java.util.List;

import girnarsoft.com.demoapp.fragment.model.MatchModel;
import girnarsoft.com.demoapp.fragment.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.communication.response.MatchesResponse;
import girnarsoft.com.demoapp.network.viewcallback.IMapper;
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

                    MatchModel matchModel = new MatchModel();
                    matchModel.setMatchStarted(Boolean.parseBoolean(matchesResponseItem.getMatchStarted()));
                    matchModel.setSquad(Boolean.parseBoolean(matchesResponseItem.getSquad()));
                    matchModel.setTeamOne(matchesResponseItem.getTeam1());
                    matchModel.setTeamtwo(matchesResponseItem.getTeam2());
                    matchModel.setTime(matchesResponseItem.getTime());
                    matchModel.setShortName(matchesResponseItem.getFormattedTeamName());
                    matchModel.setShortType(matchesResponseItem.getTypeShort());
                    matchModel.setTeamOneUrl(matchesResponseItem.getTeamOneUrl());
                    matchModel.setTeamTwoUrl(matchesResponseItem.getTeamOneUrl());
                    matchModel.setUniqueId(matchesResponseItem.getUniqueId());
                    matchModel.setSeriesName(matchesResponseItem.getTournamentName());
                    matchModel.setIsActive(matchesResponseItem.getIsActive());

                    matchModelList.add(matchModel);
                }
                matchViewModel.setMatchModelList(matchModelList);
            }
        }
        return matchViewModel;
    }
}
