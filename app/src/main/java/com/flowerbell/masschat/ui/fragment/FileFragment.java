package com.flowerbell.masschat.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseFragment;


public class FileFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_resource_list, container, false);
    }

    @Override
    protected void initListener() {
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
