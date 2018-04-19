package com.jt.sharemap.ui.login;

import com.jt.sharemap.ui.IView;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginContract {
    interface IUserPresenter {
        void register();
    }

    interface ILoginRegisterView extends IView{
        void showResult(String msg);
    }
}
