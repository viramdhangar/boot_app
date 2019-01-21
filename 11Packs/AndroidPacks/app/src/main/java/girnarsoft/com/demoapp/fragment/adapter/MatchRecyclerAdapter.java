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
import girnarsoft.com.demoapp.fragment.model.MatchModel;
import girnarsoft.com.demoapp.databinding.MatchRowBinding;
import girnarsoft.com.demoapp.utils.NormalUtility;

public class MatchRecyclerAdapter extends AbstractRecyclerAdapter<MatchRecyclerAdapter.MatchViewHolder> {

    public List<MatchModel> myTaskModelList;
    private Context mContext;

    public MatchRecyclerAdapter(Context context, List<MatchModel> myTaskModelList) {
        super(myTaskModelList);
        this.mContext = context;
        this.myTaskModelList = myTaskModelList;
    }

    @Override
    public MatchViewHolder setViewHolder(ViewGroup viewGroup, int viewType) {
        MatchRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.match_row, viewGroup, false);
        return new MatchViewHolder(binding, getOnItemClickListener());
    }

    @Override
    public void onBindData(MatchViewHolder matchViewHolder, Object val) {
        matchViewHolder.bindView(mContext, (MatchModel) val);
    }

    class MatchViewHolder extends RecyclerView.ViewHolder{

        private MatchRowBinding binding;
        private MatchModel matchModel;
        public MatchViewHolder(MatchRowBinding viewDataBinding, final OnItemViewClickListener holderClick) {
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

        public void bindView(Context mContext, final MatchModel matchModel) {
            this.matchModel = matchModel;
            if (matchModel == null) {
                NormalUtility.showLog("Holder", "Trying to work on a null object ,returning.");
                return;
            }
//            binding.ivCallBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (getOnItemClickListener() != null) {
//                        getOnItemClickListener().onItemViewClick(view, myTaskModel);
//                    }
//                }
//            });

            binding.setMatchModel(matchModel);
         }
    }

}
