package com.bdhs.bossapp.di.component;

import com.bdhs.bossapp.di.module.NetModule;
import com.bdhs.bossapp.http.NetworkService;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * 项目名称：bossapp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-15 10:21
 * 修改人：Administrator
 * 修改时间：2017-12-15 10:21
 * 修改备注：
 */

@Component(modules = NetModule.class)
@Singleton
public interface NetComponent {
    NetworkService getApiService();
    OkHttpClient getOkHttp();
    Retrofit getRetrofit();
}
