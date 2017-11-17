package dy.utils.libupdater.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import dy.utils.libupdater.R;
import dy.utils.libupdater.bean.UpdateTypeEnum;
import dy.utils.libupdater.bean.VersionBean;
import dy.utils.libupdater.utils.IUploaderSDKTlmp;
import dy.utils.libupdater.utils.VersionPreferences;
import dy.utils.libupdater.download.DownloadManager;
import dy.utils.libupdater.download.utils.IDownloadListener;
import dy.utils.libupdater.notice.VersionNotice;
import dy.utils.libupdater.utils.ToolUtils;

/**
 * Created by dy on 2016/5/3.
 */
public class VersionDialog {


    static long waitTime = 3000;// 3秒
    static long touchTime = 0;
    public static void showAppVersionCheckDialog(final Activity context, final VersionBean item) {
        if (item == null)
            return;
        if (context == null || context.isFinishing()) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View v;
        if (item.isForce()) {//强制升级
            v = inflater.inflate(R.layout.tool_dialog_msg_twobtn_force, null);// 得到加载view
        } else {
            v = inflater.inflate(R.layout.tool_dialog_msg_twobtn, null);// 得到加载view
        }

        TextView tvMsg = (TextView) v.findViewById(R.id.tv_msm);
        tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());

        StringBuffer sb = new StringBuffer();
        sb.append(context.getString(R.string.updater_version_name) + item.versionname);
        sb.append("\n");
        sb.append(context.getString(R.string.updater_version_descripty) +"\n"+ item.content);
        sb.append("\n");
        tvMsg.setText(sb);

        Button btnNO = (Button) v.findViewById(R.id.btn_no);
        Button btn_ok = (Button) v.findViewById(R.id.btn_ok);

        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkBox);

        if(item.isUserClickCheckUpdater()){
            checkBox.setVisibility(View.GONE);
        }


        final ForceDialog dialogCreateAppVersion = new ForceDialog(context, R.style.Theme_CustomDialog);// 创建自定义样式dialog
        dialogCreateAppVersion.setContentView(v, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));// 设置布局
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (item.isForce()) {
                    showProgressDialog(context, item);
                } else {
                    dialogCreateAppVersion.dismiss();
                    VersionNotice.getInstance().setUpNotification(context,item);
                    DownloadManager.getDownloadManager().startDownload(item.url);
                }
            }
        });
        btnNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialogCreateAppVersion.dismiss();
                DownloadManager.getDownloadManager().pauseDownloadTask(item.url);
            }
        });
        dialogCreateAppVersion.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if( !VersionPreferences.getInstance().getIgnore(item.versionname)){
                    VersionPreferences.getInstance().setIgnore(checkBox.isChecked(), item.versionname);
                }
            }
        });

        if (item.isForce()) {//如果强制更新，不显示忽略该版本
            dialogCreateAppVersion.setCancelable(false);
//            dialogCreateAppVersion.setCanceledOnTouchOutside(false);
            dialogCreateAppVersion.setiForeDialogBackPressed(new ForceDialog.IForeDialogBackPressed() {
                @Override
                public void onbackpressed() {
                    if (context.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return;
                    }
                    long currentTime = System.currentTimeMillis();
                    if ((currentTime - touchTime) >= waitTime) {
                        IUploaderSDKTlmp.getInstance().showToast(R.string.finish_app);
                        touchTime = currentTime;
                    } else {
                        context.finish();
                    }
                }
            });
        }
        dialogCreateAppVersion.show();
    }




    public static void showProgressDialog(final Activity context, final VersionBean item) {
        if (item == null)
            return;
        if (context == null || context.isFinishing()) {
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.updater_dialog_progress, null);// 得到加载view
        final ProgressBar progressbar = (ProgressBar) v.findViewById(R.id.progressbar);

        final TextView text_speed = (TextView) v.findViewById(R.id.text_speed);
        final TextView text_size = (TextView) v.findViewById(R.id.text_size);
        final TextView tv_title_error = (TextView) v.findViewById(R.id.tv_title_error);
        final Button btn_ok = (Button) v.findViewById(R.id.btn_ok);
        btn_ok.setVisibility(View.GONE);
        tv_title_error.setVisibility(View.GONE);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.getDownloadManager().startDownload(item.url);
                tv_title_error.setVisibility(View.GONE);
                btn_ok.setVisibility(View.GONE);
                text_speed.setText(context.getString(R.string.updater_loading));
            }
        });

        final ForceDialog dialogCreateAppVersion = new ForceDialog(context, R.style.Theme_CustomDialog);// 创建自定义样式dialog
        dialogCreateAppVersion.setContentView(v);
        dialogCreateAppVersion.setCancelable(false);

        final IDownloadListener iDownloadListener = new IDownloadListener() {
            @Override
            public void onDownloadSize(long offsize, long size, String speed) {
//                Log.i("xx", "onDownloadSize:" + offsize + "," + size);
                progressbar.setMax((int) size);
                progressbar.setProgress((int) offsize);
                text_speed.setText(speed);
                text_size.setText(ToolUtils.getSize(offsize) + "/" + ToolUtils.getSize(size));
            }

            @Override
            public void onComplete() {
                dialogCreateAppVersion.dismiss();
            }

            @Override
            public void onError(String error) {
                tv_title_error.setText(error);
                text_speed.setText(context.getString(R.string.updater_pause));
                btn_ok.setVisibility(View.VISIBLE);
                tv_title_error.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancle() {
                dialogCreateAppVersion.dismiss();
            }
        };
        if (item.isForce()) {//如果强制更新，不显示忽略该版本
            dialogCreateAppVersion.setCancelable(false);
//            dialogCreateAppVersion.setCanceledOnTouchOutside(false);
            dialogCreateAppVersion.setiForeDialogBackPressed(new ForceDialog.IForeDialogBackPressed() {
                @Override
                public void onbackpressed() {
                    if (context.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        return;
                    }
                    long currentTime = System.currentTimeMillis();
                    if ((currentTime - touchTime) >= waitTime) {
                        IUploaderSDKTlmp.getInstance().showToast(R.string.finish_app);
                        touchTime = currentTime;
                    } else {
                        context.finish();
                    }
                }
            });
        }

        DownloadManager.getDownloadManager().register(iDownloadListener);

        dialogCreateAppVersion.show();
        dialogCreateAppVersion.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DownloadManager.getDownloadManager().unRegister(iDownloadListener);
            }
        });
        DownloadManager.getDownloadManager().startDownload(item.url);
    }


}
