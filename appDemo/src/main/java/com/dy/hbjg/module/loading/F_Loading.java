package com.dy.hbjg.module.loading;

import android.content.Intent;
import android.os.Handler;

import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.hbjg.module.home.A_Home;
import com.dy.hbjg.R;

/**
 * Auth : dy
 * Time : 2017/2/10 16:31
 * Email: dymh21342@163.com
 * Description:
 */

public class F_Loading extends DYBaseFragment {
    public static F_Loading createFragment(){
        F_Loading f_loading = new F_Loading();
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
                Intent it = new Intent(getActivity(), A_Home.class);
                startActivity(it);
                getActivity().finish();
            }
        },1000);

    }

}
