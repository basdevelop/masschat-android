package com.flowerbell.masschat.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseFragment;
import com.flowerbell.masschat.global.ZXApplication;
import com.flowerbell.masschat.ui.activity.ScanActivity;
import com.flowerbell.masschat.utils.ToastUtil;
import com.flowerbell.masschat.view.BackgroundProgress;

import butterknife.BindView;


public class ReceiveFileFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.pg_pass)
    BackgroundProgress mPgpass;
    @BindView(R.id.rl_scan)
    RelativeLayout mRlScan;
    private static final int REQUEST_CODE = 100;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_receivefile, container, false);
    }

    @Override
    protected void initListener() {
        mRlScan.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        go();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_scan:
                //TODO 处理相机权限

                Intent intent = new Intent(context, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.showToast(context, "解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showToast(context, "解析二维码失败");
                }
            }
        }
    }

    //test
    int i = 0;
    Handler handler = new Handler();

    void go() {
        i++;
        mPgpass.setProgress(i);
        mPgpass.setMax(100);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (i == 100) {
                    return;
                }
                go();
            }
        }, 100);
    }

}
