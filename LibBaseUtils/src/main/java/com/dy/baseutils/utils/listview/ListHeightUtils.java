package com.dy.baseutils.utils.listview;

import android.content.Context;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.dy.baseutils.utils.system.DevicesUtils;

/**
 * Created by xuchangqing on 2017/1/9.
 */

public class ListHeightUtils {

    public static  int setListViewHeightBasedOnChildren(Context context, ListView listView) {
        int totalHeight = DevicesUtils.dp2px(context, 80);
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return 0;
            }

            for (int i = 0; i < 1; i++) {
                View listItem = listAdapter.getView(i, null, listView);
                if (null != listItem) {
                    listItem.measure(
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    totalHeight = listItem.getMeasuredHeight();
                }

            }

//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight
//                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
            return totalHeight;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return totalHeight;
    }


}
