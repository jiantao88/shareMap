package com.jt.sharemap.common;

/**
 * api地址
 */
public class UrlConstainer {
    public static final String BASE_URL_DEV = "http://127.0.0.1:3000/";
    public static final String BASE_URL = "http://fenxiangditu.com/";
    /**
     * 登录
     */
    public static final String LOGIN = "user/login";

    /**
     * 注册
     */
    public static final String REGISTER = "user/signup";

    public static final String SMS_CODE = "user/msgCode";
    /**
     * 首页文章列表
     */
    public static final String HOME_LIST = "map/list?";

    /**
     * 地图详情
     */
    public static final String MAP_DETAIL = "map";
    /**
     * 照片上传
     */
    public static final String PHOTO_UPLOAD="qiniu/token";

    /**
     * 个人信息上传
     */
    public static final String USER_UPDATE = "user/update";

    /**
     * 点赞
     */
    public static final String LIKE = "like";

    /**
     * 评论
     */
    public static final String COMMENT = "comment";
}
