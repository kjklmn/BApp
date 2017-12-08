package com.bdhs.bossapp.model.login;

import com.bdhs.bossapp.http.RetrofitManager;
import com.bdhs.bossapp.presenter.login.LoginRequest;
import com.bdhs.bossapp.presenter.login.LoginResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:34
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:34
 * 修改备注：
 */
public class Login {

    public void handleLogin(LoginRequest loginRequest,final OnLoginListener l) {
        RetrofitManager.builder().handleLogin(loginRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoginResponse>() {
                    @Override
                    public void call(LoginResponse loginResponse) {
                        l.OnResultSucc(loginResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                      l.OnResultFail(throwable);
                    }
                });
    }
}
