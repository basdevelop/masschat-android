package com.flowerbell.masschat.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseFragment;
import com.flowerbell.masschat.ui.activity.ChatMessageActivity;
import com.flowerbell.masschat.utils.ToastUtil;
import com.flowerbell.masschat.utils.Utils;

import butterknife.BindView;


public class Fragment2 extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.login)
    TextView mTitle;
    @BindView(R.id.rl_item)
    RelativeLayout rlItem;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment2_list, container, false);
    }

    @Override
    protected void initListener() {
        mTitle.setText("众信");
        //test
        rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatMessageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
