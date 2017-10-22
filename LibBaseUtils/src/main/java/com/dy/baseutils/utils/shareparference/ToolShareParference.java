package com.dy.baseutils.utils.shareparference;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ToolShareParference {
    public static String SHARE_FILE_NAME = "gametool";
    public static String SHARE_FILE_NAME_LOGIN = "loginuser";
    public static String SHARE_FILE_NAME_APPTIME = "apptime";

    public static void saveShare(String key, String value, Context context) {
        saveShare(key, value, ToolShareParference.SHARE_FILE_NAME, context);
    }

    public static String getShare(String key, String defaultValue,
                                  Context context) {
        return getShare(key, defaultValue, ToolShareParference.SHARE_FILE_NAME, context);
    }

    public static String getShare(String key, String defaultValue, String fileName,
                                  Context context) {
        SharedPreferences share = context.getSharedPreferences(fileName,
                Context.MODE_PRIVATE);
        String result = share.getString(key, defaultValue);
        return result;
    }

    public static void saveShare(String key, String value, String filename,
                                 Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        Editor editor = share.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveShare(String key, long value, String filename,
                                 Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        Editor editor = share.edit();
        editor.putLong(key, value);
        editor.commit();
    }


    public static long getShare(String key, long defult, String filename,
                                Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        long result = share.getLong(key, defult);
        return result;
    }


    public static void clearShare(String filename, Context context) {
        SharedPreferences share = context.getSharedPreferences(filename,
                Context.MODE_PRIVATE);
        Editor editor = share.edit();
        editor.clear();
        editor.commit();
    }


}
