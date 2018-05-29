package com.fenxiangditu.sharemap.ui.login;

import com.fenxiangditu.sharemap.manager.UserInfoManager;
import com.fenxiangditu.sharemap.net.bean.LoginBean;
import com.fenxiangditu.sharemap.net.callback.RxObserver;
import com.fenxiangditu.sharemap.presenter.BasePresenter;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginAccountPresenter extends BasePresenter<LoginAcountContract.ILoginAcountView> implements LoginAcountContract.ILoginAcountPresenter{
    private LoginModel mLoginModel;
    private LoginAcountContract.ILoginAcountView mILoginAcountView;


    public LoginAccountPresenter() {
        mLoginModel = new LoginModel();
    }

    @Override
    public void login(String phone,String password) {
        mILoginAcountView = getView();
        mLoginModel.login(phone, password, new RxObserver<LoginBean>(this) {
            @Override
            protected void onSuccess(LoginBean data) {
                UserInfoManager.saveUserInfo(data);
                UserInfoManager.saveIsLogin(true);
                mILoginAcountView.hideLoading();
                mILoginAcountView.LoginSuccess();
            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {
                mILoginAcountView.hideLoading();
                mILoginAcountView.showFail(errorMsg);
            }
        });
    }


}
