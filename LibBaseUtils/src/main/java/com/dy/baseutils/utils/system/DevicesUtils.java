package com.dy.baseutils.utils.system;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Descripty:
 * Auth:  邓渊
 * Date: 2017/12/11.21:15
 */

public class DevicesUtils {
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
