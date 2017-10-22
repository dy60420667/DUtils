package dy.utils.libhttp.config;

import retrofit2.http.Part;

/**
 * Auth : dy
 * Time : 2017/4/13 09:34
 * Email: dymh21342@163.com
 * Description:
 */

public class LibHttpConfig {
    private LibHttpConfig(){
    }
    public static LibHttpConfig getDefault(){
        return new LibHttpConfig();
    }

   private long TIME_OUT_ = 15;
    private int PAGE_SIZE = 20;

    public long getTIME_OUT_() {
        return TIME_OUT_;
    }

    public void setTIME_OUT_(long TIME_OUT_) {
        this.TIME_OUT_ = TIME_OUT_;
    }

    public int getPAGE_SIZE() {
        return PAGE_SIZE;
    }

    public void setPAGE_SIZE(int PAGE_SIZE) {
        this.PAGE_SIZE = PAGE_SIZE;
    }


}
