package com.cjym.yunmabao.base;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:24
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:24
 * 修改备注：
 */
public interface BaseMvpView {
    public void showLoading();

    public void hideLoading();

    public void showFailedError(Throwable throwable);

    public void toMainActivity(BaseResponse baseResponse);

}
