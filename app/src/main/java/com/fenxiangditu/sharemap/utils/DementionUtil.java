package com.fenxiangditu.sharemap.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

import java.lang.reflect.Field;

public class DementionUtil {
  /** 根据手机的分辨率从 dp 的单位 转成为 px(像素) */
  public static int dip2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }
  /** 根据手机的分辨率从 px(像素) 的单位 转成为 dp */
  public static int px2dip(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  public static int getScreenWidthInPx(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  public static int getScreenWidthInDp(Context context) {
    return px2dip(context, context.getResources().getDisplayMetrics().widthPixels);
  }

  public static int getScreenHeightInPx(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  public static int getScreenHeightInDp(Context context) {
    return px2dip(context, context.getResources().getDisplayMetrics().heightPixels);
  }

  public static int getStatusBarHeight(Context context) {
    Class<?> c = null;
    Object obj = null;
    Field field = null;
    int x = 0, statusBarHeight = 0;
    try {
      c = Class.forName("com.android.internal.R$dimen");
      obj = c.newInstance();
      field = c.getField("status_bar_height");
      x = Integer.parseInt(field.get(obj).toString());
      statusBarHeight = context.getResources().getDimensionPixelSize(x);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    return statusBarHeight;
  }

  public static void displayDeviceDemension(Activity activity) {
    DisplayMetrics dm = new DisplayMetrics();
    activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    float density = dm.density;
    int densityDPI = dm.densityDpi;
    float xdpi = dm.xdpi;
    float ydpi = dm.ydpi;
    Log.i("info", "xdpi = " + xdpi + "; ydpi = " + ydpi);
    Log.i("info", "density = " + density + "; densityDPI = " + densityDPI);
    int screenWidth = dm.widthPixels;
    int screenHeight = dm.heightPixels;
    Log.i("info", "screenWidth = " + screenWidth + "; screenHeight = " + screenHeight);
  }
}
