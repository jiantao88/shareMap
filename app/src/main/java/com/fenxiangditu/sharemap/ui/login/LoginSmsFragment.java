package com.fenxiangditu.sharemap.ui.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import sharemap.R;import com.fenxiangditu.sharemap.ui.base.BasePresenterFragment;
import com.fenxiangditu.sharemap.utils.ToastUtils;
import com.fenxiangditu.sharemap.utils.ValidateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

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

    private static final int MAX_COUNT_TIME = 60;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.text_input_phone)
    TextInputLayout mTextInputPhone;
    @BindView(R.id.et_sms)
    EditText mEtSms;
    @BindView(R.id.text_input_sms)
    TextInputLayout mTextInputSms;
    @BindView(R.id.btn_login)
    AppCompatButton mBtnLogin;
    @BindView(R.id.tv_send_sms_code)
    TextView mTvSendSmsCode;

    private String phone, smsCode;

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


    @OnClick({R.id.tv_send_sms_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_sms_code:
                phone = mEtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && ValidateUtil.isPhoneNumber(phone)) {
                    mPresenter.getSmSCode(phone);
                } else {
                    mEtPhone.setError(getString(R.string.error_phone));
                }
                break;
            case R.id.btn_login:
                smsCode = mEtSms.getText().toString().trim();
                phone = mEtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phone) && ValidateUtil.isPhoneNumber(phone)) {
                    if (!TextUtils.isEmpty(smsCode)){
                        mPresenter.login(phone,smsCode);
                    }else {
                        mEtSms.setError(getString(R.string.error_sms));
                    }
                }else {
                    mEtPhone.setError(getString(R.string.error_phone));
                }
                break;

            default:
        }
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

    @Override
    public void LoginSuccess() {
        ToastUtils.showToast(getContext(),"登录成功");
    }

}
