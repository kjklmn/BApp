package com.bdhs.bossapp.presenter.login;

import com.bdhs.bossapp.base.BaseResponse;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:31
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:31
 * 修改备注：
 */
public class LoginResponse extends BaseResponse {

    public BeanData data;

    public class BeanData {
        public int tag;
        public String store_id;
        public String shop_id;
        public String sign;
        public String img;
        public String name;

        @Override
        public String toString() {
            return "BeanData{" +
                    "tag=" + tag +
                    ", store_id='" + store_id + '\'' +
                    ", shop_id='" + shop_id + '\'' +
                    ", sign='" + sign + '\'' +
                    ", img='" + img + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return super.toString() + ";" +
        "LoginResponse{" +
                "data=" + data.toString() +
                '}';
    }
}
