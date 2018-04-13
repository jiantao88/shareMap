package com.jt.sharemapapi.listener;


import com.jt.sharemapapi.ApiConstants;
import com.jt.sharemapapi.bean.response.BaseResponse;
import com.jt.sharemapapi.utils.TraceLog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : api返回数据格式化
 *     version: 1.0
 * </pre>
 */

public abstract class BaseRequestListener<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = BaseRequestListener.class.getSimpleName();
    protected BaseResponse<T> response;

    @Override
    public void onError(Throwable e) {
        // TODO Throwable 进行统一处理
        TraceLog.e(e.getMessage() != null ? e.getMessage() : "");

        BaseResponse baseResponse = new BaseResponse();

        if (e instanceof HttpException) {
            baseResponse.setCode(((HttpException) e).code());
            baseResponse.setMsg(e.getMessage());
        } else {
            baseResponse.setCode(ApiConstants.HTTP_INVALID_REQUEST);
            baseResponse.setMsg(e.getMessage());
        }

        onFailed(baseResponse);
    }

    @Override
    public void onSubscribe(Disposable d) {
        TraceLog.i("=== onSubscribe ===");
    }

    @Override
    public void onNext(BaseResponse<T> response) {
        TraceLog.i("=== onNext ===");

        this.response = response;
    }

    @Override
    public void onComplete() {
        TraceLog.i("=== onCompleted ===");
        if (response == null) {

            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setCode(ApiConstants.HTTP_INVALID_REQUEST);
            baseResponse.setMsg("Response is Null");
            onFailed(response);
        } else if (response.getCode() != ApiConstants.SUCCESS) {
            onFailed(response);
        } else {
            onSucceed(response);
        }
    }

    /**
     * 成功
     *
     * @param response
     */
    public abstract void onSucceed(BaseResponse<T> response);

    /**
     * 失败
     *
     * @param response
     */
    public abstract void onFailed(BaseResponse<T> response);
}
