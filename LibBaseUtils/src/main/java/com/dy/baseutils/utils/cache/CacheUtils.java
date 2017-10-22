package com.dy.baseutils.utils.cache;

import android.content.Context;
import android.os.Environment;
import android.util.Log;


import com.bumptech.glide.Glide;
import com.dy.baseutils.utils.cache.MyDiskLruCache;
import com.dy.baseutils.utils.glide.MyGlideModule;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Auth : dy
 * Time : 2016/6/24 11:15
 * Email: dymh21342@163.com
 * Description:缓存管理类
 */
public class CacheUtils {

    /**
     * 清除sdk的缓存
     *
     * @param context
     */
    public static void clearCache(Context context) {
        FileUtils.deleteFilesByDirectory(context, MyDiskLruCache.getDiskCacheDir(context, "Image"));
        FileUtils.deleteFilesByDirectory(context,context.getExternalCacheDir());
        Glide.get(context).clearMemory();
        Glide.get(context).clearDiskCache();
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     */
    public static String getCacheSize(Context context) {
        long result = 0;
        try {
            result += FileUtils.getFileSizes(MyDiskLruCache.getDiskCacheDir(context, "Image"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File dir = context.getExternalCacheDir();
            if (dir != null) {
                result += FileUtils.getFileSizes(dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FileUtils.FormetFileSize(result);
    }
}

