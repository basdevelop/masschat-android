package com.flowerbell.masschat.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseActivity;
import com.flowerbell.masschat.utils.ToastUtil;

import butterknife.BindView;


public class FileHistoryActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.login_btn_back)
    ImageView mReturn;

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_history);
    }

    @Override
    protected void findViewById() {
        mTitle.setText("历史");
        mReturn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        mReturn.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_back:
                finish();
                break;
            default:
                break;
        }
    }
}
