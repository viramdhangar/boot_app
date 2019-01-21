package girnarsoft.com.demoapp.fragment;

import android.databinding.ViewDataBinding;
import android.view.View;

import java.util.List;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.FragmentHeadToHeadLeagueBinding;
import girnarsoft.com.demoapp.databinding.FragmentNormalLeagueBinding;
import girnarsoft.com.demoapp.fragment.adapter.LeagueRecyclerAdapter;
import girnarsoft.com.demoapp.fragment.adapter.MatchRecyclerAdapter;
import girnarsoft.com.demoapp.fragment.model.LeagueModel;
import girnarsoft.com.demoapp.fragment.model.MatchModel;
import girnarsoft.com.demoapp.fragment.service.IMatchService;
import girnarsoft.com.demoapp.fragment.viewModel.LeagueViewModel;
import girnarsoft.com.demoapp.fragment.viewModel.MatchViewModel;
import girnarsoft.com.demoapp.network.viewcallback.AbstractViewCallback;
import girnarsoft.com.demoapp.utils.CommonUtility;
import girnarsoft.com.demoapp.utils.PacksConstants;
import girnarsoft.com.demoapp.utils.ValidationUtils;


public class HeadToHeadLeagueFragment extends AbstractBaseFragment {
    public static final String TAG = "NormalLeagueFragment";
    private FragmentHeadToHeadLeagueBinding binding;
    private LeagueRecyclerAdapter adapter;
    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_head_to_head_league;
    }

    @Override
    protected void createView(ViewDataBinding viewDataBinding) {
        binding = (FragmentHeadToHeadLeagueBinding)viewDataBinding;
        binding.setHthLeagueFragment(this);
        reloadDataClickListenr();
    }

    private void reloadDataClickListenr(){
        binding.lytNoData.lblReload.setOnClickListener(view -> {
            fetchMatcheLeaguesList(getArguments().getString("matchId"));
        });
    }
    @Override
    protected void onFragmentReady() {
        super.onFragmentReady();
        if(null != getArguments()){
            fetchMatcheLeaguesList(getArguments().getString("matchId"));
        }

    }

    private void setAdapter(List<LeagueModel> matchModelList) {
        if (ValidationUtils.isListNotEmpty(matchModelList)) {
            binding.lytNoData.rlyNoData.setVisibility(View.GONE);
            adapter = new LeagueRecyclerAdapter(getContext(), matchModelList);
            CommonUtility.setRecyclerBasicProperties(getContext(), binding.recyclerViewMatchList, false);
            binding.recyclerViewMatchList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            adapter.setOnItemClickListener((view, viewModel) -> {
                MatchModel myTaskModel = (MatchModel) viewModel;
            });

        } else {
            // No data Available
            binding.lytNoData.rlyNoData.setVisibility(View.VISIBLE);
        }
    }
    private void fetchMatcheLeaguesList(String matchId){
        IMatchService service = getAbstractBaseActivity().getLocator().getService(IMatchService.class);
        if (null != service) {
            service.getMatchLeagueList(getContext(), matchId, PacksConstants.LeagueType.HEADTOHEAD_LEAGUE, new AbstractViewCallback<LeagueViewModel>() {

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
