package com.jt.sharemap.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.support.annotation.AttrRes;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import static com.jt.sharemap.utils.DeviceUtil.ExistSDCard;


/** * 应用相关工具类 */
@SuppressWarnings("unused")
public class AppUtils {

  /**
   * 判断应用程序是否在前台
   *
   * @param context
   * @return
   */
  public static boolean isAppOnForeground(Context context) {
    if (context == null) {
      return false;
    }
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    String packageName = context.getPackageName();
    List<RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);
    if (appTask == null || appTask.size() < 1) {
      return false;
    }
    if (appTask.get(0).baseIntent.toString().contains(packageName)) {
      return true;
    }
    return false;
  }

  /**
   * 判断activity是否在栈中
   *
   * @param context
   * @return
   */
  public static boolean isInStack(Activity context) {
    Intent mainIntent = new Intent(context, context.getClass());
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningTaskInfo> appTask = am.getRunningTasks(1);
    if (appTask.size() > 0 && appTask.get(0).baseActivity.equals(mainIntent.getComponent())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断activity是否在栈顶
   *
   * @param activityName
   * @param context
   * @return
   */
  public static boolean isTopActivity(String activityName, Context context) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(Integer.MAX_VALUE);
    String cmpNameTemp = null;
    if (null != runningTaskInfos) {
      cmpNameTemp = (runningTaskInfos.get(0).topActivity).toString();
    }
    if (null == cmpNameTemp) {
      return false;
    }
    return cmpNameTemp.contains(activityName);
  }

  /**
   * 获取当前App进程的id
   *
   * @return
   */
  public static int getAppProcessId() {
    return android.os.Process.myPid();
  }

  /**
   * 获取进程名
   *
   * @param context
   * @return
   */
  public static String getProcessName(Context context) {
    int pid = android.os.Process.myPid();
    ActivityManager activityManager =
        (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (ActivityManager.RunningAppProcessInfo info : activityManager.getRunningAppProcesses()) {
      if (pid == info.pid) {
        return info.processName;
      }
    }
    return null;
  }

  /**
   * 获取当前App进程的Name
   *
   * @param context
   * @param processId
   * @return
   */
  public static String getAppProcessName(Context context, int processId) {
    String processName = null;
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    // get all App的进程集合；
    List l = am.getRunningAppProcesses();
    Iterator i = l.iterator();
    PackageManager pm = context.getPackageManager();
    while (i.hasNext()) {
      ActivityManager.RunningAppProcessInfo info =
          (ActivityManager.RunningAppProcessInfo) (i.next());
      try {
        if (info.pid == processId) {
          CharSequence c =
              pm.getApplicationLabel(
                  pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

          processName = info.processName;
          return processName;
        }
      } catch (Exception e) {
        Log.e(DeviceUtil.class.getName(), e.getMessage(), e);
      }
    }
    return processName;
  }

  /**
   * 隐藏系统软键盘
   *
   * @param context
   * @param ed
   */
  public static void hideSoftInputMethod(Activity context, EditText ed) {
    context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

    int currentVersion = Build.VERSION.SDK_INT;
    String methodName = null;
    if (currentVersion >= 16) {
      // 4.2
      methodName = "setShowSoftInputOnFocus";
    } else if (currentVersion >= 14) {
      // 4.0
      methodName = "setSoftInputShownOnFocus";
    }

    if (methodName == null) {
      ed.setInputType(InputType.TYPE_NULL);
    } else {
      Class<EditText> cls = EditText.class;
      Method setShowSoftInputOnFocus;
      try {
        setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
        setShowSoftInputOnFocus.setAccessible(true);
        setShowSoftInputOnFocus.invoke(ed, false);
      } catch (NoSuchMethodException e) {
        ed.setInputType(InputType.TYPE_NULL);
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

  @TargetApi(14)
  public static int getActionBarHeight(Context context) {
    int result = 0;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
      TypedValue tv = new TypedValue();
      context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
      result =
          TypedValue.complexToDimensionPixelSize(
              tv.data, context.getResources().getDisplayMetrics());
    }
    return result;
  }

  /**
   * 获取顶部状态栏高度
   *
   * @param context
   * @return
   */
  public static int getStatusBarHeight(Context context) {
    Resources res = context.getResources();
    int height = 0;
    int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      height = res.getDimensionPixelSize(resourceId);
    }
    return height;
  }

  /**
   * 获取导航栏高度
   *
   * @return
   */
  public static int getNavigationBarHeight(Context context) {
    Resources resources = context.getResources();
    int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
    int height = 0;
    if (resourceId > 0) {
      height = resources.getDimensionPixelSize(resourceId);
    }
    return height;
  }

  /** 获取虚拟功能键高度 */
  public static int getVirtualBarHeigh(Context context) {
    int vh = 0;
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    DisplayMetrics dm = new DisplayMetrics();
    try {
      @SuppressWarnings("rawtypes")
      Class c = Class.forName("android.view.Display");
      @SuppressWarnings("unchecked")
      Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
      method.invoke(display, dm);
      vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return vh;
  }

  /**
   * 复制内容到剪切板
   *
   * @param context
   * @param content
   */
  public static void copy2Clip(Context context, String content) {
    ClipboardManager copy = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    copy.setPrimaryClip(ClipData.newPlainText("", content));
  }

  /**
   * 获取attr中资源id
   *
   * @param context
   * @param attrId
   * @return resID
   */
  public static int getValueOfAttr(Context context, int attrId) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(attrId, typedValue, true);
    return typedValue.resourceId;
  }

  /**
   * 获取attr中color的值
   *
   * @param context
   * @param arrtId
   * @return color值
   */
  public static int getColorOfColorAttr(Context context, @AttrRes int arrtId) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(arrtId, typedValue, true);
    return context.getResources().getColor(typedValue.resourceId);
  }

  /**
   * 获取attr中drawable的值
   *
   * @param context
   * @param arrtId
   * @return drawable值
   */
  public static Drawable getDrawableOfColorAttr(Context context, @AttrRes int arrtId) {
    TypedValue typedValue = new TypedValue();
    context.getTheme().resolveAttribute(arrtId, typedValue, true);
    return context.getResources().getDrawable(typedValue.resourceId);
  }

  /**
   * 收起软键盘
   *
   * @param context
   */
  public static void hiddenSoftInputMethod(Activity context) {
    InputMethodManager imm =
        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(
        context.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }

  public static void showSoftInputMethod(Activity context) {
    context
        .getWindow()
        .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
  }

  /**
   * dialogFragment是否显示
   *
   * @param dialog
   * @return
   */
  public static boolean isDialogShow(DialogFragment dialog) {
    return null != dialog && dialog.isAdded();
  }

  /**
   * 返回版本名字 对应build.gradle中的versionName
   *
   * @param context
   * @return
   */
  public static String getVersionName(Context context) {
    String versionName = null;
    try {
      PackageManager packageManager = context.getPackageManager();
      PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
      versionName = packInfo.versionName;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return versionName;
  }

  /**
   * 返回版本号； 对应build.gradle中的versionCode
   *
   * @param context
   * @return
   */
  public static String getVersionCode(Context context) {
    String versionCode = null;
    try {
      PackageManager packageManager = context.getPackageManager();
      PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
      versionCode = String.valueOf(packInfo.versionCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return versionCode;
  }

  /**
   * 创建App文件夹；
   *
   * @param appName
   * @param application
   * @return
   */
  public static String createAPPFolder(String appName, Application application) {
    return createAPPFolder(appName, application, null);
  }

  /**
   * 创建App文件夹；
   *
   * @param appName
   * @param application
   * @param folderName
   * @return
   */
  public static String createAPPFolder(String appName, Application application, String folderName) {
    File root = Environment.getExternalStorageDirectory();
    File folder;
    /** 如果存在SD卡； */
    if (ExistSDCard() && root != null) {
      folder = new File(root, appName);
      if (!folder.exists()) {
        folder.mkdirs();
      }
    } else {
      /** 不存在SD卡，就放到缓存文件夹内； */
      root = application.getCacheDir();
      folder = new File(root, appName);
      if (!folder.exists()) {
        folder.mkdirs();
      }
    }
    if (folderName != null) {
      folder = new File(folder, folderName);
      if (!folder.exists()) {
        folder.mkdirs();
      }
    }
    return folder.getAbsolutePath();
  }
  /**
   * 去掉超链接中的下划线
   *
   * @param source
   * @return
   */
  public static Spanned fromHtmlNoUnderLine(String source) {
    if (!TextUtils.isEmpty(source) && !TextUtils.isEmpty(source.trim())) {
      Spanned spanned = Html.fromHtml(source);
      return fromHtmlNoUnderLine(spanned);
    } else {
      return new SpannableStringBuilder();
    }
  }

  public static Spanned fromHtmlNoUnderLine(Spanned spanned) {
    if (spanned != null) {
      if (spanned instanceof Spannable) {
        Spannable spannable = (Spannable) spanned;
        URLSpan[] urlSpans = spanned.getSpans(0, spanned.length(), URLSpan.class);
        if (urlSpans != null) {
          for (URLSpan urlSpan : urlSpans) {
            int start = spanned.getSpanStart(urlSpan);
            int end = spanned.getSpanEnd(urlSpan);
            /** 重新设置span之前需要移除原来的span,否则在linkify的onKeyDownOnLink中会找不到新设置的span的start和end */
            spannable.removeSpan(urlSpan);
            spannable.setSpan(
                new NoUnderLineUrlSpan(urlSpan.getURL()),
                start,
                end,
                Spanned.SPAN_INCLUSIVE_INCLUSIVE);
          }
        }
        return spannable;
      }
      return spanned;
    } else {
      return new SpannableStringBuilder();
    }
  }

  /** 实现没有下划线的URLSpan */
  public static class NoUnderLineUrlSpan extends URLSpan {
    public NoUnderLineUrlSpan(String url) {
      super(url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
      super.updateDrawState(ds);
      ds.setUnderlineText(false);
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {}

    protected NoUnderLineUrlSpan(Parcel in) {
      super(in);
    }

    public static final Creator<NoUnderLineUrlSpan> CREATOR =
        new Creator<NoUnderLineUrlSpan>() {
          @Override
          public NoUnderLineUrlSpan createFromParcel(Parcel source) {
            return new NoUnderLineUrlSpan(source);
          }

          @Override
          public NoUnderLineUrlSpan[] newArray(int size) {
            return new NoUnderLineUrlSpan[size];
          }
        };
  }

  /**
   * 获取assets
   *
   * @param context
   * @param fileName
   * @return
   */
  public static InputStream getFromAssets(Context context, String fileName) {
    try {
      InputStream in = context.getResources().getAssets().open(fileName);
      //获取文件的字节数
      int lenght = in.available();

      return in;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }
}
