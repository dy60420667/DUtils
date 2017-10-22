package com.dy.baseutils.utils.cache;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Auth : dy
 * Time : 2017/2/9 17:02
 * Email: dymh21342@163.com
 * Description:
 */

public class FileUtils {
    /**
     * 转换文件大小
     * 小于30MB显示实际大小，大于30MB显示大于30MB
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString;
        String wrongSize = "0MB";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS <= 1048576 * 30) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = "大于 30MB";
        }
        return fileSizeString;
    }

    /**
     * 获取指定文件夹
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception {
        long size = 0;
        File flist[] = f.listFiles();
        for (int i = 0; i < flist.length; i++) {
            if (flist[i].isDirectory()) {
                size = size + getFileSizes(flist[i]);
            } else {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }




    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                size = fis.available();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fis.close();
                } catch (Exception e) {

                }
            }
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }


    /**
     * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * * @param directory
     *
     * @param context 上下文
     */
    public static void deleteFilesByDirectory(Context context, File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (null != files) {
                for (File item : files) {
                    if (item.isDirectory()) {
                        deleteFilesByDirectory(context, item);
                        item.delete();
                    } else {
                        item.delete();
                    }
                }
            }
        }
    }
}
