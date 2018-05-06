package com.jt.sharemap.manager;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.jt.sharemap.common.Const;
import com.jt.sharemap.net.bean.LoginBean;
import com.jt.sharemap.utils.AesEncryptionUtils;
import com.jt.sharemap.utils.PreUtils;

import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 *     @author : zhangjiantao
 *     time   : 2018/04/28
 *     desc   : 用户登录信息
 *     version: 1.0
 * </pre>
 */
public class UserInfoManger {
    public static LoginBean loginBean;


    public static LoginBean getUserInfo() {
        if (loginBean==null){
            SecretKeySpec keySpec = getAesKey();
            String userInfo = AesEncryptionUtils.decrypt(keySpec, (String) PreUtils.get(Const.USERINFO_KEY.USER_INFO, ""));
            if (TextUtils.isEmpty(userInfo)) {
                return null;
            }
            loginBean = new Gson().fromJson(userInfo, LoginBean.class);
        }
        return loginBean;
    }

    public static void saveUserInfo(LoginBean LoginBean) {
        String userInfo = new Gson().toJson(LoginBean);
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
