package com.jt.sharemap.ui.login;

import com.jt.sharemap.model.BaseModel;
import com.jt.sharemap.net.RxSchedulers;
import com.jt.sharemap.net.bean.LoginBean;
import com.jt.sharemap.net.bean.SmsCodeBean;
import com.jt.sharemap.net.callback.RxObserver;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginModel extends BaseModel implements ILoginModel {

    @Override
    public void login(String username, String password, RxObserver<LoginBean> callback) {
        doRxRequest().login(username,password,"password")
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

    @Override
    public void login_sms(String username, String sms_code, RxObserver<LoginBean> callback) {
        doRxRequest().login_SMS(username,sms_code,"msgCode")
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

    @Override
    public void register(String username, String password, RxObserver<String> callback) {

    }

    @Override
    public void getSmsCode(String phone,RxObserver<SmsCodeBean> callback) {
        doRxRequest().getSmsCode(phone)
                .compose(RxSchedulers.io_main())
                .subscribe(callback);
    }

}
