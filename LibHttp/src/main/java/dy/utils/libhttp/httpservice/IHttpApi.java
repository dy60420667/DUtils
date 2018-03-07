package dy.utils.libhttp.httpservice;

import java.util.HashMap;

import dy.utils.libhttp.httpservice.model.BaseResponse;
import dy.utils.libhttp.httpservice.model.movie.list.Movie_item;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Auth : dy
 * Time : 2017/1/24 11:01
 * Email: dymh21342@163.com
 * Description:
 */

public interface IHttpApi {

    /**
     * 获取最热电影
     * @param map
     * @return
     */
    @GET("movie/top250")
    Observable<BaseResponse<Movie_item>> getMovieTop(@QueryMap HashMap<String, String> map);


    /**
     * 获取最热电影
     * @param map
     * @return
     */
    @GET("movie/in_theaters")
    Observable<BaseResponse<Movie_item>> getMovieInTheaters(@QueryMap HashMap<String, String> map);


    /**
     * 电影搜索
     * @param map
     * @return
     */
    @GET("movie/search")
    Observable<BaseResponse<Movie_item>> getMovieSearch(@QueryMap HashMap<String, String> map);

}
