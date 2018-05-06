package com.jt.sharemap.net;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.jt.sharemap.net.api.ApiServer;
import com.jt.sharemap.net.interceptor.HeaderInterceptor;
import com.jt.sharemap.net.interceptor.LogInterceptor;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络工具类
 */
public class RxRetrofit {
    private Retrofit retrofit;
    private static ApiServer apiServer;
    private static Gson gson = new Gson();

    private static final class Holder {
        private static final RxRetrofit INSTANCE = new RxRetrofit();
    }

    public void initRxRetrofit(final Context context, String baseUrl) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //链接超时
                .connectTimeout(10, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(10, TimeUnit.SECONDS)
                //缓存
                .cache(new Cache(Objects.requireNonNull(context.getExternalFilesDir("http_cache")), 10 << 20))
                .addInterceptor(new LogInterceptor())
                .addNetworkInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiServer = retrofit.create(ApiServer.class);
    }

    public static RxRetrofit getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 获取ApiServer对象
     *
     * @return apiServer
     */
    public static ApiServer Api() {
        if (apiServer == null){
            throw new IllegalStateException("You must invoke init method first in Application");
        }
        return apiServer;
    }


}
