package dy.utils.libhttp.downloadservice.manager;

import android.text.TextUtils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import dy.utils.libhttp.R;
import dy.utils.libhttp.downloadservice.bean.DownloadBean;
import dy.utils.libhttp.downloadservice.bean.DownloadStatus;
import dy.utils.libhttp.downloadservice.db.DownloadDBManager;


public class DownloadTask implements Runnable {
    public DownloadBean bean;

    /**
     * 构建文件下载器
     */
    public DownloadTask(DownloadBean bean) {
        this.bean = bean;
    }

    /**
     * 更新订阅者数据
     */
    private void noticeObservers() {
        DownloadManager.getDownloadManager().updateDownloadStatus(bean);
        insertOrUpdataDownloadTable(this);
    }

    private void insertOrUpdataDownloadTable(DownloadTask task) {
        if (task == null || task.bean == null || TextUtils.isEmpty(task.bean.downloadUrl)) {
            return;
        }
        DownloadDBManager.getDb().insertOrReplaceDownloadBean(task.bean);
    }


    @Override
    public void run() {
        HttpURLConnection http = null;
        InputStream inStream = null;
        RandomAccessFile file = null;
        try {
            if (TextUtils.isEmpty(bean.fileName) || TextUtils.isEmpty(bean.saveDirPath)) {
//                bean.fileName = bean.downloadUrl.substring(bean.downloadUrl.lastIndexOf("/") + 1);
                bean.fileName = "cnhubei_"+ System.currentTimeMillis()+".apk";
                File downloadDir = DownloadManager.getContext().getCacheDir();
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs();
                }
                bean.saveDirPath = downloadDir.getAbsolutePath();
            }

            File fileTemp = new File(bean.saveDirPath);
            if (!fileTemp.exists()) {
                fileTemp.mkdirs();
            }

            if (bean.offsize == 0) {
                File f = new File(bean.saveDirPath, bean.fileName);
                if (f.isFile() && f.exists()) {
                    f.delete();
                }
            }

            file = new RandomAccessFile(bean.saveDirPath + "/" + bean.fileName, "rwd");
            if (file.length() == bean.size && bean.size > 0) {
                bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_COMPLETED;
                noticeObservers();//下载完成
                return;
            }
            bean.offsize = file.length();

            URL downUrl = new URL(bean.downloadUrl);
            //使用Get方式下载
            http = (HttpURLConnection) downUrl.openConnection();
            http.setConnectTimeout(15 * 1000);
            http.setRequestMethod("GET");
            http.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*");
            http.setRequestProperty("Accept-Language", "zh-CN");
            http.setRequestProperty("Referer", downUrl.toString());
            http.setRequestProperty("Charset", "UTF-8");

            http.setRequestProperty("Range", "bytes=" + bean.offsize + "-");//设置获取实体数据的范围
            http.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            http.setRequestProperty("Connection", "Keep-Alive");
            http.setRequestProperty("Accept-Encoding", "identity"); //加上这句话解决getcontentlength=0的问题


            if (bean.size == 0) {
                bean.size = http.getContentLength() + bean.offsize;//取文件的大小
            }
            inStream = http.getInputStream();

            http.getResponseCode();
            byte[] buffer = new byte[1024];
            file.seek(bean.offsize);

            bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_DOWNLOADING;
            int offset = 0;
            int buffOffset = 0;
            long starttime = System.currentTimeMillis();
            while ((offset = inStream.read(buffer)) != -1 && bean.downladstatus != DownloadStatus.DOWNLOAD_STATUS_CANCEL && bean.downladstatus != DownloadStatus.DOWNLOAD_STATUS_PAUSE) {
                file.write(buffer, 0, offset);
                bean.offsize += offset;
                buffOffset += offset;
                long endtime = System.currentTimeMillis();

                if ((endtime - starttime) > 500) {
                    bean.speed_Downling = buffOffset * 1000 / ((endtime - starttime) * 1024) + "kb/s";
                    buffOffset = 0;
                    starttime = endtime;
                    noticeObservers();//下载状态发生改变
                }
            }

            if (bean.offsize == bean.size) {//下载完成
                bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_COMPLETED;
                downloadTaskCompleted(bean);
            } else if (bean.offsize > bean.size) {//下载出错
                bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_ERROR;
                bean.errorString = DownloadManager.getContext().getString(R.string.downloadservice_error);
            }
        } catch (Exception e){
            e.printStackTrace();
            bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_PAUSE;
            bean.errorString = DownloadManager.getContext().getString(R.string.downloadservice_error);
        }
        finally {
            try {
                http.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                inStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                file.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        noticeObservers();//下载状态发生改变
    }



    //下载完成，触发安装
    public static void downloadTaskCompleted(DownloadBean bean) {
        try {
            ArrayList<DownloadTask> listCurrent = DownloadManager.getDownloadManager().listCurrent;
            for (int i = listCurrent.size() - 1; i >= 0; i--) {
                DownloadTask task = listCurrent.get(i);
                DownloadBean beanItem = task.bean;
                if (beanItem.downloadUrl.equals(bean.downloadUrl)) {
                    listCurrent.remove(task);
                }
            }
//            File fileThis = new File(bean.saveDirPath, bean.fileName);
//            DownloadUtils.installApp(fileThis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancleDown() {
        bean.downladstatus = DownloadStatus.DOWNLOAD_STATUS_CANCEL;
    }
}
