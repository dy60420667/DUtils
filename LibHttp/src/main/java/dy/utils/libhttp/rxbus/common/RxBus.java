package dy.utils.libhttp.rxbus.common;



import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

/**
 * 消息推送，取代EventBus
 * 介绍：http://www.jianshu.com/p/61b67567bb8a
 */
public class RxBus {
    private static volatile RxBus defaultInstance;
    private final FlowableProcessor<Object> mBus;

    private RxBus() {
        mBus = PublishProcessor.create().toSerialized();
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

    /**
     * 发送消息
     * @param o
     */
    public void post(Object o) {
        new SerializedSubscriber<>(mBus).onNext(o);
    }

    /**
     * 确定接收消息的类型
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }

    /**
     * 判断是否有订阅者
     * @return
     */
    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

}