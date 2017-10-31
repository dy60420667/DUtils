package com.dy.baseutils.utils.dialog.bean;

import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

/**
 * Created by dengyuan3 on 2017/9/22.
 */

public class BeanDialog {
    private String mTitle = "";//标题
    private boolean showTitle = false;//是否显示标题

    private boolean show_line_top = false;//是否显示标题下面的线条


    private BtnNums mBtnNums = BtnNums.TWO_BTN; //按钮个数
    private String mContent;//显示的内容
    private String text_twobtn_left;
    private String text_twobtn_right;
    private Integer color_townbtn_left_text= 0;//左边按钮的颜色
    private Integer resource_townbtn_left_background= 0;//背景颜色
    private Integer color_townbtn_right_text= 0;
    private Integer resource_townbtn_right_background= 0;

    private String text_onebtn;
    private Integer color_onebtn_text= 0;//左边按钮的颜色
    private Integer resource_onebtn_background= 0;//背景颜色

    private View.OnClickListener onPositiveButtonClickListener;//确定按钮的点击事件
    private View.OnClickListener onNegativeButtonClickListener;//取消按钮的点击事件

    private BaseAdapter mAdapter;//如果是ListView对话框，对话框点击事件
    private AdapterView.OnItemClickListener onItemClickListener;//ListViewItem的点击时间

    public String getmTitle() {
        if(TextUtils.isEmpty(mTitle)){
            return "提示";
        }else{
            return mTitle;
        }
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public void setShowTitle(boolean showTitle) {
        this.showTitle = showTitle;
    }

    public BtnNums getmBtnNums() {
        return mBtnNums;
    }

    public void setmBtnNums(BtnNums mBtnNums) {
        this.mBtnNums = mBtnNums;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getText_twobtn_left() {
        if (TextUtils.isEmpty(text_twobtn_left)) {
            return "取消";
        }
        return text_twobtn_left;
    }

    public void setText_twobtn_left(String text_twobtn_left) {
        this.text_twobtn_left = text_twobtn_left;
    }

    public String getText_twobtn_right() {
        if (TextUtils.isEmpty(text_twobtn_right)) {
            return "确定";
        }
        return text_twobtn_right;
    }

    public void setText_twobtn_right(String text_twobtn_right) {
        this.text_twobtn_right = text_twobtn_right;
    }

    public String getText_onebtn() {
        if (TextUtils.isEmpty(text_onebtn)) {
            return "知道了";
        }
        return text_onebtn;
    }

    public void setText_onebtn(String text_onebtn) {
        this.text_onebtn = text_onebtn;
    }

    public View.OnClickListener getOnPositiveButtonClickListener() {
        return onPositiveButtonClickListener;
    }

    public void setOnPositiveButtonClickListener(View.OnClickListener onPositiveButtonClickListener) {
        this.onPositiveButtonClickListener = onPositiveButtonClickListener;
    }

    public View.OnClickListener getOnNegativeButtonClickListener() {
        return onNegativeButtonClickListener;
    }

    public void setOnNegativeButtonClickListener(View.OnClickListener onNegativeButtonClickListener) {
        this.onNegativeButtonClickListener = onNegativeButtonClickListener;
    }

    public BaseAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(BaseAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public Integer getColor_townbtn_left_text() {
        return color_townbtn_left_text;
    }

    public void setColor_townbtn_left_text(Integer color_townbtn_left_text) {
        this.color_townbtn_left_text = color_townbtn_left_text;
    }

    public Integer getColor_townbtn_right_text() {
        return color_townbtn_right_text;
    }

    public void setColor_townbtn_right_text(Integer color_townbtn_right_text) {
        this.color_townbtn_right_text = color_townbtn_right_text;
    }

    public Integer getColor_onebtn_text() {
        return color_onebtn_text;
    }

    public void setColor_onebtn_text(Integer color_onebtn_text) {
        this.color_onebtn_text = color_onebtn_text;
    }

    public Integer getResource_townbtn_left_background() {
        return resource_townbtn_left_background;
    }

    public void setResource_townbtn_left_background(Integer resource_townbtn_left_background) {
        this.resource_townbtn_left_background = resource_townbtn_left_background;
    }

    public Integer getResource_townbtn_right_background() {
        return resource_townbtn_right_background;
    }

    public void setResource_townbtn_right_background(Integer resource_townbtn_right_background) {
        this.resource_townbtn_right_background = resource_townbtn_right_background;
    }

    public Integer getResource_onebtn_background() {
        return resource_onebtn_background;
    }

    public void setResource_onebtn_background(Integer resource_onebtn_background) {
        this.resource_onebtn_background = resource_onebtn_background;
    }

    public boolean isShow_line_top() {
        return show_line_top;
    }

    public void setShow_line_top(boolean show_line_top) {
        this.show_line_top = show_line_top;
    }
}
