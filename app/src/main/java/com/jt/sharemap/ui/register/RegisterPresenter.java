package com.jt.sharemap.ui.register;

import com.jt.sharemap.net.bean.SmsCodeBean;
import com.jt.sharemap.net.callback.RxObserver;
import com.jt.sharemap.presenter.BasePresenter;
import com.jt.sharemap.ui.login.LoginModel;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterIvew> implements RegisterContract.IRegisterPresenter {
    private LoginModel mLoginModel;

    public RegisterPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void getSmSCode(String phone) {
        mLoginModel.getSmsCode(phone, new RxObserver<SmsCodeBean>(this) {
            @Override
            protected void onSuccess(SmsCodeBean data) {
                getView().getSmeCodeSuccess();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                getView().showFail(errorMsg);
            }
        });
    }

    @Override
    public void register(String phone, String sms_code, String password) {
        mLoginModel.register(phone, sms_code, password, new RxObserver<String>(this) {
            @Override
            protected void onSuccess(String data) {

            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {

            }
        });
    }
}
