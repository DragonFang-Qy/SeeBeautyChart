package com.fangzitcl.libs.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.*;
import java.lang.ref.WeakReference;

/**
 * @ClassName: UtilImage
 * @PackageName: com.fangzitcl.libs.util
 * @Acthor: Fang_QingYou
 * @Time: 2016.01.05 16:30
 */
public class UtilImage {

    private static int myWidth;
    private static int myHeight;
    private static int myQuality = 30;

    private static void setWidth(int width) {
        myWidth = width;
    }

    private static void setHeight(int height) {
        myHeight = height;
    }

    public static int getMyQuality() {
        return myQuality;
    }

    public static void setMyQuality(int myQuality) {
        UtilImage.myQuality = myQuality;
    }

    /**
     * 计算压缩比例
     *
     * @param options
     * @param imageHeight 目标高
     * @param imageWidth  目标宽
     * @return
     * @author: Fang Qingyou
     * @date 2015年7月16日下午4:16:23
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int imageWidth,
                                             int imageHeight) {
        // 实际宽高
        int outHeight = options.outHeight;
        int outWidth = options.outWidth;

        int inSampleSize = 1;// 缩放比例，默认为1
        if (outHeight > imageHeight || outWidth > imageWidth) {
            // 当outHeight >imageHeight|| outWidth > imageWidth 时进行缩放
            int heightFlag = outHeight / imageHeight;
            int widthFlag = outWidth / imageWidth;

            inSampleSize = heightFlag < widthFlag ? heightFlag : widthFlag;

        }

        return inSampleSize % 2 == 0 ? inSampleSize : inSampleSize - 1;
        // 官方文档中说，inSampleSize这个属性最好是2的倍数，这样处理更快，效率更高。
    }

    /**
     * 压缩图片，不处理反转信息
     *
     * @param fileName
     * @return
     * @author: Fang Qingyou
     * @date 2015年7月16日下午4:15:38
     */
    public static Bitmap getSmallBitmap(String fileName,
                                        FileOutputStream outputStream) {
        Bitmap bitmap = getSmallBitmap(fileName, 400, 800, outputStream);

        return bitmap;
    }

    /**
     * 压缩图片，不处理反转信息
     *
     * @param fileName
     * @return
     * @author: Fang Qingyou
     * @date 2015年7月16日下午4:15:38
     */
    public static Bitmap getSmallBitmap(String fileName) {
        Bitmap bitmap = getSmallBitmap(fileName, 400, 800, null);
        return bitmap;
    }

    /**
     * 压缩图片，不处理反转信息
     *
     * @param fileName
     * @param width
     * @param height
     * @param outputStream
     * @return
     * @author: Fang Qingyou
     * @date 2015年7月16日下午4:20:59
     */
    public static Bitmap getSmallBitmap(String fileName, int width, int height,
                                        FileOutputStream outputStream) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置为true，只读边框，不读内容
        BitmapFactory.decodeFile(fileName, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        setWidth(width);
        setHeight(height);

        options.inSampleSize = calculateInSampleSize(options, myWidth, myHeight);

        options.inJustDecodeBounds = false;
        if (outputStream == null) {
            return BitmapFactory.decodeFile(fileName, options);
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);
            bitmap = compressionQuality(bitmap, outputStream);

            return bitmap;
        }

    }

    /**
     * 压缩图片,处理反转信息
     *
     * @param filePath
     * @return
     * @Title: getCompressBitmap
     */
    public static Bitmap getCompressBitmap(String filePath,
                                           FileOutputStream outputStream) {
        Bitmap bitmap = getCompressBitmap(filePath, 400, 800, outputStream);
        return bitmap;
    }

    /**
     * 压缩图片,处理反转信息
     *
     * @param filePath
     * @return
     * @Title: getCompressBitmap
     */
    public static Bitmap getCompressBitmap(String filePath) {
        Bitmap bitmap = getCompressBitmap(filePath, 400, 800, null);
        return bitmap;
    }

    /**
     * 压缩图片,处理反转信息
     *
     * @param filePath
     * @param width    目标宽
     * @param height   目标高
     * @return
     * @Title: getCompressBitmap
     */
    public static Bitmap getCompressBitmap(String filePath, int width,
                                           int height, FileOutputStream outputStream) {
        Bitmap bitmap = getSmallBitmap(filePath, width, height, outputStream);
        bitmap = compressionQuality(bitmap, outputStream);
        if (bitmap != null) {
            try {
                ExifInterface exifInterface = new ExifInterface(filePath);
                int result = exifInterface.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                int rotate = 0;
                switch (result) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotate = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotate = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotate = 270;
                        break;
                    default:
                        break;
                }
                Bitmap rotated = rotateBitmap(bitmap, rotate);
                return rotated;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;

    }

    /**
     * 旋转bitmap
     *
     * @param bitmap
     * @param rotate
     * @return
     * @Title: rotateBitmap
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * 压缩图片质量（清晰度）
     *
     * @param bitmap
     * @param outputStream
     * @return
     */
    private static Bitmap compressionQuality(Bitmap bitmap, FileOutputStream outputStream) {

        // 压缩质量  85：100-85=25 压缩率25%
        bitmap.compress(Bitmap.CompressFormat.JPEG, getMyQuality(), outputStream);
        return bitmap;
    }


    // 从路径获取文件名
    public static String getFileName(String pathandname) {
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return pathandname.substring(start + 1, end);
        } else {
            return null;
        }
    }

    // 通过路径生成Base64文件
    public static String getBase64FromPath(String path) {
        String base64 = "";
        try {
            File file = new File(path);
            byte[] buffer = new byte[(int) file.length() + 100];
            @SuppressWarnings("resource")
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    // 通过文件路径获取到bitmap
    public static Bitmap getBitmapFromPath(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    // 把bitmap转换成base64
    public static String getBase64FromBitmap(Bitmap bitmap, int bitmapQuality) {
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
        byte[] bytes = bStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    // 把base64转换成bitmap
    public static Bitmap getBitmapFromBase64(String string) {
        byte[] bitmapArray = null;
        try {
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    // 把Stream转换成String
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
