package com.jt.sharemapapi.bean;

import java.io.Serializable;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : 自定义header实体
 *     version: 1.0
 * </pre>
 */

public class ApiHttpHeaders implements Serializable {
    private String headerEnv;
    private String headerVersion;
    private String headerKey;
    private String headerType;
    private String contentLanguage;
    private String contentMd5;
    private String headerSign;
    private String headerCarTraceMode;
    private String headerToken;

    public String getHeaderEnv() {
        return headerEnv;
    }

    public void setHeaderEnv(String headerEnv) {
        this.headerEnv = headerEnv;
    }

    public String getHeaderVersion() {
        return headerVersion;
    }

    public void setHeaderVersion(String headerVersion) {
        this.headerVersion = headerVersion;
    }

    public String getHeaderKey() {
        return headerKey;
    }

    public void setHeaderKey(String headerKey) {
        this.headerKey = headerKey;
    }

    public String getHeaderType() {
        return headerType;
    }

    public void setHeaderType(String headerType) {
        this.headerType = headerType;
    }

    public String getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

    public String getContentMd5() {
        return contentMd5;
    }

    public void setContentMd5(String contentMd5) {
        this.contentMd5 = contentMd5;
    }

    public String getHeaderSign() {
        return headerSign;
    }

    public void setHeaderSign(String headerSign) {
        this.headerSign = headerSign;
    }

    public String getHeaderCarTraceMode() {
        return headerCarTraceMode;
    }

    public void setHeaderCarTraceMode(String headerCarTraceMode) {
        this.headerCarTraceMode = headerCarTraceMode;
    }

    public String getHeaderToken() {
        return headerToken;
    }

    public void setHeaderToken(String headerToken) {
        this.headerToken = headerToken;
    }
}
