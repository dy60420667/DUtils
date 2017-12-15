package com.dy.baseutils.utils.system;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Descripty:
 * Auth:  dengyuan3
 * Date: 2017/10/24.21:36
 */
public class SoftKeyInputUtils {

    /**
     * 显示软键盘
     */
    public static void showSoftKeyWorld(final EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
            }
        },300);
    }

    /**
     * 滑动退出隐藏软键盘
     */
    public static void hideSoftKeyWorld(Activity context){
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
    }
}
