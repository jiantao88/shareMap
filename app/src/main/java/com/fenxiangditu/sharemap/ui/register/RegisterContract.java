package com.fenxiangditu.sharemap.ui.register;

import com.fenxiangditu.sharemap.ui.IView;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/05/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public interface RegisterContract {
    interface IRegisterPresenter {
        void getSmSCode(String phone);

        void register(String phone, String sms_code, String password);
    }

    interface IRegisterIvew extends IView {
        void getSmeCodeSuccess();
    }
}
