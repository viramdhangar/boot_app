package girnarsoft.com.demoapp.fragment;

import android.databinding.ViewDataBinding;

import java.util.List;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.FragmentMyLeagueBinding;
import girnarsoft.com.demoapp.databinding.FragmentNormalLeagueBinding;
import girnarsoft.com.demoapp.fragment.adapter.LeagueRecyclerAdapter;
import girnarsoft.com.demoapp.fragment.model.LeagueModel;
import girnarsoft.com.demoapp.fragment.model.MatchModel;
import girnarsoft.com.demoapp.fragment.service.IMatchService;
import girnarsoft.com.demoapp.fragment.viewModel.LeagueViewModel;
import girnarsoft.com.demoapp.network.viewcallback.AbstractViewCallback;
import girnarsoft.com.demoapp.utils.CommonUtility;
import girnarsoft.com.demoapp.utils.PacksConstants;
import girnarsoft.com.demoapp.utils.ValidationUtils;


public class MyLeagueFragment extends AbstractBaseFragment {
    public static final String TAG = "MyLeagueFragment";
    private FragmentMyLeagueBinding binding;
    private LeagueRecyclerAdapter adapter;
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_my_league;
    }

    @Override
    protected void createView(ViewDataBinding viewDataBinding) {
        binding = (FragmentMyLeagueBinding)viewDataBinding;
        binding.setMyLeagueFragment(this);
    }

    @Override
    protected void onFragmentReady() {
        super.onFragmentReady();
        if(null != getArguments()){
            fetchMatcheLeaguesList(getArguments().getString("mathcId"));
        }

    }

    private void setAdapter(List<LeagueModel> matchModelList) {
        if (ValidationUtils.isListNotEmpty(matchModelList)) {
            adapter = new LeagueRecyclerAdapter(getContext(), matchModelList);
            CommonUtility.setRecyclerBasicProperties(getContext(), binding.recyclerViewMatchList, false);
            binding.recyclerViewMatchList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener((view, viewModel) -> {
                MatchModel myTaskModel = (MatchModel) viewModel;
            });

        } else {
            // No data Available
        }
    }
    private void fetchMatcheLeaguesList(String matchId){
        IMatchService service = getAbstractBaseActivity().getLocator().getService(IMatchService.class);
        if (null != service) {
            service.getMatchLeagueList(getContext(), matchId, PacksConstants.LeagueType.NORMAL_LEAGUE, new AbstractViewCallback<LeagueViewModel>() {

                @Override
                public void onSuccess(LeagueViewModel matchViewModel) {
                    setAdapter(matchViewModel.getLeagueModelList());
                }

                @Override
                public boolean isLive() {
                    return false;
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

}
