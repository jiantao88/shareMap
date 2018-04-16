package com.jt.sharemap.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 *
 * <p>设备工具
 */
public class DeviceUtil {

  /**
   * 获取屏幕尺寸(屏幕对角线的长度)
   *
   * @param context
   * @return
   */
  public static double getScreenSize(Context context) {
    DisplayMetrics dm = context.getResources().getDisplayMetrics();
    int width = dm.widthPixels;
    int height = dm.heightPixels;
    int dens = dm.densityDpi;
    double wi = (double) width / (double) dens;
    double hi = (double) height / (double) dens;
    double x = Math.pow(wi, 2);
    double y = Math.pow(hi, 2);
    double screenInches = Math.sqrt(x + y);

    return screenInches;
  }

  /**
   * dp 转为 px (四舍五入)
   *
   * @param context
   * @param dpValue
   * @return
   */
  public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * （px）转化为（dp）四舍五入；
   *
   * @param context
   * @param pxValue
   * @return
   */
  public static int px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }

  /**
   * 获取设备宽度（px）；
   *
   * @param context
   * @return
   */
  public static int deviceWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  /**
   * 获取设备高度（px）；
   *
   * @param context
   * @return
   */
  public static int deviceHeight(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  /**
   * 获取设备序列号；
   *
   * @return
   */
  public static String getSerialNumber() {
    return Build.SERIAL;
  }

  /**
   * 获取设备的IMEI
   *
   * @param context
   * @return
   */
  public static String getDeviceId(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String deviceId = tm.getDeviceId();

    return deviceId;
  }

  /**
   * 获取手机品牌
   *
   * @return
   */
  public static String getPhoneBrand() {
    return Build.BRAND;
  }

  /**
   * 获取手机型号
   *
   * @return
   */
  public static String getPhoneModel() {
    return Build.MODEL;
  }

  /**
   * （获取手机Android API等级）；
   *
   * @return
   */
  public static int getBuildLevel() {
    return Build.VERSION.SDK_INT;
  }

  /**
   * （获取手机Android 版本）；
   *
   * @return
   */
  public static String getBuildVersion() {
    return Build.VERSION.RELEASE;
  }

  public static String getLanguage(Context context) {
    Locale locale = context.getResources().getConfiguration().locale;
    String language = locale.getLanguage();
    return language;
  }

  /**
   * 判断SD卡是否存在；
   *
   * @return
   */
  public static boolean ExistSDCard() {
    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
      return true;
    }

    return false;
  }

  /**
   * get sd memory (MB)
   *
   * @return
   */
  public static long getSDFreeSize() {
    File path = Environment.getExternalStorageDirectory();

    StatFs statFs = new StatFs(path.getPath());

    //获得单个数据块的大小
    long blockSize = statFs.getBlockSize();

    //获得空闲数据块的个数
    long freeBlock = statFs.getAvailableBlocks();
    return (freeBlock * blockSize) / 1024 / 1024; //单位MB
  }

  /**
   * get sd total memory (MB)
   *
   * @return
   */
  public static long getSDAllSize() {
    File path = Environment.getExternalStorageDirectory();
    StatFs statFs = new StatFs(path.getPath());

    //（获得单个数据块的大小）
    long blockSize = statFs.getBlockSize();

    //（获得全部数据块的个数）
    long allBlock = statFs.getBlockCount();
    return (allBlock * blockSize) / 1024 / 1024;
  }

  /**
   * get meta data from AndroidManifest.xml
   *
   * @param context
   * @param name
   * @return
   */
  public static String getMetaData(Context context, String name) {
    String value = null;
    try {
      ApplicationInfo appInfo =
          context
              .getPackageManager()
              .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
      value = appInfo.metaData.getString(name);
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return value;
  }

  /**
   * 判断是否root
   *
   * @return
   */
  public static boolean isRoot() {
    String binPath = "/system/bin/su";
    String xBinPath = "/system/xbin/su";
    if (new File(binPath).exists() && isExecutable(binPath)) return true;
    if (new File(xBinPath).exists() && isExecutable(xBinPath)) return true;
    return false;
  }

  private static boolean isExecutable(String filePath) {
    Process p = null;
    try {
      p = Runtime.getRuntime().exec("ls -l " + filePath);
      // 获取返回内容
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
      String str = in.readLine();
      if (str != null && str.length() >= 4) {
        char flag = str.charAt(3);
        if (flag == 's' || flag == 'x') return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (p != null) {
        p.destroy();
      }
    }
    return false;
  }

  /**
   * 执行shell命令
   *
   * <p>会引起非常严重的性能问题，将手机系统拖的非常慢，当应用多次启动后会创建出很多个僵死的进程耗用内存
   *
   * @param command "su" 是否root
   * @return http://code.google.com/p/roottools/ RootTools.isRootAvailable()判断是否root
   *     （RootTools.isAccessGiven()返回true那么手机已经root并且app也被授予root权限）；
   */
  private boolean executeShellCommand(String command) {
    Process process = null;
    try {
      process = Runtime.getRuntime().exec(command);
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      if (process != null) {
        try {
          process.destroy();
        } catch (Exception e) {
        }
      }
    }
  }

  public static float pxToDp(float px) {
    float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
    return px / (densityDpi / 160f);
  }

  public static int dpToPx(int dp) {
    float density = Resources.getSystem().getDisplayMetrics().density;
    return Math.round(dp * density);
  }
}
