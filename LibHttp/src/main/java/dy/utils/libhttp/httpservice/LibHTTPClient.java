package dy.utils.libhttp.httpservice;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dy.utils.libhttp.httpservice.model.BaseResponse;
import dy.utils.libhttp.httpservice.model.movie.list.Movie_item;
import dy.utils.libhttp.httpservice.config.LibHttpManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Auth : dy
 * Time : 2017/1/26 08:46
 * Email: dymh21342@163.com
 * Description:
 * http://blog.csdn.net/u012184539/article/details/51043236
 */

public class LibHTTPClient {
    private volatile static LibHTTPClient instance;
    public static LibHTTPClient getInstance(){
        if(instance==null){
            synchronized (LibHTTPClient.class){
                if(instance==null){
                    instance= new LibHTTPClient();
                }
            }
        }
        return instance;
    }
    private Retrofit retrofit;
    private IHttpApi http_api;


    private LibHTTPClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//        httpClientBuilder.connectTimeout(HTTP_Config.TIME_OUT_CONNECT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder().client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(LibHttpManager.getInstance().getBaseUrl())
                .build();
        http_api = retrofit.create(IHttpApi.class);
    }

    //添加线程管理并订阅
    private void toSubscribe(Observable o, Subscriber s){
        o.subscribeOn(Schedulers.io())
                .timeout(LibHttpManager.getInstance().getTIME_OUT_(), TimeUnit.SECONDS)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    public void getMovieTops(Subscriber<BaseResponse<Movie_item>> subscriber, int start){
        HashMap<String,String> map = new HashMap<>();
        map.put("count", LibHttpManager.getInstance().getPAGE_SIZE()+"");
        map.put("start",start+"");
        Observable observable = http_api.getMovieTop(map);
        toSubscribe(observable,subscriber);
    }

    public void getMovieInTheaters(Subscriber<BaseResponse<Movie_item>> subscriber, int start){
        HashMap<String,String> map = new HashMap<>();
        map.put("count", LibHttpManager.getInstance().getPAGE_SIZE()+"");
        map.put("start",start+"");
        Observable observable = http_api.getMovieInTheaters(map);
        toSubscribe(observable,subscriber);
    }

    public void searchMovie(Subscriber<BaseResponse<Movie_item>> subscriber, String tag, int start){
        HashMap<String,String> map = new HashMap<>();
        map.put("count", LibHttpManager.getInstance().getPAGE_SIZE()+"");
        map.put("start",start+"");
        map.put("q",tag);
        Observable observable = http_api.getMovieSearch(map);
        toSubscribe(observable,subscriber);
    }


}
