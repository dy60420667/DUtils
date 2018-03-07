package com.dy.hbjg.module.loading;

import android.os.Handler;

import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.hbjg.module.home.HomeActivity;
import com.dy.hbjg.R;

/**
 * Auth : dy
 * Time : 2017/2/10 16:31
 * Email: dymh21342@163.com
 * Description:
 */

public class LoadingFragment extends DYBaseFragment {
    public static LoadingFragment createFragment(){
        LoadingFragment f_loading = new LoadingFragment();
        return f_loading;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.f_loading;
    }

    @Override
    public void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               HomeActivity.gotoActivity(getActivity());
                getActivity().finish();
            }
        },10);

    }

}
