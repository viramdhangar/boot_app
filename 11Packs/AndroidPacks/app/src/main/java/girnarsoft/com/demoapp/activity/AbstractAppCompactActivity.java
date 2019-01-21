package girnarsoft.com.demoapp.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import girnarsoft.com.demoapp.application.BaseApplication;
import girnarsoft.com.demoapp.fragment.SavedInstanceFragment;
import girnarsoft.com.demoapp.network.locator.IServiceLocator;
import girnarsoft.com.demoapp.utils.HideUtility;
import girnarsoft.com.demoapp.utils.LoadingDialog;
import girnarsoft.com.demoapp.utils.NormalUtility;


public abstract class AbstractAppCompactActivity extends AppCompatActivity {

    private static AbstractAppCompactActivity baseActivity;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ViewDataBinding viewDataBinding;

    public static AbstractAppCompactActivity getAbstractActivity() {
        return AbstractAppCompactActivity.baseActivity;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        SavedInstanceFragment.getInstance(getFragmentManager()).pushData((Bundle) outState.clone());
        outState.clear(); // We don't want a TransactionTooLargeException, so we handle things via the SavedInstanceFragment
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        try {
            super.onRestoreInstanceState(SavedInstanceFragment.getInstance(getFragmentManager()).popData());
        } catch (Exception e) {
            NormalUtility.showException(e);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(SavedInstanceFragment.getInstance(getFragmentManager()).popData());
        baseActivity = this;
        onPreSetContentView();
        viewDataBinding = DataBindingUtil.setContentView(this, getActivityLayout());
        HideUtility.init(this);
        onCreate(viewDataBinding);
        onActivityReady();
    }


    protected void onPreSetContentView() {
        //To minimize the overdraw of the screen
        getWindow().setBackgroundDrawable(null);
    }

    public void hideKeyboard() {
        HideUtility.hideSoftInput(this);
    }

    protected void onActivityReady() {
        // do nothing
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment baseFragment = NormalUtility.getActiveFragment(getAbstractActivity());
        if (null != baseFragment) {
            baseFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment baseFragment = NormalUtility.getActiveFragment(getAbstractActivity());
        if (null != baseFragment) {
            baseFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isChangingConfigurations()) {
            attachBaseContext(getBaseContext());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        LoadingDialog.dismissDialog();

    }
    public IServiceLocator getLocator() {
        if (getApplication() instanceof BaseApplication) {
            return ((BaseApplication) getApplication()).getLocator();
        }
        throw new UnsupportedOperationException("Your Application class must extends Abstract Application class.");
    }

    protected abstract int getActivityLayout();

    public abstract String getScreenName();

    // public abstract Toolbar getToolBar();

    //public abstract void setToolBarTitle(String title);

    //public abstract void setToolBarIcon(int drawable);

    protected abstract void onCreate(ViewDataBinding viewDataBinding);

}