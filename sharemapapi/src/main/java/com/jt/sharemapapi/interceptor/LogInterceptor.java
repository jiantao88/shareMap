package com.jt.sharemapapi.interceptor;


import com.jt.sharemapapi.utils.TraceLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : 日志监听
 *     version: 1.0
 * </pre>
 */

public  class LogInterceptor implements Interceptor {
    private static final String TAG = CheckSignInterceptor.class.getSimpleName();


    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //输出请求前整个url
        TraceLog.e( "okhttp3:" + request.toString());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();

        //输出返回信息
        TraceLog.e( "response body:" + content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
