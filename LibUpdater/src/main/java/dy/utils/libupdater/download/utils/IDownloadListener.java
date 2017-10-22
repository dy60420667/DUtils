package dy.utils.libupdater.download.utils;

public interface IDownloadListener {
	 void onDownloadSize(long offsize, long size, String speed);
	 void onComplete();
	 void onError(String error);
	 void onCancle();
}
