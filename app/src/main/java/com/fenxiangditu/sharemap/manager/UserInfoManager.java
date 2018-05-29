package com.fenxiangditu.sharemap.manager;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.fenxiangditu.sharemap.common.Const;
import com.fenxiangditu.sharemap.net.bean.LoginBean;
import com.fenxiangditu.sharemap.utils.AesEncryptionUtils;
import com.fenxiangditu.sharemap.utils.PreUtils;

import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/28
 *     desc   : 用户登录信息
 *     version: 1.0
 * </pre>
 */
public class UserInfoManager {
    private static LoginBean loginBean;
    private static String userId;


    public static LoginBean getUserInfo() {
        if (loginBean==null){
            SecretKeySpec keySpec = getAesKey();
            String userInfo = AesEncryptionUtils.decrypt(keySpec, (String) PreUtils.get(Const.USERINFO_KEY.USER_INFO, ""));
            if (TextUtils.isEmpty(userInfo)) {
                return null;
            }
            loginBean = new Gson().fromJson(userInfo, LoginBean.class);
            userId = loginBean.getUserId();
        }
        return loginBean;
    }

    public static String getUserId() {
        return userId;
    }

    public static void saveUserInfo(LoginBean loginbean) {
        userId = loginbean.getUserId();
        String userInfo = new Gson().toJson(loginbean);
        SecretKeySpec key = AesEncryptionUtils.createKey();
        String aesContent = AesEncryptionUtils.encrypt(key, userInfo);
        PreUtils.put(Const.USERINFO_KEY.USER_INFO, aesContent);
        saveAesKey(key);
    }

    private static SecretKeySpec getAesKey() {
        String keyStr = (String) PreUtils.get(Const.USERINFO_KEY.AES, "");
        return AesEncryptionUtils.getSecretKey(Base64.decode(keyStr, Base64.DEFAULT));
    }

    public static void saveAesKey(SecretKeySpec keySpec) {
        PreUtils.put(Const.USERINFO_KEY.AES, Base64.encodeToString(keySpec.getEncoded(), Base64.DEFAULT));
    }

    public static boolean isLogin() {
        return (boolean) PreUtils.get(Const.USERINFO_KEY.IS_LOGIN, false);
    }

    public static void saveIsLogin(boolean isLogin){
        PreUtils.put(Const.USERINFO_KEY.IS_LOGIN,isLogin);
    }
}
