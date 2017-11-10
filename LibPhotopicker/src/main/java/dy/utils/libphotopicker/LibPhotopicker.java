package dy.utils.libphotopicker;

import android.app.Activity;

import dy.utils.libphotopicker.views.PhotoPickerIntent;
import dy.utils.libphotopicker.views.SelectModel;

/**
 * Auth : dy
 * Time : 2017/2/9 16:28
 * Email: dymh21342@163.com
 * Description:
 */

public class LibPhotopicker {

    public static void goToPickerActivity(Activity context,boolean isShowCamera,int picNums,int result_code){
        PhotoPickerIntent intent = new PhotoPickerIntent(context);
        if(picNums==1){
            intent.setSelectModel(SelectModel.SINGLE);
        }else{
            intent.setSelectModel(SelectModel.MULTI);
        }
        intent.setMaxTotal(picNums);
        intent.setShowCarema(isShowCamera);
        context.startActivityForResult(intent,result_code);
    }


}
