package com.dy.baseutils.utils.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.dy.baseutils.R;
import com.dy.baseutils.utils.system.DevicesUtils;

/**
 * Auth : dy
 * Time : 2017/1/24 16:53
 * Email: dymh21342@163.com
 * Description:
 */

public class ToastUtils {
    private static Toast toast;

    public static void showToast(final Context context, int resourceid){
        showToast(context,context.getResources().getString(resourceid));
    }

    public static void showToast(final Context context, final String text) {
        if (toast != null) {
            toast.cancel();
        }

        if(context==null|| TextUtils.isEmpty(text)){
            return;
        }

        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.toast_alert_common,
                null);
        TextView tv = (TextView) view.findViewById(R.id.toast_text);
        tv.setText(text);
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, DevicesUtils.dp2px(context, 120));
        toast.setView(view);
        toast.show();

        Animation alpAnimation = new AlphaAnimation(0.1f, 1.0f);
        alpAnimation.setDuration(Toast.LENGTH_LONG);
        alpAnimation.setFillAfter(true);
        view.startAnimation(alpAnimation);

    }

}
