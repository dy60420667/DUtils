package com.dy.baseutils.module.picturesshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.dy.baseutils.R;
import com.dy.baseutils.module.base.DYBaseActivity;
import com.dy.baseutils.utils.dialog.ToastUtils;

import java.util.ArrayList;

/**
 * Auth : dy
 * Time : 2017/2/3 15:02
 * Email: dymh21342@163.com
 * Description:
 */

public class A_PictureListShow extends DYBaseActivity {
    public static void gotoActivity(Activity context, ArrayList<String> list, int position){
        if(list==null||list.size()==0){
            ToastUtils.showToast(context, R.string.resoure_null);
            return;
        }
        Intent it = new Intent(context,A_PictureListShow.class);
        it.putExtra("list",list);
        it.putExtra("position",position);
        context.startActivity(it);
    }

    @Override
    public Fragment initFragment() {
        Bundle b = getIntent().getExtras();
        ArrayList<String> list  = b.getStringArrayList("list");
        int position =b.getInt("position",0);

        return F_PicturelistShow.createFragment(list,position);
    }
}
