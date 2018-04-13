package com.jt.sharemap.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.media.ThumbnailUtils;
import android.util.Base64;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * for VLC 对图片的操作类，包括： 截屏，保存图片，获取指定路径的图片， 图片转换成字节数组，字节数组转换成图片，对图片的缩放
 *
 * @author cnmobi
 */
public class BitmapUtils {

  private static final long MB = 1024 * 1024;

  /**
   * 图片转换成字节数组
   *
   * @param bm 图片对象
   * @return
   */
  public static byte[] Bitmap2Bytes(Bitmap bm) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bm.compress(CompressFormat.PNG, 100, baos);
    return baos.toByteArray();
  }

  /**
   * byte转bitmap
   *
   * @param b
   * @return
   */
  public static Bitmap Bytes2Bitmap(byte[] b) {
    if (b.length != 0) {
      return BitmapFactory.decodeByteArray(b, 0, b.length);
    } else {
      return null;
    }
  }

  /**
   * 字节数组转换成图片
   *
   * @param intent Intent对象
   * @return 图片对象
   */
  public static Bitmap Bytes2Bitmap(Intent intent) {
    byte[] buff = intent.getByteArrayExtra("bitmap");
    Bitmap bm = BitmapFactory.decodeByteArray(buff, 0, buff.length);
    return bm;
  }

  /**
   * 截屏方法
   *
   * @param ，可以通过getActivity()方法获取
   * @return
   */
  public static Bitmap shot(Activity activity) {
    View view = activity.getWindow().getDecorView();
    view.setDrawingCacheEnabled(true);
    view.buildDrawingCache();
    Display display = activity.getWindowManager().getDefaultDisplay();
    view.layout(0, 500, display.getWidth() - 200, display.getHeight() - 250);
    Bitmap bitmap = view.getDrawingCache();
    Bitmap bmp = Bitmap.createBitmap(bitmap);
    // return Bitmap.createBitmap(bmp, 100,100, 500, 500);
    return bmp;
  }

  /**
   * 截取指定view的视图
   *
   * @param v 要截取的view对象
   * @return Bitmap对象
   */
  public static Bitmap getViewBitmap(View v) {
    v.clearFocus(); // 清除视图焦点
    v.setPressed(false); // 将视图设为不可点击

    boolean willNotCache = v.willNotCacheDrawing(); // 返回视图是否可以保存他的画图缓存
    v.setWillNotCacheDrawing(false);

    // Reset the drawing cache background color to fully transparent
    // for the duration of this operation
    // 将视图在此操作时置为透明
    int color = v.getDrawingCacheBackgroundColor(); // 获得绘制缓存位图的背景颜色
    v.setDrawingCacheBackgroundColor(0); // 设置绘图背景颜色
    if (color != 0) { // 如果获得的背景不是黑色的则释放以前的绘图缓存
      v.destroyDrawingCache(); // 释放绘图资源所使用的缓存
    }
    v.buildDrawingCache(); // 重新创建绘图缓存，此时的背景色是黑色
    Bitmap cacheBitmap = v.getDrawingCache(); // 将绘图缓存得到的,注意这里得到的只是一个图像的引用
    if (cacheBitmap == null) {
      return null;
    }
    Bitmap bitmap = Bitmap.createBitmap(cacheBitmap); // 将位图实例化
    // Restore the view
    // 恢复视图
    v.destroyDrawingCache(); // 释放位图内存
    v.setWillNotCacheDrawing(willNotCache); // 返回以前缓存设置
    v.setDrawingCacheBackgroundColor(color); // 返回以前的缓存颜色设置
    return bitmap;
  }

  /**
   * 保存图片到指定路径的方法
   *
   * @param path 图片保存的相对路径
   * @param name 图片的名字
   * @param bitmap 要保存的图片
   * @throws IOException 读写图片文件出现的异常信息
   */
  public static void save(String path, String name, Bitmap bitmap) throws IOException {
    File file = new File(path, name);
    // 若图片文件在SD卡的文件夹不存在
    if (!file.getParentFile().exists()) {
      // 创建该文件夹
      file.getParentFile().mkdirs();
    }
    // 若文件不存在，则创建
    if (!file.exists()) {
      file.createNewFile();
    }
    // 创建文件输出流
    FileOutputStream out = new FileOutputStream(file);
    // 保存图片至SD卡指定文件夹
    bitmap.compress(CompressFormat.JPEG, 100, out);
  }

  /**
   * 获得指定路径的图片
   *
   * @param path 图片的本地路径
   * @param name 图片的名字
   * @return 图片对象
   * @throws IOException
   */
  public static Bitmap getBitmap(String path, String name) throws IOException {
    BitmapFactory.Options options = new BitmapFactory.Options();
    File file = new File(path, name);
    if (file.exists() && (file.length() / MB) > 1) {
      options.inSampleSize = 2;
    }
    Bitmap imageBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    return imageBitmap;
  }

  public static Bitmap getBitmap(String path) {
    Bitmap imageBitmap = null;
    try {
      BitmapFactory.Options options = new BitmapFactory.Options();
      File file = new File(path);
      if (file.exists() && (file.length() / MB) > 1) {
        options.inSampleSize = 2;
      }
      imageBitmap = BitmapFactory.decodeFile(path, options);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return imageBitmap;
  }

  /**
   * * 图片的缩放方法（图片按照给定宽高缩放）
   *
   * @param ：源图片资源
   * @param newWidth ：缩放后宽度
   * @param newHeight ：缩放后高度
   * @return 可用的图片 bitmap对象
   */
  public static Bitmap zoomImage(Bitmap bm, double newWidth, double newHeight) {
    // 获取这个图片的宽和高
    float width = bm.getWidth();
    float height = bm.getHeight();
    // 创建操作图片用的matrix对象
    Matrix matrix = new Matrix();
    // 计算宽高缩放率
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;
    // 缩放图片动作
    matrix.postScale(scaleWidth, scaleHeight);
    Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, (int) width, (int) height, matrix, true);
    return bitmap;
  }

  /**
   * 获取视频缩略图
   *
   * @param videoPath
   * @param width
   * @param height
   * @param kind
   * @return
   */
  public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
    Bitmap bitmap = null;

    bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);

    if (bitmap == null) return bitmap;

    bitmap =
        ThumbnailUtils.extractThumbnail(
            bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
    return bitmap;
  }

  /**
   * 获取视频缩略图
   *
   * @param uri
   * @return
   */
  public static Bitmap createVideoThumbnail(String uri) {
    Bitmap bitmap = null;
    MediaMetadataRetriever mmr = new MediaMetadataRetriever();

    try {
      mmr.setDataSource(uri);
      bitmap = mmr.getFrameAtTime();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (RuntimeException e) {
      e.printStackTrace();
    } finally {
      try {
        mmr.release();
      } catch (RuntimeException e) {
        e.printStackTrace();
      }
    }

    return bitmap;
  }

  /**
   * 获取本地图片
   *
   * @param uri
   * @return
   */
  public static Bitmap getLoacalBitmap(String uri, int max_w, int max_h) {

    try {

      BitmapFactory.Options option = new BitmapFactory.Options();
      option.inSampleSize = getImageScale(uri, max_w, max_h);
      Bitmap bm = BitmapFactory.decodeFile(uri, option);

      return bm;

    } catch (Exception e) {
      e.printStackTrace();

      return null;
    }
  }

  /**
   * 获取图片
   *
   * @param res
   * @param id
   * @return
   */
  public static Bitmap getLoacalBitmap(
          Resources res, int id, int imageMaxWidth, int imageMaxHeight) {

    try {

      BitmapFactory.Options option = new BitmapFactory.Options();
      option.inSampleSize = getImageScale(res, id, imageMaxWidth, imageMaxHeight);
      Bitmap bm = BitmapFactory.decodeResource(res, id, option);

      return bm;

    } catch (Exception e) {
      e.printStackTrace();

      return null;
    }
  }

  public static int getImageScale(Resources res, int id, int imageMaxWidth, int imageMaxHeight)
      throws Exception {
    BitmapFactory.Options option = new BitmapFactory.Options();
    // set inJustDecodeBounds to true, allowing the caller to query the bitmap info without having to allocate the
    // memory for its pixels.
    option.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, id, option);

    int scale = 1;
    while (option.outWidth / scale >= imageMaxWidth || option.outHeight / scale >= imageMaxHeight) {
      scale *= 2;
    }
    return scale;
  }

  /**
   * scale image to fixed height and weight
   *
   * @param imagePath
   * @return
   */
  private static int getImageScale(String imagePath, int max_w, int max_h) throws Exception {

    BitmapFactory.Options option = new BitmapFactory.Options();
    // set inJustDecodeBounds to true, allowing the caller to query the bitmap info without having to allocate the
    // memory for its pixels.
    option.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(imagePath, option);

    int scale = 1;
    while (option.outWidth / scale >= max_w || option.outHeight / scale >= max_h) {
      scale *= 2;
    }
    return scale;
  }

  /**
   * 将bitmap转换成base64字符串
   *
   * @param bitmap
   * @return base64 字符串
   */
  public static String bitmaptoString(Bitmap bitmap, int bitmapQuality) {

    String string = null;
    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, bitmapQuality, bStream);
    byte[] bytes = bStream.toByteArray();
    string = Base64.encodeToString(bytes, Base64.DEFAULT);
    return string;
  }

  /**
   * 将base64转换成bitmap图片
   *
   * @param string base64字符串
   * @return bitmap
   */
  public static Bitmap stringtoBitmap(String string, ImageView imageView) {
    Bitmap bitmap = null;
    try {
      byte[] bitmapArray;
      bitmapArray = Base64.decode(string, Base64.DEFAULT);
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = true;
      options.inSampleSize =
          calculateInSampleSize(options, imageView.getWidth(), imageView.getHeight());
      options.inJustDecodeBounds = false;
      bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length, options);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  /**
   * bitmap转为base64
   *
   * @param bitmap
   * @return
   */
  public static String bitmapToBase64(Bitmap bitmap) {

    String result = null;
    ByteArrayOutputStream baos = null;
    try {
      if (bitmap != null) {
        baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);

        baos.flush();
        baos.close();

        byte[] bitmapBytes = baos.toByteArray();
        com.ampmind.onesdk.api.app.security.Base64.Encoder encoder =
            com.ampmind.onesdk.api.app.security.Base64.getEncoder();

        result = encoder.encodeToString(bitmapBytes);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (baos != null) {
          baos.flush();
          baos.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  private static int calculateInSampleSize(
          BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      final int halfHeight = height / 2;
      final int halfWidth = width / 2;

      // Calculate the largest inSampleSize value that is a power of 2 and keeps both
      // height and width larger than the requested height and width.
      while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
        inSampleSize *= 2;
      }
    }

    return inSampleSize;
  }

  /**
   * 保存图片为JPEG
   *
   * @param bitmap
   * @param path
   */
  public static void saveJPGE_After(Context context, Bitmap bitmap, String path, int quality) {
    File file = new File(path);
    makeDir(file);
    try {
      FileOutputStream out = new FileOutputStream(file);
      if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
        out.flush();
        out.close();
      }
      updateResources(context, file.getPath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 保存图片为PNG
   *
   * @param bitmap
   * @param path
   */
  public static void savePNG_After(Context context, Bitmap bitmap, String path, int quality) {
    File file = new File(path);
    makeDir(file);
    try {
      FileOutputStream out = new FileOutputStream(file);
      if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
        out.flush();
        out.close();
      }
      updateResources(context, file.getPath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 保存图片为PNG
   *
   * @param bitmap
   * @param path
   */
  public static void saveJPGE_After_WebP(Context context, Bitmap bitmap, String path, int quality) {
    File file = new File(path);
    makeDir(file);
    try {
      FileOutputStream out = new FileOutputStream(file);
      if (bitmap.compress(Bitmap.CompressFormat.WEBP, quality, out)) {
        out.flush();
        out.close();
      }
      updateResources(context, file.getPath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void makeDir(File file) {
    File tempPath = new File(file.getParent());
    if (!tempPath.exists()) {
      tempPath.mkdirs();
    }
  }

  public static void updateResources(Context context, String path) {
    MediaScannerConnection.scanFile(context, new String[] {path}, null, null);
  }

  /**
   * 生成圆角 bitmap
   *
   * @param bitmap
   * @return
   */
  public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
    try {
      Bitmap output =
          Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(output);
      final Paint paint = new Paint();
      final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
      final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
      final float roundPx = 14;
      paint.setAntiAlias(true);
      canvas.drawARGB(0, 0, 0, 0);
      paint.setColor(Color.BLACK);
      canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
      paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

      final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

      canvas.drawBitmap(bitmap, src, rect, paint);
      return output;
    } catch (Exception e) {
      return bitmap;
    }
  }
}
