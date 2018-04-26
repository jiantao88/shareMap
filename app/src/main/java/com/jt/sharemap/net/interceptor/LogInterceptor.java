package com.jt.sharemap.net.interceptor;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LogInterceptor implements Interceptor {
    private static final String TAG = LogInterceptor.class.getSimpleName();


    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Logger.d( "请求开始 : requestUrl : %s ,",request.url().toString());//输出请求前整个url
        long start = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long end = System.currentTimeMillis();
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.d("请求结束 : 耗时: %s ms,mediaType : %s ", (end - start), mediaType);//输出请求前整个url
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
