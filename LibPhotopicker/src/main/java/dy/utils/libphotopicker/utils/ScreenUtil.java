package dy.utils.libphotopicker.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by mxp on 2016/8/24.
 */
public class ScreenUtil {
    private ScreenUtil() {
        throw new AssertionError();
    }

    public static float dp2Px(Context context, float dp) {
        return context == null?-1.0F:dp * context.getResources().getDisplayMetrics().density;
    }

    public static float px2dp(Context context, float px) {
        return context == null?-1.0F:px / context.getResources().getDisplayMetrics().density;
    }

    public static int dp2PxInt(Context context, float dp) {
        return (int)(dp2Px(context, dp) + 0.5F);
    }

    public static int pxToDpCeilInt(Context context, float px) {
        return (int)(px2dp(context, px) + 0.5F);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        return w_screen;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int h_screen = dm.heightPixels;
        return h_screen;
    }

    public static int getScreenWidth_DIP(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        return pxToDpCeilInt(context, (float)w_screen);
    }
}
