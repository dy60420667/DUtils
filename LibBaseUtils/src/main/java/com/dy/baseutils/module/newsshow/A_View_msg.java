package com.dy.baseutils.module.newsshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.dy.baseutils.module.base.DYBaseActivity;
import com.dy.baseutils.module.newsshow.bean.ViewMsg;

/**
 * Auth : dy
 * Time : 2017/1/22 11:11
 * Email: dymh21342@163.com
 * Description:
 */

public class A_View_msg extends DYBaseActivity {
    public static void gotoActivity(Activity context, ViewMsg detail){
        Intent it = new Intent(context,A_View_msg.class);
        it.putExtra("item",detail);
        context.startActivity(it);
    }

    public static void gotoActivity(Activity context){
        Intent it = new Intent(context,A_View_msg.class);
        it.putExtra("item",ViewMsg.getDetailTest());
        context.startActivity(it);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Fragment initFragment() {
        return F_View_msg.getFragment((ViewMsg) getIntent().getSerializableExtra("item"));
    }
}
