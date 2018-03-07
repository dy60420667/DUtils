package com.dy.baseutils.module.common;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dy.baseutils.R;


/**
 * 首页标题栏
 *
 * @author ouyezi
 */
public class CommonTitleBar extends RelativeLayout {

    // 防重复点击时间
    private static final int BTN_LIMIT_TIME = 500;

    private TextView leftButton;
    private ImageView leftButtonImg;
    private TextView middleButton;
    private TextView rightButton;
    private ImageView rightButtonImg;
    private int leftBtnIconId;
    private String leftBtnStr;
    private String titleTxtStr;
    private String rightBtnStr;
    private int rightBtnIconId;

    public View title_out_frame;//跟布局


    public CommonTitleBar(Context context) {
        super(context);
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar);
        // 如果后续有文字按钮，可使用该模式设置
        leftBtnStr = arr.getString(R.styleable.CommonTitleBar_leftBtnTxt);
        leftBtnIconId = arr.getResourceId(R.styleable.CommonTitleBar_leftBtnIcon, 0);
        titleTxtStr = arr.getString(R.styleable.CommonTitleBar_titleTxt);
        rightBtnStr = arr.getString(R.styleable.CommonTitleBar_rightBtnTxt);
        rightBtnIconId = arr.getResourceId(R.styleable.CommonTitleBar_rightBtnIcon, 0);
        LayoutInflater.from(context).inflate(R.layout.libbase_view_title_bar, this);
        if (isInEditMode()) {//使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
            return;
        }
        arr.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isInEditMode()) {
            return;
        }
        leftButtonImg = (ImageView) findViewById(R.id.title_left_btn);
        leftButton = (TextView) findViewById(R.id.title_left);
        middleButton = (TextView) findViewById(R.id.title_middle);
        rightButtonImg = (ImageView) findViewById(R.id.title_right_btn);
        rightButton = (TextView) findViewById(R.id.title_right);
        title_out_frame = findViewById(R.id.title_out_frame);

        if (leftBtnIconId != 0) {
            leftButtonImg.setImageResource(leftBtnIconId);
        }
        if (rightBtnIconId != 0) {
            rightButtonImg.setImageResource(rightBtnIconId);
            rightButtonImg.setVisibility(View.VISIBLE);
        } else {
            rightButtonImg.setVisibility(View.GONE);
        }
        setLeftTxtBtn(leftBtnStr);
        setTitleTxt(titleTxtStr);
        setRightTxtBtn(rightBtnStr);
    }

    @Override
    public void setBackground(Drawable background) {
        title_out_frame.setBackground(background);
    }

    @Override
    public void setBackgroundColor(int color) {
        title_out_frame.setBackgroundColor(color);
    }


    public void setRightTxtBtn(String btnTxt) {
            rightButton.setText(btnTxt);
    }

    public void setLeftTxtBtn(String leftBtnStr) {
            leftButton.setText(leftBtnStr);
    }

    public void setTitleTxt(CharSequence title) {
        if(TextUtils.isEmpty(title)){
            title = getResources().getString(R.string.app_name);
        }
        middleButton.setText(title);
    }

    public void hideLeftBtn() {
        leftButton.setVisibility(View.GONE);
        leftButtonImg.setVisibility(View.GONE);
        findViewById(R.id.title_left_area).setOnClickListener(null);
    }

    public void hideRightBtn() {
        rightButton.setVisibility(View.GONE);
        rightButtonImg.setVisibility(View.GONE);
        findViewById(R.id.title_right_area).setOnClickListener(null);
    }

    public void setLeftBtnOnclickListener(OnClickListener listener) {
        OnClickListener myListener = new GlobalLimitClickOnClickListener(listener, BTN_LIMIT_TIME);
        findViewById(R.id.title_left_area).setOnClickListener(myListener);
    }

    public void setRightBtnOnclickListener(OnClickListener listener) {
        OnClickListener myListener = new GlobalLimitClickOnClickListener(listener, BTN_LIMIT_TIME);
        findViewById(R.id.title_right_area).setOnClickListener(myListener);
    }

}
