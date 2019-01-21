package girnarsoft.com.demoapp.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.ActivityLeagueJoinBinding;
import girnarsoft.com.demoapp.databinding.ActivityPayoutLeagueBinding;

public class LeagueJoinActivity extends AbstractAppCompactActivity {

    private String TAG = "LeagueJoinActivity";
    private ActivityLeagueJoinBinding binding;
    private String matchId, title;
    //private MatchRecyclerAdapter adapter;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_league_join;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityLeagueJoinBinding)viewDataBinding;
        binding.setLeagueJoinActivity(this);
        if(null != getIntent()){
            Bundle bundle  = getIntent().getExtras();
            matchId = bundle.getString("matchId");
            title = bundle.getString("title");
        }
    }


    @Override
    protected void onActivityReady() {
        super.onActivityReady();
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(title);
            }
        }
        //fetchMatchesList();
    }

    //    private void setAdapter(List<MatchModel> matchModelList) {
//        if (ValidationUtils.isListNotEmpty(matchModelList)) {
//            adapter = new MatchRecyclerAdapter(this, matchModelList);
//            CommonUtility.setRecyclerBasicProperties(this, binding.recyclerViewMatchList, false);
//            binding.recyclerViewMatchList.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//            adapter.setOnItemClickListener((view, viewModel) -> {
//                MatchModel myTaskModel = (MatchModel) viewModel;
//            });
//
//        } else {
//           // No data Available
//        }
//    }

}
