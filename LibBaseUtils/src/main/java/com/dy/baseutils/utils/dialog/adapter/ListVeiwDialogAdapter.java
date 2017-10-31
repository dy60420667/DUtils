package com.dy.baseutils.utils.dialog.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.baseutils.utils.dialog.adapter.bean.BeanItemListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengyuan3 on 2017/9/22.
 */

public class ListVeiwDialogAdapter extends BaseAdapter {

    private Activity mContext;
    List<BeanItemListView> mListData;

    public ListVeiwDialogAdapter(Activity mContext, List<BeanItemListView> mListData) {
        super();
        this.mContext = mContext;
        if (mListData == null) {
            mListData = new ArrayList<>();
        }
        this.mListData = mListData;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mListData.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.libbase_dialog_item_listview, null);
            holder = new ViewHolder();
            holder.iv_good_icon = (ImageView) convertView.findViewById(R.id.iv_good_icon);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_title_second = (TextView) convertView.findViewById(R.id.tv_title_second);
            holder.tv_title_third = (TextView) convertView.findViewById(R.id.tv_title_third);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BeanItemListView item = mListData.get(position);
        Glide.with(mContext).load(item.mUrl_icon).into(holder.iv_good_icon);
        holder.tv_title.setText(item.mTitle);
        holder.tv_title_second.setText(item.mTitle_second);
        holder.tv_title_third.setText(item.mTitle_third);
        return convertView;
    }

    class ViewHolder {
        ImageView iv_good_icon;
        TextView tv_title, tv_title_second, tv_title_third;
    }
}
