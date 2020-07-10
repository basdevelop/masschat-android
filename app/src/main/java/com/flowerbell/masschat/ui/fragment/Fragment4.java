package com.flowerbell.masschat.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseFragment;
import com.flowerbell.masschat.global.Constants;
import com.flowerbell.masschat.utils.GlideUtil;

import butterknife.BindView;


public class Fragment4 extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.login)
    TextView mTitle;
    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_signature)
    TextView mTvSignature;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.rl_music)
    RelativeLayout mRlMusic;
    @BindView(R.id.rl_video)
    RelativeLayout mRlVideo;
    @BindView(R.id.rl_picture)
    RelativeLayout mRlPicture;
    @BindView(R.id.rl_file)
    RelativeLayout mRlFile;
    @BindView(R.id.rl_wechat)
    RelativeLayout mRlWechat;
    @BindView(R.id.rl_exit)
    RelativeLayout mRlExit;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment4_list, container, false);
    }

    @Override
    protected void initListener() {
        mRlMusic.setOnClickListener(this);
        mRlVideo.setOnClickListener(this);
        mRlPicture.setOnClickListener(this);
        mRlFile.setOnClickListener(this);
        mRlWechat.setOnClickListener(this);
        mRlExit.setOnClickListener(this);
        mRlExit.setOnClickListener(this);
        mIvEdit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mTitle.setText("我的");
        GlideUtil.LoadCircleCorners(mIvAvatar, Constants.IC_AVATAR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_music:
                break;
            case R.id.rl_video:
                break;
            case R.id.rl_picture:
                break;
            case R.id.rl_file:
                break;
            case R.id.rl_wechat:
                break;
            case R.id.rl_exit:
                break;
            case R.id.iv_edit:
                break;
            default:
                break;
        }
    }

}
