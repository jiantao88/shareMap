package com.fenxiangditu.sharemap.utils;

import com.orhanobut.logger.Logger;

import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
class SimpleThrowableAction implements Consumer<Throwable> {
    private String mTag;

    public SimpleThrowableAction(String tag) {
        mTag = tag;
    }

    @Override
    public void accept(Throwable throwable) throws Exception {
        Logger.e(mTag, "订阅发生错误！", throwable);
    }
}
