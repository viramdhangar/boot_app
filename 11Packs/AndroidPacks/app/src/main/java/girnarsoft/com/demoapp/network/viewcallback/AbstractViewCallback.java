package girnarsoft.com.demoapp.network.viewcallback;


import girnarsoft.com.demoapp.utils.NormalUtility;

public abstract class AbstractViewCallback<T> extends IViewCallback<T> {

    @Override
    public void onFailure(Throwable e) {
        NormalUtility.showException(e);

    }
}
