package com.cjym.yunmabao.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjym.yunmabao.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BaseFragment extends Fragment {

    protected Activity mActivity;



//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//
//
//        return inflater.inflate(R.layout.fragment_base, container, false);
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

}
