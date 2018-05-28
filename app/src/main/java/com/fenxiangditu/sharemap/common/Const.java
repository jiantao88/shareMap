package com.fenxiangditu.sharemap.common;

/**
 * App 常量类
 * Created by 康栋普 on 2018/2/1.
 */

public class Const {

    public static final String FRAGMENT_TAG = "android:support:fragments";

    //用户相关
    public static class USERINFO_KEY {
        //用户信息
        public static final String USER_INFO = "mUserInfo";
        //登录状态
        public static final String IS_LOGIN = "mIsLogin";
        //用户信息密钥
        public static final String AES = "mAES";
    }

    //事件Action
    public static class EVENT_ACTION {
        public static final String REFRESH_DATA = "refresh_list_item";
    }

    //Intent传值
    public static class BUNDLE_KEY {
        public static final String ID = "_id";
        public static final String TITLE = "title";
        public static final String URL = "url";
        public static final String OBJ = "obj";
        public static final String CHAPTER_ID = "chapter_id";
        public static final String CHAPTER_NAME = "chapter_name";
        public static final String INTENT_ACTION_TYPE = "intent_action_type";
        public static final String COLLECT_TYPE = "collect_type";
        public static final String MAP_ID = "map_id";
        public static final int INTENT_ACTION_TREE = 1;
        public static final int INTENT_ACTION_LIST = 2;
    }


    //图片加载
    public static class IMAGE_LOADER {
        public static final int HEAD_IMG = 0;
        public static final int NOMAL_IMG = 1;
        public static final int ROUND_IMG = 2;
    }

    //当前页面状态
    public static class PAGE_STATE {
        //刷新
        public static final int STATE_REFRESH = 0;
        //加载更多
        public static final int STATE_LOAD_MORE = 1;
    }

    //列表Type
    public static class LIST_TYPE {
        public static final int HOME = 0;
        public static final int TREE = 1;
        public static final int COLLECT = 2;
        public static final int SEARCH = 3;
    }

    public static final boolean IS_PRINT_LOG_TO_FILE = false;


}
