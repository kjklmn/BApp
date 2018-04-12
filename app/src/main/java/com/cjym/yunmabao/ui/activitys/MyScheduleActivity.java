package com.cjym.yunmabao.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cjym.yunmabao.R;
import com.cjym.yunmabao.utils.LogUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：yunmabao
 * 类描述：
 * 创建人：kejian
 * 创建时间：2018-04-11 15:17
 * 修改人：Administrator
 * 修改时间：2018-04-11 15:17
 * 修改备注：
 */
public class MyScheduleActivity extends BaseActivity implements
        CalendarView.OnDateSelectedListener,
        CalendarView.OnYearChangeListener {
    @BindView(R.id.tv_month_day)
    TextView tvMonthDay;
    @BindView(R.id.calendarView)
    CalendarView calendarView;

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public int bindLayout() {
        return R.layout.t_activity_colorful;
    }

    @Override
    public void initView() {
        tvMonthDay.setText(calendarView.getCurYear()+"/"+calendarView.getCurMonth() + "/" + calendarView.getCurDay());
        calendarView.setOnDateSelectedListener(this);
        calendarView.setOnYearChangeListener(this);
        List<Calendar> schemes = new ArrayList<>();
        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();

        schemes.add(getSchemeCalendar(year, month, 3, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 4, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 5, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 6, 0xFFdf1356, "事"));
        schemes.add(getSchemeCalendar(year, month, 7, 0xFFdf1356, "假"));

        schemes.add(getSchemeCalendar(year, month, 13, 0xFFdf1356, "记"));
        schemes.add(getSchemeCalendar(year, month, 14, 0xFFdf1356, "记"));
        schemes.add(getSchemeCalendar(year, month, 15, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 16, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 17, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 18, 0xFFdf1356, "记"));

        schemes.add(getSchemeCalendar(year, month, 21, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 22, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 23, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 24, 0xFFdf1356, "假"));
        schemes.add(getSchemeCalendar(year, month, 25, 0xFFdf1356, "假"));
        calendarView.setSchemeDate(schemes);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        LogUtils.w(TAG,"onDateSelected");
        tvMonthDay.setText(calendar.getYear()+"/"+calendar.getMonth() + "/" + calendar.getDay());
    }

    @Override
    public void onYearChange(int year) {
        LogUtils.w(TAG,"onYearChange");
        tvMonthDay.setText(String.valueOf(year));
    }
}
