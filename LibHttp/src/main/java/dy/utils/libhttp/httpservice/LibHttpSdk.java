package dy.utils.libhttp.httpservice;

import dy.utils.libhttp.httpservice.config.ILibHttp;
import dy.utils.libhttp.httpservice.config.LibHttpManager;

/**
 * Auth : dy
 * Time : 2017/4/13 09:03
 * Email: dymh21342@163.com
 * Description:
 */

public class LibHttpSdk {

    public static void initLibHttp(ILibHttp iLibHttp) {
        LibHttpManager.getInstance().setiLibHttp(iLibHttp);
    }

}
