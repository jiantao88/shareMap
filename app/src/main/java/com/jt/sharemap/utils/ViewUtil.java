package com.jt.sharemap.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

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
}
