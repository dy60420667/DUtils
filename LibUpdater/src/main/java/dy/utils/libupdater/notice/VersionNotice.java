package dy.utils.libupdater.notice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.widget.RemoteViews;

import dy.utils.libupdater.R;
import dy.utils.libupdater.bean.VersionBean;
import dy.utils.libupdater.download.DownloadManager;
import dy.utils.libupdater.download.utils.IDownloadListener;
import dy.utils.libupdater.utils.ToolSpan;
import dy.utils.libupdater.utils.ToolUtils;

/**
 * Created by dy on 2016/5/5.
 */
public class VersionNotice implements IDownloadListener {
    private Context context;

    private VersionNotice() {
    }

    private  static volatile VersionNotice instance;

    public static VersionNotice getInstance() {
        if (instance == null) {
            synchronized (VersionNotice.class) {
                if (instance == null) {
                    instance = new VersionNotice();
                }
            }
        }
        return instance;
    }

    private static final int NOTIFY_ID = 0;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder builder;
    private RemoteViews contentView;
    private VersionBean bean;

    public void clearNotification(Context context) {
        if(mNotificationManager==null){
            mNotificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        }
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFY_ID);
        }
    }

    /**
     * 创建通知
     */
    public void setUpNotification(Context context, VersionBean bean) {
        this.context = context;
        this.bean = bean;
        mNotificationManager = (NotificationManager) context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        if (builder == null) {
            contentView = new RemoteViews(context.getPackageName(), R.layout.download_notification_layout);
            contentView.setImageViewResource(R.id.image_tips,context.getApplicationInfo().icon);

            builder = new NotificationCompat.Builder(context);
            builder.setContent(contentView)
                    .setWhen(System.currentTimeMillis())
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setSmallIcon(context.getApplicationInfo().icon)
                    .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                    .setOngoing(true);// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)

            try{
                changeSmallIcon(context,builder);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        contentView.setTextViewText(R.id.name, ToolUtils.getAppname(context));
        contentView.setTextViewText(R.id.tv_progress, context.getString(R.string.updater_loading));

        builder.setOngoing(true);
        Notification notification = builder.build();
        contentView.setOnClickPendingIntent(R.id.layout_root, getContentIntent("0"));

        mNotificationManager.notify(NOTIFY_ID, notification);
        DownloadManager.getDownloadManager().register(this);
    }

    private void changeSmallIcon(Context context, NotificationCompat.Builder builder) {
        if(bean==null){
            return;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH ){
            if(bean.getIcon_small_drawable_5_0()!=0){
                //5.0以上系统采用纸片化图片
                builder.setSmallIcon(bean.getIcon_small_drawable_5_0());
            }else{
                builder.setSmallIcon(context.getApplicationInfo().icon);
            }
        }else {
            builder.setSmallIcon(context.getApplicationInfo().icon);
        }
    }


    @Override
    public void onDownloadSize(long offsize, long size, String speed) {
        try {
            contentView.setTextViewText(R.id.name, ToolUtils.getAppname(context));
            contentView.setImageViewResource(R.id.image_tips,context.getApplicationInfo().icon);
            contentView.setTextViewText(R.id.tv_progress, ToolUtils.getSize(offsize) + "/" + ToolUtils.getSize(size));
            contentView.setProgressBar(R.id.progressbar, (int) size, (int) offsize, false);
            builder.setOngoing(true);
            Notification notification = builder.build();

            mNotificationManager.notify(NOTIFY_ID, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        try {
            mNotificationManager.cancel(NOTIFY_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownloadManager.getDownloadManager().unRegister(this);
    }

    @Override
    public void onError(String error) {
        try {
            String name = ToolUtils.getAppname(context);
            SpannableString sb = new SpannableString(name + "(" + error + ")");
            sb = ToolSpan.addForeColorSpan(sb, name.length(), sb.length(), ContextCompat.getColor(context,R.color.updater_color_item_normal));
            builder.setOngoing(false);
            contentView.setOnClickPendingIntent(R.id.layout_root, getContentIntent("1"));
            contentView.setImageViewResource(R.id.image_tips,context.getApplicationInfo().icon);
            contentView.setTextViewText(R.id.name, sb);
            contentView.setTextViewText(R.id.tv_progress, "");
            Notification notification = builder.build();

            mNotificationManager.notify(NOTIFY_ID, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownloadManager.getDownloadManager().unRegister(this);
    }

    @Override
    public void onCancle() {
        try {
            mNotificationManager.cancel(NOTIFY_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DownloadManager.getDownloadManager().unRegister(this);
    }

    //1点击重试.0表示下载中
    private PendingIntent getContentIntent(String isTips) {
        Intent intent = new Intent(context, NotificationClickReceiver.class);
        intent.putExtra("bean", bean);
        intent.putExtra("tip", isTips);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, (int) (System.currentTimeMillis() / 1000), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return contentIntent;
    }


}
