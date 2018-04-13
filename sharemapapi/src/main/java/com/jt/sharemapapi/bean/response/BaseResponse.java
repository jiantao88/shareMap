package com.jt.sharemapapi.bean.response;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : api接口返回格式定义
 *     version: 1.0
 * </pre>
 */

public class BaseResponse<Data> {

    /**
     * code : 100001
     * msg :
     * descs :
     * data : {}
     * pageNo : 12
     * pageSize : 10
     */

    private int code;
    private String msg;
    private String descs;
    private Data data;
    private int pageNo;
    private int pageSize;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDescs() {
        return descs;
    }

    public void setDescs(String descs) {
        this.descs = descs;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
