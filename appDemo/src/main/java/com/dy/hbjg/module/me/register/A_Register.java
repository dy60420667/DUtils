package com.dy.hbjg.module.me.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dy.baseutils.module.base.DYBaseActivity;

/**
 * Auth : dy
 * Time : 2017/1/22 09:55
 * Email: dymh21342@163.com
 * Description:
 */

public class A_Register extends DYBaseActivity implements View.OnClickListener {

    public static void gotoActivity(Context context){
        Intent it = new Intent(context ,A_Register.class);
        context.startActivity(it);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("注册");
        setEnableShowBack(true);

//        title_right_text.setVisibility(View.VISIBLE);
//        title_right_text.setText("下一步");
//        title_right_text.setOnClickListener(this);
    }

    @Override
    public Fragment initFragment() {
        return F_Register.getFragment();
    }

    @Override
    public void onClick(View view) {
    }
}
