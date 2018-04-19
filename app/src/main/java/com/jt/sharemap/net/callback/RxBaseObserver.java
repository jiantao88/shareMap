package com.jt.sharemap.net.callback;

import android.content.Context;

import com.google.gson.JsonParseException;
import com.jt.sharemap.R;
import com.jt.sharemap.application.AppContext;
import com.jt.sharemap.net.NetConfig;
import com.jt.sharemap.net.bean.BaseBean;
import com.jt.sharemap.presenter.BasePresenter;
import com.jt.sharemap.utils.ToastUtils;
import com.jt.sharemap.ui.IView;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;

/**
 * Observer基类
 */

public abstract class RxBaseObserver<T> extends DisposableObserver<BaseBean<T>> {

    protected IView view;

    RxBaseObserver(BasePresenter mPresenter) {
        this.view = mPresenter.getView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        showLoading();
    }

    public void showLoading() {
        view.showLoading("");
    }

    @Override
    public void onError(Throwable e) {
        /**
         * 处理异常
         */
        hideLoading();
        dealException(AppContext.getContext(), e);
    }

    @Override
    public void onComplete() {
        hideLoading();
    }

    private void hideLoading() {
        if (null != view) {
            this.view.hideLoading();
        }
    }

    /**
     * 处理异常错误
     *
     * @param t
     */
    void dealException(Context context, Throwable t) {

        if (t instanceof ConnectException || t instanceof UnknownHostException) {
            //连接错误
            onException(NetConfig.CONNECT_ERROR, context);
        } else if (t instanceof InterruptedException) {
            //连接超时
            onException(NetConfig.CONNECT_TIMEOUT, context);
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {
            //解析错误
            onException(NetConfig.PARSE_ERROR, context);
        } else if (t instanceof SocketTimeoutException) {
            //请求超时
            onException(NetConfig.REQUEST_TIMEOUT, context);
        } else if (t instanceof UnknownError) {
            //未知错误
            onException(NetConfig.UNKNOWN_ERROR, context);
        } else {
            //未知错误
            onException(NetConfig.UNKNOWN_ERROR, context);
        }
    }


    void onException(int errorCode, Context context) {
        switch (errorCode) {
            case NetConfig.CONNECT_ERROR:
                ToastUtils.showToast(context, R.string.connect_error);
                break;
            case NetConfig.CONNECT_TIMEOUT:
                ToastUtils.showToast(context, R.string.connect_timeout);
                break;
            case NetConfig.PARSE_ERROR:
                ToastUtils.showToast(context, R.string.parse_error);
                break;
            case NetConfig.REQUEST_TIMEOUT:
                ToastUtils.showToast(context, R.string.request_timeout);
                break;
            case NetConfig.UNKNOWN_ERROR:
                ToastUtils.showToast(context, R.string.unknown_error);
                break;

            default:
        }
    }
}
