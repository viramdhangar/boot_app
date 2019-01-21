package girnarsoft.com.demoapp.fragment.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import girnarsoft.com.demoapp.R;
import girnarsoft.com.demoapp.activity.adapter.AbstractRecyclerAdapter;
import girnarsoft.com.demoapp.databinding.AdapterLeagueRowBinding;
import girnarsoft.com.demoapp.databinding.MatchRowBinding;
import girnarsoft.com.demoapp.fragment.model.LeagueModel;
import girnarsoft.com.demoapp.fragment.model.MatchModel;
import girnarsoft.com.demoapp.utils.NormalUtility;

public class LeagueRecyclerAdapter extends AbstractRecyclerAdapter<LeagueRecyclerAdapter.LeagueViewHolder> {

    public List<LeagueModel> myTaskModelList;
    private Context mContext;

    public LeagueRecyclerAdapter(Context context, List<LeagueModel> myTaskModelList) {
        super(myTaskModelList);
        this.mContext = context;
        this.myTaskModelList = myTaskModelList;
    }

    @Override
    public LeagueViewHolder setViewHolder(ViewGroup viewGroup, int viewType) {
        AdapterLeagueRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.adapter_league_row, viewGroup, false);
        return new LeagueViewHolder(binding, getOnItemClickListener());
    }

    @Override
    public void onBindData(LeagueViewHolder matchViewHolder, Object val) {
        matchViewHolder.bindView(mContext, (LeagueModel) val);
    }

    class LeagueViewHolder extends RecyclerView.ViewHolder{

        private AdapterLeagueRowBinding binding;
        private LeagueModel matchModel;
        public LeagueViewHolder(AdapterLeagueRowBinding viewDataBinding, final OnItemViewClickListener holderClick) {
            super(viewDataBinding.getRoot());
            viewDataBinding.executePendingBindings();
            binding = viewDataBinding;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null == holderClick) {
                        NormalUtility.showLog("Holder", "Trying to work on a null object ,returning.");
                        return;
                    }
                    holderClick.onItemViewClick(v, matchModel);

                }
            });

        }

        public void bindView(Context mContext, final LeagueModel matchModel) {
            this.matchModel = matchModel;
            if (matchModel == null) {
                NormalUtility.showLog("Holder", "Trying to work on a null object ,returning.");
                return;
            }
            binding.btnJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnItemClickListener() != null) {
                        getOnItemClickListener().onItemViewClick(view, matchModel);
                    }
                }
            });
            binding.rlyPayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getOnItemClickListener() != null) {
                        getOnItemClickListener().onItemViewClick(view, matchModel);
                    }
                }
            });
            binding.setMatchleagueModel(matchModel);
         }
    }

}
