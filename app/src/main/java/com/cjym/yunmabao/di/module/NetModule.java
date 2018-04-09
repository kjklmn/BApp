package com.cjym.yunmabao.di.module;

import com.cjym.yunmabao.application.MainApplication;
import com.cjym.yunmabao.config.DebugConfig;
import com.cjym.yunmabao.http.AddCookiesInterceptor;
import com.cjym.yunmabao.http.NetworkService;
import com.cjym.yunmabao.http.ReceivedCookiesInterceptor;
import com.google.gson.Gson;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 项目名称：bossapp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-15 9:39
 * 修改人：Administrator
 * 修改时间：2017-12-15 9:39
 * 修改备注：
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient mOkHttpClient = null;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 指定缓存路径,缓存大小100Mb
        Cache cache = new Cache(new File(MainApplication.getInstance().getCacheDir(), "HttpCache"),
                1024 * 1024 * 100);
        if(!DebugConfig.DEBUG) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new ReceivedCookiesInterceptor(MainApplication.getInstance()))
                    .addInterceptor(new AddCookiesInterceptor(MainApplication.getInstance()))
                    .retryOnConnectionFailure(true)
                    .connectTimeout(DebugConfig.connect_timeout, TimeUnit.SECONDS)
                    .readTimeout(DebugConfig.read_timeout,TimeUnit.SECONDS)
                    .writeTimeout(DebugConfig.write_timeout,TimeUnit.SECONDS)
                    .build();
        } else {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new ReceivedCookiesInterceptor(MainApplication.getInstance()))
                    .addInterceptor(new AddCookiesInterceptor(MainApplication.getInstance()))
                    .addInterceptor(interceptor)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(DebugConfig.connect_timeout, TimeUnit.SECONDS)
                    .readTimeout(DebugConfig.read_timeout,TimeUnit.SECONDS)
                    .writeTimeout(DebugConfig.write_timeout,TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }
    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okhttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DebugConfig.Base_URL)
                .client(okhttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        return retrofit;
    }
    @Provides
    @Singleton
    public NetworkService provideApiService(Retrofit retrofit){
        return retrofit.create(NetworkService.class);
    }
}
