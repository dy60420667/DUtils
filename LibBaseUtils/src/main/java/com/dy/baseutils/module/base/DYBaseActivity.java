package com.dy.baseutils.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dy.baseutils.R;
import com.dy.baseutils.module.common.CommonTitleBar;
import com.dy.baseutils.utils.log.LD;

/**
 * Auth : dy
 * Time : 2017/1/23 16:01
 * Email: dymh21342@163.com
 * Description:
 */
public abstract class DYBaseActivity<T extends Fragment> extends AppCompatActivity {

    protected CommonTitleBar titleBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LD.i("xx", "onCreate" + getClass().getName());
        setContentView(getContentLayoutId());
        changeFragment();
        initView();
    }

    @Override
    protected void onDestroy() {
        LD.i("xx", "onDestroy" + getClass().getName());
        super.onDestroy();
    }

    public void initView(){
    }

    public void setEnableTitlebar(boolean isEnable){
        if(titleBar!=null&&!isEnable){
            titleBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        titleBar.setTitleTxt(title.toString());
    }

    public void setEnableBack(boolean isEnable){
        if(titleBar!=null&&!isEnable){
            titleBar.hideLeftBtn();
        }
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        titleBar = (CommonTitleBar) findViewById(R.id.layout_titlebar);
        if (titleBar != null)
            titleBar.setLeftBtnOnclickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }


    public int getContentLayoutId() {
        return R.layout.dy_base_activity;
    }


    private T fragment;

    public T getFragment() {
        return fragment;
    }

    public void changeFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = initFragment();
        transaction.replace(R.id.id_fragment_content, fragment);
        transaction.commit();
    }

    public abstract T initFragment();
}
