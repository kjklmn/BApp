package com.bdhs.bossapp.http;


import com.bdhs.bossapp.presenter.login.LoginRequest;
import com.bdhs.bossapp.presenter.login.LoginResponse;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 网络访问接口
 */
public interface NetworkService {

    @POST("login_boss/login_in")
    Observable<LoginResponse> handleLogin(@Body LoginRequest bean);

    @FormUrlEncoded
    @POST("login_boss/login_in")
    Observable<LoginResponse> handleLogin(@Field("seller_name") String seller_name,
                                          @Field("seller_pwd") String seller_pwd,
                                          @Field("b_box") String b_box,
                                          @Field("v") String v);
}
