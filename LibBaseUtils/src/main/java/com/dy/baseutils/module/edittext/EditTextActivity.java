package com.dy.baseutils.module.edittext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dy.baseutils.module.base.DYBaseActivity;
import com.dy.baseutils.utils.dialog.ToastUtils;


/**
 * 编辑文字
 * Created by sky on 2015/7/20.
 * Weibo: http://weibo.com/2030683111
 * Email: 1132234509@qq.com
 */
public class EditTextActivity extends DYBaseActivity<EditTextFragment> {
    public static void gotoActivityForResult(Context mContext,String defaultStr,int maxLength,int minLength,int reqCode){
        Intent it = new Intent(mContext,EditTextActivity.class);
        it.putExtra("maxLength",maxLength);
        it.putExtra("minLength",minLength);
        it.putExtra("reqCode",reqCode);
        it.putExtra("defaultStr",defaultStr);
        mContext.startActivity(it);
    }
    private int minLength = 0;
    private int reqCode = 0;

    @Override
    public EditTextFragment initFragment() {
        Bundle bundle = getIntent().getExtras();
        String defaultStr = bundle.getString("defaultStr");
        int maxLength = bundle.getInt("maxLength");
        minLength = bundle.getInt("minLength");
        reqCode = bundle.getInt("reqCode");
        return EditTextFragment.newInstance(defaultStr,maxLength);
    }

    @Override
    public void initView() {
        titleBar.setRightBtnOnclickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getFragment().getInputText();

                if(minLength>0&&text.length()<minLength){
                    ToastUtils.showToast(getBaseContext(),"最少输入"+minLength+"个字符");
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("text", text);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        titleBar.setTitleTxt("输入内容");
        titleBar.setRightTxtBtn("保存");
    }
}
