package dy.utils.libupdater.dialog;

import android.app.Dialog;
import android.content.Context;


/**
 * Auth : dy
 * Time : 2016/12/23 16:34
 * Email: dymh21342@163.com
 * Description:不能强制退出的对话框
 */

public class ForceDialog extends Dialog {
    public ForceDialog(Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(true);
    }



    private boolean isCancelable = true;
    @Override
    public void setCancelable(boolean flag) {
        super.setCancelable(flag);
        isCancelable = flag;
    }

    private IForeDialogBackPressed iForeDialogBackPressed;

    public void setiForeDialogBackPressed(IForeDialogBackPressed foreDialogBackPressed){
        this.iForeDialogBackPressed = foreDialogBackPressed;
    }

    public static interface IForeDialogBackPressed{
       void  onbackpressed();
    }


    @Override
    public void onBackPressed() {
        if(isCancelable){
            super.onBackPressed();
        }else  if(iForeDialogBackPressed!=null){
            iForeDialogBackPressed.onbackpressed();
        }
    }

}
