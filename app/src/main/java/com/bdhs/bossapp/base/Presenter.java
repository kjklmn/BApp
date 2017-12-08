package com.bdhs.bossapp.base;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:27
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:27
 * 修改备注：
 */
public interface Presenter<V extends BaseMvpView> {

    /**
     * Presenter与View建立连接
     *
     * @param mvpView 与此Presenter相对应的View
     */
    void attachView(V mvpView);

    /**
     * Presenter与View连接断开
     */
    void detachView();
}
