package girnarsoft.com.demoapp.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.ActivityMatchLeagueBinding;
import girnarsoft.com.demoapp.databinding.ActivityPayoutLeagueBinding;
import girnarsoft.com.demoapp.fragment.MatchLeagueListFragment;
import girnarsoft.com.demoapp.fragment.MyLeagueFragment;
import girnarsoft.com.demoapp.fragment.MyTeamsFragment;
import girnarsoft.com.demoapp.utils.NormalUtility;
import girnarsoft.com.demoapp.utils.bottomBar.BottomBar;

public class LeaguePayoutActivity extends AbstractAppCompactActivity {

    private String TAG = "LeaguePayoutActivity";
    private ActivityPayoutLeagueBinding binding;
    private String matchId, title;
    //private MatchRecyclerAdapter adapter;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_payout_league;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityPayoutLeagueBinding)viewDataBinding;
        binding.setPayoutleagueActivity(this);
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
