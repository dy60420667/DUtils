package com.dy.baseutils.view.touchanimatview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.dy.baseutils.utils.log.LD;

import java.util.logging.Handler;

/**
 * Descripty:
 * Date: 2018/1/8.16:25
 */

public class TouchScanLayout extends RelativeLayout {
    private String TAG = "TouchScanLayout";
    public TouchScanLayout(Context context) {
        super(context);
        setLongClickable(true);
    }

    public TouchScanLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLongClickable(true);
    }

    public TouchScanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLongClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                LD.i(TAG,"ACTION_DOWN");
                touchDownAnimationStart();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                LD.i(TAG,"ACTION_UP");
                touchUpAnimationStart();
                break;
        }

        return super.onTouchEvent(event);
    }

    private void touchDownAnimationStart(){
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.9f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.9f);

        animatorSet.setDuration(200);

        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();
    }

    private void touchUpAnimationStart(){
        AnimatorSet animatorSet = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0.9f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0.9f, 1f);

        animatorSet.setDuration(200);

        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSet.start();
    }
}
