package girnarsoft.com.demoapp.network;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import girnarsoft.com.demoapp.utils.NormalUtility;
import girnarsoft.com.demoapp.utils.ValidationUtils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static final String TAG = ServiceGenerator.class.getSimpleName();
    private final String baseUrl;
    private final Map headerMap;
    private final int defaultTimeOutInMinute;
    private final int maxRetryCount;
    private final int maxCacheAgeInMinute;
    private final int maxStaleInDay;
    private final int sizeOfCacheInMB;
    private final boolean isCacheEnable;
    private final boolean isShowCacheNotification;
    private final boolean isDbBasedInterceptor;
    private final Context context;
    private final String TEMP_URL = "http://www.emptyurl.com";
    private Retrofit retrofit;

    private ServiceGenerator(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.headerMap = builder.headerMap;
        this.defaultTimeOutInMinute = builder.defaultTimeOutInMinute;
        this.maxRetryCount = builder.maxRetryCount;
        this.maxCacheAgeInMinute = builder.maxCacheAgeInMinute;
        this.sizeOfCacheInMB = builder.sizeOfCacheInMB;
        this.isCacheEnable = builder.isCacheEnable;
        this.isShowCacheNotification = builder.isShowCacheNotification;
        this.isDbBasedInterceptor = builder.isDbBasedInterceptor;
        this.maxStaleInDay = builder.maxStaleInDay;
        this.context = builder.context;
        setupClient();
    }


    /**
     * Create service s.
     *
     * @param <S>          the type parameter
     * @param serviceClass the service class
     * @return s s
     */
    public <S> S createService(Class<S> serviceClass) {
        if (null == retrofit) {
            return null;
        }
        return retrofit.create(serviceClass);
    }

    /**
     *
     */
    private void setupClient() {
        RetrofitClientBuilder retrofitClientBuilder = new RetrofitClientBuilder();
        HttpInterceptor interceptor = new HttpInterceptor(maxRetryCount);
        interceptor.setNetworkAuthorizationHeader(headerMap);
        interceptor.setLevel(HttpInterceptor.Level.BODY);
        if (isCacheEnable) {
            try {
                File httpCacheDirectory = new File(context.getExternalCacheDir(), ".responses");
                Cache cache = new Cache(httpCacheDirectory, sizeOfCacheInMB * 1024 * 1024);
                retrofitClientBuilder.setCache(cache);
            } catch (Exception e) {
                NormalUtility.showException(TAG, e);
            }
        }
        retrofitClientBuilder.setInterceptors(interceptor);

        retrofitClientBuilder.setRetryOnConnectionFailure(true);
        retrofitClientBuilder.setConnectionTimeout(defaultTimeOutInMinute, TimeUnit.MINUTES);
        retrofitClientBuilder.setReadTimeout(defaultTimeOutInMinute, TimeUnit.MINUTES);
        retrofitClientBuilder.setWriteTimeout(defaultTimeOutInMinute, TimeUnit.MINUTES);

        OkHttpClient okHttpClient = retrofitClientBuilder.build();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new AnnotationExclusionStrategy())
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder().baseUrl(ValidationUtils.isValidUrl(baseUrl) ? baseUrl : TEMP_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(new ToStringConverterFactory())
                .client(okHttpClient).build();
    }

    public static class Builder {
        private final Context context;
        private final String baseUrl;
        private Map headerMap;

        private int defaultTimeOutInMinute = 2;
        private int maxRetryCount = 3;
        private int maxCacheAgeInMinute = 5;
        private int maxStaleInDay = 28;
        private int sizeOfCacheInMB = 20;
        private boolean isCacheEnable = false;
        private boolean isShowCacheNotification = false;
        private boolean isDbBasedInterceptor = true;

        public Builder(Context context, String baseUrl) {
            this.context = context;
            this.baseUrl = baseUrl;
        }

        public Builder setDefaultTimeOutInMinute(int defaultTimeOutInMinute) {
            this.defaultTimeOutInMinute = defaultTimeOutInMinute;
            return this;
        }

        public Builder setMaxRetryCount(int maxRetryCount) {
            this.maxRetryCount = maxRetryCount;
            return this;
        }

        public Builder setMaxCacheAgeInMinute(int maxCacheAgeInMinute) {
            this.maxCacheAgeInMinute = maxCacheAgeInMinute;
            return this;
        }

        public Builder setMaxStaleInDay(int maxStaleInDay) {
            this.maxStaleInDay = maxStaleInDay;
            return this;
        }

        public Builder setSizeOfCacheInMB(int sizeOfCacheInMB) {
            this.sizeOfCacheInMB = sizeOfCacheInMB;
            return this;
        }

        public Builder setCacheEnable(boolean cacheEnable) {
            isCacheEnable = cacheEnable;
            return this;
        }

        public Builder setAuthorizationHeader(Map headerMap) {
            this.headerMap = headerMap;
            return this;
        }

        public Builder setShowCacheNotification(boolean showCacheNotification) {
            isShowCacheNotification = showCacheNotification;
            return this;
        }

        public Builder setDbBasedInterceptor(boolean dbBasedCache) {
            isDbBasedInterceptor = dbBasedCache;
            return this;
        }

        public Builder setFileBasedInterceptor(boolean dbBasedCache) {
            isDbBasedInterceptor = !dbBasedCache;
            return this;
        }


        public ServiceGenerator build() {
            ServiceGenerator serviceGenerator = new ServiceGenerator(this);
            if (TextUtils.isEmpty(serviceGenerator.baseUrl)) {
                NormalUtility.showException(TAG, new NullPointerException("Base url empty"));
            }
            return serviceGenerator;
        }

    }
}