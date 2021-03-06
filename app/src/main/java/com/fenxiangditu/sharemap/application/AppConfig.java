package com.fenxiangditu.sharemap.application;

import android.content.Context;

import com.facebook.stetho.Stetho;
import com.fenxiangditu.sharemap.common.UrlConstainer;
import com.fenxiangditu.sharemap.net.RxRetrofit;
import com.fenxiangditu.sharemap.utils.PreUtils;


/**
 */

public class AppConfig {

    static void init(Context context) {
        //初始化网络框架
        RxRetrofit.getInstance().initRxRetrofit(context, UrlConstainer.BASE_URL);
        //初始化缓存
        PreUtils.init(context);
        //网络调试工具
        Stetho.initializeWithDefaults(context);
    }

}
