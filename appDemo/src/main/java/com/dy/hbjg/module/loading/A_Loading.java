package com.dy.hbjg.module.loading;

import android.support.v4.app.Fragment;

import com.dy.baseutils.module.base.DYBaseActivity;

/**
 * Auth : dy
 * Time : 2017/2/10 16:30
 * Email: dymh21342@163.com
 * Description:
 */

public class A_Loading extends DYBaseActivity {
    @Override
    public Fragment initFragment() {
        return F_Loading.createFragment();
    }
}
