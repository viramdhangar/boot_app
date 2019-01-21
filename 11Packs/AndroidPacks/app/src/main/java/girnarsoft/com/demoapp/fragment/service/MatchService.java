package girnarsoft.com.demoapp.fragment.service;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.util.List;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.fragment.mapper.LeagueMapper;
import girnarsoft.com.demoapp.fragment.mapper.MatchMapper;
import girnarsoft.com.demoapp.fragment.viewModel.LeagueViewModel;
import girnarsoft.com.demoapp.fragment.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.ApiService;
import girnarsoft.com.demoapp.network.BaseNetworkHeaderObservable;
import girnarsoft.com.demoapp.network.ServiceGenerator;
import girnarsoft.com.demoapp.network.communication.response.LeaguesResponse;
import girnarsoft.com.demoapp.network.communication.response.MatchesResponse;
import girnarsoft.com.demoapp.network.viewcallback.IViewCallback;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MatchService implements IMatchService {
    ServiceGenerator serviceGenerator;
    public MatchService(Context context) {
        serviceGenerator = new ServiceGenerator.Builder(context, context.getResources().getString(R.string.baseUrl)) //BaseApplication.getPreferenceManager().getBaseUrl()
                .build();
//        .setCacheEnable(true)
//                .setAuthorizationHeader(CommonFrameworkUtil.getRequestHeaderParam(false))
    }
    @Override
    public void getMatchList(Context pContext, int matchType, IViewCallback<MatchViewModel> viewCallback) {
        ApiService apiService = serviceGenerator.createService(ApiService.class);

        Observable<Result<List<MatchesResponse.MatchesResponseItem>>> matchObservable = apiService.getMatchesList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
        matchObservable.subscribe(new BaseNetworkHeaderObservable<List<MatchesResponse.MatchesResponseItem>>() {
            @Override
            public void success(Response response, List<MatchesResponse.MatchesResponseItem> loginResponse) {
                if (loginResponse != null) {
                    MatchViewModel viewModel = new MatchMapper().toViewModel(loginResponse, matchType);
                    viewCallback.onSuccess(viewModel);
                }
            }

            @Override
            public void failure(Throwable t) {
                viewCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getMatchLeagueList(Context pContext, String matchId, int matchType, IViewCallback<LeagueViewModel> viewCallback) {
        ApiService apiService = serviceGenerator.createService(ApiService.class);

        Observable<Result<List<LeaguesResponse.LeaguesResponseItem>>> matchObservable = apiService.getMatchLeagueList(matchId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
        matchObservable.subscribe(new BaseNetworkHeaderObservable<List<LeaguesResponse.LeaguesResponseItem>>() {
            @Override
            public void success(Response response, List<LeaguesResponse.LeaguesResponseItem> loginResponse) {
                if (loginResponse != null) {
                    LeagueViewModel viewModel = new LeagueMapper().toViewModel(loginResponse, matchType);
                    viewCallback.onSuccess(viewModel);
                }
            }

            @Override
            public void failure(Throwable t) {
                viewCallback.onFailure(t);
            }
        });
    }
}
