package com.jt.sharemap.utils;

import android.text.TextUtils;

/** Created by zhangjiantao on 2017/9/22. */
public class PhoneUtils {
  /**
   * 将手机号替换 *
   *
   * @param mobileString
   * @return
   */
  public static String replaceMobile(String mobileString) {
    if (!TextUtils.isEmpty(mobileString)){
      return mobileString.replaceAll("(?<=\\d{3})\\d(?=\\d{4})", "*");
    }else {
      return "";
    }
  }
  /**
   * 将手机号替换 *
   *
   * @param mobileString
   * @return
   */
  public static String replaceMobile(String mobileString, int start, int end) {
    return mobileString.replaceAll("(?<=\\d{" + start + "})\\d(?=\\d{" + end + "})", "*");
  }
}
