package com.dy.hbjg.module.resource;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.baseutils.module.base.DYBaseListFragment;
import com.dy.hbjg.module.resource.presenter.MovieTopsPresenter;
import com.dy.hbjg.module.resource.view.ViewHolderItemNormal;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.List;


/**
 * Auth : dy
 * Time : 2017/1/20 16:55
 * Email: dymh21342@163.com
 * Description:
 */

public class MoviestopFragment extends DYBaseListFragment {
    public static MoviestopFragment createFragment(){
        MoviestopFragment f_movies_top =new MoviestopFragment();
        return f_movies_top;
    }

    private MovieTopsPresenter movieListPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        movieListPresenter = new MovieTopsPresenter(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void initView() {
        super.initView();
        onRefresh();
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderItemNormal(parent);
    }


    public void onRefresh() {
        movieListPresenter.loadDate(0);
    }

    public void onLoadMore() {
        movieListPresenter.loadDate(getAdapter().getCount());
    }

//    @Override
//    public void onItemClick(int position) {
//        MovieEntity item = (MovieEntity) getAdapter().getItem(position);
//        ViewMsg detail = new ViewMsg();
//        detail.title = item.title;
//        detail.msg = item.getDescript();
//        A_View_msg.gotoActivity(getActivity(), detail);
//    }


    @Override
    public void setDate(List list) {
        showDataView();
        if (isRefresh) {
            getAdapter().clear();
        }
        getAdapter().addAll(list);
        if (getAdapter().getCount() == 0) {
            showEmpty();
        }
    }
}
