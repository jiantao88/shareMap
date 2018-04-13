package com.jt.sharemap.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.util.TypedValue;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chehejia.ampmind.logger.AMLog;

import java.lang.reflect.Field;

/**
 * Created by zhangjiantao on 2017/6/26.
 */
public class ViewUtils {

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
     * 设置 tablayout 指示器宽度
     *
     * @param tabs
     * @param leftDip
     * @param rightDip
     */
    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left =
                (int)
                        TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right =
                (int)
                        TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
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
            AMLog.d("ANDROID_LAB", "memSize =" + memSize + "B =" + formatFileSize(memSize));
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
