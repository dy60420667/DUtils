package com.dy.hbjg.module.resource.presenter;

import com.dy.baseutils.module.base.presenter.BasePresenter;
import com.dy.baseutils.module.base.view.IBaseListView;

import dy.utils.libhttp.LibHTTPClient;
import dy.utils.libhttp.model.BaseResponse;
import dy.utils.libhttp.model.movie.list.Movie_item;
import dy.utils.libhttp.subscriber.BaseSubscriber;
import rx.Subscriber;

/**
 * Auth : dy
 * Time : 2017/2/3 16:04
 * Email: dymh21342@163.com
 * Description:
 */

public class MovieInTheatersPresenter extends BasePresenter{

    private IBaseListView i_movieListView;
    public MovieInTheatersPresenter(IBaseListView i_movieListView){
        this.i_movieListView = i_movieListView;
    }

    @Override
    protected void loadDate() {
    }

    public void loadDate(int count) {
        Subscriber subscriber = new BaseSubscriber<Movie_item>() {
            @Override
            public void onError(Throwable e) {
                super.onError(e);
                i_movieListView.showError("");
//                i_movieListView.onError(e);
            }

            @Override
            public void onNext(BaseResponse<Movie_item> model) {
                i_movieListView.setDate(model.subjects);
//                i_movieListView.onNext(model);
            }
        };
        LibHTTPClient.getInstance().getMovieInTheaters(subscriber, count);
    }
}
