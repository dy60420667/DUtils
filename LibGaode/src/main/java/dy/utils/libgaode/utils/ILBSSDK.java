package dy.utils.libgaode.utils;

import dy.utils.libgaode.utils.LbsInfo;

/**
 * Auth : dy
 * Time : 2017/4/13 10:12
 * Email: dymh21342@163.com
 * Description:
 */

public interface ILBSSDK {
    void postEventLbsInfo(LbsInfo lbsInfo);

    /**
     * 初始化配置信息，如果不需要自定义配置，可以直接返回默认的LBSConfig
     * @return
     */
    LBSConfig initLBSConfig();

}
