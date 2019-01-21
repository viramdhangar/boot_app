package girnarsoft.com.demoapp.activity;

import android.databinding.ViewDataBinding;
import android.os.Bundle;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.ActivityMatchBinding;
import girnarsoft.com.demoapp.databinding.ActivityMatchLeagueBinding;
import girnarsoft.com.demoapp.fragment.HeadToHeadLeagueFragment;
import girnarsoft.com.demoapp.fragment.MatchLeagueFragment;
import girnarsoft.com.demoapp.fragment.MatchLeagueListFragment;
import girnarsoft.com.demoapp.fragment.MatchListFragment;
import girnarsoft.com.demoapp.fragment.MyLeagueFragment;
import girnarsoft.com.demoapp.fragment.MyTeamsFragment;
import girnarsoft.com.demoapp.fragment.NormalLeagueFragment;
import girnarsoft.com.demoapp.utils.NormalUtility;
import girnarsoft.com.demoapp.utils.bottomBar.BottomBar;

public class MatchLeagueActivity extends AbstractAppCompactActivity {

    private String TAG = "MatchLeagueActivity";
    private ActivityMatchLeagueBinding binding;
    private String matchId, title;
    //private MatchRecyclerAdapter adapter;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_match_league;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityMatchLeagueBinding)viewDataBinding;
        binding.setMatchleagueActivity(this);
        if(null != getIntent()){
            Bundle bundle  = getIntent().getExtras();
            matchId = bundle.getString("matchId");
            title = bundle.getString("title");
        }
        setUpBottomTabbar();
    }

    private void setUpBottomTabbar(){
        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId){
                case R.id.tab_leagues:{
                    MatchLeagueListFragment matchLeagueListFragment = new MatchLeagueListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("matchId", matchId);
                    matchLeagueListFragment.setArguments(bundle);
                    NormalUtility.replaceFragment(getAbstractActivity(),matchLeagueListFragment,MatchLeagueListFragment.TAG,R.id.matchLeagueContainer,false,false);
                    break;
                }
                case R.id.tab_myLeagues:{
                    NormalUtility.replaceFragment(getAbstractActivity(),new MyLeagueFragment(),MyLeagueFragment.TAG,R.id.matchLeagueContainer,false,false);
                    break;
                }
                case R.id.tab_myTeams:{
                    NormalUtility.replaceFragment(getAbstractActivity(),new MyTeamsFragment(),MyTeamsFragment.TAG,R.id.matchLeagueContainer,false,false);
                    break;
                }

            }

        });

        bottomBar.setOnTabReselectListener(tabId -> {
            //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
        });
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
//    private void fetchMatchesList(){
//        IMatchService service = getLocator().getService(IMatchService.class);
//        if (null != service) {
//            service.getMatchList(this, new AbstractViewCallback<MatchViewModel>() {
//
//                @Override
//                public void onSuccess(MatchViewModel matchViewModel) {
//                    setAdapter(matchViewModel.getMatchModelList());
//                }
//
//                @Override
//                public boolean isLive() {
//                    return false;
//                }
//            });
//        }
//    }

}
