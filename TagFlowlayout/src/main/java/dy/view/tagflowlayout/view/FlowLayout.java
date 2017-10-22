package dy.view.tagflowlayout.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dy.view.tagflowlayout.R;

public class FlowLayout extends ViewGroup {
    private static final String TAG = "FlowLayout";
    private static final int LEFT = -1;
    private static final int CENTER = 0;
    private static final int RIGHT = 1;
    protected List<List<View>> mAllViews;
    protected List<Integer> mLineHeight;
    protected List<Integer> mLineWidth;
    private int mGravity;
    private List<View> lineViews;

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAllViews = new ArrayList();
        this.mLineHeight = new ArrayList();
        this.mLineWidth = new ArrayList();
        this.lineViews = new ArrayList();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        this.mGravity = ta.getInt(R.styleable.TagFlowLayout_gravity, -1);
        ta.recycle();
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int width = 0;
        int height = 0;
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            View child = this.getChildAt(i);
            if(child.getVisibility() == 8) {
                if(i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            } else {
                this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
                MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
                int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                if(lineWidth + childWidth > sizeWidth - this.getPaddingLeft() - this.getPaddingRight()) {
                    width = Math.max(width, lineWidth);
                    lineWidth = childWidth;
                    height += lineHeight;
                    lineHeight = childHeight;
                } else {
                    lineWidth += childWidth;
                    lineHeight = Math.max(lineHeight, childHeight);
                }

                if(i == cCount - 1) {
                    width = Math.max(lineWidth, width);
                    height += lineHeight;
                }
            }
        }

        this.setMeasuredDimension(modeWidth == 1073741824?sizeWidth:width + this.getPaddingLeft() + this.getPaddingRight(), modeHeight == 1073741824?sizeHeight:height + this.getPaddingTop() + this.getPaddingBottom());
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mAllViews.clear();
        this.mLineHeight.clear();
        this.mLineWidth.clear();
        this.lineViews.clear();
        int width = this.getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        int cCount = this.getChildCount();

        int left;
        int i;
        int currentLineWidth;
        for(left = 0; left < cCount; ++left) {
            View top = this.getChildAt(left);
            if(top.getVisibility() != 8) {
                MarginLayoutParams lineNum = (MarginLayoutParams)top.getLayoutParams();
                i = top.getMeasuredWidth();
                currentLineWidth = top.getMeasuredHeight();
                if(i + lineWidth + lineNum.leftMargin + lineNum.rightMargin > width - this.getPaddingLeft() - this.getPaddingRight()) {
                    this.mLineHeight.add(Integer.valueOf(lineHeight));
                    this.mAllViews.add(this.lineViews);
                    this.mLineWidth.add(Integer.valueOf(lineWidth));
                    lineWidth = 0;
                    lineHeight = currentLineWidth + lineNum.topMargin + lineNum.bottomMargin;
                    this.lineViews = new ArrayList();
                }

                lineWidth += i + lineNum.leftMargin + lineNum.rightMargin;
                lineHeight = Math.max(lineHeight, currentLineWidth + lineNum.topMargin + lineNum.bottomMargin);
                this.lineViews.add(top);
            }
        }

        this.mLineHeight.add(Integer.valueOf(lineHeight));
        this.mLineWidth.add(Integer.valueOf(lineWidth));
        this.mAllViews.add(this.lineViews);
        left = this.getPaddingLeft();
        int var22 = this.getPaddingTop();
        int var23 = this.mAllViews.size();

        for(i = 0; i < var23; ++i) {
            this.lineViews = (List)this.mAllViews.get(i);
            lineHeight = ((Integer)this.mLineHeight.get(i)).intValue();
            currentLineWidth = ((Integer)this.mLineWidth.get(i)).intValue();
            switch(this.mGravity) {
            case -1:
                left = this.getPaddingLeft();
                break;
            case 0:
                left = (width - currentLineWidth) / 2 + this.getPaddingLeft();
                break;
            case 1:
                left = width - currentLineWidth + this.getPaddingLeft();
            }

            for(int j = 0; j < this.lineViews.size(); ++j) {
                View child = (View)this.lineViews.get(j);
                if(child.getVisibility() != 8) {
                    MarginLayoutParams lp = (MarginLayoutParams)child.getLayoutParams();
                    int lc = left + lp.leftMargin;
                    int tc = var22 + lp.topMargin;
                    int rc = lc + child.getMeasuredWidth();
                    int bc = tc + child.getMeasuredHeight();
                    child.layout(lc, tc, rc, bc);
                    left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                }
            }

            var22 += lineHeight;
        }

    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(this.getContext(), attrs);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(-2, -2);
    }

    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    public List<List<View>> getmAllViews() {
        return this.mAllViews;
    }
}
