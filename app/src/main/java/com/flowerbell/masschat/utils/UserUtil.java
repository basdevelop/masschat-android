package com.flowerbell.masschat.utils;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.flowerbell.masschat.domain.LoginUser;
import com.flowerbell.masschat.global.ZXApplication;

public class UserUtil {
    public static LoginUser loginUser;
    public static double lat;

    public static synchronized String getUid() {
        if (null == loginUser) {
            loginUser = getLoginUser();
        }

        return loginUser.getUid();
    }

    public synchronized static void setUid(String uid) {
        if (null == loginUser) {
            loginUser = getLoginUser();
        }
        loginUser.setUid(uid);
    }

    public synchronized static LoginUser getLoginUser() {
        if (null == loginUser) {
            //TODO::reload user info.
            String loginUserStr = SharePreferenceUtil.getUtil(ZXApplication.getContext()).getString("jsonString", "");
            if (TextUtils.isEmpty(loginUserStr)) {
                loginUser = new LoginUser();
                loginUser.setUid("0");
                loginUser.setName("");
                Log.w("JCC_USER", "-------新用户本地没保存-------");
            } else {
                loginUser = JSON.parseObject(loginUserStr, LoginUser.class);
                Log.w("JCC_USER", "------ 本地保存了-------uid=" + loginUser.getUid());
                return loginUser;
            }
        }

        return loginUser;
    }

    public static void setLoginUser(LoginUser ui) {
        loginUser = ui;
    }

    public static double getLat() {
        return lat;
    }

    public static void setLat(double lat) {
        UserUtil.lat = lat;
    }

}
