/*
 * Copyright (c) 2016. Vv <envyfan@qq.com><http://www.v-sounds.com/>
 *
 * This file is part of AndroidReview (Android面试复习)
 *
 * AndroidReview is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  AndroidReview is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 * along with AndroidReview.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.flowerbell.masschat.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flowerbell.masschat.global.Constants;
import com.flowerbell.masschat.utils.HttpUtil;
import com.flowerbell.masschat.utils.UserUtil;
import com.flowerbell.masschat.utils.Utils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.Call;


public abstract class BaseFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;
    protected boolean isCreate = true;
    protected ProgressDialog pDialog;
    protected BaseActivity mActivity;
    protected Context context;
    private View mRootView;

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (BaseActivity) activity;
        context = getActivity();
        super.onAttach(activity);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        context = context;
        this.mActivity = (BaseActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView(inflater, container);
        ButterKnife.bind(this, mRootView);//绑定到butterknife
        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
        initData();
    }

    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initListener();

    protected abstract void initData();

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

    private void httpRequest(String url, Map<String, String> params, HttpUtil.RequestMethod method, int requestId) {

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
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
            pDialog = null;
        }
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(msg);
        pDialog.show();
    }


    protected void dismissDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCreate = false;
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 当Fragment可见时调用该方法
     */
    protected void onVisible() {

    }

    /**
     * Fragment不可见时调用
     */
    protected void onInvisible() {
    }

}
