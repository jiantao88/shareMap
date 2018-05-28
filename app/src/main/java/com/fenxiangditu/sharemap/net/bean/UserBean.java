package com.fenxiangditu.sharemap.net.bean;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/22
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class UserBean{

    /**
     * userId : 5a98a9822071ec04f56daa45
     * userName : jt
     * avatar : http://okyb0e40i.bkt.clouddn.com/FkCTkc0WuYLAeJLo5-aoHejrel1h
     * sex : male
     * signature : 测试测试
     */

    private String userId;
    private String userName;
    private String avatar;
    private String sex;
    private String signature;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
