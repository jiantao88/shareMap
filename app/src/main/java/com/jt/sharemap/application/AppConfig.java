package com.jt.sharemap.application;

import android.content.Context;

import com.jt.sharemap.common.UrlConstainer;
import com.jt.sharemap.net.RxRetrofit;
import com.jt.sharemap.utils.PreUtils;


/**
 */

public class AppConfig {

    static void init(Context context){
        //初始化网络框架
        RxRetrofit.getInstance().initRxRetrofit(context, UrlConstainer.baseUrl);
        //初始化缓存
        PreUtils.init(context);
    }

}