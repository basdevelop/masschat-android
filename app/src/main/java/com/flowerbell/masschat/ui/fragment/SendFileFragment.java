package com.flowerbell.masschat.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseFragment;
import com.flowerbell.masschat.view.BackgroundProgress;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;


public class SendFileFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.pg_pass)
    BackgroundProgress mPgpass;
    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.rl_scan)
    RelativeLayout mRlScan;
    @BindView(R.id.iv_frame)
    ImageView mIvFrame;
    private static final int REQUEST_CODE = 101;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sendfile, container, false);
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
                String url = "你好hello";
                Bitmap mBitmap = CodeUtils.createImage(url, 300, 300, null);
                ivErweima.setImageBitmap(mBitmap);
                mRlScan.setVisibility(View.GONE);
                //选择文件
//                Intent intent = new Intent(context, SelectFileActivity.class);
//                startActivityForResult(intent, REQUEST_CODE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
    //test
}
