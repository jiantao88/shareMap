package com.jt.sharemap.application;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;


/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : zhangjiantao@chehejia.com
 *     time   : 2018/03/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLog();
        //初始化内存泄漏检测工具
        if (LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);

    }

    private void initLog() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

}
