package com.jt.sharemap.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import retrofit2.adapter.rxjava.HttpException;

public class NetworkUtils {

  /**
   * Returns true if the Throwable is an instance of RetrofitError with an http status code equals
   * to the given one.
   */
  public static boolean isHttpStatusCode(Throwable throwable, int statusCode) {
    return throwable instanceof HttpException && ((HttpException) throwable).code() == statusCode;
  }

  public static boolean isNetworkConnected(Context context) {
    ConnectivityManager cm =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
  }

  /**
   * 判断网络情况
   *
   * @param context
   * @return
   */
  public static boolean checkNetState(Context context) {
    if (context == null) {
      return false;
    }
    boolean flag = false;
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (manager.getActiveNetworkInfo() != null) {
      NetworkInfo info = manager.getActiveNetworkInfo();
      if (null != info) {
        flag = info.isAvailable();
      }
    }
    return flag;
  }

  /**
   * 检查当前网络是否可用
   *
   * @return
   */
  public static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

    if (connectivityManager == null) {
      return false;
    } else {
      NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

      if (networkInfo != null && networkInfo.length > 0) {
        for (int i = 0; i < networkInfo.length; i++) {
          // 判断当前网络状态是否为连接状态
          if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * （获取网络状态）
   *
   * @param context
   * @return
   */
  public static String getAPNType(Context context) {
    //结果返回值；
    String netType = null;

    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    //获取NetworkInfo对象
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    //NetworkInfo对象为空 则代表没有网络；
    if (networkInfo == null) {
      return netType;
    }

    int nType = networkInfo.getType();
    if (nType == ConnectivityManager.TYPE_WIFI) {
      //WIFI
      netType = "WIFI";
    } else if (nType == ConnectivityManager.TYPE_MOBILE) {
      int nSubType = networkInfo.getSubtype();
      TelephonyManager telephonyManager =
          (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
      //4G
      if (nSubType == TelephonyManager.NETWORK_TYPE_LTE && !telephonyManager.isNetworkRoaming()) {
        netType = "4G";
      } else if (nSubType == TelephonyManager.NETWORK_TYPE_UMTS
          || nSubType == TelephonyManager.NETWORK_TYPE_HSDPA
          || nSubType == TelephonyManager.NETWORK_TYPE_EVDO_0
              && !telephonyManager.isNetworkRoaming()) {
        netType = "3G";
        //2G 移动和联通的2G为GPRS或EGDE，电信的2G为CDMA
      } else if (nSubType == TelephonyManager.NETWORK_TYPE_GPRS
          || nSubType == TelephonyManager.NETWORK_TYPE_EDGE
          || nSubType == TelephonyManager.NETWORK_TYPE_CDMA
              && !telephonyManager.isNetworkRoaming()) {
        netType = "2G";
      } else {
        netType = "2G";
      }
    }

    return netType;
  }

  /**
   * 判断wifi是否可用
   *
   * @param context
   * @return
   */
  public static boolean WifiAvailable(Context context) {
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();

    if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      return true;
    }

    return false;
  }

  /**
   * 判读移动网络
   *
   * @param context
   * @return
   */
  public static boolean MobileAvailable(Context context) {
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();

    if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
      return true;
    }

    return false;
  }

  /**
   * 判断GPS是否打开
   *
   * @param context
   * @return
   */
  public static boolean ExistGPS(Context context) {
    LocationManager locationManager =
        (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      return true;
    }

    return false;
  }
}
