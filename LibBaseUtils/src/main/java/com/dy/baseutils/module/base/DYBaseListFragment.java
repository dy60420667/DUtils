package com.dy.baseutils.module.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dy.baseutils.R;
import com.dy.baseutils.module.base.view.IBaseListView;
import com.dy.baseutils.module.base.view.IBaseView;
import com.dy.baseutils.utils.dialog.ToastUtils;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;


/**
 * Auth : dy
 * Time : 2017/1/23 17:44
 * Email: dymh21342@163.com
 * Description:
 */

public abstract class DYBaseListFragment extends Fragment implements RecyclerArrayAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener,IBaseListView {
    public View rootView;
    public LayoutInflater inflater;
    private View view_error ,view_more,view_nomore;

    public EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter adapter;

    public boolean isRefresh = true;//是否是下拉刷新

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getFragmentLayout(),null);
        this.inflater = inflater;
        initRecyclerView();
        initView();
        return rootView;
    }

    public void initRecyclerView() {
        recyclerView = (EasyRecyclerView) rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(this);

        recyclerView.setRefreshingColor(R.color.colorPrimary);
        recyclerView.setRefreshingColorResources(R.color.colorPrimary);
        recyclerView.setRefreshing(false);
        recyclerView.setRefreshListener(this);
        recyclerView.getErrorView().setOnClickListener(this);
        recyclerView.showProgress();

        view_more = inflater.inflate(R.layout.view_more,null);
        getAdapter().setMore(view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                isRefresh = false;
                onLoadMore();
            }

            @Override
            public void onMoreClick() {
            }
        });

        getAdapter().setNoMore(R.layout.view_nomore);
        getAdapter().setError(R.layout.view_more_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
            }

            @Override
            public void onErrorClick() {
                getAdapter().resumeMore();
                isRefresh = true;
                onLoadMore();
            }
        });
    }


    public void initView(){
    }

    public int getFragmentLayout() {
        return R.layout.dy_base_list_fragment;
    }

    public  void setRefreshAble(boolean isRefresh) {
        recyclerView.getSwipeToRefresh().setEnabled(isRefresh);
    }

    public int getViewType(int position) {
        return 0;
    }

    public RecyclerArrayAdapter getAdapter(){
        return adapter;
    }

    public EasyRecyclerView getRecyclerView(){
        return recyclerView;
    }

    public abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onItemClick(int position);


    /**
     * 是否是懒加载，懒加载用于Viewpager一进入的时候不加载
     */
    private boolean isLoadFirst = false;
    public void lazyLoadate(){
        if(!isLoadFirst){
            onRefresh();
        }
        isLoadFirst = true;
    }

    public abstract  void onRefresh();

    public abstract   void onLoadMore();

    @Override
    public void onClick(View view) {
        if(view==recyclerView.getErrorView()){
            recyclerView.showProgress();
            onRefresh();
        }
    }

    public class MyAdapter extends RecyclerArrayAdapter {
        private DYBaseListFragment fragment;
        public MyAdapter(DYBaseListFragment fragment) {
            super(fragment.getContext());
            this.fragment = fragment;
        }
        @Override
        public int getViewType(int position) {
            return this.fragment.getViewType(position);
        }

        @Override
        public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
            return this.fragment.getViewHolder(parent,viewType);
        }
    }


    @Override
    public void showLoading(){
        getRecyclerView().showProgress();
    }

    @Override
    public void showError(String msg){
        if (getAdapter().getCount() > 0) {
            if (!isRefresh) {
                getAdapter().pauseMore();
            } else {
                getRecyclerView().showRecycler();
            }
            ToastUtils.ShowToast(getContext(), "获取数据失败");
        } else {
            getRecyclerView().showError();
        }
    }


    @Override
    public void showEmpty(){
        getRecyclerView().showEmpty();
    }

    @Override
    public void showDataView(){
        getRecyclerView().showRecycler();
    }
}
