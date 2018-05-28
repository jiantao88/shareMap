package com.fenxiangditu.sharemap.model;

import com.fenxiangditu.sharemap.net.RxRetrofit;
import com.fenxiangditu.sharemap.net.api.ApiServer;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class BaseModel implements IModel{

    @Override
    public ApiServer doRxRequest() {
        return RxRetrofit.Api();
    }
}
