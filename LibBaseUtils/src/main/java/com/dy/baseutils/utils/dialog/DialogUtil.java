package com.dy.baseutils.utils.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;

import com.dy.baseutils.utils.dialog.bean.BeanDialog;
import com.dy.baseutils.utils.dialog.bean.BtnNums;
import com.dy.baseutils.utils.dialog.dialog.CommonDialog;


/**
 * Created by dengyuan3 on 2017/9/22.
 */

public class DialogUtil {

    public static void showNormalDialog(Activity activity,BeanDialog beanDialog){
        CommonDialog.showDialog(activity,beanDialog);
    }

    public static void showOneBtnListViewDialog(Activity activity, BaseAdapter adapter, View.OnClickListener onClickListener){
        DialogUtil.showOneBtnListViewDialog(activity,true,"提示","确定",adapter,onClickListener);
    }

   public static void showOneBtnListViewDialog(Activity activity,boolean showTitle,String textTitle, String textbtnOk, BaseAdapter adapter, View.OnClickListener onClickListener){
       BeanDialog dialog = new BeanDialog();
       dialog.setmTitle(textTitle);
       dialog.setmBtnNums(BtnNums.ONE_BTN);
       dialog.setShow_line_top(true);
       dialog.setText_onebtn(textbtnOk);
       dialog.setShowTitle(showTitle);
       dialog.setmAdapter(adapter);
       dialog.setOnPositiveButtonClickListener(onClickListener);
       CommonDialog.showDialog(activity,dialog);
   }

   public static void showOneBtnDialog(Activity activity,String textContent, View.OnClickListener onClickListener){
       DialogUtil.showOneBtnDialog(activity,false,"提示","确定",textContent,onClickListener);
   }

   public static void showOneBtnDialog(Activity activity,boolean ShowTitle,String textTitle, String textOk,String textContent, View.OnClickListener onClickListener){
       BeanDialog dialog = new BeanDialog();
       dialog.setmTitle(textTitle);
       dialog.setShowTitle(false);
       dialog.setmBtnNums(BtnNums.ONE_BTN);
       dialog.setText_onebtn(textOk);
       dialog.setmContent(textContent);
       dialog.setOnPositiveButtonClickListener(onClickListener);
       CommonDialog.showDialog(activity,dialog);
   }

   public static void showTwoBtnDialog(Activity activity,String textContent,View.OnClickListener onOkClickListener){
       DialogUtil.showTwoBtnDialog(activity,false,"提示","确定","取消",textContent,onOkClickListener,null);
   }


    public static void showTwoBtnDialog(Activity activity,boolean showTitle,String textTitle, String textOk,String textNO,String textContent, View.OnClickListener onOkClickListener,View.OnClickListener onNoClickListener){
        BeanDialog dialog = new BeanDialog();
        dialog.setmTitle(textTitle);
        dialog.setShowTitle(showTitle);
        dialog.setmBtnNums(BtnNums.TWO_BTN);
        dialog.setText_twobtn_left(textNO);
        dialog.setText_twobtn_right(textOk);
        dialog.setmContent(textContent);
        dialog.setOnPositiveButtonClickListener(onOkClickListener);
        dialog.setOnNegativeButtonClickListener(onNoClickListener);
        CommonDialog.showDialog(activity,dialog);
    }

}
