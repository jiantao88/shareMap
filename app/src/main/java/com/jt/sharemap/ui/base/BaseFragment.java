package com.jt.sharemap.ui.base;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jt.sharemap.event.RxEvent;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : a_tao123@163.com
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseFragment extends Fragment {

    private PublishSubject mSubject;
    private RxEvent mRxEvent;
    private DisposableObserver mDisposableObserver;
    private Unbinder mUnbinder;
    private ProgressDialog loadingDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            getBundle(bundle);
        }
        mRxEvent = RxEvent.getInstance();
        mDisposableObserver = new ReceiveEvent();
        //注册事件
        mSubject = mRxEvent.registerEvent(registerEvent());
        mSubject.subscribe(mDisposableObserver);
    }

    private class ReceiveEvent extends DisposableObserver {
        @Override
        public void onNext(Object o) {
            receiveEvent(o);
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    }

    protected abstract void receiveEvent(Object object);

    protected abstract String registerEvent();


    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销事件
        RxEvent.getInstance().unRegisterEvent(registerEvent(), mSubject, mDisposableObserver);
        mUnbinder.unbind();
    }

    protected abstract void getBundle(Bundle bundle);


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            view = inflater.inflate(getLayoutId(), container, false);
            mUnbinder = ButterKnife.bind(view);
            initViews(view);
        }
        return view;
    }

    protected abstract void initViews(View view);

    protected abstract int getLayoutId();

    /**
     * 显示带消息的进度框
     *
     * @param title 提示
     */
    protected void showLoadingDialog(String title) {
        createLoadingDialog();
        loadingDialog.setMessage(title);
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    /**
     * 显示进度框
     */
    protected void showLoadingDialog() {
        createLoadingDialog();
        if (!loadingDialog.isShowing()){
            loadingDialog.show();
        }
    }

    /**
     * 创建LodingDialog
     */
    private void createLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getActivity());
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
    }

    /**
     * 隐藏进度框
     */
    protected void hideLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
