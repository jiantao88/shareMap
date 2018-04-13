package com.jt.sharemapapi;

import com.chehejia.ampgo.api.bean.response.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : 车机控制API
 *     version: 1.0
 * </pre>
 */

public interface VehicleApi {

    /**
     * 获取车辆动态信息(V)
     *
     * http://rap.chj.com/workspace/myWorkspace.do?projectId=35#644
     * 对接人：贾文俊
     *
     * @param vin
     * @return
     */
    @GET("/vcs/1_0/vehicle/app/vehicleDyncByVin")
    Observable<BaseResponse<Object>> vehicleDyncByVin(@Query("vin") String vin);

    /**
     * APP远程控制车机(V)
     *
     * http://rap.chj.com/workspace/myWorkspace.do?projectId=36#446
     * 对接人：贾文俊
     *
     * @param req
     * @return
     */
    @POST("vc/1_0/push")
    Observable<BaseResponse<String>> operate(@Body Object req);


}
