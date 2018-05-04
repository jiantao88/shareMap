package com.jt.sharemap.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BasePresenterFragment;
import com.jt.sharemap.ui.register.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : a_tao123@163.com
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginAcountFragment extends BasePresenterFragment<LoginAccountPresenter, LoginAcountContract.ILoginAcountView> implements LoginAcountContract.ILoginAcountView {


    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.text_input_username)
    TextInputLayout mTextInputUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputPassword;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.btn_register)
    AppCompatButton mBtnRegister;
    private String username, password;


    @Override
    protected LoginAccountPresenter createPresenter() {
        return new LoginAccountPresenter();
    }


    @Override
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected void initViews() {
    }

    @Override
    public void showLoading(String msg) {
        super.showLoading(msg);
        showLoadingDialog();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_login_account;
    }


    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (verifyAccount()) {
                    mPresenter.login(username,password);
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                break;

            default:
        }
    }

    @Override
    public void LoginSuccess() {
        getActivity().finish();
    }

    private boolean verifyAccount() {
        username = mEtUsername.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            mEtUsername.setError("请填写用户名");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            mEtPassword.setError("请填写密码");
            return false;
        }
        return true;
    }
}
