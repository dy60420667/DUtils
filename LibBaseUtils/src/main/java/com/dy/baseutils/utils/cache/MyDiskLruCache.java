package com.dy.baseutils.utils.cache;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * 本地缓存工具
 * Created by zjs on 2016/3/28.
 */
public class MyDiskLruCache {
    /**
     * 存储本地缓存
     *
     * @param context      上下文
     * @param fileName     存储文件夹名称（文件将存储在 /data/data/com.xxx.xxx/cache/fileName 下）
     * @param key          本地存储的文件名
     * @param serializable 需要存储的数据
     * @return
     */
    public static void save(Context context, String fileName, String key, Object serializable) {
        if (context == null) {
            return;
        }
        DiskLruCache mDiskLruCache;
        try {
            File cacheDir = getDiskCacheDir(context, fileName);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 50 * 1024 * 1024);
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            OutputStream timeOs = editor.newOutputStream(0);
            OutputStream timeBos = new BufferedOutputStream(timeOs);
            ObjectOutputStream timeOos = new ObjectOutputStream(timeBos);
            timeOos.writeObject(serializable);
            timeOos.close();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取本地存储的文件信息
     *
     * @param context  上下文
     * @param fileName 文件名称（文件将存储在 /data/data/com.xxx.xxx/cache/fileName 下）
     * @param key      本地存储的文件名
     * @return 存储的数据内容
     */
    public static Object read(Context context, String fileName, String key) {
        DiskLruCache mDiskLruCache;
        try {
            File cacheDir = getDiskCacheDir(context, fileName);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1, 50 * 1024 * 1024);
            DiskLruCache.Snapshot editor = mDiskLruCache.get(key);
            if (editor == null) {
                return null;
            }
            ObjectInputStream inputStream = new ObjectInputStream(editor.getInputStream(0));
            Object object = inputStream.readObject();
            inputStream.close();
            editor.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取文件名
     *
     * @param context
     * @param uniqueName 自定义文件夹的名称
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     *
     * @param context       上下文
     * @param fileName      需要删除文件夹名称
     * @param intervaltTime 间隔时间
     */
    public static void deleteFilesByDirectory(Context context, String fileName, long intervaltTime) {
        File directory = getDiskCacheDir(context, fileName);
        if (directory != null && directory.exists() && directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if(null != files){
                for (File item : files) {
                    long now = new Date().getTime();
                    if (now - intervaltTime > item.lastModified()) {
                        item.delete();
                    }
                }
            }
        }
    }

    /**
     * 插件化的删除文件(7天删图片)
     */
    public static void delete7DayImage(Context context) {
        deleteFilesByDirectory(context, "Image", 7 * 24 * 60 * 60 * 1000);
    }
}
