
package com.flowerbell.masschat.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.orhanobut.logger.Logger;
import com.flowerbell.masschat.R;
import com.flowerbell.masschat.global.Constants;
import com.flowerbell.masschat.utils.AppManager;
import com.flowerbell.masschat.utils.HttpUtil;
import com.flowerbell.masschat.utils.SystemBarTintManager;
import com.flowerbell.masschat.utils.UserUtil;
import com.flowerbell.masschat.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;


public abstract class BaseActivity extends FragmentActivity {
    protected Context context = this;
    protected Activity activity = this;
    protected ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentLayout();
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView();
        ButterKnife.bind(this);
        initdata();
        AppManager.getAppManager().addActivity(this);
        Logger.d("当前Activity 栈中有：" + AppManager.getAppManager().getActivityCount() + "个Activity");
    }

    private void initView() {
        setStatusBar();
        loadViewLayout();
    }

    private void initdata() {
        findViewById();
        setListener();
        processLogic();
    }

    public void httpRequestByGet(String url, Map<String, String> params,
                                 final int requestId) {
        params.put("appv", Utils.getAppVersion(context));
        params.put("src", "android");
        params.put("model", Constants.MODEL);
        params.put("os", Constants.PHONEOS);
        params.put("uuid", Utils.getIMEI(context));
        params.put("uid", UserUtil.getUid());
        httpRequest(url, params, HttpUtil.RequestMethod.GET, requestId);
    }

    public void httpRequestByPost(String url, Map<String, String> params,
                                  final int requestId) {
        params.put("appv", Utils.getAppVersion(context));
        params.put("src", "android");
        params.put("model", Constants.MODEL);
        params.put("os", Constants.PHONEOS);
        params.put("uuid", Utils.getIMEI(context));
        params.put("uid", UserUtil.getUid());
        httpRequest(url, params, HttpUtil.RequestMethod.POST, requestId);
    }

    public void httpRequest(String url, Map<String, String> params, HttpUtil.RequestMethod method, int requestId) {

        if (HttpUtil.RequestMethod.GET.equals(method)) {
            OkHttpUtils.get().url(HttpUtil.BASEURL + url).params(params).id(requestId).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    dismissDialog();
                    onHttpRequestErr(call, e, id);
                }

                @Override
                public void onResponse(String response, int id) {
                    dismissDialog();
                    onHttpRequestResult(response, id);
                }
            });
        } else {
            OkHttpUtils.post().url(HttpUtil.BASEURL + url).params(params).id(requestId).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    dismissDialog();
                    onHttpRequestErr(call, e, id);
                }

                @Override
                public void onResponse(String response, int id) {
                    dismissDialog();
                    onHttpRequestResult(response, id);
                }
            });
        }
    }

    public void httpRequestFullPath(String url, Map<String, String> params, HttpUtil.RequestMethod method, int requestId) {

        if (HttpUtil.RequestMethod.GET.equals(method)) {
            OkHttpUtils.get().url(url).params(params).id(requestId).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    dismissDialog();
                    onHttpRequestErr(call, e, id);
                }

                @Override
                public void onResponse(String response, int id) {
                    dismissDialog();
                    onHttpRequestResult(response, id);
                }
            });
        } else {
            OkHttpUtils.post().url(url).params(params).id(requestId).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    dismissDialog();
                    onHttpRequestErr(call, e, id);
                }

                @Override
                public void onResponse(String response, int id) {
                    dismissDialog();
                    onHttpRequestResult(response, id);
                }
            });
        }
    }

    protected void onHttpRequestResult(String response, int requestId) {

    }

    protected void onHttpRequestErr(Call call, Exception e, int id) {

    }


    protected void showProgressDialog(String msg) {
        try {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
                pDialog = null;
            }
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage(msg);
            pDialog.show();
        } catch (Exception e) {
            if (activity.getParent() != null) {
                activity = activity.getParent();
                pDialog = new ProgressDialog(activity);
                pDialog.setMessage(msg);
                pDialog.show();
            }
            e.printStackTrace();
        }
    }

    protected void dismissDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {

        pDialog = null;
        AppManager.getAppManager().removeActivity(this);
        super.onDestroy();
    }


    /**
     * 设置布局前的操作
     */
    protected void onBeforeSetContentLayout() {
    }

    public void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            // 激活状态栏
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint 激活导航栏
            tintManager.setNavigationBarTintEnabled(true);
            //设置系统栏设置颜色
            //tintManager.setTintColor(R.color.red);
            //给状态栏设置颜色
            tintManager.setTintColor(R.drawable.shape_status_bar);
            // tintManager.setStatusBarTintResource(R.color.themecolor);
            //Apply the specified drawable or color resource to the system navigation bar.
            //给导航栏设置资源
            tintManager.setNavigationBarTintResource(R.color.themecolor);
        }
    }

    /**
     * 加载页面layout
     */
    protected abstract void loadViewLayout();

    /**
     * 加载页面元素
     */
    protected abstract void findViewById();

    /**
     * 设置各种事件的监听器
     */
    protected abstract void setListener();

    /**
     * 业务逻辑处理，主要与后端交互
     */
    protected abstract void processLogic();

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
