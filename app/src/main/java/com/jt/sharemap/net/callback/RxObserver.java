package com.jt.sharemap.net.callback;


import com.jt.sharemap.net.NetConfig;
import com.jt.sharemap.net.bean.BaseBean;
import com.jt.sharemap.presenter.BasePresenter;

/**
 * 通用Observer回调
 */

public abstract class RxObserver<T> extends RxBaseObserver<T> {
    public RxObserver(BasePresenter mPresenter) {
        super(mPresenter);
    }

    @Override
    public void onNext(BaseBean<T> mBaseBean) {

        //请求成功
        if (mBaseBean.status == NetConfig.REQUEST_SUCCESS) {
            onSuccess(mBaseBean.result);
        } else {
            //失败
            onFail(mBaseBean.status, mBaseBean.error);
        }
    }

    protected abstract void onSuccess(T data);

    protected abstract void onFail(int errorCode, String errorMsg);

}
