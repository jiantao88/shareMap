package com.jt.sharemap.net.bean;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/23
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginBean {

    /**
     * userId : 5a98a9822071ec04f56daa45
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjVhOThhOTgyMjA3MWVjMDRmNTZkYWE0NSIsImlhdCI6MTUyNDQ1MTk1NiwiZXhwIjoxNTI1MDU2NzU2fQ.0AoJ8xOZTng3mGAqjW7_hY3ya2apnLwblT0h9YSAd3Q
     * signStatus : 1
     */

    private String userId;
    private String token;
    private int signStatus;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }
}
