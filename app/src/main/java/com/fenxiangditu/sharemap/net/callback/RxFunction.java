package com.fenxiangditu.sharemap.net.callback;


import com.fenxiangditu.sharemap.net.NetConfig;
import com.fenxiangditu.sharemap.net.bean.BaseBean;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 用来处理嵌套请求的操作
 * author: 康栋普
 * date: 2018/3/7
 */

public abstract class RxFunction<T, R> implements Function<BaseBean<T>, Observable<BaseBean<R>>> {

    @Override
    public Observable<BaseBean<R>> apply(BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.status == NetConfig.REQUEST_SUCCESS){
            return doOnNextRequest();
        }
        return null;
    }

    protected abstract Observable<BaseBean<R>> doOnNextRequest();

}
