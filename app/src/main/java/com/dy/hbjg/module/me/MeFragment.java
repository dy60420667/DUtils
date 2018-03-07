package com.dy.hbjg.module.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.utils.cache.CacheUtils;
import com.dy.hbjg.R;
import com.dy.hbjg.module.me.login.LoginActivity;


/**
 * Auth : dy
 * Time : 2017/1/20 16:55
 * Email: dymh21342@163.com
 * Description:
 */

public class MeFragment extends DYBaseFragment implements View.OnClickListener {
    public static Fragment createFragment(){
        return new MeFragment();
    }

    View rootView;

    private TextView tv_cache;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.f_me,null);
        initView();
        return rootView;
    }

    public void initView() {
        rootView.findViewById(R.id.layout_login).setOnClickListener(this);
        rootView.findViewById(R.id.layout_push).setOnClickListener(this);

        tv_cache = (TextView) rootView.findViewById(R.id.tv_cache);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initViewOnShow();
        }
    }

    long lasttime = 0;
    private void initViewOnShow() {
        try {
            long nowtime = System.currentTimeMillis();
            if (nowtime - lasttime > 500) {//超过500毫秒才刷新
                tv_cache.setText(CacheUtils.getCacheSize(getContext()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_login:
                LoginActivity.gotoLogin(getActivity());
                break;
            case R.id.layout_push:
                break;
        }
    }
}
