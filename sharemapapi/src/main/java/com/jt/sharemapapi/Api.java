package com.jt.sharemapapi;

import android.content.Context;

import com.jt.sharemapapi.utils.TraceLog;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : API公开对象
 *     version: 1.0
 * </pre>
 */

public class Api extends BaseApi {
    /**
     * 账号API
     */
    protected AccountApi mAccountApi;
    /**
     * 安全API
     */
    protected SecurityApi mSecurityApi;
    /**
     * 车机控制API
     */
    protected VehicleApi mVehicleApi;


    public Api(Context context) {
        super(context);
    }

    public Api(Context context, ApiConfig sdkConfig) {
        super(context, sdkConfig);
    }


    /**
     * create a server
     */
    @Override
    public void create() {
        super.create();
        TraceLog.d("----------- api-create -----------");

        mAccountApi = createApi(AccountApi.class, mSdkConfig.getDomain());
        mSecurityApi = createApi(SecurityApi.class, mSdkConfig.getDomain());
        mVehicleApi = createApi(VehicleApi.class, mSdkConfig.getDomain());
    }

    /**
     * create a service
     *
     * @param apiClass
     * @param <T>
     * @return
     */
    public <T> T createApi(Class<T> apiClass, String domain) {
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(domain)
            .client(mClient)
            //加入json解析
            .addConverterFactory(GsonConverterFactory.create())
            // 使用RxJava作为回调适配器；
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
        return retrofit.create(apiClass);
    }

    public void addNetworkInterceptor(Interceptor interceptor) {
        mClientBuilder.addNetworkInterceptor(interceptor);
    }


    /**
     * 获取账号相关API
     *
     * @return
     */
    public <T> T getAccountApi() {
        return (T) mAccountApi;
    }

    /**
     * 获取安全相关API
     *
     * @return
     */
    public <T> T getSecurityApi() {
        return (T) mSecurityApi;
    }


    /**
     * 获取车机控制相关API
     *
     * @return
     */
    public <T> T getVehicleApi() {
        return (T) mVehicleApi;
    }

}
