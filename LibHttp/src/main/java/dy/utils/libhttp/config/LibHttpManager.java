package dy.utils.libhttp.config;

/**
 * Auth : dy
 * Time : 2017/4/13 09:01
 * Email: dymh21342@163.com
 * Description:
 */

public class LibHttpManager {
    private LibHttpManager(){
    }
    private static volatile LibHttpManager instance;
    public static LibHttpManager getInstance(){
        if(instance==null){
            synchronized (LibHttpManager.class){
                instance = new LibHttpManager();
            }
        }
        return instance;
    }


    ILibHttp iLibHttp;
    public void setiLibHttp(ILibHttp iLibHttp) {
        this.iLibHttp = iLibHttp;
    }

    public ILibHttp getiLibHttp(){
        if(iLibHttp==null){
            throw new NullPointerException("iLibHttp is not init!!!!");
        }
        return iLibHttp;
    }

    public String getBaseUrl() {
        checkILibHttp();
        return iLibHttp.getBaseUrl();
    }

    public long getTIME_OUT_(){
        checkILibHttp();
        if(iLibHttp.buildHttpConfig()!=null){
            return iLibHttp.buildHttpConfig().getTIME_OUT_();
        }else{
            return LibHttpConfig.getDefault().getTIME_OUT_();
        }
    }
    public int getPAGE_SIZE() {
        checkILibHttp();
        if(iLibHttp.buildHttpConfig()!=null){
            return iLibHttp.buildHttpConfig().getPAGE_SIZE();
        }else{
            return LibHttpConfig.getDefault().getPAGE_SIZE();
        }
    }
    public void checkILibHttp(){
        if(iLibHttp==null){
            throw new RuntimeException("no implements interface ILibHttp ");
        }
    }
}
