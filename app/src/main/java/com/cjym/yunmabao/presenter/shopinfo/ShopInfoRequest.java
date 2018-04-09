package com.cjym.yunmabao.presenter.shopinfo;


import com.cjym.yunmabao.base.BaseResquest;

/**
 * 项目名称：Hello
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-11 17:37
 * 修改人：Administrator
 * 修改时间：2017-12-11 17:37
 * 修改备注：
 */
public class ShopInfoRequest extends BaseResquest {
    public String sign = null;
    public String shop_id = null;

    public ShopInfoRequest(String sign,String shop_id) {
        this.sign = sign;
        this.shop_id = shop_id;
    }
}
