package dy.utils.libhttp.rxbus;

import dy.utils.libhttp.rxbus.common.ErrorBean;
import dy.utils.libhttp.rxbus.common.ErrorCode;
import dy.utils.libhttp.rxbus.common.RxBus;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Descripty:http://www.jianshu.com/p/61b67567bb8a
 * Auth:  邓渊  dengyuan
 * Date: 2017/12/15.10:29
 *
 */
public class RxBusHelper {
    /**
     * 发布消息
     *
     * @param o
     */
    public static void post(Object o) {
        RxBus.getDefault().post(o);
    }

    /**
     * 接收消息,并在主线程处理
     *
     * @param aClass
     * @param disposables 用于存放消息
     * @param listener
     * @param <T>
     */
    public static <T> void doOnMainThread(Class<T> aClass, CompositeDisposable disposables, OnEventListener<T> listener) {
        disposables.add(RxBus.getDefault().toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).subscribe(listener::onEvent, throwable -> listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS))));
    }

    public static <T> void doOnMainThread(Class<T> aClass, OnEventListener<T> listener) {
        RxBus.getDefault().toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).subscribe(listener::onEvent, throwable -> listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS)));
    }

    /**
     * 接收消息,并在子线程处理
     *
     * @param aClass
     * @param disposables
     * @param listener
     * @param <T>
     */
    public static <T> void doOnChildThread(Class<T> aClass, CompositeDisposable disposables, OnEventListener<T> listener) {
        disposables.add(RxBus.getDefault().toFlowable(aClass).subscribeOn(Schedulers.newThread()).subscribe(listener::onEvent, throwable -> listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS))));
    }

    public static <T> void doOnChildThread(Class<T> aClass, OnEventListener<T> listener) {
        RxBus.getDefault().toFlowable(aClass).subscribeOn(Schedulers.newThread()).subscribe(listener::onEvent, throwable -> listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_RXBUS, ErrorCode.ERROR_DESC_RXBUS)));
    }

    public interface OnEventListener<T> {
        void onEvent(T t);

        void onError(ErrorBean errorBean);
    }

}