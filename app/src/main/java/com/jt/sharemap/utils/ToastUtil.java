package com.jt.sharemap.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chehejia.ampmind.R;

import java.lang.ref.WeakReference;

/** Created by chenjianquan on 2017/4/13. */
public class ToastUtil {
  private static Toast mToast;
  private static boolean isShow;
  private static Handler mHandler = new Handler();
  private static Runnable r =
      new Runnable() {
        public void run() {
          mToast.cancel();
          isShow = false;
        }
      };

  private static TextView tv_toast;

  private static WeakReference<Context> mContext;

  @SuppressLint("ShowToast")
  public static void initCustomToast(Context context) {
    if (null == mToast) {
      synchronized (ToastUtil.class) {
        if (null == mToast) {
          mContext = new WeakReference<>(context);
          mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
          mToast.setGravity(Gravity.CENTER, 0, 0);
          View toastRoot = LayoutInflater.from(context).inflate(R.layout.layout_toast_view, null);
          tv_toast = (TextView) toastRoot.findViewById(R.id.toast_tv);
          mToast.setView(toastRoot);
        }
      }
    }
  }

  /**
   * 显示toast,移除前一个提示,只在应用在前台的时候显示
   *
   * @param text 显示内容
   * @param duration 显示时间
   */
  public static void showToast(final String text, int duration) {
    if (TextUtils.isEmpty(text)) {
      return;
    }
    mHandler.removeCallbacks(r);
/*    int second = text.length() / 10 + 1;
    duration = Math.min(duration, second * 1000);*/
    mHandler.postDelayed(r, duration);
    Context context = mContext.get();
    if (null != context && AppUtils.isAppOnForeground(context)) {
      mHandler.post(
          new Runnable() {
            @Override
            public void run() {
              tv_toast.setText(text);
              isShow = true;
              mToast.show();
            }
          });
    }
  }

  public static void showToast(String text) {
    if (!isShow) {
      showToast(text, 2000);
    }
  }

  public static void showToast(@StringRes int resId) {
    Context context = mContext.get();
    if (null != context && !isShow) {
      String string = context.getResources().getString(resId);
      showToast(string, 2000);
    }
  }
}
