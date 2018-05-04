package com.jt.sharemap.ui.register;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BasePresenterActivity;
import com.jt.sharemap.utils.ValidateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RegisterActivity extends BasePresenterActivity<RegisterPresenter, RegisterContract.IRegisterIvew> implements RegisterContract.IRegisterIvew {
    @BindView(R.id.et_register_phone)
    EditText mEtRegisterPhone;
    @BindView(R.id.text_input_username)
    TextInputLayout mTextInputUsername;
    @BindView(R.id.et_sms)
    EditText mEtSms;
    @BindView(R.id.text_input_sms)
    TextInputLayout mTextInputSms;
    @BindView(R.id.tv_send_sms_code)
    TextView mTvSendSmsCode;
    @BindView(R.id.et_register_password)
    EditText mEtRegisterPassword;
    @BindView(R.id.text_input_password)
    TextInputLayout mTextInputPassword;
    @BindView(R.id.et_register_repassword)
    EditText mEtRegisterRepassword;
    @BindView(R.id.text_input_repassword)
    TextInputLayout mTextInputRepassword;
    @BindView(R.id.btn_register)
    AppCompatButton mBtnRegister;

    private String phone, sms_code, password, repassword;
    private static final int MAX_COUNT_TIME = 60;

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViews() {

    }

    @OnClick({R.id.tv_send_sms_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_sms_code:
                phone = mEtRegisterPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    mEtRegisterPhone.setError(getString(R.string.empty_phone));
                } else if (!ValidateUtil.isPhoneNumber(phone)) {
                    mEtRegisterPhone.setError(getString(R.string.error_phone));
                } else {
                    mPresenter.getSmSCode(phone);
                }
                break;
            case R.id.btn_register:
                if (verifyAccount()){
                    mPresenter.register(phone, sms_code, password);
                }
                break;
            default:
        }
    }

    private boolean verifyAccount() {
        phone = mEtRegisterPhone.getText().toString().trim();
        sms_code = mEtSms.getText().toString().trim();
        password = mEtRegisterPassword.getText().toString().trim();
        repassword = mEtRegisterRepassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            mEtRegisterPhone.setError(getString(R.string.empty_phone));
            return false;
        } else if (!ValidateUtil.isPhoneNumber(phone)) {
            mEtRegisterPhone.setError(getString(R.string.error_phone));
            return false;
        } else if (TextUtils.isEmpty(sms_code)) {
            mEtSms.setError(getString(R.string.empty_sms_code));
            return false;
        } else if (TextUtils.isEmpty(password)) {
            mEtRegisterPassword.setError(getString(R.string.empty_password));
            return false;
        } else if (TextUtils.isEmpty(repassword)) {
            mEtRegisterRepassword.setError(getString(R.string.empty_password));
            return false;
        } else if (!password.equals(repassword)) {
            mEtRegisterPassword.setError(getString(R.string.error_repassword));
            return false;
        }
        return true;
    }

    @Override
    public void getSmeCodeSuccess() {
        Observable.interval(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .take(MAX_COUNT_TIME)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        RxTextView.text(mTvSendSmsCode).accept((MAX_COUNT_TIME - aLong) + "s");
                    }
                });
    }
}
