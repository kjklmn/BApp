package com.cjym.yunmabao.http;

import android.content.Context;
import android.content.SharedPreferences;

import com.cjym.yunmabao.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 项目名称：bossapp
 * 类描述：
 * 创建人：kejian
 * 创建时间：2017-12-12 15:06
 * 修改人：Administrator
 * 修改时间：2017-12-12 15:06
 * 修改备注：
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    private Context context;
    SharedPreferences sharedPreferences;

    public ReceivedCookiesInterceptor(Context context) {
        super();
        this.context = context;
        sharedPreferences = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (chain == null)
            LogUtils.d("ReceivedCookiesInterceptor", "Receivedchain == null");
        Response originalResponse = chain.proceed(chain.request());
        LogUtils.d("ReceivedCookiesInterceptor", "originalResponse:" + originalResponse.toString());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.from(originalResponse.headers("set-cookie"))
                    .map(new Func1<String, String>() {
                        @Override
                        public String call(String s) {
                            String[] cookieArray = s.split(";");
                            return cookieArray[0];
                        }
                    })
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String cookie) {
                            cookieBuffer.append(cookie).append(";");
                        }
                    });
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("cookie", cookieBuffer.toString());
            LogUtils.d("ReceivedCookiesInterceptor", "ReceivedCookiesInterceptor:" + cookieBuffer.toString());
            editor.commit();
        }

        return originalResponse;
    }
}
