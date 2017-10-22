package com.dy.baseutils.utils.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Auth : dy
 * Time : 2017/2/3 10:12
 * Email: dymh21342@163.com
 * Description:
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (1.2 * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (1.2 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

//        builder.setBitmapPool(new LruBitmapPool(sizeInBytes));
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        //图片缓存到"/data/data/com.xxx.xxx/cache/Image"文件夹下
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "Image", 50 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
//        glide.register(Model.class, Data.class, new MyModelLoaderFactory());
    }
}
