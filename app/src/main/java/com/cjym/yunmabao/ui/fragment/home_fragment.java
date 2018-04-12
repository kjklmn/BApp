package com.cjym.yunmabao.ui.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.cjym.yunmabao.R;
import com.cjym.yunmabao.ui.activitys.MyScheduleActivity;
import com.cjym.yunmabao.ui.activitys.WebViewActivity;
import com.cjym.yunmabao.ui.fragments.BaseFragment;
import com.cjym.yunmabao.ui.view.BottomScrollView;
import com.cjym.yunmabao.ui.view.CustomView;
import com.cjym.yunmabao.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 14487 on 2017/9/17.
 */

public class Home_fragment extends BaseFragment {
    private static final String TAG = Home_fragment.class.getSimpleName();

    @BindView(R.id.main_scroll_view)
    BottomScrollView mainScrollView;

    Unbinder unbinder;
    @BindView(R.id.ll_danmu)
    LinearLayout llDanmu;
    private View contentView;
    protected Activity mActivity;

    CustomView cv_paytype_cash;
    CustomView cv_my_schedule;
    CustomView cv_interview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.w("Home_fragment", "onCreateView");
        contentView = inflater.inflate(R.layout.fragment_fpage, container, false);
        unbinder = ButterKnife.bind(this, contentView);
        initListener();
        return contentView;
    }

    public void initListener() {
        cv_paytype_cash = contentView.findViewById(R.id.cv_paytype_cash);
        cv_paytype_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kejian", "云妈收款");
                toActivity(mActivity, WebViewActivity.class, bundle);
            }
        });

        cv_my_schedule = contentView.findViewById(R.id.cv_my_schedule);
        cv_my_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kejian", "我的档期");
                toActivity(mActivity, MyScheduleActivity.class, bundle);
            }
        });

//        float curTranslationX = llDanmu.getTranslationX();
        startDanmu();
    }
    int screenWidth = 0;
    int viewWidth = 0;
    private void startDanmu() {
        if(screenWidth == 0) {
            getHW();
            getViewHW();
            LogUtils.w(TAG,screenWidth+";viewWidth"+viewWidth);
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(llDanmu, "translationX", viewWidth, 0-screenWidth);
        animator.setDuration(20000);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                llDanmu.setVisibility(View.VISIBLE);
                LogUtils.w(TAG,"onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llDanmu.setVisibility(View.GONE);
                LogUtils.w(TAG,"onAnimationEnd");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                llDanmu.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    private void getHW() {
        WindowManager manager = mActivity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        LogUtils.w(TAG,"屏幕："+screenWidth+";"+height);
    }

    private void getViewHW() {
        int w =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        int h =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        llDanmu.measure(w,h);
        int height= llDanmu.getMeasuredHeight();
        viewWidth = llDanmu.getMeasuredWidth();
        LogUtils.w(TAG,"子view："+viewWidth+";"+height);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
