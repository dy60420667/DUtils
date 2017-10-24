package dy.utils.libhttp;

import dy.utils.libhttp.config.ILibHttp;
import dy.utils.libhttp.config.LibHttpManager;
import dy.utils.libhttp.downloadservice.DownloadManager;
import dy.utils.libhttp.downloadservice.utils.IDownloadListener;

/**
 * Auth : dy
 * Time : 2017/4/13 09:03
 * Email: dymh21342@163.com
 * Description:
 */

public class LibHttpSdk {

    public static void initLibHttp(ILibHttp iLibHttp) {
        LibHttpManager.getInstance().setiLibHttp(iLibHttp);
    }

    //断点续传下载图片和文件
    public static void downloadFile(String url, IDownloadListener iDownloadListener){
        DownloadManager.getDownloadManager().startDownload(url);
        DownloadManager.getDownloadManager().register(iDownloadListener);
    }



}
