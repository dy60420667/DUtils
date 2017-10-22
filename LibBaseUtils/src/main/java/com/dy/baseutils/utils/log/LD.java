package com.dy.baseutils.utils.log;

import android.util.Log;

public class LD {
	public static void i(String msg){
		i("xx",msg);
	}
	public static void i(String tag, String msg){
		try {
			if (msg.length() <= 3000) {
 				Log.i(tag, msg);
			} else {
				Log.i(tag,msg.substring(0,3000));
				String subStr = msg.substring(3000);
				for(;subStr.length() > 3000;subStr = subStr.substring(3000)) {
					Log.i(tag,subStr.substring(0, 3000));
				}
				Log.i(tag,subStr);
			}
			
		} catch (Exception e) {
		}
	}
	
	public static void e(String msg){
		e("xx",msg);
	}
	public static void e(String tag, String msg){
		try {
			if (msg.length() <= 3000) {
 				Log.e(tag, msg);
			} else {
				Log.e(tag,msg.substring(0,3000));
				String subStr = msg.substring(3000);
				for(;subStr.length() > 3000;subStr = subStr.substring(3000)) {
					Log.e(tag,subStr.substring(0, 3000));
				}
				Log.e(tag,subStr);
			}
		} catch (Exception e) {
		}
	}
	
	public static void d(String msg){
		d("xx",msg);
	}
	public static void d(String tag, String msg){
		try {
			if (msg.length() <= 3000) {
 				Log.d(tag, msg);
			} else {
				Log.d(tag,msg.substring(0,3000));
				String subStr = msg.substring(3000);
				for(;subStr.length() > 3000;subStr = subStr.substring(3000)) {
					Log.d(tag,subStr.substring(0, 3000));
				}
				Log.d(tag,subStr);
			}
		} catch (Exception e) {
		}
	}
}
