package com.dy.hbjg.module.me.utils;

import com.dy.baseutils.utils.shareparference.ToolShareParference;
import com.dy.hbjg.App;

/**
 * Created by oosmart on 17/1/21.
 */

public class UtilsUser {
    public static String getUserInfoParference(String key,String defauletValue){
        return ToolShareParference.getShare(key,defauletValue,ToolShareParference.SHARE_FILE_NAME_LOGIN, App.getApp());
    }

    public static void saveUserInfoParference(String key,String value){
        ToolShareParference.saveShare(key, value,ToolShareParference.SHARE_FILE_NAME_LOGIN, App.getApp());
    }

    public static void clearUserInfo(){
        ToolShareParference.clearShare(ToolShareParference.SHARE_FILE_NAME_LOGIN, App.getApp());
    }
}
