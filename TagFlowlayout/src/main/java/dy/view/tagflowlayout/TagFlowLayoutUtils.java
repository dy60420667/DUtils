package dy.view.tagflowlayout;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import dy.view.tagflowlayout.view.FlowLayout;
import dy.view.tagflowlayout.view.TagAdapter;

public class TagFlowLayoutUtils {
    private TagFlowLayout mFlowLayout;
    private String[] mVals;
    private String[] mVarlTemp;
    private TagAdapter<String> adapter;
    private LayoutInflater mInflater;
    private int textViewLayoutIdInTag;
    private String flow_layout_more_str;
    private String[] firstPart;
    private String[] finalPart;
    private static final int MAX_LINE = 2;
    private boolean trimeToMaxLine;
    private TagFlowLayoutUtils.OnTagClickListenerWrapper wrapperListener;

    public void setTrimeToMaxLine(boolean trimeToMaxLine) {
        this.trimeToMaxLine = trimeToMaxLine;
    }

    public TagFlowLayoutUtils(Activity mContext, TagFlowLayout mFlowLayout, String[] mVals, @LayoutRes int textViewLayoutIdInTag) {
        this(mContext, mFlowLayout, mVals);
        this.textViewLayoutIdInTag = textViewLayoutIdInTag;
    }

    public TagFlowLayoutUtils(Activity mContext, TagFlowLayout mFlowLayout, String[] mVals) {
        this.mVals = null;
        this.mVarlTemp = null;
        this.adapter = null;
        this.mInflater = null;
        this.textViewLayoutIdInTag = 0;
        this.flow_layout_more_str = "";
        this.trimeToMaxLine = false;
        this.wrapperListener = null;
        this.flow_layout_more_str = mContext.getString(R.string.flow_layout_more_str);
        this.mInflater = mContext.getLayoutInflater();
        this.mFlowLayout = mFlowLayout;
        this.mVals = mVals;
        this.textViewLayoutIdInTag = R.layout.flow_layout_tv;
        this.mVarlTemp = (String[])mVals.clone();
    }

    public void burn() {
        this.setAdapterAndTagClickListener(this.mVals, this.mInflater);
        this.mFlowLayout.post(new Runnable() {
            public void run() {
                List lists = TagFlowLayoutUtils.this.mFlowLayout.getmAllViews();
                if(TagFlowLayoutUtils.this.needToTrim(lists)) {
                    TagFlowLayoutUtils.this.buildFirstPart(lists);
                    TagFlowLayoutUtils.this.trimeToMaxLine(TagFlowLayoutUtils.this.firstPart);
                }

            }
        });
    }

    private void trimeToMaxLine(final String[] firstPart) {
        this.mFlowLayout.post(new Runnable() {
            public void run() {
                List lists = TagFlowLayoutUtils.this.mFlowLayout.getmAllViews();
                if(TagFlowLayoutUtils.this.needToTrim(lists)) {
                    TagFlowLayoutUtils.this.finalPart = new String[firstPart.length - 1];
                    System.arraycopy(firstPart, 0, TagFlowLayoutUtils.this.finalPart, 0, firstPart.length - 2);
                    TagFlowLayoutUtils.this.finalPart[firstPart.length - 2] = TagFlowLayoutUtils.this.flow_layout_more_str;
                    TagFlowLayoutUtils.this.setAdapterAndTagClickListener((String[])TagFlowLayoutUtils.this.finalPart.clone(), TagFlowLayoutUtils.this.mInflater);
                    TagFlowLayoutUtils.this.trimeToMaxLine(TagFlowLayoutUtils.this.finalPart);
                }

            }
        });
    }

    private void buildFirstPart(List<List<View>> lists) {
        int positon = ((List)lists.get(0)).size() + ((List)lists.get(1)).size();
        this.firstPart = new String[positon + 1];
        this.firstPart[positon] = this.flow_layout_more_str;
        System.arraycopy(this.mVals, 0, this.firstPart, 0, positon);
        this.setAdapterAndTagClickListener((String[])this.firstPart.clone(), this.mInflater);
    }

    private boolean needToTrim(List<List<View>> lists) {
        return this.trimeToMaxLine && null != lists && lists.size() > 2;
    }

    private void setAdapter(final String[] vals, final LayoutInflater mInflater) {
        this.mVals = (String[])vals.clone();
        this.adapter = new TagAdapter(vals) {
            @Override
            public View getView(FlowLayout parent, int position, Object s) {
//                View view =mInflater.inflate(TagFlowLayoutUtils.this.textViewLayoutIdInTag,null);
//                TextView tv = (TextView) view.findViewById(R.id.tv);

                TextView tv = (TextView)mInflater.inflate(TagFlowLayoutUtils.this.textViewLayoutIdInTag, TagFlowLayoutUtils.this.mFlowLayout, false);
                tv.setText(s.toString());
                return tv;
            }

        };
        this.mFlowLayout.setAdapter(this.adapter);
    }

    private void setAdapterAndTagClickListener(String[] vals, final LayoutInflater mInflater) {
        this.setAdapter(vals, mInflater);
        this.mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if(TagFlowLayoutUtils.this.mVals[position].contentEquals(TagFlowLayoutUtils.this.flow_layout_more_str)) {
                    TagFlowLayoutUtils.this.setAdapter(TagFlowLayoutUtils.this.mVarlTemp, mInflater);
                } else if(null != TagFlowLayoutUtils.this.wrapperListener) {
                    TagFlowLayoutUtils.this.wrapperListener.onTagClick(view, position, parent);
                }

                return true;
            }
        });
    }

    public void setWrapperListener(TagFlowLayoutUtils.OnTagClickListenerWrapper wrapperListener) {
        this.wrapperListener = wrapperListener;
    }

    public interface OnTagClickListenerWrapper extends TagFlowLayout.OnTagClickListener {
    }
}
