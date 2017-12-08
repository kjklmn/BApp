package com.bdhs.bossapp.base;

/**
 * 项目名称：BossApp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-07 16:41
 * 修改人：Administrator
 * 修改时间：2017-12-07 16:41
 * 修改备注：
 */
public interface BaseResultListener {
    void OnResultSucc(BaseResponse baseResponse);
    void OnResultFail(Throwable throwable);
}
