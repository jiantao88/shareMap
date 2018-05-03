package com.jt.sharemap.ui.login;

import com.jt.sharemap.ui.IView;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : a_tao123@163.com
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface LoginAcountContract {
    interface ILoginAcountPresenter {
        void login(String phone,String password);
    }

    interface ILoginAcountView extends IView {

        void LoginSuccess();
    }
}
