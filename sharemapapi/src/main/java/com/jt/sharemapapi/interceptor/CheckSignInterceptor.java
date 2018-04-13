package com.jt.sharemapapi.interceptor;

import com.chehejia.ampgo.api.ApiConstants;
import com.chehejia.ampgo.api.security.HmacCoder;
import com.chehejia.ampgo.api.utils.ApiUtils;
import com.chehejia.ampgo.api.utils.TraceLog;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : 验签监听
 *     version: 1.0
 * </pre>
 */

public class CheckSignInterceptor implements Interceptor {

    private String secUrl = "/sec/1_0/deviceKey/generate";
    @Override
    public Response intercept(Chain chain) throws IOException {
        TraceLog.d("----------- Parsing Server Sign Start -----------");

        Request request = chain.request();
        Response response = chain.proceed(request);

        if (secUrl.equals(request.url().encodedPath())) {
            TraceLog.d( "Is not active hmi request");
            TraceLog.d( "----------- Parsing Server Sign End -----------");
            return response;
        }

        Headers headers = response.headers();

        String originalSign = headers.get(ApiConstants.HEADER_KEY_X_CHJ_SIGN);
        String producedSign = HmacCoder.encrypt(
                ApiUtils.packageReceivedStringToSign(
                        headers.get(ApiConstants.HEADER_KEY_X_CHJ_ENV),
                        headers.get(ApiConstants.HEADER_KEY_X_CHJ_VERSION),
                        ApiConstants.keySuite.getKeyId(),
                        headers.get(ApiConstants.HEADER_KEY_CONTENT_MD5),
                        headers.get(ApiConstants.HEADER_KEY_X_CHJ_TIMESTAMP)
                ),
                ApiConstants.keySuite.getKeySecret().getHmacKey());
        TraceLog.d( "OriginalSign:" + originalSign);
        TraceLog.d( "ProducedSign" + producedSign);

        if (originalSign == null || !originalSign.equals(producedSign)) {
            TraceLog.d( "----------- Parsing Server Sign End -----------");
            //TODO 暂时不做验签
//            return response.newBuilder().code(ApiConstants.SERVER_DATA_WRONG).message("返回数据验签错误").build();
        }

        TraceLog.d( "----------- Parsing Server Sign End -----------");
        return response;
    }
}
