package com.jt.sharemap.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jt.sharemap.presenter.BasePresenter;
import com.jt.sharemap.ui.IView;


/**
 */

public abstract class BasePresenterFragment<P extends BasePresenter<V>, V extends IView> extends BaseFragment implements IView{

    protected P mPresenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        //关联View
        attachView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除关联
        detachView();
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.removeAllDisposable();
            mPresenter = null;
        }
    }

    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    protected abstract P createPresenter();

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showFail(String msg) {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }
}
