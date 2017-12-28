package dy.utils.libhttp.downloadservice;

import android.text.TextUtils;

import dy.utils.libhttp.downloadservice.manager.DownloadManager;
import dy.utils.libhttp.downloadservice.manager.DownloadTask;
import dy.utils.libhttp.downloadservice.utils.IDownloadListener;

/**
 * Descripty:
 * Auth:  邓渊
 * Date: 2017/12/11.21:08
 */

public class DownLoadService {
    //断点续传下载图片和文件
    public static void downloadFile(String url,IDownloadListener iDownloadListener){
        if(iDownloadListener==null|| TextUtils.isEmpty(url)){
            return;
        }
        DownloadManager.getDownloadManager().startDownload(url);
        DownloadManager.getDownloadManager().register(iDownloadListener);
    }

    public static void deleteDownload(String url){
        DownloadManager.getDownloadManager().delDownloadTask(url);
    }

    public static DownloadTask getTaskByDownloadUrl(String url){
        return DownloadManager.getDownloadManager().getTaskByDownloadUrl(url);
    }

    public static void pauseDownloadTask(String url){
        DownloadManager.getDownloadManager().pauseDownloadTask(url);
    }
}
