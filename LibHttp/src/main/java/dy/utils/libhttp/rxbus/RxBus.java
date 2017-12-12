package dy.utils.libhttp.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 消息推送，取代EventBus
 * 介绍：http://www.jianshu.com/p/ca090f6e2fe2
 */
public class RxBus {
    private static volatile RxBus defaultInstance;
    private final Subject bus = new SerializedSubject(PublishSubject.create());

    public RxBus() {
    }

    public static RxBus getDefault() {
        if(defaultInstance == null) {
            synchronized(RxBus.class) {
                if(defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    public void post(Object o) {
        this.bus.onNext(o);
    }

    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return this.bus.ofType(eventType);
    }
}