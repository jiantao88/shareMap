package com.jt.sharemap.net.bean;

/**
 * 实体基类
 */

public class BaseBean<T> {
    /**
     * 服务器返回的错误码
     */
    public int errorCode;
    /**
     * 服务器返回的成功或失败的提示
     */
    public String errorMsg;
    /**
     * 服务器返回的数据
     */
    public T data;

    public BaseBean(int errorCode, String errorMsg, T data) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
