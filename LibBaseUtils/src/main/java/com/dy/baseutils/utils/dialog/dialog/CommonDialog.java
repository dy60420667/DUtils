package com.dy.baseutils.utils.dialog.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.baseutils.R;
import com.dy.baseutils.utils.dialog.bean.BeanDialog;
import com.dy.baseutils.utils.dialog.bean.BtnNums;
import com.dy.baseutils.utils.listview.ListHeightUtils;

/**
 * Created by dengyuan3 on 2017/9/22.
 */

public class CommonDialog extends Dialog {

    /**
     * @param mContext 上下文环境
     * @param mBeanDialog  新的model请在ModuleBeanDialog中定义
     */
    public static void showDialog(Activity mContext, BeanDialog mBeanDialog){
        if(mContext==null){
            return;
        }
        if(mBeanDialog==null){
            return;
        }
        CommonDialog dialog = new CommonDialog(mContext);
        dialog.setmBeanDialog(mBeanDialog);
        dialog.show();
    }


    private BeanDialog mBeanDialog;

    public CommonDialog(@NonNull Activity mContext) {
        super(mContext, R.style.Dialog);
        setContentView(R.layout.libbase_dialog_common);

        initView();
    }

    public void setmBeanDialog(BeanDialog mBeanDialog) {
        if (mBeanDialog == null) {
            return;
        }
        this.mBeanDialog = mBeanDialog;
        initTitleView();
        initButtomView();
        initButtomViewColor();
        initContent();
    }

    private void initButtomViewColor() {
        if(mBeanDialog.getColor_onebtn_text()!=0){
            tv_onebtn.setTextColor(mBeanDialog.getColor_onebtn_text());
        }
        if(mBeanDialog.getResource_onebtn_background()!=0){
            tv_onebtn.setBackgroundResource(mBeanDialog.getResource_onebtn_background());
        }

        if(mBeanDialog.getColor_townbtn_left_text()!=0){
            tv_two_left.setTextColor(mBeanDialog.getColor_townbtn_left_text());
        }

        if(mBeanDialog.getColor_townbtn_left_text()!=0){
            tv_two_left.setTextColor(mBeanDialog.getColor_townbtn_left_text());
        }

        if(mBeanDialog.getResource_townbtn_left_background()!=0){
            tv_two_left.setBackgroundResource(mBeanDialog.getResource_townbtn_left_background());
        }

        if(mBeanDialog.getColor_townbtn_right_text()!=0){
            tv_twobtn_right.setTextColor(mBeanDialog.getColor_townbtn_right_text());
        }
        if(mBeanDialog.getResource_townbtn_right_background()!=0){
            tv_twobtn_right.setBackgroundResource(mBeanDialog.getResource_townbtn_right_background());
        }
    }

    private void initButtomView() {
        if (mBeanDialog.getmBtnNums() == BtnNums.TWO_BTN) {
            ll_twobtn.setVisibility(View.VISIBLE);
            ll_onebtn.setVisibility(View.GONE);
        } else if (mBeanDialog.getmBtnNums() == BtnNums.ONE_BTN) {
            ll_twobtn.setVisibility(View.GONE);
            ll_onebtn.setVisibility(View.VISIBLE);
        } else {
            ll_twobtn.setVisibility(View.GONE);
            ll_onebtn.setVisibility(View.GONE);
        }

        tv_twobtn_right.setText(mBeanDialog.getText_twobtn_right());
        tv_two_left.setText(mBeanDialog.getText_twobtn_left());
        tv_onebtn.setText(mBeanDialog.getText_onebtn());

        tv_two_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mBeanDialog.getOnNegativeButtonClickListener() != null) {
                    mBeanDialog.getOnNegativeButtonClickListener().onClick(v);
                }
            }
        });

        tv_twobtn_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (mBeanDialog.getOnPositiveButtonClickListener() != null) {
                    mBeanDialog.getOnPositiveButtonClickListener().onClick(v);
                }
            }
        });
        tv_onebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (mBeanDialog.getOnPositiveButtonClickListener() != null) {
                    mBeanDialog.getOnPositiveButtonClickListener().onClick(v);
                }
            }
        });
    }

    private void initContent() {
        if (mBeanDialog.getmAdapter() != null) {
            lv_content.setVisibility(View.VISIBLE);
            tv_content.setVisibility(View.GONE);
            lv_content.setAdapter(mBeanDialog.getmAdapter());
            if(mBeanDialog.getOnItemClickListener()!=null){
                lv_content.setOnItemClickListener(mBeanDialog.getOnItemClickListener());
            }

            lv_content.setSelector(new ColorDrawable(Color.TRANSPARENT));

            int listitemHeight =  ListHeightUtils.setListViewHeightBasedOnChildren(getContext(), lv_content);
            float item_flag = (mBeanDialog.getmAdapter().getCount() > 2)?2.5f:mBeanDialog.getmAdapter().getCount();

            LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (listitemHeight*item_flag));
            ll_container.setLayoutParams(ll);
        } else if (!TextUtils.isEmpty(mBeanDialog.getmContent())) {
            lv_content.setVisibility(View.GONE);
            tv_content.setVisibility(View.VISIBLE);

            tv_content.setText(mBeanDialog.getmContent());

            LinearLayout.LayoutParams ll= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll_container.setLayoutParams(ll);
        } else {
            lv_content.setVisibility(View.GONE);
            tv_content.setVisibility(View.GONE);
            ll_container.setVisibility(View.GONE);
        }
    }

    private void initTitleView() {
        if (!mBeanDialog.isShowTitle()) {
            tv_title.setVisibility(View.GONE);
        }else{
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(mBeanDialog.getmTitle());
        }

        if(!mBeanDialog.isShow_line_top()){
            line_top.setVisibility(View.GONE);
        }else{
            line_top.setVisibility(View.VISIBLE);
        }
    }

    private TextView tv_title;
    private RelativeLayout ll_container;
    private ListView lv_content;
    private View line;
    private View line_top;

    private View ll_twobtn;
    private View ll_onebtn;

    private TextView tv_two_left;
    private TextView tv_twobtn_right;
    private TextView tv_onebtn;

    private TextView tv_content;


    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);

        ll_container = (RelativeLayout) findViewById(R.id.ll_container);
        lv_content = (ListView) findViewById(R.id.lv_content);
        line = findViewById(R.id.line);
        line_top = findViewById(R.id.line_top);
        ll_twobtn = findViewById(R.id.ll_twobtn);
        ll_onebtn = findViewById(R.id.ll_onebtn);

        tv_two_left = (TextView) findViewById(R.id.tv_two_left);
        tv_twobtn_right = (TextView) findViewById(R.id.tv_twobtn_right);
        tv_onebtn = (TextView) findViewById(R.id.tv_onebtn);

        tv_content = (TextView) findViewById(R.id.tv_content);
    }

}
