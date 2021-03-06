package girnarsoft.com.demoapp.fragment;

import android.databinding.ViewDataBinding;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.databinding.FragmentMatchLeagueBinding;
import girnarsoft.com.demoapp.databinding.FragmentMatchSquadBinding;
import girnarsoft.com.demoapp.fragment.adapter.AbstractFragmentStatePagerAdapter;
import girnarsoft.com.demoapp.listner.AbstractTabSelectedListener;
import girnarsoft.com.demoapp.utils.CommonUtility;


public class MatchSquadFragment extends AbstractBaseFragment {
    public static final String TAG = "MatchSquadFragment";
    private FragmentMatchSquadBinding binding;
    public static ViewPager viewPager;

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_match_league;
    }

    @Override
    protected void createView(ViewDataBinding viewDataBinding) {
        binding = (FragmentMatchSquadBinding)viewDataBinding;
        binding.setMatchSquadFragment(this);
        viewPager = binding.vpFilter;
    }

    @Override
    protected void onFragmentReady() {
        super.onFragmentReady();
        setUpViewPager();
    }

    private void setUpViewPager(){
        CommonUtility.setTab(binding.tabFilter, getResources().getStringArray(R.array.match_type_array));
        binding.vpFilter.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabFilter));
        binding.vpFilter.setAdapter(new AbstractFragmentStatePagerAdapter(getChildFragmentManager(), getFragmentList()));
        binding.tabFilter.addOnTabSelectedListener(new AbstractTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                binding.vpFilter.setCurrentItem(tab.getPosition());
            }
        });
    }
    private List<Fragment> getFragmentList() {
        List<Fragment> filterFragmentList = new ArrayList<>();
        filterFragmentList.add(new UpcomingMatchFragment());
        filterFragmentList.add(new LiveMatchFragment());
        filterFragmentList.add(new CompletedMatchFragment());
        return filterFragmentList;
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
