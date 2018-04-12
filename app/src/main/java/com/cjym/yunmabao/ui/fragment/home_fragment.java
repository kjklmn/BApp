package com.cjym.yunmabao.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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

public class home_fragment extends BaseFragment {


    @BindView(R.id.main_scroll_view)
    BottomScrollView mainScrollView;

    Unbinder unbinder;
    private View contentView;
    protected Activity mActivity;

    CustomView cv_paytype_cash;
    CustomView cv_my_schedule;
    CustomView cv_interview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.w("home_fragment","onCreateView");
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
                bundle.putString("kejian","云妈收款");
                toActivity(mActivity,WebViewActivity.class,bundle);
            }
        });

        cv_my_schedule = contentView.findViewById(R.id.cv_my_schedule);
        cv_my_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("kejian","我的档期");
                toActivity(mActivity,MyScheduleActivity.class,bundle);
            }
        });
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
