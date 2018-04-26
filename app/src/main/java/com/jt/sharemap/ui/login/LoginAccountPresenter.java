package com.jt.sharemap.ui.login;

import com.jt.sharemap.net.bean.LoginBean;
import com.jt.sharemap.net.callback.RxObserver;
import com.jt.sharemap.presenter.BasePresenter;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginAccountPresenter extends BasePresenter<LoginAcountContract.ILoginAcountView> implements LoginAcountContract.ILoginAcountPresenter{
    private String username, password;
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

            }

            @Override
            protected void onFail(int errorCode, String errorMsg) {

            }
        });
    }


}
