package com.dy.hbjg.module.resource.view;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dy.baseutils.utils.log.LD;
import com.dy.hbjg.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import dy.utils.libhttp.model.movie.MovieEntity;

/**
 * Auth : dy
 * Time : 2017/1/24 09:32
 * Email: dymh21342@163.com
 * Description:
 */

public class ViewHolderItemNormal extends BaseViewHolder<MovieEntity>{

    private TextView text,text_info;
    private ImageView logo;
    public ViewHolderItemNormal(ViewGroup parent ) {
        super(parent,  R.layout.item_layout_resource);
        LD.i("ViewHolderItemNormal");
        initView();
    }

    private void initView() {
        text = (TextView) itemView.findViewById(R.id.text);
        text_info = (TextView) itemView.findViewById(R.id.text_info);
        logo = (ImageView) itemView.findViewById(R.id.logo);
    }

    @Override
    public void setData(MovieEntity data) {
        LD.i("ViewHolderItemNormal.setData"+data);
        text.setText(data.title);

        text_info.setText(data.getDescript());
        Glide.with(getContext())
                .load(data.images.getUrl())
                .placeholder(R.drawable.iconfont_photo)
                .into(logo);

    }
}
