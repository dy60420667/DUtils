package com.dy.baseutils.module.newsshow;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dy.baseutils.R;
import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.module.newsshow.bean.ViewMsg;

public class F_View_msg extends DYBaseFragment {
    public static F_View_msg getFragment(ViewMsg item){
        F_View_msg f = new F_View_msg();
        Bundle bundle = new Bundle();
        bundle.putSerializable("item",item);
        f.setArguments(bundle);
        return f;
    }


    @Override
    public int getFragmentLayout() {
        return R.layout.f_view_detail;
    }

    public void initView() {
        ViewMsg item= (ViewMsg) getArguments().getSerializable("item");

        TextView text_title = (TextView) rootView.findViewById(R.id.text_title);
        TextView text_subtitle = (TextView) rootView.findViewById(R.id.text_subtitle);
        TextView text_subtitle_right = (TextView) rootView.findViewById(R.id.text_subtitle_right);
        TextView tv_msm = (TextView) rootView.findViewById(R.id.tv_msm);

        text_title.setText(item.title);
        text_subtitle.setText(item.text_subtitle_left);
        text_subtitle_right.setText(item.text_subtitle_right);
        tv_msm.setText(item.msg);

    }
}
