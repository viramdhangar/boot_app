package girnarsoft.com.demoapp.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import girnarsoft.com.demoapp.network.locator.IServiceLocator;
import girnarsoft.com.demoapp.network.servicelocator.ServiceLocator;

public class BaseApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }
    @Override
    public void onCreate() {
        super.onCreate();

    }

    public IServiceLocator getLocator(){
        return new ServiceLocator(getApplicationContext());
    }

}
