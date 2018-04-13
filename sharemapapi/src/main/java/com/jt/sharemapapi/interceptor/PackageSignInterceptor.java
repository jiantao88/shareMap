package com.jt.sharemapapi.interceptor;


import com.jt.sharemapapi.ApiConstants;
import com.jt.sharemapapi.bean.ApiHttpHeaders;
import com.jt.sharemapapi.utils.ApiUtils;
import com.jt.sharemapapi.utils.Md5Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * <pre>
 *     @author : chenjianquan
 *     e-mail : chenjianquan@chenjianquan.com
 *     time   : 2017/12/27
 *     desc   : OkHttp3  签名监听
 *     version: 1.0
 * </pre>
 */

public class PackageSignInterceptor implements Interceptor {

    private ApiHttpHeaders mHttpHeaders;
    private static final String HEADER_REQ_ACCEPT = "accept";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        String method = originalRequest.method();
        String accept = originalRequest.header(HEADER_REQ_ACCEPT);
        String timestamp = String.valueOf(System.currentTimeMillis());
        String nonce = ApiUtils.getUUID();

        String env = mHttpHeaders.getHeaderEnv();
        String version = mHttpHeaders.getHeaderVersion();
        String key = mHttpHeaders.getHeaderKey();

        String contentLanguage = mHttpHeaders.getContentLanguage();

        String contentType = mHttpHeaders.getHeaderType();
        String token = mHttpHeaders.getHeaderToken();

        String contentMd5 = "";
        String requestBody = bodyToString(chain.request());
        if (requestBody != null) {
            contentMd5 = Md5Utils.eccrypt(requestBody);
        }


        Headers.Builder headers = originalRequest.headers().newBuilder();
        headers.add(ApiConstants.HEADER_KEY_X_CHJ_ENV, env);
        headers.add(ApiConstants.HEADER_KEY_X_CHJ_VERSION, version);
        headers.add(ApiConstants.HEADER_KEY_X_CHJ_KEY, key);
        headers.add(ApiConstants.HEADER_KEY_X_CHJ_TIMESTAMP, timestamp);
        headers.add(ApiConstants.HEADER_KEY_X_CHJ_NONCE, nonce);
        headers.set(ApiConstants.HEADER_KEY_CONTENT_TYPE, contentType);
        headers.add(ApiConstants.HEADER_KEY_CONTENT_LANGUAGE, contentLanguage);
        headers.add(ApiConstants.HEADER_KEY_CONTENT_MD5, contentMd5);
//        headers.add(ApiConstants.HEADER_KEY_X_CHJ_SIGN, sign);
        headers.add(ApiConstants.HEADER_KEY_X_CAT_TRACE_MODE, "true");
        headers.add(ApiConstants.HEADER_KEY_X_CHJ_TOKEN, token);

        Request newRequest = chain.request().newBuilder()
                .headers(headers.build())
                .build();
        return chain.proceed(newRequest);
    }

    private static String bodyToString(final Request request) {
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Sink sink = Okio.sink(baos);
            BufferedSink bufferedSink = Okio.buffer(sink);

            if(request.body() == null){
                return "";
            }
            request.body().writeTo(bufferedSink);

            return  bufferedSink.buffer().readUtf8();
        }catch (IOException e){
            return "";
        }
    }


    public void setHttpHeaders(ApiHttpHeaders httpHeaders){
        this.mHttpHeaders = httpHeaders;
    }

    public ApiHttpHeaders getHttpHeaders(){
        return this.mHttpHeaders;
    }
}
