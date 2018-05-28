package com.jt.sharemap.manager;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jt.sharemap.R;
import com.jt.sharemap.application.AppContext;
import com.jt.sharemap.common.Const;

/**
 * date: 2018/2/26
 */

public class GlideLoaderManager {
    private static RequestOptions nomal_image_options = RequestOptions.placeholderOf(R.drawable.iv_img_default)
            .centerCrop();
    private static RequestOptions head_options = RequestOptions.placeholderOf(R.mipmap.ic_launcher);
    private static RequestOptions round_options = RequestOptions.placeholderOf(R.mipmap.ic_launcher).circleCrop();

    public static void loadImage(String url, final ImageView imageView, int type) {

        switch (type) {
            case Const.IMAGE_LOADER.HEAD_IMG:
                Glide.with(AppContext.getContext()).load(url).apply(head_options).into(imageView);
                break;
            case Const.IMAGE_LOADER.NOMAL_IMG:
                Glide.with(AppContext.getContext()).load(url).apply(nomal_image_options).into(imageView);
                break;
            case Const.IMAGE_LOADER.ROUND_IMG:
                Glide.with(AppContext.getContext()).load(url).apply(round_options).into(imageView);
                break;
            default:
                break;
        }

    }

}
