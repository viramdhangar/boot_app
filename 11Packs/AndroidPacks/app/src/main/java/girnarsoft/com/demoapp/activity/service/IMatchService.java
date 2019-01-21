package girnarsoft.com.demoapp.activity.service;

import android.content.Context;


import girnarsoft.com.demoapp.activity.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.viewcallback.IViewCallback;

public interface IMatchService {
    void getMatchList(Context pContext, int matchType, IViewCallback<MatchViewModel> viewCallback);
}
