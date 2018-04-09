package com.cjym.yunmabao.presenter.login;

import com.cjym.yunmabao.base.BaseMvpView;
import com.cjym.yunmabao.base.BasePresenter;
import com.cjym.yunmabao.base.BaseResponse;
import com.cjym.yunmabao.model.login.Login;
import com.cjym.yunmabao.model.login.OnLoginListener;

import com.cjym.yunmabao.utils.LogUtils;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:29
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:29
 * 修改备注：
 */
public class LoginPresenter extends BasePresenter<BaseMvpView> {
    private Login mLogin;

    public LoginPresenter() {
        this.mLogin = new Login();
    }

    //,final OnLoginListener l
    public void handldLogin(LoginRequest loginRequest) {
        checkViewAttached();
        getMvpView().showLoading();
        mLogin.handleLogin(loginRequest, new OnLoginListener() {
            @Override
            public void OnResultSucc(BaseResponse baseResponse) {
                if (isViewAttached()) {
                    getMvpView().hideLoading();
                    getMvpView().toMainActivity(baseResponse);
                }
            }

            @Override
            public void OnResultFail(Throwable throwable) {
                if (isViewAttached()) {
                    if(getMvpView() !=null) {
                        getMvpView().hideLoading();
                        getMvpView().showFailedError(throwable);
                    } else {
                        LogUtils.w("kejian","getMvpView is null");
                    }
                }
            }
        });
    }
}
