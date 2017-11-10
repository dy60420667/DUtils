package dy.utils.libupdater.utils;

import dy.utils.libupdater.download.DownloadManager;

/**
 * Auth : dy
 * Time : 2017/2/22 09:32
 * Email: dymh21342@163.com
 * Description:
 */

public class IUploaderSDKTlmp {

    private IUploaderSDKTlmp(){
    }
    private static volatile IUploaderSDKTlmp instance;
    public static IUploaderSDKTlmp getInstance(){
        if(instance==null){
            synchronized (IUploaderSDKTlmp.class){
                instance = new IUploaderSDKTlmp();
            }
        }
        return instance;
    }


    private IUpdaterSDK iUpdaterSDK;

    public void setIUpdaterSDK(IUpdaterSDK iUpdaterSDK){
        this.iUpdaterSDK = iUpdaterSDK;
    }

    public void showToast(int id){
        showToast(DownloadManager.getContext().getResources().getString(id));
    }

    public void showToast(String text){
        if(iUpdaterSDK==null){
            throw new RuntimeException("no implements interface IUpdaterSDK !");
        }
            iUpdaterSDK.showToast(text);
    }
}
