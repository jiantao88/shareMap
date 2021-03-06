package com.fenxiangditu.sharemap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.fenxiangditu.sharemap.manager.UserInfoManager;
import com.fenxiangditu.sharemap.net.bean.LoginBean;
import com.fenxiangditu.sharemap.ui.base.BaseActivity;
import com.fenxiangditu.sharemap.ui.home.MainActivity;

import java.lang.ref.WeakReference;

import sharemap.R;


/**
 * 启动页
 */

public class LauncherActivity extends BaseActivity {
    private LoginBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new DelayRunnable(this), 2000);
    }

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    //自动登录
    private void autoLogin() {
        if (UserInfoManager.isLogin()) {
            //自动登录
            userBean = UserInfoManager.getUserInfo();
        }
        startToActivity();
    }


    private static class DelayRunnable implements Runnable {
        private WeakReference<LauncherActivity> mWeakReference;

        DelayRunnable(LauncherActivity instance) {
            mWeakReference = new WeakReference<>(instance);
        }

        @Override
        public void run() {
            LauncherActivity instance = mWeakReference.get();
            if (instance == null){
                return;
            }
            instance.autoLogin();
        }
    }

    //进入首页
    private void startToActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected boolean initToolbar() {
        return false;
    }

    @Override
    protected void getIntent(Intent intent) {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
