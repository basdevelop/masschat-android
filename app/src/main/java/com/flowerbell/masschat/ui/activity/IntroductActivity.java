package com.flowerbell.masschat.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseActivity;


public class IntroductActivity extends BaseActivity {

    private boolean start;
    private static final int PAUSE_TIME = 700;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        start = getIntent().getBooleanExtra("start", true);
        setContentView(R.layout.introduct);

        mHandler.sendEmptyMessageDelayed(1, PAUSE_TIME);
    }

    @Override
    protected void loadViewLayout() {

    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic() {

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {

                jumpToHome();

            }

        }
    };

    private void jumpToHome() {
        Intent i = new Intent(
                IntroductActivity.this,
                MainActivity.class);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (start) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
