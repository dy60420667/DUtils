package dy.utils.libhttp.downloadservice.utils;

import dy.utils.libhttp.downloadservice.bean.DownloadBean;

public interface IDownloadListener {
	 void onDownloadSize(long offsize, long size, String speed);
	 void onComplete();
	 void onError(String error);
	 void onCancle();
	 String getDownLoadUrl();
}
