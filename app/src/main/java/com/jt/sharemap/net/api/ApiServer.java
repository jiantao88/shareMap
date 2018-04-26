package com.jt.sharemap.net.api;

import com.jt.sharemap.common.UrlConstainer;
import com.jt.sharemap.net.bean.BaseBean;
import com.jt.sharemap.net.bean.CommentBean;
import com.jt.sharemap.net.bean.GiveLikeBean;
import com.jt.sharemap.net.bean.HomeBean;
import com.jt.sharemap.net.bean.LoginBean;
import com.jt.sharemap.net.bean.MapDetailBean;
import com.jt.sharemap.net.bean.SmsCodeBean;
import com.jt.sharemap.net.bean.UserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author zhangjiantao
 */

public interface ApiServer {

    /**
     * 登录
     *
     * @param username  用户名
     * @param password  密码
     * @param loginType password 密码
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.LOGIN)
    Observable<BaseBean<LoginBean>> login(@Field("username") String username, @Field("password") String password, @Field("loginType") String loginType);

    /**
     * 登录 短信
     *
     * @param username  用户名
     * @param msgCode   短信验证码
     * @param loginType msgCode
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.LOGIN)
    Observable<BaseBean<LoginBean>> login_SMS(@Field("username") String username, @Field("msgCode") String msgCode, @Field("loginType") String loginType);

    /**
     * 注册
     *
     * @param phone    用户名
     * @param password 密码
     * @param msgCode  验证码
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.REGISTER)
    Observable<BaseBean<String>> register(@Field("phone") String phone, @Field("password") String password, @Field("msgCode") String msgCode);


    /**
     * 获取短信验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstainer.SMS_CODE)
    Observable<BaseBean<SmsCodeBean>> getSmsCode(@Field("phone") String phone);


    /**
     * 首页 列表
     */
    @GET(UrlConstainer.HOME_LIST)
    Observable<HomeBean> getHomeList(@Path("pageSize") int pageSize);

    /**
     * 地图详情
     *
     * @param mapId
     * @return
     */
    @GET(UrlConstainer.MAP_DETAIL)
    Observable<MapDetailBean> getMapDetail(@Path("mapId") String mapId);

    /**
     * 个人信息上传
     *
     * @param userId
     * @param userName
     * @param avatar
     * @param sex
     * @param signature
     * @return
     */
    @POST(UrlConstainer.USER_UPDATE)
    Observable<UserBean> userUpdate(@Field("userId") String userId, @Field("userName") String userName, @Field("avatar") String avatar, @Field("sex") String sex, @Field("signature") String signature);

    /**
     * 点赞
     *
     * @param targetId
     * @param targetType
     * @param userId
     * @param hasLiked
     * @return
     */
    @POST(UrlConstainer.LIKE)
    Observable<GiveLikeBean> giveLike(@Field("targetId") String targetId, @Field("targetType") String targetType, @Field("userId") String userId, @Field("hasLiked") boolean hasLiked);

    /**
     * 评论
     *
     * @param userId
     * @param content
     * @param pageId
     * @param pageType
     * @return
     */
    @POST(UrlConstainer.COMMENT)
    Observable<CommentBean> comment(@Field("userId") String userId, @Field("content") String content, @Field("pageId") String pageId, @Field("pageType") String pageType);

}
