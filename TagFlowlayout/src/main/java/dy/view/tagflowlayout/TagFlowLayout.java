package dy.view.tagflowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import dy.view.tagflowlayout.view.FlowLayout;
import dy.view.tagflowlayout.view.TagAdapter;
import dy.view.tagflowlayout.view.TagView;

public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataChangedListener {
    private TagAdapter mTagAdapter;
    private boolean mAutoSelectEffect;
    private int mSelectedMax;
    private MotionEvent mMotionEvent;
    private Set<Integer> mSelectedView;
    private TagFlowLayout.OnSelectListener mOnSelectListener;
    private TagFlowLayout.OnTagClickListener mOnTagClickListener;

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mAutoSelectEffect = true;
        this.mSelectedMax = -1;
        this.mSelectedView = new HashSet();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        this.mAutoSelectEffect = ta.getBoolean(R.styleable.TagFlowLayout_auto_select_effect, true);
        this.mSelectedMax = ta.getInt(R.styleable.TagFlowLayout_max_select, -1);
        ta.recycle();
        if(this.mAutoSelectEffect) {
            this.setClickable(true);
        }

    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context) {
        this(context, (AttributeSet)null);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            TagView tagView = (TagView)this.getChildAt(i);
            if(tagView.getVisibility() != View.GONE && tagView.getTagView().getVisibility() == View.GONE) {
                tagView.setVisibility(View.GONE);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnSelectListener(TagFlowLayout.OnSelectListener onSelectListener) {
        this.mOnSelectListener = onSelectListener;
        if(this.mOnSelectListener != null) {
            this.setClickable(true);
        }

    }

    public void setOnTagClickListener(TagFlowLayout.OnTagClickListener onTagClickListener) {
        this.mOnTagClickListener = onTagClickListener;
        if(onTagClickListener != null) {
            this.setClickable(true);
        }

    }

    public void setAdapter(TagAdapter adapter) {
        this.mTagAdapter = adapter;
        this.mTagAdapter.setOnDataChangedListener(this);
        this.mSelectedView.clear();
        this.changeAdapter();
    }

    private void changeAdapter() {
        this.removeAllViews();
        TagAdapter adapter = this.mTagAdapter;
//      TagView   tagViewContainer = null;
        HashSet preCheckedList = this.mTagAdapter.getPreCheckedList();

        for(int i = 0; i < adapter.getCount(); ++i) {
            View tagView = adapter.getView(this, i, adapter.getItem(i));
            TagView  tagViewContainer = new TagView(this.getContext());
            tagView.setDuplicateParentStateEnabled(true);
            if(tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(dip2px(this.getContext(), 5.0F), dip2px(this.getContext(), 5.0F), dip2px(this.getContext(), 5.0F), dip2px(this.getContext(), 5.0F));
                tagViewContainer.setLayoutParams(lp);
            }

            tagViewContainer.addView(tagView);
            this.addView(tagViewContainer);
            if(preCheckedList.contains(Integer.valueOf(i))) {
                tagViewContainer.setChecked(true);
            }

            if(this.mTagAdapter.setSelected(i, adapter.getItem(i))) {
                this.mSelectedView.add(Integer.valueOf(i));
                tagViewContainer.setChecked(true);
            }
        }

        this.mSelectedView.addAll(preCheckedList);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == 1) {
            this.mMotionEvent = MotionEvent.obtain(event);
        }

        this.performChildStateOperation(event);
        return true;
    }

    private void performChildStateOperation(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        TagView child = this.findChild(x, y);
        if(child != null) {
            this.changeCurrentClickedChidBgByMotionEvent(event, child);
        } else {
            this.changeAllChildToUnPressedState();
        }

    }

    private void changeAllChildToUnPressedState() {
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            TagView v = (TagView)this.getChildAt(i);
            v.setPressed(false);
        }

    }

    private void changeCurrentClickedChidBgByMotionEvent(MotionEvent event, final TagView child) {
        switch(event.getAction()) {
        case 0:
            this.post(new Runnable() {
                public void run() {
                    child.setPressed(true);
                }
            });
        case 1:
            child.setPressed(false);
            this.post(new Runnable() {
                public void run() {
                    TagFlowLayout.this.performClick();
                }
            });
        default:
        }
    }

    public boolean performClick() {
        if(this.mMotionEvent == null) {
            return super.performClick();
        } else {
            int x = (int)this.mMotionEvent.getX();
            int y = (int)this.mMotionEvent.getY();
            this.mMotionEvent = null;
            TagView child = this.findChild(x, y);
            int pos = this.findPosByView(child);
            if(child != null) {
                this.doSelect(child, pos);
                if(this.mOnTagClickListener != null) {
                    return this.mOnTagClickListener.onTagClick(child.getTagView(), pos, this);
                }
            }

            return true;
        }
    }

    public void setMaxSelectCount(int count) {
        if(this.mSelectedView.size() > count) {
            this.mSelectedView.clear();
        }

        this.mSelectedMax = count;
    }

    public Set<Integer> getSelectedList() {
        return new HashSet(this.mSelectedView);
    }

    private void doSelect(TagView child, int position) {
        if(this.mAutoSelectEffect) {
            if(!child.isChecked()) {
                if(this.mSelectedMax == 1 && this.mSelectedView.size() == 1) {
                    Iterator iterator = this.mSelectedView.iterator();
                    Integer preIndex = (Integer)iterator.next();
                    TagView pre = (TagView)this.getChildAt(preIndex.intValue());
                    pre.setChecked(false);
                    child.setChecked(true);
                    this.mSelectedView.remove(preIndex);
                    this.mSelectedView.add(Integer.valueOf(position));
                } else {
                    if(this.mSelectedMax > 0 && this.mSelectedView.size() >= this.mSelectedMax) {
                        return;
                    }

                    child.setChecked(true);
                    this.mSelectedView.add(Integer.valueOf(position));
                }
            } else {
                child.setChecked(false);
                this.mSelectedView.remove(Integer.valueOf(position));
            }

            if(this.mOnSelectListener != null) {
                this.mOnSelectListener.onSelected(new HashSet(this.mSelectedView));
            }
        }

    }

    public TagAdapter getAdapter() {
        return this.mTagAdapter;
    }

    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("key_default", super.onSaveInstanceState());
        StringBuilder selectPos = new StringBuilder("");
        String substring = "";
        if(this.mSelectedView.size() > 0) {
            Iterator var4 = this.mSelectedView.iterator();

            while(var4.hasNext()) {
                int key = ((Integer)var4.next()).intValue();
                selectPos.append(key + "|");
            }

            substring = selectPos.substring(0, selectPos.length() - 1);
        }

        bundle.putString("key_choose_pos", substring);
        return bundle;
    }

    protected void onRestoreInstanceState(Parcelable state) {
        if(!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
        } else {
            Bundle bundle = (Bundle)state;
            String mSelectPos = bundle.getString("key_choose_pos");
            if(!TextUtils.isEmpty(mSelectPos)) {
                String[] split = mSelectPos.split("\\|");
                String[] var5 = split;
                int var6 = split.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    String pos = var5[var7];
                    int index = Integer.parseInt(pos);
                    this.mSelectedView.add(Integer.valueOf(index));
                    TagView tagView = (TagView)this.getChildAt(index);
                    if(tagView != null) {
                        tagView.setChecked(true);
                    }
                }
            }

            super.onRestoreInstanceState(bundle.getParcelable("key_default"));
        }
    }

    private int findPosByView(View child) {
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            View v = this.getChildAt(i);
            if(v == child) {
                return i;
            }
        }

        return -1;
    }

    private TagView findChild(int x, int y) {
        int cCount = this.getChildCount();

        for(int i = 0; i < cCount; ++i) {
            TagView v = (TagView)this.getChildAt(i);
            if(v.getVisibility() != View.GONE) {
                Rect outRect = new Rect();
                v.getHitRect(outRect);
                if(outRect.contains(x, y)) {
                    return v;
                }
            }
        }

        return null;
    }

    public void onChanged() {
        this.mSelectedView.clear();
        this.changeAdapter();
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public interface OnTagClickListener {
        boolean onTagClick(View var1, int var2, FlowLayout var3);
    }

    public interface OnSelectListener {
        void onSelected(Set<Integer> var1);
    }
}
