package com.dy.baseutils.utils.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

import java.io.ByteArrayOutputStream;

/**
 * Descripty:
 * Date: 2018/1/9.16:14
 */
public class ImageDecodeUtils {
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap){
        try{
            return new BitmapDrawable(bitmap);
        }catch (Exception e){
            return  null;
        }
    }
    public static Bitmap getBitmapFromResource(Context mContext, int resourceid){
        try{
            Resources res = mContext.getResources();
            Bitmap bmp = BitmapFactory.decodeResource(res, resourceid);
            return bmp;
        }catch (Exception e){
            return null;
        }
    }

    public static byte[] Bitmap2Bytes(Bitmap bm) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }catch (Exception e){
            return null;
        }
    }

    public static Bitmap Bytes2Bimap(byte[] b) {
        try{
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap Bytes2Bimap(byte[] b, Camera mCamera) {
        try{
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
