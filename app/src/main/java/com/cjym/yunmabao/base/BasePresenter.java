package com.cjym.yunmabao.base;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:27
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:27
 * 修改备注：
 */

/**
 * 项目名称：Serialport
 * 类描述：MVP中所有Presenter的基类
 * 创建人：kejian
 * 创建时间：2016-12-12 11:48
 * 修改人：Administrator
 * 修改时间：2016-12-12 11:48
 * 修改备注：
 */
public class BasePresenter<V extends BaseMvpView> implements Presenter<V> {
    /**
     * 当前连接的View
     */
    private V mvpView;
//    protected WeakReference<V> mViewRef; // view 的弱引用

    /**
     * Presenter与View建立连接
     *
     * @param mvpView 与此Presenter相对应的View
     */
    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
//        mViewRef = new WeakReference<V>(mvpView);
    }

    /**
     * Presenter与View连接断开
     */
    @Override
    public void detachView() {
//        if (mViewRef != null){
//            mViewRef.clear();
//            mViewRef = null;
//        }
        this.mvpView = null;
    }

    /**
     * 是否与View建立连接
     *
     * @return
     */
    public boolean isViewAttached() {
//        return mViewRef != null;
        return mvpView != null;
    }

    /**
     * 获取当前连接的View
     *
     * @return
     */
    public V getMvpView() {
//        return mViewRef.get();
        return mvpView;
    }

    /**
     * 每次调用业务请求的时候都要先调用方法检查是否与View建立连接，没有则抛出异常
     */
    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("请求数据前请先调用 attachView(MvpView) 方法与View建立连接");
        }
    }
}
