package com.jt.sharemap.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : zhangjiantao@chehejia.com
 *     time   : 2018/04/18
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RxEvent {
    private static RxEvent mInstance;
    private Map<String, List<PublishSubject>> mSubjectMaps = new HashMap<>();

    public static RxEvent getInstance() {
        if (mInstance == null) {
            synchronized (RxEvent.class) {
                if (mInstance == null) {
                    mInstance = new RxEvent();
                }
            }
        }
        return mInstance;
    }

    /**
     * 注册事件
     *
     * @param action
     * @return
     */
    public PublishSubject registerEvent(String action) {
        List<PublishSubject> mSubjectList = mSubjectMaps.get(action);
        if (mSubjectList == null) {
            mSubjectList = new ArrayList<>();
        }
        mSubjectMaps.put(action, mSubjectList);
        PublishSubject subject = PublishSubject.create();
        mSubjectList.add(subject);
        return subject;
    }

    /**
     * 发送事件
     *
     * @param action
     * @param object
     */
    public void postEvent(String action, Object object) {
        List<PublishSubject> mSubjectList = mSubjectMaps.get(action);
        if (mSubjectList != null && !mSubjectList.isEmpty()) {
            for (PublishSubject mSubject : mSubjectList) {
                mSubject.onNext(object);
            }
        }
    }

    /**
     * 注销事件
     * @param action
     * @param mSubject
     * @param mDisposable
     */
    public void unRegisterEvent(String action, PublishSubject mSubject, DisposableObserver mDisposable) {
        List<PublishSubject> mSubjectList = mSubjectMaps.get(action);
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        if (mSubjectList != null) {
            mSubjectList.remove(mSubject);
        }
        assert mSubjectList != null;
        if (mSubjectList.isEmpty()) {
            mSubjectMaps.remove(action);
        }
    }
}
