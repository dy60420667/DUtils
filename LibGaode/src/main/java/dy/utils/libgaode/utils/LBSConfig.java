package dy.utils.libgaode.utils;

/**
 * Auth : dy
 * Time : 2017/4/13 11:15
 * Email: dymh21342@163.com
 * Description:
 */

public class LBSConfig {


    public static LBSConfig getDefalut(){
        return new LBSConfig();
    }

    private int time_interval = 2000;//定位时间间隔
    private int times_fail_num = 5;//定位失败次数,

    public int getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(int time_interval) {
        this.time_interval = time_interval;
    }

    public int getTimes_fail_num() {
        return times_fail_num;
    }

    public void setTimes_fail_num(int times_fail_num) {
        this.times_fail_num = times_fail_num;
    }


}
