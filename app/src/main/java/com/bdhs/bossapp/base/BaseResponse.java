package com.bdhs.bossapp.base;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:23
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:23
 * 修改备注：
 */
public abstract class BaseResponse {
    public int code;
    public String msg;

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
