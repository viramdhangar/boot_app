package girnarsoft.com.demoapp.network;

import com.jakewharton.retrofit2.adapter.rxjava2.Result;

import java.util.List;

import girnarsoft.com.demoapp.network.communication.response.LeaguesResponse;
import girnarsoft.com.demoapp.network.communication.response.MatchesResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET(ApiUtil.MATCH_V1_API + "/matches")
    Observable<Result<List<MatchesResponse.MatchesResponseItem>>> getMatchesList();

    @GET(ApiUtil.MATCH_V1_API + "/squad/{id}")
    Observable<MatchesResponse> getSquadList(@Path("id") String matchId);

    @GET(ApiUtil.MATCH_V1_API + "/leagues/{id}")
    Observable<Result<List<LeaguesResponse.LeaguesResponseItem>>> getMatchLeagueList(@Path("id") String matchId);

}
