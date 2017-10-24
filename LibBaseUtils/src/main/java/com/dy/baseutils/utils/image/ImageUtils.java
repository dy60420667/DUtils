package com.dy.baseutils.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.dy.baseutils.Tools;

/**
 * Auth : dy
 * Time : 2017/2/3 14:46
 * Email: dymh21342@163.com
 * Description:
 */

public class ImageUtils {
    /**
     * 压缩图片
     * 该方法会导致压缩的图片虽然再内存里面变小了，但是图片本身还是很大
     * @param path
     *            文件路径
     * @return
     */
    public static Bitmap decodeBitmapNoMax(String path, Context context) {
        int w = Tools.getScreenWidth(context);
        int h = Tools.getScreenHeight(context);
        BitmapFactory.Options op = new BitmapFactory.Options();
        // 值设为true,将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
        op.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, op); // 获取尺寸信息
        // 获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / w);
        int hRatio = (int) Math.ceil(op.outHeight / h);

        // 如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 && hRatio > 1) {
            if (wRatio > hRatio) {
                op.inSampleSize = wRatio;
            } else {
                op.inSampleSize = hRatio;
            }
        }
        op.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeFile(path, op);
        return bmp;
    }

}
