package dy.utils.libupdater;

import android.app.Activity;
import android.text.TextUtils;

import dy.utils.libupdater.bean.VersionBean;
import dy.utils.libupdater.dialog.VersionDialog;
import dy.utils.libupdater.download.DownloadManager;
import dy.utils.libupdater.download.db.DownloadDBManager;
import dy.utils.libupdater.utils.IUpdaterSDK;
import dy.utils.libupdater.utils.IUploaderSDKTlmp;
import dy.utils.libupdater.utils.VersionPreferences;


/**
 * Created by dy on 2016/5/4.
 */
public class UpdaterSDK {
    public static void setiUpdaterSDK(IUpdaterSDK iUpdaterSDK) {
        IUploaderSDKTlmp.getInstance().setIUpdaterSDK(iUpdaterSDK);
    }


    /**
     * @param context
     * @param bean
     * @param isUserClickCheckUpdater 用户是否点击了检查更新
     */
    public static void checkUpdater(Activity context, VersionBean bean, boolean isUserClickCheckUpdater) {
        if (context == null || context.isFinishing()) {
            return;
        }
        //初始化变量环境
        DownloadManager.init(context.getApplication());
        VersionPreferences.init(context.getApplication());

        //当前版本为最新版本
        if (bean == null || TextUtils.isEmpty(bean.content) || TextUtils.isEmpty(bean.url) || TextUtils.isEmpty(bean.versionname)) {
            if (isUserClickCheckUpdater) {//如果是检查更新的话，需要提示用户当前没有更新
                IUploaderSDKTlmp.getInstance().showToast(R.string.updater_no_update);
            }

            DownloadDBManager.getDb().delete(bean.url);
            VersionPreferences.getInstance().clearPerferences();
            return;
        }

        if (!bean.url.toLowerCase().startsWith("http")) {
            bean.url = "http://" + bean.url;
        }

        //设置是否为检查更新
        bean.setUserClickCheckUpdater(isUserClickCheckUpdater);
        //已忽略该版本
        if (VersionPreferences.getInstance().getIgnore(bean.versionname) && !bean.isUserClickCheckUpdater()) {//如果忽略掉该版本，则不提示更新
            return;
        }

        VersionDialog.showAppVersionCheckDialog(context, bean);
    }
}

