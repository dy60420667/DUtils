package dy.utils.libgaode.utils;

/**
 * Auth : dy
 * Time : 2017/4/13 10:15
 * Email: dymh21342@163.com
 * Description:
 */

public class ILBSSDKTlmp {
    public volatile  static ILBSSDKTlmp instance;
    private ILBSSDKTlmp(){
    }

    public static ILBSSDKTlmp getInstance(){
        if(instance==null){
            synchronized (ILBSSDKTlmp.class){
                if(instance==null){
                    instance = new ILBSSDKTlmp();
                }
            }
        }
        return instance;
    }

    private ILBSSDK ilbssdk;
    public void setIlbssdk(ILBSSDK ilbssdk){
        this.ilbssdk = ilbssdk;
    }

    public void postEventLbsInfo(LbsInfo lbsInfo){
        checkLBS();
        ilbssdk.postEventLbsInfo(lbsInfo);
    }

    public int getTime_interval(){
        checkLBS();
        return ilbssdk.initLBSConfig().getTime_interval();
    }

    public int getTimes_fail_num(){
        checkLBS();
        return ilbssdk.initLBSConfig().getTimes_fail_num();
    }

    public void checkLBS(){
        if(ilbssdk==null){
            throw new RuntimeException("no implements interface ILBSSDK !");
        }
    }


    private LbsInfo info;
    public LbsInfo getLbsInfo() {
        return info;
    }

    public void setLBSInfo(LbsInfo info){
        this.info = info;
    }





}
