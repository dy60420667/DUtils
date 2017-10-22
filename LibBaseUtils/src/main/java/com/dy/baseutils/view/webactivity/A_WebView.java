package com.dy.baseutils.view.webactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.dy.baseutils.R;
import com.dy.baseutils.module.base.DYBaseActivity;

/**
 * Auth : dy
 * Time : 2017/2/6 11:45
 * Email: dymh21342@163.com
 * Description:
 */

public class A_WebView extends DYBaseActivity {
    public static void goToActivity(Context context,String url,String title){
        Intent it = new Intent(context,A_WebView.class);
        it.putExtra("url",url);
        it.putExtra("title",title);
        context.startActivity(it);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getIntent().getStringExtra("title"));
    }

    @Override
    public Fragment initFragment() {
        return F_WebView.createFragment(getIntent().getStringExtra("url"));
    }
}
