package com.dy.hbjg.module.search;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dy.baseutils.module.base.DYBaseListFragment;
import com.dy.baseutils.module.newsshow.A_View_msg;
import com.dy.baseutils.module.newsshow.bean.ViewMsg;
import com.dy.hbjg.R;
import com.dy.hbjg.module.resource.view.ViewHolderItemNormal;
import com.dy.hbjg.module.search.presenter.SearchPresenter;
import com.dy.hbjg.module.search.view.ISearchView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import dy.utils.libhttp.httpservice.model.movie.MovieEntity;
import dy.view.tagflowlayout.view.FlowLayout;
import dy.view.tagflowlayout.TagFlowLayout;
import dy.view.tagflowlayout.TagFlowLayoutUtils;
import dy.view.tagflowlayout.view.TagInfo;

/**
 * Auth : dy
 * Time : 2017/2/6 09:15
 * Email: dymh21342@163.com
 * Description:
 */

public class F_search extends DYBaseListFragment implements ISearchView {
    public static F_search createFragment(){
        F_search f_search = new F_search();
        return f_search;
    }

    public SearchPresenter searchPresenter;

    private View layout_tags ;
    private TagFlowLayout tagFlowLayout;
    private TextView tv_tips;

    @Override
    public void initView() {
        tv_tips = (TextView) rootView.findViewById(R.id.tv_tips);
        layout_tags =  rootView.findViewById(R.id.layout_tags);
        searchPresenter = new SearchPresenter(this);
        setRefreshAble(false);

        tagFlowLayout = (TagFlowLayout) rootView.findViewById(R.id.tagFlowLayout);

        ArrayList<TagInfo> listTags = new ArrayList<>();
        listTags.add(new TagInfo(0+"","云之歌"));
        listTags.add(new TagInfo(1+"","大"));
        listTags.add(new TagInfo(2+"","大漠孤烟直"));
        listTags.add(new TagInfo(3+"","雄关漫道真如铁，而今迈步从头越"));
        listTags.add(new TagInfo(3+"","雄越"));
        listTags.add(new TagInfo(3+"","雄今迈步从头越"));
        listTags.add(new TagInfo(3+"","雄步从头越"));
        listTags.add(new TagInfo(3+"","雄今迈步从头越"));
        listTags.add(new TagInfo(3+"","雄今"));
        listTags.add(new TagInfo(3+"","雄今1"));
        listTags.add(new TagInfo(3+"","雄今迈步"));

        final String[] tagStrs =getTagStrArray(listTags);

        final TagFlowLayoutUtils utils = new TagFlowLayoutUtils(getActivity(), tagFlowLayout, tagStrs);

        utils.setWrapperListener(new TagFlowLayoutUtils.OnTagClickListenerWrapper() {
            @Override
            public boolean onTagClick(View var1, int position, FlowLayout var3) {
                String world = tagStrs[position];
                clearDate();
                searchPresenter.searchMovies(world,0);
                showList();
                showLoading();
                ((A_search)getActivity()).setToolbar_et_search(world);
                return false;
            }
        });
        utils.burn();

        showTagFlow();
    }

    private String[] getTagStrArray(ArrayList<TagInfo> tagList ){
        final int size = tagList.size();
        String [] result = new String[size];
        for(int i=0;i<size;i++){
            final TagInfo tagInfo = tagList.get(i);
            if(null!= tagInfo){
                result[i] = tagInfo.getText();
            }

        }
        return result;
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.f_search;
    }

    @Override
    public BaseViewHolder getViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderItemNormal(parent);
    }

    @Override
    public void onItemClick(int position) {
        MovieEntity item = (MovieEntity) getAdapter().getItem(position);
        ViewMsg detail = new ViewMsg();
        detail.title = item.title;
        detail.msg = item.descript;
        A_View_msg.gotoActivity(getActivity(), detail);
    }

    @Override
    public void onRefresh() {
       
    }

    @Override
    public void onLoadMore() {
        showList();
        searchPresenter.searchMovies(((A_search)getActivity()).getWorld(),getAdapter().getCount());
    }

    @Override
    public void showTagFlow() {
        layout_tags.setVisibility(View.VISIBLE);
        tagFlowLayout.setVisibility(View.VISIBLE);
        tv_tips.setText(getText(R.string.search_hot));
        tv_tips.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }


    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
        layout_tags.setVisibility(View.GONE);
    }

    @Override
    public void clearDate() {
        getAdapter().clear();
        getAdapter().notifyDataSetChanged();
    }


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

    @Override
    public void showEmpty() {
        layout_tags.setVisibility(View.VISIBLE);
        tagFlowLayout.setVisibility(View.GONE);
        String world = ((A_search)getActivity()).getWorld();
        SpannableString ss = new SpannableString(getString(R.string.search_nofind_world_1)+world+getString(R.string.search_nofind_world_2));

        StyleSpan spanB = new StyleSpan(Typeface.BOLD);//粗体
        ss.setSpan(spanB, 5, ss.length()-6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_tips.setText(ss);
    }
}
