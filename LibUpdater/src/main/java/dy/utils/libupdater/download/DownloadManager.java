package dy.utils.libupdater.download;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dy.utils.libupdater.R;
import dy.utils.libupdater.download.bean.DownloadBean;
import dy.utils.libupdater.download.bean.DownloadStatus;
import dy.utils.libupdater.download.db.DownloadDBManager;
import dy.utils.libupdater.download.utils.DownloadUtils;
import dy.utils.libupdater.download.utils.IDownloadListener;
import dy.utils.libupdater.utils.IUploaderSDKTlmp;

/**
 * Created by dy on 2016/5/3.
 */
public class DownloadManager {
    private static Context context;

    private static final int mPoolSize = 1;//下载队列
    private ExecutorService executorService;


    public static void init(Context context) {
        DownloadManager.context = context;
        DownloadDBManager.init(context);
    }

    public static Context getContext() {
        return context;
    }

    ArrayList<DownloadTask> listCurrent;

    private DownloadManager() {
        listCurrent = new ArrayList<>();
        executorService = Executors.newFixedThreadPool(mPoolSize);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());
    /**
     * 使用之前请先调用init方法初始化
     */
    private  static volatile DownloadManager downloadManager;

    public  static DownloadManager getDownloadManager() {
        if (downloadManager == null) {
            synchronized (DownloadManager.class) {
                if (downloadManager == null) {
                    downloadManager = new DownloadManager();
                }
            }
        }
        return downloadManager;
    }

    //主题监听类,所有需要更新的Adapter需要实现该接口
    private ArrayList<IDownloadListener> listObserver = new ArrayList<>();

    //注册为观察者，一定要记得反注册
    public void register(IDownloadListener idownloadchanger) {
        if (idownloadchanger == null) {
            return;
        }
        if (!listObserver.contains(idownloadchanger)) {
            listObserver.add(idownloadchanger);
        }
    }

    public void unRegister(IDownloadListener idownloadchanger) {
        if (idownloadchanger == null) {
            return;
        }
        if (listObserver.contains(idownloadchanger)) {
            listObserver.remove(idownloadchanger);
        }
    }

    public void startDownload(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl) ) {
            IUploaderSDKTlmp.getInstance().showToast(R.string.updater_download_httpiserror);
            return;
        }

        DownloadBean bean = DownloadDBManager.getDb().getDownloadTask(downloadUrl);
        if (bean == null) {
            bean = new DownloadBean();
            bean.downloadUrl = downloadUrl;
            bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_INIT;
            bean.offsize = 0;
            bean.size = 0;

            delDownloadTask(bean);
        } else if(bean.size<=0){
            bean.size = 0;
        }

        //如果下载失败
        if (bean.downladstatus == DownloadStatus.DOWNLOAD_STATUS_ERROR) {
            delDownloadTask(bean);
            bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_INIT;
            bean.offsize = 0;
            updateDownloadStatus(bean);
        }

        //如果下载成功
        if (bean.downladstatus == DownloadStatus.DOWNLOAD_STATUS_COMPLETED) {
            try {
                File file = new File(bean.saveDirPath, bean.fileName);
                if (file.length() == bean.size&&bean.size!=0) {
                    DownloadUtils.installApp(file);
                    updateDownloadStatus(bean);
                    return;//下载成功，启动安装
                } else {// 如果发现文件大小不对，即下载失败，是否需要重新下载，目前按照重新下载处理
                    delDownloadTask(bean);
                    bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_PREPARE;
                    updateDownloadStatus(bean);
                }
            } catch (Exception e) {// 启动安装失败，是否需要重新下载，目前按照重新下载处理
                delDownloadTask(bean);
                bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_PREPARE;
                updateDownloadStatus(bean);
            }
        }


        //如果已经存在下载队列，则根据当前按钮状态
        for (DownloadTask task : listCurrent) {
            if (task.bean.downloadUrl.equals(downloadUrl)) {
                return;
            }
        }

        // sd card 不可用
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            IUploaderSDKTlmp.getInstance().showToast(R.string.updater_nosdcard);
            return;
        }

        //添加到下载队列
        DownloadTask task = new DownloadTask(bean);
        listCurrent.add(task);
        bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_PREPARE;
        updateDownloadStatus(bean);
        executorService.execute(task);
    }

    public void delDownloadTask(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return;
        }
        DownloadBean bean = DownloadDBManager.getDb().getDownloadTask(downloadUrl);
        if (bean == null) {
            bean = new DownloadBean();
            bean.downloadUrl = downloadUrl;
            bean.fileName = bean.downloadUrl.substring(bean.downloadUrl.lastIndexOf("/") + 1);
            File downloadDir = context.getCacheDir();
            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }
            bean.saveDirPath = downloadDir.getAbsolutePath();
        }
        delDownloadTask(bean);
    }


    //删除下载中和下载完成的任务
    public void delDownloadTask(final DownloadBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.downloadUrl)) {
            return;
        }
        DownloadDBManager.getDb().delete(bean.downloadUrl);
        try {
            String filePath = bean.saveDirPath + "/" + bean.fileName;
            if (filePath != null) {
                File f = new File(filePath);
                if (f.isFile() && f.exists()) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = listCurrent.size() - 1; i >= 0; i--) {
            DownloadTask task = listCurrent.get(i);
            if (task.bean.downloadUrl.equals(bean.downloadUrl)) {
                task.cancleDown();
                listCurrent.remove(task);
            }
        }
    }

    public void pauseDownloadTask(String downloadUrl){
        if (TextUtils.isEmpty(downloadUrl)) {
            return;
        }
        DownloadBean bean = DownloadDBManager.getDb().getDownloadTask(downloadUrl);

        for (int i = listCurrent.size() - 1; i >= 0; i--) {
            DownloadTask task = listCurrent.get(i);
            if (task.bean.downloadUrl.equals(bean.downloadUrl)) {
                task.cancleDown();
                listCurrent.remove(task);
            }
        }
    }



    //通过object_id获取下载任务
    public DownloadTask getTaskByDownloadUrl(String downloadUrl) {
        if (TextUtils.isEmpty(downloadUrl)) {
            return null;
        }
        for (int i = 0; i < listCurrent.size(); i++) {
            DownloadTask task = listCurrent.get(i);
            if (task.bean.downloadUrl.equals(downloadUrl)) {
                return task;
            }
        }
        return null;
    }


    public void updateDownloadStatus(final DownloadBean bean) {
        if (bean.downladstatus == DownloadStatus.DOWNLOAD_STATUS_CANCEL || bean.downladstatus == DownloadStatus.DOWNLOAD_STATUS_ERROR || bean.downladstatus == DownloadStatus.DOWNLOAD_STATUS_COMPLETED || bean.downladstatus == DownloadStatus.DOWNLOAD_STATUS_PAUSE) {
            for (int i = listCurrent.size() - 1; i > -1; i--) {
                DownloadTask task = listCurrent.get(i);
                if (task.bean.downloadUrl.equals(bean.downloadUrl)) {
                    listCurrent.remove(task);
                }
            }
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < listObserver.size(); i++) {
                    IDownloadListener downloadListener = listObserver.get(i);
                    switch (bean.downladstatus) {
                        case DownloadStatus.DOWNLOAD_STATUS_CANCEL:
                            downloadListener.onCancle();
                            break;
                        case DownloadStatus.DOWNLOAD_STATUS_COMPLETED:
                            DownloadTask.downloadTaskCompleted(bean);
                            downloadListener.onComplete();
                            break;
                        case DownloadStatus.DOWNLOAD_STATUS_DOWNLOADING:
                            downloadListener.onDownloadSize(bean.offsize, bean.size, bean.speed_Downling);
                            break;
                        case DownloadStatus.DOWNLOAD_STATUS_PAUSE:
                        case DownloadStatus.DOWNLOAD_STATUS_ERROR:
                            downloadListener.onError(bean.errorString);
                            break;
                        case DownloadStatus.DOWNLOAD_STATUS_INIT:
                            break;
                        case DownloadStatus.DOWNLOAD_STATUS_PREPARE:
                            break;
                        case DownloadStatus.DOWNLOAD_STATUS_START:
                            break;
                    }
                }
            }
        });
    }
}
