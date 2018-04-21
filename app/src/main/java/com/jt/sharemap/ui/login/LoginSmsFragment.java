package com.jt.sharemap.ui.login;

import android.os.Bundle;
import android.view.View;

import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BasePresenterFragment;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : a_tao123@163.com
 *     time   : 2018/04/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginSmsFragment extends BasePresenterFragment<LoginSmsPresenter, LoginSmsContract.ILoginSmsView> implements LoginSmsContract.ILoginSmsView {

    @Override
    protected LoginSmsPresenter createPresenter() {
        return new LoginSmsPresenter();
    }

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login_sms;
    }
}
