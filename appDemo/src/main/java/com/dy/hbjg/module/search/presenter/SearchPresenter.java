package com.dy.hbjg.module.search.presenter;

import com.dy.baseutils.module.base.view.IBaseListView;

import dy.utils.libhttp.httpservice.LibHTTPClient;
import dy.utils.libhttp.httpservice.model.BaseResponse;
import dy.utils.libhttp.httpservice.model.movie.list.Movie_item;
import dy.utils.libhttp.httpservice.subscriber.DObserver;
import rx.Subscriber;

/**
 * Auth : dy
 * Time : 2017/2/6 09:50
 * Email: dymh21342@163.com
 * Description:
 */

public class SearchPresenter {
    IBaseListView iBaseListView;
    public SearchPresenter(IBaseListView iBaseListView){
        this.iBaseListView = iBaseListView;
    }

    public void searchMovies(String world,int start) {
        DObserver subscriber = new DObserver<Movie_item>() {
            @Override
            public void onError(Throwable e) {
                iBaseListView.showError("");
            }

            @Override
            public void onNext(BaseResponse<Movie_item> model) {
                iBaseListView.setDate(model.subjects);
            }
        };
        LibHTTPClient.getInstance().searchMovie(subscriber, world,start);
    }
}
