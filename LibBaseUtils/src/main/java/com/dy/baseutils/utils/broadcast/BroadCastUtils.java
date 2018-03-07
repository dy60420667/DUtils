package com.dy.baseutils.utils.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Descripty:广播的工具类
 * 尽量不要用广播，使用EventBus等开源框架进行消息通讯
 * Auth: 邓渊 dymh21342@163.com
 * Date: 2018/1/25  10:20
 */
public class BroadCastUtils {

    public static void sendBrodCast(Context context,String action) {
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(action));
    }

    public static void registerSwitchShopBroadcast(Context context, String action,BroadcastReceiver receiver) {
        IntentFilter intentFilter = new IntentFilter(action);
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
    }

    public static void unRegisterSwitchShopBroadcast(Context context,BroadcastReceiver receiver) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
    }
}
