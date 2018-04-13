package com.jt.sharemapapi;

import android.content.Context;

import com.chehejia.ampgo.api.bean.ApiHttpHeaders;
import com.chehejia.ampgo.api.interceptor.CheckSignInterceptor;
import com.chehejia.ampgo.api.interceptor.LogInterceptor;
import com.chehejia.ampgo.api.interceptor.PackageSignInterceptor;
import com.chehejia.ampgo.api.utils.ApiUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : API 基类
 *     version: 1.0
 * </pre>
 */

 abstract class BaseApi {
    private static String TAG = "BaseApi";
    private final static long DEFAULT_TIMEOUT = 10;

    volatile static Context mApplicationContext;

    protected ApiConfig mSdkConfig;
    protected OkHttpClient mClient;

    protected PackageSignInterceptor mPackageSignInterceptor = new PackageSignInterceptor();
    protected CheckSignInterceptor mCheckSignInterceptor = new CheckSignInterceptor();

    /**
     * 初始化OkHttp3的客户端
     */
    protected OkHttpClient.Builder mClientBuilder;


    public BaseApi(Context c)
    {
        BaseApi.mApplicationContext = c;

        this.mSdkConfig = new ApiConfig.Builder().build();

        this.mClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(new LogInterceptor())
            .addNetworkInterceptor(mPackageSignInterceptor)
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addNetworkInterceptor(mCheckSignInterceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }


    public BaseApi(Context context, ApiConfig mSdkConfig){
        BaseApi.mApplicationContext = context;
        this.mSdkConfig = mSdkConfig;

        this.mClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(new LogInterceptor())
            .addNetworkInterceptor(mPackageSignInterceptor)
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addNetworkInterceptor(mCheckSignInterceptor)
            .retryOnConnectionFailure(true)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
    }

    public void setX509TrustManager(X509TrustManager x509TrustManager){

        SSLSocketFactory sslSocketFactory = ApiUtils.getSSLSocketFactory(x509TrustManager);
        if (x509TrustManager != null && sslSocketFactory != null) {
            mClientBuilder.sslSocketFactory(sslSocketFactory, x509TrustManager);
        }
    }


    public void create(){
        ApiHttpHeaders httpHeaders = new ApiHttpHeaders();
        httpHeaders.setHeaderEnv("test");
        httpHeaders.setHeaderVersion("0.1-20160523142212");
        httpHeaders.setHeaderKey(ApiConstants.keySuite.getKeyId());
        httpHeaders.setContentLanguage("zh-CN");
        httpHeaders.setHeaderType("application/json");
        httpHeaders.setHeaderToken("");

        mPackageSignInterceptor.setHttpHeaders(httpHeaders);

        if(mClient == null){
            mClient = mClientBuilder.build();
        }
    }


    public void setHttpHeaders(ApiHttpHeaders httpHeaders){
        this.mPackageSignInterceptor.setHttpHeaders(httpHeaders);
    }

    public ApiHttpHeaders getHttpHeaders(){
        return this.mPackageSignInterceptor.getHttpHeaders();
    }
}
