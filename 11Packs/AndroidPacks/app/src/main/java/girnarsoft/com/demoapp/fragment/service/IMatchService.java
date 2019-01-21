package girnarsoft.com.demoapp.fragment.service;

import android.content.Context;


import girnarsoft.com.demoapp.fragment.viewModel.LeagueViewModel;
import girnarsoft.com.demoapp.fragment.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.viewcallback.IViewCallback;

public interface IMatchService {
    void getMatchList(Context pContext, int matchType, IViewCallback<MatchViewModel> viewCallback);
    void getMatchLeagueList(Context pContext, String matchId, int matchType, IViewCallback<LeagueViewModel> viewCallback);
}
