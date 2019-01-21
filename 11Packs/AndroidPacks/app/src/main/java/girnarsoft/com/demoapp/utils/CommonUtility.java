package girnarsoft.com.demoapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import girnarsoft.com.demoapp.R;

public class CommonUtility {
    /**
     * Sets recycler basic properties.
     *
     * @param context      the context
     * @param recyclerView the recycler view
     */
    public static void setRecyclerBasicProperties(Context context, RecyclerView recyclerView, boolean isItemDecoration) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        if (isItemDecoration) {
            recyclerView.addItemDecoration(new SpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.dimen_seven), SpaceItemDecoration.Spacing.SPACE_BOTTOM));
        }

    }

    public static void setRecyclerGridProperties(Context context, RecyclerView recyclerView, boolean isItemDecoration) {
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        if (isItemDecoration) {
            recyclerView.addItemDecoration(new SpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.dimen_one), SpaceItemDecoration.Spacing.DEFAULT));
        }

    }

    public static void setRecyclerHorizontalProperties(Context context, RecyclerView recyclerView, boolean isItemDecoration) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        if (isItemDecoration) {
            recyclerView.addItemDecoration(new SpaceItemDecoration(context.getResources().getDimensionPixelSize(R.dimen.dimen_one), SpaceItemDecoration.Spacing.DEFAULT));
        }
    }
    /**
     * Sets tab.
     *
     * @param tabLayout the tab layout
     * @param tabName   the tab name
     * @return
     */
    @NonNull
    public static void setTab(TabLayout tabLayout, String[] tabName) {
        for (String tab : tabName) {
            tabLayout.addTab(tabLayout.newTab().setText(tab));
        }
    }
}
