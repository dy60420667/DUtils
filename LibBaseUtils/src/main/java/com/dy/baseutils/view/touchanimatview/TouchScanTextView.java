package com.dy.baseutils.view.touchanimatview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Descripty:
 * Date: 2018/1/8.16:25
 */

@SuppressLint("AppCompatCustomView")
public class TouchScanTextView extends TextView {
    public TouchScanTextView(Context context) {
        super(context);
        setLongClickable(true);
    }

    public TouchScanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLongClickable(true);
    }

    public TouchScanTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLongClickable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i("xx","down.........");
                touchDownAnimationStart();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                Log.i("xx","ACTION_UP.........");
                touchUpAnimationStart();
                break;
        }

        return super.onTouchEvent(event);
    }

    private ObjectAnimator objectAnimator;

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
