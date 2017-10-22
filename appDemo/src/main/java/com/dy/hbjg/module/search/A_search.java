package com.dy.hbjg.module.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dy.baseutils.Tools;
import com.dy.baseutils.module.base.DYBaseActivity;
import com.dy.baseutils.utils.dialog.ToastUtils;
import com.dy.hbjg.R;
import com.dy.hbjg.module.search.view.ISearchView;

/**
 * Auth : dy
 * Time : 2017/2/3 16:51
 * Email: dymh21342@163.com
 * Description:
 */

public class A_search extends DYBaseActivity<F_search> implements View.OnClickListener {


    public static void gotoActivity(Context context) {
        Intent it = new Intent(context, A_search.class);
        context.startActivity(it);
    }

    private EditText toolbar_et_search;
    private ImageView toolbar_img_delete;
    private ISearchView iSearchView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("");
        setEnableShowBack(true);
        toolbar_et_search = (EditText) findViewById(R.id.toolbar_et_search);

        toolbar_img_delete = (ImageView) findViewById(R.id.toolbar_img_delete);

        toolbar_et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String t = editable.toString();
                if (TextUtils.isEmpty(t)) {
                    toolbar_img_delete.setVisibility(View.INVISIBLE);
                } else {
                    toolbar_img_delete.setVisibility(View.VISIBLE);
                }
            }
        });
        toolbar_img_delete.setOnClickListener(this);
        toolbar_et_search.setOnClickListener(this);

        toolbar_et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int arg1, KeyEvent keyEvent) {
                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    String text = toolbar_et_search.getText().toString();
                    if(TextUtils.isEmpty(text)){
                        ToastUtils.ShowToast(getBaseContext(), "搜索内容为空");
                        return false;
                    }
                    getFragment().clearDate();
                    getFragment().searchPresenter.searchMovies(getWorld(),0);
                    getFragment().showList();
                    getFragment().showLoading();
                    Tools.hideSoftKeyWorld(A_search.this);
                }
                return false;
            }
        });

        Tools.showSoftKeyWorld(toolbar_et_search);
    }

    @Override
    public int getContentLayoutId() {
        return R.layout.a_search;
    }

    @Override
    public F_search initFragment() {
        F_search f_search = F_search.createFragment();
        iSearchView = f_search;
        return f_search;
    }

    public void setToolbar_et_search(String world){
        toolbar_et_search.setText(world);
        if(!TextUtils.isEmpty(world)){
            toolbar_et_search.setSelection(world.length());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_img_delete:
                toolbar_et_search.setText("");
                iSearchView.showTagFlow();
                Tools.showSoftKeyWorld(toolbar_et_search);
                break;
        }
    }

    public String getWorld() {
        return toolbar_et_search.getText().toString();
    }

    @Override
    protected void onPause() {
        Tools.hideSoftKeyWorld(this);
        super.onPause();
    }
}
