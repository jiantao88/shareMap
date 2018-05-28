package com.fenxiangditu.sharemap.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.fenxiangditu.sharemap.common.Const;
import com.fenxiangditu.sharemap.event.RxEvent;

import java.util.Objects;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import sharemap.R;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/03/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Toolbar mToolbar;
    protected FrameLayout mContainerLayout;
    private ProgressDialog loadingDialog = null;
    private RxEvent mRxEvent;
    private PublishSubject mSubject;
    private DisposableObserver mDisposableObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove(Const.FRAGMENT_TAG);
        }
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            getIntent(intent);
        }
        setContentView(R.layout.content_main);
        mToolbar = findViewById(R.id.toolbar);
        mContainerLayout = findViewById(R.id.base_container);
        boolean isToolbar = initToolbar();
        if (isToolbar) {
            setSupportActionBar(mToolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onNavigationClick();
                }
            });
        } else {
            mToolbar.setVisibility(View.GONE);
        }
        //初始化Content
        initContent(getLayoutId());
        mRxEvent = RxEvent.getInstance();
        mSubject = mRxEvent.registerEvent(registerEvent());
        mDisposableObserver = new ReceiveEvent();
        mSubject.subscribe(mDisposableObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxEvent.getInstance().unRegisterEvent(registerEvent(), mSubject, mDisposableObserver);
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

    protected void onNavigationClick() {
        finish();
    }

    protected abstract int getLayoutId();

    protected abstract boolean initToolbar();

    protected abstract void getIntent(Intent intent);

    protected abstract void initViews();


    private void initContent(int layoutId) {
        if (layoutId != 0) {
            View contentView = LayoutInflater.from(this).inflate(layoutId, mContainerLayout, false);
            mContainerLayout.addView(contentView);
            initViews();
        }
    }
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
            loadingDialog = new ProgressDialog(this);
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
