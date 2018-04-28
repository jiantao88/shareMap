package com.jt.sharemap.ui.login;

import com.jt.sharemap.ui.IView;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/21
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginSmsContract {
    interface ILoginSmsPresenter{
        void login(String phone,String smsCode);

        void getSmSCode(String phone);
    }
    interface ILoginSmsView extends IView {
        void getSmeCodeSuccess();

        void LoginSuccess();

    }
}
