package com.jt.sharemapapi.bean.request;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : zhangjiantao@chehejia.com
 *     time   : 2018/01/02
 *     desc   : 获取短信验证码
 *     version: 1.0
 * </pre>
 */
public class AuthCodeReq {
    /**
     * 手机号
     */
    private String mobile;

    public AuthCodeReq(String mobile) {
        this.mobile = mobile;
    }
}
