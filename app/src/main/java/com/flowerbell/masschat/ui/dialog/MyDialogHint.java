package com.flowerbell.masschat.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flowerbell.masschat.R;


public class MyDialogHint extends Dialog implements View.OnClickListener {
    private ImageView loading_image;
    private AnimationDrawable animationDrawable;
    private TextView dialog_hint_text_context;
    private TextView dialog_hint_text_yes;
    private TextView dialoh_hint_text_no;

    ClickCallBack _confirmAction = null, _cancelAction = null;

    public MyDialogHint(Context context, int theme) {
        super(context, theme);
    }

    protected MyDialogHint(Context context, boolean cancelable,
                           OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public MyDialogHint(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_hint);
        initview();
    }

    public void initview() {
        dialog_hint_text_context = (TextView) findViewById(R.id.dialog_hint_text_context);
        dialog_hint_text_yes = (TextView) findViewById(R.id.dialog_hint_text_yes);
        dialoh_hint_text_no = (TextView) findViewById(R.id.dialoh_hint_text_no);
        dialoh_hint_text_no.setOnClickListener(this);
        dialog_hint_text_yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialoh_hint_text_no:
                if (null != this._cancelAction){
                    this._cancelAction.execute();
                }
                dismiss();
                break;
            case R.id.dialog_hint_text_yes:
                if (null != this._confirmAction){
                    this._confirmAction.execute();
                }
                dismiss();
                break;
        }
    }

    public void buildDialog(String s, ClickCallBack ok, ClickCallBack cancel) {
        dialog_hint_text_context.setText(s);
        this._confirmAction = ok;
        this._cancelAction = cancel;
    }

    public interface ClickCallBack{
        public void execute();
    }
}
