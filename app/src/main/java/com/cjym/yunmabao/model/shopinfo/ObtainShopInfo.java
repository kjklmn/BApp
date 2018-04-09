package com.cjym.yunmabao.model.shopinfo;


import com.cjym.yunmabao.http.RetrofitManager;
import com.cjym.yunmabao.presenter.shopinfo.ShopInfoRequest;
import com.cjym.yunmabao.presenter.shopinfo.ShopinfoResponse;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 项目名称：Hello
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-11 17:35
 * 修改人：Administrator
 * 修改时间：2017-12-11 17:35
 * 修改备注：
 */
public class ObtainShopInfo {
    public void getShopInfo(ShopInfoRequest shopInfoRequest, final OnObtainListener l) {
        RetrofitManager.builder().getShopInfo(shopInfoRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ShopinfoResponse>() {
                    @Override
                    public void call(ShopinfoResponse shopinfoResponse) {
                        l.OnResultSucc(shopinfoResponse);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        l.OnResultFail(throwable);
                    }
                });
    }
}
