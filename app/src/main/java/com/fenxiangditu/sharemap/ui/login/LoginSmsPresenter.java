package com.fenxiangditu.sharemap.ui.login;

import com.fenxiangditu.sharemap.manager.UserInfoManger;
import com.fenxiangditu.sharemap.net.bean.LoginBean;
import com.fenxiangditu.sharemap.net.bean.SmsCodeBean;
import com.fenxiangditu.sharemap.net.callback.RxObserver;
import com.fenxiangditu.sharemap.presenter.BasePresenter;

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
        mILoginSmsView = getView();
        mILoginSmsView.showLoading("正在登录");
        mLoginModel.login_sms(phone, smsCode, new RxObserver<LoginBean>(this) {
            @Override
            protected void onSuccess(LoginBean data) {
                UserInfoManger.saveUserInfo(data);
                UserInfoManger.saveIsLogin(true);
                mILoginSmsView.hideLoading();
                mILoginSmsView.LoginSuccess();

            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mILoginSmsView.hideLoading();
                mILoginSmsView.showFail(errorMsg);
            }
        });
    }

    @Override
    public void getSmSCode(String phone) {
        mILoginSmsView = getView();
        mLoginModel.getSmsCode(phone, new RxObserver<SmsCodeBean>(this) {
            @Override
            protected void onSuccess(SmsCodeBean data) {
                getView().getSmeCodeSuccess();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mILoginSmsView.showFail(errorMsg);
            }
        });
    }
}
