package dy.utils.libhttp.httpservice;


import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import dy.utils.libhttp.httpservice.model.movie.list.Movie_item;
import dy.utils.libhttp.httpservice.config.LibHttpManager;
import dy.utils.libhttp.httpservice.subscriber.DObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(LibHttpManager.getInstance().getBaseUrl())
                .build();
        http_api = retrofit.create(IHttpApi.class);
        Log.i("xx","xx");

    }

    //添加线程管理并订阅
    private void toSubscribe(Observable o, Observer s){
        o.subscribeOn(Schedulers.io())
                .timeout(LibHttpManager.getInstance().getTIME_OUT_(), TimeUnit.SECONDS)
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    public void getMovieTops(DObserver<Movie_item> subscriber, int start){
        HashMap<String,String> map = new HashMap<>();
        map.put("count", LibHttpManager.getInstance().getPAGE_SIZE()+"");
        map.put("start",start+"");
        Observable observable = http_api.getMovieTop(map);
        toSubscribe(observable,subscriber);
    }

    public void getMovieInTheaters(DObserver<Movie_item> observer, int start){
        HashMap<String,String> map = new HashMap<>();
        map.put("count", LibHttpManager.getInstance().getPAGE_SIZE()+"");
        map.put("start",start+"");
        Observable observable = http_api.getMovieInTheaters(map);
        toSubscribe(observable,observer);
    }

    public void searchMovie(DObserver<Movie_item> subscriber, String tag, int start){
        HashMap<String,String> map = new HashMap<>();
        map.put("count", LibHttpManager.getInstance().getPAGE_SIZE()+"");
        map.put("start",start+"");
        map.put("q",tag);
        Observable observable = http_api.getMovieSearch(map);
        toSubscribe(observable,subscriber);
    }


}
