package com.flowerbell.masschat.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.flowerbell.masschat.R;
import com.flowerbell.masschat.base.BaseActivity;
import com.flowerbell.masschat.service.AdvertiseService;
import com.flowerbell.masschat.ui.dialog.MyDialogHint;
import com.flowerbell.masschat.ui.fragment.Fragment1;
import com.flowerbell.masschat.ui.fragment.Fragment2;
import com.flowerbell.masschat.ui.fragment.Fragment3;
import com.flowerbell.masschat.ui.fragment.Fragment4;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private FragmentManager fManager;
    @BindView(R.id.button1)
    RadioButton button1;
    @BindView(R.id.button2)
    RadioButton button2;
    @BindView(R.id.button3)
    RadioButton button3;
    @BindView(R.id.button4)
    RadioButton button4;
    private Context context;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button1:
                changeToF1();
                break;
            case R.id.button2:
                changeToF2();

                break;
            case R.id.button3:
                changeToF3();
                break;
            case R.id.button4:
                changeToF4();
                break;
            default:
                break;
        }
    }

    private void changeToF1() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        commonChange(transaction);
        button1.setChecked(true);
        if (fragment1 == null) {
            fragment1 = new Fragment1();
            transaction.add(R.id.container, fragment1, "f1");
        } else {
            transaction.show(fragment1);
        }

        transaction.commitAllowingStateLoss();
    }

    private void changeToF2() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        commonChange(transaction);
        button2.setChecked(true);
        if (fragment2 == null) {
            fragment2 = new Fragment2();
            transaction.add(R.id.container, fragment2, "f2");
        } else {
            transaction.show(fragment2);
        }
        transaction.commitAllowingStateLoss();
    }

    private void changeToF3() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        commonChange(transaction);
        button3.setChecked(true);
        if (fragment3 == null) {
            fragment3 = new Fragment3();
            transaction.add(R.id.container, fragment3, "f3");
        } else {
            transaction.show(fragment3);
        }
        transaction.commitAllowingStateLoss();
    }

    private void changeToF4() {
        fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        commonChange(transaction);
        button4.setChecked(true);
        if (fragment4 == null) {
            fragment4 = new Fragment4();
            transaction.add(R.id.container, fragment4, "f4");
        } else {
            transaction.show(fragment4);
        }
        transaction.commitAllowingStateLoss();
    }

    private void commonChange(FragmentTransaction transaction) {
        fragment1 = (Fragment1) fManager.findFragmentByTag("f1");
        fragment2 = (Fragment2) fManager.findFragmentByTag("f2");
        fragment3 = (Fragment3) fManager.findFragmentByTag("f3");
        fragment4 = (Fragment4) fManager.findFragmentByTag("f4");
        if (fragment1 != null) {
            transaction.hide(fragment1);
        }
        if (fragment2 != null) {
            transaction.hide(fragment2);
        }
        if (fragment3 != null) {
            transaction.hide(fragment3);
        }
        if (fragment4 != null) {
            transaction.hide(fragment4);
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_main);
        context = this;
    }

    @Override
    protected void findViewById() {
        ColorStateList colorStateList = getResources().getColorStateList(
                R.color.tab_text_color);
        button1.setTextColor(colorStateList);
        button2.setTextColor(colorStateList);
        button3.setTextColor(colorStateList);
        button4.setTextColor(colorStateList);

    }

    @Override
    protected void setListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
    }

    @Override
    protected void processLogic() {

        // requestCheckUpdate();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                jump(getIntent());
            }
        }, 200);
        Intent service_intent = new Intent(context, AdvertiseService.class);
        context.startService(service_intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (((keyCode == KeyEvent.KEYCODE_BACK) ||
                (keyCode == KeyEvent.KEYCODE_HOME))
                ) {

            new MyDialogHint(MainActivity.this, R.style.MyDialog1).show();
        }
        return false;
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void jump(Intent intent) {
        if (this == null)
            return;
        int index = intent.getIntExtra("index", 1);
        switch (index) {
            case 1:
                changeToF1();
                break;
            case 2:
                changeToF2();
                break;
            case 3:
                changeToF3();
                break;
            case 4:
                changeToF4();
                break;
            default:
                break;
        }
    }
}
