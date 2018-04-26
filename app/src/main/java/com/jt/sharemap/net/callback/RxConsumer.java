package com.jt.sharemap.net.callback;


import com.jt.sharemap.net.NetConfig;
import com.jt.sharemap.net.bean.BaseBean;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * author: 康栋普
 * date: 2018/3/7
 */

public abstract class RxConsumer<T> implements Consumer<BaseBean<T>> {

    @Override
    public void accept(@NonNull BaseBean<T> tBaseBean) throws Exception {
        if (tBaseBean.status == NetConfig.REQUEST_SUCCESS){
            onSuccess(tBaseBean.result);
        }else {
            onFail(tBaseBean.error);
        }
    }

    protected abstract void onFail(String errorMsg);

    protected abstract void onSuccess(T data);
}