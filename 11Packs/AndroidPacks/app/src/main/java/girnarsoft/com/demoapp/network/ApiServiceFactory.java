package girnarsoft.com.demoapp.network;

import android.content.Context;

import girnarsoft.com.demoapp.fragment.service.MatchService;
import girnarsoft.com.demoapp.network.locator.IServiceFactory;

public class ApiServiceFactory implements IServiceFactory {

    private static ApiServiceFactory apiServiceFactory;

    public static synchronized ApiServiceFactory getInstance() {
        if (null == apiServiceFactory) {
            apiServiceFactory = new ApiServiceFactory();
        }
        return apiServiceFactory;
    }
    @Override
    public <T> T getService(Context context, Class<T> serviceClass) {
        String serviceName = serviceClass.getSimpleName();
        Object service = null;
        switch (serviceName) {
            case "IMatchService":
                service = new MatchService(context);
                break;
        }
        return serviceClass.cast(service);
    }
}
