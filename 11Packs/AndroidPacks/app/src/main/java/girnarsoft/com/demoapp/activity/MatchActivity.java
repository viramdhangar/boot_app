package girnarsoft.com.demoapp.activity;

import android.databinding.ViewDataBinding;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.ActivityMatchBinding;
import girnarsoft.com.demoapp.fragment.MatchLeagueFragment;
import girnarsoft.com.demoapp.fragment.MatchListFragment;
import girnarsoft.com.demoapp.utils.NormalUtility;
import girnarsoft.com.demoapp.utils.bottomBar.BottomBar;

public class MatchActivity extends AbstractAppCompactActivity {

    private String TAG = "MatchActivity";
    private ActivityMatchBinding binding;
    //private MatchRecyclerAdapter adapter;

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_match;
    }

    @Override
    public String getScreenName() {
        return TAG;
    }

    @Override
    protected void onCreate(ViewDataBinding viewDataBinding) {
        binding = (ActivityMatchBinding)viewDataBinding;
        binding.setMatchActivity(this);
        setUpBottomTabbar();
    }

    private void setUpBottomTabbar(){
        BottomBar bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(tabId -> {
            switch (tabId){
                case R.id.tab_favorites:{
                    NormalUtility.replaceFragment(getAbstractActivity(),new MatchListFragment(),MatchListFragment.TAG,R.id.matchContainer,false,false);
                    break;
                }
                case R.id.tab_nearby:{
                    NormalUtility.replaceFragment(getAbstractActivity(),new MatchLeagueFragment(),MatchLeagueFragment.TAG,R.id.matchContainer,false,false);
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
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Matches");
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
