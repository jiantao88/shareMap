package com.jt.sharemap.ui.login;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BasePresenterFragment;
import com.jt.sharemap.utils.ValidateUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    Unbinder unbinder;

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
//        mEtPhone = view.findViewById(R.id.et_phone);
//        if (!TextUtils.isEmpty(phone) && ValidateUtil.isPhoneNumber(phone)) {
//
//            Observable<Long> mObservableCountTime = RxView.clicks(mTvSendSmsCode)
//                    //防止重复点击
//                    .throttleFirst(MAX_COUNT_TIME, TimeUnit.SECONDS)
//                    //将点击事件转换成倒计时事件
//                    .flatMap(new Function<Object, ObservableSource<Long>>() {
//                        @Override
//                        public ObservableSource<Long> apply(Object o) throws Exception {
//                            //更新发送按钮的状态并初始化显现倒计时文字
//
////                            RxTextView.text(mTvSendSmsCode).accept("剩余 " + MAX_COUNT_TIME + " 秒");
//
//                            //在实际操作中可以在此发送获取网络的请求
//                            mPresenter.getSmSCode(phone);
//                            //返回 N 秒内的倒计时观察者对象。
//                            return Observable.interval(1, TimeUnit.SECONDS, Schedulers.io()).take(MAX_COUNT_TIME);
//                        }
//                    });
//
//        } else {
//            mEtPhone.setError(getString(R.string.error_phone));
//        }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.text_input_phone)
    public void onViewClicked() {
    }
}
