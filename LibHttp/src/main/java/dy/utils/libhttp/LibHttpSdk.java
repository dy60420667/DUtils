package dy.utils.libhttp;

import dy.utils.libhttp.config.ILibHttp;
import dy.utils.libhttp.config.ILibHttpTlmp;

/**
 * Auth : dy
 * Time : 2017/4/13 09:03
 * Email: dymh21342@163.com
 * Description:
 */

public class LibHttpSdk {
    public static void setiLibHttp(ILibHttp iLibHttp) {
        ILibHttpTlmp.getInstance().setiLibHttp(iLibHttp);
    }
}
