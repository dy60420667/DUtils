package com.dy.baseutils.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.baseutils.R;

/**
 * Auth : dy
 * Time : 2017/2/6 11:46
 * Email: dymh21342@163.com
 * Description:
 */

public abstract class DYBaseFragment extends Fragment {
    public View rootView;
    public LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getFragmentLayout(),null);
        this.inflater = inflater;
        initView();
        return rootView;
    }

    public abstract void initView();

    public int getFragmentLayout() {
        return R.layout.dy_base_fragment;
    }
}
