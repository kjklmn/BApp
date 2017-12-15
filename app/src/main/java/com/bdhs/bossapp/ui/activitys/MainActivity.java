package com.bdhs.bossapp.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bdhs.bossapp.R;
import com.bdhs.bossapp.base.BaseMvpView;
import com.bdhs.bossapp.base.BaseResponse;
import com.bdhs.bossapp.presenter.login.LoginResponse;
import com.bdhs.bossapp.presenter.shopinfo.ShopInfoPresenter;
import com.bdhs.bossapp.presenter.shopinfo.ShopInfoRequest;
import com.bdhs.bossapp.ui.fragment.home_fragment;
import com.bdhs.bossapp.ui.fragment.message_fragment;
import com.bdhs.bossapp.ui.fragment.person_fragment;
import com.bdhs.bossapp.ui.fragment.pond_fragment;
import com.bdhs.bossapp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.View_Pager)
    android.support.v4.view.ViewPager View_Pager;
    @BindView(R.id.home_image_view)
    ImageView home_image_view;
    @BindView(R.id.home_layout_view)
    LinearLayout home_layout_view;
    @BindView(R.id.message_image_view)
    ImageView message_image_view;
    @BindView(R.id.message_layout_view)
    LinearLayout message_layout_view;
    @BindView(R.id.pond_image_view)
    ImageView pond_image_view;
    @BindView(R.id.pond_layout_view)
    LinearLayout pond_layout_view;
    @BindView(R.id.person_image_view)
    ImageView person_image_view;
    @BindView(R.id.person_layout_view)
    LinearLayout person_layout_view;

    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments;

    private ShopInfoPresenter shopInfoPresenter;
    private String shop_id = null;
    private String sign = null;
    private String img_path = null;
    private String shop_name = null;

    @Override
    public void initParms(Bundle parms) {
        LoginResponse response = (LoginResponse) parms.getSerializable("data");
        if (response != null
                && response.data != null) {
            shop_id = response.data.shop_id;
            sign = response.data.sign;
            img_path = response.data.img;
            shop_name = response.data.name;
        } else {
            LogUtils.e(TAG, "response is null");
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    private void setCustomActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
    }


    @Override
    public void initView() {
        setCustomActionBar();

        //新建滑动的4个数据源 fragments数据集中
        fragments = new ArrayList<>();
        Fragment home = new home_fragment();
        Fragment message = new message_fragment();
        Fragment pond = new pond_fragment();
        Fragment person = new person_fragment();
        fragments.add(home);
        fragments.add(message);
        fragments.add(pond);
        fragments.add(person);

        //新建一个适配器
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        //Viewpager传值
        View_Pager.setAdapter(fragmentPagerAdapter);

        //View_Pager和itemMenu进行联动效果
        View_Pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ReSetimg();
                int Currentitem = View_Pager.getCurrentItem();
                /**
                 *  文件进行索引调用 (Alt+Shift+m)
                 */
                Switch_Tab(Currentitem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void doBusiness() {
        initEvent();
        SetSelect(0);   //首启动打开的界面为home_layout_view
        shopInfoPresenter = new ShopInfoPresenter();
        shopInfoPresenter.attachView(new BaseMvpView() {
            @Override
            public void showLoading() {
                LogUtils.w(TAG,"showLoading");
            }

            @Override
            public void hideLoading() {
                LogUtils.w(TAG,"showLoading");
            }

            @Override
            public void showFailedError(Throwable throwable) {
                LogUtils.w(TAG,"showLoading");
            }

            @Override
            public void toMainActivity(BaseResponse baseResponse) {
                LogUtils.w(TAG,"showLoading");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        shopInfoPresenter.getShopInfo(new ShopInfoRequest(sign,shop_id));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopInfoPresenter.detachView();
    }

    @Override
    public void widgetClick(View v) {

    }

    //添加菜单点击事件
    private void initEvent() {
        home_layout_view.setOnClickListener(this);
        message_layout_view.setOnClickListener(this);
        pond_layout_view.setOnClickListener(this);
        person_layout_view.setOnClickListener(this);
    }

    //点击后的相关操作
    @Override
    public void onClick(View v) {
        ReSetimg();
        switch (v.getId()) {
            case R.id.home_layout_view:
                SetSelect(0);
                break;
            case R.id.message_layout_view:
                SetSelect(1);
                break;
            case R.id.pond_layout_view:
                SetSelect(2);
                break;
            case R.id.person_layout_view:
                SetSelect(3);
                break;
            default:
                break;
        }
    }

    private void SetSelect(int a) {
        ReSetimg();
        Switch_Tab(a);
    }

    //设置点击菜单后当前图片变亮,同时View_Pager滑动到指定的界面
    private void Switch_Tab(int a) {
        switch (a) {
            case 0:
                View_Pager.setCurrentItem(0);
                home_image_view.setImageResource(R.mipmap.comui_tab_home_selected);
                break;
            case 1:
                View_Pager.setCurrentItem(1);
                message_image_view.setImageResource(R.mipmap.comui_tab_message_selected);
                break;
            case 2:
                View_Pager.setCurrentItem(2);
                pond_image_view.setImageResource(R.mipmap.comui_tab_pond_selected);
                break;
            case 3:
                View_Pager.setCurrentItem(3);
                person_image_view.setImageResource(R.mipmap.comui_tab_person_selected);
                break;
            default:
                break;
        }
    }

    //设置所有图片的颜色为暗色
    private void ReSetimg() {
        home_image_view.setImageResource(R.mipmap.comui_tab_home);
        message_image_view.setImageResource(R.mipmap.comui_tab_message);
        pond_image_view.setImageResource(R.mipmap.comui_tab_pond);
        person_image_view.setImageResource(R.mipmap.comui_tab_person);
    }
}
