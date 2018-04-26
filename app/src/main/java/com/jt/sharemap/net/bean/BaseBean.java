package com.jt.sharemap.net.bean;

/**
 * 实体基类
 */

public class BaseBean<T> {
    /**
     * 服务器返回的错误码
     */
    public int status;
    /**
     * 服务器返回的成功或失败的提示
     */
    public String error;
    /**
     * 服务器返回的数据
     */
    public T result;

    public BaseBean(int errorCode, String error, T data) {
        this.status = errorCode;
        this.error = error;
        this.result = data;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "status=" + status +
                ", error='" + error + '\'' +
                ", result=" + result +
                '}';
    }
}
