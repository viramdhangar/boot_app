package girnarsoft.com.demoapp.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import girnarsoft.com.demoapp.activity.AbstractAppCompactActivity;
import girnarsoft.com.demoapp.utils.LoadingDialog;

/**
 * Created by Deepak .
 */
public abstract class AbstractBaseFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewDataBinding viewDataBinding = DataBindingUtil.bind(inflater.inflate(getFragmentLayout(), container, false));
        setHasOptionsMenu(true);
        createView(viewDataBinding);
        onFragmentReady();
        return viewDataBinding.getRoot();
    }

    /**
     * Gets fragment layout.
     *
     * @return the fragment layout
     */
    protected abstract int getFragmentLayout();

    protected void onFragmentReady() {
        // do nothing
    }


    @Override
    public void onStop() {
        super.onStop();
        if (!(null != getActivity() && isAdded()))
            return;
        LoadingDialog.dismissDialog();

    }

    /**
     * Create view.
     *
     * @param viewDataBinding the view
     */
    protected abstract void createView(ViewDataBinding viewDataBinding);

    public AbstractAppCompactActivity getAbstractBaseActivity() {
        return AbstractAppCompactActivity.getAbstractActivity();
    }

    public String getBaseString(int id) {
        return null != getContext() ? getContext().getResources().getString(id) : null;
    }

}