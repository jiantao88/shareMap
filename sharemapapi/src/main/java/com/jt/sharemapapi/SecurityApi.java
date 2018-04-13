package com.jt.sharemapapi;

import com.chehejia.ampgo.api.bean.KeySuite;
import com.chehejia.ampgo.api.bean.request.KeySuiteReq;
import com.chehejia.ampgo.api.bean.response.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : 安全API
 *     version: 1.0
 * </pre>
 */

public interface SecurityApi {

    /**
     * App首次激活时申请KeySuite
     * 由于安全需要，App后续更换时，要重新申请KeySuite
     * @param req
     * @return
     */
    @POST("/eds/1_0/app/key_suite")
    Observable<BaseResponse<KeySuite>> keySuite(@Body KeySuiteReq req);

    /**
     * 申请KeySuite后，需要验证KeySuite才能正式使用。
     * @param req
     * @return
     */
    @POST("/eds/1_0/app/key_suite/verification")
    Observable<BaseResponse<Boolean>> keyVerification(@Body KeySuiteReq req);
}
