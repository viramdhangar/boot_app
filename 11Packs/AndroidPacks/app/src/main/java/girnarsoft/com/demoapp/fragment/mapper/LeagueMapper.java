package girnarsoft.com.demoapp.fragment.mapper;

import java.util.ArrayList;
import java.util.List;

import girnarsoft.com.demoapp.fragment.model.LeagueModel;
import girnarsoft.com.demoapp.fragment.model.MatchModel;
import girnarsoft.com.demoapp.fragment.viewModel.LeagueViewModel;
import girnarsoft.com.demoapp.fragment.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.communication.response.LeaguesResponse;
import girnarsoft.com.demoapp.network.communication.response.MatchesResponse;
import girnarsoft.com.demoapp.network.viewcallback.IMapper;
import girnarsoft.com.demoapp.utils.PacksConstants;
import girnarsoft.com.demoapp.utils.ValidationUtils;

public class LeagueMapper implements IMapper<List<LeaguesResponse.LeaguesResponseItem>, LeagueViewModel> {
    @Override
    public LeagueViewModel toViewModel(List<LeaguesResponse.LeaguesResponseItem> matchesResponse, int matchType) {
        LeagueViewModel leagueViewModel = new LeagueViewModel();
        if(ValidationUtils.isValidResponse(matchesResponse)){
            if(ValidationUtils.isListNotEmpty(matchesResponse)){
                List<LeaguesResponse.LeaguesResponseItem> matchesResponseItemList = matchesResponse;
                List<LeagueModel> matchModelList = new ArrayList<>();
                for (LeaguesResponse.LeaguesResponseItem leagueResponseItem :matchesResponseItemList) {
                    switch (matchType){
                        case PacksConstants.LeagueType.NORMAL_LEAGUE:
                            matchModelList.add(getLeagueModel(leagueResponseItem));
                            break;
                        case PacksConstants.LeagueType.HEADTOHEAD_LEAGUE:
                            if(leagueResponseItem.getSize() == PacksConstants.LeagueType.HEADTOHEAD_LEAGUE){
                                matchModelList.add(getLeagueModel(leagueResponseItem));
                            }
                            break;
                        case PacksConstants.LeagueType.TRIPLE_LEAGUE:
                            if(leagueResponseItem.getSize() == PacksConstants.LeagueType.TRIPLE_LEAGUE){
                                matchModelList.add(getLeagueModel(leagueResponseItem));
                            }
                            break;
                            default:
                                matchModelList.add(getLeagueModel(leagueResponseItem));
                                break;
                    }
                }
                leagueViewModel.setLeagueModelList(matchModelList);
            }
        }
        return leagueViewModel;
    }

    private LeagueModel getLeagueModel(LeaguesResponse.LeaguesResponseItem leagueResponseItem){
        LeagueModel leagueModel = new LeagueModel();
        leagueModel.setLeagueId(leagueResponseItem.getId());
        leagueModel.setLeagueName(leagueResponseItem.getLeague());
        leagueModel.setWinners(leagueResponseItem.getWinners());
        leagueModel.setWinningAmount(leagueResponseItem.getWinningAmount());
        leagueModel.setTeamSize(leagueResponseItem.getSize());
        leagueModel.setJoinedTeam(leagueResponseItem.getJoinedTeam());
        leagueModel.setBreakupId(leagueResponseItem.getBreakupId());
        leagueModel.setEntryFee(leagueResponseItem.getEntryFee());
        leagueModel.setMatchId(leagueResponseItem.getMatchId());
        return leagueModel;
    }
}
