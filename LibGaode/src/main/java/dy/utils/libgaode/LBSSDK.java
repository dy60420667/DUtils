package dy.utils.libgaode;

import android.content.Context;

import dy.utils.libgaode.tools.LBSClientGaode;
import dy.utils.libgaode.utils.ILBSSDK;
import dy.utils.libgaode.utils.ILBSSDKTlmp;
import dy.utils.libgaode.utils.LbsInfo;

/**
 * Auth : dy
 * Time : 2016/12/1 16:55
 * Email: dymh21342@163.com
 * Description:
 */

public class LBSSDK {
    /**
     * <!-- 设置key -->
     * <!--<meta-data-->
     * <!--android:name="com.amap.api.v2.apikey"-->
     * <!--android:value="请输入你申请的KEY" />-->
     */
    public static void startLBS(final Context context) {
        LBSClientGaode.getInstance().startLBS(context);
    }

    /**
     * 一般不需要手动调用stop
     * 系统在定位结束会自动stop
     */
    public static void stopLBS() {
        LBSClientGaode.getInstance().stopLBS();
    }

    public static void setIlbssdk(ILBSSDK ilbssdk) {
        ILBSSDKTlmp.getInstance().setIlbssdk(ilbssdk);
    }

    public static LbsInfo getLbsInfo() {
       return ILBSSDKTlmp.getInstance().getLbsInfo();
    }



}
