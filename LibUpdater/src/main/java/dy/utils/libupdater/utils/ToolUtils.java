package dy.utils.libupdater.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.DecimalFormat;

/**
 * Created by dy on 2016/5/4.
 */
public class ToolUtils {
    public static String getVersionName(Context context) {//获取版本号
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "-1";
        }
    }

    public static int getVersionCode(Context context) {//获取版本号(内部识别号)

        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String getAppname(Context context) {//获取版本号(内部识别号)
        try {
            return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String getSize(final long size) {
        if (size < 0) {
            return "0K";
        }
        String ret = "0B";
        long tempSize = size;
        int index = 0;
        while (tempSize / 1024 > 0) {
            tempSize = tempSize / 1024;
            index++;
        }
        DecimalFormat df1 = new DecimalFormat("0.#");
        if (index < 2) {
            ret = df1.format(size / 1024.0) + "K";
        } else if (index < 3) {
            ret = df1.format(size / 1024.0 / 1024.0) + "M";
        } else {
            ret = df1.format(size / 1024.0 / 1024.0 / 1024.0) + "G";
        }
        return ret;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
