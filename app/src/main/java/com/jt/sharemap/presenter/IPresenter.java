package com.jt.sharemap.presenter;


import com.jt.sharemap.view.IView;

import io.reactivex.disposables.Disposable;

/**
 */

public interface IPresenter<V extends IView> {

    //绑定View
    void attachView(V view);

    //解除View绑定
    void detachView();

    //检查View是否存在
    void checkAttachView();

    V getView();

    //添加指定的请求
    void addDisposable(Disposable disposable);
    //移除指定的请求
    void removeDisposable(Disposable disposable);
    //取消所有请求
    void removeAllDisposable();
}
