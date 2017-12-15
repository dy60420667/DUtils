package dy.utils.libhttp.httpservice.subscriber;


import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import dy.utils.libhttp.httpservice.exception.ApiException;
import dy.utils.libhttp.httpservice.exception.ResultException;
import dy.utils.libhttp.httpservice.model.BaseResponse;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Auth : dy
 * Time : 2017/2/3 09:13
 * Email: dymh21342@163.com
 * Description:
 */

public class DObserver<T> implements Observer<BaseResponse<T>> {
    private String networkMsg = "网络错误";
    private String parseMsg = "解析错误";
    private String unknownMsg = "未知错误";


    @Override
    public void onError(Throwable e) {
        try{
            processException(e);
        }catch (Exception e1){
            e1.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(BaseResponse<T> tBaseResponse) {
    }


    protected boolean processException(Throwable e) {
        for(Throwable throwable = e; throwable.getCause() != null; throwable = throwable.getCause()) {
            e = throwable;
        }

        ApiException ex;
        if(e instanceof HttpException) {
            HttpException resultException = (HttpException)e;
            ex = new ApiException(e, resultException.code());
            switch(resultException.code()) {
                case 401:
                case 403:
                    this.onPermissionError(ex);
                    break;
                case 404:
                case 408:
                case 500:
                case 502:
                case 503:
                case 504:
                default:
                    ex.setDisplayMessage(this.networkMsg);
                    this.onClientError(ex);
            }
        } else if(e instanceof ResultException) {
            ResultException resultException1 = (ResultException)e;
            ex = new ApiException(resultException1, resultException1.getErrCode());
            this.onServiceError(ex);
        } else if(!(e instanceof JsonParseException) && !(e instanceof JSONException) && !(e instanceof ParseException)) {
            ex = new ApiException(e, 1000);
            ex.setDisplayMessage(this.unknownMsg);
            this.onClientError(ex);
        } else {
            ex = new ApiException(e, 1001);
            ex.setDisplayMessage(this.parseMsg);
            this.onClientError(ex);
        }

        return false;
    }

    protected void onClientError(ApiException ex) {
    }

    protected void onPermissionError(ApiException ex) {
    }

    protected void onServiceError(ApiException ex) {
    }

}
