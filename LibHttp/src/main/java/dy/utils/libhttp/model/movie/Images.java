package dy.utils.libhttp.model.movie;

import android.text.TextUtils;

/**
 * Auth : dy
 * Time : 2017/2/3 10:56
 * Email: dymh21342@163.com
 * Description:
 */

public class Images {
    public String large;
    public String medium;
    public String small;

    public String getUrl(){
        if(!TextUtils.isEmpty(medium)){
            return medium;
        }
        if(!TextUtils.isEmpty(small)){
            return small;
        }
        return large;

    }
}
