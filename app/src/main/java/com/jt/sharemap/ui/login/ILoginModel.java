package com.jt.sharemap.ui.login;

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
public interface ILoginModel {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    void login(String username, String password, RxObserver<LoginBean> callback);

    /**
     * 登录 sms
     *
     * @param username 用户名
     * @param sms_code 密码
     */
    void login_sms(String username, String sms_code, RxObserver<LoginBean> callback);


    /**
     * 注册
     *
     * @param phone    用户名
     * @param password 密码
     */
    void register(String phone, String sms_code,String password, RxObserver<String> callback);

    /**
     * 获取短信验证码
     *
     * @param phone
     */
    void getSmsCode(String phone, RxObserver<SmsCodeBean> callback);
}
