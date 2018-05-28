package com.fenxiangditu.sharemap.utils;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;

/**
 * <pre>
 *     time   : 2017/12/25
 *     desc   : 处理View相关工具类 类名称：ViewUtil
 *     version: 1.0
 * </pre>
 */
public final class ViewUtil {
    private ViewUtil() {
    }

    /**
     * 设置当前是否需要隐藏
     *
     * @param view
     * @param visibility
     */
    public static void setViewVisibility(View view, int visibility) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    /**
     * 释放View上的相关资源
     *
     * @param view
     */
    public static void releaseViewResource(View view) {
        if (view == null) {
            return;
        }
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.setCallback(null);
        }
        SdkUtil.setBackgroundDrawable(view, null);
    }

    /**
     * 释放ImageView上的相关资源
     *
     * @param imageView
     */
    public static void releaseImageViewResource(ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Drawable drawable = imageView.getDrawable();
        if (drawable != null) {
            drawable.setCallback(null);
        }
        imageView.setImageDrawable(null);
    }



    public static boolean isVisible(View view) {
        if (view != null && view.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 增加控件的可点击范围，最大范围只能是父布局所包含的的区域
     */
    public static void addDefaultScreenArea(
            final View view,
            final int top,
            final int bottom,
            final int left,
            final int right) { // 增大checkBox的可点击范围
        final View parent = (View) view.getParent();
        parent.post(
                new Runnable() {
                    public void run() {

                        Rect bounds = new Rect();
                        view.setEnabled(true);
                        view.getHitRect(bounds);

                        bounds.top -= top;
                        bounds.bottom += bottom;
                        bounds.left -= left;
                        bounds.right += right;

                        TouchDelegate touchDelegate = new TouchDelegate(bounds, view);

                        if (View.class.isInstance(view.getParent())) {
                            ((View) view.getParent()).setTouchDelegate(touchDelegate);
                        }
                    }
                });
    }

    public static void setMarginTop(View v, int t) {

        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(p.leftMargin, t, p.rightMargin, p.bottomMargin);
            v.requestLayout();
        }
    }

    public static void setMarginBottom(View v, int t) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(p.leftMargin, p.topMargin, p.rightMargin, t);
            v.requestLayout();
        }
    }


    /**
     * 获取图片占用内存大小
     *
     * @param imageView
     */
    public static void getImageBitmapMemSize(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitDrawable = (BitmapDrawable) drawable;
            Bitmap bit = bitDrawable.getBitmap();
            int rowBytes = bit.getRowBytes();
            int height = bit.getHeight();
            long memSize = rowBytes * height;
            Logger.d("ANDROID_LAB", "memSize =" + memSize + "B =" + formatFileSize(memSize));
        }
    }

    private static String formatFileSize(long memSize) {
        return memSize / (1024 * 1024) + "m";
    }

    public static void showView(View view) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideView(View view) {
        if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    private static long lastClickTime;
    private final static int SPACE_TIME = 500;

    public static void initLastClickTime() {
        lastClickTime = 0;
    }

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime >
                SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }
}
