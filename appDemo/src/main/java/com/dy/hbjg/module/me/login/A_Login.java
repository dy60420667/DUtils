package com.dy.hbjg.module.me.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.dy.baseutils.module.base.DYBaseActivity;
import com.dy.hbjg.R;

/**
 * Created by oosmart on 17/1/21.
 */

public class A_Login extends DYBaseActivity {

    public static void gotoLoginForResult(Activity context, int resultCode) {
        Intent it = new Intent(context, A_Login.class);
        it.putExtra("loginresult", true);
        it.setClass(context, A_Login.class);
        context.startActivityForResult(it, resultCode);
    }

    public static void gotoLogin(Activity context) {
        Intent it = new Intent(context, A_Login.class);
        it.setClass(context, A_Login.class);
        context.startActivity(it);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.me_login));
        setEnableShowBack(true);


    }
    @Override
    public Fragment initFragment() {
        return F_Login.getFragment();
    }



}
