package dy.utils.libupdater.notice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dy.utils.libupdater.bean.VersionBean;
import dy.utils.libupdater.utils.VersionPreferences;
import dy.utils.libupdater.download.DownloadManager;


public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        VersionBean bean = (VersionBean) intent.getSerializableExtra("bean");
        String tip = intent.getStringExtra("tip");

        if(DownloadManager.getContext()==null){
            DownloadManager.init(context.getApplicationContext());
            VersionPreferences.init(context.getApplicationContext());
            VersionNotice.getInstance().clearNotification(context);

            VersionNotice.getInstance().setUpNotification(context, bean);
            DownloadManager.getDownloadManager().startDownload(bean.url);
            return;
        }

        if (tip.equals("0")) {
            return;
        }
        VersionNotice.getInstance().clearNotification(context);

        VersionNotice.getInstance().setUpNotification(context, bean);
        DownloadManager.getDownloadManager().startDownload(bean.url);
    }
}