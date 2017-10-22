package com.dy.baseutils.utils.glide;

import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * Auth : dy
 * Time : 2017/2/3 10:37
 * Email: dymh21342@163.com
 * Description: Glide使用方法
 * http://blog.csdn.net/shangmingchao/article/details/51125554/
 */

public class GlideUtils {
    public static void GlearGlide(final Context context){
        //        清除内存缓存： // 必须在UI线程中调用
        Glide.get(context).clearMemory();
        // 必须在后台线程中调用，建议同时clearMemory()
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

//    //圆形裁剪
//    Glide.with(this)
//            .load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
//    .bitmapTransform(new CropCircleTransformation(this))
//            .into(iv_0);
//    //圆角处理
//    Glide.with(this)
//            .load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
//    .bitmapTransform(new RoundedCornersTransformation(this,30,0, RoundedCornersTransformation.CornerType.ALL))
//            .into(iv_0);
//    //灰度处理
//    Glide.with(this)
//            .load("http://inthecheesefactory.com/uploads/source/nestedfragment/fragments.png")
//    .bitmapTransform(new GrayscaleTransformation(this))
//            .into(iv_0);
//    //其它变换...
}
