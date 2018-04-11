package com.cjym.yunmabao.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cjym.yunmabao.R;
import com.cjym.yunmabao.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author ChayChan
 * @description: 加载网页的activity
 * @date 2017/7/4  22:01
 */

public class WebViewActivity extends BaseActivity {
    private static final String TAG = WebViewActivity.class.getSimpleName();
    public static final String URL = "url";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.wv_content)
    WebView wvContent;


    @Override
    public void initParms(Bundle parms) {
        LogUtils.w(TAG,"kejian = "+ parms.getString("kejian"));
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        wvContent.loadUrl("http://www.baidu.com");
        //设置WebViewClient客户端
        wvContent.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void widgetClick(View v) {

    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }

}

