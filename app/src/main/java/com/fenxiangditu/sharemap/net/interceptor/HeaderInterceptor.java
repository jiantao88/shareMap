package com.fenxiangditu.sharemap.net.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.fenxiangditu.sharemap.manager.UserInfoManger;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class HeaderInterceptor implements Interceptor {
    private static final String X_USER_ID = "X-User-Id";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Headers.Builder headers = originalRequest.headers().newBuilder();
        String userid = UserInfoManger.getUserId();
        if (!TextUtils.isEmpty(userid)) {
            headers.add(X_USER_ID, userid);
        }

        Request newRequest = chain.request().newBuilder()
                .headers(headers.build())
                .build();
        return chain.proceed(newRequest);
    }
}
