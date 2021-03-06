package com.cjym.yunmabao.http;


import com.cjym.yunmabao.application.MainApplication;
import com.cjym.yunmabao.config.AppConfig;
import com.cjym.yunmabao.presenter.login.LoginRequest;
import com.cjym.yunmabao.presenter.login.LoginResponse;
import com.cjym.yunmabao.presenter.shopinfo.ShopInfoRequest;
import com.cjym.yunmabao.presenter.shopinfo.ShopinfoResponse;
import com.cjym.yunmabao.utils.StringUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * 网络访问管理类
 */
public class RetrofitManager {

    private static final String TAG = "RetrofitManager";

    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_LONG;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
    private static OkHttpClient mOkHttpClient;
    private final NetworkService mNetworkService;

    public static RetrofitManager builder() {
        return new RetrofitManager();
    }

    private RetrofitManager() {

        initOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        mNetworkService = retrofit.create(NetworkService.class);
    }

    //add end by kejian
    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(MainApplication.getInstance().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);
                    if (!AppConfig.DEBUG) {
                        mOkHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(new ReceivedCookiesInterceptor(MainApplication.getInstance()))
                                .addInterceptor(new AddCookiesInterceptor(MainApplication.getInstance()))
                                .retryOnConnectionFailure(true)
                                .connectTimeout(AppConfig.connect_timeout, TimeUnit.SECONDS)
                                .readTimeout(AppConfig.read_timeout, TimeUnit.SECONDS)
                                .writeTimeout(AppConfig.write_timeout, TimeUnit.SECONDS)
                                .build();
                    } else {
                        mOkHttpClient = new OkHttpClient.Builder()
                                .addInterceptor(new ReceivedCookiesInterceptor(MainApplication.getInstance()))
                                .addInterceptor(new AddCookiesInterceptor(MainApplication.getInstance()))
                                .addInterceptor(interceptor)
                                .retryOnConnectionFailure(true)
                                .connectTimeout(AppConfig.connect_timeout, TimeUnit.SECONDS)
                                .readTimeout(AppConfig.read_timeout, TimeUnit.SECONDS)
                                .writeTimeout(AppConfig.write_timeout, TimeUnit.SECONDS)
                                .build();
                    }

                }
            }
        }
    }

    // 云端响应头拦截器，用来配置缓存策略
    private Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!StringUtils.isNetworkConnected()) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (StringUtils.isNetworkConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_LONG)
                        .removeHeader("Pragma").build();
            }
        }
    };


    public Observable<LoginResponse> handleLogin(LoginRequest loginRequest) {
        return mNetworkService.handleLogin(loginRequest.seller_name, loginRequest.seller_pwd, loginRequest.b_box, loginRequest.v);
    }

    public Observable<ShopinfoResponse> getShopInfo(ShopInfoRequest shopInfoRequest) {
        return mNetworkService.getShopInfo(shopInfoRequest.shop_id, shopInfoRequest.sign, shopInfoRequest.b_box, shopInfoRequest.v);
    }

}
