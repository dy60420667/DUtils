package com.dy.baseutils.module.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dy.baseutils.R;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

/**
 * Auth : dy
 * Time : 2017/1/23 16:01
 * Email: dymh21342@163.com
 * Description:
 */

public abstract class DYBaseActivity<T extends Fragment> extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {


    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("xx","onCreate"+getClass().getName());
        setContentView(getContentLayoutId());
        changeFragment();
        initToolbar();
    }
    public Toolbar getToolbar(){
        return toolbar;
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        if(toolbar==null){
            return;
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolbar.setOnMenuItemClickListener(this);
    }

    public void setEnableShowBack(boolean isEnable){
        if(getSupportActionBar()==null){
            return;
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(isEnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("xx","onDestroy"+getClass().getName());
    }

    public int getContentLayoutId() {
        return R.layout.dy_base_activity;
    }


    private T fragment;

    public T getFragment(){
        return fragment;
    }
    public void changeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = initFragment();
        transaction.replace(R.id.id_fragment_content, fragment);
        transaction.commit();
    }

    public abstract T initFragment();

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
