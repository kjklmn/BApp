package com.cjym.yunmabao.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjym.yunmabao.R;
import com.cjym.yunmabao.ui.fragments.BaseFragment;


/**
 * Created by 14487 on 2017/9/17.
 */

public class person_fragment  extends BaseFragment {

    private View ContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ContentView = inflater.inflate(R.layout.person_layout_view,container,false);
        return ContentView;
    }
}
