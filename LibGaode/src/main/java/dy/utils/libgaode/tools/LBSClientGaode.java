package dy.utils.libgaode.tools;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import dy.utils.libgaode.utils.ILBSSDKTlmp;
import dy.utils.libgaode.utils.LbsInfo;


/**
 * Auth : dy
 * Time : 2016/12/1 17:18
 * Email: dymh21342@163.com
 * Description:
 */

public class LBSClientGaode implements AMapLocationListener {
    private volatile static LBSClientGaode instance;

    public static LBSClientGaode getInstance() {
        if (instance == null) {
            synchronized (LBSClientGaode.class) {
                if (instance == null) {
                    instance = new LBSClientGaode();
                }
            }
        }
        return instance;
    }

    private AMapLocationClient mLocationClient;

    private LBSClientGaode() {
    }

    private Context context;

    private void initLbsClient() {
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(this);

        //声明mLocationOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(ILBSSDKTlmp.getInstance().getTime_interval());
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }


    private int times_num = 0;//定位次数

    @Override
    public void onLocationChanged(AMapLocation location) {
        Log.i("xx", "errorCode:" + location.getErrorCode());
        times_num++;
        if (location == null || location.getErrorCode() != 0) {
            Log.i("xx", "errorDetail:" + location.getErrorInfo());
            onError(location);
            return;
        }
        final LbsInfo lbsInfo = new LbsInfo();
        lbsInfo.setLongitude(location.getLongitude() + "");
        lbsInfo.setLatitude(location.getLatitude() + "");
        String city = location.getCity();
        lbsInfo.setCityName(TextUtils.isEmpty(city) ? "武汉"
                : city);
        lbsInfo.setProvince(TextUtils.isEmpty(location.getProvince()) ? "湖北省"
                : location.getProvince());
        lbsInfo.setCityCode(TextUtils.isEmpty(location.getCity()) ? "4201" : location.getAdCode());

        lbsInfo.setAddress(TextUtils.isEmpty(location.getAddress()) ? "" : location.getAddress());
        stopLBS();

        ILBSSDKTlmp.getInstance().postEventLbsInfo(lbsInfo);
        ILBSSDKTlmp.getInstance().setLBSInfo(lbsInfo);
//      RxBus.getDefault().post(lbsInfo);

//        LogUtils.e("onLocationChanged mLocation City:" + location.getCity() + "mLocation Province:" + location.getProvince());
//        LogUtils.e("onLocationChanged mLocation AdCode:" + location.getAdCode());
//        LogUtils.e("onLocationChanged mLocation Latitude:" + location.getLatitude() + ",mLocation Longitude:" + location.getLongitude());
//        LogUtils.e("onLocationChanged mLocation address:" + location.getAddress());
    }

    private void onError(final AMapLocation location) {
        if (times_num < ILBSSDKTlmp.getInstance().getTimes_fail_num()) {
            return;
        }
//        RxBus.getDefault().post(LibGaode.getInstance().getLbsInfo());
        stopLBS();
    }

    public void startLBS(Context context) {
        this.context = context;
        if (mLocationClient == null) {
            initLbsClient();
        }
        if (mLocationClient != null && !mLocationClient.isStarted()) {
            //启动定位
            mLocationClient.startLocation();
            Log.i("xx", "开启高德定位");
            times_num = 0;
        }
    }

    public void stopLBS() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位
            mLocationClient.onDestroy();//销毁定位客户端。
        }
        this.context = null;
        Log.i("xx", "终止高德定位");
        mLocationClient = null;
    }
}
