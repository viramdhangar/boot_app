package girnarsoft.com.demoapp.network.servicelocator;

import android.content.Context;

import girnarsoft.com.demoapp.network.ApiServiceFactory;
import girnarsoft.com.demoapp.network.locator.IServiceLocator;


public class ServiceLocator implements IServiceLocator {
    private Context mContext;

    public ServiceLocator(Context context) {
        mContext = context;
    }

    @Override
    public <T> T getService(Class<T> serviceClass) {
        return ApiServiceFactory.getInstance().getService(mContext, serviceClass);
    }

}