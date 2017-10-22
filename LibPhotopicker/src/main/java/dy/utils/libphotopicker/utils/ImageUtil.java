package dy.utils.libphotopicker.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Auth : mxp
 * Time : 2016/8/3 09:46
 * Email: dymh21342@163.com
 * Description:
 */
public class ImageUtil {
    private static final int PIC_MEGA_BYTE = 1048576;

    /**
     * 压缩图片
     * @param path
     *            文件路径
     * @return
     */
    public static Bitmap decodeBitmapNoMax(String path, Context context) {
        int w = ScreenUtil.getScreenWidth(context);
        int h = ScreenUtil.getScreenHeight(context);
        BitmapFactory.Options op = new BitmapFactory.Options();
        // 值设为true,将不返回实际的bitmap不给其分配内存空间而里面只包括一些解码边界信息即图片大小信息
        op.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, op); // 获取尺寸信息
        // 获取比例大小
        int wRatio = (int) Math.ceil(op.outWidth / w);
        int hRatio = (int) Math.ceil(op.outHeight / h);

        // 如果超出指定大小，则缩小相应的比例
        if (wRatio > 1 || hRatio > 1) {
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

    /**
     * 获取文件大小
     * @param path
     * @return
     * @throws Exception
     */
    public static int getFileSizes(String path){//取得文件大小
        int s = 0;
        if (path != null && !path.equals("")) {
            File f = new File(path);
            FileInputStream fis = null;
            try {
                if (f.exists()) {
                    fis = new FileInputStream(f);
                    s = fis.available();
                } else {
                    f.createNewFile();
                }
            } catch (Exception e) {
                e.getMessage();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return s;
    }

    public static boolean getListTotalSizeExceed(ArrayList<String> paths, String newPath) {
        int total = 0;
        int fileSize;

        ArrayList<String> list = new ArrayList<>();
        list.addAll(paths);
        list.add(newPath);

        //上传图片大于30M判断
        for (int i = 0; i < list.size(); i++){
            try {
                //获取文件大小
                fileSize = getFileSizes(list.get(i));
                //循环累加
                total = total+fileSize;
                if (total > 30 * PIC_MEGA_BYTE){
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
