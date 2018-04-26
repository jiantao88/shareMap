package com.jt.sharemap.ui.login;

import com.jt.sharemap.net.bean.SmsCodeBean;
import com.jt.sharemap.net.callback.RxObserver;
import com.jt.sharemap.presenter.BasePresenter;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginSmsPresenter extends BasePresenter<LoginSmsContract.ILoginSmsView> implements LoginSmsContract.ILoginSmsPresenter{
    private LoginModel mLoginModel;
    private LoginSmsContract.ILoginSmsView mILoginSmsView;

    public LoginSmsPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String phone,String smsCode) {

    }

    @Override
    public void getSmSCode(String phone) {
        mILoginSmsView = getView();
        mLoginModel.getSmsCode(phone, new RxObserver<SmsCodeBean>(this) {
            @Override
            protected void onSuccess(SmsCodeBean data) {
                mILoginSmsView.getSmeCodeSuccess();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mILoginSmsView.showFail(errorMsg);
            }
        });
    }
}
