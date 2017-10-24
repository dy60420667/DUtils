package dy.utils.libhttp.config;

import android.content.Context;

/**
 * Auth : dy
 * Time : 2017/4/13 09:00
 * Email: dymh21342@163.com
 * Description:
 */

public interface ILibHttp {
    /**
     * 获取基Url
     */
    String getBaseUrl();

    LibHttpConfig buildHttpConfig();

    void showToast(String text);

    Context getContext();
}
