package com.dy.baseutils.viewinject;

import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dy.baseutils.utils.log.LD;
import com.dy.baseutils.viewinject.view.ResLoader;
import com.dy.baseutils.viewinject.view.ViewFinder;
import com.dy.baseutils.viewinject.view.ViewInjectInfo;
import com.dy.baseutils.viewinject.view.annotation.ContentView;
import com.dy.baseutils.viewinject.view.annotation.PreferenceInject;
import com.dy.baseutils.viewinject.view.annotation.ResInject;
import com.dy.baseutils.viewinject.view.annotation.ViewInject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Descripty: 注解类  参考XUtils:https://github.com/wyouflf/xUtils
 * Date: 2018/1/16.10:49
 * // xUtils的view注解要求必须提供id，以使代码混淆不受影响。
 *  @ViewInject(R.id.textView)
 * TextView textView;
 *
 * //@ViewInject(vale=R.id.textView, parentId=R.id.parentView)
 * //TextView textView;
 *
 * @ResInject(id = R.string.label, type = ResType.String)
 *  private String label;
 * // 取消了之前使用方法名绑定事件的方式，使用id绑定不受混淆影响
 * // 支持绑定多个id @OnClick({R.id.id1, R.id.id2, R.id.id3})
 * // or @OnClick(value={R.id.id1, R.id.id2, R.id.id3}, parentId={R.id.pid1, R.id.pid2, R.id.pid3})
 * // 更多事件支持参见ViewCommonEventListener类和包com.lidroid.xutils.view.annotation.event。
 * @OnClick(R.id.test_button)
 * public void testButtonClick(View v) { // 方法签名必须和接口中的要求一致
 * ...
 * }
 * ...
 * //在Activity中注入：
 * @Override
 * public void onCreate(Bundle savedInstanceState) {
 * super.onCreate(savedInstanceState);
 * setContentView(R.layout.main);
 * ViewUtils.inject(this); //注入view和事件
 * ...
 * textView.setText("some text...");
 * ...
 * }
 * //在Fragment中注入：
 * @Override
 * public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 * View view = inflater.inflate(R.layout.bitmap_fragment, container, false); // 加载fragment布局
 * ViewUtils.inject(this, view); //注入view和事件
 * ...
 * }
 * //在PreferenceFragment中注入：
 * public void onActivityCreated(Bundle savedInstanceState) {
 * super.onActivityCreated(savedInstanceState);
 * ViewUtils.inject(this, getPreferenceScreen()); //注入view和事件
 * ...
 * }
 * // 其他重载
 * // inject(View view);
 * // inject(Activity activity)
 * // inject(PreferenceActivity preferenceActivity)
 * // inject(Object handler, View view)
 * // inject(Object handler, Activity activity)
 * // inject(Object handler, PreferenceGroup preferenceGroup)
 * // inject(Object handler, PreferenceActivity preferenceActivity)
 */

public class ViewUtils {
    private static String TAG = "ViewUtils";
    public static void inject(View view) {
        injectObject(view, new ViewFinder(view));
    }

    public static void inject(Activity activity) {
        injectObject(activity, new ViewFinder(activity));
    }

    public static void inject(PreferenceActivity preferenceActivity) {
        injectObject(preferenceActivity, new ViewFinder(preferenceActivity));
    }

    public static void inject(Object handler, View view) {
        injectObject(handler, new ViewFinder(view));
    }

    public static void inject(Object handler, Activity activity) {
        injectObject(handler, new ViewFinder(activity));
    }

    public static void inject(Object handler, PreferenceGroup preferenceGroup) {
        injectObject(handler, new ViewFinder(preferenceGroup));
    }

    public static void inject(Object handler, PreferenceActivity preferenceActivity) {
        injectObject(handler, new ViewFinder(preferenceActivity));
    }


    private static void injectObject(Object handler, ViewFinder finder) {
        Class<?> handlerType = handler.getClass();
        // inject ContentView
        ContentView contentView = handlerType.getAnnotation(ContentView.class);
        if (contentView != null) {
            try {
                Method setContentViewMethod = handlerType.getMethod("setContentView", int.class);
                setContentViewMethod.invoke(handler, contentView.value());
            } catch (Throwable e) {
                LD.e(TAG, e.getMessage());
            }
        }

        // inject view
        Field[] fields = handlerType.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                ViewInject viewInject = field.getAnnotation(ViewInject.class);
                if (viewInject != null) {
                    try {
                        View view = finder.findViewById(viewInject.value(), viewInject.parentId());
                        if (view != null) {
                            field.setAccessible(true);
                            field.set(handler, view);
                        }
                    } catch (Throwable e) {
                        LD.e(TAG, e.getMessage());
                    }
                } else {
                    ResInject resInject = field.getAnnotation(ResInject.class);
                    if (resInject != null) {
                        try {
                            Object res = ResLoader.loadRes(
                                    resInject.type(), finder.getContext(), resInject.id());
                            if (res != null) {
                                field.setAccessible(true);
                                field.set(handler, res);
                            }
                        } catch (Throwable e) {
                            LD.e(TAG, e.getMessage());
                        }
                    } else {
                        PreferenceInject preferenceInject = field.getAnnotation(PreferenceInject.class);
                        if (preferenceInject != null) {
                            try {
                                Preference preference = finder.findPreference(preferenceInject.value());
                                if (preference != null) {
                                    field.setAccessible(true);
                                    field.set(handler, preference);
                                }
                            } catch (Throwable e) {
                                LD.e(TAG, e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }
}
