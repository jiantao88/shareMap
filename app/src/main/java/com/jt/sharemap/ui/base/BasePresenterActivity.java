package com.jt.sharemap.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jt.sharemap.presenter.BasePresenter;
import com.jt.sharemap.ui.IView;
import com.jt.sharemap.utils.ToastUtils;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : a_tao123@163.com
 *     time   : 2018/04/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BasePresenterActivity<P extends BasePresenter<V>, V extends IView> extends BaseActivity implements IView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        attachView();
    }

    /**
     * 创建presenter
     *
     * @return
     */
    protected abstract P createPresenter();


    private void attachView() {
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    protected void removeAllDisposable() {
        if (mPresenter != null) {
            mPresenter.removeAllDisposable();
        }
    }

    @Override
    protected void onDestroy() {
        detachView();
        removeAllDisposable();
        super.onDestroy();
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

    @Override
    public void showFail(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }


    @Override
    protected boolean initToolbar() {
        return false;
    }

    @Override
    protected void getIntent(Intent intent) {
    }

}
