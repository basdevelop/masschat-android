package com.flowerbell.masschat.global;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.List;

import okhttp3.OkHttpClient;

public class ZXApplication extends Application {

    private static Context mCtx;

    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        initOkHttp();
    }

    private void initOkHttp() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    public static Context getContext() {
        return mCtx;
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
